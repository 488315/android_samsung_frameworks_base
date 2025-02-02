package com.android.systemui.qs;

import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QsEventLoggerImpl implements QsEventLogger, UiEventLogger {
    public final /* synthetic */ UiEventLogger $$delegate_0;
    public final InstanceIdSequence sequence = new InstanceIdSequence(QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public QsEventLoggerImpl(UiEventLogger uiEventLogger) {
        this.$$delegate_0 = uiEventLogger;
    }

    public final void log(UiEventLogger.UiEventEnum uiEventEnum) {
        this.$$delegate_0.log(uiEventEnum);
    }

    public final void logWithInstanceId(UiEventLogger.UiEventEnum uiEventEnum, int i, String str, InstanceId instanceId) {
        this.$$delegate_0.logWithInstanceId(uiEventEnum, i, str, instanceId);
    }

    public final void logWithInstanceIdAndPosition(UiEventLogger.UiEventEnum uiEventEnum, int i, String str, InstanceId instanceId, int i2) {
        this.$$delegate_0.logWithInstanceIdAndPosition(uiEventEnum, i, str, instanceId, i2);
    }

    public final void logWithPosition(UiEventLogger.UiEventEnum uiEventEnum, int i, String str, int i2) {
        this.$$delegate_0.logWithPosition(uiEventEnum, i, str, i2);
    }

    public final void log(UiEventLogger.UiEventEnum uiEventEnum, int i, String str) {
        this.$$delegate_0.log(uiEventEnum, i, str);
    }

    public final void log(UiEventLogger.UiEventEnum uiEventEnum, InstanceId instanceId) {
        this.$$delegate_0.log(uiEventEnum, instanceId);
    }
}
