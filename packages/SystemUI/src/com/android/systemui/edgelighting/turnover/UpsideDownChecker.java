package com.android.systemui.edgelighting.turnover;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import com.android.systemui.edgelighting.turnover.TurnOverEdgeLighting;
import com.samsung.android.hardware.context.SemContextDevicePosition;
import com.samsung.android.hardware.context.SemContextEvent;
import com.samsung.android.hardware.context.SemContextListener;
import com.samsung.android.hardware.context.SemContextManager;

public final class UpsideDownChecker implements SemContextListener {
    public boolean mLastSensorValue;
    public TurnOverEdgeLighting.AnonymousClass2 mListener;
    public final SemContextManager mSContextManager;
    public final boolean mSupportPositionSensor;

    public UpsideDownChecker(Context context) {
        this.mSupportPositionSensor = true;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null || !packageManager.hasSystemFeature("com.sec.feature.sensorhub")) {
            return;
        }
        SemContextManager semContextManager = (SemContextManager) context.getSystemService("scontext");
        this.mSContextManager = semContextManager;
        if (semContextManager.isAvailableService(22)) {
            return;
        }
        this.mSContextManager = null;
        Log.w("UpsideDownChecker", "The Sensor is not supported in device");
        this.mSupportPositionSensor = false;
    }

    public final void cancel() {
        this.mLastSensorValue = false;
        synchronized (this) {
            try {
                if (this.mListener == null) {
                    return;
                }
                Log.d("UpsideDownChecker", "unregister sensor.");
                this.mSContextManager.unregisterListener(this, 22);
                this.mListener = null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onSemContextChanged(SemContextEvent semContextEvent) {
        if (semContextEvent.semContext.getType() != 22) {
            Log.d("UpsideDownChecker", "onSemContextChanged: not sensor type");
            return;
        }
        SemContextDevicePosition devicePositionContext = semContextEvent.getDevicePositionContext();
        Log.d("UpsideDownChecker", "onSContextChanged:" + devicePositionContext.getPosition() + ",mLastSensorValue=" + this.mLastSensorValue);
        int position = devicePositionContext.getPosition();
        if (position != 2) {
            if (position != 6) {
                TurnOverEdgeLighting.AnonymousClass2 anonymousClass2 = this.mListener;
                if (anonymousClass2 != null) {
                    anonymousClass2.onChanged(false);
                    this.mLastSensorValue = false;
                    return;
                }
                return;
            }
            if (this.mLastSensorValue) {
                return;
            }
            TurnOverEdgeLighting.AnonymousClass2 anonymousClass22 = this.mListener;
            if (anonymousClass22 == null) {
                Log.w("UpsideDownChecker", "onSContextChanged(), listener is null");
            } else {
                anonymousClass22.onChanged(true);
                this.mLastSensorValue = true;
            }
        }
    }

    public final void run(TurnOverEdgeLighting.AnonymousClass2 anonymousClass2) {
        this.mLastSensorValue = false;
        if (this.mSContextManager == null) {
            Log.w("UpsideDownChecker", "The sensor is not supported in device");
            return;
        }
        synchronized (this) {
            try {
                if (this.mListener != null) {
                    Log.d("UpsideDownChecker", "run: Listener is not null");
                } else {
                    this.mListener = anonymousClass2;
                    this.mSContextManager.registerListener(this, 22);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
