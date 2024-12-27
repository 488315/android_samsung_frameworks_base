package com.android.systemui.statusbar.pipeline.wifi.data.repository.prod;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
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

final class WifiRepositoryImpl$receivedInetCondition$2 extends SuspendLambda implements Function2 {
    /* synthetic */ int I$0;
    int label;
    final /* synthetic */ WifiRepositoryImpl this$0;

    public WifiRepositoryImpl$receivedInetCondition$2(WifiRepositoryImpl wifiRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = wifiRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WifiRepositoryImpl$receivedInetCondition$2 wifiRepositoryImpl$receivedInetCondition$2 = new WifiRepositoryImpl$receivedInetCondition$2(this.this$0, continuation);
        wifiRepositoryImpl$receivedInetCondition$2.I$0 = ((Number) obj).intValue();
        return wifiRepositoryImpl$receivedInetCondition$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiRepositoryImpl$receivedInetCondition$2) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        WifiRepositoryImpl wifiRepositoryImpl = this.this$0;
        WifiRepositoryImpl.Companion companion = WifiRepositoryImpl.Companion;
        wifiRepositoryImpl.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        WifiRepositoryImpl$logReceivedInetCondition$2 wifiRepositoryImpl$logReceivedInetCondition$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$logReceivedInetCondition$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj2).getInt1(), "ReceivedInetCondition: ");
            }
        };
        LogBuffer logBuffer = wifiRepositoryImpl.inputLogger;
        LogMessage obtain = logBuffer.obtain(wifiRepositoryImpl.TAG$1, logLevel, wifiRepositoryImpl$logReceivedInetCondition$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
        return Unit.INSTANCE;
    }
}
