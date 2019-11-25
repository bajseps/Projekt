package e.davidbajs.cropmanagement;

import android.location.Location;

public class MyEventNewLoc {
    private Location location;

    public MyEventNewLoc(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
