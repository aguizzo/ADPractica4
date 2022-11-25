package models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

public class ImageServiceREST implements ImageService {
    
    private static final ImageServiceREST instance = new ImageServiceREST();
    private static HttpURLConnection connection;
    private static final String APIURL = "http://localhost:8080/RESTService/"
        + "resources/api/";    
    private final Gson gson = new Gson();
    
    public ImageServiceREST() {
        connection = null;
    }
    
    public static ImageServiceREST getInstance() {
        return instance;
    }

    public boolean deleteImage2(int id, String uploader) 
        throws MalformedURLException, IOException {
        try {
            initPOSTConection("delete");
            String data = "id=" + Integer.toString(id)
                    + "&creator=" + uploader;
            int status = sendForm(data);
            return !(status == 409 || status == 403 || status == -1);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return false;      
        }
        finally {
            closeConnection();
        }
    }

    @Override
    public Image getImage(int id) 
        throws MalformedURLException, IOException {
        try {
            String ID = Integer.toString(id);
            int status = initGETConection("searchID/" + ID);
            if (status == 200) {
                String json = readResponse();
                final Image image = gson.fromJson(json, Image.class); 
                return image;
            }
            return null;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return null;      
        }
        finally {
            closeConnection();
        }
    }

    @Override
    public List<Image> getImageList()
            throws MalformedURLException, IOException {
        try {
            List<Image> list = null;
            int status = initGETConection("list");
            if (status == 200) {
                list = obtainList();
            }
            return list;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        finally {
            closeConnection();
        }
    }

    @Override
    public boolean imageRegister(Image image) 
            throws MalformedURLException, IOException {
        try {
            initPOSTConection("register");
            String data = "title=" + image.getTitle()
                    + "&description=" + image.getDescription()
                    + "&keywords=" + image.getKeywords()
                    + "&author=" + image.getAuthor()
                    + "&creator=" + image.getUploader()
                    + "&capt_date=" + image.getCaptureDate()
                    + "&fileName=" + image.getFileName();
            int status = sendForm(data);
            return !(status == 409 || status == -1);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return false;      
        }
        finally {
            closeConnection();
        }
    }

    @Override
    public boolean modifyImage(Image image)
            throws MalformedURLException, IOException {
        try {
            initPOSTConection("modify");
            String data = "id=" + Integer.toString(image.getId())
                    + "&title=" + image.getTitle()
                    + "&description=" + image.getTitle()
                    + "&keywords=" + image.getKeywords()
                    + "&author=" + image.getAuthor()
                    + "&creator=" + image.getUploader()
                    + "&capt_date=" + image.getCaptureDate();
            int status = sendForm(data);
            return !(status == 409 || status == 403 || status == -1);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return false;      
        }
        finally {
            closeConnection();
        }
    }

    @Override
    public List<Image> searchImages(String title, String keywords,
        String author, String captureDate)
        throws MalformedURLException, IOException {
        try {
            List<Image> list = null;
            int status = initGETConection("combinedSearch?"
                    + "title=" + title
                    + "&keywords=" + keywords
                    + "&author=" + author
                    + "&capt_date=" + captureDate);
            if (status == 200) {
                list = obtainList();
            }
            return list;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        finally {
            closeConnection();
        }

    }
    
    @Override
    public List<Image> searchByTitle(String title)
            throws MalformedURLException, IOException {
        try {
            List<Image> list = null;
            int status = initGETConection("searchTitle/" + title);
            if (status == 200) {
                list = obtainList();
            }
            return list;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        finally {
            closeConnection();
        }
    }
    
        @Override
    public List<Image> searchByStorageDate(String storageDate)
            throws MalformedURLException, IOException {
        try {
            List<Image> list = null;
            int status = initGETConection("searchCreationDate/" + storageDate);
            if (status == 200) {
                list = obtainList();
            }
            return list;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        finally {
            closeConnection();
        } //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Image> searchByAuthor(String author)
           throws MalformedURLException, IOException {
        try {
            List<Image> list = null;
            int status = initGETConection("searchAuthor/" + author);
            if (status == 200) {
                list = obtainList();
            }
            return list;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        finally {
            closeConnection();
        } 
    }

    @Override
    public List<Image> searchByKeywords(String keywords) 
         throws MalformedURLException, IOException {
        try {
            List<Image> list = null;
            int status = initGETConection("searchKeywords/" + keywords);
            if (status == 200) {
                list = obtainList();
            }
            return list;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        finally {
            closeConnection();
        }    
    }
    
    public boolean downloadImage(int id, String filename) {
        try {
            String ID = Integer.toString(id);
            int status = initGETConection("getImage/" + ID);
            InputStream is = connection.getInputStream();
            boolean downloaded = downloadFile(is, filename);
            return downloaded;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        finally {
            closeConnection();
        }  
    }
    
    private String getPath(String fileName) {
        String path = "/home/alumne/images/down/" + fileName;
        return path;
    }
    
    private boolean downloadFile(InputStream is, String fileName)
            throws IOException {
        boolean uploaded = false;
        String path = getPath(fileName);
        FileOutputStream fops = null;
        try {
            int read;
            fops = new FileOutputStream(new File(path));
            final byte[] bytes = new byte[1024];
            
            while((read = is.read(bytes)) != -1) {
                fops.write(bytes, 0, read);
            }
            uploaded = true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fops != null) {
                fops.close();
            }
            if (is != null) {
                is.close();
            }
        }
        return uploaded;
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
    
    private int initGETConection(String resource)
        throws MalformedURLException{
        try {
            URL url = new URL(APIURL + resource);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            return connection.getResponseCode();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return -1;
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
    
    private String readResponse() 
            throws IOException{
        InputStreamReader is = null;
        try {
            is = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(is);
            String json = br.lines().collect(Collectors.joining());
            return json;
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
        finally {
            if (is != null) {
                is.close();
            }
        }
    }
    
    private List<Image> obtainList() 
        throws IOException { 
        String json = readResponse();
        final Type imageList = new TypeToken<List<Image>>(){}.getType();
        List<Image> list = gson.fromJson(json, imageList);
        return list;
    }
    

    @Override
    public boolean deleteImage(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported."); 
        //To change body of generated methods, choose Tools | Templates.
    }

}
