import java.sql.*;

public class JDBC02_Query01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "1234");
        Statement st = con.createStatement();


        //*=======================================================================
       // ORNEK1: Id'si 1'den buyuk firmalarin ismini ve iletisim_isim'ini isim
        //ters sirali yazdirin.
         // ========================================================================*/

        /*
        CREATE TABLE firmalar
        (
        id INT,
        isim VARCHAR(20),
        iletisim_isim VARCHAR(20),
        CONSTRAINT firmalar_pk PRIMARY KEY (id, isim)
        );

        INSERT INTO firmalar VALUES
        (1, 'ACB', 'Ali Can'),
        (2, 'RDB', 'Veli Gul'),
        (3, 'KMN', 'Ayse Gulmez');
         */





        String selectquery = "SELECT isim, iletisim_isim " +
                "FROM firmalar " +
                "WHERE id>1 " +
                "ORDER BY isim DESC";

      //  String selectquery2 = "SELECT isim, iletisim_isim FROM firmalar WHERE id>1 ORDER BY isim DESC";

        ResultSet data = st.executeQuery(selectquery);

        while(data.next()) {
            System.out.println(data.getString("isim") + " " +
                    data.getString("iletisim_isim"));


        }


        //*=======================================================================
        // ORNEK1: iletisim isminde 'li' iceren
        // firmalarin id lerini ve isimlerini
        //id sirali yazdirin.
        // ========================================================================*/


        System.out.println("============ORNEK 2================");

        String selectquery1= "SELECT id, isim from firmalar where iletisim_isim like '%li%' order by id ";


        ResultSet data1 = st.executeQuery(selectquery1);

        while(data1.next()) {
            System.out.println(data1.getInt("id") + " " +
                    data1.getString("isim"));


        }

        //while(data1.next()) {
        //    System.out.println(data1.getInt(1) + " " +
        //            data1.getString(2));

        //    // Sorgudaki (String selectquery1= "SELECT id, isim from firmalar where iletisim_isim like '%li%' order by id ";)
        //    // siralama onemli birinci indexte id olduğu icn ciktida ilk id sonra isim olur.
        //    // Sorgudaki siralamyi degistirirsek yani fieldlerin yerini, isim ile id yerini degistirisek o zaman cikti ilk isim
        //    // sonra id olur.=====>>4 FDS
        //}



        // NOT1 : Sorgulama icin get ile istenirse sütun (field) ismini yazabilecegimiz gibi sutun index
        // (field olusturulma sirasina gore) yazilabilir.

        // NOT2 : Sorgumuzda SELECT'ten sonra sadece belli fieldlari dondurmesini istiyorsak
        // get ile cagirdigimiz field indexleri sorguda belirttigimiz sirayla ifade etmemiz gerekiyor

        con.close();
        st.close();
        data1.close();
        data.close();



    }
}
