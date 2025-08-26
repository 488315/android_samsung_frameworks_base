package com.android.systemui.statusbar.pipeline.shared.ui.viewmodel;

import com.android.systemui.statusbar.chips.ui.model.MultipleOngoingActivityChipsModel;
import com.android.systemui.statusbar.pipeline.shared.ui.model.ChipsVisibilityModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class HomeStatusBarViewModelImpl$chipsVisibilityModel$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public HomeStatusBarViewModelImpl$chipsVisibilityModel$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        HomeStatusBarViewModelImpl$chipsVisibilityModel$1 homeStatusBarViewModelImpl$chipsVisibilityModel$1 = new HomeStatusBarViewModelImpl$chipsVisibilityModel$1((Continuation) obj3);
        homeStatusBarViewModelImpl$chipsVisibilityModel$1.L$0 = (MultipleOngoingActivityChipsModel) obj;
        homeStatusBarViewModelImpl$chipsVisibilityModel$1.Z$0 = zBooleanValue;
        return homeStatusBarViewModelImpl$chipsVisibilityModel$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new ChipsVisibilityModel((MultipleOngoingActivityChipsModel) this.L$0, this.Z$0);
    }
}
