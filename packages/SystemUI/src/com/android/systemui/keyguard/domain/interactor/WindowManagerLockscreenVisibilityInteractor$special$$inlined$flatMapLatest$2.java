package com.android.systemui.keyguard.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

public final class WindowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ WindowManagerLockscreenVisibilityInteractor this$0;

    public WindowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$2(Continuation continuation, WindowManagerLockscreenVisibilityInteractor windowManagerLockscreenVisibilityInteractor) {
        super(3, continuation);
        this.this$0 = windowManagerLockscreenVisibilityInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        WindowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$2 windowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$2 = new WindowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$2((Continuation) obj3, this.this$0);
        windowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        windowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return windowManagerLockscreenVisibilityInteractor$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                WindowManagerLockscreenVisibilityInteractor windowManagerLockscreenVisibilityInteractor = this.this$0;
                flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(windowManagerLockscreenVisibilityInteractor.transitionSpecificSurfaceBehindVisibility, windowManagerLockscreenVisibilityInteractor.defaultSurfaceBehindVisibility, new WindowManagerLockscreenVisibilityInteractor$surfaceBehindVisibility$2$1(null));
            } else {
                flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = this.this$0.defaultSurfaceBehindVisibility;
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, flowCollector) == coroutineSingletons) {
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
