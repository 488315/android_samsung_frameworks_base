package com.android.systemui.qs;

import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SecQSGradationDrawableController$onViewAttached$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SecQSGradationDrawableController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecQSGradationDrawableController$onViewAttached$1(SecQSGradationDrawableController secQSGradationDrawableController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = secQSGradationDrawableController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SecQSGradationDrawableController$onViewAttached$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecQSGradationDrawableController$onViewAttached$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SecQSGradationDrawableController secQSGradationDrawableController = this.this$0;
            SecPanelExpansionStateInteractor secPanelExpansionStateInteractor = secQSGradationDrawableController.panelExpansionStateInteractor;
            FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.distinctUntilChanged(FlowKt.combine(secPanelExpansionStateInteractor.shadeFraction, secPanelExpansionStateInteractor.lockscreenShadeFraction, secPanelExpansionStateInteractor.statusBarState, new SecQSGradationDrawableController$onViewAttached$1$1$1(null))), secQSGradationDrawableController.uiDisplayModeInteractor.getUiDisplayMode(), new SecQSGradationDrawableController$onViewAttached$1$1$2(null));
            SecQSGradationDrawableController$onViewAttached$1$1$3 secQSGradationDrawableController$onViewAttached$1$1$3 = new SecQSGradationDrawableController$onViewAttached$1$1$3(secQSGradationDrawableController, null);
            this.label = 1;
            if (FlowKt.collectLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, secQSGradationDrawableController$onViewAttached$1$1$3, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
