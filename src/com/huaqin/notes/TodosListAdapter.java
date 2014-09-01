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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class TodosListAdapter extends BaseAdapter implements QueryListener,
		View.OnClickListener, TimeChangeReceiver.TimeChangeListener{
	private static final String TAG = "TodosListAdapter" ;
	private static final String EMPTY_TEXT = "";
	
	private static final int TYPE_TODOS_HEADER = 0;
	private static final int TYPE_TODOS_ITEM = TYPE_TODOS_HEADER + 1;
	private static final int TYPE_TODOS_FOOTER = TYPE_TODOS_ITEM + 1;
	private static final int TYPE_DONES_HEADER = TYPE_TODOS_FOOTER + 1;
	private static final int TYPE_DONES_ITEM = TYPE_DONES_HEADER + 1;
	private static final int TYPE_DONES_FOOTER = TYPE_DONES_ITEM + 1;
	
	public static final int EDIT_NULL = 0;
	public static final int EDIT_TODOS = 1;
	public static final int EDIT_DONES = EDIT_TODOS << 1 ;
	
	private static final float ALPHA_DONE = 0.6f;
    private static final float ALPHA_TODO = 1.0f;
    private static final float ALPHA_DISABLE = 0.4f;
    private static final float ALPHA_ENABLE = 1.0f;
    private static final int ALPHA_TRANSPARENT = 0;
    private static final int ALPHA_OPAQUE = 255;
    
	private Context mContext = null ;
	private LayoutInflater mInflater = null ;
	private TodoAsyncQuery mAsyncQuery = null ;
	
	private View mTodosHeaderView = null;
	private View mTodosFooterView = null;
	private View mDonesHeaderView = null;
	private View mDonesFooterView = null;
	
	// the data display in list.
	private ArrayList<TodoInfo> mTodosDataSource = new ArrayList<TodoInfo>();
	// the data display in list.
	private ArrayList<TodoInfo> mDoneDataSource = new ArrayList<TodoInfo>();
	
	private int mEditType = EDIT_NULL;
	private boolean mTodosExpand = true;
	private boolean mDonesExpand = true;
	// assume the Adapter should loading items form DB first.
    private boolean mIsLoadingData = false;
	
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
	
	static class HeaderHolder {
		TextView mHeaderTitle = null;
		ImageView mBtnExpand = null;
	}
	
	static class FooterHolder {
		TextView mFooterHelper = null;
	}
	
	static class ViewHolder {
		TextView mTofoInfoText = null;
		ImageView mTofoInfoState = null;
		TextView mTodoInfoDueDate = null;
		CheckBox mTodoInfoCheckBox = null;
		ImageView mChangeInfoState = null;
	}
	
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
		int count = 2;//Todos' Header,Dones' Header
		if(mTodosDataSource.isEmpty()){
			count++; //No item
		}else{
			count += (mTodosExpand ? mTodosDataSource.size() : 0);
		}
		
		if(mDoneDataSource.isEmpty()){
			count++; //No item
		}else{
			count+= (mDonesExpand ? mDoneDataSource.size() : 0);
		}
		LogUtils.d(TAG, "getCount()= " + count);
		return count;
	}

	@Override
	public TodoInfo getItem(int position) {
		if(mTodosExpand){
			int index = position - 1;
			if(index < 0){
				return null;
			}else if (index < mTodosDataSource.size()){
				return mTodosDataSource.get(index);
			}
		}
		
		if(mDonesExpand){
			int index = 0;
			if(mTodosDataSource.isEmpty()){
				index = position - 3;
			}else{
				if(mTodosExpand){
					index = position - 2 - mTodosDataSource.size();
				}else{
					index = position - 2;
				}
				
				if(index >= 0 && index < mDoneDataSource.size()){
					return mDoneDataSource.get(index);
				}
			}
			
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LogUtils.d(TAG, "getView");
		int viewType = getItemViewType(position); 
		switch(viewType){
			case TYPE_TODOS_HEADER:
				updateTodosHeaderView();
			case TYPE_TODOS_FOOTER :
				updateTodosFooterView();
			case TYPE_DONES_HEADER:
				updateDonesHeaderView();
			case TYPE_DONES_FOOTER:
				updateDonesFooterView();
			case TYPE_TODOS_ITEM :
			case TYPE_DONES_ITEM :
			default :
				return getItemView(position, convertView, parent);
		}
	}
	
	@Override
	public int getItemViewType(int position) {
		int viewType = -1;
		final int mTodosDataSize = mTodosDataSource.size();
		final int mTodosShowSize = mTodosExpand ? mTodosDataSource.size() : 0;
		if(position == 0){
			viewType = TYPE_TODOS_HEADER;
		}else if(position == 1 && mTodosDataSize == 0){
			viewType = TYPE_TODOS_FOOTER;
		}else if(position <= mTodosShowSize){
			viewType = TYPE_TODOS_ITEM;
		}else if((mTodosDataSize == 0 && position ==2)
				|| (mTodosDataSize != 0 && position == mTodosShowSize + 1)){
			viewType = TYPE_DONES_HEADER;
			
		}else if(mDoneDataSource.isEmpty()){
			viewType = TYPE_DONES_FOOTER;
		}else{
			viewType = TYPE_DONES_ITEM;
		}
		return viewType;
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
		
		updateHeaderNumberText();
        notifyDataSetChanged();
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
	
	private void updateHeaderNumberText(){
		LogUtils.d(TAG, "updateHeaderNumberText()");
		TodosActivity activity = (TodosActivity) mContext;
		if(mTodosDataSource.isEmpty() && mDoneDataSource.isEmpty()){
			activity.updateHeaderNumberText(EMPTY_TEXT);
		}else{
			//// If todos' count is more than 999, we display "999+"
			int count = mTodosDataSource.size();
			
		}
	}
	
	private void updateTodosHeaderView(){
		StringBuffer todosTitle = new StringBuffer();
		todosTitle.append(mContext.getResources().getString(R.string.todos_title));
		todosTitle.append("(").append(mTodosDataSource.size()).append(")");
		HeaderHolder todosHolder = null;
		if(mTodosHeaderView == null){
			todosHolder = new HeaderHolder();
			mTodosHeaderView = mInflater.inflate(R.layout.list_header, null);
			todosHolder.mHeaderTitle = (TextView) mTodosHeaderView.findViewById(R.id.list_title);
			todosHolder.mBtnExpand = (ImageView) mTodosHeaderView.findViewById(R.id.btn_expand);
			mTodosHeaderView.setTag(todosHolder);
		}else{
			todosHolder = (HeaderHolder) mTodosHeaderView.getTag();
		}
		todosHolder.mHeaderTitle.setText(todosTitle.toString());
		
		if(mTodosExpand){
			todosHolder.mBtnExpand.setImageResource(R.drawable.ic_expand_open);
		}else{
			todosHolder.mBtnExpand.setImageResource(R.drawable.ic_expand_close);
		}
		// no items in todos list, set the button transparent and un-clickable
		if(mTodosDataSource.isEmpty()){
			todosHolder.mBtnExpand.setAlpha(0f);
			todosHolder.mBtnExpand.setClickable(true);
		}else{
			todosHolder.mBtnExpand.setAlpha(1f);
            todosHolder.mBtnExpand.setClickable(false);
		}
		mTodosHeaderView.setBackgroundColor(mContext.getResources().getColor(R.color.ListHeaderBgColor));
	}
	
	private void updateTodosFooterView(){
		String todosInfo = mContext.getResources().getString(R.string.todos_empty_info);
		FooterHolder todoFooterHolder = null;
		if(todoFooterHolder == null){
			todoFooterHolder = new FooterHolder();
			mTodosFooterView = mInflater.inflate(R.layout.list_footer, null);
			todoFooterHolder.mFooterHelper = (TextView) mTodosFooterView
					.findViewById(R.id.footer_info);
			mTodosFooterView.setTag(todoFooterHolder);
		}else{
			todoFooterHolder = (FooterHolder) mTodosFooterView.getTag();
		}
		todoFooterHolder.mFooterHelper.setText(todosInfo);
		updateFooterViewState(mTodosFooterView);
	}
	
	private void updateDonesHeaderView(){
		StringBuffer donesTitle = new StringBuffer();
		donesTitle.append(mContext.getResources().getString(R.string.dones_title));
		donesTitle.append("(").append(mDoneDataSource.size()).append(")");
		HeaderHolder donesHolder = null;
		if(mDonesHeaderView == null){
			donesHolder = new HeaderHolder();
			mDonesHeaderView = mInflater.inflate(R.layout.list_header, null);
			donesHolder.mHeaderTitle = (TextView) mDonesHeaderView.findViewById(R.id.list_title);
			donesHolder.mBtnExpand = (ImageView) mDonesHeaderView.findViewById(R.id.btn_expand);
			mDonesHeaderView.setTag(donesHolder);
		}else{
			donesHolder = (HeaderHolder) mDonesHeaderView.getTag();
		}
		donesHolder.mHeaderTitle.setText(donesTitle.toString());
		if (mDonesExpand) {
            donesHolder.mBtnExpand.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_expand_open));
        } else {
            donesHolder.mBtnExpand.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_expand_close));
        }
		
		// no items in dones list, set the button transparent and un-clickable
        if (mDoneDataSource.isEmpty()) {
            donesHolder.mBtnExpand.setAlpha(0f);
            donesHolder.mBtnExpand.setClickable(true);
        } else {
            donesHolder.mBtnExpand.setAlpha(1f);
            donesHolder.mBtnExpand.setClickable(false);
            mDonesHeaderView.setBackgroundColor(mContext.getResources().getColor(R.color.ListHeaderBgColor));
        }
	}
	
	private void updateDonesFooterView(){
		String donesInfo = mContext.getResources().getString(R.string.todos_empty_info);
		FooterHolder donesFooterHolder = null;
		if(donesFooterHolder == null){
			donesFooterHolder = new FooterHolder();
			mDonesFooterView = mInflater.inflate(R.layout.list_footer, null);
			donesFooterHolder.mFooterHelper = (TextView) mDonesFooterView
					.findViewById(R.id.footer_info);
			mDonesFooterView.setTag(donesFooterHolder);
		}else{
			donesFooterHolder = (FooterHolder) mDonesFooterView.getTag();
		}
		donesFooterHolder.mFooterHelper.setText(donesInfo);
		updateFooterViewState(mDonesFooterView);
	}
	
	private View getItemView(int position, View convertView, ViewGroup parent){
		return null;
	}
	
	public boolean isEditing(){
		return mEditType == EDIT_DONES || mEditType == EDIT_DONES;
	}
	
	public void updateFooterViewState(View footerView){
		if(isEditing()){
			footerView.setEnabled(false);
			footerView.setAlpha(ALPHA_DISABLE);
		}else{
			footerView.setEnabled(true);
			footerView.setAlpha(ALPHA_ENABLE);
		}
	}
}
