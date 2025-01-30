package com.android.systemui.statusbar.pipeline.wifi.data.repository.prod;

import com.android.systemui.statusbar.pipeline.wifi.shared.WifiInputLogger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$wifiStateChangeEvents$1", m277f = "WifiRepositoryImpl.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class WifiRepositoryImpl$wifiStateChangeEvents$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ WifiInputLogger $logger;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiRepositoryImpl$wifiStateChangeEvents$1(WifiInputLogger wifiInputLogger, Continuation<? super WifiRepositoryImpl$wifiStateChangeEvents$1> continuation) {
        super(2, continuation);
        this.$logger = wifiInputLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new WifiRepositoryImpl$wifiStateChangeEvents$1(this.$logger, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiRepositoryImpl$wifiStateChangeEvents$1) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.$logger.logIntent();
        return Unit.INSTANCE;
    }
}
