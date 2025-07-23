package com.android.systemui.qs.panels.ui.viewmodel;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.ui.unit.Dp;
import com.android.compose.animation.Bounceable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BounceableTileViewModel implements Bounceable {
    public static final float BounceSize;
    public final Animatable animatableBounce;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        Dp.Companion companion = Dp.Companion;
        BounceSize = 8;
    }

    public BounceableTileViewModel() {
        Dp m835boximpl = Dp.m835boximpl(0);
        Dp.Companion companion = Dp.Companion;
        this.animatableBounce = new Animatable(m835boximpl, VectorConvertersKt.DpToVector, null, null, 12, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x006e, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r1, r2, null, null, null, r6, 14) != r0) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0070, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0055, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r9.animatableBounce, r10, null, null, null, r6, 14) == r0) goto L22;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object animateBounce(kotlin.coroutines.jvm.internal.ContinuationImpl r10) {
        /*
            r9 = this;
            boolean r0 = r10 instanceof com.android.systemui.qs.panels.ui.viewmodel.BounceableTileViewModel$animateBounce$1
            if (r0 == 0) goto L14
            r0 = r10
            com.android.systemui.qs.panels.ui.viewmodel.BounceableTileViewModel$animateBounce$1 r0 = (com.android.systemui.qs.panels.ui.viewmodel.BounceableTileViewModel$animateBounce$1) r0
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
            com.android.systemui.qs.panels.ui.viewmodel.BounceableTileViewModel$animateBounce$1 r0 = new com.android.systemui.qs.panels.ui.viewmodel.BounceableTileViewModel$animateBounce$1
            r0.<init>(r9, r10)
            goto L12
        L1a:
            java.lang.Object r10 = r6.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r8 = 2
            r2 = 1
            if (r1 == 0) goto L3c
            if (r1 == r2) goto L34
            if (r1 != r8) goto L2c
            kotlin.ResultKt.throwOnFailure(r10)
            goto L71
        L2c:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L34:
            java.lang.Object r9 = r6.L$0
            com.android.systemui.qs.panels.ui.viewmodel.BounceableTileViewModel r9 = (com.android.systemui.qs.panels.ui.viewmodel.BounceableTileViewModel) r9
            kotlin.ResultKt.throwOnFailure(r10)
            goto L58
        L3c:
            kotlin.ResultKt.throwOnFailure(r10)
            float r10 = com.android.systemui.qs.panels.ui.viewmodel.BounceableTileViewModel.BounceSize
            androidx.compose.ui.unit.Dp r10 = androidx.compose.ui.unit.Dp.m835boximpl(r10)
            r6.L$0 = r9
            r6.label = r2
            r5 = 0
            r7 = 14
            androidx.compose.animation.core.Animatable r1 = r9.animatableBounce
            r3 = 0
            r4 = 0
            r2 = r10
            java.lang.Object r10 = androidx.compose.animation.core.Animatable.animateTo$default(r1, r2, r3, r4, r5, r6, r7)
            if (r10 != r0) goto L58
            goto L70
        L58:
            androidx.compose.animation.core.Animatable r1 = r9.animatableBounce
            r9 = 0
            float r9 = (float) r9
            androidx.compose.ui.unit.Dp r2 = androidx.compose.ui.unit.Dp.m835boximpl(r9)
            r9 = 0
            r6.L$0 = r9
            r6.label = r8
            r5 = 0
            r7 = 14
            r3 = 0
            r4 = 0
            java.lang.Object r9 = androidx.compose.animation.core.Animatable.animateTo$default(r1, r2, r3, r4, r5, r6, r7)
            if (r9 != r0) goto L71
        L70:
            return r0
        L71:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.viewmodel.BounceableTileViewModel.animateBounce(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* renamed from: getBounce-D9Ej5fM, reason: not valid java name */
    public final float m2892getBounceD9Ej5fM() {
        return ((Dp) this.animatableBounce.internalState.getValue()).value;
    }
}
