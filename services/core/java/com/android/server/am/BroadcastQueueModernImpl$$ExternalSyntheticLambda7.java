package com.android.server.am;


public final /* synthetic */ class BroadcastQueueModernImpl$$ExternalSyntheticLambda7
        implements BroadcastProcessQueue.BroadcastPredicate {
    public final /* synthetic */ int $r8$classId;

    @Override // com.android.server.am.BroadcastProcessQueue.BroadcastPredicate
    public final boolean test(BroadcastRecord broadcastRecord, int i) {
        switch (this.$r8$classId) {
            case 0:
                BroadcastQueueModernImpl$$ExternalSyntheticLambda1
                        broadcastQueueModernImpl$$ExternalSyntheticLambda1 =
                                BroadcastQueueModernImpl.QUEUE_PREDICATE_ANY;
                return true;
            default:
                return broadcastRecord.receivers.get(i) instanceof BroadcastFilter;
        }
    }
}
