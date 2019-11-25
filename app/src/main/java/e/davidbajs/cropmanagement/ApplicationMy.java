package e.davidbajs.cropmanagement;

import android.app.Activity;
import android.app.Application;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import e.davidbajs.lib_crop.Crop;
import e.davidbajs.lib_crop.CropList;

public class ApplicationMy extends Application {
    private String fileName = "CropsList.txt";
    private CropList crops;
    private Location location;
    private CropAdapter cropAdapter;
    private MainActivity mainActivity;

    public ApplicationMy() {
        super();
    }

    public CropList getCrops() {
        return crops;
    }

    public void setCrops(CropList crops) {
        this.crops = crops;
    }

    public Crop getByID(String id) {
        return crops.find(id);
    }

    private boolean checkIfFound(Location loc){
        if(!crops.isEnd()){
            Crop crop = crops.getSpecificCrop();
            if (myGPS.distanceInKM(loc.getLatitude(), loc.getLongitude(), crop.getMyLocation().getLatitude(),  crop.getMyLocation().getLongitude()) < 0.002) {
                return true;
            }
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyEventNewLoc eventNewLoc){
        location = eventNewLoc.getLocation();
        if(checkIfFound(location)){
            EventBus.getDefault().post(new MyEventFoundLoc(location, 2, crops.getSpecificCrop().getMyLocation().getFieldName()));
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        crops = AppDataSave.getAppDataSave(this).load(fileName);
        EventBus.getDefault().register(this);
        if(crops == null){
            crops = CropList.getExample();
        }
        cropAdapter = new CropAdapter(getCrops(), mainActivity);
        cropAdapter.notifyDataSetChanged();
    }

    public void save() {
        AppDataSave.getAppDataSave(this).save(crops, fileName);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        EventBus.getDefault().unregister(this);
    }

    public Location getLocation() {
        return location;
    }

    public void add(Crop c){
        crops.add(c);
    }
}
