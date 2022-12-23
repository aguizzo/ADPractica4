package models;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserServiceDB implements UserService {
    
    private Connection connection;
    private static final UserServiceDB instance = new UserServiceDB();
    
    public UserServiceDB() {
        connection = null;
    }
    
    public static UserServiceDB getInstance() {
        return instance;
    }

    @Override
    public User userLogin(String username, String password) 
            throws  IOException, SQLException, NoSuchAlgorithmException{
        try {
            String query;
            PreparedStatement statement;
            initConnection();
            User us = null;
            
            query = "select * from users " +
                        "where username= ? and password= ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();    
         
            if (!rs.next()) {
                return us;
            }
            us = new User(username , password);
            us.setToken();
            String token = us.getToken();
            
            query = "update users " +
                    "set token=? " +
                    "where username=?";
            statement = connection.prepareStatement(query);
            statement.setString(1, token);
            statement.setString(2, username);
            int result = statement.executeUpdate(); 
            
            us.encryptPassword();
            return us;
        }
        catch(Exception e) {
            return null;
        }
        finally {
            closeConnection();
        }
    }
    
    @Override
    public boolean userRegister(String username, String password) 
            throws  IOException, SQLException {
        try {
            String query;
            PreparedStatement statement;
            initConnection();

            query = "insert into users values(?,?, ?)";
            statement = connection.prepareStatement(query);    
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, "");
            int result = statement.executeUpdate();   
         
            return !(result == 0);
        }
        catch(Exception e) {
            return false;
        }
        finally {
            closeConnection();
        }
    }
    
    public UserDTO userRegister2(String username, String password)
            throws  IOException, SQLException, NoSuchAlgorithmException {
        try {
            boolean registered = userRegister(username, password);
            User us = null;
            if(registered) {
                us = getUser(username);
            }
            UserDTO dto = new UserDTO(registered, us);
            return dto;
        }
        catch(SQLException e) {
            return new UserDTO();
        }
        finally {
            closeConnection();
        }
    }
    
    public boolean checkOwenership(String token, String username) 
            throws  IOException, SQLException{
        try {
            String query;
            PreparedStatement statement;
            initConnection();

            query = "select * from users " +
                        "where username=? AND token= ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, token);

            ResultSet rs = statement.executeQuery();    
            return rs.next();
        }
        catch(Exception e) {
            return false;
        }
        finally {
            closeConnection();
        }
    }
    
    public String getAuthenticacitonInfo(String token) 
            throws  IOException, SQLException{
        try {
            String query;
            PreparedStatement statement;
            initConnection();

            query = "select * from users " +
                        "where token= ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, token);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) { 
                String username = rs.getString("username");
                if (username.isEmpty())
                    return null;
                return username;
            }
            else
                return null;
        }
        catch(Exception e) {
            return null;
        }
        finally {
            closeConnection();
        }
    }
    
    private void initConnection() throws IOException {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    private void closeConnection() throws IOException {
        try {
            if (connection != null) {
                connection.close();
            }
        } 
        catch (Exception e) {
            System.err.println(e.getMessage());
         }
    }

    private User getUser(String username)
         throws  IOException, SQLException, NoSuchAlgorithmException{
        try {
            User us = null;
            String query;
            PreparedStatement statement;
            initConnection();

            query = "select * from users "
                    + "where username=?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                String un = rs.getString("username");
                String pw = rs.getString("password");
                us = new User(un, pw);
                us.encryptPassword();
            }
            return us;
        }
        catch(SQLException e) {
            return null;
        }
        finally {
            closeConnection();
        }
        
    }
}
