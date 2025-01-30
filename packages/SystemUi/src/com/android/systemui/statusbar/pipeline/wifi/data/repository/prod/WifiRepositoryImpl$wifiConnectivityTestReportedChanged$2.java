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
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$wifiConnectivityTestReportedChanged$2", m277f = "WifiRepositoryImpl.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class WifiRepositoryImpl$wifiConnectivityTestReportedChanged$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ WifiInputLogger $logger;
    /* synthetic */ boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiRepositoryImpl$wifiConnectivityTestReportedChanged$2(WifiInputLogger wifiInputLogger, Continuation<? super WifiRepositoryImpl$wifiConnectivityTestReportedChanged$2> continuation) {
        super(2, continuation);
        this.$logger = wifiInputLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WifiRepositoryImpl$wifiConnectivityTestReportedChanged$2 wifiRepositoryImpl$wifiConnectivityTestReportedChanged$2 = new WifiRepositoryImpl$wifiConnectivityTestReportedChanged$2(this.$logger, continuation);
        wifiRepositoryImpl$wifiConnectivityTestReportedChanged$2.Z$0 = ((Boolean) obj).booleanValue();
        return wifiRepositoryImpl$wifiConnectivityTestReportedChanged$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiRepositoryImpl$wifiConnectivityTestReportedChanged$2) create(Boolean.valueOf(((Boolean) obj).booleanValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.$logger.logTestReported(this.Z$0);
        return Unit.INSTANCE;
    }
}
