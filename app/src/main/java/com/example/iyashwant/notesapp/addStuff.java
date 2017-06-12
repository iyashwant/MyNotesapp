package com.example.iyashwant.notesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class addStuff extends AppCompatActivity {

    private Toolbar toolbar;
    DBAdapter myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstuff);

        toolbar = (Toolbar) findViewById(R.id.app_barr);
        setSupportActionBar(toolbar);

        openDB();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int item_id= item.getItemId();




        if(item_id==R.id.save)
        {
            EditText stuff = (EditText)findViewById(R.id.editstuff_addit);
            EditText head = (EditText)findViewById(R.id.edithead_add);
            if (!TextUtils.isEmpty(stuff.getText().toString()))
            {
                if (TextUtils.isEmpty(head.getText().toString())) {
                    head.setText("Untitled Note");
                    myDb.insertRow(head.getText().toString(), stuff.getText().toString());

                }
                else myDb.insertRow(head.getText().toString(), stuff.getText().toString());

                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(this, "No Data Entered", Toast.LENGTH_SHORT).show();




            Intent goToNextActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(goToNextActivity);

            return true;


        }

        return super.onOptionsItemSelected(item);
    }


    private void openDB()
    {
        myDb= new DBAdapter(this);
        myDb.open();

    }





}

