package com.android.compose.animation.scene;

import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerType;
import androidx.compose.ui.unit.IntOffsetKt;
import androidx.compose.ui.unit.Velocity;
import com.android.compose.animation.scene.SwipeSource;
import com.android.compose.animation.scene.UserActionResult;
import com.android.compose.animation.scene.content.Content;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.compose.animation.scene.effect.GestureEffect;
import com.android.compose.gesture.NestedDraggable;
import com.android.compose.ui.util.SpaceVectorConverter;
import com.android.compose.ui.util.SpaceVectorConverterKt;
import com.android.mechanics.DistanceGestureContext;
import com.android.mechanics.spec.InputDirection;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public final class DraggableHandler implements NestedDraggable {
    public DragControllerImpl dragController;
    public final Function1 gestureEffectProvider;
    public final SceneTransitionLayoutImpl layoutImpl;
    public final Orientation orientation;
    public final DelegatingOverscrollEffect overscrollEffect = new DelegatingOverscrollEffect();

    public final class DelegatingOverscrollEffect implements OverscrollEffect, SpaceVectorConverter {
        public final /* synthetic */ SpaceVectorConverter $$delegate_0;
        public ContentKey currentContent;
        public GestureEffect currentDelegate;

        public DelegatingOverscrollEffect() {
            this.$$delegate_0 = SpaceVectorConverterKt.SpaceVectorConverter(DraggableHandler.this.orientation);
        }

        @Override // androidx.compose.foundation.OverscrollEffect
        /* renamed from: applyToFling-BMRW4eQ */
        public final Object mo19applyToFlingBMRW4eQ(long j, Function2 function2, ContinuationImpl continuationImpl) {
            DragControllerImpl dragControllerImpl;
            float fMo916toFloatTH1AsA0$1 = this.$$delegate_0.mo916toFloatTH1AsA0$1(j);
            if (fMo916toFloatTH1AsA0$1 != 0.0f && (dragControllerImpl = DraggableHandler.this.dragController) != null && dragControllerImpl.isDrivingTransition()) {
                ensureDelegateIsNotNull(fMo916toFloatTH1AsA0$1);
            }
            GestureEffect gestureEffect = this.currentDelegate;
            setCurrentDelegate(null);
            this.currentContent = null;
            if (gestureEffect == null || !((Modifier.Node) gestureEffect.delegate.getNode()).node.isAttached) {
                Object objInvoke = function2.invoke(Velocity.m878boximpl(j), continuationImpl);
                return objInvoke == CoroutineSingletons.COROUTINE_SUSPENDED ? objInvoke : Unit.INSTANCE;
            }
            Object objMo19applyToFlingBMRW4eQ = gestureEffect.mo19applyToFlingBMRW4eQ(j, function2, continuationImpl);
            return objMo19applyToFlingBMRW4eQ == CoroutineSingletons.COROUTINE_SUSPENDED ? objMo19applyToFlingBMRW4eQ : Unit.INSTANCE;
        }

        @Override // androidx.compose.foundation.OverscrollEffect
        /* renamed from: applyToScroll-Rhakbz0 */
        public final long mo20applyToScrollRhakbz0(int i, long j, Function1 function1) {
            float fMo917toFloatk4lQ0M$1 = this.$$delegate_0.mo917toFloatk4lQ0M$1(j);
            if (fMo917toFloatk4lQ0M$1 == 0.0f) {
                return ((Offset) function1.mo781invoke(Offset.m395boximpl(j))).packedValue;
            }
            ensureDelegateIsNotNull(fMo917toFloatk4lQ0M$1);
            GestureEffect gestureEffect = this.currentDelegate;
            if (gestureEffect != null) {
                return ((Modifier.Node) gestureEffect.delegate.getNode()).node.isAttached ? gestureEffect.mo20applyToScrollRhakbz0(i, j, function1) : ((Offset) function1.mo781invoke(Offset.m395boximpl(j))).packedValue;
            }
            throw new IllegalStateException("Required value was null.");
        }

        public final void ensureDelegateIsNotNull(float f) {
            ContentKey key;
            if (f == 0.0f) {
                throw new IllegalArgumentException("Failed requirement.");
            }
            if (isInProgress()) {
                return;
            }
            DraggableHandler draggableHandler = DraggableHandler.this;
            DragControllerImpl dragControllerImpl = draggableHandler.dragController;
            if (dragControllerImpl == null || !dragControllerImpl.isDrivingTransition()) {
                key = draggableHandler.layoutImpl.contentForUserActions$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout().getKey();
            } else {
                DragControllerImpl dragControllerImpl2 = draggableHandler.dragController;
                if (dragControllerImpl2 == null) {
                    throw new IllegalStateException("Required value was null.");
                }
                SwipeAnimation swipeAnimation = dragControllerImpl2.swipeAnimation;
                swipeAnimation.getClass();
                if (f == 0.0f) {
                    throw new IllegalArgumentException(("Cannot find a content in this direction: " + f).toString());
                }
                boolean z = swipeAnimation.isUpOrLeft;
                key = ((!z || f >= 0.0f) && (z || f <= 0.0f)) ? swipeAnimation.fromContent : swipeAnimation.toContent;
            }
            if (Intrinsics.areEqual(key, this.currentContent)) {
                return;
            }
            this.currentContent = key;
            setCurrentDelegate((GestureEffect) draggableHandler.gestureEffectProvider.mo781invoke(key));
        }

        @Override // androidx.compose.foundation.OverscrollEffect
        public final boolean isInProgress() {
            GestureEffect gestureEffect = this.currentDelegate;
            if (gestureEffect != null) {
                return gestureEffect.delegate.isInProgress();
            }
            return false;
        }

        public final void setCurrentDelegate(GestureEffect gestureEffect) {
            GestureEffect gestureEffect2 = this.currentDelegate;
            if (gestureEffect2 != null && gestureEffect2.delegate.isInProgress()) {
                BuildersKt.launch$default(DraggableHandler.this.layoutImpl.animationScope, null, null, new DraggableHandler$DelegatingOverscrollEffect$currentDelegate$1$1(gestureEffect2, null), 3);
            }
            this.currentDelegate = gestureEffect;
        }

        @Override // com.android.compose.ui.util.SpaceVectorConverter
        /* renamed from: toFloat-TH1AsA0$1 */
        public final float mo916toFloatTH1AsA0$1(long j) {
            return this.$$delegate_0.mo916toFloatTH1AsA0$1(j);
        }

        @Override // com.android.compose.ui.util.SpaceVectorConverter
        /* renamed from: toFloat-k-4lQ0M$1 */
        public final float mo917toFloatk4lQ0M$1(long j) {
            return this.$$delegate_0.mo917toFloatk4lQ0M$1(j);
        }

        @Override // com.android.compose.ui.util.SpaceVectorConverter
        /* renamed from: toIntOffset-Bjo55l4 */
        public final long mo918toIntOffsetBjo55l4(int i) {
            return this.$$delegate_0.mo918toIntOffsetBjo55l4(i);
        }

        @Override // com.android.compose.ui.util.SpaceVectorConverter
        /* renamed from: toOffset-tuRUvjQ$1 */
        public final long mo919toOffsettuRUvjQ$1(float f) {
            return this.$$delegate_0.mo919toOffsettuRUvjQ$1(f);
        }
    }

    public DraggableHandler(SceneTransitionLayoutImpl sceneTransitionLayoutImpl, Orientation orientation, Function1 function1) {
        this.layoutImpl = sceneTransitionLayoutImpl;
        this.orientation = orientation;
        this.gestureEffectProvider = function1;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x005b  */
    /* renamed from: onDragStarted-w4f02Oo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final NestedDraggable.Controller m920onDragStartedw4f02Oo(long j, float f, int i, PointerType pointerType) {
        UserActionResult userActionResult;
        boolean z;
        if (f == 0.0f) {
            throw new IllegalStateException("Check failed.");
        }
        SceneTransitionLayoutImpl sceneTransitionLayoutImpl = this.layoutImpl;
        SwipeSource.Resolved resolvedMo926sourceNDhlJko = sceneTransitionLayoutImpl.swipeSourceDetector.mo926sourceNDhlJko(sceneTransitionLayoutImpl.lastSize, IntOffsetKt.m856roundk4lQ0M(j), sceneTransitionLayoutImpl.density, this.orientation);
        Orientation orientation = this.orientation;
        Swipes swipes = new Swipes(DraggableHandlerKt.m921access$resolveSwipeDrK6AWw(orientation, true, resolvedMo926sourceNDhlJko, i, pointerType), DraggableHandlerKt.m921access$resolveSwipeDrK6AWw(orientation, false, resolvedMo926sourceNDhlJko, i, pointerType));
        Content contentContentForUserActions$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = sceneTransitionLayoutImpl.contentForUserActions$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
        Pair pair = new Pair(Swipes.findActionResultBestMatch(contentContentForUserActions$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout, swipes.upOrLeft), Swipes.findActionResultBestMatch(contentContentForUserActions$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout, swipes.downOrRight));
        UserActionResult userActionResult2 = (UserActionResult) pair.component1();
        UserActionResult userActionResult3 = (UserActionResult) pair.component2();
        swipes.upOrLeftResult = userActionResult2;
        swipes.downOrRightResult = userActionResult3;
        if (f < 0.0f) {
            userActionResult = userActionResult2 == null ? userActionResult3 : userActionResult2;
        } else if (f < 0.0f) {
            userActionResult = null;
        } else if (userActionResult3 == null) {
        }
        if (userActionResult == null) {
            return NoOpDragController.INSTANCE;
        }
        if (userActionResult instanceof UserActionResult.ShowOverlay) {
            sceneTransitionLayoutImpl.hideOverlays$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(((UserActionResult.ShowOverlay) userActionResult).hideCurrentOverlays);
        }
        UserActionResult userActionResult4 = swipes.upOrLeftResult;
        UserActionResult userActionResult5 = swipes.downOrRightResult;
        if (userActionResult.equals(userActionResult4)) {
            z = true;
        } else {
            if (!userActionResult.equals(userActionResult5)) {
                throw new IllegalStateException(("Unknown result " + userActionResult + " (" + userActionResult4 + " " + userActionResult5 + ")").toString());
            }
            z = false;
        }
        SwipeAnimation swipeAnimationCreateSwipeAnimation = SwipeAnimationKt.createSwipeAnimation(sceneTransitionLayoutImpl, userActionResult, z, this.orientation, new DistanceGestureContext(0.0f, z ? InputDirection.Min : InputDirection.Max, sceneTransitionLayoutImpl.directionChangeSlop), sceneTransitionLayoutImpl.decayAnimationSpec, 0.0f);
        DragControllerImpl dragControllerImpl = new DragControllerImpl(this, swipes, swipeAnimationCreateSwipeAnimation);
        CoroutineScope coroutineScope = dragControllerImpl.draggableHandler.layoutImpl.animationScope;
        TransitionState.Transition transition = swipeAnimationCreateSwipeAnimation.contentTransition;
        dragControllerImpl.layoutState.startTransitionImmediately(coroutineScope, transition != null ? transition : null, true);
        dragControllerImpl.swipeAnimation = swipeAnimationCreateSwipeAnimation;
        this.dragController = dragControllerImpl;
        return dragControllerImpl;
    }
}
