package com.example.backgroundthread;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	public ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void onToggleClicked(View v){
		//easy_thread(v);
		harder_thread(v);
		/*	
		boolean on = ((ToggleButton) v).isChecked();
		ImageView image_display = (ImageView)findViewById(R.id.imageView1);
		if (on){
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			image_display.setImageResource(R.drawable.pass);
		}
		else{
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			image_display.setImageResource(R.drawable.fail);
		}
			*/
	}
	public void easy_thread(final View v){
		new Thread(new Runnable() {
			@Override
			public void run(){
				int i;
				try{
					Thread.sleep(3000);
					if(((ToggleButton) v).isChecked())
						i = R.drawable.pass;
					else
						i = R.drawable.fail;
				} catch (InterruptedException e){
					i=R.drawable.fail;
					e.printStackTrace();
				}
				final int k = i;
				final ImageView image_display = (ImageView)findViewById(R.id.imageView1);
				image_display.post(new Runnable(){
					@Override
					public void run(){
						image_display.setImageResource(k);
					}
				});
			}
		}).start();
	}
	public void harder_thread(View v){
		boolean on = ((ToggleButton) v).isChecked();
		ImageView image_display = (ImageView)findViewById(R.id.imageView1);
		if (on){
			new MyAClass().execute(R.drawable.pass);
		}
		else{
			new MyAClass().execute(R.drawable.fail);
		}
	}
	public void toaster(View v){
		Toast.makeText(getApplicationContext(), "Hi!", Toast.LENGTH_LONG).show();
		
	}
	
	class MyAClass extends AsyncTask<Integer,Integer,Integer>{
		@Override
		protected void onPreExecute(){
			mProgressBar.setVisibility(ProgressBar.VISIBLE);
		}
		@Override
		protected Integer doInBackground(Integer... parameter){
			int myProgress = 0;
				
			for (int i = 0; i <= 10; i++){
				myProgress = i;
				try{
					Thread.sleep(500);
				} catch (InterruptedException e){}
				publishProgress(myProgress*10);
			}
			return parameter[0];
		}
		@Override
		protected void onProgressUpdate(Integer...progress){
			mProgressBar.setProgress(progress[0]);
		}
			
		@Override
		protected void onPostExecute(Integer result){
			mProgressBar.setVisibility(ProgressBar.INVISIBLE);
			ImageView image_display = (ImageView)findViewById(R.id.imageView1);
			image_display.setImageResource(result);
		}
	}


}
