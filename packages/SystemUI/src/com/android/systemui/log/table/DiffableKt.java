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
import com.android.systemui.plugins.log.TableLogBufferBase;
import com.android.systemui.util.kotlin.FlowKt;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public abstract class DiffableKt {

    /* renamed from: com.android.systemui.log.table.DiffableKt$logDiffsForTable$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1 {
        public AnonymousClass1(Object obj) {
            super(1, obj, Intrinsics.Kotlin.class, "suspendConversion0", "logDiffsForTable$suspendConversion0(Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((Function0) this.receiver).invoke();
        }
    }

    /* renamed from: com.android.systemui.log.table.DiffableKt$logDiffsForTable$10, reason: invalid class name */
    final class AnonymousClass10 extends SuspendLambda implements Function3 {
        final /* synthetic */ String $columnName;
        final /* synthetic */ String $columnPrefix;
        final /* synthetic */ TableLogBuffer $tableLogBuffer;
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass10(TableLogBuffer tableLogBuffer, String str, String str2, Continuation continuation) {
            super(3, continuation);
            this.$tableLogBuffer = tableLogBuffer;
            this.$columnPrefix = str;
            this.$columnName = str2;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass10 anonymousClass10 = new AnonymousClass10(this.$tableLogBuffer, this.$columnPrefix, this.$columnName, (Continuation) obj3);
            anonymousClass10.L$0 = (String) obj;
            anonymousClass10.L$1 = (String) obj2;
            return anonymousClass10.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            String str = (String) this.L$0;
            String str2 = (String) this.L$1;
            if (!Intrinsics.areEqual(str, str2)) {
                TableLogBuffer tableLogBuffer = this.$tableLogBuffer;
                String str3 = this.$columnPrefix;
                String str4 = this.$columnName;
                tableLogBuffer.getClass();
                TableLogBufferBase.DefaultImpls.logChange(tableLogBuffer, str3, str4, str2);
            }
            return str2;
        }
    }

    /* renamed from: com.android.systemui.log.table.DiffableKt$logDiffsForTable$11, reason: invalid class name */
    final /* synthetic */ class AnonymousClass11 extends FunctionReferenceImpl implements Function1 {
        public AnonymousClass11(Object obj) {
            super(1, obj, Intrinsics.Kotlin.class, "suspendConversion0", "logDiffsForTable$suspendConversion0$11(Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((Function0) this.receiver).invoke();
        }
    }

    /* renamed from: com.android.systemui.log.table.DiffableKt$logDiffsForTable$12, reason: invalid class name */
    final class AnonymousClass12 extends SuspendLambda implements Function3 {
        final /* synthetic */ String $columnName;
        final /* synthetic */ String $columnPrefix;
        final /* synthetic */ TableLogBuffer $tableLogBuffer;
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass12(TableLogBuffer tableLogBuffer, String str, String str2, Continuation continuation) {
            super(3, continuation);
            this.$tableLogBuffer = tableLogBuffer;
            this.$columnPrefix = str;
            this.$columnName = str2;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass12 anonymousClass12 = new AnonymousClass12(this.$tableLogBuffer, this.$columnPrefix, this.$columnName, (Continuation) obj3);
            anonymousClass12.L$0 = (List) obj;
            anonymousClass12.L$1 = (List) obj2;
            return anonymousClass12.invokeSuspend(Unit.INSTANCE);
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
                String string = list2.toString();
                tableLogBuffer.getClass();
                TableLogBufferBase.DefaultImpls.logChange(tableLogBuffer, str, str2, string);
            }
            return list2;
        }
    }

    /* renamed from: com.android.systemui.log.table.DiffableKt$logDiffsForTable$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        final /* synthetic */ String $columnPrefix;
        final /* synthetic */ TableLogBuffer $tableLogBuffer;
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(TableLogBuffer tableLogBuffer, String str, Continuation continuation) {
            super(3, continuation);
            this.$tableLogBuffer = tableLogBuffer;
            this.$columnPrefix = str;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$tableLogBuffer, this.$columnPrefix, (Continuation) obj3);
            anonymousClass2.L$0 = (Diffable) obj;
            anonymousClass2.L$1 = (Diffable) obj2;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
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
            this.$tableLogBuffer.logDiffs(this.$columnPrefix, diffable, diffable2);
            return diffable2;
        }
    }

    /* renamed from: com.android.systemui.log.table.DiffableKt$logDiffsForTable$3, reason: invalid class name */
    final /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function1 {
        public AnonymousClass3(Object obj) {
            super(1, obj, Intrinsics.Kotlin.class, "suspendConversion0", "logDiffsForTable$suspendConversion0$3(Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((Function0) this.receiver).invoke();
        }
    }

    /* renamed from: com.android.systemui.log.table.DiffableKt$logDiffsForTable$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function3 {
        final /* synthetic */ String $columnName;
        final /* synthetic */ String $columnPrefix;
        final /* synthetic */ TableLogBuffer $tableLogBuffer;
        /* synthetic */ boolean Z$0;
        /* synthetic */ boolean Z$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(TableLogBuffer tableLogBuffer, String str, String str2, Continuation continuation) {
            super(3, continuation);
            this.$tableLogBuffer = tableLogBuffer;
            this.$columnPrefix = str;
            this.$columnName = str2;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
            AnonymousClass4 anonymousClass4 = new AnonymousClass4(this.$tableLogBuffer, this.$columnPrefix, this.$columnName, (Continuation) obj3);
            anonymousClass4.Z$0 = zBooleanValue;
            anonymousClass4.Z$1 = zBooleanValue2;
            return anonymousClass4.invokeSuspend(Unit.INSTANCE);
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

    /* renamed from: com.android.systemui.log.table.DiffableKt$logDiffsForTable$5, reason: invalid class name */
    final /* synthetic */ class AnonymousClass5 extends FunctionReferenceImpl implements Function1 {
        public AnonymousClass5(Object obj) {
            super(1, obj, Intrinsics.Kotlin.class, "suspendConversion0", "logDiffsForTable$suspendConversion0$5(Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((Function0) this.receiver).invoke();
        }
    }

    /* renamed from: com.android.systemui.log.table.DiffableKt$logDiffsForTable$6, reason: invalid class name */
    final class AnonymousClass6 extends SuspendLambda implements Function3 {
        final /* synthetic */ String $columnName;
        final /* synthetic */ String $columnPrefix;
        final /* synthetic */ TableLogBuffer $tableLogBuffer;
        /* synthetic */ int I$0;
        /* synthetic */ int I$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass6(TableLogBuffer tableLogBuffer, String str, String str2, Continuation continuation) {
            super(3, continuation);
            this.$tableLogBuffer = tableLogBuffer;
            this.$columnPrefix = str;
            this.$columnName = str2;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            int iIntValue = ((Number) obj).intValue();
            int iIntValue2 = ((Number) obj2).intValue();
            AnonymousClass6 anonymousClass6 = new AnonymousClass6(this.$tableLogBuffer, this.$columnPrefix, this.$columnName, (Continuation) obj3);
            anonymousClass6.I$0 = iIntValue;
            anonymousClass6.I$1 = iIntValue2;
            return anonymousClass6.invokeSuspend(Unit.INSTANCE);
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

    /* renamed from: com.android.systemui.log.table.DiffableKt$logDiffsForTable$7, reason: invalid class name */
    final /* synthetic */ class AnonymousClass7 extends FunctionReferenceImpl implements Function1 {
        public AnonymousClass7(Object obj) {
            super(1, obj, Intrinsics.Kotlin.class, "suspendConversion0", "logDiffsForTable$suspendConversion0$7(Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((Function0) this.receiver).invoke();
        }
    }

    /* renamed from: com.android.systemui.log.table.DiffableKt$logDiffsForTable$8, reason: invalid class name */
    final class AnonymousClass8 extends SuspendLambda implements Function3 {
        final /* synthetic */ String $columnName;
        final /* synthetic */ String $columnPrefix;
        final /* synthetic */ TableLogBuffer $tableLogBuffer;
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass8(TableLogBuffer tableLogBuffer, String str, String str2, Continuation continuation) {
            super(3, continuation);
            this.$tableLogBuffer = tableLogBuffer;
            this.$columnPrefix = str;
            this.$columnName = str2;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass8 anonymousClass8 = new AnonymousClass8(this.$tableLogBuffer, this.$columnPrefix, this.$columnName, (Continuation) obj3);
            anonymousClass8.L$0 = (Integer) obj;
            anonymousClass8.L$1 = (Integer) obj2;
            return anonymousClass8.invokeSuspend(Unit.INSTANCE);
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

    /* renamed from: com.android.systemui.log.table.DiffableKt$logDiffsForTable$9, reason: invalid class name */
    final /* synthetic */ class AnonymousClass9 extends FunctionReferenceImpl implements Function1 {
        public AnonymousClass9(Object obj) {
            super(1, obj, Intrinsics.Kotlin.class, "suspendConversion0", "logDiffsForTable$suspendConversion0$9(Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return ((Function0) this.receiver).invoke();
        }
    }

    public static final void logBooleanDiffsForTable(BuildScope buildScope, State state, TableLogBuffer tableLogBuffer, String str, String str2) {
        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        ref$BooleanRef.element = true;
        ((BuildScopeImpl) buildScope).observe(state, new DiffableKt$$ExternalSyntheticLambda5(tableLogBuffer, str, str2, ref$BooleanRef, 0));
    }

    public static final Flow logDiffsForTable(Flow flow, final TableLogBuffer tableLogBuffer, final String str, final Diffable diffable) {
        return FlowKt.pairwiseBy(flow, (Function1) new AnonymousClass1(new Function0() { // from class: com.android.systemui.log.table.DiffableKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Diffable diffable2 = diffable;
                TableLogBuffer tableLogBuffer2 = tableLogBuffer;
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
        }), (Function3) new AnonymousClass2(tableLogBuffer, str, null));
    }

    public static final void logIntDiffsForTable(BuildScope buildScope, State state, TableLogBuffer tableLogBuffer, String str, String str2) {
        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        ref$BooleanRef.element = true;
        ((BuildScopeImpl) buildScope).observe(state, new DiffableKt$$ExternalSyntheticLambda5(tableLogBuffer, str, str2, ref$BooleanRef, 1));
    }

    public static final Flow logDiffsForTable(Flow flow, final TableLogBuffer tableLogBuffer, final String str, final String str2, final boolean z) {
        return FlowKt.pairwiseBy(flow, (Function1) new AnonymousClass3(new Function0() { // from class: com.android.systemui.log.table.DiffableKt$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TableLogBuffer tableLogBuffer2 = tableLogBuffer;
                String str3 = str;
                String str4 = str2;
                boolean z2 = z;
                tableLogBuffer2.logChange(str3, str4, z2, true);
                return Boolean.valueOf(z2);
            }
        }), (Function3) new AnonymousClass4(tableLogBuffer, str, str2, null));
    }

    public static final Flow logDiffsForTable(Flow flow, final TableLogBuffer tableLogBuffer, final String str, final String str2, final int i) {
        return FlowKt.pairwiseBy(flow, (Function1) new AnonymousClass5(new Function0() { // from class: com.android.systemui.log.table.DiffableKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i2 = i;
                tableLogBuffer.logChange(str, str2, Integer.valueOf(i2), true);
                return Integer.valueOf(i2);
            }
        }), (Function3) new AnonymousClass6(tableLogBuffer, str, str2, null));
    }

    public static final Flow logDiffsForTable(Flow flow, final TableLogBuffer tableLogBuffer, final String str) {
        return FlowKt.pairwiseBy(flow, (Function1) new AnonymousClass7(new Function0() { // from class: com.android.systemui.log.table.DiffableKt$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                tableLogBuffer.logChange("Repo", str, (Integer) null, true);
                return null;
            }
        }), (Function3) new AnonymousClass8(tableLogBuffer, "Repo", str, null));
    }

    public static final Flow logDiffsForTable(Flow flow, TableLogBuffer tableLogBuffer, String str, String str2, String str3) {
        return FlowKt.pairwiseBy(flow, (Function1) new AnonymousClass9(new DiffableKt$$ExternalSyntheticLambda2(tableLogBuffer, str, str2, str3)), (Function3) new AnonymousClass10(tableLogBuffer, str, str2, null));
    }

    public static final Flow logDiffsForTable(Flow flow, TableLogBuffer tableLogBuffer, String str, String str2, EmptyList emptyList) {
        return FlowKt.pairwiseBy(flow, (Function1) new AnonymousClass11(new DiffableKt$$ExternalSyntheticLambda2(tableLogBuffer, str, str2, emptyList)), (Function3) new AnonymousClass12(tableLogBuffer, str, str2, null));
    }

    public static final void logDiffsForTable(BuildScope buildScope, final State state, final TableLogBuffer tableLogBuffer, final String str) {
        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
        final DeferredValue deferredValueSampleDeferred = buildScopeImpl.sampleDeferred(state);
        BuildScopeKt.effect$default(buildScopeImpl, new Function1() { // from class: com.android.systemui.log.table.DiffableKt$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                DeferredValue deferredValue = deferredValueSampleDeferred;
                TableLogBuffer tableLogBuffer2 = tableLogBuffer;
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
                Diffable diffable = (Diffable) ((BuildScopeImpl$observe$outputNode$2$scope$1) ((EffectScope) obj)).$$delegate_0.sample(state);
                tableLogBuffer.logDiffs(str, diffable, (Diffable) obj2);
                return Unit.INSTANCE;
            }
        }, 1);
    }
}
