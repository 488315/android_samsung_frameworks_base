package com.android.systemui.util.sensors;

import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.text.TextUtils;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.concurrency.ExecutionImpl;
import com.android.systemui.util.sensors.ThresholdSensor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ThresholdSensorImpl implements ThresholdSensor {
    public static final boolean DEBUG = Log.isLoggable("ThresholdSensor", 3);
    public final Execution mExecution;
    public Boolean mLastBelow;
    public final List mListeners;
    public boolean mPaused;
    public boolean mRegistered;
    public final Sensor mSensor;
    public int mSensorDelay;
    public final C35921 mSensorEventListener;
    public final AsyncSensorManager mSensorManager;
    public String mTag;
    public final float mThreshold;
    public final float mThresholdLatch;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public final Execution mExecution;
        public final Resources mResources;
        public Sensor mSensor;
        public int mSensorDelay = 3;
        public final AsyncSensorManager mSensorManager;
        public boolean mSensorSet;
        public float mThresholdLatchValue;
        public boolean mThresholdLatchValueSet;
        public boolean mThresholdSet;
        public float mThresholdValue;

        public Builder(Resources resources, AsyncSensorManager asyncSensorManager, Execution execution) {
            this.mResources = resources;
            this.mSensorManager = asyncSensorManager;
            this.mExecution = execution;
        }

        public final ThresholdSensorImpl build() {
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
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BuilderFactory {
        public final Execution mExecution;
        public final Resources mResources;
        public final AsyncSensorManager mSensorManager;

        public BuilderFactory(Resources resources, AsyncSensorManager asyncSensorManager, Execution execution) {
            this.mResources = resources;
            this.mSensorManager = asyncSensorManager;
            this.mExecution = execution;
        }
    }

    public /* synthetic */ ThresholdSensorImpl(AsyncSensorManager asyncSensorManager, Sensor sensor, Execution execution, float f, float f2, int i, int i2) {
        this(asyncSensorManager, sensor, execution, f, f2, i);
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public final boolean isLoaded() {
        return this.mSensor != null;
    }

    public boolean isRegistered() {
        return this.mRegistered;
    }

    public final void logDebug(String str) {
        if (DEBUG) {
            ExifInterface$$ExternalSyntheticOutline0.m36m(new StringBuilder(), this.mTag != null ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("["), this.mTag, "] ") : "", str, "ThresholdSensor");
        }
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public final void pause() {
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        this.mPaused = true;
        unregisterInternal();
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public final void register(ThresholdSensor.Listener listener) {
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        ArrayList arrayList = (ArrayList) this.mListeners;
        if (!arrayList.contains(listener)) {
            arrayList.add(listener);
        }
        registerInternal();
    }

    public final void registerInternal() {
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        if (this.mRegistered || this.mPaused || ((ArrayList) this.mListeners).isEmpty()) {
            return;
        }
        logDebug("Registering sensor listener");
        this.mSensorManager.registerListener(this.mSensorEventListener, this.mSensor, this.mSensorDelay);
        this.mRegistered = true;
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public final void resume() {
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        this.mPaused = false;
        registerInternal();
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public final void setDelay() {
        if (1 == this.mSensorDelay) {
            return;
        }
        this.mSensorDelay = 1;
        if (isLoaded()) {
            unregisterInternal();
            registerInternal();
        }
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public final void setTag(String str) {
        this.mTag = str;
    }

    public final String toString() {
        return String.format("{isLoaded=%s, registered=%s, paused=%s, threshold=%s, sensor=%s}", Boolean.valueOf(isLoaded()), Boolean.valueOf(this.mRegistered), Boolean.valueOf(this.mPaused), Float.valueOf(this.mThreshold), this.mSensor);
    }

    @Override // com.android.systemui.util.sensors.ThresholdSensor
    public final void unregister(ThresholdSensor.Listener listener) {
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        ((ArrayList) this.mListeners).remove(listener);
        unregisterInternal();
    }

    public final void unregisterInternal() {
        ((ExecutionImpl) this.mExecution).assertIsMainThread();
        if (this.mRegistered) {
            logDebug("Unregister sensor listener");
            this.mSensorManager.unregisterListener(this.mSensorEventListener);
            this.mRegistered = false;
            this.mLastBelow = null;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.util.sensors.ThresholdSensorImpl$1] */
    private ThresholdSensorImpl(AsyncSensorManager asyncSensorManager, Sensor sensor, Execution execution, float f, float f2, int i) {
        this.mListeners = new ArrayList();
        this.mSensorEventListener = new SensorEventListener() { // from class: com.android.systemui.util.sensors.ThresholdSensorImpl.1
            @Override // android.hardware.SensorEventListener
            public final void onSensorChanged(SensorEvent sensorEvent) {
                float f3 = sensorEvent.values[0];
                ThresholdSensorImpl thresholdSensorImpl = ThresholdSensorImpl.this;
                final boolean z = f3 < thresholdSensorImpl.mThreshold;
                boolean z2 = f3 >= thresholdSensorImpl.mThresholdLatch;
                thresholdSensorImpl.logDebug("Sensor value: " + sensorEvent.values[0]);
                ThresholdSensorImpl thresholdSensorImpl2 = ThresholdSensorImpl.this;
                final long j = sensorEvent.timestamp;
                ((ExecutionImpl) thresholdSensorImpl2.mExecution).assertIsMainThread();
                if (thresholdSensorImpl2.mRegistered) {
                    Boolean bool = thresholdSensorImpl2.mLastBelow;
                    if (bool != null) {
                        if (bool.booleanValue() && !z2) {
                            return;
                        }
                        if (!thresholdSensorImpl2.mLastBelow.booleanValue() && !z) {
                            return;
                        }
                    }
                    thresholdSensorImpl2.mLastBelow = Boolean.valueOf(z);
                    thresholdSensorImpl2.logDebug("Alerting below: " + z);
                    new ArrayList(thresholdSensorImpl2.mListeners).forEach(new Consumer() { // from class: com.android.systemui.util.sensors.ThresholdSensorImpl$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((ThresholdSensor.Listener) obj).onThresholdCrossed(new ThresholdSensorEvent(z, j));
                        }
                    });
                }
            }

            @Override // android.hardware.SensorEventListener
            public final void onAccuracyChanged(Sensor sensor2, int i2) {
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
