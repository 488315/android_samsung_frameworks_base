package com.android.systemui.qs.panels.domain.interactor;

import com.android.systemui.qs.panels.data.repository.QSColumnsRepository;
import com.android.systemui.shade.shared.model.ShadeMode;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSColumnsInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ QSColumnsRepository $repo$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSColumnsInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, QSColumnsRepository qSColumnsRepository) {
        super(3, continuation);
        this.$repo$inlined = qSColumnsRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        QSColumnsInteractor$special$$inlined$flatMapLatest$1 qSColumnsInteractor$special$$inlined$flatMapLatest$1 = new QSColumnsInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$repo$inlined);
        qSColumnsInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        qSColumnsInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return qSColumnsInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            ShadeMode shadeMode = (ShadeMode) this.L$1;
            if (Intrinsics.areEqual(shadeMode, ShadeMode.Dual.INSTANCE)) {
                flow = this.$repo$inlined.dualShadeColumns;
            } else if (Intrinsics.areEqual(shadeMode, ShadeMode.Split.INSTANCE)) {
                flow = this.$repo$inlined.splitShadeColumns;
            } else {
                if (!Intrinsics.areEqual(shadeMode, ShadeMode.Single.INSTANCE)) {
                    throw new NoWhenBranchMatchedException();
                }
                flow = this.$repo$inlined.columns;
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flow, this) == coroutineSingletons) {
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
