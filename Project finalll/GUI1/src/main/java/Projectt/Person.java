package Projectt;

import java.util.Scanner;

public abstract class Person {

    protected final Scanner scanner = new Scanner(System.in);

    //attributes
    private String username;
    private String password;
    //Constructors
    public Person(){}
    public Person(String username,String password)
    {this.username = username;
        this.password = password;}

    //Username setter and getter
    /* To input a new username from the user,use username check and iterate over the
    admin/organiser/attendee array and make sure that it never returns false because if so,
    then the username is taken by another user */
    public void setUsername(String username)
    {
        if (username != null) {
            this.username = username;
            System.out.println("You have set your username successfully✅ ");
        }
    }
    public String getUsername()
    {
        if (username != null) {
            return username;
        }
        else
            return "Invalid output❌. Username value is null";
    }

    //Password setter and getter
    public void setPassword(String password)
    {
        if (passwordCheck(password))
        {
            this.password = password;
        }
    }
    public String getPassword()
    {

        if (password != null) {
            return password;
        }
        else
            return "Invalid output❌. Password value is null";
    }

    @Override
    public abstract String toString();
    @Override
    public abstract boolean equals(Object obj);

    public boolean login(String username,String password)
    {
        if (this.username == null || this.password == null)
        {
            System.out.println("Error⛔ : The username or password is null.");
            return false;
        }
        if (!(this.username.equals(username) && this.password.equals(password))) {
            return false;
            //In main check valid, if found true break from the loop and log in
            //Else, if found false at the end of the while loop, output this :
            // System.out.println("Error❗ : Your username or password is incorrect.");
        }
        else {
            System.out.println("You have logged in successfully✅");
            return true;
        }
    }

    //This could be used also to input password as a new user
    public boolean editPassword(String newPassword) {
        if (passwordCheck(newPassword)) {
            this.password = newPassword;
            return true;
        }
        return false;
    }

    public String getPasswordErrorMessage() {
        return passwordErrorMessage;
    }

    private String passwordErrorMessage = " ";

    //Password validation
    public boolean passwordCheck(String password) {
        boolean ucase = false, lcase = false, specialcharacter = false, digit = false;

        if (password == null) {
            passwordErrorMessage = "❗Password can not be null❗";
            return false;
        }

        if (password.isBlank()) {
            passwordErrorMessage = "❗Password can not be empty❗";
            return false;
        }

        if (password.length() < 8) {
            passwordErrorMessage = "❗The password must be 8 characters or more❗";
            return false;
        }

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) ucase = true;
            else if (Character.isLowerCase(ch)) lcase = true;
            if (ch == ' ') {
                passwordErrorMessage = "❗The password can not contain spaces❗";
                return false;
            }
            if (!Character.isLetterOrDigit(ch)) specialcharacter = true;
            if (Character.isDigit(ch)) digit = true;
        }

        if (!ucase || !lcase || !specialcharacter || !digit) {
            passwordErrorMessage = "❗The password must contain a digit, lower case, upper case, and a special character❗";
            return false;
        }

        passwordErrorMessage = "You have set your password successfully✅ ";
        this.password = password;
        return true;
    }



    public boolean usernameCheck(String username){
        if(username == null)
        {
            System.out.println("Error⛔ : Username can not be null❗");
            return false;
        }
        if (username.length() < 4 || username.length() > 20)
        {
            System.out.println("The username must be 4-20 characters long❗");
            return false;
        }
        for (int i = 0; i < username.length(); i++)
        {
            if (username.charAt(i) ==' ')
            {
                System.out.println("The username can not contain spaces❗");
                return false;
            }
        }
        return true;

    }

}