package com.android.systemui.keyguard.domain.interactor;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes2.dex */
public final class ShowWhileAwakeReason {
    public static final /* synthetic */ ShowWhileAwakeReason[] $VALUES;
    public static final ShowWhileAwakeReason FOLDED_WITH_SWIPE_UP_TO_CONTINUE = null;
    public static final ShowWhileAwakeReason KEYGUARD_REENABLED;
    public static final ShowWhileAwakeReason KEYGUARD_TIMEOUT_WHILE_SCREEN_ON;
    public static final ShowWhileAwakeReason LOCKDOWN;
    public static final ShowWhileAwakeReason SWITCHED_TO_SECURE_USER_WHILE_GOING_AWAY = null;
    private final String logReason;

    static {
        ShowWhileAwakeReason showWhileAwakeReason = new ShowWhileAwakeReason("FOLDED_WITH_SWIPE_UP_TO_CONTINUE", 0, "Folded with continue using apps on fold set to 'swipe up to continue'.");
        ShowWhileAwakeReason showWhileAwakeReason2 = new ShowWhileAwakeReason("LOCKDOWN", 1, "Lockdown initiated.");
        LOCKDOWN = showWhileAwakeReason2;
        ShowWhileAwakeReason showWhileAwakeReason3 = new ShowWhileAwakeReason("KEYGUARD_REENABLED", 2, "Keyguard was re-enabled. We weren't unlocked when it was disabled, so we're returning to the lockscreen.");
        KEYGUARD_REENABLED = showWhileAwakeReason3;
        ShowWhileAwakeReason showWhileAwakeReason4 = new ShowWhileAwakeReason("KEYGUARD_TIMEOUT_WHILE_SCREEN_ON", 3, "Timed out while the screen was kept on, or WM#lockNow() was called.");
        KEYGUARD_TIMEOUT_WHILE_SCREEN_ON = showWhileAwakeReason4;
        ShowWhileAwakeReason[] showWhileAwakeReasonArr = {showWhileAwakeReason, showWhileAwakeReason2, showWhileAwakeReason3, showWhileAwakeReason4, new ShowWhileAwakeReason("SWITCHED_TO_SECURE_USER_WHILE_GOING_AWAY", 4, "User switch to secure user occurred during keyguardGoingAway sequence, so we're locking.")};
        $VALUES = showWhileAwakeReasonArr;
        EnumEntriesKt.enumEntries(showWhileAwakeReasonArr);
    }

    private ShowWhileAwakeReason(String str, int i, String str2) {
        this.logReason = str2;
    }

    public static ShowWhileAwakeReason valueOf(String str) {
        return (ShowWhileAwakeReason) Enum.valueOf(ShowWhileAwakeReason.class, str);
    }

    public static ShowWhileAwakeReason[] values() {
        return (ShowWhileAwakeReason[]) $VALUES.clone();
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.logReason;
    }
}
