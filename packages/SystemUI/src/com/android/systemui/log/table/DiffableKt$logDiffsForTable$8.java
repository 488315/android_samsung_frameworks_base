package com.android.systemui.log.table;

import com.android.systemui.plugins.log.TableLogBufferBase;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class DiffableKt$logDiffsForTable$8 extends SuspendLambda implements Function3 {
    final /* synthetic */ String $columnName;
    final /* synthetic */ String $columnPrefix;
    final /* synthetic */ TableLogBuffer $tableLogBuffer;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DiffableKt$logDiffsForTable$8(TableLogBuffer tableLogBuffer, String str, String str2, Continuation continuation) {
        super(3, continuation);
        this.$tableLogBuffer = tableLogBuffer;
        this.$columnPrefix = str;
        this.$columnName = str2;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DiffableKt$logDiffsForTable$8 diffableKt$logDiffsForTable$8 = new DiffableKt$logDiffsForTable$8(this.$tableLogBuffer, this.$columnPrefix, this.$columnName, (Continuation) obj3);
        diffableKt$logDiffsForTable$8.L$0 = (Integer) obj;
        diffableKt$logDiffsForTable$8.L$1 = (Integer) obj2;
        return diffableKt$logDiffsForTable$8.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Integer num = (Integer) this.L$0;
        Integer num2 = (Integer) this.L$1;
        if (!Intrinsics.areEqual(num, num2)) {
            TableLogBuffer tableLogBuffer = this.$tableLogBuffer;
            String str = this.$columnPrefix;
            String str2 = this.$columnName;
            tableLogBuffer.getClass();
            TableLogBufferBase.DefaultImpls.logChange(tableLogBuffer, str, str2, num2);
        }
        return num2;
    }
}
