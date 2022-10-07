package pl.recordit.examples.techlif.notifyapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "hello";
    public static final int NOTIFICATION_ID = 1234;
    private Button simpleNotificationButton;
    private Button defaultActionNotificationButton;
    private Button customActionNotificationButton;
    private Button startServiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createChannel();
        simpleNotificationButton = findViewById(R.id.simple_notify_button);
        customActionNotificationButton = findViewById(R.id.custom_action_button);
        defaultActionNotificationButton = findViewById(R.id.action_notify_button);
        startServiceButton = findViewById(R.id.start_service_button);
        simpleNotificationButton.setOnClickListener(v -> {
            Notification notification = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                    .setContentTitle("Hello")
                    .setContentText("Hello from activity")
                    .setSmallIcon(R.drawable.ic_baseline_directions_bike_24)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build();
            NotificationManagerCompat.from(getBaseContext()).notify(
                    NOTIFICATION_ID,
                    notification
            );
        });
        defaultActionNotificationButton.setOnClickListener(v -> {
                    Intent intent = new Intent(this,  ActionActivity.class);
                    intent.putExtra("MESSAGE", "Hello from notification");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                    Notification notification = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                            .setContentTitle("Hello")
                            .setContentText("Hello from activity")
                            .setSmallIcon(R.drawable.ic_baseline_directions_bike_24)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)
                            .build();
                    NotificationManagerCompat.from(getBaseContext()).notify(
                            5677,
                            notification
                    );

                }
        );
        customActionNotificationButton.setOnClickListener(v -> {
            Intent intent = new Intent(this,  ActionActivity.class);
            intent.setAction("CLICK");
            intent.putExtra("MESSAGE", "Hello from notification");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
            Notification notification = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                    .setContentTitle("Custom action")
                    .setContentText("Notification with custom action")
                    .setSmallIcon(R.drawable.ic_baseline_directions_bike_24)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .addAction(R.drawable.ic_baseline_directions_bike_24, "CLICK", pendingIntent)
                    .build();
            NotificationManagerCompat.from(getBaseContext()).notify(
                    5677,
                    notification
            );
        });
        startServiceButton.setOnClickListener(v -> {
            Intent service = new Intent(this.getBaseContext(), DelayedService.class);
            service.putExtra("delay", 2);
            service.putExtra("message", "hello");
            startService(service);
        });
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                notificationManager = getSystemService(NotificationManager.class);
            }
            notificationManager.createNotificationChannel(channel);
        }
    }
}