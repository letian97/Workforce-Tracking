package com.example.theodhor.retrofit2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
    private String name,fromSite,toSite,duration, description;
    private EditText nameET, siteET, durationET, descriptionET;
    private Button sendButton;
    private TextView information, extraInformation;
    private Spinner fromSpinner,toSpinner, durationSpinner, descriptionSpinner;
    public static final String MY_PREFERENCES = "MyPrefs" ;
    public static final String NAME_KEY = "nameKey";

    private String savedName;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        communicator = new Communicator();

        nameET = (EditText)findViewById(R.id.nameInput);
//        siteET = (EditText)findViewById(R.id.siteInput);
//        durationET = (EditText)findViewById(R.id.durationInput);
//        descriptionET = (EditText)findViewById(R.id.descriptionInput);

        sharedpreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);


        sendButton = (Button)findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                sendSMS();

                saveName();


                name = nameET.getText().toString();
//                site = siteET.getText().toString();
//                duration = durationET.getText().toString();
//                description = descriptionET.getText().toString();

                fromSite = String.valueOf(fromSpinner.getSelectedItem());
                toSite = String.valueOf(toSpinner.getSelectedItem());
                duration = String.valueOf(durationSpinner.getSelectedItem());
                description = String.valueOf(descriptionSpinner.getSelectedItem());

                useSend(name, fromSite,toSite, duration, description);

            }
        });

        savedName = sharedpreferences.getString(NAME_KEY, null);
        nameET.setText(savedName, TextView.BufferType.EDITABLE);


        //set listener for item selected in spinner
        addListenerOnSpinnerItemSelection();

    }


    private void useSend(String name, String fromSite, String toSite,
                         String duration, String description){

        communicator.sendPost(name, fromSite,toSite, duration, description);

    }



    @Subscribe
    public void onServerEvent(ServerEvent serverEvent){
        Toast.makeText(this, ""+serverEvent.getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Subscribe
    public void onErrorEvent(ErrorEvent errorEvent){
        Toast.makeText(this,""+errorEvent.getErrorMsg(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume(){
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }



//    //edit SMS
//    private void sendSMS() {
//
//        name = nameET.getText().toString();
//        site = siteET.getText().toString();
//        duration = durationET.getText().toString();
//        description = descriptionET.getText().toString();
//
//        String SMS = "Name: "+name;
//        SMS += "\nSite: " + site;
//        SMS += "\nDuration: " + duration;
//        SMS += "\nDescription:" + description;
//
//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage("0127338110", null, SMS, null, null);
//        return;
//
//    }

    public void addListenerOnSpinnerItemSelection(){

        fromSpinner = (Spinner) findViewById(R.id.from_spinner);
        fromSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        toSpinner = (Spinner) findViewById(R.id.to_spinner);
        toSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        durationSpinner = (Spinner) findViewById(R.id.duration_spinner);
        durationSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        descriptionSpinner = (Spinner) findViewById(R.id.description_spinner);
        descriptionSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());


    }

     //save the name in local storage
    public void saveName() {

        String text  = nameET.getText().toString();

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(NAME_KEY, text);

        editor.commit();


    }

    public void getStringValues() {

    }

}
