package e.davidbajs.cropmanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import e.davidbajs.lib_crop.Crop;
import e.davidbajs.lib_crop.CropList;
import e.davidbajs.lib_crop.myLocation;

public class ActivityCrop extends AppCompatActivity {

    public static final String TAG = ActivityCrop.class.getSimpleName();
    public static final String APP_ID = "APP_ID";
    String idAPP;

    private static final int MY_ACTIVITY_ID = 2356;

    private EditText etSeedWeight, etSeedPrice, etFieldName, etPesticides;
    private Button btnSave, btnAdvance, btnMap;

    private TextView tvCrops, tvDate;
    Typeface typeface;

    private Spinner spinCropName;

    private DatePicker datePicker;

    final String uuid = UUID.randomUUID().toString().replace("-", "");

    private Crop crop;
    ApplicationMy app;
    private boolean bool;

    public void onClickAdvance(View view){
        this.startActivityForResult(new Intent(this.getBaseContext(), ActivityCropAdvance.class), MY_ACTIVITY_ID);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        app = (ApplicationMy) this.getApplication();

        tvCrops = (TextView) findViewById(R.id.tvaddCrop);
        typeface = Typeface.createFromAsset(getAssets(), "Beautiful_People2.ttf");
        tvCrops.setTypeface(typeface);

        spinCropName = (Spinner) findViewById(R.id.spinCropName);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.crops, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCropName.setAdapter(adapter);
        spinCropName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getBaseContext(), "You selected: "+s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etSeedPrice = (EditText) findViewById(R.id.etSeedPrice);
        etSeedWeight = (EditText) findViewById(R.id.etSeedWeight);
        etFieldName = (EditText) findViewById(R.id.etFieldName);
        etPesticides = (EditText) findViewById(R.id.etPesticides);

        datePicker = (DatePicker) findViewById(R.id.datePicker);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnAdvance = (Button) findViewById(R.id.btnAdvance);
        btnMap = (Button) findViewById(R.id.btnMap);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowMap();
            }
        });

    }

    @Override
    protected void onPause(){
        super.onPause();
        updateState();
    }

    private void updateState() {
        crop.setName(spinCropName.getSelectedItem().toString());
        String s1 = etSeedPrice.getText().toString();
        String s2 = etSeedWeight.getText().toString();
        if(s1.matches("")){
            etSeedPrice.setText("0");
        }
        if(s2.matches("")){
            etSeedWeight.setText("0");
        }
        crop.setPricePerKg(BigDecimal.valueOf(Double.parseDouble(etSeedPrice.getText().toString())));
        crop.setWeightofSeeds(BigDecimal.valueOf(Double.parseDouble(etSeedWeight.getText().toString())));
        crop.getMyLocation().setFieldName(etFieldName.getText().toString());
        crop.setPesticides(etPesticides.getText().toString());
        crop.setStartDate(getDate(datePicker));
    }

    @Override
    protected void onResume(){
        super.onResume();
        Bundle b = getIntent().getExtras();
        if(b != null){
            String id = b.getString(CropList.ID);
            if(id.equals(CropList.ID_NEW)){
                bool = false;
                crop = null;
                //System.out.println(app.getLocation().getLongitude());
            }
            else{
                bool = true;
                crop = app.getByID(id);
                updateGUI();
            }
        }
        else{
            System.out.println("Bundle is empty");
        }
        updateGUI();
    }

    private void updateGUI() {
        if(bool){
            String compareName = crop.getName();

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.crops, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinCropName.setAdapter(adapter);
            if (compareName != null) {
                int spinnerPosition = adapter.getPosition(compareName);
                spinCropName.setSelection(spinnerPosition);
            }

            etSeedPrice.setText(crop.getPricePerKg().toString());
            etSeedWeight.setText(crop.getWeightofSeeds().toString());
            etFieldName.setText(crop.getMyLocation().getFieldName());
            etPesticides.setText(crop.getPesticides());

            int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(crop.getStartDate()));
            int month = Integer.parseInt(new SimpleDateFormat("MM").format(crop.getStartDate()));
            int day = Integer.parseInt(new SimpleDateFormat("dd").format(crop.getStartDate()));

            datePicker.init(year, month, day, null);
        }
        else{
            crop = new Crop();

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.crops, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinCropName.setAdapter(adapter);

            etSeedPrice.setText("");
            etSeedWeight.setText("");
            etFieldName.setText("");
            etPesticides.setText("");

            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            datePicker.init(year, month, day, null);

        }
    }

    public void onClickSave(View view) {
        if(bool){
            updateState();
        }
        else{
            String s1 = etSeedPrice.getText().toString();
            String s2 = etSeedWeight.getText().toString();
            if(s1.matches("")){
                etSeedPrice.setText("0");
            }
            if(s2.matches("")){
                etSeedWeight.setText("0");
            }
            crop = new Crop(spinCropName.getSelectedItem().toString(),
                    BigDecimal.valueOf(Double.parseDouble(etSeedPrice.getText().toString())),
                    BigDecimal.valueOf(Double.parseDouble(etSeedWeight.getText().toString())),
                    new myLocation(app.getLocation().getLatitude(), app.getLocation().getLongitude(), etFieldName.getText().toString()),
                    etPesticides.getText().toString(),
                    getDate(datePicker)
                    );
            app.add(crop);
        }
        app.save();
        finish();

    }

    public void ShowMap(){
        Intent i = new Intent(this, Map.class);
        i.putExtra("cropLatitude", crop.getMyLocation().getLatitude());
        i.putExtra("cropLongitude", crop.getMyLocation().getLongitude());
        startActivity(i);
    }

    public static Date getDate(DatePicker datePicker1){
        int day = datePicker1.getDayOfMonth();
        int month = datePicker1.getMonth();
        int year = datePicker1.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
