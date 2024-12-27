package com.android.systemui.keyguard.shared.model;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class TransitionState {
    public static final /* synthetic */ TransitionState[] $VALUES;
    public static final TransitionState CANCELED;
    public static final TransitionState FINISHED;
    public static final TransitionState RUNNING;
    public static final TransitionState STARTED;

    static {
        TransitionState transitionState = new TransitionState("STARTED", 0) { // from class: com.android.systemui.keyguard.shared.model.TransitionState.STARTED
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.keyguard.shared.model.TransitionState
            public final boolean isTransitioning() {
                return true;
            }
        };
        STARTED = transitionState;
        TransitionState transitionState2 = new TransitionState("RUNNING", 1) { // from class: com.android.systemui.keyguard.shared.model.TransitionState.RUNNING
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.keyguard.shared.model.TransitionState
            public final boolean isTransitioning() {
                return true;
            }
        };
        RUNNING = transitionState2;
        TransitionState transitionState3 = new TransitionState("FINISHED", 2) { // from class: com.android.systemui.keyguard.shared.model.TransitionState.FINISHED
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.keyguard.shared.model.TransitionState
            public final boolean isTransitioning() {
                return false;
            }
        };
        FINISHED = transitionState3;
        TransitionState transitionState4 = new TransitionState("CANCELED", 3) { // from class: com.android.systemui.keyguard.shared.model.TransitionState.CANCELED
            {
                DefaultConstructorMarker defaultConstructorMarker = null;
            }

            @Override // com.android.systemui.keyguard.shared.model.TransitionState
            public final boolean isTransitioning() {
                return false;
            }
        };
        CANCELED = transitionState4;
        TransitionState[] transitionStateArr = {transitionState, transitionState2, transitionState3, transitionState4};
        $VALUES = transitionStateArr;
        EnumEntriesKt.enumEntries(transitionStateArr);
    }

    public /* synthetic */ TransitionState(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i);
    }

    public static TransitionState valueOf(String str) {
        return (TransitionState) Enum.valueOf(TransitionState.class, str);
    }

    public static TransitionState[] values() {
        return (TransitionState[]) $VALUES.clone();
    }

    public abstract boolean isTransitioning();

    private TransitionState(String str, int i) {
    }
}
