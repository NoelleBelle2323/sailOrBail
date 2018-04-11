package com.example.noelle.sailorbail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Noelle on 4/7/2018.
 */

public class InfoScreen extends AppCompatActivity{
    EditText enterPhoneNum;
    EditText enterAlias;
    Button toHomeScreen;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_screen);

        enterPhoneNum = (EditText) findViewById(R.id.editText);
        enterAlias = (EditText) findViewById(R.id.editText2);
        /*enterPhoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numInput = enterPhoneNum.getText().toString().trim();
                phoneNum = Integer.parseInt(numInput);
            }
        }); */

        changeFocus(enterPhoneNum);
        changeFocus(enterAlias);

        toHomeScreen = (Button) findViewById(R.id.button4);
        toHomeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNum = enterPhoneNum.getText().toString().trim();
                //int phoneNum = Integer.valueOf(String.valueOf(enterPhoneNum.getText()));
                String theAlias = enterAlias.getText().toString().trim();
                Intent toHome = new Intent(InfoScreen.this, HomeScreen.class);
                toHome.putExtra("theNum",phoneNum);
                toHome.putExtra("alias", theAlias);
                startActivity(toHome);
            }
        });



    }

    public void changeFocus(View v){
        v.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
