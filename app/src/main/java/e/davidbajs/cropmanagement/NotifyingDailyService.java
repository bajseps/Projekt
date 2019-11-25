package e.davidbajs.cropmanagement;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

public class NotifyingDailyService extends Service {

    public static final String TAG = NotifyingDailyService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent pIntent, int flags, int startId) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "NotifyingDailyService", Toast.LENGTH_SHORT).show();
        Log.i(TAG,"NotifyingDailyService");

        return super.onStartCommand(pIntent, flags, startId);
    }
}
