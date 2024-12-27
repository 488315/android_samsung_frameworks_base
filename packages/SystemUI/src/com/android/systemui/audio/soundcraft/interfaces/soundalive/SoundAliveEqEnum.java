package com.android.systemui.audio.soundcraft.interfaces.soundalive;

import com.android.systemui.R;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

public final class SoundAliveEqEnum {
    public static final /* synthetic */ EnumEntries $ENTRIES;
    public static final /* synthetic */ SoundAliveEqEnum[] $VALUES;
    public static final SoundAliveEqEnum Balanced;
    private final int nameResId;
    private final int realIndex;
    private final String routineActionValue;

    static {
        SoundAliveEqEnum soundAliveEqEnum = new SoundAliveEqEnum("Balanced", 0, R.string.soundcraft_equalizer_mode_balanced, 0, "00000000");
        Balanced = soundAliveEqEnum;
        SoundAliveEqEnum[] soundAliveEqEnumArr = {soundAliveEqEnum, new SoundAliveEqEnum("BaseBoost", 1, R.string.soundcraft_equalizer_mode_bass_boost, 1, "11111111"), new SoundAliveEqEnum("Smooth", 2, R.string.soundcraft_equalizer_mode_smooth, 2, "22222222"), new SoundAliveEqEnum("Dynamic", 3, R.string.soundcraft_equalizer_mode_dynamic, 3, "33333333"), new SoundAliveEqEnum("Clear", 4, R.string.soundcraft_equalizer_mode_clear, 4, "44444444"), new SoundAliveEqEnum("TrebleBoost", 5, R.string.soundcraft_equalizer_mode_treble_boost, 6, "66666666"), new SoundAliveEqEnum(SystemUIAnalytics.DT_WALLPAPER_SET_FROM_CUSTOM, 6, R.string.soundcraft_equalizer_mode_custom, 5, "55555555")};
        $VALUES = soundAliveEqEnumArr;
        $ENTRIES = EnumEntriesKt.enumEntries(soundAliveEqEnumArr);
    }

    private SoundAliveEqEnum(String str, int i, int i2, int i3, String str2) {
        this.nameResId = i2;
        this.realIndex = i3;
        this.routineActionValue = str2;
    }

    public static SoundAliveEqEnum valueOf(String str) {
        return (SoundAliveEqEnum) Enum.valueOf(SoundAliveEqEnum.class, str);
    }

    public static SoundAliveEqEnum[] values() {
        return (SoundAliveEqEnum[]) $VALUES.clone();
    }

    public final int getNameResId() {
        return this.nameResId;
    }

    public final int getRealIndex() {
        return this.realIndex;
    }

    public final String getRoutineActionValue() {
        return this.routineActionValue;
    }
}
