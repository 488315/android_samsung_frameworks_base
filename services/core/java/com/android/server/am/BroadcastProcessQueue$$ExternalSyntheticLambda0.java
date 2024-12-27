package com.android.server.am;


public final /* synthetic */ class BroadcastProcessQueue$$ExternalSyntheticLambda0
        implements BroadcastProcessQueue.BroadcastPredicate {
    public final /* synthetic */ int $r8$classId;

    @Override // com.android.server.am.BroadcastProcessQueue.BroadcastPredicate
    public final boolean test(BroadcastRecord broadcastRecord, int i) {
        switch (this.$r8$classId) {
            case 0:
                if (broadcastRecord.delivery[i] == 0) {}
                break;
            case 1:
                if (broadcastRecord.delivery[i] == 6) {}
                break;
            default:
                if (broadcastRecord.delivery[i] == 6) {}
                break;
        }
        return false;
    }
}
