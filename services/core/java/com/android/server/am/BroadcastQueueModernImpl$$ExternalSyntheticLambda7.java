package com.android.server.am;

import com.android.server.am.BroadcastProcessQueue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BroadcastQueueModernImpl$$ExternalSyntheticLambda7 implements BroadcastProcessQueue.BroadcastPredicate {
    public final /* synthetic */ int $r8$classId;

    @Override // com.android.server.am.BroadcastProcessQueue.BroadcastPredicate
    public final boolean test(BroadcastRecord broadcastRecord, int i) {
        switch (this.$r8$classId) {
            case 0:
                BroadcastQueueModernImpl$$ExternalSyntheticLambda1 broadcastQueueModernImpl$$ExternalSyntheticLambda1 = BroadcastQueueModernImpl.QUEUE_PREDICATE_ANY;
                return true;
            default:
                return broadcastRecord.receivers.get(i) instanceof BroadcastFilter;
        }
    }
}
