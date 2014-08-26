package com.huaqin.notes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TodosListAdapter extends BaseAdapter implements QueryListener,
		View.OnClickListener, TimeChangeReceiver.TimeChangeListener{
	private static final String TAG = "TodosListAdapter" ;
	
	private Context mContext = null ;
	private LayoutInflater mInflater = null ;
	private TodoAsyncQuery mAsyncQuery = null ;
	
	private Comparator<TodoInfo> mTodoComparator = new Comparator<TodoInfo>() {

		@Override
		public int compare(TodoInfo info1, TodoInfo info2) {
			final long dueDate1 = info1.getDueDate();
			final long dueDate2 = info2.getDueDate();
			int result = 0;
			if(dueDate1 == dueDate2){
				result = 0;
			}else if(dueDate2 == 0 || (dueDate1 !=0 && dueDate2 > dueDate1)){
				result = -1;
			}else {
				result =1 ;
			}
			return result ;
		}
		
	};
	
	private Comparator<TodoInfo> mDoneComparator = new Comparator<TodoInfo>(){

		@Override
		public int compare(TodoInfo info1, TodoInfo info2) {
			final long compTime1 = info1.getCompleteTime();
			final long compTime2 = info2.getCompleteTime();
			int result = 0;
			if(compTime1 < compTime2){
				result = 1;
			}else if(compTime1 < compTime2){
				result = -1;
			}
			return result ;
		}
		
	};
	// the data display in list.
	private ArrayList<TodoInfo> mTodosDataSource = new ArrayList<TodoInfo>();
	// the data display in list.
	private ArrayList<TodoInfo> mDoneDataSource = new ArrayList<TodoInfo>();
	
	// assume the Adapter should loading items form DB first.
    private boolean mIsLoadingData = false;
	
	public TodosListAdapter(Context context){
		mContext = context ;
		mAsyncQuery = TodoAsyncQuery.getInstance(context.getApplicationContext());
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		startQuery(null);
	}
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
		LogUtils.d(TAG, "startQuery query from DB. selection : " + selection);
		mIsLoadingData = true ;
		mAsyncQuery.startQuery(0, this, TodoAsyncQuery.TODO_URI, null, selection, null, null);
	}

	@Override
	public void onQueryComplete(int token, Cursor cursor) {
		LogUtils.d(TAG, "onQueryComplete.");
		if(cursor != null && cursor.moveToFirst()){
			do{
				TodoInfo todoInfo = TodoInfo.makeTodoInfoFromCusor(cursor);
				if(TodoInfo.STATUS_TODO.equals(todoInfo.getStatus())){
					mTodosDataSource.add(todoInfo);
				}else{
					mDoneDataSource.add(todoInfo);
				}
			}while(cursor.moveToNext());
		}
		/**close cursor**/
		if(cursor != null){
			cursor.close();
		}
		
		if(mTodosDataSource.size() > 1){
			Collections.sort(mTodosDataSource, mTodoComparator);
		}
		
		if(mDoneDataSource.size() > 1){
			Collections.sort(mDoneDataSource, mDoneComparator);
		}
		mIsLoadingData = false ;
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
