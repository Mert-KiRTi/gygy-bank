package intro;

public class Customer {
    private String tcNo;
    private String name;
    private String surname;
    private Account account;

    public Customer(String tcNo, String name, String surname, Account account) {
        // 11 Hane Kontrolü
        if (tcNo != null && tcNo.length() == 11) {
            this.tcNo = tcNo;
        } else {
            System.out.println("HATA: TC Kimlik No tam 11 haneli olmalıdır! (" + name + " için geçici TC atanıyor...)");
            this.tcNo = "00000000000"; 
        }
        
        this.name = name;
        this.surname = surname;
        
        // YENİ: Hesap Atama Kontrolü (Güvenlik İhlalini Önleme)
        if (!account.isAssigned()) {
            this.account = account;
            this.account.assign(); // Hesabı mühürle! Artık başkası alamaz.
        } else {
            System.out.println("GÜVENLİK İHLALİ: " + account.getAccountNumber() + " numaralı hesap zaten başka bir müşteriye tanımlı!");
            System.out.println("SİSTEM ÇÖZÜMÜ: " + name + " için yeni bir bağımsız hesap oluşturuluyor...");
            
            // Aynı hesabı vermek yerine yeni ve temiz bir hesap oluşturup onu veriyoruz.
            this.account = new Account(this.tcNo + "-OTO"); 
            this.account.assign(); // Yeni hesabı da mühürle
        }
    }

    public String getTcNo() { return tcNo; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public Account getAccount() { return account; }
}