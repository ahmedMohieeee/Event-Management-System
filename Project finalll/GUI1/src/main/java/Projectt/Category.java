package Projectt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Category {

    private Events[] event;

    private int eventCount = 0;

    private static int numCounter = 1;

    private int num;

    private String name;


    private String description;

    public Category(String name, String description) {

        this.num = numCounter++;

        this.name = name;

        this.description = description;

        this.event = new Events[eventCount];

    }
    public void addEvent(Events newEvent) {
        if (newEvent == null) {
            throw new NullPointerException("Cannot add null event.");
        }
        Events[] newEventArray = new Events[eventCount + 1];

        for (int i = 0; i < eventCount; i++) {
            newEventArray[i] = event[i];
        }

        newEventArray[eventCount] = newEvent;
        event = newEventArray;
        eventCount++;


    }



    public void listEvents() {
        if (eventCount == 0) System.out.println("No events in this category.");
        for (int i = 0; i < eventCount; i++) System.out.println(event[i]);
    }


    @Override

    public String toString() {

        return "Category{" +

                "num=" + num +

                ", name='" + name + ' ' +

                ", description='" + description + ' ' +

                '}';

    }

    public int getEventCount() {
        return eventCount;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public String getName() {
        if (this.name != null)
        {
            return name;
        }
        else
            return "Invalid output❌.";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public static ObservableList<Category> observableCategories = FXCollections.observableArrayList(Database.categories);

    public static void updateObservableList() {
        observableCategories.setAll(Database.categories);
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
