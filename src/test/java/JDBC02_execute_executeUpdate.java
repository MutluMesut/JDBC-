import java.sql.*;

public class JDBC02_execute_executeUpdate {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
     /*
 	A) CREATE TABLE, DROP TABLE, ALTER TABLE gibi DDL ifadeleri icin sonuc kümesi (ResultSet)
 	   dondurmeyen metotlar kullanilmalidir. Bunun icin JDBC'de 2 alternatif bulunmaktadir.

 	    1) execute() metodu - boolean dondurur.
 	    2) executeUpdate() metodu - int deger dondurur.

 	B) - execute() metodu her tur SQL ifadesiyle kullanilabilen genel bir komuttur.
 	   - execute(), Boolean bir deger dondurur. DDL islemlerinde false dondururken,
 	     DML islemlerinde true deger dondurur.
 	   -  Bu metod, sorgunun sonucunu döndüren ResultSet nesnesini döndürür.
 	   - Ozellikle, hangi tip SQL ifadesine hangi metodun uygun oldugunun bilinemedigi
 	     durumlarda tercih edilmektedir.

 	C) - executeUpdate() metodu ise INSERT, Update gibi DML islemlerinde yaygin kullanilir.
 	   - bu islemlerde islemden etkilenen satir sayisini dondurur.
 	   - Bu metod, veritabanındaki verilerin değiştirilmesi sonucu oluşan satır sayısını döndürür.
 	   - Ayrıca, DDL islemlerinde de kullanilabilir ve bu islemlerde 0 dondurur.

    */

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "1234");
        Statement st = con.createStatement();

        /*======================================================================
		  ORNEK1: isciler tablosunu siliniz.
 	    ========================================================================*/

        String dropQuery = "DROP TABLE isciler";
                        // ResultSete atama yapmiyoruz. Cunku  ResulSet veritabani sorgusundan donen
                        // verileri tutar ve bu veriler uzrinde gezinme okuma-degistirme gibi islemler
                        // yapılmasina olanak saglayan bir veri yapsi olup DDL komutlari gbi veritabani
                        // uzerinden degisiklik yapıp veri dondurmeyen komutlar ResultSet ile atanmazlar
                        //KISACA===> DDL komutlari RESULTSET ile ATANMAZLAR.

          System.out.println(st.execute(dropQuery));
                        // isciler tableyi burda ve hmen  asagida silmek istersem hata
                        // verir.Silinen bir tablo ikinci kez silinmez


       // if (!st.execute(dropQuery)) {
       //     System.out.println("Isciler tablosu silindi!");
       // }====>>>> asagidaki if (!st.execute(createTable)) yazimindan dolayi bu kismi
        //=====<<>>sonradan yoruma aldım

        /*=======================================================================
          ORNEK2: isciler adinda bir tablo olusturunuz id int,
          birim VARCHAR(10), maas int
	    ========================================================================*/

                         // create komutu DDL komut olup execute metodu icinde kullanildiginda FALSE deger donderir.
        String createTable = "CREATE TABLE isciler" +
                "(id INT, " +
                "birim VARCHAR(10), " +
                "maas INT)";

        if (!st.execute(createTable)) {
            System.out.println("Isciler tablosu olusturuldu!");
        }








        /*=======================================================================
		  ORNEK3: isciler tablosuna yeni bir kayit (80, 'ARGE', 4000)
		  ekleyelim.
		========================================================================*/

                         // isciler tablosunda birim variable i varchar oldugu icin tek tirnak icine alındı.
        String insertData = "INSERT INTO isciler VALUES(80, 'ARGE', 4000)";

        System.out.println("Islemden etkilenen satir sayisi : " + st.executeUpdate(insertData));
                       // Burada ResultSet e atama yapmadik.Cnku:executeUpdate() metodu int bir sonuc zaten
                       // Donduruyor.











        /*=======================================================================

          ORNEK4: isciler tablosuna birden fazla yeni kayıt ekleyelim.
            INSERT INTO isciler VALUES(70, 'HR', 5000)
            INSERT INTO isciler VALUES(60, 'LAB', 3000)
            INSERT INTO isciler VALUES(50, 'ARGE', 4000)
         ========================================================================*/

        System.out.println("================================ 1. Yontem ==========================================");


                        //Array: Ayni data turunden birden fazla eleman barindirabilen obje olduğu icin Stringlerden olusan bir
                        //array olusturduk
        String [] queries= {"INSERT INTO isciler VALUES(70, 'HR', 5000)",
                            "INSERT INTO isciler VALUES(60, 'LAB', 3000)",
                            "INSERT INTO isciler VALUES(50, 'ARGE', 4000)"};


        int count=0;
        for (String each:queries) {
          count+=  st.executeUpdate(each);

                            //st.executeUpdate(each) ===><><><<>> metodu bize uc tane bir dondereceginden
                            //count isimli int bir variable olusturduk
        }
        System.out.println(count + " satir eklendi!");





                            // Ayri ayri sorgular ile veritabanina tekrar tekrar ulasmak islemlerin
                            // yavas yapilmasina yol acar. 10000 tane veri kaydi yapildigi dusunuldugunde
                            // bu kotu bir yaklasimdir.


         System.out.println("================================ 2. Yontem ==========================================");

                            // 2.YONTEM (addBatch ve executeBatch() metotlari ile)
                            // ----------------------------------------------------
                            // addBatch metodu ile SQL ifadeleri gruplandirilabilir ve executeBatch()
                            // metodu ile veritabanina bir kere gonderilebilir.
                            // executeBatch() metodu bir int [] dizi dondurur. Bu dizi her bir ifade sonucunda
                            // etkilenen satir sayisini gosterir.

        String [] queries2= {"INSERT INTO isciler VALUES(10, 'Teknik', 3000)",
                             "INSERT INTO isciler VALUES(20, 'Kantin', 2000)",
                             "INSERT INTO isciler VALUES(30, 'ARGE', 5000)"};




        for (String each:queries2) { // Bu dongude her bir sql komutunu torpaya atiyoruz

             st.addBatch(each);

        }
        st.executeBatch();// Burada  tek seferde tum torpayi database e isliyor
        System.out.println("satirlar eklendi");









        /*===============================================
         ORNEK 5: isciler tablosunu goruntuleyin
         ================================================*/

        System.out.println("===================ISCİLER TABLOSU=====================");


        String selectQuery = "SELECT * FROM isciler";
        ResultSet iscilerTablosu= st.executeQuery(selectQuery);

        while(iscilerTablosu.next()){
            System.out.println(iscilerTablosu.getInt(1)+ " "+
                    iscilerTablosu.getString(2)+" "+
                    iscilerTablosu.getInt(3));
        }









        /*=======================================================================
        ORNEK6: isciler tablosundaki maasi 5000'den az olan iscilerin maasina
        %10 zam yapiniz
         ========================================================================*/

        String updateQery="UPDATE isciler SET maas=maas*1.1 WHERE maas<5000";
        int satir=st.executeUpdate(updateQery);
                      // executeUpdate () metodu bize int bir deger donderdigi icin ,
                      // int bir variable a atama yaptm

        System.out.println(satir +" satir guncellendi" );

                      // Guncellenip guncellenmedigini select komutu ile girebilirim
                      // maasa zam guncellemesini kontrol icin select komutunu kllanmak gerekiyor
                      // 5. soru ile 6. soru arasindaki yeri bu nednele kopyalayip 7. sorunun altina
                      // yapistirdik. Ancak String selectQuery = "SELECT * FROM isciler" bu kismi
                      // sildik CNK olusturdugumuz selectQuery variableni executeQuery(selectQuery)
                      // metodu uzerinden kullanrak RESULTSET e atadik.
                      // Sadece  ResultSet iscilerTablosu2 olarak ismi degistirdik









        /*===============================================
         ORNEK 7: isciler tablosunun son hali goruntuleyin
         =================================================*/

        System.out.println("===================ISCİLER TABLOSU MAAS ZAAMLARİ=====================");

        ResultSet iscilerTablosu2= st.executeQuery(selectQuery);
                         //select komutunu 5. sorunun yukarisinda String selectQuery variablina
                         // atandigi icin bu kismi kullaniyoruz

        while(iscilerTablosu2.next()){
            System.out.println(iscilerTablosu2.getInt(1)+ " "+
                    iscilerTablosu2.getString(2)+" "+
                    iscilerTablosu2.getInt(3));

        }









        /*=======================================================================
       ORNEK8: Isciler tablosundan birimi 'ARGE' olan iscileri siliniz.

         =======================================================================*/


        System.out.println("==========================isciler tablo son hal  ============================");

        String deleteQuery= "DELETE FROM isciler WHERE birim='ARGE'";

        int silinenSatirSayisi= st.executeUpdate(deleteQuery);

        System.out.println(silinenSatirSayisi+ " satir silindi");

        ResultSet iscilerTablosu3= st.executeQuery(selectQuery);
                             //select komutunu 5. sorunun yukarisinda String selectQuery variablina
                             // atandigi icin bu kismi kullaniyoruz

        while(iscilerTablosu3.next()){
            System.out.println(iscilerTablosu3.getInt(1)+ " "+
                    iscilerTablosu3.getString(2)+" "+
                    iscilerTablosu3.getInt(3));
        }


        con.close();
        st.close();
        iscilerTablosu.close();
        iscilerTablosu2.close();
        iscilerTablosu3.close();


    }
}
