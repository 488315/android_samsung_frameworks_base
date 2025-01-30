package com.android.server.aod;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.CoverState;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/* loaded from: classes.dex */
public class AODSCoverController {
    public static final Queue LUX_QUE = new LinkedList();
    public AODSettingHelper mAODSettingHelper;
    public Context mContext;
    public CoverManager mCoverManager;
    public SensorEventListener mSViewSensorEventListener;
    public SensorManager mSensorManager;
    public CoverManager.StateListener mCoverStateListener = null;
    public boolean mCoverAttached = false;
    public boolean mCoverClosed = false;
    public boolean mCoverUIWorking = false;

    public AODSCoverController(Context context, AODSettingHelper aODSettingHelper) {
        this.mContext = context;
        this.mAODSettingHelper = aODSettingHelper;
        this.mCoverManager = new CoverManager(this.mContext);
    }

    public void register() {
        this.mCoverStateListener = new CoverManager.StateListener() { // from class: com.android.server.aod.AODSCoverController.1
            public void onCoverStateChanged(CoverState coverState) {
                if (coverState != null) {
                    AODSCoverController.this.mCoverAttached = coverState.getAttachState();
                    AODSCoverController.this.mCoverClosed = !coverState.getSwitchState();
                    AODSCoverController aODSCoverController = AODSCoverController.this;
                    aODSCoverController.mCoverUIWorking = aODSCoverController.mCoverAttached && AODSCoverController.this.mCoverClosed;
                    if (AODSCoverController.this.mAODSettingHelper.isAODEnabled() && AODSCoverController.this.mCoverAttached && coverState.type == 1) {
                        AODSCoverController.this.registerSViewCoverSensorListener();
                    } else {
                        AODSCoverController.this.unregisterSViewCoverSensorListener();
                    }
                }
            }
        };
        CoverManager coverManager = this.mCoverManager;
        if (coverManager != null) {
            CoverState coverState = coverManager.getCoverState();
            if (coverState != null) {
                this.mCoverAttached = coverState.getAttachState();
                boolean z = !coverState.getSwitchState();
                this.mCoverClosed = z;
                this.mCoverUIWorking = this.mCoverAttached && z;
            }
            this.mCoverManager.registerListener(this.mCoverStateListener);
            return;
        }
        Log.d("AODManagerService.SCover", "initCoverManager is not supported");
    }

    public void refresh() {
        CoverState coverState;
        CoverManager coverManager = this.mCoverManager;
        if (coverManager == null || (coverState = coverManager.getCoverState()) == null || !coverState.attached || coverState.type != 1) {
            return;
        }
        if (this.mAODSettingHelper.isAODEnabled()) {
            registerSViewCoverSensorListener();
        } else {
            unregisterSViewCoverSensorListener();
        }
    }

    public final void registerSViewCoverSensorListener() {
        Queue queue = LUX_QUE;
        synchronized (queue) {
            if (this.mSensorManager == null) {
                this.mSensorManager = (SensorManager) this.mContext.getSystemService("sensor");
            }
            if (this.mSViewSensorEventListener == null) {
                queue.clear();
                SensorEventListener sensorEventListener = new SensorEventListener() { // from class: com.android.server.aod.AODSCoverController.2
                    @Override // android.hardware.SensorEventListener
                    public void onAccuracyChanged(Sensor sensor, int i) {
                    }

                    @Override // android.hardware.SensorEventListener
                    public void onSensorChanged(SensorEvent sensorEvent) {
                        synchronized (AODSCoverController.LUX_QUE) {
                            if (!AODSCoverController.this.mCoverClosed) {
                                AODSCoverController.LUX_QUE.offer(Float.valueOf(sensorEvent.values[0]));
                                if (AODSCoverController.LUX_QUE.size() > 15) {
                                    AODSCoverController.LUX_QUE.poll();
                                }
                            }
                        }
                    }
                };
                this.mSViewSensorEventListener = sensorEventListener;
                SensorManager sensorManager = this.mSensorManager;
                sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(5), 3);
            }
        }
    }

    public final void unregisterSViewCoverSensorListener() {
        SensorEventListener sensorEventListener;
        Queue queue = LUX_QUE;
        synchronized (queue) {
            SensorManager sensorManager = this.mSensorManager;
            if (sensorManager != null && (sensorEventListener = this.mSViewSensorEventListener) != null) {
                sensorManager.unregisterListener(sensorEventListener);
                this.mSViewSensorEventListener = null;
                queue.clear();
            }
        }
    }

    public boolean isSViewCoverBrightnessHigh() {
        float f;
        Queue queue = LUX_QUE;
        synchronized (queue) {
            boolean z = true;
            if (queue.size() == 0) {
                return true;
            }
            StringBuilder sb = new StringBuilder();
            Object peek = queue.peek();
            float f2 = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
            if (peek != null) {
                Iterator it = queue.iterator();
                int i = 0;
                while (it.hasNext()) {
                    float floatValue = ((Float) it.next()).floatValue();
                    f2 += floatValue;
                    i++;
                    sb.append(Float.toString(floatValue));
                    sb.append(", ");
                }
                f2 /= i;
                f = ((Float) LUX_QUE.poll()).floatValue();
            } else {
                f = 3.0f;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("isSViewCoverBrightnessHighInternal: (SVIEW_COVER) luxes [");
            sb2.append((Object) sb);
            sb2.append("], average [");
            sb2.append(f2);
            sb2.append("], lux [");
            sb2.append(f);
            sb2.append("], NIT state [");
            sb2.append(f2 > 2.0f);
            sb2.append("]");
            Log.d("AODManagerService.SCover", sb2.toString());
            if (f2 <= 2.0f) {
                z = false;
            }
            return z;
        }
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println(" AODSCoverController");
        printWriter.println("  - mCoverAttached : " + this.mCoverAttached);
        printWriter.println("  - mCoverClosed : " + this.mCoverClosed);
        printWriter.println("  - mCoverUIWorking : " + this.mCoverUIWorking);
    }
}
