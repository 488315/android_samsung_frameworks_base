package androidx.compose.material.ripple;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.geometry.Offset;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CompletableDeferredImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RippleAnimation {
    public final Animatable animatedAlpha;
    public final Animatable animatedCenterPercent;
    public final Animatable animatedRadiusPercent;
    public final boolean bounded;
    public final MutableState finishRequested$delegate;
    public final CompletableDeferredImpl finishSignalDeferred;
    public final MutableState finishedFadingIn$delegate;
    public Offset origin;
    public final float radius;
    public Float startRadius;
    public Offset targetCenter;

    public /* synthetic */ RippleAnimation(Offset offset, float f, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(offset, f, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0089, code lost:
    
        if (r7 == r1) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x008b, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0071, code lost:
    
        if (r7.finishSignalDeferred.awaitInternal(r0) != r1) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x005b, code lost:
    
        if (r8 == r1) goto L32;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object animate(kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof androidx.compose.material.ripple.RippleAnimation$animate$1
            if (r0 == 0) goto L13
            r0 = r8
            androidx.compose.material.ripple.RippleAnimation$animate$1 r0 = (androidx.compose.material.ripple.RippleAnimation$animate$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.material.ripple.RippleAnimation$animate$1 r0 = new androidx.compose.material.ripple.RippleAnimation$animate$1
            r0.<init>(r7, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 3
            r4 = 2
            r5 = 1
            r6 = 0
            if (r2 == 0) goto L46
            if (r2 == r5) goto L3e
            if (r2 == r4) goto L36
            if (r2 != r3) goto L2e
            kotlin.ResultKt.throwOnFailure(r8)
            goto L8c
        L2e:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L36:
            java.lang.Object r7 = r0.L$0
            androidx.compose.material.ripple.RippleAnimation r7 = (androidx.compose.material.ripple.RippleAnimation) r7
            kotlin.ResultKt.throwOnFailure(r8)
            goto L74
        L3e:
            java.lang.Object r7 = r0.L$0
            androidx.compose.material.ripple.RippleAnimation r7 = (androidx.compose.material.ripple.RippleAnimation) r7
            kotlin.ResultKt.throwOnFailure(r8)
            goto L5e
        L46:
            kotlin.ResultKt.throwOnFailure(r8)
            r0.L$0 = r7
            r0.label = r5
            androidx.compose.material.ripple.RippleAnimation$fadeIn$2 r8 = new androidx.compose.material.ripple.RippleAnimation$fadeIn$2
            r8.<init>(r7, r6)
            java.lang.Object r8 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r8, r0)
            if (r8 != r1) goto L59
            goto L5b
        L59:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
        L5b:
            if (r8 != r1) goto L5e
            goto L8b
        L5e:
            androidx.compose.runtime.MutableState r8 = r7.finishedFadingIn$delegate
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            androidx.compose.runtime.SnapshotMutableStateImpl r8 = (androidx.compose.runtime.SnapshotMutableStateImpl) r8
            r8.setValue(r2)
            r0.L$0 = r7
            r0.label = r4
            kotlinx.coroutines.CompletableDeferredImpl r8 = r7.finishSignalDeferred
            java.lang.Object r8 = r8.awaitInternal(r0)
            if (r8 != r1) goto L74
            goto L8b
        L74:
            r0.L$0 = r6
            r0.label = r3
            r7.getClass()
            androidx.compose.material.ripple.RippleAnimation$fadeOut$2 r8 = new androidx.compose.material.ripple.RippleAnimation$fadeOut$2
            r8.<init>(r7, r6)
            java.lang.Object r7 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r8, r0)
            if (r7 != r1) goto L87
            goto L89
        L87:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
        L89:
            if (r7 != r1) goto L8c
        L8b:
            return r1
        L8c:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material.ripple.RippleAnimation.animate(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    private RippleAnimation(Offset offset, float f, boolean z) {
        this.origin = offset;
        this.radius = f;
        this.bounded = z;
        this.animatedAlpha = AnimatableKt.Animatable(0.0f, 0.01f);
        this.animatedRadiusPercent = AnimatableKt.Animatable(0.0f, 0.01f);
        this.animatedCenterPercent = AnimatableKt.Animatable(0.0f, 0.01f);
        this.finishSignalDeferred = new CompletableDeferredImpl(null);
        Boolean bool = Boolean.FALSE;
        this.finishedFadingIn$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.finishRequested$delegate = SnapshotStateKt.mutableStateOf$default(bool);
    }
}
