# Data Flow & Implementation Guide

## Request Flow Diagram

### For WRITE Operations (Commands)

```
┌─────────────────────────────────────────────────────────────────────┐
│ Client Request (HTTP POST)                                          │
│ POST /api/kategoriler                                              │
│ { "kategoriAdi": "Roman" }                                         │
└────────────────────────┬────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────────┐
│ KategorilerController.createKategori()                             │
│ - Request'i CreateKategoriRequest DTO'ya dönüştür                 │
│ - CreateKategoriCommand oluştur                                   │
│ - mediator.send(command) çağır                                    │
└────────────────────────┬────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────────┐
│ SpringMediator.send(Command)                                       │
│ - Command tipi: CreateKategoriCommand                             │
│ - Handler adı generate et: createKategoriHandler                  │
│ - ApplicationContext'ten handler bean'i al                        │
│ - handler.handle(command) çağır                                   │
└────────────────────────┬────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────────┐
│ CreateKategoriCommandHandler.handle(CreateKategoriCommand)        │
│ - Yeni Kategori entity'si oluştur                                 │
│ - kategoriRepository.save(kategori) ile DB'ye kaydet             │
│ - Saved entity'den CreatedKategoriResponse DTO oluştur          │
│ - CreatedKategoriResponse return et                               │
└────────────────────────┬────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────────┐
│ Controller Response (HTTP 201)                                     │
│ { "kategoriId": 1, "kategoriAdi": "Roman" }                      │
│ Status: CREATED                                                    │
└─────────────────────────────────────────────────────────────────────┘
```

### For READ Operations (Queries)

```
┌─────────────────────────────────────────────────────────────────────┐
│ Client Request (HTTP GET)                                           │
│ GET /api/kategoriler                                               │
└────────────────────────┬────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────────┐
│ KategorilerController.getAllKategoriler()                          │
│ - GetAllKategoriQuery oluştur                                     │
│ - mediator.send(query) çağır                                      │
└────────────────────────┬────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────────┐
│ SpringMediator.send(Query)                                         │
│ - Query tipi: GetAllKategoriQuery                                 │
│ - Handler adı generate et: getAllKategoriHandler                  │
│ - ApplicationContext'ten handler bean'i al                        │
│ - handler.handle(query) çağır                                    │
└────────────────────────┬────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────────┐
│ GetAllKategoriQueryHandler.handle(GetAllKategoriQuery)            │
│ - kategoriRepository.findAll() ile tüm kategorileri al           │
│ - Stream ile her Kategori entity'sini ListKategoriResponse'e  │
│   dönüştür                                                        │
│ - List<ListKategoriResponse> return et                            │
└────────────────────────┬────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────────┐
│ Controller Response (HTTP 200)                                     │
│ [                                                                   │
│   { "kategoriId": 1, "kategoriAdi": "Roman" },                   │
│   { "kategoriId": 2, "kategoriAdi": "Bilim Kurgu" }              │
│ ]                                                                   │
└─────────────────────────────────────────────────────────────────────┘
```

---

## Class Relationships

### Command Processing Chain

```
        ┌─────────────────┐
        │    Client       │
        └────────┬────────┘
                 │
                 ▼
        ┌─────────────────────────────────┐
        │  KategorilerController          │
        │  └─ Mediator (inject)           │
        └────────┬────────────────────────┘
                 │
        ┌────────────────────────┐
        │  mediator.send(cmd)    │
        ▼                        ▼
   ┌──────────────────┐   ┌──────────────────┐
   │ CreateKategoriCommand
   │ (implements Command<R>)
   └─────────┬────────┘
             │
        ┌────┴─────────────────────────────┐
        │ SpringMediator                   │
        │ ├─ findCommandHandler()          │
        │ ├─ ApplicationContext            │
        │ └─ handler.handle(command)       │
        └────┬─────────────────────────────┘
             │
             ▼
   ┌─────────────────────────────────┐
   │CreateKategoriCommandHandler     │
   │implements CommandHandler<C, R>  │
   │ └─ kategoriRepository           │
   │ └─ handle(command): Response    │
   └────────┬────────────────────────┘
            │
            ▼
   ┌─────────────────────────────────┐
   │ CreatedKategoriResponse DTO     │
   │ { id, name }                    │
   └─────────────────────────────────┘
```

### Query Processing Chain

