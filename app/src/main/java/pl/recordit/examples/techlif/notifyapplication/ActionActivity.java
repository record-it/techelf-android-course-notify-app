package pl.recordit.examples.techlif.notifyapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class ActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String message = getIntent().getStringExtra("MESSAGE");
        Log.i("APP", "Message from notification " + message);
        setContentView(R.layout.activity_action);
    }
}