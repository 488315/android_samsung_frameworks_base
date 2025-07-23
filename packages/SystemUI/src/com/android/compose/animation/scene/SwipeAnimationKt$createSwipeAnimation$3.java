package com.android.compose.animation.scene;

import androidx.compose.foundation.gestures.Orientation;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.mechanics.MutableDragOffsetGestureContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$FloatRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class SwipeAnimationKt$createSwipeAnimation$3 extends FunctionReferenceImpl implements Function1 {
    final /* synthetic */ boolean $isUpOrLeft;
    final /* synthetic */ Ref$FloatRef $lastDistance;
    final /* synthetic */ SceneTransitionLayoutImpl $layoutImpl;
    final /* synthetic */ Orientation $orientation;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SwipeAnimationKt$createSwipeAnimation$3(Ref$FloatRef ref$FloatRef, boolean z, SceneTransitionLayoutImpl sceneTransitionLayoutImpl, Orientation orientation) {
        super(1, Intrinsics.Kotlin.class, "distance", "createSwipeAnimation$distance(Lkotlin/jvm/internal/Ref$FloatRef;ZLcom/android/compose/animation/scene/SceneTransitionLayoutImpl;Landroidx/compose/foundation/gestures/Orientation;Lcom/android/compose/animation/scene/SwipeAnimation;)F", 0);
        this.$lastDistance = ref$FloatRef;
        this.$isUpOrLeft = z;
        this.$layoutImpl = sceneTransitionLayoutImpl;
        this.$orientation = orientation;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        SwipeAnimation swipeAnimation = (SwipeAnimation) obj;
        Ref$FloatRef ref$FloatRef = this.$lastDistance;
        boolean z = this.$isUpOrLeft;
        SceneTransitionLayoutImpl sceneTransitionLayoutImpl = this.$layoutImpl;
        Orientation orientation = this.$orientation;
        float f = ref$FloatRef.element;
        if (f == 0.0f) {
            TransitionState.Transition transition = swipeAnimation.contentTransition;
            if (transition == null) {
                transition = null;
            }
            UserActionDistance userActionDistance = transition.transformationSpec.distance;
            if (userActionDistance == null) {
                userActionDistance = DefaultSwipeDistance.INSTANCE;
            }
            UserActionDistanceScopeImpl userActionDistanceScopeImpl = sceneTransitionLayoutImpl._userActionDistanceScope;
            if (userActionDistanceScopeImpl == null) {
                userActionDistanceScopeImpl = new UserActionDistanceScopeImpl(sceneTransitionLayoutImpl);
                sceneTransitionLayoutImpl._userActionDistanceScope = userActionDistanceScopeImpl;
            }
            float absoluteDistance = userActionDistance.absoluteDistance(userActionDistanceScopeImpl, swipeAnimation.fromContent, swipeAnimation.toContent, orientation);
            if (absoluteDistance <= 0.0f) {
                f = 0.0f;
            } else {
                MutableDragOffsetGestureContext mutableDragOffsetGestureContext = swipeAnimation.gestureContext;
                if (z) {
                    float dragOffset = mutableDragOffsetGestureContext.getDragOffset();
                    absoluteDistance = -absoluteDistance;
                    if (dragOffset < absoluteDistance) {
                        dragOffset = absoluteDistance;
                    }
                    swipeAnimation.setDragOffset(dragOffset <= 0.0f ? dragOffset : 0.0f);
                } else {
                    float dragOffset2 = mutableDragOffsetGestureContext.getDragOffset();
                    float f2 = dragOffset2 >= 0.0f ? dragOffset2 : 0.0f;
                    if (f2 > absoluteDistance) {
                        f2 = absoluteDistance;
                    }
                    swipeAnimation.setDragOffset(f2);
                }
                f = absoluteDistance;
                ref$FloatRef.element = f;
            }
        }
        return Float.valueOf(f);
    }
}
