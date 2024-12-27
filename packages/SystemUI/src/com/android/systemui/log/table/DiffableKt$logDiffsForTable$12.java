package com.android.systemui.log.table;

import com.android.systemui.plugins.log.TableLogBufferBase;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

final class DiffableKt$logDiffsForTable$12 extends SuspendLambda implements Function3 {
    final /* synthetic */ String $columnName;
    final /* synthetic */ String $columnPrefix;
    final /* synthetic */ TableLogBuffer $tableLogBuffer;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public DiffableKt$logDiffsForTable$12(TableLogBuffer tableLogBuffer, String str, String str2, Continuation continuation) {
        super(3, continuation);
        this.$tableLogBuffer = tableLogBuffer;
        this.$columnPrefix = str;
        this.$columnName = str2;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DiffableKt$logDiffsForTable$12 diffableKt$logDiffsForTable$12 = new DiffableKt$logDiffsForTable$12(this.$tableLogBuffer, this.$columnPrefix, this.$columnName, (Continuation) obj3);
        diffableKt$logDiffsForTable$12.L$0 = (List) obj;
        diffableKt$logDiffsForTable$12.L$1 = (List) obj2;
        return diffableKt$logDiffsForTable$12.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        List list2 = (List) this.L$1;
        if (!Intrinsics.areEqual(list, list2)) {
            TableLogBuffer tableLogBuffer = this.$tableLogBuffer;
            String str = this.$columnPrefix;
            String str2 = this.$columnName;
            String obj2 = list2.toString();
            tableLogBuffer.getClass();
            TableLogBufferBase.DefaultImpls.logChange(tableLogBuffer, str, str2, obj2);
        }
        return list2;
    }
}
