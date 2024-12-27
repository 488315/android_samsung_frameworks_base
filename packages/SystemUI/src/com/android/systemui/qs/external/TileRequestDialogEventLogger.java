package com.android.systemui.qs.external;

import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class TileRequestDialogEventLogger {
    public final InstanceIdSequence instanceIdSequence;
    public final UiEventLogger uiEventLogger;

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

    public TileRequestDialogEventLogger(UiEventLogger uiEventLogger, InstanceIdSequence instanceIdSequence) {
        this.uiEventLogger = uiEventLogger;
        this.instanceIdSequence = instanceIdSequence;
    }

    public TileRequestDialogEventLogger() {
        this(new UiEventLoggerImpl(), new InstanceIdSequence(QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING));
    }
}
