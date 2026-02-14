import java.util.HashMap;
import java.util.*;

public class MonitorVehicleTracker {

    private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = deepCopy(locations);
    }

    public synchronized void setLocation(String id, int x, int y) {
        MutablePoint location = locations.get(id);
        if(location == null) {
            throw new IllegalArgumentException("No such ID: " + id);
        }
        location.setX(x);
        location.setY(y);
    }

    public synchronized Map<String, MutablePoint> getLocations() {
        return deepCopy(locations);
    }

    public synchronized MutablePoint getLocation(String id) {
        MutablePoint location = locations.get(id);
        if(location == null) {
            throw new IllegalArgumentException("No such ID: " + id);
        }
        return new MutablePoint(location.getX(), location.getY());
    }

    private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m) {
        Map<String , MutablePoint> result = new HashMap<>();
        for(String id: m.keySet()) {
            MutablePoint location = m.get(id);
            result.put(id, new MutablePoint(location.getX(), location.getY()));
        }
        return Collections.unmodifiableMap(result);
    }
}
