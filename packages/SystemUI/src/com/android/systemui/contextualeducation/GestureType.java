package com.android.systemui.contextualeducation;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GestureType {
    public static final /* synthetic */ GestureType[] $VALUES;
    public static final GestureType ALL_APPS;
    public static final GestureType BACK;
    public static final GestureType HOME;
    public static final GestureType OVERVIEW;

    static {
        GestureType gestureType = new GestureType("BACK", 0);
        BACK = gestureType;
        GestureType gestureType2 = new GestureType("HOME", 1);
        HOME = gestureType2;
        GestureType gestureType3 = new GestureType("OVERVIEW", 2);
        OVERVIEW = gestureType3;
        GestureType gestureType4 = new GestureType("ALL_APPS", 3);
        ALL_APPS = gestureType4;
        GestureType[] gestureTypeArr = {gestureType, gestureType2, gestureType3, gestureType4};
        $VALUES = gestureTypeArr;
        EnumEntriesKt.enumEntries(gestureTypeArr);
    }

    private GestureType(String str, int i) {
    }

    public static GestureType valueOf(String str) {
        return (GestureType) Enum.valueOf(GestureType.class, str);
    }

    public static GestureType[] values() {
        return (GestureType[]) $VALUES.clone();
    }
}
