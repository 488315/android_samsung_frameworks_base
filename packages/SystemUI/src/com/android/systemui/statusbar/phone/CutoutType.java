package com.android.systemui.statusbar.phone;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class CutoutType {
    public static final /* synthetic */ CutoutType[] $VALUES;
    public static final CutoutType CENTER_CUTOUT;
    public static final CutoutType LEFT_CUTOUT;
    public static final CutoutType NO_CUTOUT;
    public static final CutoutType RIGHT_CUTOUT;
    public static final CutoutType UDC;

    static {
        CutoutType cutoutType = new CutoutType("NO_CUTOUT", 0);
        NO_CUTOUT = cutoutType;
        CutoutType cutoutType2 = new CutoutType("LEFT_CUTOUT", 1);
        LEFT_CUTOUT = cutoutType2;
        CutoutType cutoutType3 = new CutoutType("RIGHT_CUTOUT", 2);
        RIGHT_CUTOUT = cutoutType3;
        CutoutType cutoutType4 = new CutoutType("CENTER_CUTOUT", 3);
        CENTER_CUTOUT = cutoutType4;
        CutoutType cutoutType5 = new CutoutType("UDC", 4);
        UDC = cutoutType5;
        CutoutType[] cutoutTypeArr = {cutoutType, cutoutType2, cutoutType3, cutoutType4, cutoutType5};
        $VALUES = cutoutTypeArr;
        EnumEntriesKt.enumEntries(cutoutTypeArr);
    }

    private CutoutType(String str, int i) {
    }

    public static CutoutType valueOf(String str) {
        return (CutoutType) Enum.valueOf(CutoutType.class, str);
    }

    public static CutoutType[] values() {
        return (CutoutType[]) $VALUES.clone();
    }
}
