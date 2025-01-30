package com.android.systemui.log.table;

import com.android.systemui.plugins.log.TableLogBufferBase;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.log.table.DiffableKt$logDiffsForTable$4", m277f = "Diffable.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class DiffableKt$logDiffsForTable$4 extends SuspendLambda implements Function3 {
    final /* synthetic */ String $columnName;
    final /* synthetic */ String $columnPrefix;
    final /* synthetic */ TableLogBuffer $tableLogBuffer;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DiffableKt$logDiffsForTable$4(TableLogBuffer tableLogBuffer, String str, String str2, Continuation<? super DiffableKt$logDiffsForTable$4> continuation) {
        super(3, continuation);
        this.$tableLogBuffer = tableLogBuffer;
        this.$columnPrefix = str;
        this.$columnName = str2;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        DiffableKt$logDiffsForTable$4 diffableKt$logDiffsForTable$4 = new DiffableKt$logDiffsForTable$4(this.$tableLogBuffer, this.$columnPrefix, this.$columnName, (Continuation) obj3);
        diffableKt$logDiffsForTable$4.Z$0 = booleanValue;
        diffableKt$logDiffsForTable$4.Z$1 = booleanValue2;
        return diffableKt$logDiffsForTable$4.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        if (z != z2) {
            TableLogBuffer tableLogBuffer = this.$tableLogBuffer;
            String str = this.$columnPrefix;
            String str2 = this.$columnName;
            tableLogBuffer.getClass();
            TableLogBufferBase.DefaultImpls.logChange(tableLogBuffer, str, str2, z2);
        }
        return Boolean.valueOf(z2);
    }
}
