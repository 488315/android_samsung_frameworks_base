package com.android.systemui.statusbar.window.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarWindowState {
    public static final /* synthetic */ StatusBarWindowState[] $VALUES;
    public static final StatusBarWindowState Hidden;
    public static final StatusBarWindowState Hiding;
    public static final StatusBarWindowState Showing;

    static {
        StatusBarWindowState statusBarWindowState = new StatusBarWindowState("Showing", 0);
        Showing = statusBarWindowState;
        StatusBarWindowState statusBarWindowState2 = new StatusBarWindowState("Hiding", 1);
        Hiding = statusBarWindowState2;
        StatusBarWindowState statusBarWindowState3 = new StatusBarWindowState("Hidden", 2);
        Hidden = statusBarWindowState3;
        StatusBarWindowState[] statusBarWindowStateArr = {statusBarWindowState, statusBarWindowState2, statusBarWindowState3};
        $VALUES = statusBarWindowStateArr;
        EnumEntriesKt.enumEntries(statusBarWindowStateArr);
    }

    private StatusBarWindowState(String str, int i) {
    }

    public static StatusBarWindowState valueOf(String str) {
        return (StatusBarWindowState) Enum.valueOf(StatusBarWindowState.class, str);
    }

    public static StatusBarWindowState[] values() {
        return (StatusBarWindowState[]) $VALUES.clone();
    }
}
