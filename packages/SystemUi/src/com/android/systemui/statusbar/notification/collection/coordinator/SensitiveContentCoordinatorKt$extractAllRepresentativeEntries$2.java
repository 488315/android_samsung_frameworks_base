package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.FlatteningSequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2", m277f = "SensitiveContentCoordinator.kt", m278l = {164, 166}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ ListEntry $listEntry;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2(ListEntry listEntry, Continuation<? super SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2> continuation) {
        super(2, continuation);
        this.$listEntry = listEntry;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2 sensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2 = new SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2(this.$listEntry, continuation);
        sensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2.L$0 = obj;
        return sensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2) create((SequenceScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        SequenceScope sequenceScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            sequenceScope = (SequenceScope) this.L$0;
            NotificationEntry representativeEntry = this.$listEntry.getRepresentativeEntry();
            if (representativeEntry != null) {
                this.L$0 = sequenceScope;
                this.label = 1;
                if (sequenceScope.yield(representativeEntry, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            sequenceScope = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        ListEntry listEntry = this.$listEntry;
        if (listEntry instanceof GroupEntry) {
            FlatteningSequence flatMap = SequencesKt___SequencesKt.flatMap(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(((GroupEntry) listEntry).mUnmodifiableChildren), SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$1.INSTANCE);
            this.L$0 = null;
            this.label = 2;
            if (sequenceScope.yieldAll(flatMap, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }
}
