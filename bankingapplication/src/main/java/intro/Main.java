package intro;

public class Main {
    public static void main(String[] args) {
        System.out.println("====== GYGY BANK SİMÜLASYONU BAŞLIYOR ======\n");

        // 1. Veritabanımızı (Repository) ayağa kaldırıyoruz (Polymorphism)
        CustomerRepository repository = new InMemoryCustomerRepository();

        System.out.println("--- 1. TC KİMLİK UZUNLUK KONTROLÜ TESTİ ---");
        Account account1 = new Account("TR-1001");
        // TC bilerek 5 haneli giriliyor. Kapsülleme bunu yakalayıp "00000000000" yapmalı.
        Customer musteri1 = new Customer("12345", "Ali", "Veli", account1);


        System.out.println("\n--- 2. HESAP GÜVENLİĞİ (ÇALINMA ÖNLEME) TESTİ ---");
        // Sadece 1 tane ortak hesap nesnesi üretiyoruz
        Account ortakHesap = new Account("TR-ORTAK");
        
        // Ahmet bu hesabı ilk alan kişi olacak ve hesap mühürlenecek
        Customer ahmet = new Customer("11111111111", "Ahmet", "Yılmaz", ortakHesap);
        
        // Mehmet AYNI hesabı almaya çalışacak. Sistem engelleyip ona OTO (yeni) hesap vermeli
        Customer mehmet = new Customer("22222222222", "Mehmet", "Kaya", ortakHesap);


        System.out.println("\n--- 3. MÜŞTERİ KAYIT (REPOSITORY) İŞLEMLERİ ---");
        // Müşterileri veritabanına ekliyoruz ve boolean (true/false) dönüşlerine göre mesaj veriyoruz
        if (repository.add(musteri1)) System.out.println("Başarılı: " + musteri1.getName() + " sisteme eklendi.");
        if (repository.add(ahmet)) System.out.println("Başarılı: " + ahmet.getName() + " sisteme eklendi.");
        if (repository.add(mehmet)) System.out.println("Başarılı: " + mehmet.getName() + " sisteme eklendi.");


        System.out.println("\n--- 4. ÇİFT TC KİMLİK (KOPYA MÜŞTERİ) TESTİ ---");
        // Ahmet'in TC'si (11111111111) ile sisteme başka birini eklemeye çalışalım
        Account fakeAccount = new Account("TR-9999");
        Customer hacker = new Customer("11111111111", "Hacker", "Okan", fakeAccount);
        
        boolean isHackerAdded = repository.add(hacker);
        if (!isHackerAdded) {
            System.out.println("SİSTEM MESAJI: Şüpheli işlem engellendi! Bu TC numarası zaten kullanımda.");
        }


        System.out.println("\n--- 5. BANKACILIK İŞLEMLERİ VE HESAP ÖZETİ ---");
        // Veritabanından Ahmet'i TC'si ile bulalım
        Customer foundCustomer = repository.findByTc("11111111111");
        
        if (foundCustomer != null) {
            System.out.println("Müşteri Bulundu: " + foundCustomer.getName() + " " + foundCustomer.getSurname());
            Account activeAccount = foundCustomer.getAccount();
            
            System.out.println("\nİşlemler başlıyor...");
            activeAccount.deposit(2000);   // 2000 TL yatır
            activeAccount.withdraw(500);   // 500 TL çek
            activeAccount.withdraw(5000);  // Yetersiz bakiye testi (5000 TL çekmeye çalış - Hata vermeli!)
            
            System.out.println();
            // Ahmet'in son durumunu fiş gibi yazdır
            activeAccount.displayAccountInfo(); 
        }


        System.out.println("\n--- 6. MEHMET'İN HESAP DURUMU (GÜVENLİK SAĞLAMASI) ---");
        // Ortak hesabı çalmaya çalışan Mehmet'in hesabının Ahmet'inkiyle karışmadığını kanıtlayalım
        Customer foundMehmet = repository.findByTc("22222222222");
        if (foundMehmet != null) {
            System.out.println("Mehmet'in atanan yeni bağımsız hesabı kontrol ediliyor:");
            // Mehmet'in bakiyesi 0 olmalı ve hesap numarası TR-ORTAK olmamalı
            foundMehmet.getAccount().displayAccountInfo();
        }

        System.out.println("\n====== SİMÜLASYON BİTTİ ======");
    }
}