package com.example.fgcontroller.view_model;

import android.util.Log;

import androidx.databinding.Bindable;

import com.example.fgcontroller.model.FGModel;
import com.example.fgcontroller.views.MainActivity;

public class ViewModel {
    private String port = "";
    private String ip = "";
    private int rudder = 0;
    private int throttle = 0;
    private FGModel model;

    public  ViewModel(FGModel model) {
        this.model = model;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
        Log.d("ip", "set IP to " + ip);
    }

    public int getRudder() {
        return rudder;
    }

    public void setRudder(int rudder) {
        this.rudder = rudder;
        try {
            model.setRudder((double) rudder/1000 - 1);
        } catch (Exception e) {

        }
        Log.d("rudder", "set rudder to " + rudder);
    }

    public int getThrottle() {
        return throttle;
    }

    public void setThrottle(int throttle) {
        this.throttle = throttle;
        try {
            model.setThrottle((double) throttle / 1000);
        } catch (Exception e) {

        }
        Log.d("throttle", "set throttle to " + throttle);
    }

    public void connect(Runnable onFail, Runnable onSuccess, Runnable ifConnected) throws InterruptedException {
        model.connect(ip, port, onFail, onSuccess, ifConnected);
    }

    public void setPort(String port) {
        this.port = port;
        Log.d("port", "set port to " + port);
    }

    public String getPort() {
        return port;
    }
}
