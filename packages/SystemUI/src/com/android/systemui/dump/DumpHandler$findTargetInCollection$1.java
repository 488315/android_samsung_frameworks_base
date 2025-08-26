package com.android.systemui.dump;

import com.android.systemui.dump.DumpHandler;
import com.android.systemui.dump.DumpsysEntry;
import java.util.Collection;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

/* loaded from: classes2.dex */
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

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0076, code lost:
    
        if (r1.yield(r8, r7) == r0) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x006d  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        SequenceScope sequenceScope;
        DumpsysEntry dumpsysEntryAccess$findBestTargetMatch;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            sequenceScope = (SequenceScope) this.L$0;
            DumpsysEntry dumpsysEntryAccess$findBestTargetMatch2 = DumpHandler.Companion.access$findBestTargetMatch(DumpHandler.Companion, this.$dumpables, this.$target);
            if (dumpsysEntryAccess$findBestTargetMatch2 != null) {
                this.L$0 = sequenceScope;
                this.label = 1;
                if (sequenceScope.yield(dumpsysEntryAccess$findBestTargetMatch2, this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
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
                dumpsysEntryAccess$findBestTargetMatch = DumpHandler.Companion.access$findBestTargetMatch(DumpHandler.Companion, this.$tableBuffers, this.$target);
                if (dumpsysEntryAccess$findBestTargetMatch != null) {
                    this.L$0 = null;
                    this.label = 3;
                }
                return Unit.INSTANCE;
            }
            sequenceScope = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        DumpsysEntry dumpsysEntryAccess$findBestTargetMatch3 = DumpHandler.Companion.access$findBestTargetMatch(DumpHandler.Companion, this.$logBuffers, this.$target);
        if (dumpsysEntryAccess$findBestTargetMatch3 != null) {
            this.L$0 = sequenceScope;
            this.label = 2;
            if (sequenceScope.yield(dumpsysEntryAccess$findBestTargetMatch3, this) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        dumpsysEntryAccess$findBestTargetMatch = DumpHandler.Companion.access$findBestTargetMatch(DumpHandler.Companion, this.$tableBuffers, this.$target);
        if (dumpsysEntryAccess$findBestTargetMatch != null) {
        }
        return Unit.INSTANCE;
    }
}
