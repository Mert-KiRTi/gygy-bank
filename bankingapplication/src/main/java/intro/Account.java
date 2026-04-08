package intro;

public class Account {
    private String accountNumber;
    private double balance;
    // YENİ: Hesabın birine ait olup olmadığını tutan kilit (Encapsulation)
    private boolean isAssigned = false; 

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
    }

    // YENİ: Hesabı mühürleme metodu
    public void assign() {
        this.isAssigned = true;
    }

    // YENİ: Hesabın durumunu okuma metodu
    public boolean isAssigned() {
        return this.isAssigned;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println(amount + " TL yatırıldı. Yeni bakiye: " + this.balance + " TL");
        } else {
            System.out.println("Hata: Yatırılacak tutar 0'dan büyük olmalıdır!");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && this.balance >= amount) {
            this.balance -= amount;
            System.out.println(amount + " TL çekildi. Kalan bakiye: " + this.balance + " TL");
        } else if (amount <= 0) {
            System.out.println("Hata: Çekilecek tutar 0'dan büyük olmalıdır!");
        } else {
            System.out.println("Hata: Yetersiz bakiye! Mevcut bakiyeniz: " + this.balance + " TL");
        }
    }

    public void displayAccountInfo() {
        System.out.println("----- Hesap Özeti -----");
        System.out.println("Hesap No : " + this.accountNumber);
        System.out.println("Bakiye   : " + this.balance + " TL");
        System.out.println("-----------------------");
    }

    public double getBalance() { return this.balance; }
    public String getAccountNumber() { return this.accountNumber; }
}