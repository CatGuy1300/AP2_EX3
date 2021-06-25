package com.example.fgcontroller.model;

import android.util.Log;

import com.example.fgcontroller.view_model.ViewModel;
import com.example.fgcontroller.views.MainActivity;

import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FGModel {
    private BlockingQueue<Runnable> dispatchQueue = new LinkedBlockingQueue<Runnable>();
    private Socket FGClient;
    private PrintWriter out;

    public FGModel() {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        // take() blocks, so no busy waiting
                        dispatchQueue.take().run();
                    } catch (InterruptedException e) {
                        Log.d("Error", e.getMessage());
                    }
                }
            }
        }).start();
    }

    public void setRudder(double rudder) throws InterruptedException {
        if (out != null) {
            dispatchQueue.put(new Runnable() {
                public void run() {
                        out.print("set /controls/flight/aileron " + rudder + "\r\n");
                        out.flush();
                }
            });
        }
    }

    public void setThrottle(double throttle) throws InterruptedException {
        if (out != null) {
            dispatchQueue.put(new Runnable() {
                public void run() {
                    out.print("set controls/engines/current-engine/throttle " + throttle + "\r\n");
                    out.flush();
                }
            });
        }
    }

    public void setAileron(double aileron) throws InterruptedException {
        if (out != null) {
            dispatchQueue.put(new Runnable() {
                public void run() {
                    out.print("set /controls/flight/aileron " + aileron + "\r\n");
                    out.flush();
                }
            });
        }
    }

    public void setElevator(double elevator) throws InterruptedException {
        if (out != null) {
            dispatchQueue.put(new Runnable() {
                public void run() {
                    out.print("set /controls/flight/elevator " + elevator + "\r\n");
                    out.flush();
                }
            });
        }
    }

    public void connect(final String ip, final String port, final Runnable onFail, final Runnable onSuccess, Runnable ifConnected) throws InterruptedException {
        if (out != null) {
            ifConnected.run();
            return;
        }
        dispatchQueue.put(new Runnable() {
            public void run() {
                try {
                    FGClient = new Socket();
                    FGClient.connect(new InetSocketAddress(ip, Integer.valueOf(port)), 2 * 1000);
                    out = new PrintWriter(FGClient.getOutputStream(), true);
                } catch (Exception e) {
                    FGClient = null;
                    out = null;
                }
                if (FGClient == null) {
                    onFail.run();
                } else {
                    onSuccess.run();
                }
            }
        });
    }

    public void reconnect(final String ip, final String port, final Runnable onFail, final Runnable onSuccess) throws InterruptedException {

        dispatchQueue.put(new Runnable() {
            public void run() {
                try {
                    FGClient = new Socket();
                    FGClient.connect(new InetSocketAddress(ip, Integer.valueOf(port)), 2 * 1000);
                    out = new PrintWriter(FGClient.getOutputStream(), true);
                } catch (Exception e) {
                    FGClient = null;
                    out = null;
                }
                if (FGClient == null) {
                    onFail.run();
                } else {
                    onSuccess.run();
                }
            }
        });
    }
}
