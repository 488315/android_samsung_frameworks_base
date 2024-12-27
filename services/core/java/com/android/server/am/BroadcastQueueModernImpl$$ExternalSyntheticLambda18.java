package com.android.server.am;

import android.os.BundleMerger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BroadcastQueueModernImpl$$ExternalSyntheticLambda18
        implements BroadcastProcessQueue.BroadcastConsumer,
                BroadcastProcessQueue.BroadcastPredicate {
    public final /* synthetic */ BroadcastQueueModernImpl f$0;
    public final /* synthetic */ BroadcastRecord f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ BroadcastQueueModernImpl$$ExternalSyntheticLambda18(
            BroadcastQueueModernImpl broadcastQueueModernImpl,
            BroadcastRecord broadcastRecord,
            Object obj) {
        this.f$0 = broadcastQueueModernImpl;
        this.f$1 = broadcastRecord;
        this.f$2 = obj;
    }

    @Override // com.android.server.am.BroadcastProcessQueue.BroadcastConsumer
    public void accept(BroadcastRecord broadcastRecord, int i) {
        BundleMerger bundleMerger = (BundleMerger) this.f$2;
        BroadcastQueueModernImpl broadcastQueueModernImpl = this.f$0;
        broadcastQueueModernImpl.getClass();
        this.f$1.intent.mergeExtras(broadcastRecord.intent, bundleMerger);
        broadcastQueueModernImpl.mBroadcastConsumerSkipAndCanceled.accept(broadcastRecord, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:40:? A[RETURN, SYNTHETIC] */
    @Override // com.android.server.am.BroadcastProcessQueue.BroadcastPredicate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean test(com.android.server.am.BroadcastRecord r7, int r8) {
        /*
            Method dump skipped, instructions count: 228
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.BroadcastQueueModernImpl$$ExternalSyntheticLambda18.test(com.android.server.am.BroadcastRecord,"
                    + " int):boolean");
    }
}
