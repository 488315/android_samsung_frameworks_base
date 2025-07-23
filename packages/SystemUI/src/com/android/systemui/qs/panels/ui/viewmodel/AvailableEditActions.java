package com.android.systemui.qs.panels.ui.viewmodel;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
