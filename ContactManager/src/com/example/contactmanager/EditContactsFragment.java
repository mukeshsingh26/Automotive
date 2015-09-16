package com.example.contactmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class EditContactsFragment extends Fragment {
	
		
	MainActivity mainactivity;
	Resources resources;
	private View view;
	private Button viewcontact,addcontact,modifycontact,deletecontact;
	ProgressBar contacts_progressbar;
	EditText name,phone,email;
	String strname,strphone,stremail;
	Button modify ;
	Spinner spinneremailtype,spinnerphonetype;
	ArrayAdapter<String> spinner_email_adapter,spinner_phone_adapter;
	ArrayList<String> type_email = new ArrayList<String>();
	ArrayList<String> type_phone = new ArrayList<String>();
	String phonetypeitem ,emailtypeitem;
	int spinner_sel_email,spinner_sel_phone;
	
	
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
	
		
		
		view = inflater.inflate(R.layout.fragment_edit_contacts, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		initializetheViews();
	
	
		//queueHandlerMsgToLoadData(getArguments().getBoolean("Isync"));
		
	}

	private void initializetheViews() {
		// TODO Auto-generated method stub
		
		String edit_item_name = getArguments().getString("ITEM_NAME", "");
		String edit_item_phone = getArguments().getString("ITEM_PHONE", "");
		String edit_item_email = getArguments().getString("ITEM_EMAIL", "");
		String edit_item_phonetype = getArguments().getString("ITEM_PHONE_TYPE", "");
		String edit_item_emailtype = getArguments().getString("ITEM_EMAIL_TYPE", "");
		
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
		

		
		 contacts_progressbar = (ProgressBar)mainactivity.findViewById(R.id.contacts_addprogressbar);
			
		name = (EditText)mainactivity.findViewById(R.id.add_list_name_text);
		name.setText(edit_item_name);
		phone = (EditText)mainactivity.findViewById(R.id.add_list_phone_text);
		phone.setText(edit_item_phone);
		email = (EditText)mainactivity.findViewById(R.id.add_list_email_text);
		email.setText(edit_item_email);
		modify = (Button)mainactivity.findViewById(R.id.btnmodify);
		modify.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				contacts_progressbar.setVisibility(View.VISIBLE);
				strname = name.getText().toString();
				strphone = phone.getText().toString();
				stremail = email.getText().toString();
				
				 Log.i("AddContacts", "NAME : "+strname);
				 Log.i("AddContacts", "PHONE : "+strphone);
				 Log.i("AddContacts", "Email  : "+stremail);
				 if(strname.equals("") || strphone.equals("") || stremail.equals("") || phonetypeitem.equals("") || emailtypeitem.equals("") )
				 {
					 Toast.makeText(mainactivity,"Please Enter all the fields", Toast.LENGTH_SHORT).show();
					 contacts_progressbar.setVisibility(View.GONE);
				 }
				 else
				 {
			
					 updateContact(strname, strphone, stremail,phonetypeitem , emailtypeitem);
				 }
			}
		});
		
		// Spinner element
		spinneremailtype = (Spinner)mainactivity.findViewById(R.id.spinner_email);
		
				spinnerphonetype = (Spinner)mainactivity.findViewById(R.id.spinner_phone);
		
		// Spinner click listener
		spinneremailtype.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				((TextView) parent.getChildAt(0)).setTextColor(resources.getColor(R.color.blue));
				((TextView) parent.getChildAt(0)).setTypeface(null, Typeface.BOLD);
				((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);
				
				 // On selecting a spinner item
		         emailtypeitem = parent.getItemAtPosition(position).toString();
		 
		        // Showing selected spinner item
		     //   Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
		 
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
 
        // Spinner Drop down elements
        
		type_email.add("Home");
		type_email.add("Work");
		type_email.add("Other");
		type_email.add("Custom");
		type_email.add("Mobile");


		for(int i=0;i<type_email.size();i++)
		{
			if(type_email.get(i).equals(edit_item_emailtype))
			{
				spinner_sel_email = i;
			}
		}
		
		 spinner_email_adapter = new ArrayAdapter<String>(mainactivity, android.R.layout.simple_spinner_item, type_email);
		 
		// Drop down layout style - list view with radio button
		 spinner_email_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	 
	        // attaching data adapter to spinner
		 spinneremailtype.setAdapter(spinner_email_adapter);
		 spinneremailtype.setSelection(spinner_sel_email);
		 
		 
		 
		// Spinner click listener
			spinnerphonetype.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					
					((TextView) parent.getChildAt(0)).setTextColor(resources.getColor(R.color.blue));
					((TextView) parent.getChildAt(0)).setTypeface(null, Typeface.BOLD);
					((TextView) parent.getChildAt(0)).setGravity(Gravity.CENTER);
					
					 // On selecting a spinner item
			         phonetypeitem = parent.getItemAtPosition(position).toString();
			 
			        // Showing selected spinner item
			     //   Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
			 
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	 
	        // Spinner Drop down elements
	        
			type_phone.add("Home");
			type_phone.add("Work");
			type_phone.add("Other");
			type_phone.add("Custom");
			type_phone.add("Mobile");
			type_phone.add("Main");
			
			for(int i=0;i<type_phone.size();i++)
			{
				if(type_phone.get(i).equals(edit_item_phonetype))
				{
					spinner_sel_phone = i;
				}
			}
			
			
			spinner_phone_adapter = new ArrayAdapter<String>(mainactivity, android.R.layout.simple_spinner_item, type_phone);
			 
			// Drop down layout style - list view with radio button
			spinner_phone_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 
		        // attaching data adapter to spinner
			 spinnerphonetype.setAdapter(spinner_phone_adapter);
			 spinnerphonetype.setSelection(spinner_sel_phone);
		
	
		
	}
	
	private void updateContact(String name, String phone, String email, String phonetypeitem, String emailtypeitem) {
		
		int phonetype = 0;
    	int emailtype = 0;
    	
    	if(phonetypeitem.equals("Home"))
    	{    	
    	phonetype = ContactsContract.CommonDataKinds.Phone.TYPE_HOME;
    	}
    	else if(phonetypeitem.equals("Work"))
    	{    	
    	phonetype = ContactsContract.CommonDataKinds.Phone.TYPE_WORK;
    	}
    	else if(phonetypeitem.equals("Other"))
    	{    	
    	phonetype = ContactsContract.CommonDataKinds.Phone.TYPE_OTHER;
    	}
    	else if(phonetypeitem.equals("Custom"))
    	{    	
    	phonetype = ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM;
    	}
    	else if(phonetypeitem.equals("Mobile"))
    	{    	
    	phonetype = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
    	}
    	else if(phonetypeitem.equals("Main"))
    	{    	
    	phonetype = ContactsContract.CommonDataKinds.Phone.TYPE_MAIN;
    	}
    	
    	

    	if(emailtypeitem.equals("Home"))
    	{    	
    		emailtype = ContactsContract.CommonDataKinds.Email.TYPE_HOME;
    	}
    	else if(emailtypeitem.equals("Work"))
    	{    	
    		emailtype = ContactsContract.CommonDataKinds.Email.TYPE_WORK;
    	}
    	else if(emailtypeitem.equals("Other"))
    	{    	
    		emailtype = ContactsContract.CommonDataKinds.Email.TYPE_OTHER;
    	}
    	else if(emailtypeitem.equals("Custom"))
    	{    	
    		emailtype = ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM;
    	}
    	else if(emailtypeitem.equals("Mobile"))
    	{    	
    		emailtype = ContactsContract.CommonDataKinds.Email.TYPE_MOBILE;
    	}
    	
    	
		
    	ContentResolver cr = mainactivity.getContentResolver();
 
        String wherephonetype = ContactsContract.Data.DISPLAY_NAME + " = ? AND " + 
        			ContactsContract.Data.MIMETYPE + " = ? AND " +
        			String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE) + " = ?";
        
        String wherephone= ContactsContract.Data.DISPLAY_NAME + " = ? AND " + 
    			ContactsContract.Data.MIMETYPE + " = ? AND " +
    			String.valueOf(ContactsContract.CommonDataKinds.Phone.NUMBER) + " = ?";
        
        String whereemailtype = ContactsContract.Data.DISPLAY_NAME + " = ? AND " + 
    			ContactsContract.Data.MIMETYPE + " = ? AND " +
    			String.valueOf(ContactsContract.CommonDataKinds.Email.TYPE) + " = ?";
    
      String whereemail= ContactsContract.Data.DISPLAY_NAME + " = ? AND " + 
			ContactsContract.Data.MIMETYPE + " = ? AND " +
			String.valueOf(ContactsContract.CommonDataKinds.Email.ADDRESS) + " = ?";
        
        
        String[] paramsphonetype = new String[] {name,
        		ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
        		String.valueOf(phonetype)};
        
        String[] paramsemailtype = new String[] {name,
        		ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
        		String.valueOf(emailtype)};
        
        String[] paramsphone = new String[] {name,
        		ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
        		String.valueOf(phone)};
        
        String[] paramsemail = new String[] {name,
        		ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
        		String.valueOf(email)};

        Cursor phonetypeCur = cr.query(ContactsContract.Data.CONTENT_URI, null, wherephonetype, paramsphonetype, null);
        Cursor emailtypeCur = cr.query(ContactsContract.Data.CONTENT_URI, null, whereemailtype, paramsemailtype, null);
        
        Cursor phoneCur = cr.query(ContactsContract.Data.CONTENT_URI, null, wherephone, paramsphone, null);
        Cursor emailCur = cr.query(ContactsContract.Data.CONTENT_URI, null, whereemail, paramsemail, null);
      
        
        
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        
        if ( (null == phonetypeCur)  ) {
        	createContact(name, phone, email,phonetypeitem, emailtypeitem);
        } else {
        	ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
        	        .withSelection(wherephonetype, paramsphonetype)
        	        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
        	         .build());
        	
        	
        }
        
        phonetypeCur.close();
        
        if ( (null == phoneCur)  ) {
        	createContact(name, phone, email,phonetypeitem, emailtypeitem);
        } else {
        	
        	ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
        	        .withSelection(wherephone, paramsphone)        	        
        	        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, phonetype)
        	        .build());
        	
        }
        
        phoneCur.close();
        
        if ( (null == emailtypeCur)  ) {
        	createContact(name, phone, email,phonetypeitem, emailtypeitem);
        } else {
                	ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
        	        .withSelection(whereemailtype, paramsemailtype)
        	        .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, email)        	        
        	        .build());
                	
                	
        }
        
        emailtypeCur.close();
        
        if ( (null == emailCur)  ) {
        	createContact(name, phone, email,phonetypeitem, emailtypeitem);
        } else {
                	
                	
                	ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                	        .withSelection(whereemail, paramsemail)
                	        .withValue(ContactsContract.CommonDataKinds.Email.TYPE, emailtype)
                	        .build());
        }
        
        emailCur.close();
        
        try {
			cr.applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Toast.makeText(mainactivity, "Updated the Conatct with Display Name : "+name, Toast.LENGTH_SHORT).show();
		contacts_progressbar.setVisibility(View.GONE);
    }
	
	private void createContact(String name, String phone, String email, String phonetypeitem , String emailtypeitem) {
    	ContentResolver cr = mainactivity.getContentResolver();
    	int phonetype = 0;
    	int emailtype = 0;
    	
    	if(phonetypeitem.equals("Home"))
    	{    	
    	phonetype = ContactsContract.CommonDataKinds.Phone.TYPE_HOME;
    	}
    	else if(phonetypeitem.equals("Work"))
    	{    	
    	phonetype = ContactsContract.CommonDataKinds.Phone.TYPE_WORK;
    	}
    	else if(phonetypeitem.equals("Other"))
    	{    	
    	phonetype = ContactsContract.CommonDataKinds.Phone.TYPE_OTHER;
    	}
    	else if(phonetypeitem.equals("Custom"))
    	{    	
    	phonetype = ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM;
    	}
    	else if(phonetypeitem.equals("Mobile"))
    	{    	
    	phonetype = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
    	}
    	else if(phonetypeitem.equals("Main"))
    	{    	
    	phonetype = ContactsContract.CommonDataKinds.Phone.TYPE_MAIN;
    	}
    	
    	

    	if(emailtypeitem.equals("Home"))
    	{    	
    		emailtype = ContactsContract.CommonDataKinds.Email.TYPE_HOME;
    	}
    	else if(emailtypeitem.equals("Work"))
    	{    	
    		emailtype = ContactsContract.CommonDataKinds.Email.TYPE_WORK;
    	}
    	else if(emailtypeitem.equals("Other"))
    	{    	
    		emailtype = ContactsContract.CommonDataKinds.Email.TYPE_OTHER;
    	}
    	else if(emailtypeitem.equals("Custom"))
    	{    	
    		emailtype = ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM;
    	}
    	else if(emailtypeitem.equals("Mobile"))
    	{    	
    		emailtype = ContactsContract.CommonDataKinds.Email.TYPE_MOBILE;
    	}
    	
    	
    	
    	Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        
    	if (cur.getCount() > 0) {
        	while (cur.moveToNext()) {
        		String existName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        		if (existName.contains(name)) {
                	Toast.makeText(mainactivity,"The contact name: " + name + " already exists", Toast.LENGTH_SHORT).show();
                	contacts_progressbar.setVisibility(View.GONE);
                	return;        			
        		}
        	}
    	}
    	
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, "accountname@gmail.com")
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, "com.google")
                .build());
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                .build());
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, phonetype)
                .build());
        
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, email)
                .withValue(ContactsContract.CommonDataKinds.Email.TYPE, emailtype)
                .build());

        
        try {
			cr.applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	Toast.makeText(mainactivity, "Created a new contact with name: " + name + " , Phone No: " + phone + " and Email: " + email, Toast.LENGTH_SHORT).show();
        contacts_progressbar.setVisibility(View.GONE);
    }

}
