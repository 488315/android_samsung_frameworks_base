package com.android.systemui.shared.clocks.view;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes3.dex */
public final class VerticalAlignment {
    public static final /* synthetic */ VerticalAlignment[] $VALUES;
    public static final VerticalAlignment BASELINE;
    public static final VerticalAlignment BOTTOM;
    public static final VerticalAlignment CENTER;
    public static final VerticalAlignment TOP;

    static {
        VerticalAlignment verticalAlignment = new VerticalAlignment("TOP", 0);
        TOP = verticalAlignment;
        VerticalAlignment verticalAlignment2 = new VerticalAlignment("BOTTOM", 1);
        BOTTOM = verticalAlignment2;
        VerticalAlignment verticalAlignment3 = new VerticalAlignment("BASELINE", 2);
        BASELINE = verticalAlignment3;
        VerticalAlignment verticalAlignment4 = new VerticalAlignment("CENTER", 3);
        CENTER = verticalAlignment4;
        VerticalAlignment[] verticalAlignmentArr = {verticalAlignment, verticalAlignment2, verticalAlignment3, verticalAlignment4};
        $VALUES = verticalAlignmentArr;
        EnumEntriesKt.enumEntries(verticalAlignmentArr);
    }

    private VerticalAlignment(String str, int i) {
    }

    public static VerticalAlignment valueOf(String str) {
        return (VerticalAlignment) Enum.valueOf(VerticalAlignment.class, str);
    }

    public static VerticalAlignment[] values() {
        return (VerticalAlignment[]) $VALUES.clone();
    }
}
