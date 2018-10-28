package com.worldmer.bluetoothdemo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvStatus;
    Button btnBlueToothStatus, btnBlueToothScan;
    boolean isBlueToothStatus = false;
    Activity activity;
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        tvStatus = findViewById(R.id.tvstatus);
        btnBlueToothStatus = findViewById(R.id.btnonoff);
        btnBlueToothScan = findViewById(R.id.btnscan);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btnBlueToothStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBlueToothStatus)
                    bluetoothAdapter.disable();
                else
                    bluetoothAdapter.enable();
                isBlueToothStatus = bluetoothAdapter.isEnabled();
                tvStatus.setText(isBlueToothStatus == true ? "ON" : "OFF");
                btnBlueToothStatus.setText(isBlueToothStatus == true ? "OFF" : "ON");
                btnBlueToothScan.setEnabled(isBlueToothStatus);
            }
        });
        btnBlueToothScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, ScanBluetoothActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isBlueToothStatus = bluetoothAdapter.isEnabled();
        tvStatus.setText(isBlueToothStatus == true ? "ON" : "OFF");
        btnBlueToothStatus.setText(isBlueToothStatus == true ? "OFF" : "ON");
        btnBlueToothScan.setEnabled(isBlueToothStatus);
    }
}
