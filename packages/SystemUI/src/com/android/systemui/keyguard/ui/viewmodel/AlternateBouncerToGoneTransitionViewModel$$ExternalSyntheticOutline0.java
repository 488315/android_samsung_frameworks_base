package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract /* synthetic */ class AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0 {
    public static KeyguardTransitionAnimationFlow.FlowBuilder m(KeyguardState keyguardState, KeyguardState keyguardState2, KeyguardTransitionAnimationFlow.FlowBuilder flowBuilder) {
        return flowBuilder.setupWithoutSceneContainer(new Edge.StateToState(keyguardState, keyguardState2));
    }
}
