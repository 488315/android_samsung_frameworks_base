package com.android.systemui.qs.panels.ui.viewmodel;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.ui.unit.Dp;
import com.android.compose.animation.Bounceable;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class BounceableTileViewModel implements Bounceable {
    public static final float BounceSize;
    public final Animatable animatableBounce;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.BounceableTileViewModel$animateBounce$1, reason: invalid class name */
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
            return BounceableTileViewModel.this.animateBounce(this);
        }
    }

    static {
        new Companion(null);
        Dp.Companion companion = Dp.Companion;
        BounceSize = 8;
    }

    public BounceableTileViewModel() {
        Dp dpM837boximpl = Dp.m837boximpl(0);
        Dp.Companion companion = Dp.Companion;
        this.animatableBounce = new Animatable(dpM837boximpl, VectorConvertersKt.DpToVector, null, null, 12, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x006e, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r1, r2, null, null, null, r6, 14) == r0) goto L22;
     */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object animateBounce(ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        AnonymousClass1 anonymousClass12 = anonymousClass1;
        Object obj = anonymousClass12.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass12.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Dp dpM837boximpl = Dp.m837boximpl(BounceSize);
            anonymousClass12.L$0 = this;
            anonymousClass12.label = 1;
            if (Animatable.animateTo$default(this.animatableBounce, dpM837boximpl, null, null, null, anonymousClass12, 14) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        this = (BounceableTileViewModel) anonymousClass12.L$0;
        ResultKt.throwOnFailure(obj);
        Animatable animatable = this.animatableBounce;
        Dp dpM837boximpl2 = Dp.m837boximpl(0);
        anonymousClass12.L$0 = null;
        anonymousClass12.label = 2;
    }

    /* renamed from: getBounce-D9Ej5fM, reason: not valid java name */
    public final float m2909getBounceD9Ej5fM() {
        return ((Dp) this.animatableBounce.internalState.getValue()).value;
    }
}
