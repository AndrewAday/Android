package com.gomurmur.chord;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.Fragment;
import java.util.regex.Pattern;

//Facebook imports
import com.facebook.AppEventsLogger;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.Session;
//Spotify imports
import com.spotify.sdk.android.Spotify;
import com.spotify.sdk.android.playback.Config;
import com.spotify.sdk.android.playback.ConnectionStateCallback;
import com.spotify.sdk.android.playback.Player;
import com.spotify.sdk.android.playback.PlayerNotificationCallback;
import com.spotify.sdk.android.playback.PlayerState;
import com.spotify.sdk.android.authentication.*;

/**
 * Created by andrewaday on 2/26/15.
 */
public class AuthActivity extends FragmentActivity implements PlayerNotificationCallback, ConnectionStateCallback {
    //Track FB session
    private boolean isResumed = false;

    //Fragments
    ViewPager viewPager;
//    SpotifyFragment spotify;

    //Spotify CLIENT_ID
    private static final String SPOTIFY_CLIENT_ID = "87b6a0d9bec54bfeb65beef22c20d484";
    //Spotify Redirect URI
    private static final String REDIRECT_URI = "chord-spotify-auth://callback";
    //Spotify Player
    private Player mPlayer;
    //Spotify Authentication Request Code
    private static final int SPOTIFY_REQUEST_CODE = 1337;

    SpotifyFragment spotify = new SpotifyFragment();
    Pandora pandora;
    SoundCloud soundcloud;




    private UiLifecycleHelper uiHelper;
    private Session.StatusCallback callback =
            new Session.StatusCallback() {
                @Override
                public void call(Session session,
                                 SessionState state, Exception exception) {
                    onSessionStateChange(session, state, exception);
                }
            };

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        // Only make changes if the activity is visible
        if (isResumed) {
            if (state.isOpened()) {
                ;
//                pb.setVisibility(View.VISIBLE);
//                Log.d("state", "openned");
//                MyAsyncTask post_process = new MyAsyncTask();
//                Log.d("async", "processing");
//                post_process.execute();
            } else if (state.isClosed()) {
                // If the session state is closed:
                // Return to main login screen
                Log.d("state", "closed");
                Intent reAuth = new Intent(this, MainActivity.class);
                startActivity(reAuth);
            }
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create instance of UILifeCycleHelperand pass in listener
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        setContentView(R.layout.auth_activity);

        // Locate the viewpager in activity_main.xml
        viewPager = (ViewPager) findViewById(R.id.pager);
//        spotify = (SpotifyFragment) getSupportFragmentManager().findFragmentByTag(getFragmentName(viewPager.getId(), 0));

        // Set the ViewPagerAdapter into ViewPager
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));


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
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SPOTIFY_REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Log.d("token", response.getAccessToken());
                Config playerConfig = new Config(this, response.getAccessToken(), SPOTIFY_CLIENT_ID);
                mPlayer = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
                    @Override
                    public void onInitialized(Player player) {
                        Log.d("mplayer", "initialized");
                        mPlayer.addConnectionStateCallback(AuthActivity.this);
                        mPlayer.addPlayerNotificationCallback(AuthActivity.this);
                        mPlayer.play("spotify:track:7yNK27ZTpHew0c55VvIJgm");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        } else {
            Log.e("LOGIN", "couldn't log in");
        }

        super.onActivityResult(requestCode, resultCode, intent);
        uiHelper.onActivityResult(requestCode, resultCode, intent);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    /*---------------------------------SPOTIFY STUFF---------------------------------------*/
    @Override
    public void onLoggedIn() {
        Log.d("SpotifyFragment", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("SpotifyFragment", "User logged out");
    }

    @Override
    public void onLoginFailed(Throwable error) {
        Log.d("SpotifyFragment", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("SpotifyFragment", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("SpotifyFragment", "Received connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(PlayerNotificationCallback.EventType eventType, PlayerState playerState) {
        Log.d("SpotifyFragment", "Playback event received: " + eventType.name());
    }

    @Override
    public void onPlaybackError(PlayerNotificationCallback.ErrorType errorType, String errorDetails) {
        Log.d("SpotifyFragment", "Playback error received: " + errorType.name());
    }

    @Override
    public void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
        uiHelper.onDestroy();
    }

    /*---------------------------------END SPOTIFY STUFF---------------------------------*/

     public void clickMethod(View v){
          switch(v.getTag().toString()) {
                 case "spotify":
//                     spotify.clickMethod(v);
                     AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(SPOTIFY_CLIENT_ID,
                             AuthenticationResponse.Type.TOKEN,
                             REDIRECT_URI);

                     builder.setScopes(new String[]{"user-read-private", "streaming", "playlist-read-private", "playlist-modify-public", "playlist-modify-private", "user-read-birthdate", "user-read-email"});
                     AuthenticationRequest request = builder.build();

                     AuthenticationClient.openLoginActivity(this, SPOTIFY_REQUEST_CODE, request);
                     Log.d("SpotifyFragment", "Logout called");
                     break;
                 default:
                     Log.d("auth click method", "hit default");
                     break;
          }
     }




    private static String getFragmentName(int viewId, int index)
    {
        return "android:switcher:" + viewId + ":" + index;
    }



}
