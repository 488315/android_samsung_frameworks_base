package com.android.server.biometrics.log;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Slog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ALSProbe {
    public boolean mDestroyed;
    public final Sensor mLightSensor;
    public final long mMaxSubscriptionTime;
    public final SensorManager mSensorManager;
    public final Handler mTimer;
    public boolean mEnabled = false;
    public boolean mDestroyRequested = false;
    public boolean mDisableRequested = false;
    public NextConsumer mNextConsumer = null;
    public volatile float mLastAmbientLux = -1.0f;
    public final AnonymousClass1 mLightSensorListener =
            new SensorEventListener() { // from class: com.android.server.biometrics.log.ALSProbe.1
                @Override // android.hardware.SensorEventListener
                public final void onAccuracyChanged(Sensor sensor, int i) {}

                @Override // android.hardware.SensorEventListener
                public final void onSensorChanged(SensorEvent sensorEvent) {
                    ALSProbe.this.onNext(sensorEvent.values[0]);
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NextConsumer {
        public final Consumer mConsumer;
        public final List mOthers = new ArrayList();
        public final Handler mHandler = null;

        public NextConsumer(
                BiometricFrameworkStatsLogger$$ExternalSyntheticLambda2
                        biometricFrameworkStatsLogger$$ExternalSyntheticLambda2) {
            this.mConsumer = biometricFrameworkStatsLogger$$ExternalSyntheticLambda2;
        }

        public final void consume(final float f) {
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(
                        new Runnable() { // from class:
                                         // com.android.server.biometrics.log.ALSProbe$NextConsumer$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                ALSProbe.NextConsumer.this.mConsumer.accept(Float.valueOf(f));
                            }
                        });
            } else {
                this.mConsumer.accept(Float.valueOf(f));
            }
            Iterator it = ((ArrayList) this.mOthers).iterator();
            while (it.hasNext()) {
                ((NextConsumer) it.next()).consume(f);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.biometrics.log.ALSProbe$1] */
    public ALSProbe(SensorManager sensorManager, Handler handler, long j) {
        this.mMaxSubscriptionTime = -1L;
        this.mDestroyed = false;
        this.mSensorManager = sensorManager;
        Sensor defaultSensor = sensorManager != null ? sensorManager.getDefaultSensor(5) : null;
        this.mLightSensor = defaultSensor;
        this.mTimer = handler;
        this.mMaxSubscriptionTime = j;
        if (sensorManager == null || defaultSensor == null) {
            Slog.w("ALSProbe", "No sensor - probe disabled");
            this.mDestroyed = true;
        }
    }

    public final synchronized void destroy() {
        this.mDestroyRequested = true;
        if (!this.mDestroyed && this.mNextConsumer == null) {
            disableLightSensorLoggingLocked(true);
            this.mDestroyed = true;
        }
    }

    public final synchronized void disable() {
        this.mDisableRequested = true;
        if (!this.mDestroyed && this.mNextConsumer == null) {
            disableLightSensorLoggingLocked(false);
        }
    }

    public final void disableLightSensorLoggingLocked(boolean z) {
        this.mTimer.removeCallbacksAndMessages(this);
        if (!this.mEnabled || this.mLightSensor == null) {
            return;
        }
        this.mEnabled = false;
        if (!z) {
            this.mLastAmbientLux = -1.0f;
        }
        this.mSensorManager.unregisterListener(this.mLightSensorListener);
        Slog.v("ALSProbe", "Disable ALS: " + hashCode());
    }

    public final synchronized void enable() {
        if (!this.mDestroyed && !this.mDestroyRequested) {
            this.mDisableRequested = false;
            enableLightSensorLoggingLocked();
        }
    }

    public final void enableLightSensorLoggingLocked() {
        if (!this.mEnabled && this.mLightSensor != null) {
            this.mEnabled = true;
            this.mLastAmbientLux = -1.0f;
            this.mSensorManager.registerListener(this.mLightSensorListener, this.mLightSensor, 3);
            Slog.v("ALSProbe", "Enable ALS: " + hashCode());
        }
        Handler handler = this.mTimer;
        handler.removeCallbacksAndMessages(this);
        long j = this.mMaxSubscriptionTime;
        if (j > 0) {
            handler.postDelayed(
                    new Runnable() { // from class:
                                     // com.android.server.biometrics.log.ALSProbe$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ALSProbe aLSProbe = ALSProbe.this;
                            synchronized (aLSProbe) {
                                Slog.e(
                                        "ALSProbe",
                                        "Max time exceeded for ALS logger - disabling: "
                                                + aLSProbe.mLightSensorListener.hashCode());
                                aLSProbe.onNext(aLSProbe.mLastAmbientLux);
                                aLSProbe.disable();
                            }
                        }
                    },
                    this,
                    j);
        }
    }

    public final synchronized void onNext(float f) {
        try {
            this.mLastAmbientLux = f;
            NextConsumer nextConsumer = this.mNextConsumer;
            this.mNextConsumer = null;
            if (nextConsumer != null) {
                Slog.v("ALSProbe", "Finishing next consumer");
                if (this.mDestroyRequested) {
                    destroy();
                } else if (this.mDisableRequested) {
                    disable();
                }
                nextConsumer.consume(f);
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
