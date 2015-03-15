package com.gomurmur.chord;

/**
 * Created by andrewaday on 3/2/15.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
//Spotify imports
import com.spotify.sdk.android.Spotify;
import com.spotify.sdk.android.playback.Config;
import com.spotify.sdk.android.playback.ConnectionStateCallback;
import com.spotify.sdk.android.playback.Player;
import com.spotify.sdk.android.playback.PlayerNotificationCallback;
import com.spotify.sdk.android.playback.PlayerState;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

public class SpotifyFragment extends Fragment implements PlayerNotificationCallback, ConnectionStateCallback  {

    //Spotify CLIENT_ID
    private static final String SPOTIFY_CLIENT_ID = "87b6a0d9bec54bfeb65beef22c20d484";
    //Spotify Redirect URI
    private static final String REDIRECT_URI = "chord-spotify-auth://callback";
    //Spotify Player
    private Player mPlayer;
    //Spotify Authentication Request Code
    private static final int SPOTIFY_REQUEST_CODE = 1337;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from spotify_layout.xml
        View view = inflater.inflate(R.layout.spotify_layout, container, false);
//
//        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(SPOTIFY_CLIENT_ID,
//                AuthenticationResponse.Type.TOKEN,
//                REDIRECT_URI);
//
//        builder.setScopes(new String[]{"user-read-private", "streaming", "playlist-read-private", "playlist-modify-public", "playlist-modify-private", "user-read-birthdate", "user-read-email"});
//        AuthenticationRequest request = builder.build();
//
//        AuthenticationClient.openLoginActivity(getActivity(), SPOTIFY_REQUEST_CODE, request);

        AuthenticationClient.logout(getActivity().getApplicationContext());


//

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SPOTIFY_REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Log.d("token", response.getAccessToken());
                Config playerConfig = new Config(getActivity(), response.getAccessToken(), SPOTIFY_CLIENT_ID);
                mPlayer = Spotify.getPlayer(playerConfig, getActivity(), new Player.InitializationObserver() {
                    @Override
                    public void onInitialized(Player player) {
                        Log.d("mplayer", "initialized");
                        mPlayer.addConnectionStateCallback(SpotifyFragment.this);
                        mPlayer.addPlayerNotificationCallback(SpotifyFragment.this);
                        mPlayer.play("spotify:track:3DK6m7It6Pw857FcQftMds");
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
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d("SpotifyFragment", "Playback event received: " + eventType.name());
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Log.d("SpotifyFragment", "Playback error received: " + errorType.name());
    }

    @Override
    public void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }
    /*---------------------------------END SPOTIFY STUFF---------------------------------*/


    public void clickMethod(View v) {
        switch(v.getId()) {
            case R.id.spotify_login:
                //TODO: add stuff
                Log.d("SpotifyFragment", "Login clicked");
                spotifyLogin();
                break;
            default:
                Log.d("SpotifyFragment", "Hit default");
                break;
        }

    }

    public void spotifyLogin() {
//        Log.d("SpotifyFragment", "function called");
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(SPOTIFY_CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);

        builder.setScopes(new String[]{"user-read-private", "streaming", "playlist-read-private", "playlist-modify-public", "playlist-modify-private", "user-read-birthdate", "user-read-email"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(getActivity(), SPOTIFY_REQUEST_CODE, request);
        Log.d("SpotifyFragment", "Logout called");

    }
}
