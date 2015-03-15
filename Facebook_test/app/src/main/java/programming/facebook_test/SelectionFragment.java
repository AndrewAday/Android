package programming.facebook_test;


import android.support.v4.app.*;
import android.view.*;
import android.os.Bundle;
import android.content.Intent;
/**
 * Created by andrewaday on 2/9/15.
 */
public class SelectionFragment extends Fragment{

    private static final String TAG = "SelectionFragment";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.selection,
                container, false);
        return view;
    }
    public void cont (View v) {
        Intent i = new Intent(v.getContext(), activitybar.class);
        startActivity(i);
    }




}
