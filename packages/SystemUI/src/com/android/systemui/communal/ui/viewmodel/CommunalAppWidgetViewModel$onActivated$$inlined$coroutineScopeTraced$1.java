package com.android.systemui.communal.ui.viewmodel;

import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.ChannelAsFlow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class CommunalAppWidgetViewModel$onActivated$$inlined$coroutineScopeTraced$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $traceName$inlined;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CommunalAppWidgetViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalAppWidgetViewModel$onActivated$$inlined$coroutineScopeTraced$1(Continuation continuation, String str, CommunalAppWidgetViewModel communalAppWidgetViewModel) {
        super(2, continuation);
        this.$traceName$inlined = str;
        this.this$0 = communalAppWidgetViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalAppWidgetViewModel$onActivated$$inlined$coroutineScopeTraced$1 communalAppWidgetViewModel$onActivated$$inlined$coroutineScopeTraced$1 = new CommunalAppWidgetViewModel$onActivated$$inlined$coroutineScopeTraced$1(continuation, this.$traceName$inlined, this.this$0);
        communalAppWidgetViewModel$onActivated$$inlined$coroutineScopeTraced$1.L$0 = obj;
        return communalAppWidgetViewModel$onActivated$$inlined$coroutineScopeTraced$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalAppWidgetViewModel$onActivated$$inlined$coroutineScopeTraced$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ChannelAsFlow channelAsFlowReceiveAsFlow = FlowKt.receiveAsFlow(this.this$0.requests);
            final CommunalAppWidgetViewModel communalAppWidgetViewModel = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel$onActivated$2$1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Request request = (Request) obj2;
                    boolean z = request instanceof SetListener;
                    CommunalAppWidgetViewModel communalAppWidgetViewModel2 = communalAppWidgetViewModel;
                    if (z) {
                        SetListener setListener = (SetListener) request;
                        Object objAccess$handleSetListener = CommunalAppWidgetViewModel.access$handleSetListener(communalAppWidgetViewModel2, setListener.appWidgetId, setListener.listener, continuation);
                        return objAccess$handleSetListener == CoroutineSingletons.COROUTINE_SUSPENDED ? objAccess$handleSetListener : Unit.INSTANCE;
                    }
                    if (!(request instanceof UpdateSize)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    UpdateSize updateSize = (UpdateSize) request;
                    Object objAccess$handleUpdateSize = CommunalAppWidgetViewModel.access$handleUpdateSize(communalAppWidgetViewModel2, updateSize.size, updateSize.view, continuation);
                    return objAccess$handleUpdateSize == CoroutineSingletons.COROUTINE_SUSPENDED ? objAccess$handleUpdateSize : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (channelAsFlowReceiveAsFlow.collect(flowCollector, this) == coroutineSingletons) {
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
