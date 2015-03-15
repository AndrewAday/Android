/*
 * android.os.AsyncTask


AsyncTask enables proper and easy use of the UI thread. 
This class allows to perform background operations and 
publish results on the UI thread without having to manipulate 
threads and/or handlers.

An asynchronous task is defined by a computation that runs 
on a background thread and whose result is published on the 
UI thread. An asynchronous task is defined by 3 generic types, 
called Params, Progress and Result, and 4 steps, called begin, 
doInBackground, processProgress and end.

Usage
AsyncTask must be subclassed to be used. The subclass will override 
at least one method (doInBackground(Params...)), and most often 
will override a second one (onPostExecute(Result).)

Here is an example of subclassing:

 private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
     protected Long doInBackground(URL... urls) {
         int count = urls.length;
         long totalSize = 0;
         for (int i = 0; i < count; i++) {
             totalSize += Downloader.downloadFile(urls[i]);
             publishProgress((int) ((i / (float) count) * 100));
         }
         return totalSize;
     }

     protected void onProgressUpdate(Integer... progress) {
         setProgressPercent(progress[0]);
     }

     protected void onPostExecute(Long result) {
         showDialog("Downloaded " + result + " bytes");
     }
 }
 
Once created, a task is executed very simply:

 new DownloadFilesTask().execute(url1, url2, url3);
 
AsyncTask's generic types
The three types used by an asynchronous task are the following:

Params: 	the type of the parameters sent to the task upon execution. 
Progress: 	the type of the progress units published during the background 
         	computation. 
Result: 	the type of the result of the background computation. 

Not all types are always used by an asynchronous task. To mark a type as 
unused, simply use the type Void:

 private class MyTask extends AsyncTask<Void, Void, Void> { ... }
 
The 4 steps
When an asynchronous task is executed, the task goes through 4 steps:

onPreExecute(), 
	invoked on the UI thread immediately after the task is executed. 
	This step is normally used to setup the task, for instance by showing 
	a progress bar in the user interface. 
doInBackground(Params...), 
	invoked on the background thread immediately after onPreExecute() 
	finishes executing. This step is used to perform background computation that can take a long time. The parameters of the asynchronous task are passed to this step. The result of the computation must be returned by this step and will be passed back to the last step. This step can also use publishProgress(Progress...) to publish one or more units of progress. These values are published on the UI thread, in the onProgressUpdate(Progress...) step. 
onProgressUpdate(Progress...), 
	invoked on the UI thread after a call to publishProgress(Progress...). 
	The timing of the execution is undefined. This method is used 
	to display any form of progress in the user interface while the 
	background computation is still executing. For instance, it can be 
	used to animate a progress bar or show logs in a text field. 
onPostExecute(Result), 
	invoked on the UI thread after the background computation finishes. 
	The result of the background computation is passed to this step 
	as a parameter. 

Threading rules
	There are a few threading rules that must be followed for this class 
	to work properly:

	1. The task instance must be created on the UI thread. 
	2. execute(Params...) must be invoked on the UI thread. 
	3. Do not call onPreExecute(), onPostExecute(Result), 
	   doInBackground(Params...), onProgressUpdate(Progress...) manually. 
	4. The task can be executed only once (an exception will be thrown 
	   if a second execution is attempted.) 


 */

package cis493.simpleasync;

import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Main extends Activity {
	Button btnSlowWork;
	Button btnQuickWork;
	EditText etMsg;
	Long  startingMillis;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//TODO : assign your buttons and edit texts to there R references 
		
		//TODO: either set up button listeners OR change your XML 

	}// onCreate

	// /////////////////////////////////////////////////////////////////////////////////
	// the three argument types in the AsyncTask definition correspond to:
	// Params, Progress, Result.
	// (Varargs) Array type notation "Type..." such as "Long..." works same as "Long[]"
	// /////////////////////////////////////////////////////////////////////////////////
	
	//TODO provide the 3 argument types you need
	private class VerySlowTask extends AsyncTask<_____________________> {
		//a progress screen - downside is that it covers the screen; feel free to use 
		//what was used in class
		private final ProgressDialog dialog = new ProgressDialog(Main.this);
		
		//TODO create a Calendar object OR figure out another way to get the time 

		// can use UI thread here
		protected void onPreExecute() {
			//TODO : get the current time - can just print in milliseconds or any way you want
			startingMillis = __________________;
			etMsg.setText("Start Time: " + startingMillis);
			this.dialog.setMessage("Wait\nSome SLOW job is being done...");
			this.dialog.show();
		}

		// automatically done on worker thread (separated from UI thread)
		//protected Void doInBackground(final String... args) {
		protected Void doInBackground(final String... args) {
			try {
				
				// simulate here the slow activity
				for (Long i = 0L; i < 3L; i++) {
					Thread.sleep(2000);
					//TODO - what do you need to call to update the progress?
					_________________((Long)i);
				}
			} catch (InterruptedException e) {
				Log.v("slow-job being done", e.getMessage());
			}
			return null;
		}

		// periodic updates - it is OK to change UI
		
		//TODO create onProgressUpdate function - updates the editText with 
		//messages for user - see screencast
	
		
		// can use UI thread here
		//TODO add the inputs....are any used?
		protected void onPostExecute(___________________) {
			if (this.dialog.isShowing()) {
				//TODO the progress window is still showing - close it
				
			}
			// cleaning-up all done
			etMsg.append("\nEnd Time:" 
					+ (System.currentTimeMillis()-startingMillis)/1000);
			etMsg.append("\ndone!");
		}
	}//AsyncTask

}// Main