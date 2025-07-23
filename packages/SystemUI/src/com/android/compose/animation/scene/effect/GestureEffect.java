package com.android.compose.animation.scene.effect;

import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.unit.Velocity;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class GestureEffect implements OverscrollEffect {
    public final OverscrollEffect delegate;
    public boolean shouldFling;

    public GestureEffect(OverscrollEffect overscrollEffect) {
        this.delegate = overscrollEffect;
    }

    @Override // androidx.compose.foundation.OverscrollEffect
    /* renamed from: applyToFling-BMRW4eQ */
    public final Object mo19applyToFlingBMRW4eQ(long j, Function2 function2, ContinuationImpl continuationImpl) {
        if (!this.shouldFling) {
            Object invoke = function2.invoke(Velocity.m876boximpl(j), continuationImpl);
            return invoke == CoroutineSingletons.COROUTINE_SUSPENDED ? invoke : Unit.INSTANCE;
        }
        this.shouldFling = false;
        Object mo19applyToFlingBMRW4eQ = this.delegate.mo19applyToFlingBMRW4eQ(j, function2, continuationImpl);
        return mo19applyToFlingBMRW4eQ == CoroutineSingletons.COROUTINE_SUSPENDED ? mo19applyToFlingBMRW4eQ : Unit.INSTANCE;
    }

    @Override // androidx.compose.foundation.OverscrollEffect
    /* renamed from: applyToScroll-Rhakbz0 */
    public final long mo20applyToScrollRhakbz0(int i, long j, Function1 function1) {
        this.shouldFling = true;
        return this.delegate.mo20applyToScrollRhakbz0(i, j, function1);
    }

    public final Object ensureApplyToFlingIsCalled(Continuation continuation) {
        Velocity.Companion.getClass();
        Object mo19applyToFlingBMRW4eQ = mo19applyToFlingBMRW4eQ(0L, new GestureEffect$ensureApplyToFlingIsCalled$2(null), (ContinuationImpl) continuation);
        return mo19applyToFlingBMRW4eQ == CoroutineSingletons.COROUTINE_SUSPENDED ? mo19applyToFlingBMRW4eQ : Unit.INSTANCE;
    }

    @Override // androidx.compose.foundation.OverscrollEffect
    public final Modifier getEffectModifier() {
        return this.delegate.getEffectModifier();
    }

    @Override // androidx.compose.foundation.OverscrollEffect
    public final DelegatableNode getNode() {
        return this.delegate.getNode();
    }

    @Override // androidx.compose.foundation.OverscrollEffect
    public final boolean isInProgress() {
        return this.delegate.isInProgress();
    }
}
