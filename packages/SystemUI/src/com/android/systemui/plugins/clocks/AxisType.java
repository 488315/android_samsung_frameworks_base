package com.android.systemui.plugins.clocks;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes2.dex */
public final class AxisType {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ AxisType[] $VALUES;
    public static final AxisType Float = new AxisType("Float", 0);
    public static final AxisType Boolean = new AxisType("Boolean", 1);

    private static final /* synthetic */ AxisType[] $values() {
        return new AxisType[]{Float, Boolean};
    }

    static {
        AxisType[] axisTypeArr$values = $values();
        $VALUES = axisTypeArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(axisTypeArr$values);
    }

    private AxisType(String str, int i) {
    }

    public static EnumEntries getEntries() {
        return $ENTRIES;
    }

    public static AxisType valueOf(String str) {
        return (AxisType) Enum.valueOf(AxisType.class, str);
    }

    public static AxisType[] values() {
        return (AxisType[]) $VALUES.clone();
    }
}
