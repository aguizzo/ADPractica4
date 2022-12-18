package models;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

    
    private String username;
    private String password;
    private String token;
    
    public User(){
    }
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.token = "";
    }
   
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    public String getToken() {
        return token;
    }

    public void setToken() throws NoSuchAlgorithmException {
        this.token = getMd5(this.username+this.password);
    }
    
    public void encryptPassword() throws NoSuchAlgorithmException {
        this.password = getMd5(this.password);
    }
    
    private String getMd5(String pwd) throws NoSuchAlgorithmException {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(pwd.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            String hashtext = bigInt.toString(16);
            while(hashtext.length() < 32 ){
              hashtext = "0"+hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            return pwd;
        }
    }
}
