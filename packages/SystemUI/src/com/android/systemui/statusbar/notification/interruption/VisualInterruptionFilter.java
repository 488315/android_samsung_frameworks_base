package com.android.systemui.statusbar.notification.interruption;

import com.android.internal.logging.UiEventLogger;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class VisualInterruptionFilter {
    public final VisualInterruptionSuppressor$EventLogData eventLogData;
    public final String reason;
    public final Set types;
    public final UiEventLogger.UiEventEnum uiEventId;

    public VisualInterruptionFilter(Set<? extends VisualInterruptionType> set, String str, UiEventLogger.UiEventEnum uiEventEnum, VisualInterruptionSuppressor$EventLogData visualInterruptionSuppressor$EventLogData, boolean z) {
        this.types = set;
        this.reason = str;
        this.uiEventId = uiEventEnum;
        this.eventLogData = visualInterruptionSuppressor$EventLogData;
    }

    public /* synthetic */ VisualInterruptionFilter(Set set, String str, UiEventLogger.UiEventEnum uiEventEnum, VisualInterruptionSuppressor$EventLogData visualInterruptionSuppressor$EventLogData, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(set, str, (i & 4) != 0 ? null : uiEventEnum, (i & 8) != 0 ? null : visualInterruptionSuppressor$EventLogData, (i & 16) != 0 ? false : z);
    }

    public VisualInterruptionFilter(Set<? extends VisualInterruptionType> set, String str) {
        this(set, str, null, null, false, 24, null);
    }
}
