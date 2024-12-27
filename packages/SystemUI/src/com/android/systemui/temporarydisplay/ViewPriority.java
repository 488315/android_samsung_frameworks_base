package com.android.systemui.temporarydisplay;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
