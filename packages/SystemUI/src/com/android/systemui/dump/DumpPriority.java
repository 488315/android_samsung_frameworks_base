package com.android.systemui.dump;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class DumpPriority {
    public static final /* synthetic */ DumpPriority[] $VALUES;
    public static final DumpPriority CRITICAL;
    public static final DumpPriority NORMAL;

    static {
        DumpPriority dumpPriority = new DumpPriority("CRITICAL", 0);
        CRITICAL = dumpPriority;
        DumpPriority dumpPriority2 = new DumpPriority("NORMAL", 1);
        NORMAL = dumpPriority2;
        DumpPriority[] dumpPriorityArr = {dumpPriority, dumpPriority2};
        $VALUES = dumpPriorityArr;
        EnumEntriesKt.enumEntries(dumpPriorityArr);
    }

    private DumpPriority(String str, int i) {
    }

    public static DumpPriority valueOf(String str) {
        return (DumpPriority) Enum.valueOf(DumpPriority.class, str);
    }

    public static DumpPriority[] values() {
        return (DumpPriority[]) $VALUES.clone();
    }
}
