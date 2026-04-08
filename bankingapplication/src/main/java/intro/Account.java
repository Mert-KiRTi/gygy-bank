package intro;

public class Account {
    private String accountNumber;
    private double balance;

    // Constructor (Yapıcı Metot) - Hesap açılırken sıfır bakiye ile başlar
    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
    }

    // Para Yatırma
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println(amount + " TL yatırıldı. Yeni bakiye: " + this.balance + " TL");
        } else {
            System.out.println("Hata: Yatırılacak tutar 0'dan büyük olmalıdır!");
        }
    }

    // Para Çekme
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

    // Bakiye Sorgulama (Senin istediğin özellik)
    public double getBalance() {
        return this.balance;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }
}