package com.android.systemui.logging;

import com.android.systemui.log.LogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class PanelScreenShotBufferLogger {
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

    public PanelScreenShotBufferLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }
}
