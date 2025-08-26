package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;

/* loaded from: classes2.dex */
public abstract /* synthetic */ class AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0 {
    public static KeyguardTransitionAnimationFlow.FlowBuilder m(KeyguardState keyguardState, KeyguardState keyguardState2, KeyguardTransitionAnimationFlow.FlowBuilder flowBuilder) {
        return flowBuilder.setupWithoutSceneContainer(new Edge.StateToState(keyguardState, keyguardState2));
    }
}
