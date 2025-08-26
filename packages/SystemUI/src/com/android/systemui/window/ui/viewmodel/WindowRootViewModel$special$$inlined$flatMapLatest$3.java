package com.android.systemui.window.ui.viewmodel;

import com.android.systemui.window.ui.viewmodel.WindowRootViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* loaded from: classes3.dex */
public final class WindowRootViewModel$special$$inlined$flatMapLatest$3 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ WindowRootViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WindowRootViewModel$special$$inlined$flatMapLatest$3(Continuation continuation, WindowRootViewModel windowRootViewModel) {
        super(3, continuation);
        this.this$0 = windowRootViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        WindowRootViewModel$special$$inlined$flatMapLatest$3 windowRootViewModel$special$$inlined$flatMapLatest$3 = new WindowRootViewModel$special$$inlined$flatMapLatest$3((Continuation) obj3, this.this$0);
        windowRootViewModel$special$$inlined$flatMapLatest$3.L$0 = (FlowCollector) obj;
        windowRootViewModel$special$$inlined$flatMapLatest$3.L$1 = obj2;
        return windowRootViewModel$special$$inlined$flatMapLatest$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                WindowRootViewModel.Companion companion = WindowRootViewModel.Companion;
                Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(this.this$0.blurInteractor.isBlurOpaque);
                companion.getClass();
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(flowDistinctUntilChanged, new WindowRootViewModel$Companion$logIfPossible$1("isBlurOpaque", null));
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, this) == coroutineSingletons) {
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
