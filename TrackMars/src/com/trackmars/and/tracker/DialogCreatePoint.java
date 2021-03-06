package com.trackmars.and.tracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.trackmars.and.tracker.dataUtils.EntityHelper;
import com.trackmars.and.tracker.model.Point;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class DialogCreatePoint extends Activity {

	double longitude;
	double latitude;
	
	private class AddressObj {
		
		private String line;
		private String addressLine;
		private String locality;
		private String subLocality;
		private String thoroughfare;
		private String SubThoroughfare;
		
		public String getLine() {
			return line;
		}
		public void setLine(String line) {
			this.line = line;
		}
		public String getAddressLine() {
			return addressLine;
		}
		public void setAddressLine(String addressLine) {
			this.addressLine = addressLine;
		}
		public String getLocality() {
			return locality;
		}
		public void setLocality(String locality) {
			this.locality = locality;
		}
		public String getSubLocality() {
			return subLocality;
		}
		public void setSubLocality(String subLocality) {
			this.subLocality = subLocality;
		}
		public String getThoroughfare() {
			return thoroughfare;
		}
		public void setThoroughfare(String thoroughfare) {
			this.thoroughfare = thoroughfare;
		}
		public String getSubThoroughfare() {
			return SubThoroughfare;
		}
		public void setSubThoroughfare(String subThoroughfare) {
			SubThoroughfare = subThoroughfare;
		}
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_create_point);
		
		Bundle extras = getIntent().getExtras();
		longitude = extras.getDouble("long");
		latitude = extras.getDouble("lat");
		

		((TextView) findViewById(R.id.nameThePoint)).setTextSize(20);
		((TextView) findViewById(R.id.chooseTheAddress)).setTextSize(20);
		//String nameThePointText = textView.getText().toString();
		//nameThePointText = "<font size=13>" + nameThePointText + "</font>";
		
		
		
		EditText description = (EditText) findViewById(R.id.address1);
		EditText title = (EditText) findViewById(R.id.editText1);
		
		
		
		List<AddressObj> addressObjs = setAddressInitially();
		
		if (addressObjs != null && addressObjs.size() > 0) {
		
			((EditText) findViewById(R.id.address1)).setVisibility(View.VISIBLE);
			((ImageButton) findViewById(R.id.check1)).setVisibility(View.VISIBLE);
			((EditText) findViewById(R.id.address1)).setText(addressObjs.get(0).getLine());
	
			if (addressObjs.size() >= 2) {
				AddressObj addrObj = addressObjs.get(1);
				if (addrObj != null && addrObj.getLine() != null && !addrObj.getLine().equals("")) {
					String addrLine = addrObj.getLine();
					
					((EditText) findViewById(R.id.address2)).setText(addrLine);
					((EditText) findViewById(R.id.address2)).setVisibility(View.VISIBLE);
					((ImageButton) findViewById(R.id.check2)).setVisibility(View.VISIBLE);
				} else {
					((EditText) findViewById(R.id.address2)).setVisibility(View.INVISIBLE);
					((ImageButton) findViewById(R.id.check2)).setVisibility(View.INVISIBLE);
				}
			}
		

			if (addressObjs.size() >= 3) {
				AddressObj addrObj = addressObjs.get(2);
				if (addrObj != null && addrObj.getLine() != null && !addrObj.getLine().equals("")) {
					String addrLine = addrObj.getLine();
					
					((EditText) findViewById(R.id.address3)).setText(addrLine);
					((EditText) findViewById(R.id.address3)).setVisibility(View.VISIBLE);
					((ImageButton) findViewById(R.id.check3)).setVisibility(View.VISIBLE);
				} else {
					((EditText) findViewById(R.id.address3)).setVisibility(View.INVISIBLE);
					((ImageButton) findViewById(R.id.check3)).setVisibility(View.INVISIBLE);
				}
			}
			

			if (addressObjs.size() >= 4) {
				AddressObj addrObj = addressObjs.get(3);
				if (addrObj != null && addrObj.getLine() != null && !addrObj.getLine().equals("")) {
					String addrLine = addrObj.getLine();
					
					((EditText) findViewById(R.id.address4)).setText(addrLine);
					((EditText) findViewById(R.id.address4)).setVisibility(View.VISIBLE);
					((ImageButton) findViewById(R.id.check4)).setVisibility(View.VISIBLE);
				} else {
					((EditText) findViewById(R.id.address4)).setVisibility(View.INVISIBLE);
					((ImageButton) findViewById(R.id.check4)).setVisibility(View.INVISIBLE);
				}
			}
			
			//title.setText(addressObjs.get(0).getLocality());
		} else {
			((TextView) findViewById(R.id.chooseTheAddress)).setText(getResources().getString(R.string.no_conn_no_address));
			
		}
		
		
	}

	
	
	private List<AddressObj> setAddressInitially() {
		
		
    	List<AddressObj> addressObjs = new ArrayList<DialogCreatePoint.AddressObj>();
		
	    Geocoder geoCoder = new Geocoder(this, Locale.getDefault());    
	    try {
	            List<Address> addresses = geoCoder.getFromLocation(latitude, longitude, 5);
	            String add = "";
	            
	            if (addresses.size() > 0) {
	            	
	            	
	            	for (Address address : addresses) {
	            		
	                	String addressLine = new String("");
	                	AddressObj addressObj = new DialogCreatePoint.AddressObj();
	            		
	            		
//	            		addressLine += "#";
//	            		addressLine += "getAdminArea " + address.getAdminArea();
//	            		addressLine += ", getAddressLine(0) " + address.getAddressLine(0);
//	            		addressLine += ", getLocality " + address.getLocality();
//	            		addressLine += ", getFeatureName " + address.getFeatureName();
//	            		addressLine += ", getPremises " + address.getPremises();
//	            		addressLine += ", getSubAdminArea " + address.getSubAdminArea();
//	            		addressLine += ", getSubLocality " + address.getSubLocality();
//	            		addressLine += ", getThoroughfare " + address.getThoroughfare();
//	            		addressLine += ", getSubThoroughfare " + address.getSubThoroughfare();

	            		addressLine += address.getLocality()!=null?address.getLocality():"";
	            		addressLine += address.getSubLocality()!=null?(addressLine.equals("")?"":" " + address.getSubLocality()):"";
	            		addressLine += address.getAddressLine(0)!=null?(addressLine.equals("")?"":" " + address.getAddressLine(0)):"";
	            		
	            		if(addressLine.equals("") && address.getThoroughfare() != null) {
		            		addressLine += address.getThoroughfare()!=null?address.getThoroughfare():"";
		            		addressLine += address.getSubThoroughfare()!=null?(addressLine.equals("")?"":" " + address.getSubThoroughfare()):"";
	            		}
	            		
	            		if (!addressLine.equals("")) {
	            			
		            		addressObj.setAddressLine(address.getAddressLine(0));
		            		addressObj.setLine(addressLine);
		            		addressObj.setLocality(address.getLocality());
		            		addressObj.setSubLocality(address.getSubLocality());
		            		addressObj.setSubThoroughfare(address.getSubThoroughfare());
		            		addressObj.setThoroughfare(address.getThoroughfare());
		            		
	            			//break;
		            		
		            		addressObjs.add(addressObj);
	            		}
	            	
	            	}
	            	
	            }    
	    } catch (IOException e) {
	            e.printStackTrace();
	    }		
		
		return addressObjs;
	}
	
	public void onClick(View view) throws IllegalAccessException, InstantiationException {
		
		if (view.getId() == R.id.buttonOk) {
			
				EditText titleEditText = (EditText) findViewById(R.id.editText1);
				
				if (titleEditText.getText().length() != 0) {
				
					EntityHelper entityHelper = new EntityHelper(getApplicationContext(), Point.class);
					
					Point point = new Point();
					
					//EditText descEditText = (EditText) findViewById(R.id.editText2);
					
					point.COLUMN_TITLE = titleEditText.getText().toString();
					//point.COLUMN_DESC = descEditText.getText().toString();
					point.COLUMN_LAT = latitude;
					point.COLUMN_LNG = longitude;
					point.COLUMN_CREATED = (new Date().getTime());
					
					try {
						entityHelper.save(point);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				    Intent intent = new Intent(this, MainActivity.class);
				    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				      
				    startActivity(intent);
			    
				} else {
		    		Toast.makeText(DialogCreatePoint.this, getResources().getString(R.string.no_name), Toast.LENGTH_LONG).show();
				}
			
		} else if (view.getId() == R.id.buttonBack) {
			
		    Intent intent = new Intent(this, MainActivity.class);
		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		      
		    startActivity(intent);
		    
		} else if (view.getId() == R.id.check1) {
			
			EditText title = (EditText) findViewById(R.id.editText1);
			TextView textView = (TextView) findViewById(R.id.address1);
			title.setText(textView.getText());
			
		} else if (view.getId() == R.id.check2) {
			
			EditText title = (EditText) findViewById(R.id.editText1);
			TextView textView = (TextView) findViewById(R.id.address2);
			title.setText(textView.getText());
			
		} else if (view.getId() == R.id.check3) {
			
			EditText title = (EditText) findViewById(R.id.editText1);
			TextView textView = (TextView) findViewById(R.id.address3);
			title.setText(textView.getText());
			
		} else if (view.getId() == R.id.check4) {
			
			EditText title = (EditText) findViewById(R.id.editText1);
			TextView textView = (TextView) findViewById(R.id.address4);
			title.setText(textView.getText());
			
		}
	}
	
}
