package com.android.systemui.shade.domain.interactor;

import com.android.systemui.statusbar.phone.DozeParameters;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

final class ShadeInteractorImpl$isShadeTouchable$2 extends SuspendLambda implements Function4 {
    final /* synthetic */ DozeParameters $dozeParams;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    public ShadeInteractorImpl$isShadeTouchable$2(DozeParameters dozeParameters, Continuation continuation) {
        super(4, continuation);
        this.$dozeParams = dozeParameters;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj3).booleanValue();
        ShadeInteractorImpl$isShadeTouchable$2 shadeInteractorImpl$isShadeTouchable$2 = new ShadeInteractorImpl$isShadeTouchable$2(this.$dozeParams, (Continuation) obj4);
        shadeInteractorImpl$isShadeTouchable$2.Z$0 = booleanValue;
        shadeInteractorImpl$isShadeTouchable$2.Z$1 = booleanValue2;
        shadeInteractorImpl$isShadeTouchable$2.Z$2 = booleanValue3;
        return shadeInteractorImpl$isShadeTouchable$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        boolean z3 = this.Z$2;
        if (z2) {
            z3 = this.$dozeParams.mControlScreenOffAnimation;
        } else if (!z) {
            z3 = true;
        }
        return Boolean.valueOf(z3);
    }
}