```
        ┌─────────────────┐
        │    Client       │
        └────────┬────────┘
                 │
                 ▼
        ┌─────────────────────────────────┐
        │  KategorilerController          │
        │  └─ Mediator (inject)           │
        └────────┬────────────────────────┘
                 │
        ┌────────────────────────┐
        │  mediator.send(query)  │
        ▼
   ┌──────────────────┐
   │ GetAllKategoriQuery
   │ (implements Query<R>)
   └─────────┬────────┘
             │
        ┌────┴─────────────────────────────┐
        │ SpringMediator                   │
        │ ├─ findQueryHandler()            │
        │ ├─ ApplicationContext            │
        │ └─ handler.handle(query)         │
        └────┬─────────────────────────────┘
             │
             ▼
   ┌─────────────────────────────────┐
   │GetAllKategoriQueryHandler       │
   │implements QueryHandler<Q, R>    │
   │ └─ kategoriRepository           │
   │ └─ handle(query): List<Response>│
   └────────┬────────────────────────┘
            │
            ▼
   ┌─────────────────────────────────┐
   │ List<ListKategoriResponse>      │
   │ [{ id, name }, { id, name }]    │
   └─────────────────────────────────┘
```

---

## Handler Discovery Algorithm

### SpringMediator Handler Bulma Mekanizması

```java
// Command Handler Bulma:
String commandClassName = command.getClass().getSimpleName();
// "CreateKategoriCommand" -> "createKategoriHandler"

String handlerBeanName = commandClassName.replaceAll("Command$", "");
String withHandler = handlerBeanName + "Handler";
String beanName = withHandler.substring(0,1).toLowerCase() + withHandler.substring(1);
// Result: "createKategoriHandler"

// 1. ApplicationContext'te bean'i ara (exact name match)
if (applicationContext.containsBean(beanName)) {
    return applicationContext.getBean(beanName);
}

// 2. Tüm CommandHandler bean'lerini ara (type match)
Map<String, CommandHandler> handlers = applicationContext.getBeansOfType(CommandHandler.class);
for (CommandHandler handler : handlers.values()) {
    if (isHandlerForCommand(handler, command)) {
        return handler;
    }
}

// 3. Handler bulunmazsa exception fırlat
throw new IllegalStateException("No CommandHandler found for: " + commandClassName);
```

---

## Data Transfer Objects (DTOs)

### Request DTOs

```java
// CreateKategoriRequest
{
  "kategoriAdi": "String"
}

// CreateKitapRequest
{
  "isbn": "String",
  "kitapAdi": "String",
  "yazar": "String",
  "kategoriId": "Integer",
  "durum": "String"
}
```

### Response DTOs

```java
// CreatedKategoriResponse
{
  "kategoriId": "Integer",
  "kategoriAdi": "String"
}

// ListKategoriResponse
{
  "kategoriId": "Integer",
  "kategoriAdi": "String"
}

// CreatedKitapResponse
{
  "kitapId": "Integer",
  "isbn": "String",
  "kitapAdi": "String",
  "yazar": "String",
  "kategoriId": "Integer",
  "durum": "String"
}

// ListKitapResponse
{
  "kitapId": "Integer",
  "isbn": "String",
  "kitapAdi": "String",
  "yazar": "String",
  "kategoriId": "Integer",
  "kategoriAdi": "String",
  "durum": "String"
}
```

---

## Error Handling Flow

```
┌─────────────────────────────────────────────────────────┐
│ Controller yada Handler'da Exception oluştu             │
└────────────────┬────────────────────────────────────────┘
                 │
                 ▼
        ┌────────────────────────────────────┐
        │ GlobalExceptionHandler             │
        │ @RestControllerAdvice              │
        │ @ExceptionHandler(...)             │
        └────────┬───────────────────────────┘
                 │
    ┌────────────┼────────────┬────────────────┐
    │            │            │                │
    ▼            ▼            ▼                ▼
┌────────┐ ┌──────────┐ ┌──────────────────┐ ┌──────┐
│Business│ │Kitap     │ │MethodArgument    │ │General│
│Exception│ │Bulunamadi│ │NotValidException │ │Error │
└────┬───┘ └────┬─────┘ └────────┬─────────┘ └──┬───┘
     │          │                 │             │
     ▼          ▼                 ▼             ▼
   HTTP 400   HTTP 400        HTTP 400      HTTP 500
   ErrorResp  ErrorResp       ValidationErr  ErrorResp
```

---

## Database Integration

### JPA Repository Pattern

```java
// KategoriRepository
public interface KategoriRepository extends JpaRepository<Kategori, Integer> {
    // findById(), findAll(), save(), delete(), etc.
    // Spring Data JPA otomatik olarak implementation sağlar
}

// KitapRepository
public interface KitapRepository extends JpaRepository<Kitap, Integer> {
    // findById(), findAll(), save(), delete(), etc.
}
```

