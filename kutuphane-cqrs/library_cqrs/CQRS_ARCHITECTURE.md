# CQRS & Mediator Pattern - Library Management System

## Architecture Overview

Bu dokument, Kütüphane Yönetim Sistemi'nin CQRS (Command Query Responsibility Segregation) ve Custom Mediator tasarım deseni kullanılarak yeniden yapılandırılması hakkında bilgi vermektedir.

---

## 1. Proje Yapısı

```
src/main/java/com/turkcell/library_cqrs/library_cqrs/
├── core/
│   └── mediator/
│       ├── Mediator.java (Interface)
│       ├── SpringMediator.java (Implementation)
│       └── cqrs/
│           ├── Command.java (Interface - Generic)
│           ├── CommandHandler.java (Interface - Generic)
│           ├── Query.java (Interface - Generic)
│           └── QueryHandler.java (Interface - Generic)
│
├── application/
│   ├── dto/
│   │   ├── CreateKategoriRequest.java
│   │   ├── CreatedKategoriResponse.java
│   │   ├── ListKategoriResponse.java
│   │   ├── CreateKitapRequest.java
│   │   ├── CreatedKitapResponse.java
│   │   ├── ListKitapResponse.java
│   │   ├── ErrorResponse.java
│   │   └── ValidationErrorResponse.java
│   │
│   └── features/
│       ├── kategori/
│       │   ├── command/
│       │   │   └── create/
│       │   │       ├── CreateKategoriCommand.java
│       │   │       └── CreateKategoriCommandHandler.java
│       │   │
│       │   └── query/
│       │       └── getall/
│       │           ├── GetAllKategoriQuery.java
│       │           └── GetAllKategoriQueryHandler.java
│       │
│       └── kitap/
│           ├── command/
│           │   └── create/
│           │       ├── CreateKitapCommand.java
│           │       └── CreateKitapCommandHandler.java
│           │
│           └── query/
│               └── getall/
│                   ├── GetAllKitapQuery.java
│                   └── GetAllKitapQueryHandler.java
│
├── entity/
│   ├── Kategori.java
│   ├── Kitap.java
│   ├── Ogrenci.java
│   ├── Gorevli.java
│   ├── OduncAlma.java
│   ├── Iade.java
│   └── Ceza.java
│
├── repository/
│   ├── KategoriRepository.java
│   └── KitapRepository.java
│
├── exception/
│   ├── BusinessException.java
│   ├── GlobalExceptionHandler.java
│   ├── KitapBulunamadiException.java
│   ├── KitapZatenOduncteException.java
│   └── OgrenciZatenKayitliException.java
│
└── web/
    └── controller/
        ├── KategorilerController.java
        └── KitaplarController.java
```

---

## 2. Core CQRS Mimarisi

### 2.1 Command & CommandHandler

**Command**: Veri değiştirme işlemlerini temsil eder.
```java
public interface Command<R> {
    // Marker Interface
    // R: Döndürülecek response tipi
}
```

**CommandHandler**: Komutları işleyen handler'lar.
```java
public interface CommandHandler<C extends Command<R>, R> {
    R handle(C command);
}
```

**Örnek:**
```java
// Command
public class CreateKategoriCommand implements Command<CreatedKategoriResponse> {
    private String kategoriAdi;
    // Getter/Setter
}

// CommandHandler
@Component
public class CreateKategoriCommandHandler 
    implements CommandHandler<CreateKategoriCommand, CreatedKategoriResponse> {
    
    @Override
    public CreatedKategoriResponse handle(CreateKategoriCommand command) {
        // İş mantığını uygula
        Kategori kategori = new Kategori(command.getKategoriAdi());
        Kategori saved = kategoriRepository.save(kategori);
        return new CreatedKategoriResponse(saved.getKategoriId(), saved.getKategoriAdi());
    }
}
```

### 2.2 Query & QueryHandler

**Query**: Veri okuma işlemlerini temsil eder (yan etki yaratmaz).
```java
public interface Query<R> {
    // Marker Interface
    // R: Döndürülecek response tipi
}
```

**QueryHandler**: Sorguları işleyen handler'lar.
```java
public interface QueryHandler<Q extends Query<R>, R> {
    R handle(Q query);
}
```

**Örnek:**
```java
// Query
public class GetAllKategoriQuery implements Query<List<ListKategoriResponse>> {
    // Sorgu parametreleri (gerekirse)
}

// QueryHandler
@Component
public class GetAllKategoriQueryHandler 
    implements QueryHandler<GetAllKategoriQuery, List<ListKategoriResponse>> {
    
    @Override
    public List<ListKategoriResponse> handle(GetAllKategoriQuery query) {
        return kategoriRepository.findAll()
            .stream()
            .map(k -> new ListKategoriResponse(k.getKategoriId(), k.getKategoriAdi()))
            .collect(Collectors.toList());
    }
}
```

