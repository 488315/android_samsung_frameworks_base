package com.android.systemui.statusbar.policy;

import com.android.systemui.log.LogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class BatteryControllerLogger {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LogBuffer logBuffer;

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

    public BatteryControllerLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }
}
