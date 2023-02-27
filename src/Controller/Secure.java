
package Controller;

public class Secure {
    
    public Secure (){
        javax.swing.JTextField test = new javax.swing.JTextField();
        test.setText("");
        
    }
    public void setClearTextField(javax.swing.JTextField fld){
        fld.setText("");
    }
    public boolean registerCheckPassEqual(String password, String conf_password){
        if(password.equals(conf_password)){
            return true;
        }
        else{
            return false;
        }
       
    }
    public boolean checkLoginIfFilled(String username, String password){
        if(username.equals("") && password.equals("")){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean checkRegisterIfFilled(String username, String password, String conf_password){
        if(username.equals("") || password.equals("") || conf_password.equals("")){
            return false;
        }
        else{
            return true;
        }
    }
    
}
