package com.android.systemui.accessibility.hearingaid;

import com.android.internal.logging.UiEventLogger;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class HearingDevicesUiEventLogger {
    public final UiEventLogger uiEventLogger;

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

    public HearingDevicesUiEventLogger(UiEventLogger uiEventLogger) {
        this.uiEventLogger = uiEventLogger;
    }

    public final void log(UiEventLogger.UiEventEnum uiEventEnum, int i, String str) {
        this.uiEventLogger.log(uiEventEnum, i, str);
    }
}
