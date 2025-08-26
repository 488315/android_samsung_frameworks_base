package com.android.compose.animation.scene;

import androidx.compose.ui.unit.Dp;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.compose.gesture.NestedDraggable;
import com.android.compose.ui.util.SpaceVectorConverter;
import com.android.compose.ui.util.SpaceVectorConverterKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class DragControllerImpl implements NestedDraggable.Controller, SpaceVectorConverter {
    public final /* synthetic */ SpaceVectorConverter $$delegate_0;
    public final DraggableHandler draggableHandler;
    public final MutableSceneTransitionLayoutStateImpl layoutState;
    public SwipeAnimation swipeAnimation;

    public DragControllerImpl(DraggableHandler draggableHandler, Swipes swipes, SwipeAnimation swipeAnimation) {
        this.$$delegate_0 = SpaceVectorConverterKt.SpaceVectorConverter(draggableHandler.orientation);
        this.draggableHandler = draggableHandler;
        this.swipeAnimation = swipeAnimation;
        this.layoutState = draggableHandler.layoutImpl.state;
        if (isDrivingTransition()) {
            throw new IllegalStateException("Multiple controllers with the same SwipeTransition");
        }
    }

    public final boolean isDrivingTransition() {
        TransitionState transitionState = this.layoutState.getTransitionState();
        TransitionState.Transition transition = this.swipeAnimation.contentTransition;
        if (transition == null) {
            transition = null;
        }
        return Intrinsics.areEqual(transitionState, transition);
    }

    @Override // com.android.compose.gesture.NestedDraggable.Controller
    public final boolean isReadyToDrag() {
        if (!this.layoutState.deferTransitionProgress) {
            return true;
        }
        ElementStateScopeImpl elementStateScopeImpl = this.draggableHandler.layoutImpl.elementStateScope;
        return (elementStateScopeImpl.m925targetSizeGG5KONw(this.swipeAnimation.fromContent) == null || elementStateScopeImpl.m925targetSizeGG5KONw(this.swipeAnimation.toContent) == null) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0034 A[PHI: r5
      0x0034: PHI (r5v6 float) = (r5v4 float), (r5v3 float) binds: [B:28:0x0040, B:20:0x0032] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0036 A[PHI: r1
      0x0036: PHI (r1v1 float) = (r1v0 float), (r1v2 float) binds: [B:28:0x0040, B:20:0x0032] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.android.compose.gesture.NestedDraggable.Controller
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final float onDrag(float f) {
        SwipeAnimation swipeAnimation = this.swipeAnimation;
        if (f == 0.0f || !isDrivingTransition() || swipeAnimation.isAnimatingOffset()) {
            return 0.0f;
        }
        float fDistance = swipeAnimation.distance();
        float dragOffset = swipeAnimation.gestureContext.getDragOffset();
        float f2 = dragOffset + f;
        if (fDistance != 0.0f) {
            if (fDistance > 0.0f) {
                f = f2 >= 0.0f ? f2 : 0.0f;
                f = f > fDistance ? fDistance : f;
            } else {
                if (f2 >= fDistance) {
                    fDistance = f2;
                }
                if (fDistance > 0.0f) {
                }
            }
        }
        swipeAnimation.setDragOffset(f);
        return f - dragOffset;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x009d  */
    @Override // com.android.compose.gesture.NestedDraggable.Controller
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onDragStopped(float f, Function1 function1, SuspendLambda suspendLambda) {
        ContentKey contentKey;
        SwipeAnimation swipeAnimation = this.swipeAnimation;
        if (!isDrivingTransition() || swipeAnimation.isAnimatingOffset()) {
            return new Float(0.0f);
        }
        float dragOffset = swipeAnimation.gestureContext.getDragOffset();
        float fDistance = swipeAnimation.distance();
        if (fDistance == 0.0f) {
            contentKey = swipeAnimation.fromContent;
        } else {
            ContentKey currentContent = swipeAnimation.getCurrentContent();
            contentKey = swipeAnimation.toContent;
            boolean zAreEqual = Intrinsics.areEqual(currentContent, contentKey);
            if (!swipeAnimation.requiresFullDistanceSwipe || zAreEqual) {
                DraggableHandler draggableHandler = this.draggableHandler;
                Dp.Companion companion = Dp.Companion;
                float fMo58toPx0680j_4 = draggableHandler.layoutImpl.density.mo58toPx0680j_4(125);
                float fMo58toPx0680j_42 = draggableHandler.layoutImpl.density.mo58toPx0680j_4(56);
                if (fDistance >= 0.0f ? dragOffset < 0.0f || f <= (-fMo58toPx0680j_4) || (f < fMo58toPx0680j_4 && ((dragOffset < fMo58toPx0680j_42 || zAreEqual) && Math.abs(dragOffset - fDistance) >= Math.abs(dragOffset))) : dragOffset > 0.0f || f >= fMo58toPx0680j_4 || (f > (-fMo58toPx0680j_4) && ((dragOffset > (-fMo58toPx0680j_42) || zAreEqual) && Math.abs(dragOffset - fDistance) >= Math.abs(dragOffset)))) {
                }
            } else if (dragOffset / fDistance < 1.0f) {
            }
        }
        return SwipeAnimation.animateOffset$default(swipeAnimation, f, contentKey, null, function1, suspendLambda, 4);
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toFloat-TH1AsA0$1, reason: not valid java name */
    public final float mo916toFloatTH1AsA0$1(long j) {
        return this.$$delegate_0.mo916toFloatTH1AsA0$1(j);
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toFloat-k-4lQ0M$1, reason: not valid java name */
    public final float mo917toFloatk4lQ0M$1(long j) {
        return this.$$delegate_0.mo917toFloatk4lQ0M$1(j);
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toIntOffset-Bjo55l4, reason: not valid java name */
    public final long mo918toIntOffsetBjo55l4(int i) {
        return this.$$delegate_0.mo918toIntOffsetBjo55l4(i);
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toOffset-tuRUvjQ$1, reason: not valid java name */
    public final long mo919toOffsettuRUvjQ$1(float f) {
        return this.$$delegate_0.mo919toOffsettuRUvjQ$1(f);
    }
}
