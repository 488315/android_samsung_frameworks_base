package com.android.systemui.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.util.Log;
import android.util.SparseArray;
import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class SensorController implements SensorEventListener2 {
    public final SparseArray sensorInfos;
    public final SensorManager sensorManager;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class SensorInfo {
        public boolean bRegistered;
        public Sensor sensor;
    }

    public abstract class SensorListener {
        public abstract void onExecute();
    }

    static {
        new Companion(null);
    }

    public SensorController(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
        SparseArray sparseArray = new SparseArray();
        this.sensorInfos = sparseArray;
        SensorInfo sensorInfo = new SensorInfo();
        sensorInfo.sensor = sensorManager.getDefaultSensor(65590);
        sparseArray.put(1, sensorInfo);
    }

    @Override // android.hardware.SensorEventListener
    public final void onSensorChanged(SensorEvent sensorEvent) {
        Log.i("SensorController", "onTrigger=" + sensorEvent);
        onTrigger();
    }

    public void onTrigger() {
        Log.i("SensorController", "onTrigger=null");
    }

    public final void unregister$1() {
        SensorInfo sensorInfo = (SensorInfo) this.sensorInfos.get(1);
        if ((sensorInfo != null ? sensorInfo.sensor : null) == null) {
            Log.w("SensorController", "unregister - not supported sensor type=1");
        } else {
            if (!sensorInfo.bRegistered) {
                Log.w("SensorController", "unregister - already unregistered sensor type=1");
                return;
            }
            this.sensorManager.unregisterListener(this);
            Log.i("SensorController", "unregister");
            sensorInfo.bRegistered = false;
        }
    }

    @Override // android.hardware.SensorEventListener2
    public final void onFlushCompleted(Sensor sensor) {
    }

    @Override // android.hardware.SensorEventListener
    public final void onAccuracyChanged(Sensor sensor, int i) {
    }
}
