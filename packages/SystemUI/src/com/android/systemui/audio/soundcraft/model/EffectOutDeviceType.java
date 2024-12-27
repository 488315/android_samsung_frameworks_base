package com.android.systemui.audio.soundcraft.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class EffectOutDeviceType {
    public static final /* synthetic */ EffectOutDeviceType[] $VALUES;
    public static final EffectOutDeviceType BUDS;
    public static final EffectOutDeviceType PHONE;

    static {
        EffectOutDeviceType effectOutDeviceType = new EffectOutDeviceType("BUDS", 0);
        BUDS = effectOutDeviceType;
        EffectOutDeviceType effectOutDeviceType2 = new EffectOutDeviceType("PHONE", 1);
        PHONE = effectOutDeviceType2;
        EffectOutDeviceType[] effectOutDeviceTypeArr = {effectOutDeviceType, effectOutDeviceType2};
        $VALUES = effectOutDeviceTypeArr;
        EnumEntriesKt.enumEntries(effectOutDeviceTypeArr);
    }

    private EffectOutDeviceType(String str, int i) {
    }

    public static EffectOutDeviceType valueOf(String str) {
        return (EffectOutDeviceType) Enum.valueOf(EffectOutDeviceType.class, str);
    }

    public static EffectOutDeviceType[] values() {
        return (EffectOutDeviceType[]) $VALUES.clone();
    }
}
