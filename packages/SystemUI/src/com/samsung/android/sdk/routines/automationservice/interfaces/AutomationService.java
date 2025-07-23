package com.samsung.android.sdk.routines.automationservice.interfaces;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface AutomationService {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SystemRoutineType {
        public static final /* synthetic */ EnumEntries $ENTRIES;
        public static final /* synthetic */ SystemRoutineType[] $VALUES;
        public static final Companion Companion;
        public static final SystemRoutineType GAME_CRAFT;
        public static final SystemRoutineType SOUND_CRAFT_FOR_BUDS;
        public static final SystemRoutineType SOUND_CRAFT_FOR_PHONE;
        private final String value;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            SystemRoutineType systemRoutineType = new SystemRoutineType("SOUND_CRAFT_FOR_PHONE", 0, "system_sound_craft_for_phone");
            SOUND_CRAFT_FOR_PHONE = systemRoutineType;
            SystemRoutineType systemRoutineType2 = new SystemRoutineType("SOUND_CRAFT_FOR_BUDS", 1, "system_sound_craft_for_buds");
            SOUND_CRAFT_FOR_BUDS = systemRoutineType2;
            SystemRoutineType systemRoutineType3 = new SystemRoutineType("GAME_CRAFT", 2, "system_game_craft");
            GAME_CRAFT = systemRoutineType3;
            SystemRoutineType[] systemRoutineTypeArr = {systemRoutineType, systemRoutineType2, systemRoutineType3};
            $VALUES = systemRoutineTypeArr;
            $ENTRIES = EnumEntriesKt.enumEntries(systemRoutineTypeArr);
            Companion = new Companion(null);
        }

        private SystemRoutineType(String str, int i, String str2) {
            this.value = str2;
        }

        public static SystemRoutineType valueOf(String str) {
            return (SystemRoutineType) Enum.valueOf(SystemRoutineType.class, str);
        }

        public static SystemRoutineType[] values() {
            return (SystemRoutineType[]) $VALUES.clone();
        }

        public final String getValue() {
            return this.value;
        }
    }
}
