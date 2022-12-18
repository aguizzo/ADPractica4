package models;

public class UserDTO {
    
    private boolean operationSucess;
    private User user;

    public UserDTO() {
        operationSucess = false;
        user = null;
    }

    public UserDTO(boolean operationSucess, User user) {
        this.operationSucess = operationSucess;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public boolean isOperationSucess() {
        return operationSucess;
    }

    public void setOperationSucess(boolean operationSucess) {
        this.operationSucess = operationSucess;
    }

}
