package com.yahoo.bshivani.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TodoActivity extends Activity {
	private ArrayList <String> 		todoItems;
	private ArrayAdapter<String>	todoAdapter;
	private ListView				lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();        
        todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
        lvItems.setAdapter(todoAdapter);
        setupListViewListener();
    }
    
    private void setupListViewListener () {
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
    		@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
    			todoItems.remove(pos);
    			todoAdapter.notifyDataSetChanged();
    			writeItems();
    			
    			if (todoItems.size() == 0)
    			{
    				// Not able to access R.id.toast_hint_empty_list
    				Toast.makeText(getBaseContext(), "List is empty", -2).show();
    			}
    			return true;
			}
    	});
    }
    
    private void populateArrayItems() {
		// TODO Auto-generated method stub
		todoItems = new ArrayList<String>();
		todoItems.add("Item 1");
		todoItems.add("Item 2");
		todoItems.add("Item 3");
	}
	
	public void addTodoItem(View v) {
		// TODO Auto-generated method stub
		EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
		if (etNewItem.getText().toString().trim().compareTo("") != 0)
		{
			todoAdapter.add(etNewItem.getText().toString());
			etNewItem.setText("");
			writeItems();
		}
		else
		{
			// Not able to access R.id.toast_hint_enter_item_to_add_in_list
			Toast.makeText(this, "Please enter item to add in List", -5).show(); //Toast.LENGTH_SHORT
		}
	}
	
	private void readItems()
	{
		File fileDir = getFilesDir();
		File todoFile = new File(fileDir, "todo.txt");
		try {
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			todoItems = new ArrayList<String>();
		}
	}
	
	private void writeItems ()
	{
		File fileDir = getFilesDir();
		File todoFile = new File(fileDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, todoItems);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
