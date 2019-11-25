package e.davidbajs.cropmanagement;

import android.content.Context;
import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import e.davidbajs.lib_crop.CropList;

public class AppDataSave {
    private static AppDataSave single;
    private boolean mExternalStorageAvailable = false;
    private boolean mExternalStorageWriteable = false;
    public static final String DATA_MAP="myMap";
    private String state = "";
    private Context context;

    private AppDataSave(Context context) {
        this.context = context;
        updateExternalStorageState();
    }

    public static AppDataSave getAppDataSave(Context context) {
        if (single == null) {
            single = new AppDataSave(context);
        }
        return single;
    }


    private void updateExternalStorageState() {
        state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
    }

    public boolean save(CropList cl, String filename) {
        if(this.mExternalStorageWriteable){
            File file = new File(context.getExternalFilesDir(DATA_MAP), "" + filename);
            try{
                System.out.println("Save "+file.getAbsolutePath()+" "+file.getName());
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                PrintWriter pw = new PrintWriter(file);
                String s = gson.toJson(cl);
                pw.println(s);
                pw.close();
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("Save isn't writable");
        }
        return false;
    }

    public CropList load(String name){
        if(this.mExternalStorageAvailable){
            try{
                File file = new File(context.getExternalFilesDir(DATA_MAP),"" + name);
                System.out.println("Load "+file.getAbsolutePath()+" "+file.getName());
                FileInputStream fileInputStream = new FileInputStream(file);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String string;
                while((string = bufferedReader.readLine()) != null){
                    stringBuffer.append(string).append('\n');
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                CropList cl = gson.fromJson(stringBuffer.toString(), CropList.class);
                if(cl == null){
                    System.out.println("Croplist error in load from json");
                }else{
                    System.out.println(cl.toString());
                }
                return cl;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Load isn't availabale");
        return null;
    }
}
