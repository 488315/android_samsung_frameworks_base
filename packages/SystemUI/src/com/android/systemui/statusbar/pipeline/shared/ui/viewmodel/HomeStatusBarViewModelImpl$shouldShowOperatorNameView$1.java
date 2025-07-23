package com.android.systemui.statusbar.pipeline.shared.ui.viewmodel;

import com.android.systemui.statusbar.pipeline.shared.domain.model.StatusBarDisableFlagsVisibilityModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class HomeStatusBarViewModelImpl$shouldShowOperatorNameView$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    public HomeStatusBarViewModelImpl$shouldShowOperatorNameView$1(Continuation continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
        HomeStatusBarViewModelImpl$shouldShowOperatorNameView$1 homeStatusBarViewModelImpl$shouldShowOperatorNameView$1 = new HomeStatusBarViewModelImpl$shouldShowOperatorNameView$1((Continuation) obj5);
        homeStatusBarViewModelImpl$shouldShowOperatorNameView$1.Z$0 = booleanValue;
        homeStatusBarViewModelImpl$shouldShowOperatorNameView$1.Z$1 = booleanValue2;
        homeStatusBarViewModelImpl$shouldShowOperatorNameView$1.L$0 = (StatusBarDisableFlagsVisibilityModel) obj3;
        homeStatusBarViewModelImpl$shouldShowOperatorNameView$1.Z$2 = booleanValue3;
        return homeStatusBarViewModelImpl$shouldShowOperatorNameView$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.Z$0 && !this.Z$1 && ((StatusBarDisableFlagsVisibilityModel) this.L$0).isSystemInfoAllowed && this.Z$2);
    }
}
