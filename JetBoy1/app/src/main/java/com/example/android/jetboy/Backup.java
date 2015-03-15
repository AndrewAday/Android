package com.example.android.jetboy;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by andrewaday on 2/2/15.
 */
public class Backup extends Activity {
    static String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backup);

        TextView name = (TextView)findViewById(R.id.name);

        Intent a = getIntent();
        username = a.getExtras().getString("username");

        name.setText("Let's Go, " + username + "!");

    }

    public void back(View v) {
        Intent i = new Intent(this, Home.class);
        i.putExtra("username", username);
        startActivity(i);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }




}
