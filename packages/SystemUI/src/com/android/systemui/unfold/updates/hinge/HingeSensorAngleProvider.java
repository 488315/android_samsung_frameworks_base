package com.android.systemui.unfold.updates.hinge;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Trace;
import androidx.core.util.Consumer;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class HingeSensorAngleProvider implements HingeAngleProvider {
    public final Handler listenerHandler;
    public final SensorManager sensorManager;
    public final Executor singleThreadBgExecutor;
    public boolean started;
    public final HingeAngleSensorListener sensorListener = new HingeAngleSensorListener();
    public final List listeners = new CopyOnWriteArrayList();

    public HingeSensorAngleProvider(SensorManager sensorManager, Executor executor, Handler handler) {
        this.sensorManager = sensorManager;
        this.singleThreadBgExecutor = executor;
        this.listenerHandler = handler;
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void addCallback(Object obj) {
        ((CopyOnWriteArrayList) this.listeners).add((Consumer) obj);
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
                hingeSensorAngleProvider.sensorManager.registerListener(hingeSensorAngleProvider.sensorListener, defaultSensor, 0, hingeSensorAngleProvider.listenerHandler);
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
