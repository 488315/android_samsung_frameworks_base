package com.android.systemui.unfold.updates.hinge;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Trace;
import androidx.core.util.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HingeSensorAngleProvider implements HingeAngleProvider {
    public final SensorManager sensorManager;
    public final Executor singleThreadBgExecutor;
    public boolean started;
    public final HingeAngleSensorListener sensorListener = new HingeAngleSensorListener();
    public final List listeners = new ArrayList();

    public HingeSensorAngleProvider(SensorManager sensorManager, Executor executor) {
        this.sensorManager = sensorManager;
        this.singleThreadBgExecutor = executor;
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void addCallback(Object obj) {
        ((ArrayList) this.listeners).add((Consumer) obj);
    }

    @Override // com.android.systemui.unfold.updates.hinge.HingeAngleProvider
    public final void start() {
        this.singleThreadBgExecutor.execute(new Runnable() { // from class: com.android.systemui.unfold.updates.hinge.HingeSensorAngleProvider$start$1
            @Override // java.lang.Runnable
            public final void run() {
                if (HingeSensorAngleProvider.this.started) {
                    return;
                }
                Trace.beginSection("HingeSensorAngleProvider#start");
                Sensor defaultSensor = HingeSensorAngleProvider.this.sensorManager.getDefaultSensor(36);
                HingeSensorAngleProvider hingeSensorAngleProvider = HingeSensorAngleProvider.this;
                hingeSensorAngleProvider.sensorManager.registerListener(hingeSensorAngleProvider.sensorListener, defaultSensor, 0);
                Trace.endSection();
                HingeSensorAngleProvider.this.started = true;
            }
        });
    }

    @Override // com.android.systemui.unfold.updates.hinge.HingeAngleProvider
    public final void stop() {
        this.singleThreadBgExecutor.execute(new Runnable() { // from class: com.android.systemui.unfold.updates.hinge.HingeSensorAngleProvider$stop$1
            @Override // java.lang.Runnable
            public final void run() {
                HingeSensorAngleProvider hingeSensorAngleProvider = HingeSensorAngleProvider.this;
                if (hingeSensorAngleProvider.started) {
                    hingeSensorAngleProvider.sensorManager.unregisterListener(hingeSensorAngleProvider.sensorListener);
                    HingeSensorAngleProvider.this.started = false;
                }
            }
        });
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class HingeAngleSensorListener implements SensorEventListener {
        public HingeAngleSensorListener() {
        }

        @Override // android.hardware.SensorEventListener
        public final void onSensorChanged(SensorEvent sensorEvent) {
            Iterator it = HingeSensorAngleProvider.this.listeners.iterator();
            while (it.hasNext()) {
                ((Consumer) it.next()).accept(Float.valueOf(sensorEvent.values[0]));
            }
        }

        @Override // android.hardware.SensorEventListener
        public final void onAccuracyChanged(Sensor sensor, int i) {
        }
    }
}
