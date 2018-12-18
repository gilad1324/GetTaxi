package com.jct.gilad.gettaxi.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jct.gilad.gettaxi.R;

import java.util.jar.Attributes;

public class MainActivity extends Activity implements View.OnClickListener {
    public static final String EXTRA_MESSAGE = "com.jct.gilad.gettaxi.NAME";

    //private EditText IdEditText;
    private EditText NameEditText;
    private EditText PhoneEditText;
    private EditText EmailEditText;
    private EditText SurceEditText;
    private EditText DestinationEditText;

    private Button addTexiButton;

    private void findViews(){
    //IdEditText = (EditText)findViewById( R.id.IdEditText );
    NameEditText = (EditText)findViewById( R.id.NameEditText );
    PhoneEditText = (EditText)findViewById( R.id.PhoneEditText );
    EmailEditText=(EditText)findViewById( R.id.EmailEditText );;
    SurceEditText=(EditText)findViewById( R.id.SurceEditText );;
    DestinationEditText=(EditText)findViewById( R.id.DestinationEditText );;
    addTexiButton = (Button)findViewById( R.id.addTexiButton );
    addTexiButton.setOnClickListener( this );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();


    }
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.NameEditText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if ( v == addTexiButton )
            addTaxi();

    }

    private void addTaxi() {
        try {
            //int id = Integer.valueOf(this.IdEditText.getText().toString());
            String clientName;
            long clientPhoneNumber;
            String clientEmail;
            if(EmailEditText.getText().toString().trim().length() > 0)
            {clientEmail = EmailEditText.getText().toString();}
            else//do somting with tost

            if(NameEditText.getText().toString().trim().length() > 0)
                {clientName = NameEditText.getText().toString();}
                else//do somting with tost

             if(PhoneEditText.getText().toString().trim().length() > 0)
             {clientPhoneNumber = Long.parseLong(PhoneEditText.getText().toString());}
             else//do somting with tost

            Status status,
            Location sourceLocation,
            Location destLocation,
            Time startTime,
            Time endTime,



            DBManagerFactory.getManager().addLecturer(contentValues);
        } catch (Exception e)
        {

        }
    }
}