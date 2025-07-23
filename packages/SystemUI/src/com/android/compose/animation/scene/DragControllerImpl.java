package com.android.compose.animation.scene;

import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.compose.gesture.NestedDraggable;
import com.android.compose.ui.util.SpaceVectorConverter;
import com.android.compose.ui.util.SpaceVectorConverterKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        return (elementStateScopeImpl.m923targetSizeGG5KONw(this.swipeAnimation.fromContent) == null || elementStateScopeImpl.m923targetSizeGG5KONw(this.swipeAnimation.toContent) == null) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0032, code lost:
    
        if (r1 > r5) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0034, code lost:
    
        r6 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0036, code lost:
    
        r6 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0040, code lost:
    
        if (r5 > 0.0f) goto L22;
     */
    @Override // com.android.compose.gesture.NestedDraggable.Controller
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final float onDrag(float r6) {
        /*
            r5 = this;
            com.android.compose.animation.scene.SwipeAnimation r0 = r5.swipeAnimation
            r1 = 0
            int r2 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r2 != 0) goto L8
            goto L48
        L8:
            boolean r5 = r5.isDrivingTransition()
            if (r5 == 0) goto L48
            boolean r5 = r0.isAnimatingOffset()
            if (r5 == 0) goto L15
            goto L48
        L15:
            float r5 = r0.distance()
            com.android.mechanics.MutableDragOffsetGestureContext r2 = r0.gestureContext
            float r2 = r2.getDragOffset()
            float r3 = r2 + r6
            int r4 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r4 != 0) goto L26
            goto L43
        L26:
            int r6 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r6 <= 0) goto L38
            int r6 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r6 >= 0) goto L2f
            goto L30
        L2f:
            r1 = r3
        L30:
            int r6 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r6 <= 0) goto L36
        L34:
            r6 = r5
            goto L43
        L36:
            r6 = r1
            goto L43
        L38:
            int r6 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r6 >= 0) goto L3d
            goto L3e
        L3d:
            r5 = r3
        L3e:
            int r6 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r6 <= 0) goto L34
            goto L36
        L43:
            r0.setDragOffset(r6)
            float r6 = r6 - r2
            return r6
        L48:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.DragControllerImpl.onDrag(float):float");
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0036, code lost:
    
        if ((r1 / r3) >= 1.0f) goto L43;
     */
    @Override // com.android.compose.gesture.NestedDraggable.Controller
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onDragStopped(float r11, kotlin.jvm.functions.Function1 r12, kotlin.coroutines.jvm.internal.SuspendLambda r13) {
        /*
            r10 = this;
            com.android.compose.animation.scene.SwipeAnimation r0 = r10.swipeAnimation
            boolean r1 = r10.isDrivingTransition()
            r2 = 0
            if (r1 == 0) goto Laa
            boolean r1 = r0.isAnimatingOffset()
            if (r1 == 0) goto L11
            goto Laa
        L11:
            com.android.mechanics.MutableDragOffsetGestureContext r1 = r0.gestureContext
            float r1 = r1.getDragOffset()
            float r3 = r0.distance()
            int r4 = (r3 > r2 ? 1 : (r3 == r2 ? 0 : -1))
            if (r4 != 0) goto L21
            goto L9d
        L21:
            com.android.compose.animation.scene.ContentKey r5 = r0.getCurrentContent()
            com.android.compose.animation.scene.ContentKey r6 = r0.toContent
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
            boolean r7 = r0.requiresFullDistanceSwipe
            if (r7 == 0) goto L39
            if (r5 != 0) goto L39
            float r1 = r1 / r3
            r10 = 1065353216(0x3f800000, float:1.0)
            int r10 = (r1 > r10 ? 1 : (r1 == r10 ? 0 : -1))
            if (r10 < 0) goto L9d
            goto L9b
        L39:
            com.android.compose.animation.scene.DraggableHandler r10 = r10.draggableHandler
            com.android.compose.animation.scene.SceneTransitionLayoutImpl r7 = r10.layoutImpl
            androidx.compose.ui.unit.Density r7 = r7.density
            r8 = 125(0x7d, float:1.75E-43)
            float r8 = (float) r8
            androidx.compose.ui.unit.Dp$Companion r9 = androidx.compose.ui.unit.Dp.Companion
            float r7 = r7.mo57toPx0680j_4(r8)
            com.android.compose.animation.scene.SceneTransitionLayoutImpl r10 = r10.layoutImpl
            androidx.compose.ui.unit.Density r10 = r10.density
            r8 = 56
            float r8 = (float) r8
            float r10 = r10.mo57toPx0680j_4(r8)
            if (r4 >= 0) goto L79
            int r2 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r2 > 0) goto L9d
            int r2 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
            if (r2 < 0) goto L5e
            goto L9d
        L5e:
            float r2 = -r7
            int r2 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r2 <= 0) goto L9b
            float r10 = -r10
            int r10 = (r1 > r10 ? 1 : (r1 == r10 ? 0 : -1))
            if (r10 > 0) goto L6a
            if (r5 == 0) goto L9b
        L6a:
            float r10 = r1 - r3
            float r10 = java.lang.Math.abs(r10)
            float r1 = java.lang.Math.abs(r1)
            int r10 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            if (r10 >= 0) goto L9d
            goto L9b
        L79:
            int r2 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r2 < 0) goto L9d
            float r2 = -r7
            int r2 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r2 > 0) goto L83
            goto L9d
        L83:
            int r2 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
            if (r2 >= 0) goto L9b
            int r10 = (r1 > r10 ? 1 : (r1 == r10 ? 0 : -1))
            if (r10 < 0) goto L8d
            if (r5 == 0) goto L9b
        L8d:
            float r10 = r1 - r3
            float r10 = java.lang.Math.abs(r10)
            float r1 = java.lang.Math.abs(r1)
            int r10 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            if (r10 >= 0) goto L9d
        L9b:
            r2 = r6
            goto La0
        L9d:
            com.android.compose.animation.scene.ContentKey r6 = r0.fromContent
            goto L9b
        La0:
            r3 = 0
            r6 = 4
            r1 = r11
            r4 = r12
            r5 = r13
            java.lang.Object r10 = com.android.compose.animation.scene.SwipeAnimation.animateOffset$default(r0, r1, r2, r3, r4, r5, r6)
            return r10
        Laa:
            java.lang.Float r10 = new java.lang.Float
            r10.<init>(r2)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.DragControllerImpl.onDragStopped(float, kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.SuspendLambda):java.lang.Object");
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toFloat-TH1AsA0$1, reason: not valid java name */
    public final float mo914toFloatTH1AsA0$1(long j) {
        return this.$$delegate_0.mo914toFloatTH1AsA0$1(j);
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toFloat-k-4lQ0M$1, reason: not valid java name */
    public final float mo915toFloatk4lQ0M$1(long j) {
        return this.$$delegate_0.mo915toFloatk4lQ0M$1(j);
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toIntOffset-Bjo55l4, reason: not valid java name */
    public final long mo916toIntOffsetBjo55l4(int i) {
        return this.$$delegate_0.mo916toIntOffsetBjo55l4(i);
    }

    @Override // com.android.compose.ui.util.SpaceVectorConverter
    /* renamed from: toOffset-tuRUvjQ$1, reason: not valid java name */
    public final long mo917toOffsettuRUvjQ$1(float f) {
        return this.$$delegate_0.mo917toOffsettuRUvjQ$1(f);
    }
}
