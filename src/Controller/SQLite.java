package Controller;

import Model.History;
import Model.Logs;
import Model.Product;
import Model.User;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JOptionPane;
public class SQLite {
    
    public int DEBUG_MODE = 0;
    String driverURL = "jdbc:sqlite:" + "database.db";
    private static final int MAX_ATTEMPTS = 5;
    private static final int LOCKOUT_TIME = 5 * 60 * 1000;
    
    public void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Database database.db created.");
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void createHistoryTable() {
        String sql = "CREATE TABLE IF NOT EXISTS history (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " username TEXT NOT NULL,\n"
            + " name TEXT NOT NULL,\n"
            + " stock INTEGER DEFAULT 0,\n"
            + " timestamp TEXT NOT NULL\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table history in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void createLogsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS logs (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " event TEXT NOT NULL,\n"
            + " username TEXT NOT NULL,\n"
            + " desc TEXT NOT NULL,\n"
            + " timestamp TEXT NOT NULL\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table logs in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
     
    public void createProductTable() {
        String sql = "CREATE TABLE IF NOT EXISTS product (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " name TEXT NOT NULL UNIQUE,\n"
            + " stock INTEGER DEFAULT 0,\n"
            + " price REAL DEFAULT 0.00\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table product in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
     
    public void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " username TEXT NOT NULL UNIQUE,\n"
            + " password TEXT NOT NULL,\n"
            + " role INTEGER DEFAULT 2,\n"
            + " locked INTEGER DEFAULT 0,\n"
            + " failed_attempt INTEGER DEFAULT 0\n"
            + ");";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table users in database.db created.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropHistoryTable() {
        String sql = "DROP TABLE IF EXISTS history;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table history in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropLogsTable() {
        String sql = "DROP TABLE IF EXISTS logs;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table logs in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropProductTable() {
        String sql = "DROP TABLE IF EXISTS product;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table product in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void dropUserTable() {
        String sql = "DROP TABLE IF EXISTS users;";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table users in database.db dropped.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addHistory(String username, String name, int stock, String timestamp) {
        String sql = "INSERT INTO history(username,name,stock,timestamp) VALUES('" + username + "','" + name + "','" + stock + "','" + timestamp + "')";
        
        try {
            Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, name);
            pstmt.setInt(3, stock);
            pstmt.setString(4, timestamp);
            pstmt.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            System.out.print(ex);
            System.out.println("1");
        }
    }
    
    public void addLogs(String event, String username, String desc, String timestamp) {
        String sql = "INSERT INTO logs(event,username,desc,timestamp) VALUES(?,?,?,?)";
        
        try {
            Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, event);
            pstmt.setString(2, username);
            pstmt.setString(3, desc);
            pstmt.setString(4, timestamp);
            pstmt.executeUpdate();
            
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public void addProduct(String name, int stock, double price) {
        String sql = "INSERT INTO product(name,stock,price) VALUES(?,?,?)";
        
        try {
            Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, stock);
            pstmt.setDouble(3, price);
            pstmt.executeUpdate();
            this.addLogs("UPDATED", name, "Successfully added " + stock + " stocks of " + name +".", new Timestamp(new Date().getTime()).toString());
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public void addUser(String username, String password) {
        String sql = "INSERT INTO users(username,password) VALUES('" + username + "','" + password + "')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
            this.addLogs("NOTICE", username, "User creation successful", new Timestamp(new Date().getTime()).toString());

        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    
    public ArrayList<History> getHistory(){
        String sql = "SELECT id, username, name, stock, timestamp FROM history";
        ArrayList<History> histories = new ArrayList<History>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                histories.add(new History(rs.getInt("id"),
                                   rs.getString("username"),
                                   rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return histories;
    }
    
    public ArrayList<Logs> getLogs(){
        String sql = "SELECT id, event, username, desc, timestamp FROM logs";
        ArrayList<Logs> logs = new ArrayList<Logs>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                logs.add(new Logs(rs.getInt("id"),
                                   rs.getString("event"),
                                   rs.getString("username"),
                                   rs.getString("desc"),
                                   rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return logs;
    }
    
    public ArrayList<Product> getProduct(){
        String sql = "SELECT id, name, stock, price FROM product";
        ArrayList<Product> products = new ArrayList<Product>();
        
        try {
            Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                products.add(new Product(rs.getInt("id"),
                                   rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getFloat("price")));
            }
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("8");
        }
        return products;
    }
    
    public ArrayList<User> getUsers(){
        String sql = "SELECT id, username, password, role, locked FROM users";
        ArrayList<User> users = new ArrayList<User>();
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                users.add(new User(rs.getInt("id"),
                                   rs.getString("username"),
                                   rs.getString("password"),
                                   rs.getInt("role"),
                                   rs.getInt("locked")));
            }
        } catch (Exception ex) {}
        return users;
    }
    
    
    
    
    public void updateProduct(String name, String oldProdName, int newStock, float newPrice){
        String sql = "UPDATE product SET name=?, stock=?, price=? WHERE name=?;";
        try {
            Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            // SET UPDATE 
            pstmt.setString(1, name);
            pstmt.setInt(2, newStock);
            pstmt.setFloat(3, newPrice);
            pstmt.setString(4, oldProdName);
            pstmt.executeUpdate();
            this.addLogs("UPDATE", name, "Successfully updated " + name + " stocks.", new Timestamp(new Date().getTime()).toString());
            
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public void updateHistory (String newName, String oldName) {
        String sql = "UPDATE history SET name=? WHERE name=?;";
        
        try {
            Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newName);
            pstmt.setString(2, oldName);
            pstmt.executeUpdate();
            conn.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void removeUser(String username) {
        String sql = "DELETE FROM users WHERE username='" + username + "';";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            this.addLogs("DELETE", username, "User deleted", new Timestamp(new Date().getTime()).toString());
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void deleteLogs(){
        String sql = "DELETE FROM logs;";
        try {
            Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public Product getProduct(String name){
        String sql = "SELECT name, stock, price FROM product WHERE name=?;";
        Product product = null;
        try {
            Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            product = new Product(rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getFloat("price"));
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("17");
        }
        return product;
    }
    
    public void deleteProduct(String name){
        String sql = "DELETE FROM product WHERE name=?;";
        try {
            Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            this.addLogs("DELETE", name, "Successfully deleted "+ name + " from products.", new Timestamp(new Date().getTime()).toString());
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public void purchaseProduct(String name, int newStock, float newPrice){
        String sql = "UPDATE product SET stock=?, price=? WHERE name=?;";
        try {
            Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            // SET UPDATE 
            pstmt.setString(1, name);
            pstmt.setInt(2, newStock);
            pstmt.setFloat(3, newPrice);
            pstmt.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public void editAttempts (String username, int attempts) {
        
        String sql = "UPDATE users SET attempts=? WHERE username=?";
        
        try { 
            Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, attempts);
            pstmt.setString(2, username.toLowerCase());
            pstmt.executeUpdate();
            this.addLogs("UPDATE", username, "Successfully edited attempts of user ''"+ username + "'' to " + attempts +" attempts.", new Timestamp(new Date().getTime()).toString());
            conn.close();
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public void editLocked (String username, int locked) {
        String sql = "UPDATE users SET locked=? WHERE username=?";
        
        try { 
            Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, locked);
            pstmt.setString(2, username.toLowerCase());
            pstmt.executeUpdate();
            this.addLogs("LOCK", username, "Successfully changed lock of user ''"+ username + "''.", new Timestamp(new Date().getTime()).toString());
            conn.close();
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public void editRole(String username, int role){
        
        String sql = "UPDATE users SET role=? WHERE username=?";
        try { 
            Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, role);
            pstmt.setString(2, username.toLowerCase());
            pstmt.executeUpdate();
            this.addLogs("UPDATE", username, "Successfully edited role of user ''"+ username + "'' to " + role +".", new Timestamp(new Date().getTime()).toString());
            conn.close();
            
            
        } catch (Exception ex) {
            System.out.println(ex);
        }        
        
    }
    
    public boolean checkUser(String username, String password){
        String sql = "SELECT id, username, password, role, locked FROM users WHERE username='"+username+"' AND password='"+password+"';";
        User user = new User();
        System.out.println(username);
        System.out.println(password);
        try{
            Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                user = new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("role"),
                        rs.getInt("locked"));
            }
            if(user.getId() != 0)
                return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
        }
    

    public boolean login(javax.swing.JTextField username, javax.swing.JTextField password){
        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()){
             stmt.execute("CREATE TABLE IF NOT EXISTS login_attempts (" +
                          "username TEXT, " +
                          "timestamp INTEGER)");
             
             if (isAccountLockedOut(username.getText())) {
                JOptionPane.showMessageDialog(null, "Account is locked out. Please try again later.");
                return false;
            }

            // Check if username and password are valid
            if (isValidCredentials(username.getText(), password.getText())) {
                System.out.println("Login successful!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.");
                

                // Log login attempt
                logLoginAttempt(username.getText());

                // Check if account should be locked out
                if (isAccountLockedOut(username.getText())) {
                    JOptionPane.showMessageDialog(null, "Too many failed login attempts. Account is now locked out for 5 minutes.");

                    return false;
                } else {
                    return false;
                }
            }
        }catch(Exception e){
            return false;
        }
    }
    
    private boolean isValidCredentials(String username, String password) throws Exception{
        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
             stmt.setString(1, username);
             stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    
    private void logLoginAttempt(String username) throws Exception{
        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO login_attempts (username, timestamp) VALUES (?, ?)")) {
            stmt.setString(1, username);
            stmt.setLong(2, System.currentTimeMillis());
            stmt.executeUpdate();
        }
    }
    
    private boolean isAccountLockedOut(String username) throws Exception{
        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM login_attempts WHERE username = ? AND timestamp > ?")) {
            stmt.setString(1, username);
            stmt.setLong(2, System.currentTimeMillis() - LOCKOUT_TIME);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                int count = rs.getInt(1);
                return count >= MAX_ATTEMPTS;
            }
        }
    }
    
    public User getUser(String name){
    String sql = "SELECT id, username, password, role, locked FROM users WHERE username=?";
    User user = null;
    try {
        Connection conn = DriverManager.getConnection(driverURL);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            user = new User(rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("role"),
                    rs.getInt("locked"));
        }
        conn.close();
    } catch (Exception ex) {
        System.out.println(ex);
        System.out.println("17");
        System.out.println(user);
    }
        return user;
}
   
    
    
     public void updateProduct(String name, int newStock, float price){
        String sql = "UPDATE product SET name=?, stock=?, price=? WHERE name=?;";
        try {
            Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, newStock);
            pstmt.setFloat(3, price);
            pstmt.setString(4, name);
            pstmt.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("18");
        }
    }
     
    public void updateUser (String username, String password) {
        
        String sql = "UPDATE users SET password=? WHERE username=?";
        
        try { 
            Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, password);
            pstmt.setString(2, username.toLowerCase());
            pstmt.executeUpdate();
            conn.close();
            
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("13");
        }
    }
    
    public User claimUser(String username){
        String sql = "SELECT id, username, password, role, locked FROM users WHERE username=?";
        User user = this.getUser(username);
        System.out.println(username);
        try{
            Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                user = new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("role"),
                        rs.getInt("locked"));
            }
            conn.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
            return user;
        }
}    
