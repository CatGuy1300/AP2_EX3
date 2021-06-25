package com.example.fgcontroller.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import android.view.View;

import android.widget.Toast;

import com.example.fgcontroller.R;
import com.example.fgcontroller.databinding.ActivityMainBinding;
import com.example.fgcontroller.model.FGModel;
import com.example.fgcontroller.view_model.ViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ViewModel vm;
    private SharedPreferences preferences;
    public static final String PREFS_NAME = "FG_CONNECTION";
    public static final String PORT_KEY = "PORT";
    public static final String IP_KEY = "IP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //To reuse port and ip
        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // setting view model
        vm = new ViewModel(new FGModel());
        binding.setViewModel(vm);


    }

    @Override
    protected void onStart() {
        super.onStart();
        // set port and ip
        vm.setIp(preferences.getString(IP_KEY, ""));
        vm.setPort(preferences.getString(PORT_KEY, ""));

        // set starting value
        vm.setRudder(1000);

        // setting listener to joystick change
        binding.js.setListener((a, e)-> {vm.setAileron(a); vm.setElevator(e);});

        // setting listener to button click
        binding.btConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saving port and ip
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(IP_KEY, binding.etIP.getText().toString());
                editor.putString(PORT_KEY, binding.etPort.getText().toString());
                editor.commit();

                Runnable onFail = new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(() -> {Toast.makeText(getApplicationContext(), "Connection failed", Toast.LENGTH_SHORT).show();});
                    }
                };
                Runnable onSuccess = new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(() -> {Toast.makeText(getApplicationContext(), "Connected successfully", Toast.LENGTH_SHORT).show();});
                    }
                };
                Runnable ifConnected = new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

                        alertDialog.setMessage("You are already connected. Would you like to reconnect?");
                        alertDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    vm.reconnect(onFail, onSuccess);
                                }
                                catch (Exception e) {
                                    Log.d("Error", e.getMessage());
                                }
                            }
                        });
                        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        //show dialog
                        alertDialog.show();
                    }
                };
                try {
                    vm.connect(onFail, onSuccess, ifConnected);
                }
                catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }
        });
    }
}