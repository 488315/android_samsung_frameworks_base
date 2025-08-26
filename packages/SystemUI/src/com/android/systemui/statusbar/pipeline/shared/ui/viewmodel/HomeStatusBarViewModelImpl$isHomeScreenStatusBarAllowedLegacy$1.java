package com.android.systemui.statusbar.pipeline.shared.ui.viewmodel;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class HomeStatusBarViewModelImpl$isHomeScreenStatusBarAllowedLegacy$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public HomeStatusBarViewModelImpl$isHomeScreenStatusBarAllowedLegacy$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        HomeStatusBarViewModelImpl$isHomeScreenStatusBarAllowedLegacy$1 homeStatusBarViewModelImpl$isHomeScreenStatusBarAllowedLegacy$1 = new HomeStatusBarViewModelImpl$isHomeScreenStatusBarAllowedLegacy$1((Continuation) obj3);
        homeStatusBarViewModelImpl$isHomeScreenStatusBarAllowedLegacy$1.L$0 = (KeyguardState) obj;
        homeStatusBarViewModelImpl$isHomeScreenStatusBarAllowedLegacy$1.Z$0 = zBooleanValue;
        return homeStatusBarViewModelImpl$isHomeScreenStatusBarAllowedLegacy$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        KeyguardState keyguardState = (KeyguardState) this.L$0;
        return Boolean.valueOf((keyguardState == KeyguardState.GONE || keyguardState == KeyguardState.OCCLUDED) && !this.Z$0);
    }
}
