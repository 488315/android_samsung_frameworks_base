package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.scene.shared.model.Scenes;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

/* loaded from: classes2.dex */
public final class OccludedToGoneTransitionViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long DEFAULT_DURATION;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        Duration.Companion companion = Duration.Companion;
        DEFAULT_DURATION = DurationKt.toDuration(300, DurationUnit.MILLISECONDS);
    }

    public OccludedToGoneTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.OCCLUDED;
        SceneKey sceneKey = Scenes.Gone;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM2613setupVtjQ1oo = keyguardTransitionAnimationFlow.m2613setupVtjQ1oo(DEFAULT_DURATION, new Edge.StateToContent(keyguardState, sceneKey));
        KeyguardState keyguardState2 = KeyguardState.GONE;
        companion.getClass();
        this.transitionAnimation = flowBuilderM2613setupVtjQ1oo.setupWithoutSceneContainer(new Edge.StateToState(keyguardState, keyguardState2));
    }
}
