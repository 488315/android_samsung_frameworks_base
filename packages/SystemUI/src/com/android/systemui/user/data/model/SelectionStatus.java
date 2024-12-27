package com.android.systemui.user.data.model;

import kotlin.enums.EnumEntriesKt;

public final class SelectionStatus {
    public static final /* synthetic */ SelectionStatus[] $VALUES;
    public static final SelectionStatus SELECTION_COMPLETE;
    public static final SelectionStatus SELECTION_IN_PROGRESS;

    static {
        SelectionStatus selectionStatus = new SelectionStatus("SELECTION_IN_PROGRESS", 0);
        SELECTION_IN_PROGRESS = selectionStatus;
        SelectionStatus selectionStatus2 = new SelectionStatus("SELECTION_COMPLETE", 1);
        SELECTION_COMPLETE = selectionStatus2;
        SelectionStatus[] selectionStatusArr = {selectionStatus, selectionStatus2};
        $VALUES = selectionStatusArr;
        EnumEntriesKt.enumEntries(selectionStatusArr);
    }

    private SelectionStatus(String str, int i) {
    }

    public static SelectionStatus valueOf(String str) {
        return (SelectionStatus) Enum.valueOf(SelectionStatus.class, str);
    }

    public static SelectionStatus[] values() {
        return (SelectionStatus[]) $VALUES.clone();
    }
}
