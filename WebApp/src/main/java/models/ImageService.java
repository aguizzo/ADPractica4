package models;

import java.util.List;

public interface ImageService {

    boolean deleteImage(int id) throws Exception;

    Image getImage(int id) throws Exception;

    List<Image> getImageList() throws Exception;

    boolean imageRegister(Image image) throws Exception;

    boolean modifyImage(Image image) throws Exception;

    List<Image> searchImages(String title, String keywords, String author,
            String captureDate) throws Exception;
    
    List<Image> searchByTitle(String title) throws Exception;
    
    List<Image> searchByStorageDate(String storageDate) throws Exception;
    
    List<Image> searchByAuthor(String author) throws Exception;
    
    List<Image> searchByKeywords(String keywords) throws Exception;
    
}
