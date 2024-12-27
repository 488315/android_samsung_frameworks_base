package com.android.systemui.temporarydisplay;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class TemporaryViewUiEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ TemporaryViewUiEvent[] $VALUES;
    public static final TemporaryViewUiEvent TEMPORARY_VIEW_ADDED;
    public static final TemporaryViewUiEvent TEMPORARY_VIEW_MANUALLY_DISMISSED;
    private final int metricId;

    static {
        TemporaryViewUiEvent temporaryViewUiEvent = new TemporaryViewUiEvent("TEMPORARY_VIEW_ADDED", 0, 1389);
        TEMPORARY_VIEW_ADDED = temporaryViewUiEvent;
        TemporaryViewUiEvent temporaryViewUiEvent2 = new TemporaryViewUiEvent("TEMPORARY_VIEW_MANUALLY_DISMISSED", 1, 1390);
        TEMPORARY_VIEW_MANUALLY_DISMISSED = temporaryViewUiEvent2;
        TemporaryViewUiEvent[] temporaryViewUiEventArr = {temporaryViewUiEvent, temporaryViewUiEvent2};
        $VALUES = temporaryViewUiEventArr;
        EnumEntriesKt.enumEntries(temporaryViewUiEventArr);
    }

    private TemporaryViewUiEvent(String str, int i, int i2) {
        this.metricId = i2;
    }

    public static TemporaryViewUiEvent valueOf(String str) {
        return (TemporaryViewUiEvent) Enum.valueOf(TemporaryViewUiEvent.class, str);
    }

    public static TemporaryViewUiEvent[] values() {
        return (TemporaryViewUiEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this.metricId;
    }
}
