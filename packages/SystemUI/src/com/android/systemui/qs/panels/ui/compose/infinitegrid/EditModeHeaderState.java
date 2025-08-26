package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes2.dex */
final class EditModeHeaderState {
    public static final /* synthetic */ EditModeHeaderState[] $VALUES;
    public static final EditModeHeaderState Idle;
    public static final EditModeHeaderState Place;
    public static final EditModeHeaderState Remove;

    static {
        EditModeHeaderState editModeHeaderState = new EditModeHeaderState("Remove", 0);
        Remove = editModeHeaderState;
        EditModeHeaderState editModeHeaderState2 = new EditModeHeaderState("Place", 1);
        Place = editModeHeaderState2;
        EditModeHeaderState editModeHeaderState3 = new EditModeHeaderState("Idle", 2);
        Idle = editModeHeaderState3;
        EditModeHeaderState[] editModeHeaderStateArr = {editModeHeaderState, editModeHeaderState2, editModeHeaderState3};
        $VALUES = editModeHeaderStateArr;
        EnumEntriesKt.enumEntries(editModeHeaderStateArr);
    }

    private EditModeHeaderState(String str, int i) {
    }

    public static EditModeHeaderState valueOf(String str) {
        return (EditModeHeaderState) Enum.valueOf(EditModeHeaderState.class, str);
    }

    public static EditModeHeaderState[] values() {
        return (EditModeHeaderState[]) $VALUES.clone();
    }
}
