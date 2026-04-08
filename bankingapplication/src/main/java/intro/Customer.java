package intro;

public class Customer {
    private String tcNo;
    private String name;
    private String surname;
    
    // Müşterinin bir hesabı var (Kompozisyon/Composition)
    private Account account;

    public Customer(String tcNo, String name, String surname, Account account) {
        this.tcNo = tcNo;
        this.name = name;
        this.surname = surname;
        this.account = account;
    }

    // Getter metotları (Dışarıdan okumak için)
    public String getTcNo() {
        return tcNo;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Account getAccount() {
        return account;
    }
}