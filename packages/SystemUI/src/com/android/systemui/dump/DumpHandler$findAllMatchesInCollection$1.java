package com.android.systemui.dump;

import com.android.systemui.dump.DumpHandler;
import com.android.systemui.dump.DumpsysEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

/* loaded from: classes2.dex */
final class DumpHandler$findAllMatchesInCollection$1 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ Collection<DumpsysEntry.DumpableEntry> $dumpables;
    final /* synthetic */ Collection<DumpsysEntry.LogBufferEntry> $logBuffers;
    final /* synthetic */ Collection<DumpsysEntry.TableLogBufferEntry> $tableBuffers;
    final /* synthetic */ List<String> $targets;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00d5, code lost:
    
        if (r1.yieldAll(r4, r11) != r0) goto L40;
     */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b6  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        SequenceScope sequenceScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SequenceScope sequenceScope2 = (SequenceScope) this.L$0;
            Collection<DumpsysEntry.DumpableEntry> collection = this.$dumpables;
            List<String> list = this.$targets;
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : collection) {
                if (DumpHandler.Companion.access$matchesAny(DumpHandler.Companion, (DumpsysEntry.DumpableEntry) obj2, list)) {
                    arrayList.add(obj2);
                }
            }
            this.L$0 = sequenceScope2;
            this.label = 1;
            if (sequenceScope2.yieldAll(arrayList, this) != coroutineSingletons) {
                sequenceScope = sequenceScope2;
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            sequenceScope = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
        } else {
            if (i != 2) {
                if (i != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            sequenceScope = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            Collection<DumpsysEntry.TableLogBufferEntry> collection2 = this.$tableBuffers;
            List<String> list2 = this.$targets;
            ArrayList arrayList2 = new ArrayList();
            for (Object obj3 : collection2) {
                if (DumpHandler.Companion.access$matchesAny(DumpHandler.Companion, (DumpsysEntry.TableLogBufferEntry) obj3, list2)) {
                    arrayList2.add(obj3);
                }
            }
            this.L$0 = null;
            this.label = 3;
        }
        Collection<DumpsysEntry.LogBufferEntry> collection3 = this.$logBuffers;
        List<String> list3 = this.$targets;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj4 : collection3) {
            if (DumpHandler.Companion.access$matchesAny(DumpHandler.Companion, (DumpsysEntry.LogBufferEntry) obj4, list3)) {
                arrayList3.add(obj4);
            }
        }
        this.L$0 = sequenceScope;
        this.label = 2;
        if (sequenceScope.yieldAll(arrayList3, this) != coroutineSingletons) {
            Collection<DumpsysEntry.TableLogBufferEntry> collection22 = this.$tableBuffers;
            List<String> list22 = this.$targets;
            ArrayList arrayList22 = new ArrayList();
            while (r12.hasNext()) {
            }
            this.L$0 = null;
            this.label = 3;
        }
        return coroutineSingletons;
    }
}
