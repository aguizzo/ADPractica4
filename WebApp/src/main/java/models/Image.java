/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author alumne
 */
public class Image {

    private int id;
    private String title;
    private String description;
    private String keywords;
    private String author;
    private String uploader;
    private String captureDate;
    private String storageDate;
    private String fileName;
    
    public Image() {
    }

    public Image(String title, String description, String keywords, 
            String author, String uploader, String captureDate, 
            String storageDate, String fileName) {
        this.title = title;
        this.description = description;
        this.keywords = keywords;
        this.author = author;
        this.uploader = uploader;
        this.captureDate = captureDate;
        this.storageDate = storageDate;
        this.fileName = fileName;
        this.id = -1;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
         
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getCaptureDate() {
        return captureDate;
    }

    public void setCaptureDate(String captureDate) {
        this.captureDate = captureDate;
    }

    public String getStorageDate() {
        return storageDate;
    }

    public void setStorageDate(String storageDate) {
        this.storageDate = storageDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
