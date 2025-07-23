package com.android.systemui.statusbar.events.shared.model;

import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SystemEventAnimationState {
    public static final /* synthetic */ SystemEventAnimationState[] $VALUES;
    public static final SystemEventAnimationState AnimatingIn;
    public static final SystemEventAnimationState AnimatingOut;
    public static final SystemEventAnimationState AnimationQueued;
    public static final SystemEventAnimationState Idle;
    public static final SystemEventAnimationState RunningChipAnim;
    public static final SystemEventAnimationState ShowingPersistentDot;

    static {
        SystemEventAnimationState systemEventAnimationState = new SystemEventAnimationState("Idle", 0);
        Idle = systemEventAnimationState;
        SystemEventAnimationState systemEventAnimationState2 = new SystemEventAnimationState("AnimationQueued", 1);
        AnimationQueued = systemEventAnimationState2;
        SystemEventAnimationState systemEventAnimationState3 = new SystemEventAnimationState("AnimatingIn", 2);
        AnimatingIn = systemEventAnimationState3;
        SystemEventAnimationState systemEventAnimationState4 = new SystemEventAnimationState("RunningChipAnim", 3);
        RunningChipAnim = systemEventAnimationState4;
        SystemEventAnimationState systemEventAnimationState5 = new SystemEventAnimationState("AnimatingOut", 4);
        AnimatingOut = systemEventAnimationState5;
        SystemEventAnimationState systemEventAnimationState6 = new SystemEventAnimationState("ShowingPersistentDot", 5);
        ShowingPersistentDot = systemEventAnimationState6;
        SystemEventAnimationState[] systemEventAnimationStateArr = {systemEventAnimationState, systemEventAnimationState2, systemEventAnimationState3, systemEventAnimationState4, systemEventAnimationState5, systemEventAnimationState6};
        $VALUES = systemEventAnimationStateArr;
        EnumEntriesKt.enumEntries(systemEventAnimationStateArr);
    }

    private SystemEventAnimationState(String str, int i) {
    }

    public static SystemEventAnimationState valueOf(String str) {
        return (SystemEventAnimationState) Enum.valueOf(SystemEventAnimationState.class, str);
    }

    public static SystemEventAnimationState[] values() {
        return (SystemEventAnimationState[]) $VALUES.clone();
    }
}
