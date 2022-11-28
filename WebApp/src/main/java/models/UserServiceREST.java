package models;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;


public class UserServiceREST implements UserService {

    private static final UserServiceREST instance = new UserServiceREST();
    private static HttpURLConnection connection;
    private static final String APIURL = "http://localhost:8080/RESTService/"
        + "resources/api/";
    private final Gson gson = new Gson();
    public UserServiceREST() {
        connection = null;
    }
    
    public static UserServiceREST getInstance() {
        return instance;
    }
    
    @Override
    public User userLogin(String username, String password)
            throws MalformedURLException, IOException {
        InputStreamReader is = null;
        try {
            initPOSTConection("login");
            String data = "username=" + username
                    + "&password=" + password;

            int status = sendForm(data);
            if (status == 404 || status == -1 || status == 500) {
                return null;
            }
            else {
                is = new InputStreamReader(connection.getInputStream());
                BufferedReader br = new BufferedReader(is);
                String json = br.lines().collect(Collectors.joining());
                final User user = gson.fromJson(json, User.class);
                return user;
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        finally {
            if (is != null) {
                is.close();
            }
            closeConnection();
        }
    }

    @Override
    public boolean userRegister(String username, String password)
            throws MalformedURLException, IOException {
        try {
            initPOSTConection("userRegister");
            String data = "username=" + username
                    + "&password=" + password;

            int status = sendForm(data);
            return !(status == 409 || status == -1 || status == 500);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        finally {
            closeConnection();
        }
    }

    private void initPOSTConection(String resource)
        throws MalformedURLException{
        try {
            URL url = new URL(APIURL + resource);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    private void closeConnection() {
        connection.disconnect();
    }
    
    private int sendForm(String data) throws IOException {
        OutputStream os = null;
        try {
            os = connection.getOutputStream();
            os.write(data.getBytes("utf-8"));
            return connection.getResponseCode();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            return -1;
        }
        finally {
            if (os != null) {
                os.close();
            }
        }
    }
    
}
