package programming.calculator;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;

import java.io.FileInputStream;

/**
 * Created by andrewaday on 1/28/15.
 */
public class answer extends Activity {
    static int answerr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer);

        final EditText ans = (EditText) findViewById(R.id.answer_field);
        Intent a = getIntent();
        String s = a.getExtras().getString("answer");

        answerr = (int) Math.floor(Double.parseDouble(s));

        ans.setText("Your answer is: " + s);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ret(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void call(View v) {
        //Intent a = getIntent();
        //int num = Integer.parseInt(a.getExtras().getString("answer"));
        //String numb = "" + num;
        String num = "" + answerr;
        Uri number = Uri.parse("tel:" + num);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);

        startActivity(callIntent);
    }

    public void history(View v) {
        String FILENAME = "calc_history";
        //FileInputStream
    }


}
