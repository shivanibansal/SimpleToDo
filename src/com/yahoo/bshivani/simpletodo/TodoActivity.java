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
    				Toast.makeText(getBaseContext(), R.string.toast_hint_empty_list, -2).show();
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
		String etNewtItemString = etNewItem.getText().toString().trim();
		/* Validation : Blank Item Name */
		if (etNewtItemString.compareTo("") == 0)
		{
			Toast.makeText(this, R.string.toast_hint_enter_item_to_add_in_list, -5).show(); //Toast.LENGTH_SHORT
		}
		/* Validation : Item Already Exist */
		else if (isItemExistInList(etNewtItemString) == true)
		{
			Toast.makeText(this, R.string.toast_hint_item_already_exist, -5).show(); //Toast.LENGTH_SHORT
			etNewItem.setText("");
		}
		else
		{
			todoAdapter.add(etNewtItemString);
			etNewItem.setText("");
			writeItems();
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
	
	private boolean isItemExistInList (String itemString)
	{
		int posItem = todoItems.indexOf(itemString);
		if (posItem == -1)
			return false; // Item does not exist in List
		return true; // Item already exist in List 
	}
}
