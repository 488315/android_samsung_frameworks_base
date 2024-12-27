package com.android.systemui.uithreadmonitor;

import com.android.systemui.util.LogUtil;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class LooperSlowLogControllerImpl$updateSlowDispatchOnChoreographer$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $csLog;
    final /* synthetic */ long $curTime;
    final /* synthetic */ String $slowLog;
    int label;
    final /* synthetic */ LooperSlowLogControllerImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LooperSlowLogControllerImpl$updateSlowDispatchOnChoreographer$2(LooperSlowLogControllerImpl looperSlowLogControllerImpl, long j, String str, String str2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = looperSlowLogControllerImpl;
        this.$curTime = j;
        this.$slowLog = str;
        this.$csLog = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LooperSlowLogControllerImpl$updateSlowDispatchOnChoreographer$2(this.this$0, this.$curTime, this.$slowLog, this.$csLog, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LooperSlowLogControllerImpl$updateSlowDispatchOnChoreographer$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        LooperSlowLogControllerImpl looperSlowLogControllerImpl = this.this$0;
        long j = this.$curTime;
        String str = this.$slowLog;
        String str2 = this.$csLog;
        int i = LooperSlowLogControllerImpl.$r8$clinit;
        synchronized (((ArrayDeque) looperSlowLogControllerImpl.dumpBuf$delegate.getValue())) {
            try {
                ArrayDeque arrayDeque = (ArrayDeque) looperSlowLogControllerImpl.dumpBuf$delegate.getValue();
                if (arrayDeque.size == 101) {
                    arrayDeque.removeFirst();
                }
                arrayDeque.addLast(LogUtil.makeTimeStr(j) + " " + str + str2);
            } catch (Throwable th) {
                throw th;
            }
        }
        return Unit.INSTANCE;
    }
}
