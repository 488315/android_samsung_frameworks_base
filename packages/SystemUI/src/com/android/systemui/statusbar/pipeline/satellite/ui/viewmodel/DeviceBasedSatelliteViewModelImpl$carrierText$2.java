package com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
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
import kotlin.jvm.functions.Function2;

final class DeviceBasedSatelliteViewModelImpl$carrierText$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ LogBuffer $logBuffer;
    /* synthetic */ Object L$0;
    int label;

    public DeviceBasedSatelliteViewModelImpl$carrierText$2(LogBuffer logBuffer, Continuation continuation) {
        super(2, continuation);
        this.$logBuffer = logBuffer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceBasedSatelliteViewModelImpl$carrierText$2 deviceBasedSatelliteViewModelImpl$carrierText$2 = new DeviceBasedSatelliteViewModelImpl$carrierText$2(this.$logBuffer, continuation);
        deviceBasedSatelliteViewModelImpl$carrierText$2.L$0 = obj;
        return deviceBasedSatelliteViewModelImpl$carrierText$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceBasedSatelliteViewModelImpl$carrierText$2) create((String) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        String str = (String) this.L$0;
        LogBuffer logBuffer = this.$logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceBasedSatelliteViewModel", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModelImpl$carrierText$2.2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Resulting carrier text = ", ((LogMessage) obj2).getStr1());
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
        return Unit.INSTANCE;
    }
}
