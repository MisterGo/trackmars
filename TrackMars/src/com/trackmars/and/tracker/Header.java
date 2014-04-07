package com.trackmars.and.tracker;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

public class Header extends Fragment {
	
	// settings
	// interval 0=0,5min; 1=1min; 2=2min; 3=5min; 4=10min; 5=30min; 6=1hour
	final public static String PREF_INTERVAL = "interval";
	final public static String PREFERENCES_NAME = "settings";
	
	private Integer interval = 1 ; // default 1=1min
	
    // spinner to interval select
	private String[] data;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_header,
		        container, false);
		
		ImageButton menuBtn = (ImageButton)view.findViewById(R.id.imageButtonSettings);
		
		menuBtn.setOnClickListener(new ImageButton.OnClickListener(){

			@Override
			public void onClick(View v) {
				showSettings(v);
			}
			
		});

		
	    data = new String[]{"0.5", "1", "2", "5", "10", "30", "1" + getActivity().getResources().getString(R.string.hour1)};		    

		Button intervalButton = (Button) view.findViewById(R.id.headerIntervalButton);
		intervalButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				showSettings(v);
			}
			
		});
		
		getSettings();
		intervalButton.setText(getResources().getString(R.string.interval) + " " + data[interval]);
		
		return view;
	
	}

	
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder binder) {
        	((TrackRecorderService.ManagerBinder) binder).getMe().setInterval(interval);
        }
        public void onServiceDisconnected(ComponentName className) {
        }
    };    
	
	
	private void getSettings() {
		SharedPreferences sPref = getActivity().getSharedPreferences(PREFERENCES_NAME, getActivity().MODE_PRIVATE);
	    interval = sPref.getInt(PREF_INTERVAL, 1);		
	}
	
	private void saveSettings() {
		SharedPreferences sPref = getActivity().getSharedPreferences(PREFERENCES_NAME, getActivity().MODE_PRIVATE);
	    Editor ed = sPref.edit();
	    ed.putInt(PREF_INTERVAL, this.interval);
	    ed.commit();
	}
	
	public void showSettings(View v) {

		getSettings();
		
		if (v.getId() == R.id.imageButtonSettings || v.getId() == R.id.headerIntervalButton) {
			
			// creating popup window from XML layout
			LayoutInflater layoutInflater = (LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View popupView = layoutInflater.inflate(R.layout.settings, null);
		    
		    final PopupWindow popupWindow = new PopupWindow(
		               popupView, 
		               LayoutParams.MATCH_PARENT,  
		               LayoutParams.WRAP_CONTENT);  

		    
		    
		    // adding popup window views processors
		    //////////////////////////////////////////////////////////////////////////////////
		     
		    // button "back"
		    ImageButton btnDismiss = (ImageButton)popupView.findViewById(R.id.buttonBack);
		    btnDismiss.setOnClickListener(new Button.OnClickListener(){
			     @Override
			     public void onClick(View v) {
			    	 // TODO Auto-generated method stub
			    	 popupWindow.dismiss();
			     }
		     });
		    
		    // button "ok"
		    ImageButton btnOk = (ImageButton) popupView.findViewById(R.id.buttonOk);
		    btnOk.setOnClickListener(new Button.OnClickListener(){
			     @Override
			     public void onClick(View v) {
			    	 saveSettings();

			 	     Intent intent = new Intent(getActivity().getApplicationContext(), TrackRecorderService.class);
				     getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
				     
			    	 popupWindow.dismiss();
			     }
		     });

		    
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data);
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        
	        Spinner spinner = (Spinner) popupView.findViewById(R.id.SpinnerInterval);
	        spinner.setAdapter(adapter);
	        // заголовок
	        spinner.setPrompt("Title");
	        spinner.setSelection(interval);
	        
	        spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
	            @Override
	            public void onItemSelected(AdapterView<?> parent, View view,
	                int position, long id) {
	              // показываем позиция нажатого элемента
	              Toast.makeText(getActivity(), "Position = " + position, Toast.LENGTH_SHORT).show();
	              interval = position;
	            }
	            @Override
	            public void onNothingSelected(AdapterView<?> arg0) {
	            }
	          });
	        
		    //////////////////////////////////////////////////////////////////////////////////
	        
		    /// show popup window
		    popupWindow.showAsDropDown(v, 50, 0);
		}  

	}
}
		
