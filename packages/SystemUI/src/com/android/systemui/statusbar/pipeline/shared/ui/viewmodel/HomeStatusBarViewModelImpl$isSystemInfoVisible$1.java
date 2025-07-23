package com.android.systemui.statusbar.pipeline.shared.ui.viewmodel;

import com.android.systemui.statusbar.pipeline.shared.domain.model.StatusBarDisableFlagsVisibilityModel;
import com.android.systemui.statusbar.pipeline.shared.ui.model.VisibilityModel;
import com.android.systemui.statusbar.pipeline.shared.ui.viewmodel.HomeStatusBarViewModelImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class HomeStatusBarViewModelImpl$isSystemInfoVisible$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ HomeStatusBarViewModelImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeStatusBarViewModelImpl$isSystemInfoVisible$1(HomeStatusBarViewModelImpl homeStatusBarViewModelImpl, Continuation continuation) {
        super(3, continuation);
        this.this$0 = homeStatusBarViewModelImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        HomeStatusBarViewModelImpl$isSystemInfoVisible$1 homeStatusBarViewModelImpl$isSystemInfoVisible$1 = new HomeStatusBarViewModelImpl$isSystemInfoVisible$1(this.this$0, (Continuation) obj3);
        homeStatusBarViewModelImpl$isSystemInfoVisible$1.Z$0 = booleanValue;
        homeStatusBarViewModelImpl$isSystemInfoVisible$1.L$0 = (StatusBarDisableFlagsVisibilityModel) obj2;
        return homeStatusBarViewModelImpl$isSystemInfoVisible$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        StatusBarDisableFlagsVisibilityModel statusBarDisableFlagsVisibilityModel = (StatusBarDisableFlagsVisibilityModel) this.L$0;
        boolean z2 = z && statusBarDisableFlagsVisibilityModel.isSystemInfoAllowed;
        HomeStatusBarViewModelImpl homeStatusBarViewModelImpl = this.this$0;
        HomeStatusBarViewModelImpl.Companion companion = HomeStatusBarViewModelImpl.Companion;
        homeStatusBarViewModelImpl.getClass();
        return new VisibilityModel(z2 ? 0 : 8, statusBarDisableFlagsVisibilityModel.animate);
    }
}
