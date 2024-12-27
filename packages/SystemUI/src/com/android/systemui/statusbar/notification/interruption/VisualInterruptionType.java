package com.android.systemui.statusbar.notification.interruption;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class VisualInterruptionType {
    public static final /* synthetic */ VisualInterruptionType[] $VALUES;
    public static final VisualInterruptionType BUBBLE;
    public static final VisualInterruptionType PEEK;
    public static final VisualInterruptionType PULSE;

    static {
        VisualInterruptionType visualInterruptionType = new VisualInterruptionType("PEEK", 0);
        PEEK = visualInterruptionType;
        VisualInterruptionType visualInterruptionType2 = new VisualInterruptionType("PULSE", 1);
        PULSE = visualInterruptionType2;
        VisualInterruptionType visualInterruptionType3 = new VisualInterruptionType("BUBBLE", 2);
        BUBBLE = visualInterruptionType3;
        VisualInterruptionType[] visualInterruptionTypeArr = {visualInterruptionType, visualInterruptionType2, visualInterruptionType3};
        $VALUES = visualInterruptionTypeArr;
        EnumEntriesKt.enumEntries(visualInterruptionTypeArr);
    }

    private VisualInterruptionType(String str, int i) {
    }

    public static VisualInterruptionType valueOf(String str) {
        return (VisualInterruptionType) Enum.valueOf(VisualInterruptionType.class, str);
    }

    public static VisualInterruptionType[] values() {
        return (VisualInterruptionType[]) $VALUES.clone();
    }
}
