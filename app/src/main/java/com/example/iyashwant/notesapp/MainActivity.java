package com.example.iyashwant.notesapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    public int first;


    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3,floatingActionButton4;


    DBAdapter myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        first=getvalue();

        toolbar = (Toolbar) findViewById(R.id.app_barr);
        setSupportActionBar(toolbar);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton4 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item4);


        openDB();
        populateLitView();
        if (first==0)
        showhelp();

        listViewItemLongClick();
        listViewItemClick();


        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked



                        Intent goToNextActivity = new Intent(getApplicationContext(), addStuff.class);
                        startActivity(goToNextActivity);
                        populateLitView();
            }


        });

        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.delete_all,null);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(view2).setPositiveButton("Sure !", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        myDb.deleteAll();
                        Toast.makeText(MainActivity.this, "Deleted All", Toast.LENGTH_SHORT).show();
                        populateLitView();





                    }
                }).setNegativeButton("cancel",null).setCancelable(true);

                AlertDialog alertDialog= builder.create();
                alertDialog.show();



            }
        });


    }

    @Override
    protected void onPostResume() {

        populateLitView();
        super.onPostResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id==R.id.action_settings)
        {
            showhelp();
        }


        return super.onOptionsItemSelected(item);
    }

    private void openDB()
    {
        myDb= new DBAdapter(this);
        myDb.open();

    }



    private void populateLitView()
    {
        Cursor cursor = myDb.getAllRows();
        String[] fromFieldNames = new String[] {DBAdapter.KEY_HEAD,DBAdapter.KEY_TASK};
        int[] toViewIds = new int[] {R.id.textViewItemHead,R.id.textViewItemTask};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(),R.layout.item_layout,cursor, fromFieldNames, toViewIds,0);
        ListView mylist = (ListView) findViewById(R.id.ListViewTasks);
        mylist.setAdapter(myCursorAdapter);



    }




    private void listViewItemClick()
    {
        ListView mylist = (ListView) findViewById(R.id.ListViewTasks);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent goToNextActivity = new Intent(getApplicationContext(), Main2Activity.class);

                Bundle bundle = new Bundle();
                bundle.putLong("stuff",id);
                goToNextActivity.putExtras(bundle);
                startActivity(goToNextActivity);

                //updateTask(id);
              //  populateLitView();

            }
        });
    }





    private void listViewItemLongClick()
    {




        ListView mylist = (ListView) findViewById(R.id.ListViewTasks);

        mylist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {

                View view2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.deletesingle,null);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(view2).setPositiveButton("Sure !", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        myDb.deleteRow(id);
                        populateLitView();



                    }
                }).setNegativeButton("cancel",null).setCancelable(true);

                AlertDialog alertDialog= builder.create();
                alertDialog.show();

                return true;
            }
        });
    }


    public void showhelp()
    {
        View view2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.help,null);
        first=1;
        savedata(first);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view2).setPositiveButton("Got it ? ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });

        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }


    public void savedata(int i) {
        SharedPreferences sharedpref = getSharedPreferences("value3", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putInt("twovalue1", i);
        editor.apply();
    }

    public  int getvalue()
    {
        SharedPreferences sharedpref = getSharedPreferences("value3", Context.MODE_PRIVATE);
        Integer i = sharedpref.getInt("twovalue1",0);
        return i;

    }






}
