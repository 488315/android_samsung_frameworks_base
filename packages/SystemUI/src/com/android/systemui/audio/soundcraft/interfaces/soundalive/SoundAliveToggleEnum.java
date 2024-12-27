package com.android.systemui.audio.soundcraft.interfaces.soundalive;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SoundAliveToggleEnum {
    public static final /* synthetic */ SoundAliveToggleEnum[] $VALUES;
    public static final Companion Companion;
    public static final SoundAliveToggleEnum OFF;
    public static final SoundAliveToggleEnum ON;
    private final String routineActionValue;
    private final boolean value;

    public final class Companion {
        private Companion() {
        }

        public static boolean getBooleanActionValue(String str) {
            SoundAliveToggleEnum soundAliveToggleEnum = SoundAliveToggleEnum.ON;
            return str.equals(soundAliveToggleEnum.getRoutineActionValue()) ? soundAliveToggleEnum.getValue() : SoundAliveToggleEnum.OFF.getValue();
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        SoundAliveToggleEnum soundAliveToggleEnum = new SoundAliveToggleEnum("ON", 0, true, "11111111");
        ON = soundAliveToggleEnum;
        SoundAliveToggleEnum soundAliveToggleEnum2 = new SoundAliveToggleEnum("OFF", 1, false, "00000000");
        OFF = soundAliveToggleEnum2;
        SoundAliveToggleEnum[] soundAliveToggleEnumArr = {soundAliveToggleEnum, soundAliveToggleEnum2};
        $VALUES = soundAliveToggleEnumArr;
        EnumEntriesKt.enumEntries(soundAliveToggleEnumArr);
        Companion = new Companion(null);
    }

    private SoundAliveToggleEnum(String str, int i, boolean z, String str2) {
        this.value = z;
        this.routineActionValue = str2;
    }

    public static SoundAliveToggleEnum valueOf(String str) {
        return (SoundAliveToggleEnum) Enum.valueOf(SoundAliveToggleEnum.class, str);
    }

    public static SoundAliveToggleEnum[] values() {
        return (SoundAliveToggleEnum[]) $VALUES.clone();
    }

    public final String getRoutineActionValue() {
        return this.routineActionValue;
    }

    public final boolean getValue() {
        return this.value;
    }
}
