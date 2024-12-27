package com.android.systemui.keyguard.shared.model;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
