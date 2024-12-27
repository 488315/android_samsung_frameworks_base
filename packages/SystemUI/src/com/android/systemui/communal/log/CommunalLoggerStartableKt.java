package com.android.systemui.communal.log;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.systemui.communal.shared.model.CommunalScenes;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
