package e.davidbajs.lib_crop;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static java.time.LocalDate.now;

public class CropList {

    public static final String ID = "CROP_ID";
    public static final String ID_NEW = "-1";

    private String name;
    private ArrayList<Crop> crops;
    int currentCrop;

    public Crop getSpecificCrop(){return crops.get(currentCrop);}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Crop> getCrops() {
        return crops;
    }

    public void setCrops(ArrayList<Crop> crops) {
        this.crops = crops;
    }

    public int getCurrentCrop() {
        return currentCrop;
    }

    public void setCurrentCrop(int currentCrop) {
        this.currentCrop = currentCrop;
    }

    public void add(Crop c) {
        crops.add(c);
    }

    public int size(){
        return crops.size();
    }

    public CropList(String name) {
        this.name = name;
        crops = new ArrayList<>();
    }

    public Crop find(String id) {
        for(Crop c:this.crops){
            if(c.getIdCrop().equals(id)){
                return c;
            }
        }
        System.out.println("Crop hasn't been found.");
        return null;
    }

    public void RemoveCrop(String c){
        Crop crop = find(c);
        crops.remove(crop);
    }

    public Crop getPosition(int i) {
        return crops.get(i);
    }

    public boolean isEnd() {
        if(currentCrop < crops.size()){
            return false;
        }
        return true;
    }

    public static CropList getExample(){
        CropList cl = new CropList("Example");
        cl.add(new Crop("Corn", BigDecimal.valueOf(0.25), BigDecimal.valueOf(20.25), new myLocation(46.5061726, 15.8553695, "Blace"), "Random1, random2", Calendar.getInstance().getTime()));
        cl.add(new Crop("Wheat", BigDecimal.valueOf(0.19), BigDecimal.valueOf(123.11), new myLocation(46.5061726, 15.1235695, "No name"), "Pesticid", Calendar.getInstance().getTime()));
        cl.add(new Crop("Potato", BigDecimal.valueOf(0.34), BigDecimal.valueOf(55.55), new myLocation(46.8139173, 15.8553695, "Dat field"), "Dangerous for plevel", Calendar.getInstance().getTime()));
        return  cl;
    }
}
