package intro;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Bankacılık Simülasyonu Başlıyor ---\n");

        // 1. Veritabanımızı (Repository) ayağa kaldıralım (Polymorphism örneği)
        // Sol taraf kural, sağ taraf o kuralı uygulayan somut teknoloji.
        CustomerRepository repository = new InMemoryCustomerRepository();

        // 2. Müşteri ve Hesaplarını oluşturalım
        Account account1 = new Account("TR1001");
        Customer customer1 = new Customer("12345678901", "Ahmet", "Yılmaz", account1);

        Account account2 = new Account("TR1002");
        Customer customer2 = new Customer("10987654321", "Mehmet", "Kaya", account2);

        // 3. Müşterileri sisteme (veritabanına) kaydedelim
        System.out.println("-- Müşteri Kayıt İşlemleri --");
        repository.add(customer1);
        repository.add(customer2);

        // 4. TC Kontrolünü test edelim (Aynı TC ile Ahmet'i tekrar eklemeye çalışalım)
        System.out.println("\n-- TC Kimlik Kontrolü Testi --");
        Customer fakeCustomer = new Customer("12345678901", "Ahmet", "Kopya", new Account("TR9999"));
        repository.add(fakeCustomer);

        // 5. Bakiye ve Hesap İşlemleri Testi
        System.out.println("\n-- Hesap İşlemleri Testi --");
        Customer foundCustomer = repository.findByTc("12345678901"); // Ahmet'i TC'sinden bul
        
        if (foundCustomer != null) {
            System.out.println("İşlem Yapılan Müşteri: " + foundCustomer.getName());
            
            // Müşterinin hesabını (referansını) alalım
            Account activeAccount = foundCustomer.getAccount();
            
            activeAccount.deposit(1000);   // 1000 TL yatır
            activeAccount.withdraw(300);   // 300 TL çek
            activeAccount.withdraw(5000);  // Yetersiz bakiye hatası vermeli! (Kapsülleme güvenliği)
            
            // Bakiye sorgulama
            System.out.println("Güncel Bakiye Sorgusu: " + activeAccount.getBalance() + " TL");
        }
    }
}