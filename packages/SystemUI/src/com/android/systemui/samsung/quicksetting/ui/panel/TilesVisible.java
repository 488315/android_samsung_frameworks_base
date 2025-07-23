package com.android.systemui.samsung.quicksetting.ui.panel;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TilesVisible {
    public static final /* synthetic */ TilesVisible[] $VALUES;
    public static final TilesVisible INVISIBLE = null;
    public static final TilesVisible VISIBLE;

    static {
        TilesVisible tilesVisible = new TilesVisible("VISIBLE", 0);
        VISIBLE = tilesVisible;
        TilesVisible[] tilesVisibleArr = {tilesVisible, new TilesVisible("INVISIBLE", 1)};
        $VALUES = tilesVisibleArr;
        EnumEntriesKt.enumEntries(tilesVisibleArr);
    }

    private TilesVisible(String str, int i) {
    }

    public static TilesVisible valueOf(String str) {
        return (TilesVisible) Enum.valueOf(TilesVisible.class, str);
    }

    public static TilesVisible[] values() {
        return (TilesVisible[]) $VALUES.clone();
    }
}
