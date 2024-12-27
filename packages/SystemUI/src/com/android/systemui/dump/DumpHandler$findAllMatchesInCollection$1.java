package com.android.systemui.dump;

import com.android.systemui.dump.DumpsysEntry;
import java.util.Collection;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

final class DumpHandler$findAllMatchesInCollection$1 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ Collection<DumpsysEntry.DumpableEntry> $dumpables;
    final /* synthetic */ Collection<DumpsysEntry.LogBufferEntry> $logBuffers;
    final /* synthetic */ Collection<DumpsysEntry.TableLogBufferEntry> $tableBuffers;
    final /* synthetic */ List<String> $targets;
    private /* synthetic */ Object L$0;
    int label;

    public DumpHandler$findAllMatchesInCollection$1(Collection<DumpsysEntry.DumpableEntry> collection, Collection<DumpsysEntry.LogBufferEntry> collection2, Collection<DumpsysEntry.TableLogBufferEntry> collection3, List<String> list, Continuation continuation) {
        super(2, continuation);
        this.$dumpables = collection;
        this.$logBuffers = collection2;
        this.$tableBuffers = collection3;
        this.$targets = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DumpHandler$findAllMatchesInCollection$1 dumpHandler$findAllMatchesInCollection$1 = new DumpHandler$findAllMatchesInCollection$1(this.$dumpables, this.$logBuffers, this.$tableBuffers, this.$targets, continuation);
        dumpHandler$findAllMatchesInCollection$1.L$0 = obj;
        return dumpHandler$findAllMatchesInCollection$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DumpHandler$findAllMatchesInCollection$1) create((SequenceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 3
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L2d
            if (r1 == r4) goto L25
            if (r1 == r3) goto L1c
            if (r1 != r2) goto L14
            kotlin.ResultKt.throwOnFailure(r12)
            goto Ld8
        L14:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L1c:
            java.lang.Object r1 = r11.L$0
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            kotlin.ResultKt.throwOnFailure(r12)
            goto La1
        L25:
            java.lang.Object r1 = r11.L$0
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            kotlin.ResultKt.throwOnFailure(r12)
            goto L6b
        L2d:
            kotlin.ResultKt.throwOnFailure(r12)
            java.lang.Object r12 = r11.L$0
            kotlin.sequences.SequenceScope r12 = (kotlin.sequences.SequenceScope) r12
            java.util.Collection<com.android.systemui.dump.DumpsysEntry$DumpableEntry> r1 = r11.$dumpables
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            java.util.List<java.lang.String> r5 = r11.$targets
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            java.util.Iterator r1 = r1.iterator()
        L43:
            boolean r7 = r1.hasNext()
            if (r7 == 0) goto L5f
            java.lang.Object r7 = r1.next()
            r8 = r7
            com.android.systemui.dump.DumpsysEntry$DumpableEntry r8 = (com.android.systemui.dump.DumpsysEntry.DumpableEntry) r8
            com.android.systemui.dump.DumpHandler$Companion r9 = com.android.systemui.dump.DumpHandler.Companion
            r10 = r5
            java.util.Collection r10 = (java.util.Collection) r10
            boolean r8 = com.android.systemui.dump.DumpHandler.Companion.access$matchesAny(r9, r8, r10)
            if (r8 == 0) goto L43
            r6.add(r7)
            goto L43
        L5f:
            r11.L$0 = r12
            r11.label = r4
            java.lang.Object r1 = r12.yieldAll(r6, r11)
            if (r1 != r0) goto L6a
            return r0
        L6a:
            r1 = r12
        L6b:
            java.util.Collection<com.android.systemui.dump.DumpsysEntry$LogBufferEntry> r12 = r11.$logBuffers
            java.lang.Iterable r12 = (java.lang.Iterable) r12
            java.util.List<java.lang.String> r4 = r11.$targets
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.util.Iterator r12 = r12.iterator()
        L7a:
            boolean r6 = r12.hasNext()
            if (r6 == 0) goto L96
            java.lang.Object r6 = r12.next()
            r7 = r6
            com.android.systemui.dump.DumpsysEntry$LogBufferEntry r7 = (com.android.systemui.dump.DumpsysEntry.LogBufferEntry) r7
            com.android.systemui.dump.DumpHandler$Companion r8 = com.android.systemui.dump.DumpHandler.Companion
            r9 = r4
            java.util.Collection r9 = (java.util.Collection) r9
            boolean r7 = com.android.systemui.dump.DumpHandler.Companion.access$matchesAny(r8, r7, r9)
            if (r7 == 0) goto L7a
            r5.add(r6)
            goto L7a
        L96:
            r11.L$0 = r1
            r11.label = r3
            java.lang.Object r12 = r1.yieldAll(r5, r11)
            if (r12 != r0) goto La1
            return r0
        La1:
            java.util.Collection<com.android.systemui.dump.DumpsysEntry$TableLogBufferEntry> r12 = r11.$tableBuffers
            java.lang.Iterable r12 = (java.lang.Iterable) r12
            java.util.List<java.lang.String> r3 = r11.$targets
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.util.Iterator r12 = r12.iterator()
        Lb0:
            boolean r5 = r12.hasNext()
            if (r5 == 0) goto Lcc
            java.lang.Object r5 = r12.next()
            r6 = r5
            com.android.systemui.dump.DumpsysEntry$TableLogBufferEntry r6 = (com.android.systemui.dump.DumpsysEntry.TableLogBufferEntry) r6
            com.android.systemui.dump.DumpHandler$Companion r7 = com.android.systemui.dump.DumpHandler.Companion
            r8 = r3
            java.util.Collection r8 = (java.util.Collection) r8
            boolean r6 = com.android.systemui.dump.DumpHandler.Companion.access$matchesAny(r7, r6, r8)
            if (r6 == 0) goto Lb0
            r4.add(r5)
            goto Lb0
        Lcc:
            r12 = 0
            r11.L$0 = r12
            r11.label = r2
            java.lang.Object r11 = r1.yieldAll(r4, r11)
            if (r11 != r0) goto Ld8
            return r0
        Ld8:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.dump.DumpHandler$findAllMatchesInCollection$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
