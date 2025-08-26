package com.android.systemui.shared.clocks.view;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes3.dex */
public final class XAlignment {
    public static final /* synthetic */ XAlignment[] $VALUES;
    public static final XAlignment CENTER;
    public static final XAlignment LEFT;
    public static final XAlignment RIGHT;

    static {
        XAlignment xAlignment = new XAlignment("LEFT", 0);
        LEFT = xAlignment;
        XAlignment xAlignment2 = new XAlignment("RIGHT", 1);
        RIGHT = xAlignment2;
        XAlignment xAlignment3 = new XAlignment("CENTER", 2);
        CENTER = xAlignment3;
        XAlignment[] xAlignmentArr = {xAlignment, xAlignment2, xAlignment3};
        $VALUES = xAlignmentArr;
        EnumEntriesKt.enumEntries(xAlignmentArr);
    }

    private XAlignment(String str, int i) {
    }

    public static XAlignment valueOf(String str) {
        return (XAlignment) Enum.valueOf(XAlignment.class, str);
    }

    public static XAlignment[] values() {
        return (XAlignment[]) $VALUES.clone();
    }
}
