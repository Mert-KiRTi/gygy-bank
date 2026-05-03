CREATE DATABASE kutuphane_db;

CREATE TABLE kategori(
	kategori_id SERIAL PRIMARY KEY,
	kategori_adi VARCHAR(100) NOT NULL
);

CREATE TABLE ogrenci(
	ogrenci_id SERIAL PRIMARY KEY,
	okul_no VARCHAR(20) UNIQUE NOT NULL,
	ad_soyad VARCHAR(100) NOT NULL,
	email VARCHAR(100) UNIQUE,
	telefon VARCHAR(15)
);

CREATE TABLE gorevli(
	gorevli_id SERIAL PRIMARY KEY,
	ad_soyad VARCHAR(100) NOT NULL,
	rol VARCHAR(50) NOT NULL
);

CREATE TABLE kitap(
	kitap_id SERIAL PRIMARY KEY,
	isbn VARCHAR(20) UNIQUE NOT NULL,
	kitap_adi VARCHAR(200) NOT NULL,
	yazar VARCHAR(100) NOT NULL,
	kategori_id INTEGER NOT NULL,
	durum VARCHAR(50) DEFAULT 'Rafta',
	
	-- FOREIGN KEY (bu_tablo_FK) REFERENCES (diğer_tablo)(diğer_tablo_PK)
	FOREIGN KEY (kategori_id) REFERENCES kategori(kategori_id)
);

CREATE TABLE odunc_alma(
	odunc_id SERIAL PRIMARY KEY,
	kitap_id INTEGER NOT NULL,
	ogrenci_id INTEGER NOT NULL,
	veren_gorevli_id INTEGER NOT NULL,
	verilis_tarihi DATE DEFAULT CURRENT_DATE,
	planlanan_iade_tarihi DATE NOT NULL,
	durum VARCHAR(50) DEFAULT 'Aktif',
	
	FOREIGN KEY (kitap_id) REFERENCES kitap(kitap_id),
	FOREIGN KEY (ogrenci_id) REFERENCES ogrenci(ogrenci_id),
	FOREIGN KEY (veren_gorevli_id) REFERENCES gorevli(gorevli_id)
);

CREATE TABLE iade(
	iade_id SERIAL PRIMARY KEY,
	odunc_id INTEGER UNIQUE NOT NULL, -- Bir ödünç işleminin sadece 1 iadesi olur
	alan_gorevli_id INTEGER NOT NULL,
	gercek_iade_tarihi DATE DEFAULT CURRENT_DATE,
	kitap_kondisyonu VARCHAR(50) DEFAULT 'İyi',
	
	FOREIGN KEY (odunc_id) REFERENCES odunc_alma(odunc_id),
	FOREIGN KEY (alan_gorevli_id) REFERENCES gorevli(gorevli_id)
);

CREATE TABLE ceza(
	ceza_id SERIAL PRIMARY KEY,
	odunc_id INTEGER NOT NULL,
	ogrenci_id INTEGER NOT NULL,
	ceza_tutari DECIMAL(10,2) NOT NULL,
	ceza_nedeni VARCHAR(150),
	odendi_mi BOOLEAN DEFAULT FALSE,
	
	FOREIGN KEY (odunc_id) REFERENCES odunc_alma(odunc_id),
	FOREIGN KEY (ogrenci_id) REFERENCES ogrenci(ogrenci_id)
);


-- ==========================================
-- DML (Data Manipulation Language)
-- Veri Ekleme, Güncelleme, Silme ve Seçme
-- ==========================================

-- ------------------------------------------
-- 1. INSERT (Veri Ekleme - Her Tabloya Min 5 Veri)
-- ------------------------------------------

INSERT INTO kategori (kategori_adi) VALUES 
('Roman'), 
('Bilim Kurgu'), 
('Tarih'), 
('Felsefe'), 
('Yazılım/Teknoloji');

INSERT INTO ogrenci (okul_no, ad_soyad, email, telefon) VALUES 
('101', 'Halit Kalaycı', 'halit@mail.com', '+905551112233'),
('102', 'Ahmet Yılmaz', 'ahmet@mail.com', '+905552223344'),
('103', 'Ayşe Demir', 'ayse@mail.com', '+905553334455'),
('104', 'Fatma Şahin', 'fatma@mail.com', '+905554445566'),
('105', 'Mehmet Kaya', 'mehmet@mail.com', '+905555556677');

INSERT INTO gorevli (ad_soyad, rol) VALUES 
('Ali Veli', 'Kütüphaneci'),
('Zeynep Çelik', 'Uzman Kütüphaneci'),
('Hasan Can', 'Arşiv Sorumlusu'),
('Elif Yıldız', 'Kütüphane Müdürü'),
('Burak Kaplan', 'Gece Sorumlusu');

