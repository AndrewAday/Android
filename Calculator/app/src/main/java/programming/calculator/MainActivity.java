package programming.calculator;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends ActionBarActivity {


    public static final String calc_file = "calc_file";
    public static final String calc_history = "calc_history";
    public static final String separator = System.getProperty("line.separator");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onPause() {
        SharedPreferences.Editor editor = getSharedPreferences(calc_file, MODE_PRIVATE).edit();
        final EditText value1 = (EditText) findViewById(R.id.value1);
        final EditText value2 = (EditText) findViewById(R.id.value2);
        final EditText ans = (EditText) findViewById(R.id.answer);
        editor.putString("value1", value1.getText().toString());
        editor.putString("value2", value2.getText().toString());
        editor.putString("ans",ans.getText().toString());
        super.onPause();
    }

    @Override
    public void onResume() {
        //Initialize text stuff
        final EditText val1 = (EditText) findViewById(R.id.value1);
        final EditText val2 = (EditText) findViewById(R.id.value2);
        final EditText answer = (EditText) findViewById(R.id.answer);

        SharedPreferences prefs = getSharedPreferences(calc_file, MODE_PRIVATE);
        String value1 = prefs.getString("value1", null);
        String value2 = prefs.getString("value2", null);
        String ans = prefs.getString("ans", null);

        if (value1 != null) {
            val1.setText(value1);
        }
        if (value2 != null) {
            val2.setText(value2);
        }
        if (ans != null) {
            answer.setText(ans);
        }

        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.camera:
                camera();
                return true;
            case R.id.share:
                share();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void camera() {
        Context context = getApplicationContext();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public void share() {

    }


    public void clear(View v) {
        final EditText value1 = (EditText) findViewById(R.id.value1);
        final EditText value2 = (EditText) findViewById(R.id.value2);
        final EditText ans = (EditText) findViewById(R.id.answer);

        value1.setText("");
        value2.setText("");
        ans.setText("");
    }

    public void operate(View v, char operator) {
        final EditText value1 = (EditText) findViewById(R.id.value1);
        final EditText value2 = (EditText) findViewById(R.id.value2);
        final EditText ans = (EditText) findViewById(R.id.answer);

        double v1 = Double.parseDouble(value1.getText().toString());
        double v2 = Double.parseDouble(value2.getText().toString());
        double answer;

        Intent i = new Intent(this, answer.class);
        switch(operator) {
            case '+':
                answer = v1 + v2;
                //ans.setText(Double.toString(answer));
                i.putExtra("answer", "" + answer);
                //Write equation to file
                saveHistory(operator,v1,v2,answer);
                startActivity(i);
                break;
            case '-':
                answer = v1 - v2;
                //ans.setText(Double.toString(answer));
                i.putExtra("answer", "" + answer);
                saveHistory(operator,v1,v2,answer);
                startActivity(i);
                break;
            case 'x':
                answer = v1 * v2;
                //ans.setText(Double.toString(answer));
                i.putExtra("answer", "" + answer);
                saveHistory(operator,v1,v2,answer);
                startActivity(i);
                break;
            case '/':
                answer = v1 / v2;
                //ans.setText(Double.toString(answer));
                i.putExtra("answer", "" + answer);
                saveHistory(operator,v1,v2,answer);
                startActivity(i);
                break;
            default:
                break;
        }
    }

    public void add(View v) {
       operate(v, '+');
    }
    public void subtract(View v) {
        operate(v,'-');
    }
    public void multiply(View v) {
        operate(v,'x');
    }
    public void divide(View v) {
        operate(v,'/');
    }

    public void saveHistory(char operator, double v1, double v2, double answer) {
        String equation = v1 + " " + operator + " " + v2 + " = " + answer;
        try {
            FileOutputStream fos = openFileOutput(calc_history, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            fos.write(equation.getBytes());
            osw.append(separator);
            osw.flush();
            osw.close();
            fos.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
