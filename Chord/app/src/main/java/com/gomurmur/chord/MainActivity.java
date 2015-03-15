package com.gomurmur.chord;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.client.*;
import org.json.*;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.Request;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.Response;

public class MainActivity extends FragmentActivity {


    //Facebook info
    private static String facebook_id;
    private static String profile_url;
    private static String full_name;
    private static String email;
    private static String first_name;
    private static String last_name;
    private static String access_token;

    //Progress bar
//    ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);

    //Fragment Manager
    private static final int MAIN = 0;
    private static final int AUTH = 1;
    private static final int FRAGMENT_COUNT = AUTH +1;

    private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];

    private boolean isResumed = false;

    private UiLifecycleHelper uiHelper;
    private Session.StatusCallback callback =
            new Session.StatusCallback() {
                @Override
                public void call(Session session,
                                 SessionState state, Exception exception) {
                    onSessionStateChange(session, state, exception);
                }
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);;
        //Create instance of UILifeCycleHelperand pass in listener
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        fragments[MAIN] = fm.findFragmentById(R.id.mainFragment);
        fragments[AUTH] = fm.findFragmentById(R.id.authFragment);

        FragmentTransaction transaction = fm.beginTransaction();
        for(int i = 0; i < fragments.length; i++) {
            transaction.hide(fragments[i]);
        }
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();
        isResumed = true;
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        uiHelper.onPause();
        isResumed = false;
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        Session session = Session.getActiveSession();

        if (session != null && session.isOpened()) {
            // if the session is already open,
            // try to show the selection fragment
            //showFragment(AUTH, false);
            authenticate();
        } else {
            // otherwise present the splash screen
            // and ask the person to login.
            showFragment(MAIN, false);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    private void showFragment(int fragmentIndex, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            if (i == fragmentIndex) {
                transaction.show(fragments[i]);
            } else {
                transaction.hide(fragments[i]);
            }
        }
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        // Only make changes if the activity is visible
        if (isResumed) {
            FragmentManager manager = getSupportFragmentManager();
            // Get the number of entries in the back stack
            int backStackSize = manager.getBackStackEntryCount();
            // Clear the back stack
            for (int i = 0; i < backStackSize; i++) {
                manager.popBackStack();
            }
            if (state.isOpened()) {
                // If the session state is open:
                // Show the authenticated fragment
//                pb.setVisibility(View.VISIBLE);
                Log.d("state", "openned");
                MyAsyncTask post_process = new MyAsyncTask();
                Log.d("async", "processing");
                post_process.execute();
            } else if (state.isClosed()) {
                // If the session state is closed:
                // Show the login fragment
                Log.d("state", "closed");
                showFragment(MAIN, false);
            }
        }
    }
//    public void onClick(View v) {
//        // TODO Auto-generated method stub
//        if(val.getText().toString().length()<1){
//            // out of range
//            Toast.makeText(this, "please enter something", Toast.LENGTH_LONG).show();
//        }else{
//            pb.setVisibility(View.VISIBLE);
//            new MyAsyncTask().execute(val.getText().toString());
//        }
//    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Double>{
        //Message handling
        private String server_response;
        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            postData();
            return null;
        }
        protected void onPostExecute(Double result){
//            pb.setVisibility(View.GONE);
            if (server_response == "success") {
                //showFragment(AUTH, false);
                authenticate();
            } else {
                sendToast(server_response);
            }
        }

        protected void onProgressUpdate(Integer... progress){
//            pb.setProgress(progress[0]);
        }

        public void postData() {
            // Begin the session management, yo
            Log.d("postdata", "posting");
            final Session session = Session.getActiveSession();
            Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    // If the response is successful
                    if (session == Session.getActiveSession()) {
                        if (user != null) {
                            facebook_id = user.getId();//user id
                            full_name = user.getName();//user's profile name
                            first_name = user.getFirstName();
                            last_name = user.getLastName();
                            email = user.getProperty("email").toString();
                            profile_url = user.getLink();
                            access_token = session.getAccessToken();

                            HttpClient httpclient = new DefaultHttpClient();
                            // specify the URL you want to post to
                            HttpPost httppost = new HttpPost("http://chord.gomurmur.com/add_user.php");
                            try {
                                // create a list to store HTTP variables and their values
                                List nameValuePairs = new ArrayList();
                                // add an HTTP variable and value pair
                                nameValuePairs.add(new BasicNameValuePair("email", email));
                                nameValuePairs.add(new BasicNameValuePair("facebook_id", facebook_id));
                                nameValuePairs.add(new BasicNameValuePair("full_name", full_name));
                                nameValuePairs.add(new BasicNameValuePair("first_name", first_name));
                                nameValuePairs.add(new BasicNameValuePair("last_name", last_name));
                                nameValuePairs.add(new BasicNameValuePair("profile_url", profile_url));
                                nameValuePairs.add(new BasicNameValuePair("access_token", access_token));
                                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                                Log.d("email", email);
                                // send the variable and value, in other words post, to the URL
                                // Execute HTTP Post Request
                                ResponseHandler<String> responseHandler=new BasicResponseHandler();
                                String responseBody = httpclient.execute(httppost, responseHandler);
                                if (responseBody != null) {
                                    try {
                                        JSONObject ret = new JSONObject(responseBody);
                                        server_response = ret.getString("status");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        server_response = "nothing returned";
                                        sendToast(server_response);
                                    }
                                }
                            } catch (ClientProtocolException e) {
                                // process execption
                                e.printStackTrace();
                            } catch (IOException e) {
                                // process execption
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
            Request.executeAndWait(request);
        }
    }
    private void sendToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void authenticate() {
        Intent reAuth = new Intent(this, AuthActivity.class);
        startActivity(reAuth);
    }

    /* Code to make notification
    public void makeNotification(View v) {

        Notification.Builder mBuilder =
                new Notification
                        .Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());

    }*/
}
