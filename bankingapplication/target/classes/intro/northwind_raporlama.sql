-- Northwind İstatistik ve Raporlama Sorguları --

-- -------------------------------------------------------------------------
-- 1. Toplam cirosu 50.000$'dan büyük olan VIP müşteriler
-- (Müşteri, Sipariş ve Sipariş Detayları tabloları birleşir)
-- -------------------------------------------------------------------------
SELECT c.customer_id, c.company_name, SUM(od.unit_price * od.quantity) AS total_revenue
FROM customers c
INNER JOIN orders o ON c.customer_id = o.customer_id
INNER JOIN order_details od ON o.order_id = od.order_id
GROUP BY c.customer_id, c.company_name
-- WHERE ile Aggregate (SUM, COUNT) filtrelenemez, o yüzden HAVING kullanıyoruz!
HAVING SUM(od.unit_price * od.quantity) > 50000
ORDER BY total_revenue DESC;


-- -------------------------------------------------------------------------
-- 2. Her kategori için en az 5 farklı ürün satan kategoriler
-- (Hangi kategoride ne kadar çeşit ürünümüz var?)
-- -------------------------------------------------------------------------
SELECT c.category_name, COUNT(p.product_id) AS urun_cesidi_sayisi
FROM categories c
INNER JOIN products p ON c.category_id = p.category_id
GROUP BY c.category_id, c.category_name
-- Sadece 5 ve daha fazla ürüne sahip olan kategorileri filtrele
HAVING COUNT(p.product_id) >= 5
ORDER BY urun_cesidi_sayisi DESC;


-- -------------------------------------------------------------------------
-- 3. Çalışan bazlı toplam satış tutarı 
-- (Hangi personelimiz şirkete toplam ne kadar kazandırmış?)
-- -------------------------------------------------------------------------
SELECT e.employee_id, e.first_name, e.last_name, SUM(od.unit_price * od.quantity) AS total_sales
FROM employees e
INNER JOIN orders o ON e.employee_id = o.employee_id
INNER JOIN order_details od ON o.order_id = od.order_id
GROUP BY e.employee_id, e.first_name, e.last_name
ORDER BY total_sales DESC;