INSERT INTO kitap (isbn, kitap_adi, yazar, kategori_id, durum) VALUES 
('978-01', '1984', 'George Orwell', 2, 'Rafta'),
('978-02', 'Sefiller', 'Victor Hugo', 1, 'Ödünçte'),
('978-03', 'Nutuk', 'Mustafa Kemal Atatürk', 3, 'Ödünçte'),
('978-04', 'Devlet', 'Platon', 4, 'Rafta'),
('978-05', 'Clean Code', 'Robert C. Martin', 5, 'Rafta');

INSERT INTO odunc_alma (kitap_id, ogrenci_id, veren_gorevli_id, verilis_tarihi, planlanan_iade_tarihi, durum) VALUES 
(1, 1, 1, '2023-10-01', '2023-10-15', 'İade Edildi'),
(2, 2, 2, '2023-10-05', '2023-10-20', 'Aktif'),
(3, 3, 1, '2023-10-10', '2023-10-25', 'Aktif'),
(4, 4, 3, '2023-10-12', '2023-10-27', 'İade Edildi'),
(5, 5, 1, '2023-10-15', '2023-10-30', 'Gecikti');

INSERT INTO iade (odunc_id, alan_gorevli_id, gercek_iade_tarihi, kitap_kondisyonu) VALUES 
(1, 1, '2023-10-14', 'İyi'),
(4, 3, '2023-10-26', 'Yıpranmış'),
(5, 4, '2023-11-05', 'Hasarlı'),
(2, 2, '2023-10-18', 'İyi'),
(3, 1, '2023-10-20', 'İyi');

INSERT INTO ceza (odunc_id, ogrenci_id, ceza_tutari, ceza_nedeni, odendi_mi) VALUES 
(5, 5, 50.00, '6 Gün Gecikme', TRUE),
(5, 5, 150.00, 'Kitap Hasarı (Yırtık Sayfa)', FALSE),
(4, 4, 30.00, 'Hafif Yıpranma', FALSE),
(1, 1, 10.00, 'Kapak Çizilmesi', TRUE),
(2, 2, 20.00, 'Kayıp Barkod', TRUE);


-- ------------------------------------------
-- 2. UPDATE (Veri Güncelleme)
-- ------------------------------------------

-- Öğrencinin telefon numarasını güncelle
UPDATE ogrenci SET telefon = '+905310000000' WHERE ogrenci_id = 1;

-- Kitabın durumunu güncelle
UPDATE kitap SET durum = 'Ödünçte' WHERE kitap_id = 4;

-- Cezanın ödendiğini sisteme işle
UPDATE ceza SET odendi_mi = TRUE WHERE ceza_id = 3;


-- ------------------------------------------
-- 3. DELETE (Veri Silme)
-- ------------------------------------------

-- Hata verir çünkü ogrenci_id 5'e bağlı odunc_alma ve ceza kayıtları var
DELETE FROM ogrenci WHERE ogrenci_id = 5;   

-- Cezası ödenmiş ve gereksiz bir ceza kaydını silmek (Bağımsız veri)
DELETE FROM ceza WHERE ceza_id = 5;


-- ------------------------------------------
-- 4. SELECT (Veri Okuma ve Filtreleme)
-- ------------------------------------------

-- Tüm kitapları listele
SELECT * FROM kitap;

-- Belirli sütunları listele
SELECT ad_soyad, email FROM ogrenci;

-- Durumu 'Rafta' olan kitapları bul
SELECT * FROM kitap WHERE durum = 'Rafta';

-- Asc -> Ascending (Küçükten büyüğe, A-Z)
-- Desc -> Descending (Büyükten küçüğe, Z-A)
SELECT * FROM ogrenci ORDER BY ad_soyad ASC;
SELECT * FROM ceza ORDER BY ceza_tutari DESC;


-- ------------------------------------------
-- 5. AGGREGATE FUNC. (İstatistiksel Fonksiyonlar)
-- ------------------------------------------

-- Tablodaki toplam kitap sayısını bul
SELECT COUNT(*) FROM kitap;

-- Kesilen en düşük ceza tutarı
SELECT MIN(ceza_tutari) FROM ceza;

-- Kesilen en yüksek ceza tutarı
SELECT MAX(ceza_tutari) FROM ceza;

-- Kesilen cezaların ortalaması
SELECT AVG(ceza_tutari) FROM ceza;

-- Toplam kesilen ceza miktarı
SELECT SUM(ceza_tutari) FROM ceza;


-- ------------------------------------------
-- 6. LIKE / ILIKE (Metin Arama Sembolleri)
-- ------------------------------------------

-- Adı 'A' ile başlayan öğrencileri getir (Büyük harf A)
SELECT * FROM ogrenci WHERE ad_soyad LIKE 'A%';

-- İçinde 'code' geçen kitapları getir (Büyük/Küçük harf duyarsız - ILIKE)
SELECT * FROM kitap WHERE kitap_adi ILIKE '%code%';

-- İsminin sondan bir önceki harfi 'm' olan görevlileri getir
SELECT * FROM gorevli WHERE ad_soyad LIKE '%m_';

-- ------------------------------------------