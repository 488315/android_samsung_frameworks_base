package com.android.settingslib.wifi;

import android.net.wifi.WifiManager;
import java.util.function.Consumer;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.ExecutorsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class WifiUtils$Companion$queryWepAllowed$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ WifiManager $this_queryWepAllowed;
    Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiUtils$Companion$queryWepAllowed$2(WifiManager wifiManager, Continuation continuation) {
        super(2, continuation);
        this.$this_queryWepAllowed = wifiManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new WifiUtils$Companion$queryWepAllowed$2(this.$this_queryWepAllowed, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiUtils$Companion$queryWepAllowed$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        WifiManager wifiManager = this.$this_queryWepAllowed;
        this.L$0 = wifiManager;
        this.label = 1;
        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
        cancellableContinuationImpl.initCancellability();
        wifiManager.queryWepAllowed(ExecutorsKt.asExecutor(Dispatchers.Default), new Consumer() { // from class: com.android.settingslib.wifi.WifiUtils$Companion$queryWepAllowed$2$1$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                CancellableContinuation cancellableContinuation = CancellableContinuation.this;
                int i2 = Result.$r8$clinit;
                cancellableContinuation.resumeWith((Boolean) obj2);
            }
        });
        Object result = cancellableContinuationImpl.getResult();
        return result == coroutineSingletons ? coroutineSingletons : result;
    }
}
