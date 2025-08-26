package com.android.compose.animation.scene;

import androidx.compose.animation.core.DecayAnimationSpec;
import androidx.compose.foundation.gestures.Orientation;
import com.android.compose.animation.scene.UserActionResult;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.mechanics.MutableDragOffsetGestureContext;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$FloatRef;

/* loaded from: classes.dex */
public abstract class SwipeAnimationKt {

    /* renamed from: com.android.compose.animation.scene.SwipeAnimationKt$createSwipeAnimation$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function1 {
        final /* synthetic */ boolean $isUpOrLeft;
        final /* synthetic */ Ref$FloatRef $lastDistance;
        final /* synthetic */ SceneTransitionLayoutImpl $layoutImpl;
        final /* synthetic */ Orientation $orientation;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Ref$FloatRef ref$FloatRef, boolean z, SceneTransitionLayoutImpl sceneTransitionLayoutImpl, Orientation orientation) {
            super(1, Intrinsics.Kotlin.class, "distance", "createSwipeAnimation$distance(Lkotlin/jvm/internal/Ref$FloatRef;ZLcom/android/compose/animation/scene/SceneTransitionLayoutImpl;Landroidx/compose/foundation/gestures/Orientation;Lcom/android/compose/animation/scene/SwipeAnimation;)F", 0);
            this.$lastDistance = ref$FloatRef;
            this.$isUpOrLeft = z;
            this.$layoutImpl = sceneTransitionLayoutImpl;
            this.$orientation = orientation;
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
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
                float fAbsoluteDistance = userActionDistance.absoluteDistance(userActionDistanceScopeImpl, swipeAnimation.fromContent, swipeAnimation.toContent, orientation);
                if (fAbsoluteDistance <= 0.0f) {
                    f = 0.0f;
                } else {
                    MutableDragOffsetGestureContext mutableDragOffsetGestureContext = swipeAnimation.gestureContext;
                    if (z) {
                        float dragOffset = mutableDragOffsetGestureContext.getDragOffset();
                        fAbsoluteDistance = -fAbsoluteDistance;
                        if (dragOffset < fAbsoluteDistance) {
                            dragOffset = fAbsoluteDistance;
                        }
                        swipeAnimation.setDragOffset(dragOffset <= 0.0f ? dragOffset : 0.0f);
                    } else {
                        float dragOffset2 = mutableDragOffsetGestureContext.getDragOffset();
                        float f2 = dragOffset2 >= 0.0f ? dragOffset2 : 0.0f;
                        if (f2 > fAbsoluteDistance) {
                            f2 = fAbsoluteDistance;
                        }
                        swipeAnimation.setDragOffset(f2);
                    }
                    f = fAbsoluteDistance;
                    ref$FloatRef.element = f;
                }
            }
            return Float.valueOf(f);
        }
    }

    public static final SwipeAnimation createSwipeAnimation(SceneTransitionLayoutImpl sceneTransitionLayoutImpl, UserActionResult userActionResult, boolean z, Orientation orientation, MutableDragOffsetGestureContext mutableDragOffsetGestureContext, DecayAnimationSpec decayAnimationSpec, float f) {
        Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
        ref$FloatRef.element = f;
        MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = sceneTransitionLayoutImpl.state;
        AnonymousClass3 anonymousClass3 = new AnonymousClass3(ref$FloatRef, z, sceneTransitionLayoutImpl, orientation);
        if (userActionResult instanceof UserActionResult.ChangeScene) {
            UserActionResult.ChangeScene changeScene = (UserActionResult.ChangeScene) userActionResult;
            return new ChangeSceneSwipeTransition(createSwipeAnimation$swipeAnimation(mutableSceneTransitionLayoutStateImpl, orientation, z, userActionResult, anonymousClass3, mutableDragOffsetGestureContext, decayAnimationSpec, mutableSceneTransitionLayoutStateImpl.getCurrentScene(), changeScene.toScene), changeScene.transitionKey, null).swipeAnimation;
        }
        if (userActionResult instanceof UserActionResult.ShowOverlay) {
            SceneKey currentScene = mutableSceneTransitionLayoutStateImpl.getCurrentScene();
            UserActionResult.ShowOverlay showOverlay = (UserActionResult.ShowOverlay) userActionResult;
            OverlayKey overlayKey = showOverlay.overlay;
            return new ShowOrHideOverlaySwipeTransition(createSwipeAnimation$swipeAnimation(mutableSceneTransitionLayoutStateImpl, orientation, z, userActionResult, anonymousClass3, mutableDragOffsetGestureContext, decayAnimationSpec, currentScene, overlayKey), overlayKey, currentScene, showOverlay.transitionKey, null).swipeAnimation;
        }
        if (userActionResult instanceof UserActionResult.HideOverlay) {
            SceneKey currentScene2 = mutableSceneTransitionLayoutStateImpl.getCurrentScene();
            UserActionResult.HideOverlay hideOverlay = (UserActionResult.HideOverlay) userActionResult;
            OverlayKey overlayKey2 = hideOverlay.overlay;
            return new ShowOrHideOverlaySwipeTransition(createSwipeAnimation$swipeAnimation(mutableSceneTransitionLayoutStateImpl, orientation, z, userActionResult, anonymousClass3, mutableDragOffsetGestureContext, decayAnimationSpec, overlayKey2, currentScene2), overlayKey2, currentScene2, hideOverlay.transitionKey, null).swipeAnimation;
        }
        if (!(userActionResult instanceof UserActionResult.ReplaceByOverlay)) {
            throw new NoWhenBranchMatchedException();
        }
        ContentKey key = sceneTransitionLayoutImpl.contentForUserActions$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout().getKey();
        if (key instanceof SceneKey) {
            throw new IllegalStateException("ReplaceByOverlay can only be called when an overlay is shown");
        }
        if (!(key instanceof OverlayKey)) {
            throw new NoWhenBranchMatchedException();
        }
        OverlayKey overlayKey3 = (OverlayKey) key;
        UserActionResult.ReplaceByOverlay replaceByOverlay = (UserActionResult.ReplaceByOverlay) userActionResult;
        return new ReplaceOverlaySwipeTransition(createSwipeAnimation$swipeAnimation(mutableSceneTransitionLayoutStateImpl, orientation, z, userActionResult, anonymousClass3, mutableDragOffsetGestureContext, decayAnimationSpec, overlayKey3, replaceByOverlay.overlay), replaceByOverlay.transitionKey, null).swipeAnimation;
    }

    public static final SwipeAnimation createSwipeAnimation$swipeAnimation(MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl, Orientation orientation, boolean z, UserActionResult userActionResult, Function1 function1, MutableDragOffsetGestureContext mutableDragOffsetGestureContext, DecayAnimationSpec decayAnimationSpec, ContentKey contentKey, ContentKey contentKey2) {
        return new SwipeAnimation(mutableSceneTransitionLayoutStateImpl, contentKey, contentKey2, orientation, z, userActionResult.getRequiresFullDistanceSwipe(), function1, null, mutableDragOffsetGestureContext, decayAnimationSpec, 128, null);
    }
}
