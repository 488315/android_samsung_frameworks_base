package com.android.systemui.keyboard.stickykeys.data.repository;

import com.android.systemui.keyboard.stickykeys.StickyKeysLogger;
import com.android.systemui.keyboard.stickykeys.StickyKeysLogger$$ExternalSyntheticLambda0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
final class StickyKeysRepositoryImpl$settingEnabled$1 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ StickyKeysRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StickyKeysRepositoryImpl$settingEnabled$1(StickyKeysRepositoryImpl stickyKeysRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = stickyKeysRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        StickyKeysRepositoryImpl$settingEnabled$1 stickyKeysRepositoryImpl$settingEnabled$1 = new StickyKeysRepositoryImpl$settingEnabled$1(this.this$0, continuation);
        stickyKeysRepositoryImpl$settingEnabled$1.Z$0 = ((Boolean) obj).booleanValue();
        return stickyKeysRepositoryImpl$settingEnabled$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((StickyKeysRepositoryImpl$settingEnabled$1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        StickyKeysLogger stickyKeysLogger = this.this$0.stickyKeysLogger;
        stickyKeysLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        StickyKeysLogger$$ExternalSyntheticLambda0 stickyKeysLogger$$ExternalSyntheticLambda0 = new StickyKeysLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = stickyKeysLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("stickyKeys", logLevel, stickyKeysLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).bool1 = z;
        logBuffer.commit(logMessageObtain);
        return Unit.INSTANCE;
    }
}