---

## 3. Mediator Pattern

### 3.1 Mediator Interface
```java
public interface Mediator {
    <R> R send(Command<R> command);
    <R> R send(Query<R> query);
}
```

### 3.2 SpringMediator Implementation

**SpringMediator**, ApplicationContext kullanarak dinamik olarak ilgili handler'ı bulur ve çalıştırır:

```java
@Component
public class SpringMediator implements Mediator {
    private final ApplicationContext applicationContext;

    @Override
    public <R> R send(Command<R> command) {
        // İlgili CommandHandler'ı bul
        CommandHandler<Command<R>, R> handler = findCommandHandler(command, ...);
        // Handler'ı çalıştır
        return handler.handle(command);
    }

    @Override
    public <R> R send(Query<R> query) {
        // İlgili QueryHandler'ı bul
        QueryHandler<Query<R>, R> handler = findQueryHandler(query, ...);
        // Handler'ı çalıştır
        return handler.handle(query);
    }
}
```

**Handler Bulma Mekanizması:**
1. Bean adını Convention'a göre generate et (CreateKategoriCommand → createKategoriHandler)
2. ApplicationContext'te o bean'i ara
3. Bulunmazsa, tüm CommandHandler/QueryHandler bean'lerini tara ve generic type kontrol et

---

## 4. Controller Katmanı

Controller'lar **sadece Mediator**'ı inject alırlar ve bağımlılıklarını Mediator aracılığıyla çözerler:

```java
@RestController
@RequestMapping("/api/kategoriler")
public class KategorilerController {

    private final Mediator mediator;

    public KategorilerController(Mediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping
    public ResponseEntity<CreatedKategoriResponse> createKategori(
            @RequestBody CreateKategoriRequest request) {
        // Command oluştur
        CreateKategoriCommand command = new CreateKategoriCommand(request.getKategoriAdi());
        // Mediator aracılığıyla gönder
        CreatedKategoriResponse response = mediator.send(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ListKategoriResponse>> getAllKategoriler() {
        // Query oluştur
        GetAllKategoriQuery query = new GetAllKategoriQuery();
        // Mediator aracılığıyla gönder
        List<ListKategoriResponse> response = mediator.send(query);
        return ResponseEntity.ok(response);
    }
}
```

---

## 5. CQRS Prensipleri

### 5.1 Separation of Concerns
- **Commands**: Veri yazma işlemleri (Create, Update, Delete)
- **Queries**: Veri okuma işlemleri (Read)
- Aynı model iki farklı amaç için kullanılmaz

### 5.2 Scalability
- Read ve Write operasyonları bağımsız olarak scale edilebilir
- Farklı database'ler kullanılabilir (Query DB ve Write DB ayrı)

### 5.3 Performance
- Query'ler optimize edilmiş read model kullanabilir
- Caching stratejileri kolay uygulanabilir

---

## 6. Clean Code & Design Patterns

### 6.1 Naming Conventions
- Command sınıfları: `[Fiil][Entity]Command`
  - Örnek: `CreateKategoriCommand`, `UpdateKitapCommand`
- CommandHandler sınıfları: `[Fiil][Entity]CommandHandler`
  - Örnek: `CreateKategoriCommandHandler`
- Query sınıfları: `[Fiil][Entity]Query` veya `[Entity][Fiil]Query`
  - Örnek: `GetAllKategoriQuery`, `GetKategoriByIdQuery`
- QueryHandler sınıfları: `[Fiil][Entity]QueryHandler`
  - Örnek: `GetAllKategoriQueryHandler`

### 6.2 No Lombok
Tüm Getter, Setter ve Constructor'lar **manuel** yazılmıştır:
- Daha açık ve explicit kod
- IDE refactoring tools'ü daha iyi çalışır
- Debugging sırasında daha kolay takip edilir

### 6.3 Dependency Injection
- Constructor Injection kullanıldı (Spring best practice)
- Final field'ler immutability sağlar
- Testability artırır

---

## 7. Kullanım Örnekleri

### 7.1 Kategori Oluşturma
```bash
POST /api/kategoriler
Content-Type: application/json

{
  "kategoriAdi": "Roman"
}

Response (201 Created):
{
  "kategoriId": 1,
  "kategoriAdi": "Roman"
}
```

