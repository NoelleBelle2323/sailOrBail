package com.example.noelle.sailorbail;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.*;
import java.util.*;
import android.graphics.*;
import android.view.*;


/**
 * Created by Noelle on 4/7/2018.
 */

public class SetADate extends AppCompatActivity {
    private Button buttonText;
    private Spinner spinnerText;
    private String thePhoneNum;
    private String message;
    private String theAlias;
    private Timer timer;
    private int mYear, mMonth, mDay, hour, minute;

    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_date);

        thePhoneNum = this.getIntent().getExtras().getString("theNum");
        theAlias = this.getIntent().getExtras().getString("alias");

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bail_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                //takes the user's choice in order to display the proper string in the textView
                switch(pos)
                {
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                }

                spinnerText = (Spinner)  findViewById(R.id.spinnerText);
                ArrayAdapter<CharSequence> adapterText = ArrayAdapter.createFromResource(SetADate.this,
                        R.array.text_options, android.R.layout.simple_spinner_item);
                adapterText.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerText.setAdapter(adapterText);

            }

            public void onNothingSelected(AdapterView<?> parent)
            {
                //necessary interface callback
            }
        });

        buttonText = (Button) findViewById(R.id.buttonText);
        //clicking the "Done" button starts the text message
        buttonText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //sendSMS();
                final int interval = 1000;
                Handler handler = new Handler();
                Runnable runnable = new Runnable(){
                    public void run(){
                        sendSMS();
                    }
                };

                handler.postAtTime(runnable, System.currentTimeMillis()+interval);

                /*
                Calendar currentTime = Calendar.getInstance();
                int yearsLeft = mYear - currentTime.get(Calendar.YEAR);
                int monthsLeft = mMonth - currentTime.get(Calendar.MONTH);
                int daysLeft = mDay - currentTime.get(Calendar.DAY_OF_MONTH);
                int hoursLeft = hour - currentTime.get(Calendar.HOUR);
                int minutesLeft = minute - currentTime.get(Calendar.MINUTE);

                int timeLeft = (yearsLeft*3154000*10000) + (monthsLeft*26280000*100) + (daysLeft*86400000) + (hoursLeft*3600000) +  (minutesLeft*60000);
                */
                Calendar currentTime = Calendar.getInstance();
                Calendar theDate = Calendar.getInstance();
                theDate.set(mYear,mMonth,mDay,hour,minute);
                long timeLeft = theDate.getTimeInMillis() - theDate.getTimeInMillis();
                handler.postDelayed(runnable, timeLeft);

                Intent toHome = new Intent(SetADate.this, HomeScreen.class);
                toHome.putExtra("theNum",thePhoneNum);
                toHome.putExtra("alias", theAlias);
                startActivity(toHome);
            }
        });

        // initiate the date picker and a button
        final Button date = (Button) findViewById(R.id.date);
        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();

                mYear = c.get(Calendar.YEAR); // current year
                mMonth = c.get(Calendar.MONTH); // current month
                mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(SetADate.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        //  initiate the edit text
        final Button time = (Button) findViewById(R.id.time);
        // perform click event listener on edit text
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SetADate.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }
    class firstTask extends TimerTask {
        public void run(){
            Log.i("Send SMS", "");
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);

            message = spinnerText.getSelectedItem().toString() + "\n - " + theAlias;

            smsIntent.setData(Uri.parse("smsto:"));
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address" , thePhoneNum);
            smsIntent.putExtra("sms_body" , message);

            try {
                startActivity(smsIntent);
                finish();
                Log.i("Finished sending SMS...", "");
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(SetADate.this,
                        "SMS failed, please try again later.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    protected void sendSMS() {


        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        message = spinnerText.getSelectedItem().toString();

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address" , thePhoneNum);
        smsIntent.putExtra("sms_body" , message);

        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SetADate.this,
                    "SMS failed, please try again later.", Toast.LENGTH_SHORT).show();
        }


        //SmsManager sms = SmsManager.getDefault();
        //sms.sendTextMessage(thePhoneNum, null, message, null, null);
    }


}

