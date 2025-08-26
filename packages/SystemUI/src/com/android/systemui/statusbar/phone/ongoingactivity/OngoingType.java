package com.android.systemui.statusbar.phone.ongoingactivity;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes3.dex */
public final class OngoingType {
    public static final /* synthetic */ OngoingType[] $VALUES;
    public static final OngoingType ENR;
    public static final OngoingType OA;
    public static final OngoingType SUB;

    static {
        OngoingType ongoingType = new OngoingType("OA", 0);
        OA = ongoingType;
        OngoingType ongoingType2 = new OngoingType("ENR", 1);
        ENR = ongoingType2;
        OngoingType ongoingType3 = new OngoingType("SUB", 2);
        SUB = ongoingType3;
        OngoingType[] ongoingTypeArr = {ongoingType, ongoingType2, ongoingType3};
        $VALUES = ongoingTypeArr;
        EnumEntriesKt.enumEntries(ongoingTypeArr);
    }

    private OngoingType(String str, int i) {
    }

    public static OngoingType valueOf(String str) {
        return (OngoingType) Enum.valueOf(OngoingType.class, str);
    }

    public static OngoingType[] values() {
        return (OngoingType[]) $VALUES.clone();
    }
}
