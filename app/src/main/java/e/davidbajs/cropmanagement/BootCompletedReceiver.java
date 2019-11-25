package e.davidbajs.cropmanagement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("boot_broadcast_poc", "starting service...");
        context.startService(new Intent(context, NotifyingDailyService.class));
    }
}
