package com.android.server.am;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
