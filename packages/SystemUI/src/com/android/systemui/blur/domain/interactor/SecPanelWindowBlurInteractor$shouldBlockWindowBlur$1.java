package com.android.systemui.blur.domain.interactor;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SecPanelWindowBlurInteractor$shouldBlockWindowBlur$1 extends SuspendLambda implements Function4 {
    /* synthetic */ float F$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public SecPanelWindowBlurInteractor$shouldBlockWindowBlur$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        float floatValue = ((Number) obj).floatValue();
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        SecPanelWindowBlurInteractor$shouldBlockWindowBlur$1 secPanelWindowBlurInteractor$shouldBlockWindowBlur$1 = new SecPanelWindowBlurInteractor$shouldBlockWindowBlur$1((Continuation) obj4);
        secPanelWindowBlurInteractor$shouldBlockWindowBlur$1.F$0 = floatValue;
        secPanelWindowBlurInteractor$shouldBlockWindowBlur$1.Z$0 = booleanValue;
        secPanelWindowBlurInteractor$shouldBlockWindowBlur$1.Z$1 = booleanValue2;
        return secPanelWindowBlurInteractor$shouldBlockWindowBlur$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        float f = this.F$0;
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        String str = SecPanelWindowBlurInteractor.TAG;
        StringBuilder sb = new StringBuilder(" max alpha = ");
        sb.append(f);
        sb.append(" backgroundPanelShow = ");
        sb.append(z);
        sb.append(", blurReduced = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z2, str);
        return Boolean.valueOf((z && f == 1.0f) || z2);
    }
}
