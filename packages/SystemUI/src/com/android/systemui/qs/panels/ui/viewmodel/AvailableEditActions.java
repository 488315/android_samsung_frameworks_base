package com.android.systemui.qs.panels.ui.viewmodel;

import kotlin.enums.EnumEntriesKt;

public final class AvailableEditActions {
    public static final /* synthetic */ AvailableEditActions[] $VALUES;
    public static final AvailableEditActions ADD;
    public static final AvailableEditActions MOVE;
    public static final AvailableEditActions REMOVE;

    static {
        AvailableEditActions availableEditActions = new AvailableEditActions("ADD", 0);
        ADD = availableEditActions;
        AvailableEditActions availableEditActions2 = new AvailableEditActions("REMOVE", 1);
        REMOVE = availableEditActions2;
        AvailableEditActions availableEditActions3 = new AvailableEditActions("MOVE", 2);
        MOVE = availableEditActions3;
        AvailableEditActions[] availableEditActionsArr = {availableEditActions, availableEditActions2, availableEditActions3};
        $VALUES = availableEditActionsArr;
        EnumEntriesKt.enumEntries(availableEditActionsArr);
    }

    private AvailableEditActions(String str, int i) {
    }

    public static AvailableEditActions valueOf(String str) {
        return (AvailableEditActions) Enum.valueOf(AvailableEditActions.class, str);
    }

    public static AvailableEditActions[] values() {
        return (AvailableEditActions[]) $VALUES.clone();
    }
}
