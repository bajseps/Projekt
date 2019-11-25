package e.davidbajs.lib_crop;

public class myLocation {
    private double latitude;
    private double longitude;
    private String fieldName;

    public myLocation(double latitude, double longitude, String fieldName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.fieldName = fieldName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public myLocation() {
        this.latitude = 0;
        this.longitude = 0;
        this.fieldName = "";
    }
}
