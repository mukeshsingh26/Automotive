package com.example.contactmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ModifyContactsFragment extends Fragment {
	
	MainActivity mainactivity;
	Resources resources;
	private View view;
	private Button viewcontact,addcontact,modifycontact,deletecontact;
	ListView expandableContactListView,mList;
	List<AppInfo> listAppInfo;
	ProgressBar contacts_progressbar;
	public AlphabetApapter mLettersAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			 mainactivity = (MainActivity) activity;
			resources = getResources();
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/**
		 * Inflate the layout for this fragment
		 */
	
		
		
		view = inflater.inflate(R.layout.fragment_modify_contacts, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		initializetheViews();
	     displayContacts();
	
		
	}
	

	private void initializetheViews() {
		// TODO Auto-generated method stub
		
		contacts_progressbar = (ProgressBar)mainactivity.findViewById(R.id.contacts_progressbar);
		viewcontact = (Button) mainactivity
				.findViewById(R.id.btnviewcontact);
		addcontact = (Button) mainactivity
				.findViewById(R.id.btnsddcontact);
		modifycontact = (Button) mainactivity
				.findViewById(R.id.btnmodifycontact);
		deletecontact = (Button) mainactivity
				.findViewById(R.id.btndeletecontact);
		viewcontact.setSelected(false);
		viewcontact.setTextColor(resources.getColor(R.color.white));
		addcontact.setSelected(false);
		addcontact.setTextColor(resources.getColor(R.color.white));
		modifycontact.setSelected(true);
		modifycontact.setTextColor(resources.getColor(R.color.btn_hit_color));
		deletecontact.setSelected(false);
		deletecontact.setTextColor(resources.getColor(R.color.white));
		
		   listAppInfo = new ArrayList<AppInfo>();
		   mList = (ListView)view.findViewById(R.id.listfilter);
		 expandableContactListView = (ListView) view
				.findViewById(R.id.expandableListView1);
		 
		 
		
		
	}
	
	private void displayContacts() {
		// TODO Auto-generated method stub	
		String email = "";
		String stremailtype = "";
		String strphoneType="";
	
		
    	ContentResolver cr = mainactivity.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null,Phone.DISPLAY_NAME + " ASC");
     //   Cursor cursor = cr.query(Phone.CONTENT_URI, null, null, null, Phone.DISPLAY_NAME + " ASC"
        int cur_count=cur.getCount();
        Log.i("ViewContacts", "Cursor Count : "+cur_count);
        ArrayList<String> displayname = new ArrayList<String>();
        ArrayList<String> phone = new ArrayList<String>();
        ArrayList<String> phonesType = new ArrayList<String>();
        ArrayList<String> emails = new ArrayList<String>();
        ArrayList<String> emailsType = new ArrayList<String>();
        if (cur.getCount() > 0) {
        	while (cur.moveToNext()) {
        		String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
        		String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                //Log.i("ViewContacts", "Display name : "+name);
                displayname.add( name);
        		String phoneNo = "";
        		// Log.i("ViewContacts", "HAS PHONE NO :"+(Integer.parseInt(cur.getString(
                  //       cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0));
        		if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
        			
                     Cursor pCur = cr.query(
                    		 ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
                    		 null, 
                    		 ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
                    		 new String[]{id}, null);
                     while (pCur.moveToNext()) {
                    	  phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    	  
                    	  String phoneType = pCur.getString(
                    			  pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                    	  
                    	  switch(Integer.parseInt(phoneType)){
                          case ContactsContract.CommonDataKinds.Phone.TYPE_HOME: 
                       	   strphoneType = "Home";
                               break;
                          case ContactsContract.CommonDataKinds.Phone.TYPE_WORK: 
                        	  strphoneType = "Work";
                               break;
                          case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER: 
                        	  strphoneType = "Other";
                               break;
                          case ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM: 
                        	  strphoneType = "Custom";
                               break;
                          case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE: 
                        	  strphoneType = "Mobile";
                               break;
                          case ContactsContract.CommonDataKinds.Phone.TYPE_MAIN: 
                        	  strphoneType = "Main";
                               break;
                               default:
                            	   strphoneType = "Home";
                            	   break;
                         
           		 	   }
                    	  phonesType.add(strphoneType);
                    	  phone.add(phoneNo);
                    	// Toast.makeText(MainActivity.this, "Name: " + name + ", Phone No: " + phoneNo +" , Email : "+email, Toast.LENGTH_SHORT).show();
                     } 
      	        pCur.close();
      	    }
        		else
        		{
        			phonesType.add(" ");
        			phone.add(" ");        		
        		}
        		
        		Cursor emailCur = cr.query( 
        				ContactsContract.CommonDataKinds.Email.CONTENT_URI, 
        				null,
        				ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", 
        				new String[]{id}, null); 
        		Log.i("ViewContacts", "emailCur : "+emailCur.getCount());
        		if(emailCur.getCount()==0)
        		{
        			
        			emailCur.moveToNext();
        			emailsType.add(" ");
    		 	    emails.add(" ");
        			
        			
        		}
        		else
        		{
        		
        		while (emailCur.moveToNext()) {        		
    			
    		  
        		
        			
        			    // This would allow you get several email addresses
        		            // if the email addresses were stored in an array
        			     email = emailCur.getString(
        		                      emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
        			   
        		 	    String emailType = emailCur.getString(
        		                      emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE)); 
        		 	   
        		 	   switch(Integer.parseInt(emailType)){
                       case ContactsContract.CommonDataKinds.Email.TYPE_HOME: 
                    	   stremailtype = "Home";
                            break;
                       case ContactsContract.CommonDataKinds.Email.TYPE_WORK: 
                    	   stremailtype = "Work";
                            break;
                       case ContactsContract.CommonDataKinds.Email.TYPE_OTHER: 
                    	   stremailtype = "Other";
                            break;
                       case ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM: 
                    	   stremailtype = "Custom";
                            break;
                       case ContactsContract.CommonDataKinds.Email.TYPE_MOBILE: 
                    	   stremailtype = "Mobile";
                            break;
                       default:
                    	   stremailtype = "Home";
                    	   break;
                      
        		 	   }
        		 	   
        		 	
        		 	   
        		 	   
    		          emailsType.add(stremailtype);
        		 	    emails.add(email);
        		}
        		 	} //
        			
        		 	emailCur.close();
        		 	 Log.i("ViewContacts", "Display name : "+name);
        		 	 Log.i("ViewContacts", "Phone No : "+phoneNo);
        			 Log.i("ViewContacts", "Phone Type : "+strphoneType);        
        		 	 Log.i("ViewContacts", "Email : "+email);
        			 	Log.i("ViewContacts", "EmailType : "+stremailtype);
        		 	//Toast.makeText(mainactivity, "Name: " + name + ", Phone No: " + phoneNo +" , Email type : "+stremailtype+" , Email  : "+email, Toast.LENGTH_SHORT).show();
        		 	
        	}
        	
        }
        for(int i=0;i<cur_count;i++)
        {
        	listAppInfo.add(new AppInfo(displayname.get(i),phonesType.get(i),phone.get(i),emailsType.get(i),emails.get(i)));
        	//listAppInfo.add(new AppInfo(displayname.get(i),phone.get(i),""));       
        	}
     
	 
	 expandableContactListView.setAdapter(new ModifyContactAdapter(mainactivity, listAppInfo));
	 contacts_progressbar.setVisibility(View.GONE);

	 bindAlphabetalLettersToList();
		initializeFilterListener();
	}
	
	/*
	 * Filtering the sections according to the click on indices
	 */

	private void initializeFilterListener() {
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			
				int listPosition = 0;
				mList.setSelection(position);
				// mList.setFocusable(true);
				TextView view_txt = (TextView) view
						.findViewById(R.id.txt_filter_contact);
				
				String str = view_txt.getText().toString();

					Cursor cursor = mainactivity.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
			                null, null, null,Phone.DISPLAY_NAME + " ASC");
					
					if (cursor.getCount() > 0) {
						int i = 1;
						
			        	while (cursor.moveToNext()) {
			        		String charSequence = cursor.getString(cursor
									.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			        		i=i+1;
							if (str.equalsIgnoreCase(String.valueOf(charSequence
									.charAt(0)))) {
								listPosition = i;
								break;
			        	}
			        	}
					
					
					}
				
				expandableContactListView.setSelection(listPosition-2);

			}
		});
	}


	/**
	 * alphabetical letters to section index
	 * */
	private void bindAlphabetalLettersToList() {
		
		mList.setFocusable(false);
		mLettersAdapter = new AlphabetApapter(mainactivity, 0);
		mList.setAdapter(mLettersAdapter);
		mLettersAdapter.setData(getAlphabetSet());
	
		mList.getCount();
	}
	
	private static String[] getAlphabetSet() {
		String[] str = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
				"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
				"X", "Y", "Z" };

		return str;
	}
	





}



