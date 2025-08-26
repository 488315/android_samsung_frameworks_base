package com.android.compose.animation.scene.effect;

import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.DelegatableNode;
import androidx.compose.ui.unit.Velocity;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public final class GestureEffect implements OverscrollEffect {
    public final OverscrollEffect delegate;
    public boolean shouldFling;

    /* renamed from: com.android.compose.animation.scene.effect.GestureEffect$ensureApplyToFlingIsCalled$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Velocity.m878boximpl(((Velocity) obj).packedValue);
            return new AnonymousClass2((Continuation) obj2).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Velocity.Companion.getClass();
            return Velocity.m878boximpl(0L);
        }
    }

    public GestureEffect(OverscrollEffect overscrollEffect) {
        this.delegate = overscrollEffect;
    }

    @Override // androidx.compose.foundation.OverscrollEffect
    /* renamed from: applyToFling-BMRW4eQ */
    public final Object mo19applyToFlingBMRW4eQ(long j, Function2 function2, ContinuationImpl continuationImpl) {
        if (!this.shouldFling) {
            Object objInvoke = function2.invoke(Velocity.m878boximpl(j), continuationImpl);
            return objInvoke == CoroutineSingletons.COROUTINE_SUSPENDED ? objInvoke : Unit.INSTANCE;
        }
        this.shouldFling = false;
        Object objMo19applyToFlingBMRW4eQ = this.delegate.mo19applyToFlingBMRW4eQ(j, function2, continuationImpl);
        return objMo19applyToFlingBMRW4eQ == CoroutineSingletons.COROUTINE_SUSPENDED ? objMo19applyToFlingBMRW4eQ : Unit.INSTANCE;
    }

    @Override // androidx.compose.foundation.OverscrollEffect
    /* renamed from: applyToScroll-Rhakbz0 */
    public final long mo20applyToScrollRhakbz0(int i, long j, Function1 function1) {
        this.shouldFling = true;
        return this.delegate.mo20applyToScrollRhakbz0(i, j, function1);
    }

    public final Object ensureApplyToFlingIsCalled(Continuation continuation) {
        Velocity.Companion.getClass();
        Object objMo19applyToFlingBMRW4eQ = mo19applyToFlingBMRW4eQ(0L, new AnonymousClass2(null), (ContinuationImpl) continuation);
        return objMo19applyToFlingBMRW4eQ == CoroutineSingletons.COROUTINE_SUSPENDED ? objMo19applyToFlingBMRW4eQ : Unit.INSTANCE;
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
