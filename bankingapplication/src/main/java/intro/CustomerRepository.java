package intro;

public interface CustomerRepository {
    // YENİ EKLENEN ÖZELLİK: void yerine boolean döndürüyoruz
    boolean add(Customer customer);
    
    Customer findByTc(String tcNo);
}