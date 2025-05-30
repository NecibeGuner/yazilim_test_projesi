# yazilim\_test\_projesi

Bu proje, **Java 19**, **Maven**, **JUnit 4** ve **Rest Assured 5.3.1** kullanarak JSONPlaceholder ([https://jsonplaceholder.typicode.com](https://jsonplaceholder.typicode.com)) servisi üzerinde otomatik API testleri gerçekleştirmek için hazırlanmıştır. Proje temel olarak hem `GET` hem de `POST` isteklerini test eder, statü kodu, response body doğrulamaları ve süre (1 saniyenin altında) kontrollerini içerir.

---

## Önkoşullar

* Java 19 yüklü olmalı (JDK 19)
* Apache Maven kurulumu ve `mvn` komutu sistem PATH’inde olmalı
* İnternet bağlantısı (JSONPlaceholder servisine erişim için)

---

## Kurulum ve Çalıştırma

1. GitHub’dan repoyu klonlayın:

   ```bash
   git clone https://github.com/NecibeGuner/yazilim_test_projesi.git
   cd yazilim_test_projesi
   ```

2. Bağımlılıkları indirip projeyi derleyin:

   ```bash
   mvn clean install
   ```

3. Testleri çalıştırın:

   ```bash
   mvn test
   ```

4. Test raporları `target/surefire-reports` klasöründe bulunur.

---

## Test Senaryoları

### 1. GET /posts/1

* Statü kodu `200` beklenir.
* `userId`, `id` alanlarının beklenen değerlere eşit olduğu kontrol edilir.
* `title` alanının boş olmadığı (`notNullValue()`) doğrulanır.
* Cevap süresi 1 saniyenin altında olmalıdır.

### 2. POST /posts

* Statü kodu `201` beklenir.
* Gönderilen `title`, `body` ve `userId` değerlerinin response içinde doğru döndüğü kontrol edilir.
* Response’da `id` alanının dolu olduğu (`notNullValue()`) doğrulanır.
* Cevap süresi 1 saniyenin altında olmalıdır.

---

## Proje Yapısı

```text
├── .vscode/                # VS Code ayarları
├── src/
│   ├── main/java/
│   │   └── org/example/Main.java        # Örnek "Hello and welcome!" sınıfı
│   └── test/java/
│       └── com/necibe/tests/JsonPlaceholderApiTest.java
├── pom.xml                   # Maven yapılandırması
└── target/                   # Derleme çıktıları (.gitignore'a eklenmeli)
```

---

## İyileştirme Önerileri

* `target/` klasörünü `.gitignore` dosyasına ekleyerek derleme çıktılarının repoya dahil edilmesini engelleyin.
* README dosyasına ek olarak proje sunum dosyası (PowerPoint veya PDF) ekleyin.
* Testleri **JUnit 5** ve parametreli testlerle genişletin.
* CI/CD entegrasyonu için GitHub Actions yapılandırması ekleyin.

---

## Katkıda Bulunan

* **Necibe Güner**

