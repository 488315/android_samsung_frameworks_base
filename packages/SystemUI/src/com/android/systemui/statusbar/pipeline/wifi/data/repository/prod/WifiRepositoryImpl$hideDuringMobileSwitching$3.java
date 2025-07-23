package com.android.systemui.statusbar.pipeline.wifi.data.repository.prod;

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
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class WifiRepositoryImpl$hideDuringMobileSwitching$3 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ WifiRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiRepositoryImpl$hideDuringMobileSwitching$3(WifiRepositoryImpl wifiRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = wifiRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WifiRepositoryImpl$hideDuringMobileSwitching$3 wifiRepositoryImpl$hideDuringMobileSwitching$3 = new WifiRepositoryImpl$hideDuringMobileSwitching$3(this.this$0, continuation);
        wifiRepositoryImpl$hideDuringMobileSwitching$3.Z$0 = ((Boolean) obj).booleanValue();
        return wifiRepositoryImpl$hideDuringMobileSwitching$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((WifiRepositoryImpl$hideDuringMobileSwitching$3) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
        WifiRepositoryImpl$$ExternalSyntheticLambda3 wifiRepositoryImpl$$ExternalSyntheticLambda3 = new WifiRepositoryImpl$$ExternalSyntheticLambda3(1);
        LogBuffer logBuffer = wifiRepositoryImpl.inputLogger;
        LogMessage obtain = logBuffer.obtain("WifiRepo", logLevel, wifiRepositoryImpl$$ExternalSyntheticLambda3, null);
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
        return Unit.INSTANCE;
    }
}
