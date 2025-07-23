package com.android.systemui.blur.domain.interactor;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SecPanelWindowBlurInteractor$shouldShow$1 extends SuspendLambda implements Function4 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ SecPanelWindowBlurInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecPanelWindowBlurInteractor$shouldShow$1(SecPanelWindowBlurInteractor secPanelWindowBlurInteractor, Continuation continuation) {
        super(4, continuation);
        this.this$0 = secPanelWindowBlurInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        int intValue = ((Number) obj2).intValue();
        SecPanelWindowBlurInteractor$shouldShow$1 secPanelWindowBlurInteractor$shouldShow$1 = new SecPanelWindowBlurInteractor$shouldShow$1(this.this$0, (Continuation) obj4);
        secPanelWindowBlurInteractor$shouldShow$1.Z$0 = booleanValue;
        secPanelWindowBlurInteractor$shouldShow$1.I$0 = intValue;
        secPanelWindowBlurInteractor$shouldShow$1.L$0 = (KeyguardState) obj3;
        return secPanelWindowBlurInteractor$shouldShow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z2 = this.Z$0;
        int i = this.I$0;
        KeyguardState keyguardState = (KeyguardState) this.L$0;
        if (z2) {
            SecPanelWindowBlurInteractor secPanelWindowBlurInteractor = this.this$0;
            String str = SecPanelWindowBlurInteractor.TAG;
            secPanelWindowBlurInteractor.getClass();
            if (i != 0) {
                this.this$0.getClass();
                if (keyguardState != KeyguardState.OCCLUDED) {
                    z = false;
                    return Boolean.valueOf(z);
                }
            }
        }
        z = true;
        return Boolean.valueOf(z);
    }
}
