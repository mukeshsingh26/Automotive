package com.example.contactmanager;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class AlphabetApapter extends ArrayAdapter<String> {
	LayoutInflater inflate;
	
	public AlphabetApapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		inflate=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	public void setData(String[] alphabetical_letters){
		for(String str:alphabetical_letters){
			add(str);
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		
		View view = convertView;
		if(view!=null){
			view=convertView;
		}else{
			view=inflate.inflate(R.layout.filter_listview_row, null);
		}
		
		String str=getItem(position);
		
		TextView btn_alphabet=(TextView)view.findViewById(R.id.txt_filter_contact);
		btn_alphabet.setTypeface(null, Typeface.BOLD);
		btn_alphabet.setText(str);
		return view;
	}
	 
}
