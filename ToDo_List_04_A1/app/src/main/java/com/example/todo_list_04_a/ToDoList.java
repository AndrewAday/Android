package com.example.todo_list_04_a;

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
		 // Get references to UI widgets myListView and myEditText with findViewByID
	    myListView = (ListView)findViewById(R.id.myListView);
	    myEditText = (EditText)findViewById(R.id.myEditText);
	    //create an array adapter to dynamically add items
	    todoItems = new ArrayList<String>();
	    int resID = R.layout.todolist_item;  //notebook view
	    aa = new ArrayAdapter<String>(this, resID, todoItems);
	    myListView.setAdapter(aa);  
	    //this is how to do clicks/inputs in code instead of XML
	    //notice that this uses the D-pad.....change this
	    myEditText.setOnKeyListener(new OnKeyListener() {
	      public boolean onKey(View v, int keyCode, KeyEvent event) {
	    	  if (event.getAction() == KeyEvent.ACTION_DOWN)
	          if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode==KeyEvent.KEYCODE_ENTER) {
	            todoItems.add(0, myEditText.getText().toString());
	            //this originally sets subsequent elements to blank - changing it in xml
	            myEditText.setText("");
	            aa.notifyDataSetChanged();
	            cancelAdd();
	            return true; 
	          }
	       return false;
	    }});
	    registerForContextMenu(myListView);
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
	    //if you want to be tricky, in this function you can make it so the 
	    //nuke/clear all button only shows up once a specified number of items are in the list
//	  return true;
//	}
	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) { 
	      case R.id.new_item: {
	      	  	Log.e("ToDoList4-Revised", "KeyListener implemented - new item coming");
	    	  addNewItem();
	          return true; 
	      }
	      case (R.id.confirm):{
	    	  for(int hlder = myListView.getCount(); hlder>0; hlder--){
	    		  removeItem(hlder-1);
	    	  	}
	    	  return true;
	      }
	 //     case (DUPLICATE_TODO):{
	 //   	  if (addingNew)
	 //   		  return true;
	 //   	  todoItems.add(0, myListView.getSelectedItem().toString());
	 //         aa.notifyDataSetChanged();
	 //   	  return true;
//	      }
	      default:
	          return super.onOptionsItemSelected(item);
	    }
	  }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, 
			View v, 
            ContextMenu.ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Selected Item");
		menu.add(0,Menu.FIRST, Menu.NONE, "Remove").setIcon(R.drawable.remove_item);
		menu.add(0,Menu.FIRST+1, Menu.NONE,"Duplicate").setIcon(R.drawable.duplicate_icon);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item){
		AdapterView.AdapterContextMenuInfo menuInfo;
		switch(item.getItemId()){
		case 1:
	        menuInfo =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	        int index1 = menuInfo.position;
			removeItem(index1);
			return true;
		case 2:
			if (addingNew)
				 return true;
	        menuInfo =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	        int index2 = menuInfo.position; 
			todoItems.add(0, todoItems.get(index2));
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