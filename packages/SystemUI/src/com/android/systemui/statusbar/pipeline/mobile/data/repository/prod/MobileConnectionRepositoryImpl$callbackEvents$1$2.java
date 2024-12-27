package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CallbackEvent;
import com.samsung.android.knox.custom.CustomDeviceManager;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class MobileConnectionRepositoryImpl$callbackEvents$1$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public MobileConnectionRepositoryImpl$callbackEvents$1$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MobileConnectionRepositoryImpl$callbackEvents$1$2 mobileConnectionRepositoryImpl$callbackEvents$1$2 = new MobileConnectionRepositoryImpl$callbackEvents$1$2((Continuation) obj3);
        mobileConnectionRepositoryImpl$callbackEvents$1$2.L$0 = (TelephonyCallbackState) obj;
        mobileConnectionRepositoryImpl$callbackEvents$1$2.L$1 = (CallbackEvent) obj2;
        return mobileConnectionRepositoryImpl$callbackEvents$1$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        TelephonyCallbackState telephonyCallbackState = (TelephonyCallbackState) this.L$0;
        CallbackEvent callbackEvent = (CallbackEvent) this.L$1;
        telephonyCallbackState.getClass();
        if (callbackEvent instanceof CallbackEvent.OnCarrierNetworkChange) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, null, (CallbackEvent.OnCarrierNetworkChange) callbackEvent, null, null, null, null, null, null, null, null, null, null, 4093);
        }
        if (callbackEvent instanceof CallbackEvent.OnCarrierRoamingNtnModeChanged) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, null, null, (CallbackEvent.OnCarrierRoamingNtnModeChanged) callbackEvent, null, null, null, null, null, null, null, null, null, 4091);
        }
        if (callbackEvent instanceof CallbackEvent.OnDataActivity) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, (CallbackEvent.OnDataActivity) callbackEvent, null, null, null, null, null, null, null, null, null, null, null, 4094);
        }
        if (callbackEvent instanceof CallbackEvent.OnDataConnectionStateChanged) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, null, null, null, (CallbackEvent.OnDataConnectionStateChanged) callbackEvent, null, null, null, null, null, null, null, null, 4087);
        }
        if (callbackEvent instanceof CallbackEvent.OnDataEnabledChanged) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, null, null, null, null, (CallbackEvent.OnDataEnabledChanged) callbackEvent, null, null, null, null, null, null, null, 4079);
        }
        if (callbackEvent instanceof CallbackEvent.OnDisplayInfoChanged) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, null, null, null, null, null, (CallbackEvent.OnDisplayInfoChanged) callbackEvent, null, null, null, null, null, null, 4063);
        }
        if (callbackEvent instanceof CallbackEvent.OnServiceStateChanged) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, null, null, null, null, null, null, (CallbackEvent.OnServiceStateChanged) callbackEvent, null, null, null, null, null, 4031);
        }
        if (callbackEvent instanceof CallbackEvent.OnSignalStrengthChanged) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, null, null, null, null, null, null, null, (CallbackEvent.OnSignalStrengthChanged) callbackEvent, null, null, null, null, 3967);
        }
        if (callbackEvent instanceof CallbackEvent.OnCarrierRoamingNtnSignalStrengthChanged) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, null, null, null, null, null, null, null, null, (CallbackEvent.OnCarrierRoamingNtnSignalStrengthChanged) callbackEvent, null, null, null, 3839);
        }
        if (callbackEvent instanceof CallbackEvent.OnCallStateChanged) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, null, null, null, null, null, null, null, null, null, (CallbackEvent.OnCallStateChanged) callbackEvent, null, null, 3583);
        }
        if (callbackEvent instanceof CallbackEvent.onSemSatelliteServiceStateChanged) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, null, null, null, null, null, null, null, null, null, null, (CallbackEvent.onSemSatelliteServiceStateChanged) callbackEvent, null, 3071);
        }
        if (callbackEvent instanceof CallbackEvent.onSemSatelliteSignalStrengthChanged) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, null, null, null, null, null, null, null, null, null, null, null, (CallbackEvent.onSemSatelliteSignalStrengthChanged) callbackEvent, CustomDeviceManager.SETTINGS_ALL_PREVIOUS);
        }
        throw new NoWhenBranchMatchedException();
    }
}