### 7.2 Tüm Kategorileri Listeleme
```bash
GET /api/kategoriler

Response (200 OK):
[
  {
    "kategoriId": 1,
    "kategoriAdi": "Roman"
  },
  {
    "kategoriId": 2,
    "kategoriAdi": "Bilim Kurgu"
  }
]
```

### 7.3 Kitap Oluşturma
```bash
POST /api/kitaplar
Content-Type: application/json

{
  "isbn": "978-01",
  "kitapAdi": "1984",
  "yazar": "George Orwell",
  "kategoriId": 2,
  "durum": "Rafta"
}

Response (201 Created):
{
  "kitapId": 1,
  "isbn": "978-01",
  "kitapAdi": "1984",
  "yazar": "George Orwell",
  "kategoriId": 2,
  "durum": "Rafta"
}
```

---

## 8. Genişletme Örnekleri

### 8.1 Yeni Bir Command Eklemek

**Adım 1:** Command sınıfını oluştur
```java
public class UpdateKategoriCommand implements Command<UpdatedKategoriResponse> {
    private Integer kategoriId;
    private String kategoriAdi;
    // Getter/Setter
}
```

**Adım 2:** CommandHandler'ı implement et
```java
@Component
public class UpdateKategoriCommandHandler 
    implements CommandHandler<UpdateKategoriCommand, UpdatedKategoriResponse> {
    
    private final KategoriRepository repository;
    
    @Override
    public UpdatedKategoriResponse handle(UpdateKategoriCommand command) {
        Kategori kategori = repository.findById(command.getKategoriId())
            .orElseThrow(() -> new BusinessException("Kategori bulunamadi"));
        kategori.setKategoriAdi(command.getKategoriAdi());
        Kategori updated = repository.save(kategori);
        return new UpdatedKategoriResponse(updated.getKategoriId(), updated.getKategoriAdi());
    }
}
```

**Adım 3:** Controller'a metot ekle
```java
@PutMapping("/{id}")
public ResponseEntity<UpdatedKategoriResponse> updateKategori(
        @PathVariable Integer id,
        @RequestBody UpdateKategoriRequest request) {
    UpdateKategoriCommand command = new UpdateKategoriCommand(id, request.getKategoriAdi());
    UpdatedKategoriResponse response = mediator.send(command);
    return ResponseEntity.ok(response);
}
```

### 8.2 Yeni Bir Query Eklemek

**Adım 1:** Query sınıfını oluştur
```java
public class GetKategoriByIdQuery implements Query<DetailedKategoriResponse> {
    private Integer kategoriId;
    // Getter/Setter
}
```

**Adım 2:** QueryHandler'ı implement et
```java
@Component
public class GetKategoriByIdQueryHandler 
    implements QueryHandler<GetKategoriByIdQuery, DetailedKategoriResponse> {
    
    private final KategoriRepository repository;
    
    @Override
    public DetailedKategoriResponse handle(GetKategoriByIdQuery query) {
        Kategori kategori = repository.findById(query.getKategoriId())
            .orElseThrow(() -> new BusinessException("Kategori bulunamadi"));
        return new DetailedKategoriResponse(kategori.getKategoriId(), kategori.getKategoriAdi());
    }
}
```

**Adım 3:** Controller'a metot ekle
```java
@GetMapping("/{id}")
public ResponseEntity<DetailedKategoriResponse> getKategoriById(@PathVariable Integer id) {
    GetKategoriByIdQuery query = new GetKategoriByIdQuery(id);
    DetailedKategoriResponse response = mediator.send(query);
    return ResponseEntity.ok(response);
}
```

---

## 9. Avantajlar

✅ **Separation of Concerns**: Read ve Write işlemleri ayrı  
✅ **Testability**: Tamamen bağımsız olarak test edilebilir handler'lar  
✅ **Maintainability**: Her handler'ın tek sorumluluğu var (SRP)  
✅ **Scalability**: İşlemler bağımsız olarak scale edilebilir  
✅ **Flexibility**: Farklı storage mekanizmaları kolayca entegre edilebilir  
✅ **Explicit Code**: Lombok olmadan daha açık ve okunabilir kod  

---

## 10. Gelecek Geliştirmeler

- [ ] Event Sourcing entegrasyonu
- [ ] CQRS database separation (Read DB / Write DB)
- [ ] Async Command/Query processing
- [ ] Caching stratejileri (Redis entegrasyonu)
- [ ] Event Bus ile diğer sistemlerle iletişim
- [ ] Daha kompleks sorgulamalar için Query DTOs
- [ ] Delete operasyonları için Soft Delete pattern
- [ ] Audit logging mekanizması

---

**Dokument Sürümü:** 1.0  
**Son Güncelleme:** 2026-05-03  
**Harita:** Senior Spring Boot & Software Architecture
