package com.huaqin.notes;

import java.io.Serializable;

import com.huaqin.notes.provider.TodosDatabaseHelper.TodoColumn;

import android.database.Cursor;

public class TodoInfo implements Serializable {
	private static final String TAG = "TodoInfo";

	public static final String STATUS_TODO = "1";
	public static final String STATUS_DONE = "2";

	private String mId = null;
	private String mTitle = "";
	private String mDescription = "";

	private String mStatus = STATUS_TODO;
	private String mDueDate = "0";
	private String mCreateTime = null;
	private String mCompleteTime = "0";

	public TodoInfo() {

	}

	public void copy(TodoInfo info) {
		if (info != null) {
			mId = info.mId;
			mTitle = info.mTitle;
			mDescription = info.mDescription;
			mStatus = info.mStatus;
			mDueDate = info.mDueDate;
			mCreateTime = info.mCreateTime;
			mCompleteTime = info.mCompleteTime;
		}
	}

	public void clear() {
		mId = null;
		mTitle = "";
		mDescription = "";
		mStatus = STATUS_DONE;
		mDueDate = "0";
		mCreateTime = null;
		mCompleteTime = "0";
	}

	/**
	 * extract cursor content, to fill a new TodoInfo object, then return it.
	 * 
	 * @param cursor
	 *            an valid cursor. if not valid, empty TodoInfo will be created
	 * @return TodoInfo
	 */
	public static TodoInfo makeTodoInfoFromCusor(Cursor cursor) {
		String id;
		String title;
		String description;
		String status;
		String dueDate;
		String createTime;
		String completeTime;
		TodoInfo todoInfo = new TodoInfo();

		id = Utils.getColumnByName(TodoColumn.ID, cursor);
		title = Utils.getColumnByName(TodoColumn.TITLE, cursor);
		description = Utils.getColumnByName(TodoColumn.DESCRIPTION, cursor);
		status = Utils.getColumnByName(TodoColumn.STATUS, cursor);
		dueDate = Utils.getColumnByName(TodoColumn.DUE_DATE, cursor);
        createTime = Utils.getColumnByName(TodoColumn.CREATE_TIME, cursor);
        completeTime = Utils.getColumnByName(TodoColumn.COMPLETE_TIME, cursor);
        
        todoInfo.mId = id;
        todoInfo.mTitle = title == null ? "" : title ;
        todoInfo.mDescription = description == null ? "" : title ;
        todoInfo.mStatus = status;
        todoInfo.mDueDate = dueDate;
        todoInfo.mCreateTime = createTime ;
        todoInfo.mCompleteTime =completeTime;
		return todoInfo;
	}

	public String getId() {
		return mId;
	}

	public void setId(String mId) {
		this.mId = mId;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}

	public String getStatus() {
		return mStatus;
	}

	public void setStatus(String mStatus) {
		this.mStatus = mStatus;
	}

	long getDueDate() {
        return Long.parseLong(mDueDate);
    }

	public void setDueDate(String mDueDate) {
		this.mDueDate = mDueDate;
	}

	public String getCreateTime() {
		return mCreateTime;
	}

	public void setCreateTime(String mCreateTime) {
		this.mCreateTime = mCreateTime;
	}

	long getCompleteTime() {
        return Long.parseLong(mCompleteTime);
    }

	public void setCompleteTime(String mCompleteTime) {
		this.mCompleteTime = mCompleteTime;
	}

}
