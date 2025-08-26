package com.android.systemui.statusbar.pipeline.shared.ui.viewmodel;

import com.android.systemui.statusbar.pipeline.shared.domain.model.StatusBarDisableFlagsVisibilityModel;
import com.android.systemui.statusbar.pipeline.shared.ui.model.VisibilityModel;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* loaded from: classes3.dex */
final class HomeStatusBarViewModelImpl$isClockVisible$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ HomeStatusBarViewModelImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeStatusBarViewModelImpl$isClockVisible$1(HomeStatusBarViewModelImpl homeStatusBarViewModelImpl, Continuation continuation) {
        super(4, continuation);
        this.this$0 = homeStatusBarViewModelImpl;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
        HomeStatusBarViewModelImpl$isClockVisible$1 homeStatusBarViewModelImpl$isClockVisible$1 = new HomeStatusBarViewModelImpl$isClockVisible$1(this.this$0, (Continuation) obj4);
        homeStatusBarViewModelImpl$isClockVisible$1.Z$0 = zBooleanValue;
        homeStatusBarViewModelImpl$isClockVisible$1.Z$1 = zBooleanValue2;
        homeStatusBarViewModelImpl$isClockVisible$1.L$0 = (StatusBarDisableFlagsVisibilityModel) obj3;
        return homeStatusBarViewModelImpl$isClockVisible$1.invokeSuspend(Unit.INSTANCE);
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
        StatusBarDisableFlagsVisibilityModel statusBarDisableFlagsVisibilityModel = (StatusBarDisableFlagsVisibilityModel) this.L$0;
        boolean z3 = z && statusBarDisableFlagsVisibilityModel.isClockAllowed && !z2;
        HomeStatusBarViewModelImpl homeStatusBarViewModelImpl = this.this$0;
        HomeStatusBarViewModelImpl.Companion companion = HomeStatusBarViewModelImpl.Companion;
        homeStatusBarViewModelImpl.getClass();
        return new VisibilityModel(z3 ? 0 : 4, statusBarDisableFlagsVisibilityModel.animate);
    }
}
