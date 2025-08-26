package com.android.systemui.communal.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes2.dex */
public final class WhenToDream {
    public static final /* synthetic */ WhenToDream[] $VALUES;
    public static final WhenToDream NEVER;
    public static final WhenToDream WHILE_CHARGING;
    public static final WhenToDream WHILE_DOCKED;
    public static final WhenToDream WHILE_POSTURED;

    static {
        WhenToDream whenToDream = new WhenToDream("NEVER", 0);
        NEVER = whenToDream;
        WhenToDream whenToDream2 = new WhenToDream("WHILE_CHARGING", 1);
        WHILE_CHARGING = whenToDream2;
        WhenToDream whenToDream3 = new WhenToDream("WHILE_DOCKED", 2);
        WHILE_DOCKED = whenToDream3;
        WhenToDream whenToDream4 = new WhenToDream("WHILE_POSTURED", 3);
        WHILE_POSTURED = whenToDream4;
        WhenToDream[] whenToDreamArr = {whenToDream, whenToDream2, whenToDream3, whenToDream4};
        $VALUES = whenToDreamArr;
        EnumEntriesKt.enumEntries(whenToDreamArr);
    }

    private WhenToDream(String str, int i) {
    }

    public static WhenToDream valueOf(String str) {
        return (WhenToDream) Enum.valueOf(WhenToDream.class, str);
    }

    public static WhenToDream[] values() {
        return (WhenToDream[]) $VALUES.clone();
    }
}
