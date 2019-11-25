package e.davidbajs.cropmanagement;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;

public class Map extends AppCompatActivity {
    double latitude = 0.0;
    double longitude = 0.0;

    private MapView mapView;
    private DisplayMetrics displayMetrics;
    private ScaleBarOverlay scale;
    private CompassOverlay compassOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        latitude = Double.parseDouble(getIntent().getExtras().get("cropLatitude").toString());
        longitude = Double.parseDouble(getIntent().getExtras().get("cropLongitude").toString());

        Context context = getApplicationContext();
        //set user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));

        mapView = (MapView) findViewById(R.id.mapViewLayout);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);

        IMapController mapController = mapView.getController();
        mapController.setZoom(17.8);

        GeoPoint point = new GeoPoint(latitude, longitude);
        mapController.setCenter(point);

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        scale = new ScaleBarOverlay(mapView);
        scale.setCentred(true);
        scale.setScaleBarOffset(displayMetrics.widthPixels/2, 5);
        mapView.getOverlays().add(this.scale);

        this.compassOverlay = new CompassOverlay(context, new InternalCompassOrientationProvider(context), mapView);
        this.compassOverlay.enableCompass();
        mapView.getOverlays().add(this.compassOverlay);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        mapView.onResume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
