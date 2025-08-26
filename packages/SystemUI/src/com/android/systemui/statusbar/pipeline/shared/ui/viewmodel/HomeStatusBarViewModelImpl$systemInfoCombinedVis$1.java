package com.android.systemui.statusbar.pipeline.shared.ui.viewmodel;

import com.android.systemui.statusbar.events.shared.model.SystemEventAnimationState;
import com.android.systemui.statusbar.pipeline.shared.ui.model.SystemInfoCombinedVisibilityModel;
import com.android.systemui.statusbar.pipeline.shared.ui.model.VisibilityModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class HomeStatusBarViewModelImpl$systemInfoCombinedVis$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public HomeStatusBarViewModelImpl$systemInfoCombinedVis$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        HomeStatusBarViewModelImpl$systemInfoCombinedVis$1 homeStatusBarViewModelImpl$systemInfoCombinedVis$1 = new HomeStatusBarViewModelImpl$systemInfoCombinedVis$1((Continuation) obj3);
        homeStatusBarViewModelImpl$systemInfoCombinedVis$1.L$0 = (VisibilityModel) obj;
        homeStatusBarViewModelImpl$systemInfoCombinedVis$1.L$1 = (SystemEventAnimationState) obj2;
        return homeStatusBarViewModelImpl$systemInfoCombinedVis$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new SystemInfoCombinedVisibilityModel((VisibilityModel) this.L$0, (SystemEventAnimationState) this.L$1);
    }
}
