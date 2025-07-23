package com.android.compose.animation.scene;

import androidx.compose.animation.core.DecayAnimationSpec;
import androidx.compose.foundation.gestures.Orientation;
import com.android.compose.animation.scene.UserActionResult;
import com.android.mechanics.MutableDragOffsetGestureContext;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$FloatRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SwipeAnimationKt {
    public static final SwipeAnimation createSwipeAnimation(SceneTransitionLayoutImpl sceneTransitionLayoutImpl, UserActionResult userActionResult, boolean z, Orientation orientation, MutableDragOffsetGestureContext mutableDragOffsetGestureContext, DecayAnimationSpec decayAnimationSpec, float f) {
        Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
        ref$FloatRef.element = f;
        MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = sceneTransitionLayoutImpl.state;
        SwipeAnimationKt$createSwipeAnimation$3 swipeAnimationKt$createSwipeAnimation$3 = new SwipeAnimationKt$createSwipeAnimation$3(ref$FloatRef, z, sceneTransitionLayoutImpl, orientation);
        if (userActionResult instanceof UserActionResult.ChangeScene) {
            UserActionResult.ChangeScene changeScene = (UserActionResult.ChangeScene) userActionResult;
            return new ChangeSceneSwipeTransition(createSwipeAnimation$swipeAnimation(mutableSceneTransitionLayoutStateImpl, orientation, z, userActionResult, swipeAnimationKt$createSwipeAnimation$3, mutableDragOffsetGestureContext, decayAnimationSpec, mutableSceneTransitionLayoutStateImpl.getCurrentScene(), changeScene.toScene), changeScene.transitionKey, null).swipeAnimation;
        }
        if (userActionResult instanceof UserActionResult.ShowOverlay) {
            SceneKey currentScene = mutableSceneTransitionLayoutStateImpl.getCurrentScene();
            UserActionResult.ShowOverlay showOverlay = (UserActionResult.ShowOverlay) userActionResult;
            OverlayKey overlayKey = showOverlay.overlay;
            return new ShowOrHideOverlaySwipeTransition(createSwipeAnimation$swipeAnimation(mutableSceneTransitionLayoutStateImpl, orientation, z, userActionResult, swipeAnimationKt$createSwipeAnimation$3, mutableDragOffsetGestureContext, decayAnimationSpec, currentScene, overlayKey), overlayKey, currentScene, showOverlay.transitionKey, null).swipeAnimation;
        }
        if (userActionResult instanceof UserActionResult.HideOverlay) {
            SceneKey currentScene2 = mutableSceneTransitionLayoutStateImpl.getCurrentScene();
            UserActionResult.HideOverlay hideOverlay = (UserActionResult.HideOverlay) userActionResult;
            OverlayKey overlayKey2 = hideOverlay.overlay;
            return new ShowOrHideOverlaySwipeTransition(createSwipeAnimation$swipeAnimation(mutableSceneTransitionLayoutStateImpl, orientation, z, userActionResult, swipeAnimationKt$createSwipeAnimation$3, mutableDragOffsetGestureContext, decayAnimationSpec, overlayKey2, currentScene2), overlayKey2, currentScene2, hideOverlay.transitionKey, null).swipeAnimation;
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
        return new ReplaceOverlaySwipeTransition(createSwipeAnimation$swipeAnimation(mutableSceneTransitionLayoutStateImpl, orientation, z, userActionResult, swipeAnimationKt$createSwipeAnimation$3, mutableDragOffsetGestureContext, decayAnimationSpec, overlayKey3, replaceByOverlay.overlay), replaceByOverlay.transitionKey, null).swipeAnimation;
    }

    public static final SwipeAnimation createSwipeAnimation$swipeAnimation(MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl, Orientation orientation, boolean z, UserActionResult userActionResult, Function1 function1, MutableDragOffsetGestureContext mutableDragOffsetGestureContext, DecayAnimationSpec decayAnimationSpec, ContentKey contentKey, ContentKey contentKey2) {
        return new SwipeAnimation(mutableSceneTransitionLayoutStateImpl, contentKey, contentKey2, orientation, z, userActionResult.getRequiresFullDistanceSwipe(), function1, null, mutableDragOffsetGestureContext, decayAnimationSpec, 128, null);
    }
}
