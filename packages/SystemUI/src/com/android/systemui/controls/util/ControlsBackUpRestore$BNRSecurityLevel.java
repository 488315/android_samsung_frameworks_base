package com.android.systemui.controls.util;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ControlsBackUpRestore$BNRSecurityLevel {
    public static final /* synthetic */ ControlsBackUpRestore$BNRSecurityLevel[] $VALUES;
    public static final Companion Companion;
    private final int value;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
