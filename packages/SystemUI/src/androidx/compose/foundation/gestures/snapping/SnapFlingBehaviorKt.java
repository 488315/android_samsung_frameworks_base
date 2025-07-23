package androidx.compose.foundation.gestures.snapping;

import androidx.compose.animation.core.AnimationScope;
import androidx.compose.foundation.ComposeFoundationFlags;
import androidx.compose.foundation.gestures.ScrollScope;
import androidx.compose.ui.unit.Dp;
import java.util.concurrent.CancellationException;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SnapFlingBehaviorKt {
    public static final float MinFlingVelocityDp;

    static {
        Dp.Companion companion = Dp.Companion;
        MinFlingVelocityDp = 400;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$animateDecay(final androidx.compose.foundation.gestures.ScrollScope r5, final float r6, androidx.compose.animation.core.AnimationState r7, androidx.compose.animation.core.DecayAnimationSpec r8, final kotlin.jvm.functions.Function1 r9, kotlin.coroutines.jvm.internal.ContinuationImpl r10) {
        /*
            boolean r0 = r10 instanceof androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt$animateDecay$1
            if (r0 == 0) goto L13
            r0 = r10
            androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt$animateDecay$1 r0 = (androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt$animateDecay$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt$animateDecay$1 r0 = new androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt$animateDecay$1
            r0.<init>(r10)
        L18:
            java.lang.Object r10 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L39
            if (r2 != r3) goto L31
            float r6 = r0.F$0
            java.lang.Object r5 = r0.L$1
            kotlin.jvm.internal.Ref$FloatRef r5 = (kotlin.jvm.internal.Ref$FloatRef) r5
            java.lang.Object r7 = r0.L$0
            androidx.compose.animation.core.AnimationState r7 = (androidx.compose.animation.core.AnimationState) r7
            kotlin.ResultKt.throwOnFailure(r10)
            goto L69
        L31:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L39:
            kotlin.ResultKt.throwOnFailure(r10)
            kotlin.jvm.internal.Ref$FloatRef r10 = new kotlin.jvm.internal.Ref$FloatRef
            r10.<init>()
            java.lang.Object r2 = r7.getVelocity()
            java.lang.Number r2 = (java.lang.Number) r2
            float r2 = r2.floatValue()
            r4 = 0
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 != 0) goto L52
            r2 = r3
            goto L53
        L52:
            r2 = 0
        L53:
            r2 = r2 ^ r3
            androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt$animateDecay$2 r4 = new androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt$animateDecay$2
            r4.<init>()
            r0.L$0 = r7
            r0.L$1 = r10
            r0.F$0 = r6
            r0.label = r3
            java.lang.Object r5 = androidx.compose.animation.core.SuspendAnimationKt.animateDecay(r7, r8, r2, r4, r0)
            if (r5 != r1) goto L68
            return r1
        L68:
            r5 = r10
        L69:
            androidx.compose.foundation.gestures.snapping.AnimationResult r8 = new androidx.compose.foundation.gestures.snapping.AnimationResult
            float r5 = r5.element
            float r6 = r6 - r5
            java.lang.Float r5 = new java.lang.Float
            r5.<init>(r6)
            r8.<init>(r5, r7)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt.access$animateDecay(androidx.compose.foundation.gestures.ScrollScope, float, androidx.compose.animation.core.AnimationState, androidx.compose.animation.core.DecayAnimationSpec, kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static final void access$animateDecay$consumeDelta(AnimationScope animationScope, ScrollScope scrollScope, Function1 function1, float f) {
        float f2;
        if (ComposeFoundationFlags.NewNestedFlingPropagationEnabled) {
            try {
                f2 = scrollScope.scrollBy(f);
            } catch (CancellationException unused) {
                animationScope.cancelAnimation();
                f2 = 0.0f;
            }
        } else {
            f2 = scrollScope.scrollBy(f);
        }
        function1.mo779invoke(Float.valueOf(f2));
        if (Math.abs(f - f2) > 0.5f) {
            animationScope.cancelAnimation();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$animateWithTarget(final androidx.compose.foundation.gestures.ScrollScope r9, float r10, final float r11, androidx.compose.animation.core.AnimationState r12, androidx.compose.animation.core.AnimationSpec r13, final kotlin.jvm.functions.Function1 r14, kotlin.coroutines.jvm.internal.ContinuationImpl r15) {
        /*
            boolean r0 = r15 instanceof androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt$animateWithTarget$1
            if (r0 == 0) goto L14
            r0 = r15
            androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt$animateWithTarget$1 r0 = (androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt$animateWithTarget$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.label = r1
        L12:
            r6 = r0
            goto L1a
        L14:
            androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt$animateWithTarget$1 r0 = new androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt$animateWithTarget$1
            r0.<init>(r15)
            goto L12
        L1a:
            java.lang.Object r15 = r6.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r7 = 0
            r2 = 1
            if (r1 == 0) goto L3e
            if (r1 != r2) goto L36
            float r9 = r6.F$1
            float r10 = r6.F$0
            java.lang.Object r11 = r6.L$1
            kotlin.jvm.internal.Ref$FloatRef r11 = (kotlin.jvm.internal.Ref$FloatRef) r11
            java.lang.Object r12 = r6.L$0
            androidx.compose.animation.core.AnimationState r12 = (androidx.compose.animation.core.AnimationState) r12
            kotlin.ResultKt.throwOnFailure(r15)
            goto L84
        L36:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L3e:
            kotlin.ResultKt.throwOnFailure(r15)
            kotlin.jvm.internal.Ref$FloatRef r15 = new kotlin.jvm.internal.Ref$FloatRef
            r15.<init>()
            java.lang.Object r1 = r12.getVelocity()
            java.lang.Number r1 = (java.lang.Number) r1
            float r8 = r1.floatValue()
            r1 = r2
            java.lang.Float r2 = new java.lang.Float
            r2.<init>(r10)
            java.lang.Object r3 = r12.getVelocity()
            java.lang.Number r3 = (java.lang.Number) r3
            float r3 = r3.floatValue()
            int r3 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r3 != 0) goto L66
            r3 = r1
            goto L67
        L66:
            r3 = 0
        L67:
            r4 = r3 ^ 1
            androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt$animateWithTarget$2 r5 = new androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt$animateWithTarget$2
            r5.<init>()
            r6.L$0 = r12
            r6.L$1 = r15
            r6.F$0 = r10
            r6.F$1 = r8
            r6.label = r1
            r1 = r12
            r3 = r13
            java.lang.Object r9 = androidx.compose.animation.core.SuspendAnimationKt.animateTo(r1, r2, r3, r4, r5, r6)
            if (r9 != r0) goto L81
            return r0
        L81:
            r11 = r15
            r12 = r1
            r9 = r8
        L84:
            java.lang.Object r13 = r12.getVelocity()
            java.lang.Number r13 = (java.lang.Number) r13
            float r13 = r13.floatValue()
            float r9 = coerceToTarget(r13, r9)
            androidx.compose.foundation.gestures.snapping.AnimationResult r13 = new androidx.compose.foundation.gestures.snapping.AnimationResult
            float r11 = r11.element
            float r10 = r10 - r11
            java.lang.Float r11 = new java.lang.Float
            r11.<init>(r10)
            r10 = 29
            androidx.compose.animation.core.AnimationState r9 = androidx.compose.animation.core.AnimationStateKt.copy$default(r12, r7, r9, r10)
            r13.<init>(r11, r9)
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt.access$animateWithTarget(androidx.compose.foundation.gestures.ScrollScope, float, float, androidx.compose.animation.core.AnimationState, androidx.compose.animation.core.AnimationSpec, kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static final float coerceToTarget(float f, float f2) {
        if (f2 == 0.0f) {
            return 0.0f;
        }
        return (f2 <= 0.0f ? f >= f2 : f <= f2) ? f : f2;
    }
}
