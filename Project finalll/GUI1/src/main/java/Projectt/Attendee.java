package Projectt;
import com.example.gui1.AdminGui.Birthdate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Scanner;

public class Attendee extends Person {
    Scanner scanner = new Scanner(System.in);

    Wallet wallet=new Wallet();
    private Gender gender;
    private String address;
    private int numOfInterests;
    public static int numOfAttendees;
    //private ArrayList<Category> selectedInterests;
    Birthdate dateOfBirth = new Birthdate();

    private ObservableList<Category> selectedInterests;

    public static ObservableList<Events> registeredEvents = FXCollections.observableArrayList();
    public static ObservableList<Events> observableRegisteredEvents = FXCollections.observableArrayList(registeredEvents);

    public Attendee() { //this.selectedInterests = new ArrayList<>();
        this.selectedInterests = FXCollections.observableArrayList();
    }
    public void addSelectedCategory(Category category) {
        selectedInterests.add(category);
    }
    //  public ArrayList<Category> getSelectedInterests() {
    //    return selectedInterests;
    //}

    public boolean checkInterests(Category category){
        for(Category interest: selectedInterests)
        if(interest.equals(category)){
            return false;
        }
        return true;
    }

    public ObservableList<Category> getSelectedInterests() {
        return selectedInterests;
    }

    public Attendee(String username, String password) {
        super(username, password);
        //this.selectedInterests = new ArrayList<>();
        this.selectedInterests = FXCollections.observableArrayList();
    }

    public Attendee(String username,String password,Gender gender){
        super(username, password);
        this.gender = gender;
    }

    public Attendee(String username, String password, String dateOfBirth, Gender gender, String address) {
        super(username, password);
        this.dateOfBirth.setDateofbirth(dateOfBirth);
        this.address = address;
        this.gender = gender;
        numOfAttendees++;
        //this.selectedInterests = new ArrayList<>();
        this.selectedInterests = FXCollections.observableArrayList();
    }

    public Attendee(String username, String password, String dateOfBirth, Gender gender, String address, double balance) {
        super(username, password);
        this.dateOfBirth.setDateofbirth(dateOfBirth);
        this.address = address;
        this.gender = gender;
        this.selectedInterests = FXCollections.observableArrayList();
        numOfAttendees++;
    }


    public void setBalance(double amount){
        wallet.setBalance(amount);
    }

    public double getBalance(){
        return wallet.getBalance();
    }

    public void depositMoney(double amount){
        wallet.depositMoney(amount);
    }

    public void setDateOfBirth(String date) {
        dateOfBirth.setDateofbirth(date);
    }
    public String getDateOfBirth() {
        return dateOfBirth.getDateofbirth();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setNumOfInterests(int numOfInterests) {
        this.numOfInterests = numOfInterests;
    }

    public int getNumOfInterests() {
        return numOfInterests;
    }


    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public String toString() {
        return "hi";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Attendee)) return false;
        Attendee other = (Attendee) obj;
        return super.getUsername() != null && other.getUsername() != null && super.getUsername().equals(other.getUsername());
    }

    @Override
    public boolean passwordCheck(String password) {
        boolean ucase = false, lcase = false, specialcharacter = false, digit = false;

        if (password == null) {
            return false;
        }

        if (password.isBlank()) {
            return false;
        }

        if (password.length() < 8) {
            return false;
        }

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) ucase = true;
            else if (Character.isLowerCase(ch)) lcase = true;
            if (ch == ' ') {
                return false;
            }
            if (!Character.isLetterOrDigit(ch)) specialcharacter = true;
            if (Character.isDigit(ch)) digit = true;
        }

        if (!ucase || !lcase || !specialcharacter || !digit) {
            return false;
        }
        return true;
    }

    public static void updateObservableList() {
        observableRegisteredEvents.setAll(registeredEvents);
    }
}

