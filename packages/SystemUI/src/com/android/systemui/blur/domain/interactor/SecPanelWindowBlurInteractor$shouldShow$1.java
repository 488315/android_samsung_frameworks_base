package com.android.systemui.blur.domain.interactor;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

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
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        int iIntValue = ((Number) obj2).intValue();
        SecPanelWindowBlurInteractor$shouldShow$1 secPanelWindowBlurInteractor$shouldShow$1 = new SecPanelWindowBlurInteractor$shouldShow$1(this.this$0, (Continuation) obj4);
        secPanelWindowBlurInteractor$shouldShow$1.Z$0 = zBooleanValue;
        secPanelWindowBlurInteractor$shouldShow$1.I$0 = iIntValue;
        secPanelWindowBlurInteractor$shouldShow$1.L$0 = (KeyguardState) obj3;
        return secPanelWindowBlurInteractor$shouldShow$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0029  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
            if (i == 0) {
                z = true;
            } else {
                this.this$0.getClass();
                if (keyguardState != KeyguardState.OCCLUDED) {
                    z = false;
                }
            }
        }
        return Boolean.valueOf(z);
    }
}
