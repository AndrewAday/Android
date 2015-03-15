package programming.facebook_test;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.*;
import android.view.*;
import android.os.Bundle;

/*
 * Created by andrewaday on 2/9/15.
 */
public class SplashFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.splash,
                container, false);
        return view;
    }
}
