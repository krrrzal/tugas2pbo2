/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Asus-Sc
 */
public class configDB {
    
    private static final String jdbcURL = "jdbc:mysql://localhost:3306/khairurrizal_2210010065_pbo2";
    private static final String username = "root";
    private static final String password = "";
    
    public configDB () {}

    public configDB (String url, String user, String pass) {
        try (Connection Koneksi = DriverManager.getConnection(url, user, pass)) {
            Driver mysqldriver = new com.mysql.jdbc.Driver(); 
            DriverManager.registerDriver(mysqldriver);

            System.out.println("Berhasil"); 
        } catch (SQLException error) {
            System.err.println(error.toString());
        }
    }
    
    public Connection getKoneksi () throws SQLException{
        try { 
            Driver mysqldriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver (mysqldriver);

        } catch (SQLException e) {

            System.err.println(e.toString());
        }

         return DriverManager.getConnection(this.jdbcURL, this.username, this.password);
    }
    
    public boolean duplicateKey(String table, String PrimaryKey, String value) { 
        boolean hasil=false;

        try {
            Statement sts = getKoneksi().createStatement();
            ResultSet rs = sts.executeQuery("SELECT * FROM "+table+" WHERE "+PrimaryKey+" ='"+value+"'");

            hasil = rs.next();

            rs.close();
            sts.close(); 
            getKoneksi().close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        
        return hasil;
    }
    
    public void SimpanDinamisskortutupan (String No, String TutupLahan, String Skor) {
        try {
            if (duplicateKey("skorkelastutupanlahan", "No", No)) {
                JOptionPane.showMessageDialog(null, "No sudah Terdaftar");
            } else{ 
                String SQL ="INSERT INTO skorkelastutupanlahan (No, TutupLahan, Skor) VALUE (?, ?, ?)";
                PreparedStatement simpan = getKoneksi ().prepareStatement (SQL);
                simpan.setString(1, No);
                simpan.setString (2, TutupLahan);
                simpan.setString(3, Skor);
                 System.out.println("Data Berhasil Disimpan");
                simpan.executeUpdate();
                simpan.close(); getKoneksi ().close();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public void SimpanDinamisskorjenis (String No, String TutupLahan, String Skor, String Ket) {
        try {
            if (duplicateKey("skorkelasjenistanah", "No", No)) {
                JOptionPane.showMessageDialog(null, "No sudah Terdaftar");
            } else{ 
                String SQL ="INSERT INTO skorkelasjenistanah (No, JenisTanah, Skor, Ket) VALUE (?, ?, ?, ?)";
                PreparedStatement simpan = getKoneksi ().prepareStatement (SQL);
                simpan.setString(1, No);
                simpan.setString (2, TutupLahan);
                simpan.setString(3, Skor);
                simpan.setString(4, Ket);
                 System.out.println("Data Berhasil Disimpan");
                simpan.executeUpdate();
                simpan.close(); getKoneksi ().close();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public String getFieldValueEdit(String[] Field, String[] value){
        String hasil = "";
        int deteksi = Field.length-1;
        try {
            for (int i = 0; i < Field.length; i++) {
                if (i==deteksi){
                    hasil = hasil +Field[i]+" ='"+value[i]+"'";
                }else{
                   hasil = hasil +Field[i]+" ='"+value[i]+"',";  
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        return hasil;
    }
    
    public void UbahDinamis(String NamaTabel, String PrimaryKey, String IsiPrimary,String[] Field, String[] Value){
        
        try {
           String SQLUbah = "UPDATE "+NamaTabel+" SET "+getFieldValueEdit(Field, Value)+" WHERE "+PrimaryKey+"='"+IsiPrimary+"'";
           Statement perintah = getKoneksi().createStatement();
           perintah.executeUpdate(SQLUbah);
           perintah.close();
           getKoneksi().close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        
    }
    
    public void HapusDinamis(String NamaTabel, String PK, String isi){
        try {
            String SQL="DELETE FROM "+NamaTabel+" WHERE "+PK+"='"+isi+"'";
            Statement perintah = getKoneksi().createStatement();
            perintah.executeUpdate(SQL);
            perintah.close();
            JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
