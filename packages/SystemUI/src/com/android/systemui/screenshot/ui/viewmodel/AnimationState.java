package com.android.systemui.screenshot.ui.viewmodel;

import kotlin.enums.EnumEntriesKt;

public final class AnimationState {
    public static final /* synthetic */ AnimationState[] $VALUES;
    public static final AnimationState ENTRANCE_COMPLETE;
    public static final AnimationState ENTRANCE_REVEAL;
    public static final AnimationState NOT_STARTED;

    static {
        AnimationState animationState = new AnimationState("NOT_STARTED", 0);
        NOT_STARTED = animationState;
        AnimationState animationState2 = new AnimationState("ENTRANCE_STARTED", 1);
        AnimationState animationState3 = new AnimationState("ENTRANCE_REVEAL", 2);
        ENTRANCE_REVEAL = animationState3;
        AnimationState animationState4 = new AnimationState("ENTRANCE_COMPLETE", 3);
        ENTRANCE_COMPLETE = animationState4;
        AnimationState[] animationStateArr = {animationState, animationState2, animationState3, animationState4};
        $VALUES = animationStateArr;
        EnumEntriesKt.enumEntries(animationStateArr);
    }

    private AnimationState(String str, int i) {
    }

    public static AnimationState valueOf(String str) {
        return (AnimationState) Enum.valueOf(AnimationState.class, str);
    }

    public static AnimationState[] values() {
        return (AnimationState[]) $VALUES.clone();
    }
}
