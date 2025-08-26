package com.android.systemui.statusbar.pipeline.shared.ui.viewmodel;

import com.android.app.tracing.TraceStateLogger;
import com.android.systemui.statusbar.pipeline.shared.ui.model.ChipsVisibilityModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final class HomeStatusBarViewModelImpl$special$$inlined$traceEach$default$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ TraceStateLogger $stateLogger;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeStatusBarViewModelImpl$special$$inlined$traceEach$default$2(TraceStateLogger traceStateLogger, Continuation continuation) {
        super(2, continuation);
        this.$stateLogger = traceStateLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        HomeStatusBarViewModelImpl$special$$inlined$traceEach$default$2 homeStatusBarViewModelImpl$special$$inlined$traceEach$default$2 = new HomeStatusBarViewModelImpl$special$$inlined$traceEach$default$2(this.$stateLogger, continuation);
        homeStatusBarViewModelImpl$special$$inlined$traceEach$default$2.L$0 = obj;
        return homeStatusBarViewModelImpl$special$$inlined$traceEach$default$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HomeStatusBarViewModelImpl$special$$inlined$traceEach$default$2) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Object obj2 = this.L$0;
        ChipsVisibilityModel chipsVisibilityModel = (ChipsVisibilityModel) obj2;
        this.$stateLogger.log("Chips[allowed=" + chipsVisibilityModel.areChipsAllowed + " numChips=" + chipsVisibilityModel.chips.active.size() + "]");
        return Unit.INSTANCE;
    }
}
