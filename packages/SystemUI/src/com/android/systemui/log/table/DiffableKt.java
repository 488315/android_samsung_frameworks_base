package com.android.systemui.log.table;

import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.util.kotlin.FlowKt;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;

public abstract class DiffableKt {
    public static final Flow logDiffsForTable(Flow flow, final TableLogBuffer tableLogBuffer, final String str, final Diffable diffable) {
        return FlowKt.pairwiseBy(flow, (Function1) new DiffableKt$logDiffsForTable$1(new Function0() { // from class: com.android.systemui.log.table.DiffableKt$logDiffsForTable$getInitialValue$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TableLogBuffer tableLogBuffer2 = TableLogBuffer.this;
                String str2 = str;
                final Diffable diffable2 = diffable;
                Function1 function1 = new Function1() { // from class: com.android.systemui.log.table.DiffableKt$logDiffsForTable$getInitialValue$1.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        Diffable.this.logFull((TableLogBuffer.TableRowLoggerImpl) obj);
                        return Unit.INSTANCE;
                    }
                };
                synchronized (tableLogBuffer2) {
                    TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl = tableLogBuffer2.tempRow;
                    tableRowLoggerImpl.timestamp = tableLogBuffer2.systemClock.currentTimeMillis();
                    tableRowLoggerImpl.columnPrefix = str2;
                    tableRowLoggerImpl.isInitial = true;
                    function1.invoke(tableRowLoggerImpl);
                }
                return diffable;
            }
        }), (Function3) new DiffableKt$logDiffsForTable$2(tableLogBuffer, str, null));
    }

    public static final Flow logDiffsForTable(Flow flow, final TableLogBuffer tableLogBuffer, final String str, final String str2, final boolean z) {
        return FlowKt.pairwiseBy(flow, (Function1) new DiffableKt$logDiffsForTable$3(new Function0() { // from class: com.android.systemui.log.table.DiffableKt$logDiffsForTable$initialValueFun$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TableLogBuffer.this.logChange(str, str2, z, true);
                return Boolean.valueOf(z);
            }
        }), (Function3) new DiffableKt$logDiffsForTable$4(tableLogBuffer, str, str2, null));
    }

    public static final Flow logDiffsForTable(Flow flow, final TableLogBuffer tableLogBuffer, final String str, final String str2, final int i) {
        return FlowKt.pairwiseBy(flow, (Function1) new DiffableKt$logDiffsForTable$5(new Function0() { // from class: com.android.systemui.log.table.DiffableKt$logDiffsForTable$initialValueFun$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TableLogBuffer.this.logChange(str, str2, Integer.valueOf(i), true);
                return Integer.valueOf(i);
            }
        }), (Function3) new DiffableKt$logDiffsForTable$6(tableLogBuffer, str, str2, null));
    }

    public static final Flow logDiffsForTable(Flow flow, final TableLogBuffer tableLogBuffer, final String str, final Integer num) {
        final String str2 = "Repo";
        return FlowKt.pairwiseBy(flow, (Function1) new DiffableKt$logDiffsForTable$7(new Function0() { // from class: com.android.systemui.log.table.DiffableKt$logDiffsForTable$initialValueFun$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TableLogBuffer.this.logChange(str2, str, num, true);
                return num;
            }
        }), (Function3) new DiffableKt$logDiffsForTable$8(tableLogBuffer, "Repo", str, null));
    }

    public static final Flow logDiffsForTable(Flow flow, final TableLogBuffer tableLogBuffer, final String str, final String str2) {
        final String str3 = "";
        return FlowKt.pairwiseBy(flow, (Function1) new DiffableKt$logDiffsForTable$9(new Function0() { // from class: com.android.systemui.log.table.DiffableKt$logDiffsForTable$initialValueFun$4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TableLogBuffer.this.logChange(str3, str, str2, true);
                return str2;
            }
        }), (Function3) new DiffableKt$logDiffsForTable$10(tableLogBuffer, "", str, null));
    }

    public static final Flow logDiffsForTable(Flow flow, final TableLogBuffer tableLogBuffer, final String str, final String str2, final List list) {
        return FlowKt.pairwiseBy(flow, (Function1) new DiffableKt$logDiffsForTable$11(new Function0() { // from class: com.android.systemui.log.table.DiffableKt$logDiffsForTable$initialValueFun$5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TableLogBuffer.this.logChange(str, str2, list.toString(), true);
                return list;
            }
        }), (Function3) new DiffableKt$logDiffsForTable$12(tableLogBuffer, str, str2, null));
    }
}
