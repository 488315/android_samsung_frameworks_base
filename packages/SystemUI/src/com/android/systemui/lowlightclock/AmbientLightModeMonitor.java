package com.android.systemui.lowlightclock;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.util.sensors.AsyncSensorManager;
import java.io.PrintWriter;
import java.util.Optional;
import javax.inject.Provider;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class AmbientLightModeMonitor implements Dumpable {
    public static final boolean DEBUG;
    public final Optional algorithm;
    public final Optional lightSensor;
    public final AmbientLightModeMonitor$mSensorEventListener$1 mSensorEventListener = new SensorEventListener() { // from class: com.android.systemui.lowlightclock.AmbientLightModeMonitor$mSensorEventListener$1
        @Override // android.hardware.SensorEventListener
        public final void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.values.length == 0) {
                if (AmbientLightModeMonitor.DEBUG) {
                    Log.w("AmbientLightModeMonitor", "SensorEvent doesn't have any value");
                }
            } else if (this.this$0.algorithm.isPresent()) {
                if (this.this$0.algorithm.get() != null) {
                    throw new ClassCastException();
                }
                float f = sensorEvent.values[0];
                throw null;
            }
        }

        @Override // android.hardware.SensorEventListener
        public final void onAccuracyChanged(Sensor sensor, int i) {
        }
    };
    public final AsyncSensorManager sensorManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        DEBUG = Log.isLoggable("AmbientLightModeMonitor", 3);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.lowlightclock.AmbientLightModeMonitor$mSensorEventListener$1] */
    public AmbientLightModeMonitor(Optional<Object> optional, AsyncSensorManager asyncSensorManager, Optional<Provider> optional2) {
        this.algorithm = optional;
        this.sensorManager = asyncSensorManager;
        this.lightSensor = optional2;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println();
        printWriter.println("Ambient light mode monitor:");
        printWriter.println("  lightSensor=" + this.lightSensor);
        printWriter.println();
    }
}
