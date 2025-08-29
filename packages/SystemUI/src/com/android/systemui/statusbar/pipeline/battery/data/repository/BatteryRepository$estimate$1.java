package com.android.systemui.statusbar.pipeline.battery.data.repository;

import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.BatteryControllerImpl$$ExternalSyntheticLambda0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
final class BatteryRepository$estimate$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BatteryRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BatteryRepository$estimate$1(BatteryRepository batteryRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = batteryRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BatteryRepository$estimate$1 batteryRepository$estimate$1 = new BatteryRepository$estimate$1(this.this$0, continuation);
        batteryRepository$estimate$1.L$0 = obj;
        return batteryRepository$estimate$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BatteryRepository$estimate$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0086 A[PHI: r1
      0x0086: PHI (r1v2 kotlinx.coroutines.flow.FlowCollector) = (r1v3 kotlinx.coroutines.flow.FlowCollector), (r1v8 kotlinx.coroutines.flow.FlowCollector) binds: [B:25:0x0083, B:10:0x001f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:28:0x0096 -> B:13:0x0037). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        long duration;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
        } else {
            if (i == 1) {
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                this.L$0 = flowCollector;
                this.label = 2;
                if (flowCollector.emit((String) obj, this) != coroutineSingletons) {
                    Duration.Companion companion = Duration.Companion;
                    duration = DurationKt.toDuration(2, DurationUnit.MINUTES);
                    this.L$0 = flowCollector;
                    this.label = 3;
                    if (DelayKt.m3468delayVtjQ1oo(duration, this) != coroutineSingletons) {
                    }
                }
                return coroutineSingletons;
            }
            if (i == 2) {
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                Duration.Companion companion2 = Duration.Companion;
                duration = DurationKt.toDuration(2, DurationUnit.MINUTES);
                this.L$0 = flowCollector;
                this.label = 3;
                if (DelayKt.m3468delayVtjQ1oo(duration, this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        BatteryRepository batteryRepository = this.this$0;
        this.L$0 = flowCollector;
        this.label = 1;
        batteryRepository.getClass();
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
        cancellableContinuationImpl.initCancellability();
        BatteryRepository$fetchEstimate$2$callback$1 batteryRepository$fetchEstimate$2$callback$1 = new BatteryRepository$fetchEstimate$2$callback$1(cancellableContinuationImpl);
        BatteryControllerImpl batteryControllerImpl = (BatteryControllerImpl) batteryRepository.controller;
        synchronized (batteryControllerImpl.mFetchCallbacks) {
            batteryControllerImpl.mFetchCallbacks.add(batteryRepository$fetchEstimate$2$callback$1);
        }
        if (!batteryControllerImpl.mFetchingEstimate) {
            batteryControllerImpl.mFetchingEstimate = true;
            batteryControllerImpl.mBgHandler.post(new BatteryControllerImpl$$ExternalSyntheticLambda0(batteryControllerImpl, 0));
        }
        obj = cancellableContinuationImpl.getResult();
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (obj != coroutineSingletons) {
            this.L$0 = flowCollector;
            this.label = 2;
            if (flowCollector.emit((String) obj, this) != coroutineSingletons) {
            }
        }
        return coroutineSingletons;
    }
}
