package com.samsung.sesl.compose.ui.soundeffect;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
public final class SeslSoundEffectConstants {
    public static final /* synthetic */ SeslSoundEffectConstants[] $VALUES;
    public static final SeslSoundEffectConstants Click;
    private final int soundConstant;

    static {
        SeslSoundEffectConstants seslSoundEffectConstants = new SeslSoundEffectConstants("Click", 0, 0);
        Click = seslSoundEffectConstants;
        SeslSoundEffectConstants[] seslSoundEffectConstantsArr = {seslSoundEffectConstants};
        $VALUES = seslSoundEffectConstantsArr;
        EnumEntriesKt.enumEntries(seslSoundEffectConstantsArr);
    }

    private SeslSoundEffectConstants(String str, int i, int i2) {
        this.soundConstant = i2;
    }

    public static SeslSoundEffectConstants valueOf(String str) {
        return (SeslSoundEffectConstants) Enum.valueOf(SeslSoundEffectConstants.class, str);
    }

    public static SeslSoundEffectConstants[] values() {
        return (SeslSoundEffectConstants[]) $VALUES.clone();
    }

    public final int getSoundConstant$sesl8_compose_core_release() {
        return this.soundConstant;
    }
}
