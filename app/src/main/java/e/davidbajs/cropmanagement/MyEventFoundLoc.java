package e.davidbajs.cropmanagement;

import android.location.Location;

public class MyEventFoundLoc {
    private Location location;
    private int id;
    private String name;

    public MyEventFoundLoc(Location location, int id, String name) {
        this.location = location;
        this.id = id;
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
