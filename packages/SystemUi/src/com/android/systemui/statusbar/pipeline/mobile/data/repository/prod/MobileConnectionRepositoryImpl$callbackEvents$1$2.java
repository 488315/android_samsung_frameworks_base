package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CallbackEvent;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$callbackEvents$1$2", m277f = "MobileConnectionRepositoryImpl.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class MobileConnectionRepositoryImpl$callbackEvents$1$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public MobileConnectionRepositoryImpl$callbackEvents$1$2(Continuation<? super MobileConnectionRepositoryImpl$callbackEvents$1$2> continuation) {
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
            return TelephonyCallbackState.copy$default(telephonyCallbackState, null, (CallbackEvent.OnCarrierNetworkChange) callbackEvent, null, null, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getDexForegroundModePackageList);
        }
        if (callbackEvent instanceof CallbackEvent.OnDataActivity) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, (CallbackEvent.OnDataActivity) callbackEvent, null, null, null, null, null, null, null, 254);
        }
        if (callbackEvent instanceof CallbackEvent.OnDataConnectionStateChanged) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, null, null, (CallbackEvent.OnDataConnectionStateChanged) callbackEvent, null, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_removeDexURLShortcut);
        }
        if (callbackEvent instanceof CallbackEvent.OnDataEnabledChanged) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, null, null, null, (CallbackEvent.OnDataEnabledChanged) callbackEvent, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_addDexShortcut);
        }
        if (callbackEvent instanceof CallbackEvent.OnDisplayInfoChanged) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, null, null, null, null, (CallbackEvent.OnDisplayInfoChanged) callbackEvent, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteAppsMaxCount);
        }
        if (callbackEvent instanceof CallbackEvent.OnServiceStateChanged) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, null, null, null, null, null, (CallbackEvent.OnServiceStateChanged) callbackEvent, null, null, IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut);
        }
        if (callbackEvent instanceof CallbackEvent.OnSignalStrengthChanged) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, null, null, null, null, null, null, (CallbackEvent.OnSignalStrengthChanged) callbackEvent, null, 191);
        }
        if (callbackEvent instanceof CallbackEvent.OnCallStateChanged) {
            return TelephonyCallbackState.copy$default(telephonyCallbackState, null, null, null, null, null, null, null, (CallbackEvent.OnCallStateChanged) callbackEvent, 127);
        }
        throw new NoWhenBranchMatchedException();
    }
}
