package com.example.gui1.AdminGui;
import java.util.Scanner;

public class Birthdate {
    Scanner scanner = new Scanner(System.in);
    String dateofbirth="";

    //Birthdate getter and setter
    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }
    public String getDateofbirth() {
        if (dateofbirth != null) {
            return dateofbirth;
        }
        else
            return "Invalid output❌. Birthdate value is null";
    }

    public void editDateofbirth()
    {
        int counter = 0;
        String dateofbirth;
        do {
            counter++;
            if(counter == 5)
            {
                System.out.println("Too many attempts,enter exit to stop trying.");
                if(scanner.nextLine().equalsIgnoreCase("exit"))
                {
                    return;
                }
                counter = 0;
            }
            System.out.print("Enter your birthdate in the format dd/mm/yyyy. eg(03/04/2006) : ");
            dateofbirth = scanner.nextLine();
        }while (!dateFormat(dateofbirth));
        this.dateofbirth = dateofbirth;
        System.out.println("You have changed your birthdate successfully✅");
    }

    public boolean dateFormat(String dateofbirth) {
        StringBuilder temp = new StringBuilder();
        if (dateofbirth.length() != 10)
        {
            System.out.println("Invalid format❌.");
            return false;
        }
        if (dateofbirth.charAt(2) != '/' || dateofbirth.charAt(5) != '/') {
            System.out.println("Invalid format❌.");
            return false;
        }

        for (int i = 0; i < dateofbirth.length();i++)
        {
            if (dateofbirth.charAt(i) != '/')
            {
                if (!Character.isDigit(dateofbirth.charAt(i))) {
                    System.out.println("The date must contain only digits❗❗");
                    return false;
                }
            }
        }

        try {
            temp.append(dateofbirth.substring(0, 2));
        } catch (NumberFormatException e) {
            System.out.println("Error⛔ : Date contains invalid characters.");
            return false;
        }
        if (Integer.parseInt(temp.toString()) < 1 || Integer.parseInt(temp.toString()) > 31)
        {
            System.out.println("Invalid day entered❌");
            return false;
        }
        temp.setLength(0);
        try {
            temp.append(dateofbirth.substring(3, 5));
        } catch (NumberFormatException e) {
            System.out.println("Error⛔ : Date contains invalid characters.");
            return false;
        }
        if (Integer.parseInt(temp.toString()) < 1 || Integer.parseInt(temp.toString()) > 12)
        {
            System.out.println("Invalid month entered❌");
            return false;
        }
        temp.setLength(0);
        try {
            temp.append(dateofbirth.substring(6, 10));
        } catch (NumberFormatException e) {
            System.out.println("Error⛔ : Date contains invalid characters.");
            return false;
        }
        if (Integer.parseInt(temp.toString()) < 1920)
        {
            System.out.println("Invalid year entered❌");
            return false;
        }
        if (Integer.parseInt(temp.toString()) > 2007){
            System.out.println("You have to be at least 18 years old⛔.");
            return false;
        }
        return true;
    }

}

