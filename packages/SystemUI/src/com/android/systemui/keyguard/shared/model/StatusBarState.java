package com.android.systemui.keyguard.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StatusBarState {
    public static final /* synthetic */ StatusBarState[] $VALUES;
    public static final StatusBarState KEYGUARD;
    public static final StatusBarState SHADE;
    public static final StatusBarState SHADE_LOCKED;

    static {
        StatusBarState statusBarState = new StatusBarState("SHADE", 0);
        SHADE = statusBarState;
        StatusBarState statusBarState2 = new StatusBarState("KEYGUARD", 1);
        KEYGUARD = statusBarState2;
        StatusBarState statusBarState3 = new StatusBarState("SHADE_LOCKED", 2);
        SHADE_LOCKED = statusBarState3;
        StatusBarState[] statusBarStateArr = {statusBarState, statusBarState2, statusBarState3};
        $VALUES = statusBarStateArr;
        EnumEntriesKt.enumEntries(statusBarStateArr);
    }

    private StatusBarState(String str, int i) {
    }

    public static StatusBarState valueOf(String str) {
        return (StatusBarState) Enum.valueOf(StatusBarState.class, str);
    }

    public static StatusBarState[] values() {
        return (StatusBarState[]) $VALUES.clone();
    }
}
