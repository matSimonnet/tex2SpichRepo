package fr.ms.tex2spichproj;

import java.text.DecimalFormat;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	// variable declaration
	TextView textView1 = null;
	TextView textView2 = null;
	TextView textView3 = null;
	TextView textView4 = null;
	EditText editText = null;
	TextToSpeech tts = null;
	Button button1 = null;
	Button button2 = null;
	Button button3 = null;
	Button button4 = null;
	private LocationManager lm = null;
	LocationListener ll = null;
	Location loc = null;
	String bearing = "r";
	String speed = "r";
	String latitude = "r";
	String longitude = "r";
	String provider = "r";
	

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
    //pour tester le git
    Toast.makeText(this, "coucou", Toast.LENGTH_LONG).show();    
        
        
    //TextView creation
    textView1 = new TextView(this);
    textView1 = (TextView) findViewById(R.id.latitudeView);
    textView2 = new TextView(this);
    textView2 = (TextView) findViewById(R.id.longitudeView);
    textView3 = new TextView(this);
    textView3 = (TextView) findViewById(R.id.speedView);
    textView4 = new TextView(this);
    textView4 = (TextView) findViewById(R.id.bearing);
    
    // edit text creation
    //editText = new EditText(this);
    //editText = (EditText) findViewById(R.id.editTextInvitation);
	  
	//location manager creation
	lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	ll = new MyLocationListener();		
	//loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, ll);
   
	//OnInitListener Creation
	OnInitListener onInitListener = new OnInitListener() {
		@Override
		public void onInit(int status) {
		}
	};
	
    // tts creation
	tts = new TextToSpeech(this, onInitListener);
	
	// button creation
	button1 = new Button(this);
    button1 = (Button) findViewById(R.id.buttonLatitude);
	button2 = new Button(this);
    button2 = (Button) findViewById(R.id.buttonLongitude);
    button3 = new Button(this);
    button3 = (Button) findViewById(R.id.buttonSpeed);
	button4 = new Button(this);
    button4 = (Button) findViewById(R.id.buttonBearing);

    // OnClickListener creation
    View.OnClickListener onclickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v== button1){
				tts.speak("latitude : " + latitude, TextToSpeech.QUEUE_FLUSH, null);
			}
			if (v== button2){
				tts.speak("longitude : " + longitude, TextToSpeech.QUEUE_FLUSH, null);
			}
			if (v== button3){
				tts.speak("vitesse : " + speed, TextToSpeech.QUEUE_FLUSH, null);
			}
			if (v== button4){
				tts.speak("cap : " + bearing, TextToSpeech.QUEUE_FLUSH, null);
			}
		}
	};
    
	// button activation
	button1.setOnClickListener(onclickListener);
	button2.setOnClickListener(onclickListener);
	button3.setOnClickListener(onclickListener);
	button4.setOnClickListener(onclickListener);
	
    }//end of oncreate
	
	//method to round 1 decimal
	//public double arrondiLat(double val) {return (Math.floor(val*1000))/1000;}
	//public double arrondiLong(double val) {return (Math.floor(val*100))/100;}
	public double arrondiSpeed(double val) {return (Math.floor(val*10))/10;}
	//public double arrondiBearing(double val) {return (Math.floor(val*100))/100;}
//
//    @Override
//    protected void onResume() {
//      super.onResume();
//      lm.requestLocationUpdates(lm.GPS_PROVIDER, 0, 0, this);
//    }
//
//    /* Remove the locationlistener updates when Activity is paused */
//    @Override
//    protected void onPause() {
//      super.onPause();
//      locationManager.removeUpdates(this);
//    }

    public class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location loc) {
			
			Location dest = null;
			dest = new Location(loc);
			dest.setLatitude(48.4);
			dest.setLongitude(-4.5);
			
			latitude = String.valueOf(loc.getLatitude());
			longitude = String.valueOf(loc.getLongitude());			
			speed = String.valueOf(arrondiSpeed(loc.getSpeed()));
			bearing = String.valueOf((int)loc.getBearing());
			String test = String.valueOf(loc.distanceTo(dest));
			Log.i("LocationListener", "whyNot");
			//displaying value
		    textView1.setText(latitude);
		    textView2.setText(longitude);
			textView3.setText(speed);
			textView4.setText(test);    
		}

		@Override
		public void onProviderDisabled(String provider) {
			Toast.makeText( getApplicationContext(),"Gps Disabled",Toast.LENGTH_SHORT).show();	
		}

		@Override
		public void onProviderEnabled(String provider) {
			Toast.makeText( getApplicationContext(),"Gps Enabled",Toast.LENGTH_SHORT).show();	
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Toast.makeText(getApplicationContext(),"onStatusChanged",Toast.LENGTH_SHORT).show();
		}
    	
    }
    
}//end of Activity
