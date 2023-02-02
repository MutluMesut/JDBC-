import java.sql.*;

public class JDBC01_Query01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

                    // 1 - Ilgili Driver'i yuklemeliyiz. MySQL kullandigimizi bildiriyoruz.
                    // Driver'i bulamama ihtimaline karsi forName metodu icin ClassNotFoundException
                    // method signature'imiza axception olarak firlatmamizi istiyor.

        Class.forName("com.mysql.cj.jdbc.Driver");
                    // mysql icin prantez icindeki kisim sabit olup  başka driver icn
                    // internete yazr buluruz
                    // bu islemler mysql in driverini istemis oluyorouz



                     // 2 - Baglantiyi olusturmak icin username ve password girmeliyiz.
                     // Burada da bu username ve password'un yanlis olma ihtimaline karsi
                     // SQLException firlatmamizi istiyor.
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "1234");
                     //Connection con ile obje olusturmasak bile soldaki baglantıyi gercekleştirmis oluruz
                     // Bunu olusturmamizin nedeni soldaki baglanti uzerinden islem yapabilmemiz icin
                     // gereklidir. Nesne uzerinden islemlerimizi gerceklestirecegiz




                       // 3 - SQL query'leri icin bir Statement objesi olusturup , javada SQL
                      // sorgularimiz icin bir alan acacagiz.
                      // Sql deki tum metodlari bu obje uzerinden cagiracagiz

        Statement st = con.createStatement();




                    // 4- st den donen resulset. Sgl query lerimizi yazip clistirabailirz
       ResultSet veri= st.executeQuery("select * from personel ");




                    //5-Sonuclari gormek icin Iteration il Set icerisindeki elemanlari while dongusu
                    // ile yazdiracagiz.
        while(veri.next()){
            System.out.println(veri.getInt(1)+" "
                    +veri.getString(2)+" "
                    +veri.getString(3)+" "
                    +veri.getInt(4)+" "
                    +veri.getString(5));
        }

                  //6-Olusturulan baglantilari kapatiyoruz ki bellejten kaldirilsin
        con.close();
        st.close();
        veri.close();







    }



}
