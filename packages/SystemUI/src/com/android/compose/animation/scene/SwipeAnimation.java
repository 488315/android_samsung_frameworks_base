package com.android.compose.animation.scene;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.animation.core.AnimationEndReason;
import androidx.compose.animation.core.AnimationResult;
import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.DecayAnimationSpec;
import androidx.compose.animation.core.DecayAnimationSpecKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.material3.MotionScheme$Companion$standard$1;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.mechanics.MutableDragOffsetGestureContext;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineScope;

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

    /* renamed from: com.android.compose.animation.scene.SwipeAnimation$freezeAndAnimateToCurrentState$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SwipeAnimation.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SwipeAnimation swipeAnimation = SwipeAnimation.this;
                ContentKey currentContent = swipeAnimation.getCurrentContent();
                this.label = 1;
                if (SwipeAnimation.animateOffset$default(swipeAnimation, 0.0f, currentContent, null, null, this, 12) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.compose.animation.scene.SwipeAnimation$run$1, reason: invalid class name and case insensitive filesystem */
    final class C07691 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C07691(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SwipeAnimation.this.run(this);
        }
    }

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

    /* JADX WARN: Code restructure failed: missing block: B:43:0x01ab, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r11, r2, r14, r4, null, r6, 8) == r0) goto L44;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$animateOffset(SwipeAnimation swipeAnimation, Animatable animatable, float f, float f2, AnimationSpec animationSpec, ContinuationImpl continuationImpl) {
        SwipeAnimation$animateOffset$4 swipeAnimation$animateOffset$4;
        float fFloatValue;
        Object obj;
        float f3;
        swipeAnimation.getClass();
        if (continuationImpl instanceof SwipeAnimation$animateOffset$4) {
            swipeAnimation$animateOffset$4 = (SwipeAnimation$animateOffset$4) continuationImpl;
            int i = swipeAnimation$animateOffset$4.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                swipeAnimation$animateOffset$4.label = i - Integer.MIN_VALUE;
            } else {
                swipeAnimation$animateOffset$4 = new SwipeAnimation$animateOffset$4(swipeAnimation, continuationImpl);
            }
        }
        SwipeAnimation$animateOffset$4 swipeAnimation$animateOffset$42 = swipeAnimation$animateOffset$4;
        Object obj2 = swipeAnimation$animateOffset$42.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = swipeAnimation$animateOffset$42.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj2);
            fFloatValue = ((Number) animatable.internalState.getValue()).floatValue();
            DecayAnimationSpec decayAnimationSpec = swipeAnimation.decayAnimationSpec;
            float fCalculateTargetValue = DecayAnimationSpecKt.calculateTargetValue(decayAnimationSpec, fFloatValue, f2);
            Object obj3 = animatable.lowerBound;
            if (obj3 == null) {
                throw new IllegalStateException("No lower bound");
            }
            float fFloatValue2 = ((Number) obj3).floatValue();
            Object obj4 = animatable.upperBound;
            if (obj4 == null) {
                throw new IllegalStateException("No upper bound");
            }
            float fFloatValue3 = ((Number) obj4).floatValue();
            if (f != fFloatValue2) {
                if (f == fFloatValue3) {
                    if (fCalculateTargetValue >= fFloatValue3) {
                    }
                    return coroutineSingletons;
                }
                StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("Target ", f, " should be ", fFloatValue2, " or ");
                sbM.append(fFloatValue3);
                throw new IllegalStateException(sbM.toString().toString());
            }
            if (fCalculateTargetValue <= fFloatValue2) {
                Float f4 = new Float(f2);
                swipeAnimation$animateOffset$42.L$0 = animatable;
                swipeAnimation$animateOffset$42.F$0 = f;
                swipeAnimation$animateOffset$42.F$1 = f2;
                swipeAnimation$animateOffset$42.F$2 = fFloatValue;
                swipeAnimation$animateOffset$42.F$3 = fCalculateTargetValue;
                swipeAnimation$animateOffset$42.label = 1;
                Object objAnimateDecay = animatable.animateDecay(f4, decayAnimationSpec, null, swipeAnimation$animateOffset$42);
                if (objAnimateDecay != coroutineSingletons) {
                    obj = objAnimateDecay;
                    f3 = fCalculateTargetValue;
                    AnimationResult animationResult = (AnimationResult) obj;
                    if (((Number) animatable.internalState.getValue()).floatValue() != f) {
                    }
                }
            } else {
                if (animationSpec == null) {
                    animationSpec = ((MotionScheme$Companion$standard$1) swipeAnimation.layoutState.motionScheme).defaultSpatialSpec;
                }
                Float f5 = new Float(f);
                Float f6 = new Float(f2);
                swipeAnimation$animateOffset$42.F$0 = f2;
                swipeAnimation$animateOffset$42.label = 2;
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            f2 = swipeAnimation$animateOffset$42.F$0;
            ResultKt.throwOnFailure(obj2);
            return new Float(f2);
        }
        f3 = swipeAnimation$animateOffset$42.F$3;
        float f7 = swipeAnimation$animateOffset$42.F$2;
        f2 = swipeAnimation$animateOffset$42.F$1;
        f = swipeAnimation$animateOffset$42.F$0;
        Animatable animatable2 = (Animatable) swipeAnimation$animateOffset$42.L$0;
        ResultKt.throwOnFailure(obj2);
        fFloatValue = f7;
        animatable = animatable2;
        obj = obj2;
        AnimationResult animationResult2 = (AnimationResult) obj;
        if (((Number) animatable.internalState.getValue()).floatValue() != f) {
            return new Float(f2 - ((Number) animationResult2.endState.getVelocity()).floatValue());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("animatable.value = " + animatable.internalState.getValue() + " != " + f + " = targetOffset");
        sb.append('\n');
        StringBuilder sb2 = new StringBuilder("  initialOffset=");
        sb2.append(fFloatValue);
        sb.append(sb2.toString());
        sb.append('\n');
        sb.append("  targetOffset=" + f);
        sb.append('\n');
        sb.append("  initialVelocity=" + f2);
        sb.append('\n');
        sb.append("  decayOffset=" + f3);
        sb.append('\n');
        AnimationEndReason animationEndReason = animationResult2.endReason;
        AnimationState animationState = animationResult2.endState;
        sb.append("  animateDecay result: reason=" + animationEndReason + " value=" + ((SnapshotMutableStateImpl) animationState.value$delegate).getValue() + " velocity=" + animationState.getVelocity());
        sb.append('\n');
        throw new IllegalStateException(sb.toString().toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0094  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Object animateOffset$default(SwipeAnimation swipeAnimation, float f, ContentKey contentKey, AnimationSpec animationSpec, Function1 function1, SuspendLambda suspendLambda, int i) {
        ContentKey currentContent;
        TransitionState.Transition transition;
        float f2;
        boolean zBooleanValue;
        AnimationSpec animationSpec2 = (i & 4) != 0 ? null : animationSpec;
        Function1 function12 = (i & 8) != 0 ? null : function1;
        if (swipeAnimation.isAnimatingOffset()) {
            throw new IllegalStateException("SwipeAnimation.animateOffset() can only be called once");
        }
        if (Intrinsics.areEqual(contentKey, swipeAnimation.getCurrentContent())) {
            currentContent = contentKey;
        } else {
            TransitionState.Transition transition2 = swipeAnimation.contentTransition;
            if (transition2 == null) {
                transition2 = null;
            }
            boolean z = transition2 instanceof TransitionState.Transition.ChangeScene;
            MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = swipeAnimation.layoutState;
            if (z) {
                zBooleanValue = ((Boolean) mutableSceneTransitionLayoutStateImpl.canChangeScene.mo781invoke((SceneKey) contentKey)).booleanValue();
            } else if (transition2 instanceof TransitionState.Transition.ShowOrHideOverlay) {
                TransitionState.Transition.ShowOrHideOverlay showOrHideOverlay = (TransitionState.Transition.ShowOrHideOverlay) transition2;
                boolean zAreEqual = Intrinsics.areEqual(contentKey, showOrHideOverlay.overlay);
                OverlayKey overlayKey = showOrHideOverlay.overlay;
                zBooleanValue = zAreEqual ? ((Boolean) mutableSceneTransitionLayoutStateImpl.canShowOverlay.mo781invoke(overlayKey)).booleanValue() : ((Boolean) mutableSceneTransitionLayoutStateImpl.canHideOverlay.mo781invoke(overlayKey)).booleanValue();
            } else {
                if (!(transition2 instanceof TransitionState.Transition.ReplaceOverlay)) {
                    throw new NoWhenBranchMatchedException();
                }
                OverlayKey overlayKey2 = (OverlayKey) contentKey;
                TransitionState.Transition.ReplaceOverlay replaceOverlay = (TransitionState.Transition.ReplaceOverlay) transition2;
                zBooleanValue = ((Boolean) mutableSceneTransitionLayoutStateImpl.canReplaceOverlay.invoke(Intrinsics.areEqual(overlayKey2, replaceOverlay.toOverlay) ? replaceOverlay.fromOverlay : replaceOverlay.toOverlay, overlayKey2)).booleanValue();
            }
            if (!zBooleanValue) {
                currentContent = swipeAnimation.getCurrentContent();
            }
        }
        float fDistance = swipeAnimation.distance();
        if (Intrinsics.areEqual(currentContent, swipeAnimation.fromContent)) {
            transition = null;
            f2 = 0.0f;
        } else {
            if (fDistance == 0.0f) {
                throw new IllegalStateException("distance is equal to 0.0");
            }
            transition = null;
            f2 = fDistance;
        }
        if (!Intrinsics.areEqual(currentContent, swipeAnimation.getCurrentContent())) {
            if (swipeAnimation.isAnimatingOffset()) {
                throw new IllegalStateException("currentContent can not be changed once we are animating the offset");
            }
            ((SnapshotMutableStateImpl) swipeAnimation._currentContent$delegate).setValue(currentContent);
        }
        TransitionState.Transition transition3 = swipeAnimation.contentTransition;
        if (transition3 != null) {
            transition = transition3;
        }
        Animatable Animatable = AnimatableKt.Animatable((transition.previewTransformationSpec == null || !Intrinsics.areEqual(currentContent, swipeAnimation.toContent)) ? swipeAnimation.gestureContext.getDragOffset() : 0.0f, 0.5f);
        ((SnapshotMutableStateImpl) swipeAnimation.offsetAnimation$delegate).setValue(Animatable);
        if (fDistance > 0.0f) {
            Animatable.updateBounds(new Float(0.0f), new Float(fDistance));
        } else {
            Animatable.updateBounds(new Float(fDistance), new Float(0.0f));
        }
        if (!swipeAnimation.isAnimatingOffset()) {
            throw new IllegalStateException("Check failed.");
        }
        CompletableDeferredImpl completableDeferredImplCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default();
        swipeAnimation.offsetAnimationRunnable.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(new SwipeAnimation$animateOffset$3(swipeAnimation, Animatable, f2, f, animationSpec2, completableDeferredImplCompletableDeferred$default, function12, null));
        Object objAwaitInternal = completableDeferredImplCompletableDeferred$default.awaitInternal(suspendLambda);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return objAwaitInternal;
    }

    public final float distance() {
        return ((Number) this.distance.mo781invoke(this)).floatValue();
    }

    public final void freezeAndAnimateToCurrentState() {
        if (isAnimatingOffset()) {
            return;
        }
        TransitionState.Transition transition = this.contentTransition;
        if (transition == null) {
            transition = null;
        }
        BuildersKt.launch$default(transition.getCoroutineScope$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(), null, null, new AnonymousClass1(null), 3);
    }

    public final ContentKey getCurrentContent() {
        return (ContentKey) ((SnapshotMutableStateImpl) this._currentContent$delegate).getValue();
    }

    @Override // com.android.mechanics.MutableDragOffsetGestureContext
    public final float getDragOffset() {
        return this.gestureContext.getDragOffset();
    }

    public final float getPreviewProgress() {
        Animatable animatable;
        boolean zIsInPreviewStage = isInPreviewStage();
        MutableDragOffsetGestureContext mutableDragOffsetGestureContext = this.gestureContext;
        float dragOffset = (!zIsInPreviewStage || (animatable = (Animatable) ((SnapshotMutableStateImpl) this.offsetAnimation$delegate).getValue()) == null) ? mutableDragOffsetGestureContext.getDragOffset() : ((Number) animatable.internalState.getValue()).floatValue();
        float fDistance = distance();
        if (fDistance == 0.0f) {
            return 0.0f;
        }
        return dragOffset / fDistance;
    }

    public final float getProgress() {
        Animatable animatable = (Animatable) ((SnapshotMutableStateImpl) this.offsetAnimation$delegate).getValue();
        float fFloatValue = isInPreviewStage() ? 0.0f : animatable != null ? ((Number) ((SnapshotMutableStateImpl) animatable.internalState.value$delegate).getValue()).floatValue() : this.gestureContext.getDragOffset();
        float fDistance = distance();
        if (fDistance == 0.0f) {
            return 0.0f;
        }
        return fFloatValue / fDistance;
    }

    public final float getProgressVelocity() {
        Animatable animatable = (Animatable) ((SnapshotMutableStateImpl) this.offsetAnimation$delegate).getValue();
        if (animatable != null) {
            float fDistance = distance();
            if (fDistance != 0.0f) {
                return ((Number) animatable.getVelocity()).floatValue() / Math.abs(fDistance);
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

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0051, code lost:
    
        if (r6.mo781invoke(r0) == r1) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object run(ContinuationImpl continuationImpl) {
        C07691 c07691;
        if (continuationImpl instanceof C07691) {
            c07691 = (C07691) continuationImpl;
            int i = c07691.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c07691.label = i - Integer.MIN_VALUE;
            } else {
                c07691 = new C07691(continuationImpl);
            }
        }
        Object objAwaitInternal = c07691.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c07691.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objAwaitInternal);
            c07691.label = 1;
            objAwaitInternal = this.offsetAnimationRunnable.awaitInternal(c07691);
            if (objAwaitInternal != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objAwaitInternal);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(objAwaitInternal);
        Function1 function1 = (Function1) objAwaitInternal;
        if (function1 == null) {
            return Unit.INSTANCE;
        }
        c07691.label = 2;
    }

    @Override // com.android.mechanics.MutableDragOffsetGestureContext
    public final void setDragOffset(float f) {
        this.gestureContext.setDragOffset(f);
    }

    public /* synthetic */ SwipeAnimation(MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl, ContentKey contentKey, ContentKey contentKey2, Orientation orientation, boolean z, boolean z2, Function1 function1, ContentKey contentKey3, MutableDragOffsetGestureContext mutableDragOffsetGestureContext, DecayAnimationSpec decayAnimationSpec, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(mutableSceneTransitionLayoutStateImpl, contentKey, contentKey2, orientation, z, z2, function1, (i & 128) != 0 ? contentKey : contentKey3, mutableDragOffsetGestureContext, decayAnimationSpec);
    }
}
