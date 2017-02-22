package com.example.theodhor.retrofit2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theodhor.retrofit2.Events.CustomOnItemSelectedListener;
import com.example.theodhor.retrofit2.Events.ErrorEvent;
import com.example.theodhor.retrofit2.Events.ServerEvent;
import com.squareup.otto.Subscribe;


public class MainActivity extends AppCompatActivity {

    private Communicator communicator;
    private String name, fromSite, toSite, duration, description;
    private EditText nameET, fromET, toET, descriptionET;
    private Button sendButton;
    private CheckBox isType;

    private Spinner fromSpinner, toSpinner, durationSpinner, descriptionSpinner;
    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String NAME_KEY = "nameKey";

    private String savedName;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        communicator = new Communicator();
        //create edit text objects
        nameET = (EditText) findViewById(R.id.nameInput);
        fromET = (EditText) findViewById(R.id.fromInput);
        toET = (EditText)findViewById(R.id.toInput);
        descriptionET = (EditText)findViewById(R.id.descriptionInput);
        //create spinner objects
        fromSpinner = (Spinner) findViewById(R.id.from_spinner);
        toSpinner = (Spinner) findViewById(R.id.to_spinner);
        durationSpinner = (Spinner) findViewById(R.id.duration_spinner);
        descriptionSpinner = (Spinner) findViewById(R.id.description_spinner);
        //set listener for item selected in spinner
        addListenerOnSpinnerItemSelection();

        sharedpreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        //set listener for send button to:
        //get text from spinner, save name send SMS and send data to server
        sendButton = (Button) findViewById(R.id.sendButton);
        addListenerOnSendButton();

        savedName = sharedpreferences.getString(NAME_KEY, null);
        nameET.setText(savedName, TextView.BufferType.EDITABLE);



        isType = (CheckBox) findViewById(R.id.type_checkbox);
        addListenerOnTypeCheckbox();


    }


    private void useSend(String name, String fromSite, String toSite,
                         String duration, String description) {

        communicator.sendPost(name, fromSite, toSite, duration, description);

    }


    @Subscribe
    public void onServerEvent(ServerEvent serverEvent) {
        Toast.makeText(this, "yes!!" + serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Subscribe
    public void onErrorEvent(ErrorEvent errorEvent) {
        Toast.makeText(this, "no!!!" + errorEvent.getErrorMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }


    //get string from spinner and structure it into sms
    private void sendSMS() {

        String SMS = "Name: "+name;
        SMS += "\nFrom: " + fromSite;
        SMS += "\nTo: " + toSite;
        SMS += "\nDuration: " + duration;
        SMS += "\nDescription: " + description;

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("0167362339", null, SMS, null, null);
        return;
    }

    //add listener for the spinners
    public void addListenerOnSpinnerItemSelection() {
        fromSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        toSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        durationSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        descriptionSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    //add listener for send button: get text, save name, send SMS and send info to server
    public void addListenerOnSendButton() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get string from edit text and spinner
                name = nameET.getText().toString();
                duration = String.valueOf(durationSpinner.getSelectedItem());
                //if checkbox is ticked, get text from edit text, otherwise get from spinner
                if (isType.isChecked()){
                    fromSite = fromET.getText().toString();
                    toSite = toET.getText().toString();
                    description = descriptionET.getText().toString();

                }else{
                    fromSite = String.valueOf(fromSpinner.getSelectedItem());
                    toSite = String.valueOf(toSpinner.getSelectedItem());
                    description = String.valueOf(descriptionSpinner.getSelectedItem());
                }
                //send the information to server
                useSend(name, fromSite, toSite, duration, description);

                sendSMS();
                saveName();

            }
        });

    }

    public void addListenerOnTypeCheckbox() {
        isType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isType.isChecked()) {
                    //set edit text to be visible
                    toET.setVisibility(View.VISIBLE);
                    fromET.setVisibility(View.VISIBLE);
                    descriptionET.setVisibility(View.VISIBLE);

                    //set spinners to be invisible
                    toSpinner.setVisibility(View.GONE);
                    fromSpinner.setVisibility(View.GONE);
                    descriptionSpinner.setVisibility(View.GONE);

                } else {
                    //set edit text to be invisible
                    toET.setVisibility(View.GONE);
                    fromET.setVisibility(View.GONE);
                    descriptionET.setVisibility(View.GONE);

                    //set spinners to be visible
                    toSpinner.setVisibility(View.VISIBLE);
                    fromSpinner.setVisibility(View.VISIBLE);
                    descriptionSpinner.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //save the name in local storage
    public void saveName() {
        String text = nameET.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(NAME_KEY, text);
        editor.commit();
    }

}


