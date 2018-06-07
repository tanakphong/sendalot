package com.deverdie.sendalot;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.deverdie.sendalot.receivers.NetworkChangeReceiver;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private BroadcastReceiver mNetworkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkCameraAndWriteExternalStoragePermission();
        bindView();
        broadcastReceiver();
    }

    private void broadcastReceiver() {
        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();
    }

    private void checkCameraPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Toast.makeText(getApplicationContext(),response.getPermissionName()+" Granted.",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(getApplicationContext(),response.getPermissionName()+" Denied.",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    private void checkCameraAndWriteExternalStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            // Do something
                        } else {
                            Toast.makeText(MainActivity.this, R.string.camera_and_write_external_storage_denied, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }

    private void bindView() {
        findViewById(R.id.setting_button).setOnClickListener(this);
        findViewById(R.id.signature_button).setOnClickListener(this);
        findViewById(R.id.camera_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_button:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.signature_button:
                startActivity(new Intent(MainActivity.this, SignatureActivity.class));
                break;
            case R.id.camera_button:
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
                break;
        }
    }

    public static void dialog(boolean value){

        if(value){
//            tv_check_connection.setText("We are back !!!");
//            tv_check_connection.setBackgroundColor(Color.GREEN);
//            tv_check_connection.setTextColor(Color.WHITE);
//
//            Handler handler = new Handler();
//            Runnable delayrunnable = new Runnable() {
//                @Override
//                public void run() {
//                    tv_check_connection.setVisibility(View.GONE);
//                }
//            };
//            handler.postDelayed(delayrunnable, 3000);
        }else {
//            tv_check_connection.setVisibility(View.VISIBLE);
//            tv_check_connection.setText("Could not Connect to internet");
//            tv_check_connection.setBackgroundColor(Color.RED);
//            tv_check_connection.setTextColor(Color.WHITE);
        }
    }


    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }
}
