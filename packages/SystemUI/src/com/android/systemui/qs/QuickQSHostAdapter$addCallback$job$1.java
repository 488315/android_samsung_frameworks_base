package com.android.systemui.qs;

import com.android.systemui.qs.QSHost;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
final class QuickQSHostAdapter$addCallback$job$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ QSHost.Callback $callback;
    int label;
    final /* synthetic */ QuickQSHostAdapter this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QuickQSHostAdapter$addCallback$job$1(QuickQSHostAdapter quickQSHostAdapter, QSHost.Callback callback, Continuation continuation) {
        super(2, continuation);
        this.this$0 = quickQSHostAdapter;
        this.$callback = callback;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new QuickQSHostAdapter$addCallback$job$1(this.this$0, this.$callback, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QuickQSHostAdapter$addCallback$job$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            StateFlow currentTiles = this.this$0.interactor.getCurrentTiles();
            final QSHost.Callback callback = this.$callback;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.QuickQSHostAdapter$addCallback$job$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    callback.onTilesChanged();
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (currentTiles.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
