package com.gomurmur.chord;


import android.support.v4.app.*;
import android.view.*;
import android.os.Bundle;
import android.content.Intent;
import com.facebook.Session;
import com.facebook.SessionState;
import android.util.Log;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import java.util.*;
import android.app.NotificationManager;
import android.app.Notification;
import android.content.Context;

public class AuthFragment extends Fragment{

    private static final String TAG = "AuthFragment";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.auth,
                container, false);
        return view;
    }



}
