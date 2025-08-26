package com.samsung.sesl.compose.foundation;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
final class SwitchComponents {
    public static final /* synthetic */ SwitchComponents[] $VALUES;
    public static final SwitchComponents THUMB;
    public static final SwitchComponents TRACK;

    static {
        SwitchComponents switchComponents = new SwitchComponents("THUMB", 0);
        THUMB = switchComponents;
        SwitchComponents switchComponents2 = new SwitchComponents("TRACK", 1);
        TRACK = switchComponents2;
        SwitchComponents[] switchComponentsArr = {switchComponents, switchComponents2};
        $VALUES = switchComponentsArr;
        EnumEntriesKt.enumEntries(switchComponentsArr);
    }

    private SwitchComponents(String str, int i) {
    }

    public static SwitchComponents valueOf(String str) {
        return (SwitchComponents) Enum.valueOf(SwitchComponents.class, str);
    }

    public static SwitchComponents[] values() {
        return (SwitchComponents[]) $VALUES.clone();
    }
}
