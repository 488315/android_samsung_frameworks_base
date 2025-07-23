package com.android.systemui.log.table;

import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.BuildScopeKt;
import com.android.systemui.kairos.DeferredValue;
import com.android.systemui.kairos.EffectScope;
import com.android.systemui.kairos.State;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import com.android.systemui.kairos.internal.BuildScopeImpl$observe$outputNode$2$scope$1;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class DiffableKt {
    public static final void logBooleanDiffsForTable(BuildScope buildScope, State state, TableLogBuffer tableLogBuffer, String str, String str2) {
        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        ref$BooleanRef.element = true;
        ((BuildScopeImpl) buildScope).observe(state, new DiffableKt$$ExternalSyntheticLambda5(tableLogBuffer, str, str2, ref$BooleanRef, 0));
    }

    public static final Flow logDiffsForTable(Flow flow, final TableLogBuffer tableLogBuffer, final String str, final Diffable diffable) {
        return FlowKt.pairwiseBy(flow, (Function1) new DiffableKt$logDiffsForTable$1(new Function0() { // from class: com.android.systemui.log.table.DiffableKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Diffable diffable2 = diffable;
                TableLogBuffer tableLogBuffer2 = TableLogBuffer.this;
                String str2 = str;
                synchronized (tableLogBuffer2) {
                    TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl = tableLogBuffer2.tempRow;
                    tableRowLoggerImpl.timestamp = tableLogBuffer2.systemClock.currentTimeMillis();
                    tableRowLoggerImpl.columnPrefix = str2;
                    tableRowLoggerImpl.isInitial = true;
                    diffable2.logFull(tableRowLoggerImpl);
                    Unit unit = Unit.INSTANCE;
                }
                return diffable2;
            }
        }), (Function3) new DiffableKt$logDiffsForTable$2(tableLogBuffer, str, null));
    }

    public static final void logIntDiffsForTable(BuildScope buildScope, State state, TableLogBuffer tableLogBuffer, String str, String str2) {
        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        ref$BooleanRef.element = true;
        ((BuildScopeImpl) buildScope).observe(state, new DiffableKt$$ExternalSyntheticLambda5(tableLogBuffer, str, str2, ref$BooleanRef, 1));
    }

    public static final Flow logDiffsForTable(Flow flow, final TableLogBuffer tableLogBuffer, final String str, final String str2, final boolean z) {
        return FlowKt.pairwiseBy(flow, (Function1) new DiffableKt$logDiffsForTable$3(new Function0() { // from class: com.android.systemui.log.table.DiffableKt$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TableLogBuffer tableLogBuffer2 = TableLogBuffer.this;
                String str3 = str;
                String str4 = str2;
                boolean z2 = z;
                tableLogBuffer2.logChange(str3, str4, z2, true);
                return Boolean.valueOf(z2);
            }
        }), (Function3) new DiffableKt$logDiffsForTable$4(tableLogBuffer, str, str2, null));
    }

    public static final Flow logDiffsForTable(Flow flow, final TableLogBuffer tableLogBuffer, final String str, final String str2, final int i) {
        return FlowKt.pairwiseBy(flow, (Function1) new DiffableKt$logDiffsForTable$5(new Function0() { // from class: com.android.systemui.log.table.DiffableKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i2 = i;
                TableLogBuffer.this.logChange(str, str2, Integer.valueOf(i2), true);
                return Integer.valueOf(i2);
            }
        }), (Function3) new DiffableKt$logDiffsForTable$6(tableLogBuffer, str, str2, null));
    }

    public static final Flow logDiffsForTable(Flow flow, final TableLogBuffer tableLogBuffer, final String str) {
        return FlowKt.pairwiseBy(flow, (Function1) new DiffableKt$logDiffsForTable$7(new Function0() { // from class: com.android.systemui.log.table.DiffableKt$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TableLogBuffer.this.logChange("Repo", str, (Integer) null, true);
                return null;
            }
        }), (Function3) new DiffableKt$logDiffsForTable$8(tableLogBuffer, "Repo", str, null));
    }

    public static final Flow logDiffsForTable(Flow flow, TableLogBuffer tableLogBuffer, String str, String str2, String str3) {
        return FlowKt.pairwiseBy(flow, (Function1) new DiffableKt$logDiffsForTable$9(new DiffableKt$$ExternalSyntheticLambda2(tableLogBuffer, str, str2, str3)), (Function3) new DiffableKt$logDiffsForTable$10(tableLogBuffer, str, str2, null));
    }

    public static final Flow logDiffsForTable(Flow flow, TableLogBuffer tableLogBuffer, String str, String str2, EmptyList emptyList) {
        return FlowKt.pairwiseBy(flow, (Function1) new DiffableKt$logDiffsForTable$11(new DiffableKt$$ExternalSyntheticLambda2(tableLogBuffer, str, str2, emptyList)), (Function3) new DiffableKt$logDiffsForTable$12(tableLogBuffer, str, str2, null));
    }

    public static final void logDiffsForTable(BuildScope buildScope, final State state, final TableLogBuffer tableLogBuffer, final String str) {
        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
        final DeferredValue sampleDeferred = buildScopeImpl.sampleDeferred(state);
        BuildScopeKt.effect$default(buildScopeImpl, new Function1() { // from class: com.android.systemui.log.table.DiffableKt$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                DeferredValue deferredValue = sampleDeferred;
                TableLogBuffer tableLogBuffer2 = TableLogBuffer.this;
                String str2 = str;
                synchronized (tableLogBuffer2) {
                    TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl = tableLogBuffer2.tempRow;
                    tableRowLoggerImpl.timestamp = tableLogBuffer2.systemClock.currentTimeMillis();
                    tableRowLoggerImpl.columnPrefix = str2;
                    tableRowLoggerImpl.isInitial = true;
                    ((Diffable) deferredValue.unwrapped.getValue()).logFull(tableRowLoggerImpl);
                    Unit unit = Unit.INSTANCE;
                }
                return Unit.INSTANCE;
            }
        });
        BuildScope.DefaultImpls.observe$default(buildScopeImpl, StateKt.getChanges(state), new Function2() { // from class: com.android.systemui.log.table.DiffableKt$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Diffable diffable = (Diffable) ((BuildScopeImpl$observe$outputNode$2$scope$1) ((EffectScope) obj)).$$delegate_0.sample(State.this);
                tableLogBuffer.logDiffs(str, diffable, (Diffable) obj2);
                return Unit.INSTANCE;
            }
        }, 1);
    }
}
