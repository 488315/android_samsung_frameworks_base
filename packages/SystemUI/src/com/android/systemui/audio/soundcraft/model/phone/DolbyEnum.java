package com.android.systemui.audio.soundcraft.model.phone;

import com.android.systemui.R;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

public final class DolbyEnum {
    public static final /* synthetic */ EnumEntries $ENTRIES;
    public static final /* synthetic */ DolbyEnum[] $VALUES;
    public static final DolbyEnum Off;
    private final int nameResId;
    private final int realIndex;
    private final String routineActionValue;

    static {
        DolbyEnum dolbyEnum = new DolbyEnum("Off", 0, R.string.soundcraft_dolby_off, 5, "55555555");
        Off = dolbyEnum;
        DolbyEnum[] dolbyEnumArr = {dolbyEnum, new DolbyEnum("Auto", 1, R.string.soundcraft_dolby_auto, 0, "00000000"), new DolbyEnum("Movie", 2, R.string.soundcraft_dolby_movie, 1, "11111111"), new DolbyEnum("Music", 3, R.string.soundcraft_dolby_music, 2, "22222222"), new DolbyEnum("Voice", 4, R.string.soundcraft_dolby_voice, 3, "33333333")};
        $VALUES = dolbyEnumArr;
        $ENTRIES = EnumEntriesKt.enumEntries(dolbyEnumArr);
    }

    private DolbyEnum(String str, int i, int i2, int i3, String str2) {
        this.nameResId = i2;
        this.realIndex = i3;
        this.routineActionValue = str2;
    }

    public static DolbyEnum valueOf(String str) {
        return (DolbyEnum) Enum.valueOf(DolbyEnum.class, str);
    }

    public static DolbyEnum[] values() {
        return (DolbyEnum[]) $VALUES.clone();
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
