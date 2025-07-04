package Projectt;

import java.time.LocalDateTime;

public class Rooms {

    private boolean[] availability = new boolean[365];
    private boolean isRoomAvailable = false;
    private int roomCapacity;
    private String roomName;

    public Rooms(){

    }
    public Rooms(String roomName,int roomCapacity){
        this.roomName=roomName;
        this.roomCapacity=roomCapacity;
    }

    public Rooms(boolean[] availability, int roomCapacity,String roomName){
        this.setAvailability(availability);
        this.roomCapacity = roomCapacity;
        this.roomName = roomName;

    }
    public void setAvailability(boolean[] availability) {
        try {
            if (availability == null || availability.length != 365) {
                throw new IllegalArgumentException("Availability array must be 365 days ");
            }
            this.availability = availability;

        } catch (IllegalArgumentException e) {
            System.err.println("Error setting availability: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error occurred while setting availability.");
        }
    }
    public boolean[] getAvailability() {
        return availability;
    }

    public void setRoomCapacity(int roomCapacity) {
        try {

            if (roomCapacity > 0) {
                this.roomCapacity = roomCapacity;
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong input");
        }
    }
    public int getRoomCapacity() {
        return roomCapacity;
    }
    public boolean isRoomAvailable() {
        return isRoomAvailable;
    }

    public void setRoomAvailable(boolean roomAvailable) {
        this.isRoomAvailable = roomAvailable;
    }

    public void checkAvailability(boolean[] availability) {
        boolean available = false;
        for (int i = 0; i < 365; i++) {
            if (availability[i]) {
                available = true;
                System.out.println("Room is available on day: " + i);

            }
        }
        setRoomAvailable(available);

        if (!available) {
            System.out.println("Room is not available on any day this year.");
        }
    }
    public void checkRooms() {

    }
    @Override
    public String toString(){
        return   "Room Name: " + roomName +
                "\nCapacity: " + roomCapacity +
                "\nAvailable Now: " + isRoomAvailable;



    }

    public String getName() {
        if (roomName != null) {
            return roomName;
        }
        else
            return "Invalid output";
    }



    public void setName(String roomName) {
        if (roomName != null) {
            this.roomName = roomName;
        }
        else
        {
            System.out.println("Error : The text is not initialized");
            return;
        }
    }
    public void setFree(int i){
        availability[i]=true;
}
    public boolean isAvailableOn(LocalDateTime date){
        int dayOfYear = date.getDayOfYear() - 1;
        boolean[] availability = getAvailability();
        if (dayOfYear >= 0 && dayOfYear < availability.length) {
            return availability[dayOfYear];
        }
        return false;
    }

    public void bookRoomOn(LocalDateTime date){
        int dayOfYear = date.getDayOfYear() - 1;
        boolean[] availability = getAvailability();
        if (dayOfYear >= 0 && dayOfYear < availability.length) {
            availability[dayOfYear] = false;
        }
    }
}