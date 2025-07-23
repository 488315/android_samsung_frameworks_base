package com.android.systemui.controls;

import android.util.StatsEvent;
import android.util.StatsLog;
import com.android.internal.logging.InstanceIdSequence;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ControlsMetricsLoggerImpl implements ControlsMetricsLogger {
    public int instanceId;
    public final InstanceIdSequence instanceIdSequence = new InstanceIdSequence(8192);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public final void log(int i, int i2, int i3, boolean z) {
        int i4 = this.instanceId;
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(349);
        newBuilder.writeInt(i);
        newBuilder.writeInt(i4);
        newBuilder.writeInt(i2);
        newBuilder.writeInt(i3);
        newBuilder.addBooleanAnnotation((byte) 1, true);
        newBuilder.writeBoolean(z);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
    }
}
