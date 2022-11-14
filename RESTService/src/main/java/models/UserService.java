package models;


public interface UserService {

    public User userLogin(String username, String password)
        throws Exception;

    public boolean userRegister(String username, String password)
        throws Exception;
    
}
