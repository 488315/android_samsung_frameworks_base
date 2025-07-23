package com.android.systemui.controls.util;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ControlsBackUpRestore$BNRAction {
    public static final /* synthetic */ ControlsBackUpRestore$BNRAction[] $VALUES;
    public static final Companion Companion;
    private final int value;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        ControlsBackUpRestore$BNRAction[] controlsBackUpRestore$BNRActionArr = {new ControlsBackUpRestore$BNRAction("BACKUP", 0, 0), new ControlsBackUpRestore$BNRAction("BACKUP_CANCEL", 1, 2)};
        $VALUES = controlsBackUpRestore$BNRActionArr;
        EnumEntriesKt.enumEntries(controlsBackUpRestore$BNRActionArr);
        Companion = new Companion(null);
    }

    private ControlsBackUpRestore$BNRAction(String str, int i, int i2) {
        this.value = i2;
    }

    public static ControlsBackUpRestore$BNRAction valueOf(String str) {
        return (ControlsBackUpRestore$BNRAction) Enum.valueOf(ControlsBackUpRestore$BNRAction.class, str);
    }

    public static ControlsBackUpRestore$BNRAction[] values() {
        return (ControlsBackUpRestore$BNRAction[]) $VALUES.clone();
    }

    public final int getValue() {
        return this.value;
    }
}
