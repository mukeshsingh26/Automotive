package com.example.contactmanager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	
	Button viewcontact , addcontact,modifycontact , deletecontact;
	Resources resources;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.contactmanager);
	        resources = getResources();
	        initializetheViews();
	        
	 }

	private void initializetheViews() {
		// TODO Auto-generated method stub
		 viewcontact = (Button)findViewById(R.id.btnviewcontact);
         addcontact = (Button)findViewById(R.id.btnsddcontact);
         modifycontact = (Button)findViewById(R.id.btnmodifycontact);
         deletecontact = (Button)findViewById(R.id.btndeletecontact);
        viewcontact.setOnClickListener(MainActivity.this);
        addcontact.setOnClickListener(MainActivity.this);
        modifycontact.setOnClickListener(MainActivity.this);
        deletecontact.setOnClickListener(MainActivity.this);
        
        
        createFragmentAndCommit(new ViewContactsFragment());
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.btnviewcontact:
			
			createFragmentAndCommit(new ViewContactsFragment());
			break;
		case R.id.btnsddcontact:
			createFragmentAndCommit(new AddContactsFragment());
			break;
		case R.id.btnmodifycontact:
			createFragmentAndCommit(new ModifyContactsFragment());
			break;
		case R.id.btndeletecontact:
			createFragmentAndCommit(new DeleteContactsFragment());
			break;
		}
		
	}
	
	private String fragmentTag;
	private void createFragmentAndCommit(Fragment newFragment) {

		// Create new fragment and transaction
		
		fragmentTag = newFragment.getClass().getName();
	
		FragmentManager manager = getFragmentManager();

		// fragment not in back stack, create it.)
		FragmentTransaction transaction = manager.beginTransaction();
		//transaction.setCustomAnimations(R.anim.slide_in_left1, R.anim.slide_out_left1);

		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		transaction.replace(R.id.fragment_container, newFragment, fragmentTag);

		// transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		//transaction.addToBackStack(null);

		// Commit the transaction
		transaction.commit();

	}

}
