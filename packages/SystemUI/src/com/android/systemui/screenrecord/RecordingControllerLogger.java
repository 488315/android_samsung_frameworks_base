package com.android.systemui.screenrecord;

import com.android.systemui.log.LogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class RecordingControllerLogger {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LogBuffer logger;

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

    public RecordingControllerLogger(LogBuffer logBuffer) {
        this.logger = logBuffer;
    }
}
