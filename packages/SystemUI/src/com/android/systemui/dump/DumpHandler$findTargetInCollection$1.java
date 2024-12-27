package com.android.systemui.dump;

import com.android.systemui.dump.DumpsysEntry;
import java.util.Collection;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class DumpHandler$findTargetInCollection$1 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ Collection<DumpsysEntry.DumpableEntry> $dumpables;
    final /* synthetic */ Collection<DumpsysEntry.LogBufferEntry> $logBuffers;
    final /* synthetic */ Collection<DumpsysEntry.TableLogBufferEntry> $tableBuffers;
    final /* synthetic */ String $target;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DumpHandler$findTargetInCollection$1(Collection<DumpsysEntry.DumpableEntry> collection, String str, Collection<DumpsysEntry.LogBufferEntry> collection2, Collection<DumpsysEntry.TableLogBufferEntry> collection3, Continuation continuation) {
        super(2, continuation);
        this.$dumpables = collection;
        this.$target = str;
        this.$logBuffers = collection2;
        this.$tableBuffers = collection3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DumpHandler$findTargetInCollection$1 dumpHandler$findTargetInCollection$1 = new DumpHandler$findTargetInCollection$1(this.$dumpables, this.$target, this.$logBuffers, this.$tableBuffers, continuation);
        dumpHandler$findTargetInCollection$1.L$0 = obj;
        return dumpHandler$findTargetInCollection$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DumpHandler$findTargetInCollection$1) create((SequenceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x006d  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 3
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L2b
            if (r1 == r4) goto L23
            if (r1 == r3) goto L1b
            if (r1 != r2) goto L13
            kotlin.ResultKt.throwOnFailure(r8)
            goto L79
        L13:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L1b:
            java.lang.Object r1 = r7.L$0
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            kotlin.ResultKt.throwOnFailure(r8)
            goto L61
        L23:
            java.lang.Object r1 = r7.L$0
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            kotlin.ResultKt.throwOnFailure(r8)
            goto L4a
        L2b:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            r1 = r8
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            com.android.systemui.dump.DumpHandler$Companion r8 = com.android.systemui.dump.DumpHandler.Companion
            java.util.Collection<com.android.systemui.dump.DumpsysEntry$DumpableEntry> r5 = r7.$dumpables
            java.lang.String r6 = r7.$target
            com.android.systemui.dump.DumpsysEntry r8 = com.android.systemui.dump.DumpHandler.Companion.access$findBestTargetMatch(r8, r5, r6)
            if (r8 == 0) goto L4a
            r7.L$0 = r1
            r7.label = r4
            kotlin.coroutines.intrinsics.CoroutineSingletons r8 = r1.yield(r8, r7)
            if (r8 != r0) goto L4a
            return r0
        L4a:
            com.android.systemui.dump.DumpHandler$Companion r8 = com.android.systemui.dump.DumpHandler.Companion
            java.util.Collection<com.android.systemui.dump.DumpsysEntry$LogBufferEntry> r4 = r7.$logBuffers
            java.lang.String r5 = r7.$target
            com.android.systemui.dump.DumpsysEntry r8 = com.android.systemui.dump.DumpHandler.Companion.access$findBestTargetMatch(r8, r4, r5)
            if (r8 == 0) goto L61
            r7.L$0 = r1
            r7.label = r3
            kotlin.coroutines.intrinsics.CoroutineSingletons r8 = r1.yield(r8, r7)
            if (r8 != r0) goto L61
            return r0
        L61:
            com.android.systemui.dump.DumpHandler$Companion r8 = com.android.systemui.dump.DumpHandler.Companion
            java.util.Collection<com.android.systemui.dump.DumpsysEntry$TableLogBufferEntry> r3 = r7.$tableBuffers
            java.lang.String r4 = r7.$target
            com.android.systemui.dump.DumpsysEntry r8 = com.android.systemui.dump.DumpHandler.Companion.access$findBestTargetMatch(r8, r3, r4)
            if (r8 == 0) goto L79
            r3 = 0
            r7.L$0 = r3
            r7.label = r2
            kotlin.coroutines.intrinsics.CoroutineSingletons r7 = r1.yield(r8, r7)
            if (r7 != r0) goto L79
            return r0
        L79:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.dump.DumpHandler$findTargetInCollection$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
