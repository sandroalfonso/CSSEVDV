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
            + " locked INTEGER DEFAULT 0\n"
            + " failed_attempts INTEGER DEFAULT 0\n"
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
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addLogs(String event, String username, String desc, String timestamp) {
        String sql = "INSERT INTO logs(event,username,desc,timestamp) VALUES('" + event + "','" + username + "','" + desc + "','" + timestamp + "')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addProduct(String name, int stock, double price) {
        String sql = "INSERT INTO product(name,stock,price) VALUES('" + name + "','" + stock + "','" + price + "')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void addUser(String username, String password) {
        String sql = "INSERT INTO users(username,password) VALUES('" + username + "','" + password + "')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
            
//      PREPARED STATEMENT EXAMPLE
//      String sql = "INSERT INTO users(username,password) VALUES(?,?)";
//      PreparedStatement pstmt = conn.prepareStatement(sql)) {
//      pstmt.setString(1, username);
//      pstmt.setString(2, password);
//      pstmt.executeUpdate();
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
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                products.add(new Product(rs.getInt("id"),
                                   rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getFloat("price")));
            }
        } catch (Exception ex) {
            System.out.print(ex);
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
    
    public void addUser(String username, String password, int role) {
        String sql = "INSERT INTO users(username,password,role) VALUES('" + username + "','" + password + "','" + role + "')";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
            
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public void removeUser(String username) {
        String sql = "DELETE FROM users WHERE username='" + username + "';";

        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("User " + username + " has been deleted.");
        } catch (Exception ex) {
            System.out.print(ex);
        }
    }
    
    public Product getProduct(String name){
        String sql = "SELECT name, stock, price FROM product WHERE name='" + name + "';";
        Product product = null;
        try (Connection conn = DriverManager.getConnection(driverURL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            product = new Product(rs.getString("name"),
                                   rs.getInt("stock"),
                                   rs.getFloat("price"));
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return product;
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
    
    public boolean validateUser(String username, String password) {
    boolean isValidUser = false;
    int maxFailedAttempts = 5; // Maximum number of failed attempts allowed
    int lockoutDuration = 5; // Lockout duration in minutes
    long lockoutEndTime = 0;

    try (Connection conn = DriverManager.getConnection(driverURL);
         Statement stmt = conn.createStatement()) {
        String sql = "SELECT * FROM users WHERE username = '" + username + "'";
        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next()) {
            int id = rs.getInt("id");
            String storedPassword = rs.getString("password");
            int role = rs.getInt("role");
            int locked = rs.getInt("locked");
            int failedAttempts = rs.getInt("failed_attempts");
            Timestamp lastFailedAttempt = rs.getTimestamp("last_failed_attempt");

            if (locked == 1) {
                // Account is locked, check if lockout duration has passed
                long currentTime = System.currentTimeMillis();
                long lockoutStartTime = lastFailedAttempt.getTime();
                long elapsedMinutes = (currentTime - lockoutStartTime) / (60 * 1000);

                if (elapsedMinutes >= lockoutDuration) {
                    // Lockout duration has passed, unlock account
                    sql = "UPDATE users SET locked = 0, failed_attempts = 0, last_failed_attempt = NULL WHERE id = " + id;
                    stmt.executeUpdate(sql);
                } else {
                    // Account is still locked, throw exception
                    throw new Exception("Account is locked. Please try again later.");
                }
            } else {
                // Account is not locked, check password
                if (storedPassword.equals(password)) {
                    // Password is correct, reset failed attempts and return true
                    sql = "UPDATE users SET failed_attempts = 0, last_failed_attempt = NULL WHERE id = " + id;
                    stmt.executeUpdate(sql);
                    isValidUser = true;
                } else {
                    // Password is incorrect, increment failed attempts
                    int newFailedAttempts = failedAttempts + 1;

                    if (newFailedAttempts >= maxFailedAttempts) {
                        // Maximum number of failed attempts exceeded, lock account
                        lockoutEndTime = System.currentTimeMillis() + (lockoutDuration * 60 * 1000);
                        sql = "UPDATE users SET locked = 1, failed_attempts = " + newFailedAttempts + ", last_failed_attempt = '" + new Timestamp(lockoutEndTime) + "' WHERE id = " + id;
                    } else {
                        // Increment failed attempts
                        sql = "UPDATE users SET failed_attempts = " + newFailedAttempts + ", last_failed_attempt = CURRENT_TIMESTAMP WHERE id = " + id;
                    }

                    stmt.executeUpdate(sql);
                    throw new Exception("Invalid username or password.");
                }
            }
        } else {
            // Username not found
            throw new Exception("Invalid username or password.");
        }
    } catch (Exception ex) {
        System.out.print(ex);
    }

    return isValidUser;
}
//    public boolean login(String username, String password){
//        try (Connection conn = DriverManager.getConnection(driverURL);
//             Statement stmt = conn.createStatement()){
//             stmt.execute("CREATE TABLE IF NOT EXISTS login_attempts (" +
//                          "username TEXT, " +
//                          "timestamp INTEGER)");
//             
//             if (isAccountLockedOut(username)) {
//                System.out.println("Account is locked out. Please try again later.");
//                return false;
//            }
//
//            // Check if username and password are valid
//            if (isValidCredentials(username, password)) {
//                System.out.println("Login successful!");
//                return true;
//            } else {
//                System.out.println("Invalid username or password.");
//
//                // Log login attempt
//                logLoginAttempt(username);
//
//                // Check if account should be locked out
//                if (isAccountLockedOut(username)) {
//                    System.out.println("Too many failed login attempts. Account is now locked out for 5 minutes.");
//                    return false;
//                } else {
//                    return false;
//                }
//            }
//        }catch(Exception e){
//            return false;
//        }
//    }
//    
//    private boolean isValidCredentials(String username, String password) throws Exception{
//        try (Connection conn = DriverManager.getConnection(driverURL);
//             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
//            stmt.setString(1, username);
//            stmt.setString(2, password);
//            try (ResultSet rs = stmt.executeQuery()) {
//                return rs.next();
//            }
//        }
//    }
//    
//    private void logLoginAttempt(String username) throws Exception{
//        try (Connection conn = DriverManager.getConnection(driverURL);
//            PreparedStatement stmt = conn.prepareStatement("INSERT INTO login_attempts (username, timestamp) VALUES (?, ?)")) {
//            stmt.setString(1, username);
//            stmt.setLong(2, System.currentTimeMillis());
//            stmt.executeUpdate();
//        }
//    }
//    
//    private boolean isAccountLockedOut(String username) throws Exception{
//        try (Connection conn = DriverManager.getConnection(driverURL);
//             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM login_attempts WHERE username = ? AND timestamp > ?")) {
//            stmt.setString(1, username);
//            stmt.setLong(2, System.currentTimeMillis() - LOCKOUT_TIME);
//            try (ResultSet rs = stmt.executeQuery()) {
//                rs.next();
//                int count = rs.getInt(1);
//                return count >= MAX_ATTEMPTS;
//            }
//        }
//    }
    
}