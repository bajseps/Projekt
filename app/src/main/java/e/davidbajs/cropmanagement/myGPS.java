package e.davidbajs.cropmanagement;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;


public class myGPS extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static final String TAG = myGPS.class.getSimpleName();
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 5000; //5000ms = 5s
    private static final float LOCATION_DISTANCE = 0.5f; //5m

    private class LocationListener implements android.location.LocationListener{
        Location LastLocation;
        boolean isOK;

        public void setOK(boolean ok){
            isOK = ok;
        }

        public LocationListener(String provider) {
            LastLocation = new Location(provider);
            isOK = true;
        }

        @Override
        public void onLocationChanged(Location location) {
            EventBus.getDefault().post(new MyEventNewLoc(location));
            LastLocation.set(location);
            Intent i = new Intent("location_update");
            i.putExtra("Longitude", location.getLongitude());
            i.putExtra("Latitude", location.getLatitude());
            sendBroadcast(i);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            if(i == LocationProvider.AVAILABLE){
                isOK = true;
            }
        }

        @Override
        public void onProviderEnabled(String s) {
            isOK = true;
        }

        @Override
        public void onProviderDisabled(String s) {
            isOK = false;
        }
    }

    LocationListener[] LocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    public boolean isAvailable() {
        if(LocationListeners == null){
            return false;
        }
        if(LocationListeners[0].isOK){
            return true;
        }
        if(LocationListeners[1].isOK){
            return true;
        }
        return false;
    }

    @Override
    public int onStartCommand(Intent i, int flags, int startId) {
        super.onStartCommand(i, flags, startId);
        if (mLocationManager==null) {
            create();
        } else
        {
            if (!isAvailable()) {
                create();
            }
        }
        return START_STICKY;
    }

    @Override
    public void onCreate(){
        create();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mLocationManager != null){
            for(int i=0; i<LocationListeners.length; i++){
               try{
                   mLocationManager.removeUpdates(LocationListeners[i]);
               }catch (Exception e){
                   Log.i(TAG, "Failed to remove location listeners", e);
               }
            }
        }
    }

    private void create() {
        initLocManager();
        try{
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, LocationListeners[1]);
            LocationListeners[1].setOK(true);
        }catch (SecurityException|IllegalArgumentException e){
            LocationListeners[1].setOK(false);
        }
        try{
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, LocationListeners[0]);
            LocationListeners[0].setOK(true);
        }catch (SecurityException|IllegalArgumentException e){
            LocationListeners[0].setOK(false);
        }
    }

    private void initLocManager() {
        if(mLocationManager == null){
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    public static double distanceInKM(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }
}

















