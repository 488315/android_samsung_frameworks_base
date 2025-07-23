package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public StackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        StackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$1 stackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$1 = new StackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$1((Continuation) obj3);
        stackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        stackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return stackedMobileIconViewModelImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            List list = (List) this.L$1;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((MobileIconViewModelCommon) it.next()).getIcon());
            }
            final Flow[] flowArr = (Flow[]) CollectionsKt___CollectionsKt.toList(arrayList).toArray(new Flow[0]);
            Flow flow = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl$dualSim_delegate$lambda$4$$inlined$combine$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl$dualSim_delegate$lambda$4$$inlined$combine$1$3, reason: invalid class name */
                public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                    private /* synthetic */ Object L$0;
                    /* synthetic */ Object L$1;
                    int label;

                    public AnonymousClass3(Continuation continuation) {
                        super(3, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3);
                        anonymousClass3.L$0 = (FlowCollector) obj;
                        anonymousClass3.L$1 = (Object[]) obj2;
                        return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            FlowCollector flowCollector = (FlowCollector) this.L$0;
                            List list = ArraysKt___ArraysKt.toList((SignalIconModel[]) ((Object[]) this.L$1));
                            ArrayList arrayList = new ArrayList();
                            for (Object obj2 : list) {
                                if (obj2 instanceof SignalIconModel.Cellular) {
                                    arrayList.add(obj2);
                                }
                            }
                            if (arrayList.size() != 2) {
                                arrayList = null;
                            }
                            StackedMobileIconViewModel.DualSim dualSim = arrayList != null ? new StackedMobileIconViewModel.DualSim((SignalIconModel.Cellular) arrayList.get(0), (SignalIconModel.Cellular) arrayList.get(1)) : null;
                            this.label = 1;
                            if (flowCollector.emit(dualSim, this) == coroutineSingletons) {
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

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    final Flow[] flowArr2 = flowArr;
                    Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl$dualSim_delegate$lambda$4$$inlined$combine$1.2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return new SignalIconModel[flowArr2.length];
                        }
                    }, new AnonymousClass3(null), flowCollector2, continuation);
                    return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
                }
            };
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
