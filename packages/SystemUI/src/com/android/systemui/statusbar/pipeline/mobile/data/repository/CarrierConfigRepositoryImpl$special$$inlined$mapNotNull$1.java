package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
public final class CarrierConfigRepositoryImpl$special$$inlined$mapNotNull$1 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ CarrierConfigRepositoryImpl this$0;

    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepositoryImpl$special$$inlined$mapNotNull$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FlowCollector $this_unsafeFlow;
        public final /* synthetic */ CarrierConfigRepositoryImpl this$0;

        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepositoryImpl$special$$inlined$mapNotNull$1$2$1, reason: invalid class name */
        public final class AnonymousClass1 extends ContinuationImpl {
            Object L$0;
            int label;
            /* synthetic */ Object result;

            public AnonymousClass1(Continuation continuation) {
                super(continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return AnonymousClass2.this.emit(null, this);
            }
        }

        public AnonymousClass2(FlowCollector flowCollector, CarrierConfigRepositoryImpl carrierConfigRepositoryImpl) {
            this.$this_unsafeFlow = flowCollector;
            this.this$0 = carrierConfigRepositoryImpl;
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1;
            if (continuation instanceof AnonymousClass1) {
                anonymousClass1 = (AnonymousClass1) continuation;
                int i = anonymousClass1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    anonymousClass1.label = i - Integer.MIN_VALUE;
                } else {
                    anonymousClass1 = new AnonymousClass1(continuation);
                }
            }
            Object obj2 = anonymousClass1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = anonymousClass1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj2);
                int iIntValue = ((Number) obj).intValue();
                CarrierConfigManager carrierConfigManager = this.this$0.carrierConfigManager;
                PersistableBundle configForSubId = carrierConfigManager != null ? carrierConfigManager.getConfigForSubId(iIntValue) : null;
                Pair pair = configForSubId != null ? new Pair(new Integer(iIntValue), configForSubId) : null;
                if (pair != null) {
                    anonymousClass1.label = 1;
                    if (this.$this_unsafeFlow.emit(pair, anonymousClass1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj2);
            }
            return Unit.INSTANCE;
        }
    }

    public CarrierConfigRepositoryImpl$special$$inlined$mapNotNull$1(Flow flow, CarrierConfigRepositoryImpl carrierConfigRepositoryImpl) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = carrierConfigRepositoryImpl;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object objCollect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector, this.this$0), continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }
}
