package com.android.systemui.qs.tiles.impl.sensorprivacy;

import android.provider.DeviceConfig;
import android.util.Log;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class SensorPrivacyToggleTileDataInteractor$isSensorDeviceConfigSet$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SensorPrivacyToggleTileDataInteractor this$0;

    public SensorPrivacyToggleTileDataInteractor$isSensorDeviceConfigSet$2(SensorPrivacyToggleTileDataInteractor sensorPrivacyToggleTileDataInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sensorPrivacyToggleTileDataInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SensorPrivacyToggleTileDataInteractor$isSensorDeviceConfigSet$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SensorPrivacyToggleTileDataInteractor$isSensorDeviceConfigSet$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String str;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            SensorPrivacyToggleTileDataInteractor sensorPrivacyToggleTileDataInteractor = this.this$0;
            int i = sensorPrivacyToggleTileDataInteractor.sensorId;
            sensorPrivacyToggleTileDataInteractor.getClass();
            if (i == 1) {
                str = "mic_toggle_enabled";
            } else {
                if (i != 2) {
                    throw new IllegalArgumentException("getDeviceConfigName: unexpected sensorId: " + i);
                }
                str = "camera_toggle_enabled";
            }
            return Boolean.valueOf(DeviceConfig.getBoolean("privacy", str, true));
        } catch (IllegalArgumentException e) {
            Log.w("SensorPrivacyToggleTileException", "isDeviceConfigSet for sensorId " + this.this$0.sensorId + ": Defaulting to true due to exception. ", e);
            return Boolean.TRUE;
        }
    }
}
