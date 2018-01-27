package belajar.java.mysql;
/**
 * Author By : Wafi Dzulkarnain
 * Dzulkarnain Inc | 2018
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
public class JavaCRUD {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/perpustakaan";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);
    
    static void insertBuku() {
        try {
            // ambil input dari user
            System.out.print("Judul     : ");
            String judul = input.readLine().trim();
            System.out.print("Pengarang : ");
            String pengarang = input.readLine().trim();

            // query simpan
            String sql = "INSERT INTO buku (judul, pengarang) VALUE('%s', '%s')";
            sql = String.format(sql, judul, pengarang);

            // simpan buku
            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void showData() {
        String sql = "SELECT * FROM buku";
        try {
            rs = stmt.executeQuery(sql);

            System.out.println("+--------------------------------+");
            System.out.println("|    DATA BUKU DI PERPUSTAKAAN   |");
            System.out.println("+--------------------------------+");
            while (rs.next()) {
                int idBuku = rs.getInt("id_buku");
                String judul = rs.getString("judul");
                String pengarang = rs.getString("pengarang");

                System.out.println(String.format("%d. %s -- (%s)", idBuku, judul, pengarang));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void updateBuku() {
        try {  
            // ambil input dari user
            System.out.print("ID yang mau diedit: ");
            int idBuku = Integer.parseInt(input.readLine());
            System.out.print("Judul: ");
            String judul = input.readLine().trim();
            System.out.print("Pengarang: ");
            String pengarang = input.readLine().trim();

            // query update
            String sql = "UPDATE buku SET judul='%s', pengarang='%s' WHERE id_buku=%d";
            sql = String.format(sql, judul, pengarang, idBuku);

            // update data buku
            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void searchData(){
        System.out.println(" -> SEARCH BY : ");
        System.out.println("1. ID BUKU");
        System.out.println("2. JUDUL");
        System.out.println("3. PENGARANG");
        System.out.println("4. BACK");
        System.out.println();
        System.out.print("Pilih : ");
        String cari = "", jdl = "", pgr = "";
        int idbuk;
        try {
            int pil = Integer.parseInt(input.readLine());
            if(pil == 1){
                System.out.print("ID Buku yang ingin dicari : ");
                idbuk = Integer.parseInt(input.readLine());
                cari = "id_buku = " + idbuk;
                System.out.println();
            }else if(pil == 2){
                System.out.print("Judul Buku yang ingin dicari : ");
                jdl = input.readLine();
                cari = "judul = " + "'" + jdl + "'";
                System.out.println();
            }else if(pil == 3){
                System.out.print("Pengarang Buku yang ingin dicari : ");
                pgr = input.readLine();
                cari = "pengarang = " + "'" + pgr + "'";
                System.out.println();
            }else if(pil == 4){
                showMenu();
            }else{
                System.err.println("Pilihan Salah !!");
            }
            String sql = "SELECT * FROM buku WHERE " + cari;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int idBuku = rs.getInt("id_buku");
                String judul = rs.getString("judul");
                String pengarang = rs.getString("pengarang");
                
                System.out.println(String.format("%d. %s -- (%s)", idBuku, judul, pengarang));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void deleteBuku() {
        try {

            // ambil input dari user
            System.out.print("ID yang mau dihapus: ");
            int idBuku = Integer.parseInt(input.readLine());

            // buat query hapus
            String sql = String.format("DELETE FROM buku WHERE id_buku=%d", idBuku);
            // hapus data
            stmt.execute(sql);

            System.out.println("Data telah terhapus...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void showMenu() {
        System.out.println("\n========= MENU UTAMA =========");
        System.out.println("1. Insert Data");
        System.out.println("2. Show Data");
        System.out.println("3. Edit Data");
        System.out.println("4. Search Data");
        System.out.println("5. Delete Data");
        System.out.println("0. Keluar");
        System.out.println("==============================");
        System.out.print("PILIHAN -> ");
            try {
                int pilihan = Integer.parseInt(input.readLine());

                switch (pilihan) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        insertBuku();
                        break;
                    case 2:
                        showData();
                        break;
                    case 3:
                        updateBuku();
                        break;
                    case 4:
                        searchData();
                        break;
                    case 5:
                        deleteBuku();
                        break;
                    default:
                        System.err.println("Pilihan salah!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    public static void main(String[] args) {
        try {
            // register driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            while (!conn.isClosed()) {
                showMenu();
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
