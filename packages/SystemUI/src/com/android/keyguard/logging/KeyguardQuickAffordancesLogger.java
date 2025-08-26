package com.android.keyguard.logging;

import com.android.systemui.log.LogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class KeyguardQuickAffordancesLogger {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LogBuffer buffer;

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

    public KeyguardQuickAffordancesLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
