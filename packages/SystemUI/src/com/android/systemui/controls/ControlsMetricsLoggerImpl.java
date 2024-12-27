package com.android.systemui.controls;

import android.util.StatsEvent;
import android.util.StatsLog;
import com.android.internal.logging.InstanceIdSequence;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ControlsMetricsLoggerImpl implements ControlsMetricsLogger {
    public int instanceId;
    public final InstanceIdSequence instanceIdSequence = new InstanceIdSequence(8192);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
