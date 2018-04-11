package com.example.noelle.sailorbail;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeScreen extends AppCompatActivity {
    Button toMakeTextButton;
    Button emergencyButton;
    private String thePhoneNum;
    private String theAlias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        thePhoneNum = this.getIntent().getExtras().getString("theNum");
        theAlias = this.getIntent().getExtras().getString("alias");

        toMakeTextButton = (Button) findViewById(R.id.button);
        toMakeTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSetADate = new Intent(HomeScreen.this, SetADate.class);
                toSetADate.putExtra("theNum",thePhoneNum);
                toSetADate.putExtra("alias", theAlias);
                startActivity(toSetADate);
            }
        });

        //sends an automated text after 2 minutes
        emergencyButton = (Button) findViewById(R.id.button3);
        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int interval = 1000;
                Handler handler = new Handler();
                Runnable runnable = new Runnable(){
                    public void run(){
                        sendSMS();
                    }
                };
                handler.postAtTime(runnable, System.currentTimeMillis()+interval);
                handler.postDelayed(runnable, interval*5);

            }
        });
    }

    protected void sendSMS() {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        String message = "Hey can you fill in for me at work? Sorry for such short notice, but I have a family emergency.\n - " + theAlias;

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address" , thePhoneNum);
        smsIntent.putExtra("sms_body" , message);

        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(HomeScreen.this,
                    "SMS failed, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }
}
