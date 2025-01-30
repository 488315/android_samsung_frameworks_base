package com.android.systemui.log.table;

import android.util.Log;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogcatEchoTracker;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.AbstractChannel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.log.table.TableLogBuffer$init$1", m277f = "TableLogBuffer.kt", m278l = {126}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class TableLogBuffer$init$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ TableLogBuffer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TableLogBuffer$init$1(TableLogBuffer tableLogBuffer, Continuation<? super TableLogBuffer$init$1> continuation) {
        super(2, continuation);
        this.this$0 = tableLogBuffer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TableLogBuffer$init$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TableLogBuffer$init$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0022  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x008a  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:12:0x002c -> B:5:0x002f). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (!this.this$0.logMessageChannel.isClosedForReceive()) {
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            TableChange tableChange = (TableChange) obj;
            TableLogBuffer tableLogBuffer = this.this$0;
            tableLogBuffer.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            LogcatEchoTracker logcatEchoTracker = tableLogBuffer.logcatEchoTracker;
            String str = tableLogBuffer.name;
            if ((!logcatEchoTracker.isBufferLoggable(str, logLevel) || logcatEchoTracker.isTagLoggable(tableChange.columnName, logLevel)) && tableChange.hasData()) {
                String str2 = TableLogBufferKt.TABLE_LOG_DATE_FORMAT.format(Long.valueOf(tableChange.timestamp)) + "|" + tableChange.getName() + "|" + tableChange.getVal();
                ((LogProxyDefault) tableLogBuffer.localLogcat).getClass();
                Log.d(str, str2);
            }
            if (!this.this$0.logMessageChannel.isClosedForReceive()) {
                AbstractChannel abstractChannel = this.this$0.logMessageChannel;
                this.label = 1;
                obj = abstractChannel.receive(this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
                TableChange tableChange2 = (TableChange) obj;
                TableLogBuffer tableLogBuffer2 = this.this$0;
                tableLogBuffer2.getClass();
                LogLevel logLevel2 = LogLevel.DEBUG;
                LogcatEchoTracker logcatEchoTracker2 = tableLogBuffer2.logcatEchoTracker;
                String str3 = tableLogBuffer2.name;
                if (!logcatEchoTracker2.isBufferLoggable(str3, logLevel2)) {
                }
                String str22 = TableLogBufferKt.TABLE_LOG_DATE_FORMAT.format(Long.valueOf(tableChange2.timestamp)) + "|" + tableChange2.getName() + "|" + tableChange2.getVal();
                ((LogProxyDefault) tableLogBuffer2.localLogcat).getClass();
                Log.d(str3, str22);
                if (!this.this$0.logMessageChannel.isClosedForReceive()) {
                    return Unit.INSTANCE;
                }
            }
        }
    }
}
