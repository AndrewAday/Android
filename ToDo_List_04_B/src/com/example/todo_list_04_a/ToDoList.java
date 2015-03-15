package com.example.todo_list_04_a;
//everything should be imported - a very extensive list
import java.util.ArrayList;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ToDoList extends Activity {
	//my global variables
	  private boolean addingNew = false;
	  private ArrayList<String> todoItems;
	  private ListView myListView;
	  private EditText myEditText;
	  private ArrayAdapter<String> aa;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 // Inflate your view
		setContentView(R.layout.main);
		 //* Get references to UI widgets myListView and myEditText with findViewByID
	   
		
	    //create an array adapter to dynamically add items
	    todoItems = new ArrayList<String>();
	    int resID = R.layout.todolist_item;  //notebook view
	    aa = new ArrayAdapter<String>(this, resID, todoItems);
	    myListView.setAdapter(aa);  
	    //this is how to do clicks/inputs in code instead of XML
	    //* notice that this uses the D-pad.....change this
	    myEditText.setOnKeyListener(new OnKeyListener() {
	      public boolean onKey(View v, int keyCode, KeyEvent event) {
	    	  if (event.getAction() == KeyEvent.ACTION_DOWN)
	          if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
	            todoItems.add(0, myEditText//* figure out how to get this info
	            //this originally sets subsequent elements to blank - changed in xml to have hint
	            myEditText.setText("");
	            aa.notifyDataSetChanged();
	            cancelAdd();
	            return true; 
	          }
	       return false;
	    }});
	    //* if creating context menu...register here
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do_list, menu);
		return true;
	}
//	@Override
//	public boolean onPrepareOptionsMenu(Menu menu) {
//	  super.onPrepareOptionsMenu(menu);
	    //* if you want to be tricky, in this function you can make it so the 
	    //nuke/clear all button only shows up once a specified number of items are in the list
//	  return true;
//	}
	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	   //* create cases for (at least) the new item and nuke actions
		switch (item.getItemId()) { 
	      case ____________: {
	    	  	  ____________
	          return true; 
	      }
	      case (_________________):{
	    	  for(int hlder = myListView.getCount(); hlder>0; hlder--){
	    		  removeItem(hlder-1);
	    	  	}
	    	  return true;
	      }
	      default:
	          return super.onOptionsItemSelected(item);
	    }
	  }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, 
			View v, 
            ContextMenu.ContextMenuInfo menuInfo){
		// if creating a context menu, add elements here
		// if popup...you can delete
		super.onCreateContextMenu(menu, v, menuInfo);
		//you will find helpful code for this in the reading or on the dev's page
	}
	@Override
	//if doing popup....delete this
	public boolean onContextItemSelected(MenuItem item){
		AdapterView.AdapterContextMenuInfo menuInfo;
		switch(_______________){
		case _____:
	        menuInfo =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	        int index1 = menuInfo.position;
			___________________;
			return true;
		case _____:
			if (addingNew)
				 return true;
	        menuInfo =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	        int index2 = menuInfo.position; 
			todoItems.add(0, ____________________);
			aa.notifyDataSetChanged();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	  private void addNewItem() {
	    addingNew = true;
	    myEditText.setVisibility(View.VISIBLE);
	    myEditText.requestFocus(); 
	  }
	  private void removeItem(int _index) {
			Log.i("onContextItemSelected",Integer.toString(_index));
	    todoItems.remove(_index);
	    aa.notifyDataSetChanged();  
	  }	
	  private void cancelAdd() {
		    addingNew = false;
		    myEditText.setVisibility(View.GONE);
		  }
}