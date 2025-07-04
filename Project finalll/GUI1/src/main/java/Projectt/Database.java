package Projectt;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.ArrayList;

public class Database {
    public static ArrayList<Attendee> attendees = new ArrayList<>();
    public static ArrayList<Admin> admins = new ArrayList<>();
    public static ArrayList<Organizer> organizers = new ArrayList<>();
    public static ArrayList<Events> events = new ArrayList<>();
    public static ArrayList<Rooms> rooms = new ArrayList<>();
    public static ArrayList<Category> categories = new ArrayList<>();
    static Random random = new Random();

    public static void addData() {

        boolean[] workingHours1 = new boolean[24];
        boolean[] workingHours2 = new boolean[24];
        boolean[] workingHours3 = new boolean[24];

        for (int i = 9; i <= 17; i++) {
            workingHours1[i] = true;
            workingHours2[i] = (i != 13);
            workingHours3[i] = (i != 15);
        }

        boolean[]days1 = new boolean[365];
        boolean[]days2 = new boolean[365];
        boolean[]days3 = new boolean[365];
        boolean[]days4 = new boolean[365];
        boolean[]days5 = new boolean[365];
        boolean[]days6 = new boolean[365];
        boolean[]days7 = new boolean[365];
        for (int i = 0; i < 365; i++)
        {
            days1[i] = random.nextBoolean();
            days2[i] = random.nextBoolean();
            days3[i] = random.nextBoolean();
            days4[i] = random.nextBoolean();
            days5[i] = random.nextBoolean();
            days6[i] = random.nextBoolean();
            days7[i] = random.nextBoolean();
        }


        admins.add(new Admin("03/04/2006", "Head Developer", workingHours3, "Mohieee", "Aa12345%", Gender.MALE));
        admins.add(new Admin("15/02/1985", "System Administrator", workingHours1, "adminJohn", "john12H*3",Gender.MALE));
        admins.add(new Admin("20/08/1990", "Event Manager", workingHours2, "adminAlice", "alice456S%",Gender.FEMALE));

        rooms.add(new Rooms(days1,(int)(15+Math.random()*(100-14)), "Room 1"));
        rooms.add(new Rooms(days2,(int)(15+Math.random()*(100-14)), "Room 2"));
        rooms.add(new Rooms(days3,(int)(15+Math.random()*(100-14)), "Room 3"));
        rooms.add(new Rooms(days4,(int)(15+Math.random()*(100-14)), "Room 4"));
        rooms.add(new Rooms(days5,(int)(15+Math.random()*(100-14)), "Room 5"));
        rooms.add(new Rooms(days6,(int)(15+Math.random()*(100-14)), "Room 6"));
        rooms.add(new Rooms(days7,(int)(15+Math.random()*(100-14)), "Room 7"));

        attendees.add(new Attendee("Karim", "tooLate123", "01/01/1990", Gender.MALE, "123 Elm Street", 179.5));
        attendees.add(new Attendee("janeSmith", "password2", "05/03/1988", Gender.FEMALE, "456 Oak Avenue",199));
        attendees.add(new Attendee("mikeBrown", "password3", "10/07/1992", Gender.MALE, "789 Pine Road", 500.5));
        attendees.add(new Attendee("sarahLee", "password4", "23/11/1995", Gender.FEMALE, "321 Maple Lane", 250));
        attendees.add(new Attendee("davidClark", "password5", "12/06/1985", Gender.MALE, "654 Birch Blvd", 241));
        attendees.add(new Attendee("lauraKim", "password6", "14/09/1991", Gender.FEMALE, "888 Cedar Street", 300));
        attendees.add(new Attendee("alexTurner", "password7", "02/04/1994", Gender.MALE, "777 Willow Way", 186));
        attendees.add(new Attendee("emilyStone", "password8", "30/10/1987", Gender.FEMALE, "999 Redwood Terrace", 176));
        attendees.add(new Attendee("brianWhite", "password9", "08/12/1993", Gender.MALE, "555 Aspen Hill", 420));
        attendees.add(new Attendee("ninaJones", "password10", "19/02/1990", Gender.FEMALE, "333 Chestnut Street", 153));

        ArrayList<Attendee> sampleAttendees1 = new ArrayList<>();
        sampleAttendees1.add(attendees.get(0));
        sampleAttendees1.add(attendees.get(1));

        ArrayList<Attendee> sampleAttendees2 = new ArrayList<>();
        sampleAttendees2.add(attendees.get(2));
        sampleAttendees2.add(attendees.get(3));

        ArrayList<Attendee> sampleAttendees3 = new ArrayList<>();
        sampleAttendees3.add(attendees.get(4));
        sampleAttendees3.add(attendees.get(5));

        ArrayList<Attendee> sampleAttendees4 = new ArrayList<>();
        sampleAttendees4.add(attendees.get(5));
        sampleAttendees4.add(attendees.get(6));

        ArrayList<Attendee> sampleAttendees5 = new ArrayList<>();
        sampleAttendees5.add(attendees.get(6));
        sampleAttendees5.add(attendees.get(7));

        ArrayList<Attendee> sampleAttendees6 = new ArrayList<>();
        sampleAttendees6.add(attendees.get(8));
        sampleAttendees6.add(attendees.get(9));

        organizers.add(new Organizer("sarah", "Sarah1221%", 10,250));
        organizers.add(new Organizer("partyPlanner", "securRe456*", 10,200));
        organizers.add(new Organizer("concertQueen", "musicC789+", 10,150));
        organizers.add(new Organizer("meetupGuru", "gatherR101-", 10,150));
        organizers.add(new Organizer("summitKing", "confF999/", 10,200));

        categories.add(new Category("Music", "Events related to concerts, gigs, and musical performances."));
        categories.add(new Category("Technology", "Conferences, meetups, and talks about the latest in tech."));
        categories.add(new Category("Health & Wellness", "Workshops and seminars on physical and mental well-being."));
        categories.add(new Category("Business", "Networking events, startup pitch days, and seminars."));
        categories.add(new Category("Education", "Lectures, classes, and training sessions."));
        categories.add(new Category("Art & Culture", "Exhibitions, theater, and cultural experiences."));
        categories.add(new Category("Sports", "Tournaments, matches, and fitness competitions."));

        events.add(new Events("Tech Conference", 100.0, organizers.get(0).getUsername(), LocalDateTime.of(2030, 7, 20, 9, 0), "Technology", sampleAttendees1,rooms.get(0)));
        events.add(new Events("Art Exhibition", 30.0, organizers.get(2).getUsername(), LocalDateTime.of(2030, 10, 5, 11, 0), "Art & Culture", sampleAttendees2,rooms.get(1)));
        events.add(new Events("Comedy Show", 40.0, organizers.get(3).getUsername(), LocalDateTime.of(2030, 8, 10, 20, 0), "Health & Wellness", sampleAttendees3,rooms.get(2)));
        events.add(new Events("Cooking Workshop", 60.0, organizers.get(4).getUsername(), LocalDateTime.of(2030, 6, 25, 14, 0), "Education", sampleAttendees4,rooms.get(3)));
        events.add(new Events("Sports Meet", 20.0, organizers.get(0).getUsername(), LocalDateTime.of(2030, 9, 3, 16, 0), "Sports", sampleAttendees5,rooms.get(4)));
        events.add(new Events("Theater Play", 70.0, organizers.get(1).getUsername(), LocalDateTime.of(2030, 7, 12, 18, 30), "Art & Culture", sampleAttendees6,rooms.get(5)));
        events.add(new Events("Business Summit", 150.0, organizers.get(2).getUsername(), LocalDateTime.of(2030, 5, 30, 8, 0), "Business", sampleAttendees3,rooms.get(6)));
        events.add(new Events("Charity Run", 25.0, organizers.get(3).getUsername(), LocalDateTime.of(2030, 10, 5, 7, 0), "Sports", sampleAttendees5,rooms.get(6)));
        events.add(new Events("Music Concert", 50.0, organizers.get(0).getUsername(), LocalDateTime.of(2030, 6, 15, 19, 0), "Music", sampleAttendees1,rooms.get(1)));
        events.add(new Events("Fashion Show", 80.0, organizers.get(4).getUsername(), LocalDateTime.of(2030, 6, 12, 19, 30), "Art & Culture", sampleAttendees4,rooms.get(2)));



    }








}






