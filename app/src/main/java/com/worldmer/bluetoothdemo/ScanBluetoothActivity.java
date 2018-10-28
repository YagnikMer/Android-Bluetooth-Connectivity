package com.worldmer.bluetoothdemo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.Set;

public class ScanBluetoothActivity extends AppCompatActivity {
    ListView lvlist;
    Activity activity;
    String[] list;
    EditText edtPassword;
    BluetoothAdapter bluetoothAdapter = null;
    Set<BluetoothDevice> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_wifi);
        activity = this;
        lvlist = findViewById(R.id.lvlist);
        edtPassword = findViewById(R.id.edtpassword);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        results = bluetoothAdapter.getBondedDevices();
        list = new String[results.size()];
        int counter = 0;
        if (results.size() > 0) {
            for (BluetoothDevice device : results) {
                list[counter] = device.getName();
                Log.d("ResuitLog", "Scan : " + list[counter]);
                counter++;
            }
        }

        lvlist.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list));
        lvlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int counter = 0;
                BluetoothDevice selectedDevice=null;
                if (results.size() > 0) {
                    for (BluetoothDevice device : results) {
                        if (counter == i) {
                            selectedDevice = device;
                            break;
                        }
                        counter++;
                    }
                }
                Toast.makeText(getApplicationContext(), "Device : " + selectedDevice.getName(), Toast.LENGTH_SHORT).show();
                String deviceBondMethod = null;
                if (selectedDevice.getBondState() == BluetoothDevice.BOND_BONDED)
                    deviceBondMethod = "createBond";
                else
                    deviceBondMethod = "removeBond";

                try {
                    Method method = selectedDevice.getClass().getMethod(deviceBondMethod, (Class[]) null);
                    method.invoke(selectedDevice, (Object[]) null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
