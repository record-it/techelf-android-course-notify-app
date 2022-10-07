package pl.recordit.examples.techlif.notifyapplication;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DelayedService extends Service {
    private ScheduledExecutorService service;
    @Override
    public void onCreate() {
        super.onCreate();
        service = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        service.shutdown();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int delay = intent.getIntExtra("delay", 1);
        String message = intent.getStringExtra("message");
        service.schedule(() -> {
            Log.i("APP", "Job done after " + delay + " seconds and with message " + message);
        },
                delay,
                TimeUnit.SECONDS
                );
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
