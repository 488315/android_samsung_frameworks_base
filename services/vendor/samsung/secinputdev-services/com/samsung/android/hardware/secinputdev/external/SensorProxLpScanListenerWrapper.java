package com.samsung.android.hardware.secinputdev.external;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;

/* loaded from: classes.dex */
public class SensorProxLpScanListenerWrapper extends ExternalService {
    private final SensorEventListener sensorEventListener;

    public SensorProxLpScanListenerWrapper(
            Context context, SemInputExternal.IServiceListener listener, Handler handler) {
        super(context, listener, handler);
        this.sensorEventListener =
                new SensorEventListener() { // from class:
                                            // com.samsung.android.hardware.secinputdev.external.SensorProxLpScanListenerWrapper.1
                    @Override // android.hardware.SensorEventListener
                    public void onAccuracyChanged(Sensor sensor, int event) {}

                    @Override // android.hardware.SensorEventListener
                    public void onSensorChanged(SensorEvent event) {
                        if (event.sensor.getType() == 65621) {
                            int mode = (int) event.values[0];
                            SensorProxLpScanListenerWrapper.this.listener.onLpScanSensorChanged(
                                    mode);
                        }
                    }
                };
    }

    @Override // com.samsung.android.hardware.secinputdev.external.ExternalService
    public String register() throws Exception {
        try {
            SensorManager sensorManager = (SensorManager) this.context.getSystemService("sensor");
            if (sensorManager == null) {
                throw new Exception("SensorManager is null");
            }
            Sensor lpScanSensor = sensorManager.getDefaultSensor(65621);
            if (lpScanSensor == null) {
                throw new Exception("Sensor is null");
            }
            boolean ret = sensorManager.registerListener(this.sensorEventListener, lpScanSensor, 1);
            if (!ret) {
                throw new Exception("SensorProxLpScanListenerWrapper false return");
            }
            return "";
        } catch (Exception e) {
            throw e;
        }
    }
}
