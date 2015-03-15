package com.example.android.jetboy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import java.io.*;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultUserTokenHandler;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;


/**
 * Created by andrewaday on 2/2/15.
 */
public class Home extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        if (getIntent().getExtras() != null) {
            Intent a = getIntent();

            String username = a.getExtras().getString("username");

            final EditText user = (EditText) findViewById(R.id.username);
            user.setText(username);
        }
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

    public void signup(View v) {
        final EditText username = (EditText) findViewById(R.id.username);
        String name = username.getText().toString();
        Intent i = new Intent(this, Backup.class);
        i.putExtra("username", name);
        startActivity(i);
    }

    public void login(View v) {
//        Intent i = new Intent(this, Backup.class);
//        startActivity(i);
        String path = "http://personabase.com/android/test.php";
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); // Timeout
        // Limit
        HttpResponse response;
        JSONObject json = new JSONObject();
        try {
            HttpPost post = new HttpPost(path);
            json.put("service", "GOOGLE");
            Log.i("json Object", json.toString());
            post.setHeader("json", json.toString());
            StringEntity se = new StringEntity(json.toString());
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            post.setEntity(se);
            response = client.execute(post);
        /* Checking response */
            if (response != null) {
                InputStream in = response.getEntity().getContent();
                // Get data in the entity
                String a = convertStreamToString(in);
                Log.i("Read from Server", a);
            }
        } catch (Exception e) {
            Log.i("Error", "no response");
            e.printStackTrace();
        }
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }






        public void web(View v) {
        String URL = "http://gunn.pausd.org/";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
        startActivity(browserIntent);
    }


}
