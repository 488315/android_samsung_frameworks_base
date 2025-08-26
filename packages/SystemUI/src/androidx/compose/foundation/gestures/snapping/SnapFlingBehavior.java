package androidx.compose.foundation.gestures.snapping;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationStateKt;
import androidx.compose.animation.core.DecayAnimationSpec;
import androidx.compose.animation.core.DecayAnimationSpecKt;
import androidx.compose.foundation.gestures.ScrollScope;
import androidx.compose.foundation.gestures.ScrollableKt;
import androidx.compose.foundation.gestures.ScrollableKt$DefaultScrollMotionDurationScale$1;
import androidx.compose.foundation.gestures.TargetedFlingBehavior;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes.dex */
public final class SnapFlingBehavior implements TargetedFlingBehavior {
    public final DecayAnimationSpec decayAnimationSpec;
    public final ScrollableKt$DefaultScrollMotionDurationScale$1 motionScaleDuration = ScrollableKt.DefaultScrollMotionDurationScale;
    public final AnimationSpec snapAnimationSpec;
    public final SnapLayoutInfoProvider snapLayoutInfoProvider;

    /* renamed from: androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$fling$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SnapFlingBehavior.this.fling(null, 0.0f, null, this);
        }
    }

    /* renamed from: androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$performFling$1, reason: invalid class name and case insensitive filesystem */
    final class C07041 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C07041(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SnapFlingBehavior.this.performFling(null, 0.0f, null, this);
        }
    }

    public SnapFlingBehavior(SnapLayoutInfoProvider snapLayoutInfoProvider, DecayAnimationSpec<Float> decayAnimationSpec, AnimationSpec<Float> animationSpec) {
        this.snapLayoutInfoProvider = snapLayoutInfoProvider;
        this.decayAnimationSpec = decayAnimationSpec;
        this.snapAnimationSpec = animationSpec;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$tryApproach(SnapFlingBehavior snapFlingBehavior, ScrollScope scrollScope, float f, float f2, Function1 function1, ContinuationImpl continuationImpl) {
        SnapFlingBehavior$tryApproach$1 snapFlingBehavior$tryApproach$1;
        snapFlingBehavior.getClass();
        if (continuationImpl instanceof SnapFlingBehavior$tryApproach$1) {
            snapFlingBehavior$tryApproach$1 = (SnapFlingBehavior$tryApproach$1) continuationImpl;
            int i = snapFlingBehavior$tryApproach$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                snapFlingBehavior$tryApproach$1.label = i - Integer.MIN_VALUE;
            } else {
                snapFlingBehavior$tryApproach$1 = new SnapFlingBehavior$tryApproach$1(snapFlingBehavior, continuationImpl);
            }
        }
        SnapFlingBehavior$tryApproach$1 snapFlingBehavior$tryApproach$12 = snapFlingBehavior$tryApproach$1;
        Object objApproachAnimation = snapFlingBehavior$tryApproach$12.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = snapFlingBehavior$tryApproach$12.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objApproachAnimation);
            if (Math.abs(f) == 0.0f || Math.abs(f2) == 0.0f) {
                return AnimationStateKt.AnimationState$default(f, f2, 28);
            }
            snapFlingBehavior$tryApproach$12.label = 1;
            DecayAnimationSpec decayAnimationSpec = snapFlingBehavior.decayAnimationSpec;
            ApproachAnimation decayApproachAnimation = Math.abs(DecayAnimationSpecKt.calculateTargetValue(decayAnimationSpec, 0.0f, f2)) >= Math.abs(f) ? new DecayApproachAnimation(decayAnimationSpec) : new TargetApproachAnimation(snapFlingBehavior.snapAnimationSpec);
            float f3 = SnapFlingBehaviorKt.MinFlingVelocityDp;
            objApproachAnimation = decayApproachAnimation.approachAnimation(scrollScope, new Float(f), new Float(f2), function1, snapFlingBehavior$tryApproach$12);
            if (objApproachAnimation == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objApproachAnimation);
        }
        return ((AnimationResult) objApproachAnimation).currentAnimationState;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof SnapFlingBehavior) {
            SnapFlingBehavior snapFlingBehavior = (SnapFlingBehavior) obj;
            if (Intrinsics.areEqual(snapFlingBehavior.snapAnimationSpec, this.snapAnimationSpec) && Intrinsics.areEqual(snapFlingBehavior.decayAnimationSpec, this.decayAnimationSpec) && Intrinsics.areEqual(snapFlingBehavior.snapLayoutInfoProvider, this.snapLayoutInfoProvider)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object fling(ScrollScope scrollScope, float f, Function1 function1, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        Function1 function12;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objWithContext = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            ScrollableKt$DefaultScrollMotionDurationScale$1 scrollableKt$DefaultScrollMotionDurationScale$1 = this.motionScaleDuration;
            SnapFlingBehavior$fling$result$1 snapFlingBehavior$fling$result$1 = new SnapFlingBehavior$fling$result$1(this, f, function1, scrollScope, null);
            anonymousClass1.L$0 = function1;
            anonymousClass1.label = 1;
            objWithContext = BuildersKt.withContext(scrollableKt$DefaultScrollMotionDurationScale$1, snapFlingBehavior$fling$result$1, anonymousClass1);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
            function12 = function1;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            function12 = (Function1) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objWithContext);
        }
        AnimationResult animationResult = (AnimationResult) objWithContext;
        function12.mo781invoke(new Float(0.0f));
        return animationResult;
    }

    public final int hashCode() {
        return this.snapLayoutInfoProvider.hashCode() + ((this.decayAnimationSpec.hashCode() + (this.snapAnimationSpec.hashCode() * 31)) * 31);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object performFling(ScrollScope scrollScope, float f, Function1 function1, ContinuationImpl continuationImpl) throws Throwable {
        C07041 c07041;
        if (continuationImpl instanceof C07041) {
            c07041 = (C07041) continuationImpl;
            int i = c07041.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c07041.label = i - Integer.MIN_VALUE;
            } else {
                c07041 = new C07041(continuationImpl);
            }
        }
        Object objFling = c07041.result;
        Object obj = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c07041.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objFling);
            c07041.label = 1;
            objFling = fling(scrollScope, f, function1, c07041);
            if (objFling == obj) {
                return obj;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objFling);
        }
        AnimationResult animationResult = (AnimationResult) objFling;
        return new Float(((Number) animationResult.remainingOffset).floatValue() != 0.0f ? ((Number) animationResult.currentAnimationState.getVelocity()).floatValue() : 0.0f);
    }
}
