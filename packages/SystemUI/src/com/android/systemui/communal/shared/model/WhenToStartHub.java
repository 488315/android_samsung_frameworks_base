package com.android.systemui.communal.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class WhenToStartHub {
    public static final /* synthetic */ WhenToStartHub[] $VALUES;
    public static final WhenToStartHub NEVER;
    public static final WhenToStartHub WHILE_CHARGING;
    public static final WhenToStartHub WHILE_CHARGING_AND_POSTURED;
    public static final WhenToStartHub WHILE_DOCKED;

    static {
        WhenToStartHub whenToStartHub = new WhenToStartHub("NEVER", 0);
        NEVER = whenToStartHub;
        WhenToStartHub whenToStartHub2 = new WhenToStartHub("WHILE_CHARGING", 1);
        WHILE_CHARGING = whenToStartHub2;
        WhenToStartHub whenToStartHub3 = new WhenToStartHub("WHILE_CHARGING_AND_POSTURED", 2);
        WHILE_CHARGING_AND_POSTURED = whenToStartHub3;
        WhenToStartHub whenToStartHub4 = new WhenToStartHub("WHILE_DOCKED", 3);
        WHILE_DOCKED = whenToStartHub4;
        WhenToStartHub[] whenToStartHubArr = {whenToStartHub, whenToStartHub2, whenToStartHub3, whenToStartHub4};
        $VALUES = whenToStartHubArr;
        EnumEntriesKt.enumEntries(whenToStartHubArr);
    }

    private WhenToStartHub(String str, int i) {
    }

    public static WhenToStartHub valueOf(String str) {
        return (WhenToStartHub) Enum.valueOf(WhenToStartHub.class, str);
    }

    public static WhenToStartHub[] values() {
        return (WhenToStartHub[]) $VALUES.clone();
    }
}
