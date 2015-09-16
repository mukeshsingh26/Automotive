package com.example.contactmanager;

public class AppInfo {
   
    private String mName;
    private String mphone;
    private String mphoneType;
    private String memail;
    private String memailType;
 
    public AppInfo(String name, String phoneType , String phone, String emailType, String email) {
        
        mName = name;
        mphone = phone;
        memail = email;
        mphoneType = phoneType;
        memailType = emailType;
    }
 
   
    public void setName(String name) {
    mName = name;
    }
    public String getName() {
        return mName;
    }


	public String getphone() {
		return mphone;
	}


	public void setphone(String mphone) {
		this.mphone = mphone;
	}


	public String getemail() {
		return memail;
	}


	public void setemail(String memail) {
		this.memail = memail;
	}


	public String getphoneType() {
		return mphoneType;
	}


	public void setphoneType(String mphoneType) {
		this.mphoneType = mphoneType;
	}


	public String getemailType() {
		return memailType;
	}


	public void setemailType(String memailType) {
		this.memailType = memailType;
	}
 
}
