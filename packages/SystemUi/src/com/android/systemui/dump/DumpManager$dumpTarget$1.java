package com.android.systemui.dump;

import java.io.PrintWriter;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.dump.DumpManager$dumpTarget$1", m277f = "DumpManager.kt", m278l = {169, 172, 176}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class DumpManager$dumpTarget$1 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ String[] $args;
    final /* synthetic */ PrintWriter $pw;
    final /* synthetic */ int $tailLength;
    final /* synthetic */ String $target;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DumpManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DumpManager$dumpTarget$1(DumpManager dumpManager, String str, PrintWriter printWriter, String[] strArr, int i, Continuation<? super DumpManager$dumpTarget$1> continuation) {
        super(2, continuation);
        this.this$0 = dumpManager;
        this.$target = str;
        this.$pw = printWriter;
        this.$args = strArr;
        this.$tailLength = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DumpManager$dumpTarget$1 dumpManager$dumpTarget$1 = new DumpManager$dumpTarget$1(this.this$0, this.$target, this.$pw, this.$args, this.$tailLength, continuation);
        dumpManager$dumpTarget$1.L$0 = obj;
        return dumpManager$dumpTarget$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DumpManager$dumpTarget$1) create((SequenceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0098  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        SequenceScope sequenceScope;
        final RegisteredDumpable registeredDumpable;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SequenceScope sequenceScope2 = (SequenceScope) this.L$0;
            DumpManager dumpManager = this.this$0;
            final RegisteredDumpable registeredDumpable2 = (RegisteredDumpable) DumpManager.access$findBestTargetMatch(dumpManager, dumpManager.dumpables, this.$target);
            if (registeredDumpable2 != null) {
                final DumpManager dumpManager2 = this.this$0;
                final PrintWriter printWriter = this.$pw;
                final String[] strArr = this.$args;
                Pair pair = new Pair(registeredDumpable2.name, new Function0() { // from class: com.android.systemui.dump.DumpManager$dumpTarget$1$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        DumpManager dumpManager3 = DumpManager.this;
                        RegisteredDumpable registeredDumpable3 = registeredDumpable2;
                        PrintWriter printWriter2 = printWriter;
                        String[] strArr2 = strArr;
                        dumpManager3.getClass();
                        DumpManager.dumpDumpable(registeredDumpable3, printWriter2, strArr2);
                        return Unit.INSTANCE;
                    }
                });
                this.L$0 = sequenceScope2;
                this.label = 1;
                if (sequenceScope2.yield(pair, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            sequenceScope = sequenceScope2;
        } else {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                sequenceScope = (SequenceScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                DumpManager dumpManager3 = this.this$0;
                registeredDumpable = (RegisteredDumpable) DumpManager.access$findBestTargetMatch(dumpManager3, dumpManager3.nsDumpables, this.$target);
                if (registeredDumpable != null) {
                    final DumpManager dumpManager4 = this.this$0;
                    final PrintWriter printWriter2 = this.$pw;
                    final String[] strArr2 = this.$args;
                    Pair pair2 = new Pair(registeredDumpable.name, new Function0() { // from class: com.android.systemui.dump.DumpManager$dumpTarget$1$3$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            DumpManager dumpManager5 = DumpManager.this;
                            RegisteredDumpable registeredDumpable3 = registeredDumpable;
                            PrintWriter printWriter3 = printWriter2;
                            String[] strArr3 = strArr2;
                            dumpManager5.getClass();
                            DumpManager.dumpDumpable(registeredDumpable3, printWriter3, strArr3);
                            return Unit.INSTANCE;
                        }
                    });
                    this.L$0 = null;
                    this.label = 3;
                    if (sequenceScope.yield(pair2, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
                return Unit.INSTANCE;
            }
            sequenceScope = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        DumpManager dumpManager5 = this.this$0;
        final RegisteredDumpable registeredDumpable3 = (RegisteredDumpable) DumpManager.access$findBestTargetMatch(dumpManager5, dumpManager5.buffers, this.$target);
        if (registeredDumpable3 != null) {
            final DumpManager dumpManager6 = this.this$0;
            final PrintWriter printWriter3 = this.$pw;
            final int i2 = this.$tailLength;
            Pair pair3 = new Pair(registeredDumpable3.name, new Function0() { // from class: com.android.systemui.dump.DumpManager$dumpTarget$1$2$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DumpManager dumpManager7 = DumpManager.this;
                    RegisteredDumpable registeredDumpable4 = registeredDumpable3;
                    PrintWriter printWriter4 = printWriter3;
                    int i3 = i2;
                    dumpManager7.getClass();
                    DumpManager.dumpBuffer(registeredDumpable4, printWriter4, i3);
                    return Unit.INSTANCE;
                }
            });
            this.L$0 = sequenceScope;
            this.label = 2;
            if (sequenceScope.yield(pair3, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        DumpManager dumpManager32 = this.this$0;
        registeredDumpable = (RegisteredDumpable) DumpManager.access$findBestTargetMatch(dumpManager32, dumpManager32.nsDumpables, this.$target);
        if (registeredDumpable != null) {
        }
        return Unit.INSTANCE;
    }
}
