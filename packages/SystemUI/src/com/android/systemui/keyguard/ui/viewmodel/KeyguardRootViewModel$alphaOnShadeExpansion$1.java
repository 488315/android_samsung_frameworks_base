package com.android.systemui.keyguard.ui.viewmodel;

import android.util.MathUtils;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardRootViewModel$alphaOnShadeExpansion$1 extends SuspendLambda implements Function6 {
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    private /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public KeyguardRootViewModel$alphaOnShadeExpansion$1(Continuation continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        float floatValue = ((Number) obj4).floatValue();
        float floatValue2 = ((Number) obj5).floatValue();
        KeyguardRootViewModel$alphaOnShadeExpansion$1 keyguardRootViewModel$alphaOnShadeExpansion$1 = new KeyguardRootViewModel$alphaOnShadeExpansion$1((Continuation) obj6);
        keyguardRootViewModel$alphaOnShadeExpansion$1.L$0 = (FlowCollector) obj;
        keyguardRootViewModel$alphaOnShadeExpansion$1.Z$0 = booleanValue;
        keyguardRootViewModel$alphaOnShadeExpansion$1.Z$1 = booleanValue2;
        keyguardRootViewModel$alphaOnShadeExpansion$1.F$0 = floatValue;
        keyguardRootViewModel$alphaOnShadeExpansion$1.F$1 = floatValue2;
        return keyguardRootViewModel$alphaOnShadeExpansion$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            boolean z = this.Z$0;
            boolean z2 = this.Z$1;
            float f = this.F$0;
            float f2 = this.F$1;
            if (z2 && !z) {
                Float f3 = new Float(1.0f - MathUtils.constrainedMap(0.0f, 1.0f, 0.0f, 0.2f, Math.max(f, f2)));
                this.label = 1;
                if (flowCollector.emit(f3, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
