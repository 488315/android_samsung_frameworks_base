package com.android.systemui.statusbar.notification.interruption;

import com.android.internal.logging.UiEventLogger;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class VisualInterruptionFilter {
    public final VisualInterruptionSuppressor$EventLogData eventLogData;
    public final String reason;
    public final Set types;
    public final UiEventLogger.UiEventEnum uiEventId;

    public VisualInterruptionFilter(Set<? extends VisualInterruptionType> set, String str, UiEventLogger.UiEventEnum uiEventEnum, VisualInterruptionSuppressor$EventLogData visualInterruptionSuppressor$EventLogData) {
        this.types = set;
    }

    public /* synthetic */ VisualInterruptionFilter(Set set, String str, UiEventLogger.UiEventEnum uiEventEnum, VisualInterruptionSuppressor$EventLogData visualInterruptionSuppressor$EventLogData, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(set, str, (i & 4) != 0 ? null : uiEventEnum, (i & 8) != 0 ? null : visualInterruptionSuppressor$EventLogData);
    }

    public VisualInterruptionFilter(Set<? extends VisualInterruptionType> set, String str) {
        this(set, str, null, null, 8, null);
    }
}
