package com.xiaohei.auser.wenliapp.entity;


public class WeekEvent {
	public boolean isRead;

	public WeekEvent(boolean isRead) {
		this.isRead = isRead;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean read) {
		isRead = read;
	}

}
