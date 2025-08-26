package com.android.systemui.lowlightclock;

import android.util.Log;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.shared.condition.Condition;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class LowLightCondition extends Condition {
    public final AmbientLightModeMonitor ambientLightModeMonitor;

    public LowLightCondition(CoroutineScope coroutineScope, AmbientLightModeMonitor ambientLightModeMonitor, UiEventLogger uiEventLogger) {
        super(coroutineScope, null, false, 6, null);
        this.ambientLightModeMonitor = ambientLightModeMonitor;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final int getStartStrategy() {
        return 2;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final Object start(Continuation continuation) {
        new Object(this) { // from class: com.android.systemui.lowlightclock.LowLightCondition.start.2
        };
        boolean z = AmbientLightModeMonitor.DEBUG;
        AmbientLightModeMonitor ambientLightModeMonitor = this.ambientLightModeMonitor;
        if (z) {
            ambientLightModeMonitor.getClass();
            Log.d("AmbientLightModeMonitor", "start monitoring ambient light mode");
        }
        if (ambientLightModeMonitor.lightSensor.isEmpty() || ((Provider) ambientLightModeMonitor.lightSensor.get()).get() == null) {
            if (z) {
                Log.w("AmbientLightModeMonitor", "light sensor not available");
            }
        } else {
            if (!ambientLightModeMonitor.algorithm.isEmpty()) {
                ambientLightModeMonitor.algorithm.get().getClass();
                throw new ClassCastException();
            }
            if (z) {
                Log.w("AmbientLightModeMonitor", "debounce algorithm not available");
            }
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final void stop() {
        boolean z = AmbientLightModeMonitor.DEBUG;
        AmbientLightModeMonitor ambientLightModeMonitor = this.ambientLightModeMonitor;
        if (z) {
            ambientLightModeMonitor.getClass();
            Log.d("AmbientLightModeMonitor", "stop monitoring ambient light mode");
        }
        if (ambientLightModeMonitor.algorithm.isPresent()) {
            ambientLightModeMonitor.algorithm.get().getClass();
            throw new ClassCastException();
        }
        ambientLightModeMonitor.sensorManager.unregisterListener(ambientLightModeMonitor.mSensorEventListener);
        updateCondition(false);
    }
}
