package com.android.systemui.log.table;

import com.android.systemui.plugins.log.TableLogBufferBase;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class DiffableKt$logDiffsForTable$6 extends SuspendLambda implements Function3 {
    final /* synthetic */ String $columnName;
    final /* synthetic */ String $columnPrefix;
    final /* synthetic */ TableLogBuffer $tableLogBuffer;
    /* synthetic */ int I$0;
    /* synthetic */ int I$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DiffableKt$logDiffsForTable$6(TableLogBuffer tableLogBuffer, String str, String str2, Continuation continuation) {
        super(3, continuation);
        this.$tableLogBuffer = tableLogBuffer;
        this.$columnPrefix = str;
        this.$columnName = str2;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj).intValue();
        int intValue2 = ((Number) obj2).intValue();
        DiffableKt$logDiffsForTable$6 diffableKt$logDiffsForTable$6 = new DiffableKt$logDiffsForTable$6(this.$tableLogBuffer, this.$columnPrefix, this.$columnName, (Continuation) obj3);
        diffableKt$logDiffsForTable$6.I$0 = intValue;
        diffableKt$logDiffsForTable$6.I$1 = intValue2;
        return diffableKt$logDiffsForTable$6.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        int i2 = this.I$1;
        if (i != i2) {
            TableLogBuffer tableLogBuffer = this.$tableLogBuffer;
            String str = this.$columnPrefix;
            String str2 = this.$columnName;
            Integer num = new Integer(i2);
            tableLogBuffer.getClass();
            TableLogBufferBase.DefaultImpls.logChange(tableLogBuffer, str, str2, num);
        }
        return new Integer(i2);
    }
}
