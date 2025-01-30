package com.android.systemui.statusbar.pipeline.satellite.p027ui.viewmodel;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.time.Duration;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$2", m277f = "DeviceBasedSatelliteViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_addWidget, 190}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class DeviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ LogBuffer $logBuffer$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$2(Continuation continuation, LogBuffer logBuffer) {
        super(3, continuation);
        this.$logBuffer$inlined = logBuffer;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$2 deviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$2 = new DeviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$2((Continuation) obj3, this.$logBuffer$inlined);
        deviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        deviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return deviceBasedSatelliteViewModel$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0075 A[RETURN] */
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
            if (!((Boolean) this.L$1).booleanValue()) {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
                this.L$0 = null;
                this.label = 2;
                if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
            LogMessage obtain = this.$logBuffer$inlined.obtain("DeviceBasedSatelliteViewModel", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModel$shouldActuallyShowIcon$1$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    return "Waiting " + ((LogMessage) obj2).getLong1() + " seconds before showing the satellite icon";
                }
            }, null);
            long j = DeviceBasedSatelliteViewModel.DELAY_DURATION;
            Duration.Companion companion = Duration.Companion;
            obtain.setLong1(Duration.m2866toLongimpl(j, DurationUnit.SECONDS));
            this.$logBuffer$inlined.commit(obtain);
            this.L$0 = flowCollector;
            this.label = 1;
            if (DelayKt.m2868delayVtjQ1oo(j, this) == coroutineSingletons) {
                return coroutineSingletons;
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
        if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
        }
        return Unit.INSTANCE;
    }
}