### Entity Relationships

```
┌──────────────────┐         ┌──────────────────┐
│    Kategori      │ 1───────│      Kitap       │
│  (PK: id)        │    *    │  (FK: kategoriId)│
│  - kategoriId    │         │  (PK: id)        │
│  - kategoriAdi   │         │  - kitapId       │
└──────────────────┘         │  - isbn          │
                             │  - kitapAdi      │
                             │  - yazar         │
                             │  - kategori      │
                             │  - durum         │
                             └──────────────────┘
```

---

## Spring Configuration

### Otomatik Bean Registration

Tüm Handler'lar `@Component` anotasyonu ile işaretlenmiş, Spring'in classpath scanning'i tarafından otomatik olarak bulunur:

```java
// Her CommandHandler
@Component
public class CreateKategoriCommandHandler 
    implements CommandHandler<CreateKategoriCommand, CreatedKategoriResponse> {
    // ...
}

// Her QueryHandler
@Component
public class GetAllKategoriQueryHandler 
    implements QueryHandler<GetAllKategoriQuery, List<ListKategoriResponse>> {
    // ...
}
```

Spring otomatik olarak:
1. Bu sınıfları bulur
2. Bean instance'ları oluşturur
3. ApplicationContext'e kaydeder
4. Constructor injection'ı çözer (KategoriRepository, KitapRepository, vb.)

---

## Testing Strategy

### Unit Test Örneği (CommandHandler)

```java
@ExtendWith(MockitoExtension.class)
public class CreateKategoriCommandHandlerTest {
    
    @Mock
    private KategoriRepository repository;
    
    private CreateKategoriCommandHandler handler;
    
    @BeforeEach
    void setUp() {
        handler = new CreateKategoriCommandHandler(repository);
    }
    
    @Test
    void shouldCreateKategori() {
        // Arrange
        CreateKategoriCommand command = new CreateKategoriCommand("Roman");
        Kategori savedKategori = new Kategori("Roman");
        savedKategori.setKategoriId(1);
        when(repository.save(any(Kategori.class))).thenReturn(savedKategori);
        
        // Act
        CreatedKategoriResponse response = handler.handle(command);
        
        // Assert
        assertThat(response.getKategoriId()).isEqualTo(1);
        assertThat(response.getKategoriAdi()).isEqualTo("Roman");
        verify(repository, times(1)).save(any(Kategori.class));
    }
}
```

### Integration Test Örneği (Controller)

```java
@SpringBootTest
@AutoConfigureMockMvc
public class KategorilerControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldCreateKategoriBE() throws Exception {
        mockMvc.perform(post("/api/kategoriler")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"kategoriAdi\": \"Roman\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.kategoriId").exists())
            .andExpect(jsonPath("$.kategoriAdi").value("Roman"));
    }
    
    @Test
    void shouldGetAllKategoriler() throws Exception {
        mockMvc.perform(get("/api/kategoriler"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }
}
```

---

## Performance Considerations

### Caching Stratejisi (İleride uygulanabilir)

```java
@Component
public class GetAllKategoriQueryHandler 
    implements QueryHandler<GetAllKategoriQuery, List<ListKategoriResponse>> {
    
    @Cacheable(value = "kategoriler")
    @Override
    public List<ListKategoriResponse> handle(GetAllKategoriQuery query) {
        // Cache'den döner, yoksa çalışır ve cache'ler
        return kategoriRepository.findAll()
            .stream()
            .map(this::mapToListResponse)
            .collect(Collectors.toList());
    }
}

// Cache invalidation
@Component
public class CreateKategoriCommandHandler 
    implements CommandHandler<CreateKategoriCommand, CreatedKategoriResponse> {
    
    @CacheEvict(value = "kategoriler", allEntries = true)
    @Override
    public CreatedKategoriResponse handle(CreateKategoriCommand command) {
        // Command çalıştı ve cache temizlendi
        return response;
    }
}
```

---

## Deployment Checklist

- [ ] Tüm entity package'ları düzeltildi
- [ ] Tüm repository'ler implement edildi
- [ ] Tüm Command/CommandHandler çiftleri oluşturuldu
- [ ] Tüm Query/QueryHandler çiftleri oluşturuldu
- [ ] DTOs doğru bir şekilde map edildi
- [ ] Controller'lar sadece Mediator'ı inject alıyor
- [ ] Exception handling doğru bir şekilde yapılıyor
- [ ] Tests yazılmış ve geçmiş
- [ ] Documentation tamamlanmış

---

**Sürüm:** 1.0  
**Son Güncelleme:** 2026-05-03
