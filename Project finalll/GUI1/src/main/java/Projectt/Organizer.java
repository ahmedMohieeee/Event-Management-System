package Projectt;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Organizer extends Person {
    private Events[] eventsOrganized;
    private int numberOfEvents;
    private Wallet wallet =new Wallet();

    public Organizer() {
        this.eventsOrganized =new Events[10];
        this.numberOfEvents = 0;
    }

    public Organizer(String username, String password) {
        super(username, password);
        this.eventsOrganized = new Events[10];
        this.numberOfEvents = 0;
    }

    public Organizer(String username, String password, int maxNumOfEvents) {
        super(username, password);
        if (username == null || password == null) {
            throw new IllegalArgumentException("Incomplete data for organizer");
        }
        this.eventsOrganized = new Events[maxNumOfEvents];
        this.numberOfEvents = 0;
        wallet.setBalance(0);
    }
    public Organizer(String username, String password, int maxNumOfEvents,double balance) {
        super(username, password);
        if (username == null || password == null) {
            throw new IllegalArgumentException("Incomplete data for organizer");
        }
        this.eventsOrganized = new Events[maxNumOfEvents];
        this.numberOfEvents = 0;
        wallet.setBalance(balance);
    }

    public Events createEvent(String name, double ticketPrice, LocalDateTime dateTime, String roomName) {
        Rooms room = findRoomByName(roomName);
        if (room == null || !room.isAvailableOn(dateTime)) {
            return null;
        }
        if (numberOfEvents >= eventsOrganized.length) {
            return null;
        }
        Events event = new Events(name, ticketPrice, getUsername(), dateTime);
        event.setRoom(room);
        room.bookRoomOn(dateTime);
        Database.events.add(event);
        eventsOrganized[numberOfEvents++] = event;
        return event;
    }

    public boolean deleteEventByName(String eventName) {
        for (int i = 0; i < Database.events.size(); i++) {
            Events event = Database.events.get(i);
            if (event.getEventName().equalsIgnoreCase(eventName) &&
                    event.getOrganizerUsername().equals(this.getUsername())) {

                for (int j = 0; j < numberOfEvents; j++) {
                    if (eventsOrganized[j] != null &&
                            eventsOrganized[j].getEventName().equalsIgnoreCase(eventName)) {

                        for (int k = j; k < numberOfEvents - 1; k++) {
                            eventsOrganized[k] = eventsOrganized[k + 1];
                        }
                        eventsOrganized[--numberOfEvents] = null;
                        break;
                    }
                }
                Database.events.remove(event);
                return true;
            }
        }
        return false;
    }

    public List<String> showCategories() {
        List<String> categoryList = new ArrayList<>();
        for (Category categories : Database.categories) {
            categoryList.add(categories.getName());
        }
        return categoryList;
    }

    public List<String> showAvailableRooms(LocalDateTime dateTime) {
        List<String> availableRooms = new ArrayList<>();
        for (Rooms room : Database.rooms) {
            if (room.isAvailableOn(dateTime))
                availableRooms.add(room.getName());
        }
        return availableRooms;
    }

    public List<String> showEvents() {
        List<String> eventList = new ArrayList<>();
        for (Events event : Database.events) {
            if (event.getOrganizerUsername().equals(this.getUsername())) {
                eventList.add(event.getEventName());
            }
        }
        return eventList;
    }

    public List<String> showPaidAttendees(String eventName) {
        List<String> attendeesPaid = new ArrayList<>();
        for (Events event : Database.events) {
            if (event.getEventName().equalsIgnoreCase(eventName)) {
                List<Attendee> eventAttendees = event.getAttendees();
                for (Attendee attendee : eventAttendees) {
                    if (attendee.getBalance() >= event.getTicketPrice()) {
                        attendeesPaid.add(attendee.getUsername());
                    }
                }
                return attendeesPaid;
            }
        }
        return null;
    }

    public Rooms findRoomByName(String roomName) {
        for (Rooms room : Database.rooms) {
            if (room.getName().equals(roomName)) {
                return room;
            }
        }
        System.out.println("Room not found.");
        return null;
    }

    @Override
    public String toString() {
        StringBuilder string;
        string = new StringBuilder("Organizer username: " + super.getUsername() +
                "\nNumber of events organized: " + numberOfEvents +
                "\nEvents organized: ");
        for (Events event : eventsOrganized) {
            string.append(event).append(", ");
        }
        return string.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Organizer))
            return false;
        Organizer other = (Organizer) obj;
        return super.getUsername() != null && super.getUsername().equals(other.getUsername());
    }

    public Events[] getEventsOrganized() {
        return eventsOrganized;
    }

    public void setEventsOrganized(Events[] eventsOrganized) {
        this.eventsOrganized = eventsOrganized;
    }

    public int getNumberOfEvents() {
        int count = 0;
        for (Events event : Database.events) {
            if (event.getOrganizerUsername().equals(this.getUsername())) {
                count++;
            }
        }
        return count;
    }

    public void setNumberOfEvents(int numberOfEvents) {
        this.numberOfEvents = numberOfEvents;
    }
    public double getBalance() {
        return wallet.getBalance();
    }

    public void setBalance(double amount) {
        wallet.setBalance(amount);
    }



}