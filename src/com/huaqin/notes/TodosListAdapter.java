package com.huaqin.notes;

import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TodosListAdapter extends BaseAdapter implements QueryListener,
		View.OnClickListener, TimeChangeReceiver.TimeChangeListener{
	/**
	 * 4 methods to implement:
	- android.widget.Adapter.getCount()
	- android.widget.Adapter.getItem()
	- android.widget.Adapter.getItemId()
	- android.widget.Adapter.getView()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startQuery(String selection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onQueryComplete(int token, Cursor cursor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startDelete(TodoInfo info) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeleteComplete(int token, int result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startUpdate(TodoInfo info) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdateComplete(int token, int result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startInsert(TodoInfo info) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInsertComplete(int token, Uri uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDateChange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTimePick() {
		// TODO Auto-generated method stub
		
	}

}
