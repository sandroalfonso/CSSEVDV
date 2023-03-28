package Model;


public class Control {
    private static Control controls;
    private User user = new User();
    private boolean isLocked = false;
    
    private Control(User user) {
        this.user = user;
        this.isLocked = true ? user.getLocked() == 1 : false;
    }
    
    public static Control getInstance() {
        return controls;
    }
    
    public int getRole() {
        return user.getRole();
    }
    
    public String getName() {
        return user.getUsername();
    }
    
    public boolean userIsLocked() {
        return isLocked;
    }
    
    public static Control loginUser(User user) {
        if(controls == null) {
            controls = new Control(user);
        } else {
            controls.user = user;
        }
        return controls;
    }
    
    public void removeUser() {
        this.user = null;
    }
    
}
