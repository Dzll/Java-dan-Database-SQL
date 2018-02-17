package belajar.java.mysql;
/**
 *
 * @author Dzulkarnain Inc
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class ApotekGUI extends JFrame{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/apotek";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);
    
    JLabel label = new JLabel("Apotek in Corporation");
    JLabel logo = new JLabel("+");
    JTable text = new JTable();
    
    JLabel idobat = new JLabel("Kode Obat");
    JTextField txid = new JTextField();
    JLabel nama = new JLabel("Nama Obat");
    JTextField txnama = new JTextField();
    JLabel harga = new JLabel("Harga");
    JTextField txharga = new JTextField();
    JLabel search = new JLabel("Search");
    JTextField txsearch = new JTextField();
    
    JButton cari = new JButton("Search");
    JButton create = new JButton("Create");
    JButton read = new JButton("Read");
    JButton update= new JButton("Update");
    JButton delete = new JButton("Delete");
    JButton c = new JButton("C");
    
    private DefaultTableModel model;
    
    public void insertData() {
        try {
            // ambil input dari user
            String nama_obat = txnama.getText(); //input.readLine().trim();
            int harga = Integer.parseInt(txharga.getText()); //Integer.parseInt(input.readLine());

            // query simpan
            String sql = "INSERT INTO obat (nama_obat, harga) VALUE('%s', '%d')";
            sql = String.format(sql, nama_obat, harga);

            // simpan buku
            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showData(){
        model = new DefaultTableModel();
        text.setModel(model);
        model.addColumn("kode");
        model.addColumn("nama_obat");
        model.addColumn("harga");
        
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            String sql = "SELECT * FROM obat";
            rs = stmt.executeQuery(sql);

            //penelusuran baris pada tabel tblGaji dari database
            while(rs.next ()){
                Object[] obj = new Object[3];
                obj[0] = rs.getString("kode"); 
                obj[1] = rs.getString("nama_obat");
                obj[2] = rs.getString("harga"); 

                model.addRow(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateData() {
        try {  
            // ambil input dari user
            int kode = Integer.parseInt(txid.getText()); //Integer.parseInt(input.readLine());
            String nama_obat = txnama.getText(); //input.readLine().trim();
            int harga = Integer.parseInt(txharga.getText()); //Integer.parseInt(input.readLine());

            // query update
            String sql = "UPDATE obat SET nama_obat='%s', harga='%d' WHERE kode=%d";
            sql = String.format(sql, nama_obat, harga, kode);

            // update data buku
            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void searchData(){
        model = new DefaultTableModel();
        text.setModel(model);
        model.addColumn("kode");
        model.addColumn("nama_obat");
        model.addColumn("harga");
        
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            String cr = txsearch.getText();
            String sql = "SELECT * FROM obat WHERE nama_obat LIKE" + "'%" + cr + "%'";
            rs = stmt.executeQuery(sql);
            //penelusuran baris pada tabel tblGaji dari database
            while(rs.next ()){
                Object[] obj = new Object[3];
                obj[0] = rs.getString("kode"); 
                obj[1] = rs.getString("nama_obat");
                obj[2] = rs.getString("harga"); 

                model.addRow(obj);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void deleteData() {
        try {
            // ambil input dari user
            int kode = Integer.parseInt(txid.getText()); //Integer.parseInt(input.readLine());

            // buat query hapus
            String sql = String.format("DELETE FROM obat WHERE kode=%d", kode);
            // hapus data
            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void GUI(){
        label.setBounds(110, 20, 500, 50);
        label.setFont(new Font("Consolas", Font.BOLD,40));
        label.setForeground(new Color(1, 119, 20));
        logo.setBounds(10, 0, 100, 100);
        logo.setFont(new Font("Arial", Font.BOLD,150));
        logo.setForeground(Color.RED);
        
        idobat.setBounds(20, 90, 100, 30);
        idobat.setFont(new Font("Consolas", Font.BOLD,17));
        txid.setBounds(20, 120, 250, 25);
        nama.setBounds(20, 150, 100, 30);
        nama.setFont(new Font("Consolas", Font.BOLD,17));
        txnama.setBounds(20, 180, 250, 25);
        harga.setBounds(20, 210, 100, 30);
        harga.setFont(new Font("Consolas", Font.BOLD,17));
        txharga.setBounds(20, 240, 250, 25);
        search.setBounds(290, 85, 100, 30);
        search.setFont(new Font("Consolas", Font.BOLD,19));
        txsearch.setBounds(360, 85, 400, 25);
        text.setBounds(290, 120, 570, 310);
        text.setFont(new Font("Consolas", Font.BOLD,15));
        
        cari.setBounds(780, 85, 80, 25);
        cari.setBackground(new Color(20, 180, 255));
        cari.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    Class.forName(JDBC_DRIVER);

                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = conn.createStatement();

                    searchData();

                    stmt.close();
                    conn.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        create.setBounds(20, 370, 120, 25);
        create.setBackground(new Color(79, 255, 117));
        create.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    Class.forName(JDBC_DRIVER);

                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = conn.createStatement();

                    insertData();
                    txnama.setText("");
                    txharga.setText("");
                    showData();

                    stmt.close();
                    conn.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        read.setBounds(20, 405, 120, 25);
        read.setBackground(new Color(255, 249, 94));
        read.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    Class.forName(JDBC_DRIVER);

                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = conn.createStatement();

                    showData();

                    stmt.close();
                    conn.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        update.setBounds(150, 370, 120, 25);
        update.setBackground(new Color(79, 255, 117));
        update.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    Class.forName(JDBC_DRIVER);

                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = conn.createStatement();

                    updateData();
                    txid.setText("");
                    txnama.setText("");
                    txharga.setText("");
                    showData();

                    stmt.close();
                    conn.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        delete.setBounds(150, 405, 70, 25);
        delete.setBackground(new Color(255, 58, 58));
        delete.setForeground(Color.white);
        delete.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    Class.forName(JDBC_DRIVER);

                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = conn.createStatement();

                    deleteData();
                    txid.setText("");
                    showData();

                    stmt.close();
                    conn.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        c.setBounds(228, 405, 43, 25);
        c.setBackground(new Color(255, 58, 58));
        c.setForeground(Color.white);
        c.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                txid.setText("");
                txnama.setText("");
                txharga.setText("");
                txsearch.setText("");
            }
        });
        
        add(label);
        add(logo);
        add(idobat);
        add(txid);
        add(nama);
        add(txnama);
        add(harga);
        add(txharga);
        add(search);
        add(txsearch);
        add(text);
        add(cari);
        add(create);
        add(read);
        add(update);
        add(delete);
        add(c);
        setTitle("Apotek in Corporation | by :  Wafi Dzulkarnain");
        setSize(900, 500);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String args[]){
        ApotekGUI mulai = new ApotekGUI();
        mulai.GUI();

        System.out.println();
    }
}
