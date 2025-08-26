package com.android.systemui.qs.customize.view;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes2.dex */
public final class AnimType {
    public static final /* synthetic */ AnimType[] $VALUES;
    public static final AnimType SEPARATE;
    public static final AnimType TOGETHER;

    static {
        AnimType animType = new AnimType("SEPARATE", 0);
        SEPARATE = animType;
        AnimType animType2 = new AnimType("TOGETHER", 1);
        TOGETHER = animType2;
        AnimType[] animTypeArr = {animType, animType2};
        $VALUES = animTypeArr;
        EnumEntriesKt.enumEntries(animTypeArr);
    }

    private AnimType(String str, int i) {
    }

    public static AnimType valueOf(String str) {
        return (AnimType) Enum.valueOf(AnimType.class, str);
    }

    public static AnimType[] values() {
        return (AnimType[]) $VALUES.clone();
    }
}
