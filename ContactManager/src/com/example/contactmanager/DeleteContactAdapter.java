package com.example.contactmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


	
		public class DeleteContactAdapter extends BaseAdapter {
		    private Context mContext;
		    private List<AppInfo> mListAppInfo;
		    
		     ViewHolder holder;
		 
		    public DeleteContactAdapter(Context context, List<AppInfo> list) {
		        mContext = context;
		        mListAppInfo = list;
		    }
		 
	    @Override
		    public int getCount() {
		        return mListAppInfo.size();
		    }
		 
		    @Override
		    public Object getItem(int position) {
		    	
		        return mListAppInfo.get(position);
		    }
		 
		    @Override
		    public long getItemId(int position) {
		        return position;
		    }
		 
		    @Override
		   	public View getView(final int position, View convertView, ViewGroup parent) {
		   	    AppInfo entry = mListAppInfo.get(position);
		   	      holder = null;
		   	 
		   	//    if(convertView == null) {
		   	        LayoutInflater inflater = LayoutInflater.from(mContext);
		   	        convertView = inflater.inflate(R.layout.delete_list_item, null);
		   	        holder = new ViewHolder();
		   	        holder.contact_name_label = (TextView)convertView.findViewById(R.id.list_name_label);
		   	        holder.contact_name_text = (TextView)convertView.findViewById(R.id.list_name_text);
		   	        holder.contact_phone_label = (TextView)convertView.findViewById(R.id.list_phone_label);
		   	        holder.contact_phone_text = (TextView)convertView.findViewById(R.id.list_phone_text);
		   	        holder.contact_email_label = (TextView)convertView.findViewById(R.id.list_email_label);
		   	        holder.contact_email_text = (TextView)convertView.findViewById(R.id.list_email_text);
		   	        holder.contact_emailtype_label = (TextView)convertView.findViewById(R.id.list_emailtype_label);
		   	        holder.contact_emailtype_text = (TextView)convertView.findViewById(R.id.list_emailtype_text);
		        	holder.contact_phonetype_label = (TextView)convertView.findViewById(R.id.list_phonetype_label);
		        	holder.contact_phonetype_text = (TextView)convertView.findViewById(R.id.list_phonetype_text);
		        	holder.deleteContact = (Button)convertView.findViewById(R.id.btn_delete_list);
		        	holder.deleteContact.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							AppInfo entry = mListAppInfo.get(position);
							String name = entry.getName().toString();
							
							deleteContact(name);
							createFragmentAndCommit(new DeleteContactsFragment());
						}
					});
		   	        convertView.setTag(holder);
		   	//    }
		  // 	    else {
		   	//        holder = (ViewHolder)convertView.getTag();
		   	//    }
		   	 
		   	 
		   	    holder.contact_name_text.setText(entry.getName());
		   	 holder.contact_phone_text.setText(entry.getphone());
		   	holder.contact_email_text.setText(entry.getemail());
		   	holder.contact_emailtype_text.setText(entry.getemailType());
		   	holder.contact_phonetype_text.setText(entry.getphoneType());
		   	 
		   	    return convertView;
		   	}
		   	 
		   	static class ViewHolder {
		   	    TextView contact_name_label,contact_name_text,contact_phone_label,contact_phone_text,
		   	    contact_email_label,contact_email_text,contact_phonetype_label,contact_phonetype_text,contact_emailtype_label,contact_emailtype_text;
		   	   Button deleteContact;
		   	}
		   	
		   	private String fragmentTag;
			private void createFragmentAndCommit(Fragment newFragment) {

				// Create new fragment and transaction
				
				
				fragmentTag = newFragment.getClass().getName();
				Activity activity = (Activity) mContext;
			
				FragmentManager manager = activity.getFragmentManager();

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
			
			private void deleteContact(String name) {

				Activity activity = (Activity) mContext;
		    	ContentResolver cr = activity.getContentResolver();
		    	String where = ContactsContract.Data.DISPLAY_NAME + " = ? ";
		    	String[] params = new String[] {name};
		    
		        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		        ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
		    	        .withSelection(where, params)
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

				Toast.makeText(activity, "Deleted the contact with name '" + name +"'", Toast.LENGTH_SHORT).show();
		  notifyDataSetChanged();
	
			}
			
			}
		

