# Event-Management-System
This Java-based desktop application provides a complete solution for managing events through a multi-role system involving Admins, Organizers, and Attendees. Designed using Object-Oriented Programming principles and implemented with a polished GUI (JavaFX), the system enables seamless event creation, booking, and management.

💼 Key Roles & Responsibilities

👑 Administrator:
The Admin oversees all aspects of the system and can manage:

•Update their profile(username, role, date of birth, working hours)
•Organizers – View, add, or remove organizers.
•Attendees – View, add, or remove attendees.
•Rooms – Add new rooms, view Rooms, or delete them.
•Categories – Manage event categories.
•Events – View and remove events (cannot create them).

Smart Dependencies:
•When an organizer is removed, all their events are canceled and their attendees are automatically refunded (organizer’s balance is adjusted accordingly).
•Deleting an attendee removes them from registered events and issues a refund (reducing the event organizer’s balance).
•If a room or category is deleted, the related events are removed and refunds are issued to attendees.

🧑‍💼 Organizer:
Each Organizer has a dedicated dashboard that allows them to:

•Update their profile(password)
•Create events, specifying room, date/time, category, name, and Ticket price.
•View available rooms by checking date and room name.
•View events they have organized.
•See a list of attendees who have paid for their events.
•Delete events.

Smart Dependencies:
•Organizers cannot double-book rooms — if a room is taken at a specific time, it is shown as unavailable.

🧑 Attendee:
Users registered as attendees can:

•Update their profile (password, username, date of birth, address).
•Choose interests from available categories.
•View events that match their selected interests.
•Buy tickets if they have sufficient wallet balance.
•Cancel tickets and receive refunds.
•View their profile and past activity.

🖥 User Interfaces
All user types interact with intuitive dashboards. For example:

•Organizer Dashboard features buttons for profile, event creation, room checking, and attendee tracking.
•Attendee Panel includes profile management, interest selection, event browsing, and ticketing.
•Admin Dashboard provides access to rooms, categories, organizers, attendees, and events.

🔧 Technical Features

•Built with Java using JavaFX for GUI
•Uses Object-Oriented Programming principles like inheritance, encapsulation, and abstraction
•Centralized static Database.java class to hold and manage all system data
•Input validation included (e.g., wallet balance, interest filtering, room availability)

📁 Project Structure
Core classes: Admin, Organizer, Attendee, Event, Room, Category, Wallet

Database.java handles in-memory data storage

GUI screens for registration, login, and dashboards per user role
