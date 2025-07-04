package Projectt;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Events {
    private String eventName;
    private double ticketPrice;
    private Rooms room;
    private ArrayList<Attendee> attendees = new ArrayList<>();
    private LocalDateTime dateTime;
    private double totalPrice;
    private String organiserUsername;
    static int attendeesPayed;
    private String categoryName;

    public Events(){
    }

    public Events(String eventName, double ticketPrice, String organiserUsername, LocalDateTime dateTime, String categoryName, ArrayList<Attendee> attendees , Rooms room){
        if (eventName == null) {
            throw new NullPointerException("Event name must not be empty.");
        }

        if (ticketPrice < 0) {
            throw new IllegalArgumentException("Ticket price cannot be negative.");
        }


        if (organiserUsername == null){
            throw new NullPointerException("Project.Organizer name cannot be null." );
        }

        if (dateTime == null) {
            throw new NullPointerException("Event date and time cannot be null.");
        }

        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Event date and time must be in the future.");
        }

        if (categoryName == null) {
            throw new NullPointerException("Category name must not be empty.");
        }
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
        this.organiserUsername = organiserUsername;
        this.dateTime = dateTime;
        this.categoryName= categoryName;
        this.room = room;
        this.attendees = new ArrayList<>(attendees != null ? attendees : new ArrayList<>());


    }

    public Events(String eventName, double ticketPrice, String organiserUsername, LocalDateTime dateTime) {

        if (eventName == null) {
            throw new NullPointerException("Event name must not be empty.");
        }

        if (ticketPrice < 0) {
            throw new IllegalArgumentException("Ticket price cannot be negative.");
        }


        if (organiserUsername == null){
            throw new NullPointerException("Project.Organizer name cannot be null." );
        }

        if (dateTime == null) {
            throw new NullPointerException("Event date and time cannot be null.");
        }

        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Event date and time must be in the future.");
        }


        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
        this.organiserUsername = organiserUsername;
        this.dateTime = dateTime;

    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        if (eventName == null) {
            throw new NullPointerException("Event name must not be empty.");
        }
        this.eventName = eventName;
    }

    public String getCategory(){
        return categoryName;
    }
    public void setCategory(String categoryName){
        this.categoryName = categoryName;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        if (ticketPrice < 0) {
            throw new IllegalArgumentException("Ticket price cannot be negative.");
        }
        this.ticketPrice = ticketPrice;
    }

    public Rooms getRoom() {
        return room;
    }

    public void setRoom(Rooms room) {
        if (room == null) {
            throw new NullPointerException("Room cannot be null.");
        }
        this.room = room;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Event date and time must be in the future.");
        }
        this.dateTime = dateTime;
    }




    public double getTotalPrice() {
        return ticketPrice * getNumberOfAttendees();
    }
    public int getNumberOfAttendees(){

        return attendees.size();
    }


    public String getOrganizerUsername(){
        return organiserUsername;
    }

    public void setOrganizerUsername(String username){
        this.organiserUsername=username;
    }


    public boolean addAttendee(Attendee attendee) {
        if (attendees.size() < room.getRoomCapacity()) {
            attendees.add(attendee);
            attendeesPayed++;
            updateOrganizerBalance();
            return true;
        } else {
            System.out.println("Sorry, event is sold out.");
            return false;
        }
    }

    public boolean removeAttendee(Attendee attendee) {
        if (attendees.remove(attendee)) {
            attendeesPayed--;
            return true;
        }
        return false;
    }

    public ArrayList<Attendee> getAttendees() {
        return attendees;
    }
    public int getEventIndexByName(String eventName){
        int index = 0;
        for (int i = 0; i < Database.events.size(); i++) {
            if (Database.events.get(i).getEventName().equalsIgnoreCase(eventName)) {
                index=i;

            }
        }return index;
    }
    public boolean getAvailability(){
        if(attendees.size()>= room.getRoomCapacity()){
            return false;
        }
        else
            return true;
    }

    private void updateOrganizerBalance() {
        double totalRevenue = ticketPrice * attendees.size();
        Organizer organizer = findOrganizerByUsername(organiserUsername);
        if (organizer != null) {
            organizer.setBalance(organizer.getBalance() + totalRevenue);
        }
    }
    private Organizer findOrganizerByUsername(String username) {
        for (Organizer organizer : Database.organizers) {
            if (organizer.getUsername().equals(username)) {
                return organizer;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Event Information:\n"+
                "Event Name: " + eventName + "\n" +
                "Ticket Price: " + ticketPrice + "\n" +
                "Organiser Name: " + organiserUsername + "\n"+
                "Event Date: " + dateTime + "\n"+
                "Room: " + room.getName() + "\n"+
                "Status:"+ getAvailability();
    }


}







