package com.android.systemui.communal.log;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.systemui.communal.shared.model.CommunalScenes;
import kotlin.jvm.internal.Intrinsics;

public abstract class CommunalLoggerStartableKt {
    public static final boolean access$isNotOnCommunal(ObservableTransitionState observableTransitionState) {
        return (observableTransitionState instanceof ObservableTransitionState.Idle) && !Intrinsics.areEqual(((ObservableTransitionState.Idle) observableTransitionState).currentScene, CommunalScenes.Communal);
    }

    public static final boolean access$isOnCommunal(ObservableTransitionState observableTransitionState) {
        return (observableTransitionState instanceof ObservableTransitionState.Idle) && Intrinsics.areEqual(((ObservableTransitionState.Idle) observableTransitionState).currentScene, CommunalScenes.Communal);
    }

    public static final boolean access$isSwipingFromCommunal(ObservableTransitionState observableTransitionState) {
        if (observableTransitionState instanceof ObservableTransitionState.Transition) {
            ObservableTransitionState.Transition transition = (ObservableTransitionState.Transition) observableTransitionState;
            if (Intrinsics.areEqual(transition.fromScene, CommunalScenes.Communal) && transition.isInitiatedByUserInput) {
                return true;
            }
        }
        return false;
    }

    public static final boolean access$isSwipingToCommunal(ObservableTransitionState observableTransitionState) {
        if (observableTransitionState instanceof ObservableTransitionState.Transition) {
            ObservableTransitionState.Transition transition = (ObservableTransitionState.Transition) observableTransitionState;
            if (Intrinsics.areEqual(transition.toScene, CommunalScenes.Communal) && transition.isInitiatedByUserInput) {
                return true;
            }
        }
        return false;
    }
}
