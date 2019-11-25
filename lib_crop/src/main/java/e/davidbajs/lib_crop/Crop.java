package e.davidbajs.lib_crop;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Crop {

    private String idCrop;
    private String name;
    private BigDecimal pricePerKg;
    private BigDecimal weightofSeeds;
    private myLocation myLocation;
    //private ArrayList<Pesticide> pesticides;
    private String pesticides;
    private Date startDate;

    public String getIdCrop() {
        return idCrop;
    }

    public void setIdApp(String idCrop) {
        this.idCrop = idCrop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPricePerKg() {
        return pricePerKg;
    }

    public void setPricePerKg(BigDecimal pricePerKg) {
        this.pricePerKg = pricePerKg;
    }

    public BigDecimal getWeightofSeeds() {
        return weightofSeeds;
    }

    public void setWeightofSeeds(BigDecimal weightofSeeds) {
        this.weightofSeeds = weightofSeeds;
    }

    public e.davidbajs.lib_crop.myLocation getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(e.davidbajs.lib_crop.myLocation myLocation) {
        this.myLocation = myLocation;
    }
/*
    public ArrayList<Pesticide> getPesticides() {
        return pesticides;
    }

    public void setPesticides(ArrayList<Pesticide> pesticides) {
        this.pesticides = pesticides;
    }
*/

    public String getPesticides() {
        return pesticides;
    }

    public void setPesticides(String pesticides) {
        this.pesticides = pesticides;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Crop(String name, BigDecimal pricePerKg, BigDecimal weightofSeeds, e.davidbajs.lib_crop.myLocation myLocation, String pesticides, Date startDate) {
        idCrop = UUID.randomUUID().toString();
        this.name = name;
        this.pricePerKg = pricePerKg;
        this.weightofSeeds = weightofSeeds;
        this.myLocation = myLocation;
        this.pesticides = pesticides;
        this.startDate = startDate;
    }

    public Crop() {
        this.name = "";
        this.pricePerKg = BigDecimal.valueOf(0);
        this.weightofSeeds = BigDecimal.valueOf(0);
        this.myLocation = new myLocation();
        this.pesticides = "";
        this.startDate = new Date();
    }
//currWeather
}
