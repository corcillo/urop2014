package com.example.devicedetector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import com.example.simpletest.R;

import android.support.v7.app.ActionBarActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	private static final int REQUEST_ENABLE_BT = 0;
	protected static final int DISCOVERY_REQUEST = 1;
	
	ArrayList<String> deviceList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		turnOnBluetooth();
		EditText inpText = (EditText)findViewById(R.id.inputText);
		TextView outText = (TextView)findViewById(R.id.outputText);
		Button subButton = (Button)findViewById(R.id.submitButton);
		
		subButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				EditText inpText = (EditText)findViewById(R.id.inputText);
				TextView outText = (TextView)findViewById(R.id.outputText);
				Button subButton = (Button)findViewById(R.id.submitButton);
				deviceList.add(inpText.getText().toString());
				outText.setText(arrayToString(deviceList));
				inpText.setText(new String());		
	
			}
		});
	}
	public String arrayToString(ArrayList<String> wordList){
		String out=new String();
		for (String word : wordList){
			out+=word+"\n";
		}
		return out;
	}
	/**
	 * Constructs list of found bluetooth devices
	 * @return ArrayList<String> devicesFound
	 */
	public ArrayList<String> constructDeviceList(){
		ArrayList<String> devices = new ArrayList<String>();
		
//		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
//		// If there are paired devices
//		if (pairedDevices.size() > 0) {
//		    // Loop through paired devices
//		    for (BluetoothDevice device : pairedDevices) {
//		        // Add the name and address to an array adapter to show in a ListView
//				ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(getBaseContext(), 0);
//		        mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
//		    }
//		}					
//		
		final BroadcastReceiver mReceiver=new BroadcastReceiver(){
			public void onReceive(Context context, Intent intent){
				String action = intent.getAction();
				if (BluetoothDevice.ACTION_FOUND.equals(action)){
					BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//					ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(context, 0);		
//					mArrayAdapter.add(device.getName()+"==="+device.getAddress());
					deviceList.add(device.getName()+"==="+device.getAddress());
				}
			}
		};
		
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter);		
		
		return devices;
	}

	/**
	 * Asks user for permission to turn on bluetooth if bluetooth was off.
	 * Also Checks if phone does not have bluetooth capabilities, currently no action if so
	 */
	public void turnOnBluetooth(){
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
		    // Device does not support Bluetooth
		}
		
		if (!mBluetoothAdapter.isEnabled()) {
		    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
