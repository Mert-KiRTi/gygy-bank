package intro;

// Sistemimize bağlanacak her veritabanı (RAM, MySQL, PostgreSQL) bu kurallara uymak zorunda.
public interface CustomerRepository {
    // Müşteri ekleme kuralı
    void add(Customer customer);
    
    // TC Kimlik no ile müşteri bulma kuralı
    Customer findByTc(String tcNo);
}