package com.android.settingslib.satellite;

import android.telephony.satellite.SatelliteManager;
import android.telephony.satellite.SatelliteModemStateCallback;
import android.util.Log;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes.dex */
final class SatelliteDialogUtils$getIsSessionStartedFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SatelliteManager $satelliteManager;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SatelliteDialogUtils$getIsSessionStartedFlow$1(SatelliteManager satelliteManager, Continuation continuation) {
        super(2, continuation);
        this.$satelliteManager = satelliteManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SatelliteDialogUtils$getIsSessionStartedFlow$1 satelliteDialogUtils$getIsSessionStartedFlow$1 = new SatelliteDialogUtils$getIsSessionStartedFlow$1(this.$satelliteManager, continuation);
        satelliteDialogUtils$getIsSessionStartedFlow$1.L$0 = obj;
        return satelliteDialogUtils$getIsSessionStartedFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SatelliteDialogUtils$getIsSessionStartedFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.telephony.satellite.SatelliteModemStateCallback, com.android.settingslib.satellite.SatelliteDialogUtils$getIsSessionStartedFlow$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int iRegisterForModemStateChanged;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r2 = new SatelliteModemStateCallback() { // from class: com.android.settingslib.satellite.SatelliteDialogUtils$getIsSessionStartedFlow$1$callback$1
                public final void onSatelliteModemStateChanged(int i2) {
                    SatelliteDialogUtils.INSTANCE.getClass();
                    boolean z = (i2 == -1 || i2 == 4 || i2 == 5) ? false : true;
                    Log.i("SatelliteDialogUtils", "Satellite modem state changed: state=" + i2 + ", isSessionStarted=" + z);
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.valueOf(z));
                }
            };
            try {
                iRegisterForModemStateChanged = this.$satelliteManager.registerForModemStateChanged(ExecutorsKt.asExecutor(Dispatchers.Default), (SatelliteModemStateCallback) r2);
            } catch (IllegalStateException e) {
                Log.w("SatelliteDialogUtils", "IllegalStateException: " + e);
                iRegisterForModemStateChanged = -1;
            }
            if (iRegisterForModemStateChanged != 0) {
                RecordingInputConnection$$ExternalSyntheticOutline0.m(iRegisterForModemStateChanged, "Failed to register for satellite modem state change: ", "SatelliteDialogUtils");
                ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.FALSE);
            }
            final SatelliteManager satelliteManager = this.$satelliteManager;
            Function0 function0 = new Function0() { // from class: com.android.settingslib.satellite.SatelliteDialogUtils$getIsSessionStartedFlow$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    try {
                        satelliteManager.unregisterForModemStateChanged(r2);
                    } catch (IllegalStateException e2) {
                        Log.w("SatelliteDialogUtils", "IllegalStateException: " + e2);
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
