package com.android.systemui.statusbar.pipeline.satellite.domain.interactor;

import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$4 extends SuspendLambda implements Function3 {
    final /* synthetic */ Object $defaultValue$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$4(Continuation continuation, Object obj) {
        super(3, continuation);
        this.$defaultValue$inlined = obj;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$4 deviceBasedSatelliteInteractor$special$$inlined$aggregateOver$4 = new DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$4((Continuation) obj3, this.$defaultValue$inlined);
        deviceBasedSatelliteInteractor$special$$inlined$aggregateOver$4.L$0 = (FlowCollector) obj;
        deviceBasedSatelliteInteractor$special$$inlined$aggregateOver$4.L$1 = obj2;
        return deviceBasedSatelliteInteractor$special$$inlined$aggregateOver$4.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            List list = (List) this.L$1;
            if (list.isEmpty()) {
                flow = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(this.$defaultValue$inlined);
            } else {
                final Flow[] flowArr = (Flow[]) CollectionsKt___CollectionsKt.toList(list).toArray(new Flow[0]);
                flow = new Flow() { // from class: com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$4.1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$$inlined$aggregateOver$4$1$3, reason: invalid class name */
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
                                Boolean[] boolArr = (Boolean[]) ((Object[]) this.L$1);
                                int length = boolArr.length;
                                boolean z = false;
                                int i2 = 0;
                                while (true) {
                                    if (i2 >= length) {
                                        break;
                                    }
                                    if (boolArr[i2].booleanValue()) {
                                        z = true;
                                        break;
                                    }
                                    i2++;
                                }
                                Boolean valueOf = Boolean.valueOf(z);
                                this.label = 1;
                                if (flowCollector.emit(valueOf, this) == coroutineSingletons) {
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
                        Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.statusbar.pipeline.satellite.domain.interactor.DeviceBasedSatelliteInteractor$special$.inlined.aggregateOver.4.1.2
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return new Boolean[flowArr2.length];
                            }
                        }, new AnonymousClass3(null), flowCollector2, continuation);
                        return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
                    }
                };
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
