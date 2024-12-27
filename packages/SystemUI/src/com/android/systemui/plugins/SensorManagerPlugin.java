package com.android.systemui.plugins;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(action = SensorManagerPlugin.ACTION, version = 1)
public interface SensorManagerPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_SENSOR_MANAGER";
    public static final int VERSION = 1;

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
            return Anchor$$ExternalSyntheticOutline0.m(this.mType, "\"}", new StringBuilder("{PluginSensor type=\""));
        }
    }

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

    public interface SensorEventListener {
        void onSensorChanged(SensorEvent sensorEvent);
    }

    void registerListener(Sensor sensor, SensorEventListener sensorEventListener);

    void unregisterListener(Sensor sensor, SensorEventListener sensorEventListener);
}
