package com.android.systemui.display.data.repository;

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
final class FocusedDisplayRepositoryImpl$focusedTask$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ LogBuffer $logBuffer;
    /* synthetic */ int I$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FocusedDisplayRepositoryImpl$focusedTask$2(LogBuffer logBuffer, Continuation continuation) {
        super(2, continuation);
        this.$logBuffer = logBuffer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FocusedDisplayRepositoryImpl$focusedTask$2 focusedDisplayRepositoryImpl$focusedTask$2 = new FocusedDisplayRepositoryImpl$focusedTask$2(this.$logBuffer, continuation);
        focusedDisplayRepositoryImpl$focusedTask$2.I$0 = ((Number) obj).intValue();
        return focusedDisplayRepositoryImpl$focusedTask$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FocusedDisplayRepositoryImpl$focusedTask$2) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        LogBuffer logBuffer = this.$logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("FocusedDisplayRepository", LogLevel.INFO, new FocusedDisplayRepositoryImpl$focusedTask$2$$ExternalSyntheticLambda0(), null);
        ((LogMessageImpl) logMessageObtain).str1 = String.valueOf(i);
        logBuffer.commit(logMessageObtain);
        return Unit.INSTANCE;
    }
}
