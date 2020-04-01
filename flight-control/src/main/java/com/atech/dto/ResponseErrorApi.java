package com.atech.dto;


public class ResponseErrorApi{
	String msgUser;
	String msgDev;
	
	public ResponseErrorApi(String msgUser, String msgDev) {
		
		this.msgUser = msgUser;
		this.msgDev = msgDev;
	}
	public String getMsgUser() {
		return msgUser;
	}
	public void setMsgUser(String msgUser) {
		this.msgUser = msgUser;
	}
	public String getMsgDev() {
		return msgDev;
	}
	public void setMsgDev(String msgDev) {
		this.msgDev = msgDev;
	}	
}