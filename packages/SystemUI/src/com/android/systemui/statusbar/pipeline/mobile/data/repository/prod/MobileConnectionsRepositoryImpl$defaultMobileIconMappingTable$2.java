package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.util.Log;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$$ExternalSyntheticLambda0;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
final class MobileConnectionsRepositoryImpl$defaultMobileIconMappingTable$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileConnectionsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileConnectionsRepositoryImpl$defaultMobileIconMappingTable$2(MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileConnectionsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileConnectionsRepositoryImpl$defaultMobileIconMappingTable$2 mobileConnectionsRepositoryImpl$defaultMobileIconMappingTable$2 = new MobileConnectionsRepositoryImpl$defaultMobileIconMappingTable$2(this.this$0, continuation);
        mobileConnectionsRepositoryImpl$defaultMobileIconMappingTable$2.L$0 = obj;
        return mobileConnectionsRepositoryImpl$defaultMobileIconMappingTable$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileConnectionsRepositoryImpl$defaultMobileIconMappingTable$2) create((ConcurrentHashMap) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) this.L$0;
        MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl = this.this$0;
        for (Map.Entry entry : concurrentHashMap.entrySet()) {
            Log.d("MobileConnectionsRepository", "mobileIconMappingTable(" + entry.getKey() + "): " + entry.getValue() + " ");
            MobileInputLogger mobileInputLogger = mobileConnectionsRepositoryImpl.logger;
            int iIntValue = ((Number) entry.getKey()).intValue();
            Map map = (Map) entry.getValue();
            mobileInputLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(12);
            LogBuffer logBuffer = mobileInputLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.int1 = iIntValue;
            logMessageImpl.str1 = map.toString();
            logBuffer.commit(logMessageObtain);
        }
        return Unit.INSTANCE;
    }
}
