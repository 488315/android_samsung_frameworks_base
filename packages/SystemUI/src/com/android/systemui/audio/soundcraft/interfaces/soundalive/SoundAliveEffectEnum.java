package com.android.systemui.audio.soundcraft.interfaces.soundalive;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

public final class SoundAliveEffectEnum {
    public static final /* synthetic */ EnumEntries $ENTRIES;
    public static final /* synthetic */ SoundAliveEffectEnum[] $VALUES;
    public static final SoundAliveEffectEnum DOLBY_INDEX;
    public static final SoundAliveEffectEnum EQ_INDEX;
    public static final SoundAliveEffectEnum SPATIAL_AUDIO;
    public static final SoundAliveEffectEnum UHQ_UPSCALER;
    public static final SoundAliveEffectEnum VOICE_BOOST_EFFECT;
    public static final SoundAliveEffectEnum VOLUME_NORMALIZATION_EFFECT;
    private final String settingName;

    static {
        SoundAliveEffectEnum soundAliveEffectEnum = new SoundAliveEffectEnum("DOLBY_INDEX", 0, "DOLBY_ATMOS_LEVEL");
        DOLBY_INDEX = soundAliveEffectEnum;
        SoundAliveEffectEnum soundAliveEffectEnum2 = new SoundAliveEffectEnum("EQ_INDEX", 1, "EQUALIZER_PRESET_INDEX");
        EQ_INDEX = soundAliveEffectEnum2;
        SoundAliveEffectEnum soundAliveEffectEnum3 = new SoundAliveEffectEnum("VOICE_BOOST_EFFECT", 2, "VOICE_BOOST_EFFECT");
        VOICE_BOOST_EFFECT = soundAliveEffectEnum3;
        SoundAliveEffectEnum soundAliveEffectEnum4 = new SoundAliveEffectEnum("VOLUME_NORMALIZATION_EFFECT", 3, "VOLUME_NORMALIZATION_EFFECT");
        VOLUME_NORMALIZATION_EFFECT = soundAliveEffectEnum4;
        SoundAliveEffectEnum soundAliveEffectEnum5 = new SoundAliveEffectEnum("UHQ_UPSCALER", 4, "K2HD_EFFECT");
        UHQ_UPSCALER = soundAliveEffectEnum5;
        SoundAliveEffectEnum soundAliveEffectEnum6 = new SoundAliveEffectEnum("SPATIAL_AUDIO", 5, "SPATIAL_AUDIO");
        SPATIAL_AUDIO = soundAliveEffectEnum6;
        SoundAliveEffectEnum[] soundAliveEffectEnumArr = {soundAliveEffectEnum, soundAliveEffectEnum2, soundAliveEffectEnum3, soundAliveEffectEnum4, soundAliveEffectEnum5, soundAliveEffectEnum6};
        $VALUES = soundAliveEffectEnumArr;
        $ENTRIES = EnumEntriesKt.enumEntries(soundAliveEffectEnumArr);
    }

    private SoundAliveEffectEnum(String str, int i, String str2) {
        this.settingName = str2;
    }

    public static SoundAliveEffectEnum valueOf(String str) {
        return (SoundAliveEffectEnum) Enum.valueOf(SoundAliveEffectEnum.class, str);
    }

    public static SoundAliveEffectEnum[] values() {
        return (SoundAliveEffectEnum[]) $VALUES.clone();
    }

    public final String getSettingName() {
        return this.settingName;
    }
}
