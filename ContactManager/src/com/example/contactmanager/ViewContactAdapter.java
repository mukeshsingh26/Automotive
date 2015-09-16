package com.example.contactmanager;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


	
		public class ViewContactAdapter extends BaseAdapter {
		    private Context mContext;
		    private List<AppInfo> mListAppInfo;
		 
		    public ViewContactAdapter(Context context, List<AppInfo> list) {
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
		   	public View getView(int position, View convertView, ViewGroup parent) {
		   	    AppInfo entry = mListAppInfo.get(position);
		   	    ViewHolder holder = null;
		   	 
		   	    if(convertView == null) {
		   	        LayoutInflater inflater = LayoutInflater.from(mContext);
		   	        convertView = inflater.inflate(R.layout.view_list_item, null);
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
		   	        convertView.setTag(holder);
		   	    }
		   	    else {
		   	        holder = (ViewHolder)convertView.getTag();
		   	    }
		   	 
		   	 
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
		   	   
		   	}
			
			}
		

