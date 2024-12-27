package com.android.systemui.util.sensors;

import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.text.TextUtils;
import android.util.Log;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.sensors.ThresholdSensor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ThresholdSensorImpl implements ThresholdSensor {
    private final Execution mExecution;
    private Boolean mLastBelow;
    private List<ThresholdSensor.Listener> mListeners;
    private boolean mPaused;
    private boolean mRegistered;
    private final Sensor mSensor;
    private int mSensorDelay;
    private SensorEventListener mSensorEventListener;
    private final AsyncSensorManager mSensorManager;
    private String mTag;
    private final float mThreshold;
    private final float mThresholdLatch;
    private static final String TAG = "ThresholdSensor";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);

    public class Builder {
        private final Execution mExecution;
        private final Resources mResources;
        private Sensor mSensor;
        private int mSensorDelay = 3;
        private final AsyncSensorManager mSensorManager;
        private boolean mSensorSet;
        private float mThresholdLatchValue;
        private boolean mThresholdLatchValueSet;
        private boolean mThresholdSet;
        private float mThresholdValue;

        public Builder(Resources resources, AsyncSensorManager asyncSensorManager, Execution execution) {
            this.mResources = resources;
            this.mSensorManager = asyncSensorManager;
            this.mExecution = execution;
        }

        public ThresholdSensor build() {
            if (!this.mSensorSet) {
                throw new IllegalStateException("A sensor was not successfully set.");
            }
            if (!this.mThresholdSet) {
                throw new IllegalStateException("A threshold was not successfully set.");
            }
            if (this.mThresholdValue <= this.mThresholdLatchValue) {
                return new ThresholdSensorImpl(this.mSensorManager, this.mSensor, this.mExecution, this.mThresholdValue, this.mThresholdLatchValue, this.mSensorDelay, 0);
            }
            throw new IllegalStateException("Threshold must be less than or equal to Threshold Latch");
        }

        public Sensor findSensorByType(String str, boolean z) {
            Sensor sensor = null;
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            for (Sensor sensor2 : this.mSensorManager.getSensorList(-1)) {
                if (str.equals(sensor2.getStringType())) {
                    if (!z || sensor2.isWakeUpSensor()) {
                        return sensor2;
                    }
                    sensor = sensor2;
                }
            }
            return sensor;
        }

        public Builder setSensor(Sensor sensor) {
            this.mSensor = sensor;
            this.mSensorSet = true;
            return this;
        }

        public Builder setSensorDelay(int i) {
            this.mSensorDelay = i;
            return this;
        }

        public Builder setSensorResourceId(int i, boolean z) {
            setSensorType(this.mResources.getString(i), z);
            return this;
        }

        public Builder setSensorType(String str, boolean z) {
            Sensor findSensorByType = findSensorByType(str, z);
            if (findSensorByType != null) {
                setSensor(findSensorByType);
            }
            return this;
        }

        public Builder setThresholdLatchResourceId(int i) {
            try {
                setThresholdLatchValue(this.mResources.getFloat(i));
            } catch (Resources.NotFoundException unused) {
            }
            return this;
        }

        public Builder setThresholdLatchValue(float f) {
            this.mThresholdLatchValue = f;
            this.mThresholdLatchValueSet = true;
            return this;
        }

        public Builder setThresholdResourceId(int i) {
            try {
                setThresholdValue(this.mResources.getFloat(i));
            } catch (Resources.NotFoundException unused) {
            }
            return this;
        }

        public Builder setThresholdValue(float f) {
            this.mThresholdValue = f;
            this.mThresholdSet = true;
            if (!this.mThresholdLatchValueSet) {
                this.mThresholdLatchValue = f;
            }
            return this;
        }
    }

    public class BuilderFactory {
        private final Execution mExecution;
        private final Resources mResources;
        private final AsyncSensorManager mSensorManager;

        public BuilderFactory(Resources resources, AsyncSensorManager asyncSensorManager, Execution execution) {
            this.mResources = resources;
            this.mSensorManager = asyncSensorManager;
            this.mExecution = execution;
        }

        public Builder createBuilder() {
            return new Builder(this.mResources, this.mSensorManager, this.mExecution);
        }
    }

    public /* synthetic */ ThresholdSensorImpl(AsyncSensorManager asyncSensorManager, Sensor sensor, Execution execution, float f, float f2, int i, int i2) {
        this(asyncSensorManager, sensor, execution, f, f2, i);
    }

    private void alertListenersInternal(final boolean z, final long j) {
        new ArrayList(this.mListeners).forEach(new Consumer() { // from class: com.android.systemui.util.sensors.ThresholdSensorImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ThresholdSensorImpl.lambda$alertListenersInternal$0(z, j, (ThresholdSensor.Listener) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$alertListenersInternal$0(boolean z, long j, ThresholdSensor.Listener listener) {
        listener.onThresholdCrossed(new ThresholdSensorEvent(z, j));
    }

    public void logDebug(String str) {
        if (DEBUG) {
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder(), this.mTag != null ? ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("["), this.mTag, "] ") : "", str, TAG);
        }
    }

    public void onSensorEvent(boolean z, boolean z2, long j) {
        this.mExecution.assertIsMainThread();
        if (this.mRegistered) {
            Boolean bool = this.mLastBelow;
            if (bool != null) {
                if (bool.booleanValue() && !z2) {
                    return;
                }
                if (!this.mLastBelow.booleanValue() && !z) {
                    return;
                }
            }
            this.mLastBelow = Boolean.valueOf(z);
            logDebug(KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("Alerting below: ", z));
            alertListenersInternal(z, j);
        }
    }

    private void registerInternal() {
        this.mExecution.assertIsMainThread();
        if (this.mRegistered || this.mPaused || this.mListeners.isEmpty()) {
            return;
        }
        logDebug("Registering sensor listener");
        this.mSensorManager.registerListener(this.mSensorEventListener, this.mSensor, this.mSensorDelay);
        this.mRegistered = true;
    }

    private void unregisterInternal() {
        this.mExecution.assertIsMainThread();
        if (this.mRegistered) {
            logDebug("Unregister sensor listener");
            this.mSensorManager.unregisterListener(this.mSensorEventListener);
            this.mRegistered = false;
            this.mLastBelow = null;
        }
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public String getName() {
        Sensor sensor = this.mSensor;
        if (sensor != null) {
            return sensor.getName();
        }
        return null;
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public String getType() {
        Sensor sensor = this.mSensor;
        if (sensor != null) {
            return sensor.getStringType();
        }
        return null;
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public boolean isLoaded() {
        return this.mSensor != null;
    }

    public boolean isRegistered() {
        return this.mRegistered;
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public void pause() {
        this.mExecution.assertIsMainThread();
        this.mPaused = true;
        unregisterInternal();
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public void register(ThresholdSensor.Listener listener) {
        this.mExecution.assertIsMainThread();
        if (!this.mListeners.contains(listener)) {
            this.mListeners.add(listener);
        }
        registerInternal();
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public void resume() {
        this.mExecution.assertIsMainThread();
        this.mPaused = false;
        registerInternal();
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public void setDelay(int i) {
        if (i == this.mSensorDelay) {
            return;
        }
        this.mSensorDelay = i;
        if (isLoaded()) {
            unregisterInternal();
            registerInternal();
        }
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public void setTag(String str) {
        this.mTag = str;
    }

    public String toString() {
        boolean isLoaded = isLoaded();
        boolean z = this.mRegistered;
        boolean z2 = this.mPaused;
        float f = this.mThreshold;
        Sensor sensor = this.mSensor;
        StringBuilder m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("{isLoaded=", ", registered=", ", paused=", isLoaded, z);
        m.append(z2);
        m.append(", threshold=");
        m.append(f);
        m.append(", sensor=");
        m.append(sensor);
        m.append("}");
        return m.toString();
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public void unregister(ThresholdSensor.Listener listener) {
        this.mExecution.assertIsMainThread();
        this.mListeners.remove(listener);
        unregisterInternal();
    }

    private ThresholdSensorImpl(AsyncSensorManager asyncSensorManager, Sensor sensor, Execution execution, float f, float f2, int i) {
        this.mListeners = new ArrayList();
        this.mSensorEventListener = new SensorEventListener() { // from class: com.android.systemui.util.sensors.ThresholdSensorImpl.1
            @Override // android.hardware.SensorEventListener
            public void onSensorChanged(SensorEvent sensorEvent) {
                boolean z = sensorEvent.values[0] < ThresholdSensorImpl.this.mThreshold;
                boolean z2 = sensorEvent.values[0] >= ThresholdSensorImpl.this.mThresholdLatch;
                ThresholdSensorImpl.this.logDebug("Sensor value: " + sensorEvent.values[0]);
                ThresholdSensorImpl.this.onSensorEvent(z, z2, sensorEvent.timestamp);
            }

            @Override // android.hardware.SensorEventListener
            public void onAccuracyChanged(Sensor sensor2, int i2) {
            }
        };
        this.mSensorManager = asyncSensorManager;
        this.mExecution = execution;
        this.mSensor = sensor;
        this.mThreshold = f;
        this.mThresholdLatch = f2;
        this.mSensorDelay = i;
    }
}
