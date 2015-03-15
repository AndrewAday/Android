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

public class MainFragment extends Fragment{

    private static final String TAG = "MainFragment";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.main,
                container, false);
        LoginButton auth = (LoginButton) view.findViewById(R.id.authButton);
        auth.setReadPermissions(Arrays.asList("email", "public_profile", "user_friends"));

        return view;
    }

}
