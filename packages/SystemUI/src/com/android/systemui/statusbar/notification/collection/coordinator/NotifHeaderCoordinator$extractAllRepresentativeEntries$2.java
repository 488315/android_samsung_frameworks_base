package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;

final class NotifHeaderCoordinator$extractAllRepresentativeEntries$2 extends RestrictedSuspendLambda implements Function2 {
    final /* synthetic */ ListEntry $listEntry;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NotifHeaderCoordinator this$0;

    public NotifHeaderCoordinator$extractAllRepresentativeEntries$2(ListEntry listEntry, NotifHeaderCoordinator notifHeaderCoordinator, Continuation continuation) {
        super(2, continuation);
        this.$listEntry = listEntry;
        this.this$0 = notifHeaderCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NotifHeaderCoordinator$extractAllRepresentativeEntries$2 notifHeaderCoordinator$extractAllRepresentativeEntries$2 = new NotifHeaderCoordinator$extractAllRepresentativeEntries$2(this.$listEntry, this.this$0, continuation);
        notifHeaderCoordinator$extractAllRepresentativeEntries$2.L$0 = obj;
        return notifHeaderCoordinator$extractAllRepresentativeEntries$2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        SequenceScope sequenceScope;
        Sequence extractAllRepresentativeEntries;
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
            extractAllRepresentativeEntries = this.this$0.extractAllRepresentativeEntries((List<? extends ListEntry>) ((GroupEntry) listEntry).mUnmodifiableChildren);
            this.L$0 = null;
            this.label = 2;
            if (sequenceScope.yieldAll(extractAllRepresentativeEntries, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(SequenceScope sequenceScope, Continuation continuation) {
        return ((NotifHeaderCoordinator$extractAllRepresentativeEntries$2) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
