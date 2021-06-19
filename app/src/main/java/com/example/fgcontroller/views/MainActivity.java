package com.example.fgcontroller.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.fgcontroller.R;
import com.example.fgcontroller.databinding.ActivityMainBinding;
import com.example.fgcontroller.model.FGModel;
import com.example.fgcontroller.view_model.ViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        vm = new ViewModel(new FGModel());
        binding.setViewModel(vm);

        binding.btConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        runOnUiThread(() -> {Toast.makeText(getApplicationContext(), "You are already connected", Toast.LENGTH_SHORT).show();});
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