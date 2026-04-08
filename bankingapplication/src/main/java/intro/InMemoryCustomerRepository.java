package intro;

public class InMemoryCustomerRepository implements CustomerRepository {
    
    // Veritabanı yerine geçecek RAM üzerindeki listemiz (Örn: Maksimum 100 müşteri)
    private Customer[] customers = new Customer[100];
    private int currentCount = 0; // O an kaç müşteri kayıtlı olduğunu tutar

    @Override
    public void add(Customer customer) {
        // 1. Kural: Aynı TC ile kayıt var mı kontrol et!
        if (findByTc(customer.getTcNo()) != null) {
            System.out.println("Hata: " + customer.getTcNo() + " TC kimlik numarası ile zaten bir kayıt var!");
            return; // Hata varsa metodu burada kes, aşağı inme.
        }

        // 2. Kural: Dizi limiti dolmadıysa müşteriyi diziye ekle
        if (currentCount < customers.length) {
            customers[currentCount] = customer;
            currentCount++;
            System.out.println("Başarılı: " + customer.getName() + " " + customer.getSurname() + " sisteme kaydedildi.");
        } else {
            System.out.println("Hata: Banka müşteri kapasitesi doldu!");
        }
    }

    @Override
    public Customer findByTc(String tcNo) {
        // Dizideki mevcut müşterileri dön ve TC'leri karşılaştır
        for (int i = 0; i < currentCount; i++) {
            // DİKKAT: String (Referans tip) karşılaştırması yaparken == değil .equals() kullanılır! (Hocanın notu)
            if (customers[i].getTcNo().equals(tcNo)) {
                return customers[i]; // Eşleşme bulundu, müşteriyi geri gönder
            }
        }
        return null; // Döngü bitti, eşleşme yoksa boş (null) dön
    }
}