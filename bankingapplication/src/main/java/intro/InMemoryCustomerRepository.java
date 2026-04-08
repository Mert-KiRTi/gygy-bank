package intro;

public class InMemoryCustomerRepository implements CustomerRepository {
    
    private Customer[] customers = new Customer[100];
    private int currentCount = 0; 

    @Override
    public boolean add(Customer customer) {
        // 1. Kural: Aynı TC ile kayıt var mı?
        if (findByTc(customer.getTcNo()) != null) {
            System.out.println("Hata: " + customer.getTcNo() + " TC kimlik numarası ile zaten bir kayıt var!");
            return false; // Başarısız oldu
        }

        // 2. Kural: Kapasite kontrolü
        if (currentCount < customers.length) {
            customers[currentCount] = customer;
            currentCount++;
            return true; // Başarıyla eklendi
        } else {
            System.out.println("Hata: Banka müşteri kapasitesi doldu!");
            return false; // Başarısız oldu
        }
    }

    @Override
    public Customer findByTc(String tcNo) {
        for (int i = 0; i < currentCount; i++) {
            if (customers[i].getTcNo().equals(tcNo)) {
                return customers[i];
            }
        }
        return null;
    }
}