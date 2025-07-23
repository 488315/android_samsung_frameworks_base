package com.android.systemui.util.kotlin;

import android.hardware.Sensor;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AsyncSensorManagerExtKt {
    public static final Flow observeTriggerSensor(AsyncSensorManager asyncSensorManager, Sensor sensor) {
        return FlowConflatedKt.conflatedCallbackFlow(new AsyncSensorManagerExtKt$observeTriggerSensor$1(asyncSensorManager, sensor, null));
    }
}
