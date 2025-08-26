package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.util.ui.AnimatedValue;
import com.android.systemui.util.ui.AnimatedValueKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* loaded from: classes2.dex */
final class KeyguardRootViewModel$isAodPromotedNotifVisible$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;

    public KeyguardRootViewModel$isAodPromotedNotifVisible$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean zBooleanValue = ((Boolean) obj3).booleanValue();
        KeyguardRootViewModel$isAodPromotedNotifVisible$1 keyguardRootViewModel$isAodPromotedNotifVisible$1 = new KeyguardRootViewModel$isAodPromotedNotifVisible$1((Continuation) obj4);
        keyguardRootViewModel$isAodPromotedNotifVisible$1.L$0 = (AnimatedValue) obj;
        keyguardRootViewModel$isAodPromotedNotifVisible$1.L$1 = (AnimatedValue) obj2;
        keyguardRootViewModel$isAodPromotedNotifVisible$1.Z$0 = zBooleanValue;
        return keyguardRootViewModel$isAodPromotedNotifVisible$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object value;
        Object value2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        AnimatedValue animatedValue = (AnimatedValue) this.L$0;
        AnimatedValue animatedValue2 = (AnimatedValue) this.L$1;
        boolean z = this.Z$0;
        boolean z2 = animatedValue instanceof AnimatedValue.Animating;
        if (z2) {
            value = ((AnimatedValue.Animating) animatedValue).getValue();
        } else {
            if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
                throw new NoWhenBranchMatchedException();
            }
            value = ((AnimatedValue.NotAnimating) animatedValue).getValue();
        }
        boolean z3 = animatedValue2 instanceof AnimatedValue.Animating;
        if (z3) {
            value2 = ((AnimatedValue.Animating) animatedValue2).getValue();
        } else {
            if (!(animatedValue2 instanceof AnimatedValue.NotAnimating)) {
                throw new NoWhenBranchMatchedException();
            }
            value2 = ((AnimatedValue.NotAnimating) animatedValue2).getValue();
        }
        Boolean boolValueOf = Boolean.valueOf(((Boolean) value).booleanValue() && !((Boolean) value2).booleanValue() && z);
        if (z2) {
            if (z3) {
                return new AnimatedValue.Animating(boolValueOf, new AnimatedValueKt.C11831(animatedValue, animatedValue2));
            }
            if (animatedValue2 instanceof AnimatedValue.NotAnimating) {
                return new AnimatedValue.Animating(boolValueOf, ((AnimatedValue.Animating) animatedValue).getOnStopAnimating());
            }
            throw new NoWhenBranchMatchedException();
        }
        if (!(animatedValue instanceof AnimatedValue.NotAnimating)) {
            throw new NoWhenBranchMatchedException();
        }
        if (z3) {
            return new AnimatedValue.Animating(boolValueOf, ((AnimatedValue.Animating) animatedValue2).getOnStopAnimating());
        }
        if (animatedValue2 instanceof AnimatedValue.NotAnimating) {
            return new AnimatedValue.NotAnimating(boolValueOf);
        }
        throw new NoWhenBranchMatchedException();
    }
}
