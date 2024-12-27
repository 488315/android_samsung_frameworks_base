package com.android.systemui.controls.util;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class ControlsBackUpRestore$BNRSecurityLevel {
    public static final /* synthetic */ ControlsBackUpRestore$BNRSecurityLevel[] $VALUES;
    public static final Companion Companion;
    private final int value;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        ControlsBackUpRestore$BNRSecurityLevel[] controlsBackUpRestore$BNRSecurityLevelArr = {new ControlsBackUpRestore$BNRSecurityLevel("LOW", 0, 0), new ControlsBackUpRestore$BNRSecurityLevel("HIGH", 1, 1)};
        $VALUES = controlsBackUpRestore$BNRSecurityLevelArr;
        EnumEntriesKt.enumEntries(controlsBackUpRestore$BNRSecurityLevelArr);
        Companion = new Companion(null);
    }

    private ControlsBackUpRestore$BNRSecurityLevel(String str, int i, int i2) {
        this.value = i2;
    }

    public static ControlsBackUpRestore$BNRSecurityLevel valueOf(String str) {
        return (ControlsBackUpRestore$BNRSecurityLevel) Enum.valueOf(ControlsBackUpRestore$BNRSecurityLevel.class, str);
    }

    public static ControlsBackUpRestore$BNRSecurityLevel[] values() {
        return (ControlsBackUpRestore$BNRSecurityLevel[]) $VALUES.clone();
    }

    public final int getValue() {
        return this.value;
    }
}
