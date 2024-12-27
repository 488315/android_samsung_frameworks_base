package com.android.systemui.controls.util;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ControlsBackUpRestore$BNRAction {
    public static final /* synthetic */ ControlsBackUpRestore$BNRAction[] $VALUES;
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
