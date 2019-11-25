package e.davidbajs.cropmanagement;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import e.davidbajs.lib_crop.CropList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = myGPS.class.getSimpleName();

    private TextView tvCrops;
    private RecyclerView recyclerView;
    Typeface typeface;
    ApplicationMy app;
    private CropAdapter cropAdapter;

    private RecyclerView.LayoutManager layoutManagerRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCrops = (TextView) findViewById(R.id.tvCrops);
        typeface = Typeface.createFromAsset(getAssets(), "Beautiful_People2.ttf");
        tvCrops.setTypeface(typeface);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        layoutManagerRV = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManagerRV);

        app = (ApplicationMy) getApplication();

        cropAdapter = new CropAdapter(app.getCrops(), this);
        recyclerView.setAdapter(cropAdapter);

        cropAdapter.notifyDataSetChanged();

        runtimePermissions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cropAdapter.notifyDataSetChanged();
        startService(new Intent(this, myGPS.class));
    }

    @Override
    protected void onPause(){
        super.onPause();
        app.save();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        stopService(new Intent(this, myGPS.class));
    }

    private boolean runtimePermissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
            }
        }else {
            runtimePermissions();
        }
    }

    public void onClickNewEntry(View view){
        if(app.getLocation() != null){
            Intent i = new Intent(getBaseContext(), ActivityCrop.class);
            i.putExtra(CropList.ID, CropList.ID_NEW);
            startActivity(i);
        }else{
            Toast.makeText(this, "Location is unknown", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyEventNewLoc eventNewLoc) {
        Log.i(TAG, "New event location message event" + eventNewLoc.toString());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyEventFoundLoc eventFoundLoc) {
        Log.i(TAG, "Found event location message event" + eventFoundLoc.toString());
    }
}
