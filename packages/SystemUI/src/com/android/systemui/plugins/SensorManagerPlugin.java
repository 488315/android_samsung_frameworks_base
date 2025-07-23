package com.android.systemui.plugins;

import com.android.systemui.plugins.annotations.ProvidesInterface;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ProvidesInterface(action = SensorManagerPlugin.ACTION, version = 1)
/* loaded from: classes2.dex */
public interface SensorManagerPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_SENSOR_MANAGER";
    public static final int VERSION = 1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Sensor {
        public static final int TYPE_SKIP_STATUS = 4;
        public static final int TYPE_SWIPE = 3;
        public static final int TYPE_WAKE_DISPLAY = 2;
        public static final int TYPE_WAKE_LOCK_SCREEN = 1;
        private int mType;

        public Sensor(int i) {
            this.mType = i;
        }

        public int getType() {
            return this.mType;
        }

        public String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.mType, "\"}", new StringBuilder("{PluginSensor type=\""));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SensorEvent {
        Sensor mSensor;
        float[] mValues;
        int mVendorType;

        public SensorEvent(Sensor sensor, int i) {
            this(sensor, i, null);
        }

        public Sensor getSensor() {
            return this.mSensor;
        }

        public float[] getValues() {
            return this.mValues;
        }

        public int getVendorType() {
            return this.mVendorType;
        }

        public SensorEvent(Sensor sensor, int i, float[] fArr) {
            this.mSensor = sensor;
            this.mVendorType = i;
            this.mValues = fArr;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface SensorEventListener {
        void onSensorChanged(SensorEvent sensorEvent);
    }

    void registerListener(Sensor sensor, SensorEventListener sensorEventListener);

    void unregisterListener(Sensor sensor, SensorEventListener sensorEventListener);
}
