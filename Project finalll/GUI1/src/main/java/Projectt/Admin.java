package Projectt;
import com.example.gui1.AdminGui.Birthdate;

import java.util.ArrayList;
import java.util.Collections;

public class Admin extends Person{
    //attributes
    private String role;
    private Gender gender;
    private boolean[] workinghours = new boolean[24];
    private int workinghourscount;
    Birthdate birthdate = new Birthdate();


    //Constructors
    public Admin(){}
    public Admin(String dateofbirth,String role,boolean[] workinghours,String username,String password,Gender gender)
    {
        super(username,password);
        birthdate.setDateofbirth(dateofbirth);
        this.workinghours=workinghours;
        for (int i = 0; i < workinghours.length;i++){
            if (workinghours[i]){
                workinghourscount++;
            }
        }
        this.role = role;
        this.gender=gender;
    }



    //Overriden methods
    @Override
    public String toString() {
        StringBuilder string;
        string = new StringBuilder("Admin username: " + super.getUsername() + "\nRole: " + role + "\nBirthdate: " + birthdate.getDateofbirth() + "\nWorking hours: ");
        for (int i = 0; i < 24; i ++)
        {
            if (workinghours[i])
            {
                if (i <10)
                    string.append("0").append(i).append(":00 ");
                else
                    string.append(i).append(":00 ");
            }
        }
        string.append("\nTotal work hours: ").append(workinghourscount);
        return string.toString();
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (!(obj instanceof Admin))
            return false;
        Admin other = (Admin) obj;
        if (super.getUsername() != null && other.getUsername() != null) {
            return super.getUsername().equals(other.getUsername());
        }
        else
        {
            System.out.println("Error⛔ : The username value null.");
            return false;
        }
    }



    //Role setter and getter
    public void setRole(String text) {
        role=text;
    }
    public String getRole() {
        if (role != null) {
            return role;
        }
        else
            return "Invalid output❌. Role value is null";
    }

    //Working hours setter and getter
    public void setWorkinghoursarray(boolean[] hours){
        this.workinghours = hours;
    }
    public boolean[] getWorkinghours() {
        return workinghours;
    }

    //Birthdate setter and getter
    public void setBirthdate(String date) {
        birthdate.setDateofbirth(date);
    }
    public String getBirthdate() {
        return birthdate.getDateofbirth();
    }

    //Gender setter and getter
    public Gender getGender(){
        return this.gender;
    }
    public void setGender(Gender gender){
        this.gender = gender;
    }




    //Category management
    public boolean addCategory(String name, String description) {
        for(Category cat : Database.categories){
            if(cat.getName().equalsIgnoreCase(name)){
                return false;
            }
        }

        Database.categories.add(new Category(name,description));
        return true;
    }
    public boolean deleteCategory(String name){
        boolean catfound = false;
        ArrayList<Integer> indexes = new ArrayList<>();for (Category cat : Database.categories) {
            if (cat.getName().equalsIgnoreCase(name)) {
                catfound = true;
                Database.categories.remove(cat);
                break;
            }
        }
        if (catfound) {
            for (int i = 0; i < Database.events.size(); i++){
                if(Database.events.get(i).getCategory().equalsIgnoreCase(name)){
                    indexes.add(i);
                    refund(i);
                }
            }
        }
        indexes.sort(Collections.reverseOrder());
        for(int i : indexes){
            setRoomFree(i);
            Database.events.remove(i);
        }
        return catfound;
    }
    public String[][] showCategories(){
        String[][] cats = new String[Database.categories.size()][];
        for (int i = 0;i<cats.length;i++){
            cats[i] = new String[2];
        }
        int count = 0;
        for(Category cat : Database.categories){
            cats[count][0] = cat.getName();
            cats[count][1] = cat.getDescription();
            count++;

        }
        return cats;
    }

