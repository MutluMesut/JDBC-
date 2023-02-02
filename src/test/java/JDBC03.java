import java.sql.*;

public class JDBC03 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "1234");
        Statement st = con.createStatement();

      //String delete= "DROP table team6";
      //System.out.println(st.executeUpdate(delete));

      //String create = "CREATE TABLE team6 (isim VARCHAR(15), soyisim VARCHAR(15), sehir VARCHAR(15), yas INT, maas INT )";


      // System.out.println(st.executeUpdate(create));

      // System.out.println("======Tablo Olusturuldu=========");

       // String insertTekli= "INSERT INTO team6 VALUES ('Ali', 'A','Ankara', 30, 15000)";
        //System.out.println(st.executeUpdate(insertTekli));
        //System.out.println("========tekli insert tamam===============");



     /*   String [] InsertCoklu= {"INSERT INTO team6 VALUES ('Veli', 'V', 'Maras',35, 12000)",
                               "INSERT INTO team6 VALUES ('Mutlu', 'M', 'Ankara',33, 20000)",
                               "INSERT INTO team6 VALUES ('Ahmet', 'Y', 'Maras',33, 20000)"};

       int count=0;
       for (String each:InsertCoklu) {
           count+=st.executeUpdate(each);

       }
       System.out.println(count + " satir eklendi ");*/


     /*   System.out.println("********************TEAM6 TABLOSU*******************");

        String select ="SELECT * FROM team6";
        ResultSet rs= st.executeQuery(select);
        while(rs.next()){
            System.out.println(rs.getString("isim")+ " "+rs.getString("soyisim")+ " "+
              rs.getString("sehir")+" "+
              rs.getInt("yas")+" "+
              rs. getInt("maas") );
        }*/
        /*
        ********************TEAM6 TABLOSU*******************
         Ali A Ankara 30 15000
         Veli V Maras 35 12000
         Mutlu M Ankara 33 20000
         Ahmet Y Maras 33 20000
         */


        System.out.println("***********2.COZUM YOLU***************");

      /*  String [] insertCoklu2= { "INSERT INTO team6 VALUES ('Ayse', 'A', 'İzmir', ,36, 18000)",
                "INSERT INTO team6 VALUES ('Fatma', 'F', 'İzmir', ,39, 19000)",
                "INSERT INTO team6 VALUES ('Hayriye', 'H', 'İstanbul', ,36, 28000)"};

        for (String each:insertCoklu2) {
            st.addBatch(each);
        }
        st.executeBatch();

        System.out.println("******Satirlar tek seferde eklendi*******");


        String select = "SELECT * FROM team6";
        ResultSet sonuc= st.executeQuery(select);

       while (sonuc.next()){
           System.out.println(sonuc.getString("isim")+" "+
                   sonuc.getString("soyisim")+" "+
                   sonuc.getString("sehir")+" "+
                   sonuc.getInt("yas")+" "+
                   sonuc.getInt("maas"));
       }*/







        /* ********************************************************
         team6 tablosunda maasi 20000 Tl den az olanlara %20 zam yapalim
         *********************************************************** */

     /*  String update= "UPDATE team6 SET maas=maas*(10000) Where maas<20000";
        int zam= st.executeUpdate(update);
        System.out.println(zam+" %20 zam yapildi");
        System.out.println("************Update komutu calisti**************");

        String select = "SELECT maas FROM team6";
        ResultSet tblgor1=st.executeQuery(select);
        while (tblgor1.next()){
            System.out.println(tblgor1.getString("maas"));
        }*/
        /*
                50000
                40000
                20000
                20000
         */


        /*=================================================
        team6 dan memleketi maras olanlari silelim
         ===================================================*/

        String delete= "DELETE FROM team6 WHERE sehir='maras'";
        System.out.println(st.executeUpdate(delete));
        System.out.println("****************maraslilar silindi****************************");


        String select = "SELECT *FROM team6 ";
        ResultSet tbl1= st.executeQuery(select);
        while(tbl1.next()){
            System.out.println(tbl1.getString(1)+" "+
                    tbl1.getString(2)+" "+
                    tbl1.getString(3)+" "+
                    tbl1.getString(4)+" "+
                    tbl1.getString(5));
        }

        con.close();
        st.close();


    }
}
