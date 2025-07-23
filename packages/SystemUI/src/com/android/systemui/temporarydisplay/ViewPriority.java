package com.android.systemui.temporarydisplay;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ViewPriority {
    public static final /* synthetic */ ViewPriority[] $VALUES;
    public static final ViewPriority NORMAL;

    static {
        ViewPriority viewPriority = new ViewPriority("NORMAL", 0);
        NORMAL = viewPriority;
        ViewPriority[] viewPriorityArr = {viewPriority, new ViewPriority("CRITICAL", 1)};
        $VALUES = viewPriorityArr;
        EnumEntriesKt.enumEntries(viewPriorityArr);
    }

    private ViewPriority(String str, int i) {
    }

    public static ViewPriority valueOf(String str) {
        return (ViewPriority) Enum.valueOf(ViewPriority.class, str);
    }

    public static ViewPriority[] values() {
        return (ViewPriority[]) $VALUES.clone();
    }
}
