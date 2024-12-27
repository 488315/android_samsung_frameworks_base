package com.android.systemui.power.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public final class WakefulnessState {
    public static final /* synthetic */ WakefulnessState[] $VALUES;
    public static final WakefulnessState ASLEEP;
    public static final WakefulnessState AWAKE;
    public static final WakefulnessState STARTING_TO_SLEEP;
    public static final WakefulnessState STARTING_TO_WAKE;

    static {
        WakefulnessState wakefulnessState = new WakefulnessState("ASLEEP", 0);
        ASLEEP = wakefulnessState;
        WakefulnessState wakefulnessState2 = new WakefulnessState("STARTING_TO_WAKE", 1);
        STARTING_TO_WAKE = wakefulnessState2;
        WakefulnessState wakefulnessState3 = new WakefulnessState("AWAKE", 2);
        AWAKE = wakefulnessState3;
        WakefulnessState wakefulnessState4 = new WakefulnessState("STARTING_TO_SLEEP", 3);
        STARTING_TO_SLEEP = wakefulnessState4;
        WakefulnessState[] wakefulnessStateArr = {wakefulnessState, wakefulnessState2, wakefulnessState3, wakefulnessState4};
        $VALUES = wakefulnessStateArr;
        EnumEntriesKt.enumEntries(wakefulnessStateArr);
    }

    private WakefulnessState(String str, int i) {
    }

    public static WakefulnessState valueOf(String str) {
        return (WakefulnessState) Enum.valueOf(WakefulnessState.class, str);
    }

    public static WakefulnessState[] values() {
        return (WakefulnessState[]) $VALUES.clone();
    }
}