    //Organizer management
    public String[] showOrganizers(){
        String[] orgs = new String[Database.organizers.size()];
        int count = 0;
        for(Organizer org : Database.organizers){
            orgs[count] = org.getUsername();
            count++;
        }
        return orgs;
    }
    public boolean deleteOrganizer(String username) {
        Organizer other = null;
        boolean orgfound = false;
        ArrayList<Integer> indexes = new ArrayList<>();
        for (Organizer org : Database.organizers) {
            if (org.getUsername().equalsIgnoreCase(username)) {
                orgfound = true;
                other = org;
                break;
            }
        }
        if (orgfound) {
            Database.organizers.remove(other);
            for (int i = 0; i < Database.events.size();i++){
                if(Database.events.get(i).getOrganizerUsername().equalsIgnoreCase(username)){
                    indexes.add(i);
                    refund(i);
                }
            }
        }
        indexes.sort(Collections.reverseOrder());
        for(int i : indexes){
            Database.events.remove(i);
        }
        return orgfound;
    }
    public boolean addOrganizer(String username) {
        for (Organizer org : Database.organizers) {
            if (org.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }
        return true;
    }

    //Event management
    public String[] showEvents(){
        String[] events = new String[Database.events.size()];
        int count = 0;
        for(Events event : Database.events){
            events[count] = event.getEventName();
            count++;
        }
        return events;
    }
    public boolean deleteEvent(String name){
        for(int i = 0; i < Database.events.size();i++){
            if(Database.events.get(i).getEventName().equalsIgnoreCase(name)){
                refund(i);
                setRoomFree(i);
                Database.events.remove(i);
                return true;
            }
        }
        return false;
    }

    //Attendee management
    public String[] showAttendees(){
        String[] atts = new String[Database.attendees.size()];
        int count = 0;
        for(Attendee att : Database.attendees){
            atts[count] = att.getUsername();
            count++;
        }
        return atts;
    }
    public boolean deleteAttendee(String username){
        Attendee other = null;
        for (Attendee att : Database.attendees) {
            if (att.getUsername().equalsIgnoreCase(username)) {
                Database.attendees.remove(att);
                System.out.println("Attendee found✅");
                return true;
            }
        }
        System.out.println("Attendee is not found❌");
        return false;
    }
    public boolean addAttendee(String username,String password,int genderChoice){
        Gender newGender = null;
        for (Attendee att : Database.attendees) {
            if (att.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }
        if(genderChoice ==1){
            newGender = Gender.MALE;
        }
        else
            newGender = Gender.FEMALE;
        Database.attendees.add(new Attendee(username,password,newGender));
        return true;
    }

    //Room management
    public boolean addRoom(String roomName,int maxCapacity){
        if(maxCapacity<= 0){
            return false;
        }
        if(isRoomUnique(roomName)){
            Database.rooms.add(new Rooms(roomName,maxCapacity));
            return true;
        }
        return false;
    }
    public boolean deleteRoom(String roomName) {
        boolean check = false;
        ArrayList<Integer> indexes = new ArrayList<>();
        for (Rooms room : Database.rooms) {
            if (room.getName().equalsIgnoreCase(roomName)) {
                for (int i = 0; i < Database.events.size(); i++) {
                    if (Database.events.get(i).getRoom().getName().equalsIgnoreCase(roomName)) {
                        refund(i);
                        indexes.add(i);
                    }
                }
                Database.rooms.remove(room);
                check = true;
                break;
            }
        }
        indexes.sort(Collections.reverseOrder());
        for(int i : indexes){
            Database.events.remove(i);
        }
        return check;
    }
    public String[][] showRooms(){
        String[][] rooms = new String[Database.rooms.size()][];
        for (int i = 0;i<rooms.length;i++){
            rooms[i] = new String[2];
        }
        int count = 0;
        for(Rooms room : Database.rooms){
            rooms[count][0] = room.getName();
            rooms[count][1] = Integer.toString(room.getRoomCapacity());
            count++;

        }
        return rooms;
    }




    //Helper methods
    private void setRoomFree(int i){
        for(Rooms room : Database.rooms) {
            if (room.getName().equalsIgnoreCase(Database.events.get(i).getRoom().getName())) {
                room.setFree(Database.events.get(i).getDateTime().getDayOfYear());
            }
            break;
        }
    }
    private void refund(int i){
        for(Attendee att : Database.attendees){
            for(Attendee attEvent : Database.events.get(i).getAttendees()){
                if(att.getUsername().equalsIgnoreCase(attEvent.getUsername())){
                    att.setBalance(att.getBalance()+Database.events.get(i).getTicketPrice());
                    for(Organizer org : Database.organizers){
                        if(org.getUsername().equalsIgnoreCase(Database.events.get(i).getOrganizerUsername())){
                            org.setBalance(org.getBalance()-Database.events.get(i).getTicketPrice());
                            break;
                        }
                    }
                }
            }
        }
    }

    //Helper methods in other classes
    public boolean isRoomUnique(String roomName){
        for (Rooms room : Database.rooms){
            if (room.getName().equalsIgnoreCase(roomName))
                return false;
        }
        return true;
    }
    public String eventMatchesCategory(String eventName){
        for(Events event : Database.events){
            if (eventName.equalsIgnoreCase(event.getEventName())){
                return event.getCategory();
            }
        }
        return "";
    }
    public Events getEventObject(String name){
        for (Events event : Database.events){
            if (event.getEventName().equalsIgnoreCase(name)){
                return event;
            }
        }
        return null;
    }
    public boolean dateformat(String dateOfBirth){
        return birthdate.dateFormat(dateOfBirth);
    }
}