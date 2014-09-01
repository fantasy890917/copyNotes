package com.huaqin.notes;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class TodosActivity extends Activity {
	private static final String TAG = "TodosActivity";
	
	private static final int REQUEST_ADD_NEW = 1;
	
	 /** display number of All Todos */
	private TextView mNumberTextView;
	
	private TimeChangeReceiver mTimeChangeReceiver = null;
	/** Read all Todo infos from QB */
	private TodosListAdapter mTodosListAdapter = null ;
	 /** Show all Todo infos in ListView */
	private ListView mTodosListView = null;
	
	/** Item click & long click listener */
	private AdapterViewListener mAdapterViewListener = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initViews();
		configActionBar();
		mTimeChangeReceiver = TimeChangeReceiver.registTimeChangeReceiver(this);
		mTimeChangeReceiver.addDateChangeListener(mTodosListAdapter);
		LogUtils.d(TAG, "TodosActivity.onCreate() finished.");
	}
	
	private void initViews(){
		mTodosListAdapter = new TodosListAdapter(this);
		
		mAdapterViewListener = new AdapterViewListener();
		mTodosListView = (ListView) findViewById(R.id.list_todos);
		mTodosListView.setAdapter(mTodosListAdapter);
		mTodosListView.setOnItemClickListener(mAdapterViewListener);
		mTodosListView.setOnItemLongClickListener(mAdapterViewListener);
	}
	
	/**
	 * configure the action bar
	 */
	private void configActionBar(){
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayShowCustomEnabled(true);
		View actionBarNumberView = getLayoutInflater().inflate(R.layout.title_bar_number, null);
		actionBar.setCustomView(actionBarNumberView, new LayoutParams(Gravity.END));
		mNumberTextView = (TextView) findViewById(R.id.number);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	/**
	 * 加上属性android:uiOptions="splitActionBarWhenNarrow"后，
	 * 普通ActionBar将不再显示在标题栏部分，而是显示在屏幕底部
	 * android:showAsAction="always"
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_default, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		 super.onOptionsItemSelected(item);
		 switch(item.getItemId()){
		 case R.id.btn_new_todo:
			 Intent intent = new Intent(TodosActivity.this,EditTodoActivity.class);
			 startActivityForResult(intent, REQUEST_ADD_NEW);
			 break;
		 default:
			 break;
		 }
		 return true;
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		return super.onCreateDialog(id);
	}

	@Override
	@Deprecated
	protected void onPrepareDialog(int id, Dialog dialog) {
		// TODO Auto-generated method stub
		super.onPrepareDialog(id, dialog);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void updateHeaderNumberText(String text){
		mNumberTextView.setText(text);
	}
	
	class AdapterViewListener implements AdapterView.OnItemClickListener,
		AdapterView.OnItemLongClickListener{

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
