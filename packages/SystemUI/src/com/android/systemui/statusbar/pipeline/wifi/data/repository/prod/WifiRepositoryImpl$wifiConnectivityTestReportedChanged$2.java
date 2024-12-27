package com.android.systemui.statusbar.pipeline.wifi.data.repository.prod;

import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class WifiRepositoryImpl$wifiConnectivityTestReportedChanged$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ WifiRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiRepositoryImpl$wifiConnectivityTestReportedChanged$2(WifiRepositoryImpl wifiRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = wifiRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WifiRepositoryImpl$wifiConnectivityTestReportedChanged$2 wifiRepositoryImpl$wifiConnectivityTestReportedChanged$2 = new WifiRepositoryImpl$wifiConnectivityTestReportedChanged$2(this.this$0, continuation);
        wifiRepositoryImpl$wifiConnectivityTestReportedChanged$2.Z$0 = ((Boolean) obj).booleanValue();
        return wifiRepositoryImpl$wifiConnectivityTestReportedChanged$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((WifiRepositoryImpl$wifiConnectivityTestReportedChanged$2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        WifiRepositoryImpl wifiRepositoryImpl = this.this$0;
        WifiRepositoryImpl.Companion companion = WifiRepositoryImpl.Companion;
        wifiRepositoryImpl.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        WifiRepositoryImpl$logTestReported$2 wifiRepositoryImpl$logTestReported$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$logTestReported$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("testReported: ", ((LogMessage) obj2).getBool1());
            }
        };
        LogBuffer logBuffer = wifiRepositoryImpl.inputLogger;
        LogMessage obtain = logBuffer.obtain(wifiRepositoryImpl.TAG$1, logLevel, wifiRepositoryImpl$logTestReported$2, null);
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
        return Unit.INSTANCE;
    }
}
