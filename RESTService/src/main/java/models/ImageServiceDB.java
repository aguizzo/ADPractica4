package models;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ImageServiceDB implements ImageService {
    
    private Connection connection;
    private static final ImageServiceDB instance = new ImageServiceDB();
    
    public ImageServiceDB() {
        connection = null;
    }
    
    public static ImageServiceDB getInstance() {
        return instance;
    }
    
    @Override
    public boolean imageRegister(Image image) 
            throws  IOException, SQLException {
        try {
            String query;
            PreparedStatement statement;
            initConnection();

            query = "insert into images "
                    + "(TITLE, DESCRIPTION, KEYWORDS, AUTHOR, UPLOADER, "
                    + "CAPTURE_DATE, STORAGE_DATE, FILENAME) "
                    + "values(?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(query);    
            statement.setString(1, image.getTitle());
            statement.setString(2, image.getDescription());
            statement.setString(3, image.getKeywords());
            statement.setString(4, image.getAuthor());
            statement.setString(5, image.getUploader());
            statement.setString(6, image.getCaptureDate()); 
            statement.setString(7, getDate());
            statement.setString(8, image.getFileName());
                        
            int result = statement.executeUpdate();   
            return !(result == 0);
        }
        catch(SQLException e) {
            return false;
        }
        finally {
            closeConnection();
        }
    }
    
    @Override
    public boolean deleteImage(int id)
            throws  IOException, SQLException {
        try {
            String query;
            PreparedStatement statement;
            initConnection();

            query = "delete from IMAGES where ID = ?";
            statement = connection.prepareStatement(query);    
            statement.setInt(1,id);            
            int result = statement.executeUpdate();   
            return !(result == 0);
        }
        catch(SQLException e) {
            return false;
        }
        finally {
            closeConnection();
        }
    }
    
    @Override
    public boolean modifyImage(Image image)
            throws  IOException, SQLException {
        try {
            String query;
            PreparedStatement statement;
            initConnection();

            query = "update IMAGES "
                    + "set"
                    + " TITLE = ?, DESCRIPTION = ?, KEYWORDS = ?,"
                    + " AUTHOR = ?, CAPTURE_DATE = ?"
                    + " where ID = ?";
            statement = connection.prepareStatement(query);    
            statement.setString(1, image.getTitle());
            statement.setString(2, image.getDescription());
            statement.setString(3, image.getKeywords());
            statement.setString(4, image.getAuthor());
            statement.setString(5, image.getCaptureDate());
            statement.setInt(6, image.getId());            
            int result = statement.executeUpdate();   
            return !(result == 0);
        }
        catch(SQLException e) {
            return false;
        }
        finally {
            closeConnection();
        }
    }
    
    @Override
    public Image getImage(int id)
        throws  IOException, SQLException{
        try {
            Image im = null;
            String query;
            PreparedStatement statement;
            initConnection();

            query = "select * from images "
                    + "where id=?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                im = createImage(rs);
            }
            return im;
        }
        catch(SQLException e) {
            return null;
        }
        finally {
            closeConnection();
        }
    }
    
    @Override
    public List<Image> getImageList() 
            throws  IOException, SQLException {
        try {
            List<Image> list = new ArrayList<>();
            String query;
            PreparedStatement statement;
            initConnection();

            query = "select * from images"
                + " order by storage_date DESC";
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()){
                Image im = createImage(rs);
                list.add(im);
            }
            return list;
        }
        catch(SQLException e) {
            return null;
        }
        finally {
            closeConnection();
        }
    }
    
    @Override
    public List<Image> searchImages(String title, String keywords,
            String author, String captureDate)
            throws  IOException, SQLException {
        try {
            List<Image> list = new ArrayList<>();
            String query;
            title = title.toLowerCase();
            keywords = keywords.toLowerCase();
            author = author.toLowerCase();
            PreparedStatement statement;
            initConnection();

            query = "select * from images where"
                + " LOWER(title) LIKE ?"
                + " AND LOWER(keywords) LIKE ?"
                + " AND LOWER(author) LIKE ?"
                + " AND capture_date >= ?"
                + " order by storage_date DESC";    
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + title + "%");
            statement.setString(2, "%" + keywords + "%");
            statement.setString(3, "%" + author + "%");
            statement.setString(4, captureDate);
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()){
                Image im = createImage(rs);
                list.add(im);
            }
            return list;
        }
        catch(SQLException e) {
            return null;
        }
        finally {
            closeConnection();
        }
    }
    
    @Override
    public List<Image> searchByTitle(String title) 
            throws  IOException, SQLException  {
        try {
            List<Image> list = new ArrayList<>();
            String query;
            title = title.toLowerCase();
            PreparedStatement statement;
            initConnection();

            query = "select * from images where"
                + " LOWER(title) LIKE ?"
                + " order by storage_date DESC";    
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + title + "%");
            
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()){
                Image im = createImage(rs);
                list.add(im);
            }
            return list;
        }
        catch(SQLException e) {
            return null;
        }
        finally {
            closeConnection();
        }
    }

    @Override
    public List<Image> searchByStorageDate(String storageDate)
            throws  IOException, SQLException  {
        try {
            List<Image> list = new ArrayList<>();
            String query;
            PreparedStatement statement;
            initConnection();

            query = "select * from images "
                    + "where STORAGE_DATE like ? ";
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + storageDate + "%");
            
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()){
                Image im = createImage(rs);
                list.add(im);
            }
            return list;
        }
        catch(SQLException e) {
            return null;
        }
        finally {
            closeConnection();
        }
    }

    @Override
    public List<Image> searchByAuthor(String author)
            throws  IOException, SQLException  {
        try {
            List<Image> list = new ArrayList<>();
            String query;
            author = author.toLowerCase();
            PreparedStatement statement;
            initConnection();

            query = "select * from images where"
                + " LOWER(author) LIKE ?"
                + " order by storage_date DESC";    
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + author + "%");
            
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()){
                Image im = createImage(rs);
                list.add(im);
            }
            return list;
        }
        catch(SQLException e) {
            return null;
        }
        finally {
            closeConnection();
        }
        
    }

    @Override
    public List<Image> searchByKeywords(String keywords)
            throws  IOException, SQLException  {
        try {
            List<Image> list = new ArrayList<>();
            String query;
            keywords = keywords.toLowerCase();
            PreparedStatement statement;
            initConnection();

            query = "select * from images where"
                + " LOWER(keywords) LIKE ?"
                + " order by storage_date DESC";    
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + keywords + "%");
            
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()){
                Image im = createImage(rs);
                list.add(im);
            }
            return list;
        }
        catch(SQLException e) {
            return null;
        }
        finally {
            closeConnection();
        }
    }
    
    @Override
    public boolean checkOwnership(int id, String uploader)
        throws  IOException, SQLException{
        try {
            Image im = null;
            String query;
            PreparedStatement statement;
            initConnection();

            query = "select uploader from images "
                    + "where id=?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            
            String owner = "";
            if (rs.next()) {
                owner = rs.getString("uploader");
            }
            return uploader.equals(owner);
        }
        catch(SQLException e) {
            return false;
        }
        finally {
            closeConnection();
        }
    }

    private Image createImage(ResultSet rs)
            throws SQLException {
        try {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            String keywords = rs.getString("keywords");
            String author = rs.getString("author");
            String uploader = rs.getString("uploader");
            String captureDate = rs.getString("capture_date");
            String storageDate = rs.getString("storage_date");
            String fileName = rs.getString("filename");

            Image im = new Image(title, description, keywords, author,
                uploader, captureDate, storageDate, fileName);
            im.setId(id);
            return im;
        }
        catch (SQLException e) {
            return null;
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
    
    private String getDate() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDate = new SimpleDateFormat(pattern);
        return simpleDate.format(new Date());
    }

}
