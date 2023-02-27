
package Controller;
import View.Frame;
import javax.swing.JOptionPane;
public class Secure {
    public Frame frame;
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
    
    public boolean checkIfUsernameisValid(String username){
        if(username.length() < 5){
            return false;
        }
        else{
            return true;
        }
    }
    
    public boolean checkIfPasswordisValid(String password){
        boolean upperCase = false;
        boolean lowerCase = false;
        boolean hasNumber = false;
        boolean specialChar = false;
        
        for(char input : password.toCharArray()){
            if(Character.isUpperCase(input)){
                upperCase = true;
            }
            else if(Character.isLowerCase(input)){
                lowerCase = true;
            }
            else if(Character.isDigit(input)){
                hasNumber = true;
            }
            else{
                switch(input){
                case '!':
                case '@':
                case '#':
                case '$':
                case '%':
                case '^':
                case '&':
                case '*':
                case '(':
                case ')':
                case '-':
                case '+':
                case '=':
                case '{':
                case '}':
                case '[':
                case ']':
                case '|':
                case '\\':
                case '/':
                case '<':
                case '>':
                case '?':
                case ',':
                case '.':
                case ';':
                case ':':
                    specialChar = true;
                    break;
                }
            }
        }
        
        if(password.length() < 12){
            JOptionPane.showMessageDialog(frame, "Password should contain at least 12 characters");
            return false;
        }
        if(upperCase == false){
            JOptionPane.showMessageDialog(frame, "Password should contain at least one uppercase letter");
        }
        if(lowerCase == false){
            JOptionPane.showMessageDialog(frame, "Password should contain at least one lowrcase letter");
        }
        if(hasNumber == false){
            JOptionPane.showMessageDialog(frame, "Password should contain at least one digit");
        }
        if(specialChar == false){
            JOptionPane.showMessageDialog(frame, "Password should contain at least one special character");
        }
        return upperCase && lowerCase && hasNumber && specialChar;
    }
    
    public boolean checkIfLoginBlank(String username, String password){
        boolean checker = false;
        
        if(username.equals("") && password.equals("")){
            checker = false;
        }
        else{
            checker = true;
        }
        return checker;
    }
}
