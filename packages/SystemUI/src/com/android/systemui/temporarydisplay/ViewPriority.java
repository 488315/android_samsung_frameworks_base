package com.android.systemui.temporarydisplay;

import kotlin.enums.EnumEntriesKt;

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
