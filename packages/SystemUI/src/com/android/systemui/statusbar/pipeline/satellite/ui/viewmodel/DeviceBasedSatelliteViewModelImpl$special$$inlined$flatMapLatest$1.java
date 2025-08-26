package com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.time.Duration;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes3.dex */
public final class DeviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ LogBuffer $logBuffer$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1(Continuation continuation, LogBuffer logBuffer) {
        super(3, continuation);
        this.$logBuffer$inlined = logBuffer;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1 deviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1 = new DeviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$logBuffer$inlined);
        deviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        deviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return deviceBasedSatelliteViewModelImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x005b, code lost:
    
        if (kotlinx.coroutines.DelayKt.m3469delayVtjQ1oo(r5, r10) == r0) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0075, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.emitAll(r1, r4, r10) != r0) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0077, code lost:
    
        return r0;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                LogMessage logMessageObtain = this.$logBuffer$inlined.obtain("DeviceBasedSatelliteViewModel", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModelImpl$shouldShowIconForOosAfterHysteresis$1$2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        return "Waiting " + ((LogMessage) obj2).getLong1() + " seconds before showing the satellite icon";
                    }
                }, null);
                long j = DeviceBasedSatelliteViewModelImpl.DELAY_DURATION;
                Duration.Companion companion = Duration.Companion;
                ((LogMessageImpl) logMessageObtain).long1 = Duration.m3465toLongimpl(j, DurationUnit.SECONDS);
                this.$logBuffer$inlined.commit(logMessageObtain);
                this.L$0 = flowCollector;
                this.label = 1;
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
                this.L$0 = null;
                this.label = 2;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
        this.L$0 = null;
        this.label = 2;
    }
}
