package com.android.systemui.qs.panels.ui.compose.selection;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileState {
    public static final /* synthetic */ TileState[] $VALUES;
    public static final TileState GreyedOut;
    public static final TileState None;
    public static final TileState Placeable;
    public static final TileState Removable;
    public static final TileState Selected;

    static {
        TileState tileState = new TileState("None", 0);
        None = tileState;
        TileState tileState2 = new TileState("Removable", 1);
        Removable = tileState2;
        TileState tileState3 = new TileState("Selected", 2);
        Selected = tileState3;
        TileState tileState4 = new TileState("Placeable", 3);
        Placeable = tileState4;
        TileState tileState5 = new TileState("GreyedOut", 4);
        GreyedOut = tileState5;
        TileState[] tileStateArr = {tileState, tileState2, tileState3, tileState4, tileState5};
        $VALUES = tileStateArr;
        EnumEntriesKt.enumEntries(tileStateArr);
    }

    private TileState(String str, int i) {
    }

    public static TileState valueOf(String str) {
        return (TileState) Enum.valueOf(TileState.class, str);
    }

    public static TileState[] values() {
        return (TileState[]) $VALUES.clone();
    }
}
