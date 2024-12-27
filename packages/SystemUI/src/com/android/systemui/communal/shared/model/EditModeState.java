package com.android.systemui.communal.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
