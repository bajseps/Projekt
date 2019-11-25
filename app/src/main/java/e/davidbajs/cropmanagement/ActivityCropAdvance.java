package e.davidbajs.cropmanagement;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import java.math.BigDecimal;

import static e.davidbajs.cropmanagement.ActivityCrop.APP_ID;

public class ActivityCropAdvance extends AppCompatActivity {

    public static final String TAG = ActivityCropAdvance.class.getSimpleName();

    private static final int MY_ACTIVITY_ID = 2356;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private long pause;
    private boolean run;
    private long start;
    private long stop;

    private Chronometer cmSeedingTime;
    private TextView text;
    private Button btnStart, btnStop, btnReset;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_advance);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnReset = (Button) findViewById(R.id.btnReset);

        text = (TextView) findViewById(R.id.text);

        cmSeedingTime = (Chronometer) findViewById(R.id.cmSeedingTime);

        sharedPreferences = getSharedPreferences("CropSettings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btnStop.setEnabled(false);
    }

    public void start(View view) {
        SharedPreferences();

        if(!run){
            cmSeedingTime.setBase(SystemClock.elapsedRealtime() - pause);
            cmSeedingTime.start();
            cmSeedingTime.getBase();
            //startTimer = chronometer.getBase();
            start = ((pause/ 1000) % 60);

            run = true;
        }

        btnStop.setEnabled(true);
        btnStart.setEnabled(false);
    }

    public void stop(View view){
        SharedPreferences();

        if(run){
            cmSeedingTime.stop();
            pause = SystemClock.elapsedRealtime() - cmSeedingTime.getBase();
            stop = SystemClock.elapsedRealtime() - cmSeedingTime.getBase();
            stop = ((stop/1000) % 60);
            run = false;
        }

        btnStop.setEnabled(false);
        btnStart.setEnabled(true);

        String cropName = sharedPreferences.getString("e.davidbajs.cropmanagement.name", "");
        float cropPrice = sharedPreferences.getFloat("e.davidbajs.cropmanagement.price", 0.0f);
        float cropWeight = sharedPreferences.getFloat("e.davidbajs.cropmanagement.price", 0.0f);

        String ID_APP = getApplication().getPackageName();
        Crop2 crop = new Crop2(ID_APP, id, start, stop);
        text.append(crop.toString() + cropName + cropPrice + cropWeight + "\n");
    }

    private void SharedPreferences(){
        String cropName = sharedPreferences.getString("e.davidbajs.cropmanagement.name", "");
        float cropPrice = sharedPreferences.getFloat("e.davidbajs.cropmanagement.name", 0.0f);
        float cropWeight = sharedPreferences.getFloat("e.davidbajs.cropmanagement.price", 0.0f);
        String id_app = sharedPreferences.getString(APP_ID, "ID_NULL");
        id = id;
    }

    public void reset(View view){
        SharedPreferences();

        cmSeedingTime.setBase(SystemClock.elapsedRealtime());
        pause = 0;
    }

}
