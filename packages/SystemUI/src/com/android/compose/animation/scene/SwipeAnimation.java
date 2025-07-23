package com.android.compose.animation.scene;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.DecayAnimationSpec;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.mechanics.MutableDragOffsetGestureContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SwipeAnimation implements MutableDragOffsetGestureContext {
    public final MutableState _currentContent$delegate;
    public TransitionState.Transition contentTransition;
    public final DecayAnimationSpec decayAnimationSpec;
    public final Function1 distance;
    public final ContentKey fromContent;
    public final MutableDragOffsetGestureContext gestureContext;
    public final boolean isUpOrLeft;
    public final MutableSceneTransitionLayoutStateImpl layoutState;
    public final MutableState offsetAnimation$delegate;
    public final CompletableDeferredImpl offsetAnimationRunnable;
    public final boolean requiresFullDistanceSwipe;
    public final ContentKey toContent;

    public SwipeAnimation(MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl, ContentKey contentKey, ContentKey contentKey2, Orientation orientation, boolean z, boolean z2, Function1 function1, ContentKey contentKey3, MutableDragOffsetGestureContext mutableDragOffsetGestureContext, DecayAnimationSpec<Float> decayAnimationSpec) {
        this.layoutState = mutableSceneTransitionLayoutStateImpl;
        this.fromContent = contentKey;
        this.toContent = contentKey2;
        this.isUpOrLeft = z;
        this.requiresFullDistanceSwipe = z2;
        this.distance = function1;
        this.gestureContext = mutableDragOffsetGestureContext;
        this.decayAnimationSpec = decayAnimationSpec;
        this._currentContent$delegate = SnapshotStateKt.mutableStateOf$default(contentKey3);
        this.offsetAnimation$delegate = SnapshotStateKt.mutableStateOf$default(null);
        this.offsetAnimationRunnable = CompletableDeferredKt.CompletableDeferred$default();
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x007d, code lost:
    
        if (r4 <= r5) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0189, code lost:
    
        if (r14 != null) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x018b, code lost:
    
        r14 = ((androidx.compose.material3.MotionScheme$Companion$standard$1) r10.layoutState.motionScheme).defaultSpatialSpec;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0193, code lost:
    
        r2 = new java.lang.Float(r12);
        r4 = new java.lang.Float(r13);
        r6.F$0 = r13;
        r6.label = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x01ab, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r11, r2, r14, r4, null, r6, 8) != r0) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x01ad, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0088, code lost:
    
        r10 = new java.lang.Float(r13);
        r6.L$0 = r11;
        r6.F$0 = r12;
        r6.F$1 = r13;
        r6.F$2 = r15;
        r6.F$3 = r4;
        r6.label = 1;
        r10 = r11.animateDecay(r10, r1, null, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x009e, code lost:
    
        if (r10 != r0) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00a2, code lost:
    
        r14 = r10;
        r10 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0086, code lost:
    
        if (r4 >= r7) goto L30;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$animateOffset(com.android.compose.animation.scene.SwipeAnimation r10, androidx.compose.animation.core.Animatable r11, float r12, float r13, androidx.compose.animation.core.AnimationSpec r14, kotlin.coroutines.jvm.internal.ContinuationImpl r15) {
        /*
            Method dump skipped, instructions count: 479
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.SwipeAnimation.access$animateOffset(com.android.compose.animation.scene.SwipeAnimation, androidx.compose.animation.core.Animatable, float, float, androidx.compose.animation.core.AnimationSpec, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00a5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object animateOffset$default(com.android.compose.animation.scene.SwipeAnimation r9, float r10, com.android.compose.animation.scene.ContentKey r11, androidx.compose.animation.core.AnimationSpec r12, kotlin.jvm.functions.Function1 r13, kotlin.coroutines.jvm.internal.SuspendLambda r14, int r15) {
        /*
            Method dump skipped, instructions count: 332
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.SwipeAnimation.animateOffset$default(com.android.compose.animation.scene.SwipeAnimation, float, com.android.compose.animation.scene.ContentKey, androidx.compose.animation.core.AnimationSpec, kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.SuspendLambda, int):java.lang.Object");
    }

    public final float distance() {
        return ((Number) this.distance.mo779invoke(this)).floatValue();
    }

    public final void freezeAndAnimateToCurrentState() {
        if (isAnimatingOffset()) {
            return;
        }
        TransitionState.Transition transition = this.contentTransition;
        if (transition == null) {
            transition = null;
        }
        BuildersKt.launch$default(transition.getCoroutineScope$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(), null, null, new SwipeAnimation$freezeAndAnimateToCurrentState$1(this, null), 3);
    }

    public final ContentKey getCurrentContent() {
        return (ContentKey) ((SnapshotMutableStateImpl) this._currentContent$delegate).getValue();
    }

    @Override // com.android.mechanics.MutableDragOffsetGestureContext
    public final float getDragOffset() {
        return this.gestureContext.getDragOffset();
    }

    public final float getPreviewProgress() {
        float dragOffset;
        boolean isInPreviewStage = isInPreviewStage();
        MutableDragOffsetGestureContext mutableDragOffsetGestureContext = this.gestureContext;
        if (isInPreviewStage) {
            Animatable animatable = (Animatable) ((SnapshotMutableStateImpl) this.offsetAnimation$delegate).getValue();
            dragOffset = animatable != null ? ((Number) animatable.internalState.getValue()).floatValue() : mutableDragOffsetGestureContext.getDragOffset();
        } else {
            dragOffset = mutableDragOffsetGestureContext.getDragOffset();
        }
        float distance = distance();
        if (distance == 0.0f) {
            return 0.0f;
        }
        return dragOffset / distance;
    }

    public final float getProgress() {
        Animatable animatable = (Animatable) ((SnapshotMutableStateImpl) this.offsetAnimation$delegate).getValue();
        float floatValue = isInPreviewStage() ? 0.0f : animatable != null ? ((Number) ((SnapshotMutableStateImpl) animatable.internalState.value$delegate).getValue()).floatValue() : this.gestureContext.getDragOffset();
        float distance = distance();
        if (distance == 0.0f) {
            return 0.0f;
        }
        return floatValue / distance;
    }

    public final float getProgressVelocity() {
        Animatable animatable = (Animatable) ((SnapshotMutableStateImpl) this.offsetAnimation$delegate).getValue();
        if (animatable != null) {
            float distance = distance();
            if (distance != 0.0f) {
                return ((Number) animatable.getVelocity()).floatValue() / Math.abs(distance);
            }
        }
        return 0.0f;
    }

    public final boolean isAnimatingOffset() {
        return ((Animatable) ((SnapshotMutableStateImpl) this.offsetAnimation$delegate).getValue()) != null;
    }

    public final boolean isInPreviewStage() {
        TransitionState.Transition transition = this.contentTransition;
        if (transition == null) {
            transition = null;
        }
        return transition.previewTransformationSpec != null && Intrinsics.areEqual(getCurrentContent(), this.fromContent);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0051, code lost:
    
        if (r6.mo779invoke(r0) == r1) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0053, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0041, code lost:
    
        if (r6 == r1) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object run(kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.android.compose.animation.scene.SwipeAnimation$run$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.compose.animation.scene.SwipeAnimation$run$1 r0 = (com.android.compose.animation.scene.SwipeAnimation$run$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.compose.animation.scene.SwipeAnimation$run$1 r0 = new com.android.compose.animation.scene.SwipeAnimation$run$1
            r0.<init>(r5, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L36
            if (r2 == r4) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r6)
            goto L54
        L2a:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L32:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L44
        L36:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.label = r4
            kotlinx.coroutines.CompletableDeferredImpl r5 = r5.offsetAnimationRunnable
            java.lang.Object r6 = r5.awaitInternal(r0)
            if (r6 != r1) goto L44
            goto L53
        L44:
            kotlin.jvm.functions.Function1 r6 = (kotlin.jvm.functions.Function1) r6
            if (r6 != 0) goto L4b
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        L4b:
            r0.label = r3
            java.lang.Object r5 = r6.mo779invoke(r0)
            if (r5 != r1) goto L54
        L53:
            return r1
        L54:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.SwipeAnimation.run(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // com.android.mechanics.MutableDragOffsetGestureContext
    public final void setDragOffset(float f) {
        this.gestureContext.setDragOffset(f);
    }

    public /* synthetic */ SwipeAnimation(MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl, ContentKey contentKey, ContentKey contentKey2, Orientation orientation, boolean z, boolean z2, Function1 function1, ContentKey contentKey3, MutableDragOffsetGestureContext mutableDragOffsetGestureContext, DecayAnimationSpec decayAnimationSpec, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(mutableSceneTransitionLayoutStateImpl, contentKey, contentKey2, orientation, z, z2, function1, (i & 128) != 0 ? contentKey : contentKey3, mutableDragOffsetGestureContext, decayAnimationSpec);
    }
}
