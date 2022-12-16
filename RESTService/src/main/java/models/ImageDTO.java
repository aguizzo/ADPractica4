package models;

public class ImageDTO {
    
    private boolean operationSucess;
    private Image image;

    public ImageDTO() {
        operationSucess = false;
        image = null;
    }

    public ImageDTO(boolean operationSucess, Image image) {
        this.operationSucess = operationSucess;
        this.image = image;
    }
    
    public boolean isOperationSucess() {
        return operationSucess;
    }

    public void setOperationSucess(boolean operationSucess) {
        this.operationSucess = operationSucess;
    }    

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
