package com.android.systemui.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.util.Log;
import android.util.SparseArray;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class SensorController implements SensorEventListener2 {
    public final SparseArray sensorInfos;
    public final SensorManager sensorManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SensorInfo {
        public boolean bRegistered;
        public Sensor sensor;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public final void unregister$2() {
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
