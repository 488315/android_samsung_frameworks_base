package com.android.systemui.communal.shared.model;

import kotlin.enums.EnumEntriesKt;

public final class EditModeState {
    public static final /* synthetic */ EditModeState[] $VALUES;
    public static final EditModeState SHOWING;
    public static final EditModeState STARTING;
    private final int value;

    static {
        EditModeState editModeState = new EditModeState("STARTING", 0, 0);
        STARTING = editModeState;
        EditModeState editModeState2 = new EditModeState("SHOWING", 1, 1);
        SHOWING = editModeState2;
        EditModeState[] editModeStateArr = {editModeState, editModeState2};
        $VALUES = editModeStateArr;
        EnumEntriesKt.enumEntries(editModeStateArr);
    }

    private EditModeState(String str, int i, int i2) {
        this.value = i2;
    }

    public static EditModeState valueOf(String str) {
        return (EditModeState) Enum.valueOf(EditModeState.class, str);
    }

    public static EditModeState[] values() {
        return (EditModeState[]) $VALUES.clone();
    }
}
