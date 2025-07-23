package androidx.compose.foundation.gestures.snapping;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.DecayAnimationSpec;
import androidx.compose.foundation.gestures.ScrollableKt;
import androidx.compose.foundation.gestures.ScrollableKt$DefaultScrollMotionDurationScale$1;
import androidx.compose.foundation.gestures.TargetedFlingBehavior;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SnapFlingBehavior implements TargetedFlingBehavior {
    public final DecayAnimationSpec decayAnimationSpec;
    public final ScrollableKt$DefaultScrollMotionDurationScale$1 motionScaleDuration = ScrollableKt.DefaultScrollMotionDurationScale;
    public final AnimationSpec snapAnimationSpec;
    public final SnapLayoutInfoProvider snapLayoutInfoProvider;

    public SnapFlingBehavior(SnapLayoutInfoProvider snapLayoutInfoProvider, DecayAnimationSpec<Float> decayAnimationSpec, AnimationSpec<Float> animationSpec) {
        this.snapLayoutInfoProvider = snapLayoutInfoProvider;
        this.decayAnimationSpec = decayAnimationSpec;
        this.snapAnimationSpec = animationSpec;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$tryApproach(androidx.compose.foundation.gestures.snapping.SnapFlingBehavior r4, androidx.compose.foundation.gestures.ScrollScope r5, float r6, float r7, kotlin.jvm.functions.Function1 r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            r4.getClass()
            boolean r0 = r9 instanceof androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$tryApproach$1
            if (r0 == 0) goto L17
            r0 = r9
            androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$tryApproach$1 r0 = (androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$tryApproach$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L17
            int r1 = r1 - r2
            r0.label = r1
        L15:
            r9 = r0
            goto L1d
        L17:
            androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$tryApproach$1 r0 = new androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$tryApproach$1
            r0.<init>(r4, r9)
            goto L15
        L1d:
            java.lang.Object r0 = r9.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r9.label
            r3 = 1
            if (r2 == 0) goto L34
            if (r2 != r3) goto L2c
            kotlin.ResultKt.throwOnFailure(r0)
            goto L87
        L2c:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L34:
            kotlin.ResultKt.throwOnFailure(r0)
            float r0 = java.lang.Math.abs(r6)
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 != 0) goto L41
            goto L49
        L41:
            float r0 = java.lang.Math.abs(r7)
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 != 0) goto L50
        L49:
            r4 = 28
            androidx.compose.animation.core.AnimationState r4 = androidx.compose.animation.core.AnimationStateKt.AnimationState$default(r6, r7, r4)
            return r4
        L50:
            r9.label = r3
            androidx.compose.animation.core.DecayAnimationSpec r0 = r4.decayAnimationSpec
            float r2 = androidx.compose.animation.core.DecayAnimationSpecKt.calculateTargetValue(r0, r2, r7)
            float r2 = java.lang.Math.abs(r2)
            float r3 = java.lang.Math.abs(r6)
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 < 0) goto L6a
            androidx.compose.foundation.gestures.snapping.DecayApproachAnimation r4 = new androidx.compose.foundation.gestures.snapping.DecayApproachAnimation
            r4.<init>(r0)
            goto L72
        L6a:
            androidx.compose.foundation.gestures.snapping.TargetApproachAnimation r0 = new androidx.compose.foundation.gestures.snapping.TargetApproachAnimation
            androidx.compose.animation.core.AnimationSpec r4 = r4.snapAnimationSpec
            r0.<init>(r4)
            r4 = r0
        L72:
            float r0 = androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt.MinFlingVelocityDp
            r0 = r6
            java.lang.Float r6 = new java.lang.Float
            r6.<init>(r0)
            r0 = r7
            java.lang.Float r7 = new java.lang.Float
            r7.<init>(r0)
            java.lang.Object r0 = r4.approachAnimation(r5, r6, r7, r8, r9)
            if (r0 != r1) goto L87
            return r1
        L87:
            androidx.compose.foundation.gestures.snapping.AnimationResult r0 = (androidx.compose.foundation.gestures.snapping.AnimationResult) r0
            androidx.compose.animation.core.AnimationState r4 = r0.currentAnimationState
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.snapping.SnapFlingBehavior.access$tryApproach(androidx.compose.foundation.gestures.snapping.SnapFlingBehavior, androidx.compose.foundation.gestures.ScrollScope, float, float, kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object fling(androidx.compose.foundation.gestures.ScrollScope r11, float r12, kotlin.jvm.functions.Function1 r13, kotlin.coroutines.jvm.internal.ContinuationImpl r14) {
        /*
            r10 = this;
            boolean r0 = r14 instanceof androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$fling$1
            if (r0 == 0) goto L13
            r0 = r14
            androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$fling$1 r0 = (androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$fling$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$fling$1 r0 = new androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$fling$1
            r0.<init>(r10, r14)
        L18:
            java.lang.Object r14 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L34
            if (r2 != r3) goto L2c
            java.lang.Object r10 = r0.L$0
            r13 = r10
            kotlin.jvm.functions.Function1 r13 = (kotlin.jvm.functions.Function1) r13
            kotlin.ResultKt.throwOnFailure(r14)
            goto L4f
        L2c:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L34:
            kotlin.ResultKt.throwOnFailure(r14)
            androidx.compose.foundation.gestures.ScrollableKt$DefaultScrollMotionDurationScale$1 r14 = r10.motionScaleDuration
            androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$fling$result$1 r4 = new androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$fling$result$1
            r9 = 0
            r5 = r10
            r8 = r11
            r6 = r12
            r7 = r13
            r4.<init>(r5, r6, r7, r8, r9)
            r0.L$0 = r7
            r0.label = r3
            java.lang.Object r14 = kotlinx.coroutines.BuildersKt.withContext(r14, r4, r0)
            if (r14 != r1) goto L4e
            return r1
        L4e:
            r13 = r7
        L4f:
            androidx.compose.foundation.gestures.snapping.AnimationResult r14 = (androidx.compose.foundation.gestures.snapping.AnimationResult) r14
            java.lang.Float r10 = new java.lang.Float
            r11 = 0
            r10.<init>(r11)
            r13.mo779invoke(r10)
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.snapping.SnapFlingBehavior.fling(androidx.compose.foundation.gestures.ScrollScope, float, kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final int hashCode() {
        return this.snapLayoutInfoProvider.hashCode() + ((this.decayAnimationSpec.hashCode() + (this.snapAnimationSpec.hashCode() * 31)) * 31);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object performFling(androidx.compose.foundation.gestures.ScrollScope r5, float r6, kotlin.jvm.functions.Function1 r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r4 = this;
            boolean r0 = r8 instanceof androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$performFling$1
            if (r0 == 0) goto L13
            r0 = r8
            androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$performFling$1 r0 = (androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$performFling$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$performFling$1 r0 = new androidx.compose.foundation.gestures.snapping.SnapFlingBehavior$performFling$1
            r0.<init>(r4, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r8)
            goto L3b
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r8)
            r0.label = r3
            java.lang.Object r8 = r4.fling(r5, r6, r7, r0)
            if (r8 != r1) goto L3b
            return r1
        L3b:
            androidx.compose.foundation.gestures.snapping.AnimationResult r8 = (androidx.compose.foundation.gestures.snapping.AnimationResult) r8
            java.lang.Object r4 = r8.remainingOffset
            java.lang.Number r4 = (java.lang.Number) r4
            float r4 = r4.floatValue()
            r5 = 0
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 != 0) goto L4b
            goto L57
        L4b:
            androidx.compose.animation.core.AnimationState r4 = r8.currentAnimationState
            java.lang.Object r4 = r4.getVelocity()
            java.lang.Number r4 = (java.lang.Number) r4
            float r5 = r4.floatValue()
        L57:
            java.lang.Float r4 = new java.lang.Float
            r4.<init>(r5)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.snapping.SnapFlingBehavior.performFling(androidx.compose.foundation.gestures.ScrollScope, float, kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
