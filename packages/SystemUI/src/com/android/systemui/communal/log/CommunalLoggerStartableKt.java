package com.android.systemui.communal.log;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.systemui.communal.shared.model.CommunalScenes;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class CommunalLoggerStartableKt {
    public static final boolean access$isNotOnCommunal(ObservableTransitionState observableTransitionState) {
        return (observableTransitionState instanceof ObservableTransitionState.Idle) && !Intrinsics.areEqual(((ObservableTransitionState.Idle) observableTransitionState).currentScene, CommunalScenes.Communal);
    }

    public static final boolean access$isOnCommunal(ObservableTransitionState observableTransitionState) {
        return (observableTransitionState instanceof ObservableTransitionState.Idle) && Intrinsics.areEqual(((ObservableTransitionState.Idle) observableTransitionState).currentScene, CommunalScenes.Communal);
    }

    public static final boolean access$isSwipingFromCommunal(ObservableTransitionState observableTransitionState) {
        if (!(observableTransitionState instanceof ObservableTransitionState.Transition)) {
            return false;
        }
        ObservableTransitionState.Transition transition = (ObservableTransitionState.Transition) observableTransitionState;
        return Intrinsics.areEqual(transition.fromContent, CommunalScenes.Communal) && transition.isInitiatedByUserInput;
    }

    public static final boolean access$isSwipingToCommunal(ObservableTransitionState observableTransitionState) {
        if (!(observableTransitionState instanceof ObservableTransitionState.Transition)) {
            return false;
        }
        ObservableTransitionState.Transition transition = (ObservableTransitionState.Transition) observableTransitionState;
        return Intrinsics.areEqual(transition.toContent, CommunalScenes.Communal) && transition.isInitiatedByUserInput;
    }
}
