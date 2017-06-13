package com.example.iyashwant.notesapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private Toolbar toolbar;
    DBAdapter myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar = (Toolbar) findViewById(R.id.app_barr);
        setSupportActionBar(toolbar);
        openDB();

        updateTask();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent goToNextActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(goToNextActivity);
    }


    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();

    }


    public void showhead(String s) {

        EditText editText = (EditText) findViewById(R.id.edithead_edit);
        editText.setText(s);
    }

    public void showstuff(String s) {
        EditText editText = (EditText) findViewById(R.id.editstuff_edit);
        editText.setText(s);
    }


    private void updateTask() {


        Bundle bundle = getIntent().getExtras();
        Long id = bundle.getLong("stuff");


            Cursor res = myDb.gethead(id);
            if (res.getCount() == 0) {
                Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
            } else {
              //  Toast.makeText(this, "data", Toast.LENGTH_SHORT).show();

                StringBuffer buffer = new StringBuffer();
                StringBuffer buffer1 = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append(res.getString(1));
                    // buffer.append("\n                    "+res.getString(1)+"\n          ---------------------------------------------          ");
                    // buffer.append("\n     "+res.getString(2)+"\n______________________________________");
                    buffer1.append(res.getString(2));
                }


                showhead(buffer.toString());
                showstuff(buffer1.toString());


            }

        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Bundle bundle = getIntent().getExtras();
        Long id = bundle.getLong("stuff");

        int item_id= item.getItemId();

        if(item_id==R.id.saveedit)
        {
            updateTasksave(id);
            Intent goToNextActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(goToNextActivity);
        }


        return super.onOptionsItemSelected(item);
    }

    private void updateTasksave(long id)
    {

        EditText head = (EditText) findViewById(R.id.edithead_edit);
        EditText stuff = (EditText) findViewById(R.id.editstuff_edit);
        Cursor cursor = myDb.getRow(id);
        if (cursor.moveToFirst())
        {
            String heading = head.getText().toString();
            String task = stuff.getText().toString();
            // today.setToNow();
            // String date = today.format("%Y-%m-%d %H:%M:%S");
            myDb.updateRow(id,heading,task);

        }
        Toast.makeText(this, "Changes saved!", Toast.LENGTH_SHORT).show();

        cursor.close();
    }


}
