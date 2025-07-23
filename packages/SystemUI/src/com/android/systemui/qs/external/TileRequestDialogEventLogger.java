package com.android.systemui.qs.external;

import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileRequestDialogEventLogger {
    public final InstanceIdSequence instanceIdSequence;
    public final UiEventLogger uiEventLogger;

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

    public TileRequestDialogEventLogger(UiEventLogger uiEventLogger, InstanceIdSequence instanceIdSequence) {
        this.uiEventLogger = uiEventLogger;
        this.instanceIdSequence = instanceIdSequence;
    }

    public TileRequestDialogEventLogger() {
        this(new UiEventLoggerImpl(), new InstanceIdSequence(1048576));
    }
}
