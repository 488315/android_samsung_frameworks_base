package com.android.systemui.log.table;

import com.android.systemui.log.table.TableLogBuffer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class DiffableKt$logDiffsForTable$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ String $columnPrefix;
    final /* synthetic */ TableLogBuffer $tableLogBuffer;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DiffableKt$logDiffsForTable$2(TableLogBuffer tableLogBuffer, String str, Continuation continuation) {
        super(3, continuation);
        this.$tableLogBuffer = tableLogBuffer;
        this.$columnPrefix = str;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DiffableKt$logDiffsForTable$2 diffableKt$logDiffsForTable$2 = new DiffableKt$logDiffsForTable$2(this.$tableLogBuffer, this.$columnPrefix, (Continuation) obj3);
        diffableKt$logDiffsForTable$2.L$0 = (Diffable) obj;
        diffableKt$logDiffsForTable$2.L$1 = (Diffable) obj2;
        return diffableKt$logDiffsForTable$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Diffable diffable = (Diffable) this.L$0;
        Diffable diffable2 = (Diffable) this.L$1;
        TableLogBuffer tableLogBuffer = this.$tableLogBuffer;
        String str = this.$columnPrefix;
        synchronized (tableLogBuffer) {
            TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl = tableLogBuffer.tempRow;
            tableRowLoggerImpl.timestamp = tableLogBuffer.systemClock.currentTimeMillis();
            tableRowLoggerImpl.columnPrefix = str;
            tableRowLoggerImpl.isInitial = false;
            diffable2.logDiffs(diffable, tableRowLoggerImpl);
        }
        return diffable2;
    }
}
