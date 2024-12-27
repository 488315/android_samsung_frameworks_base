package com.android.server.am;

import com.android.server.am.BroadcastProcessQueue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BroadcastQueueModernImpl$$ExternalSyntheticLambda4 implements BroadcastProcessQueue.BroadcastConsumer, BroadcastProcessQueue.BroadcastPredicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BroadcastQueueModernImpl$$ExternalSyntheticLambda4(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // com.android.server.am.BroadcastProcessQueue.BroadcastConsumer
    public void accept(BroadcastRecord broadcastRecord, int i) {
        int i2 = this.$r8$classId;
        Object obj = this.f$0;
        switch (i2) {
            case 1:
                BroadcastQueueModernImpl broadcastQueueModernImpl = (BroadcastQueueModernImpl) obj;
                broadcastQueueModernImpl.getClass();
                broadcastQueueModernImpl.setDeliveryState(null, null, broadcastRecord, i, broadcastRecord.receivers.get(i), 2, "mBroadcastConsumerSkip");
                break;
            case 2:
                BroadcastQueueModernImpl broadcastQueueModernImpl2 = (BroadcastQueueModernImpl) obj;
                broadcastQueueModernImpl2.getClass();
                broadcastQueueModernImpl2.setDeliveryState(null, null, broadcastRecord, i, broadcastRecord.receivers.get(i), 2, "mBroadcastConsumerSkipAndCanceled");
                broadcastRecord.resultCode = 0;
                broadcastRecord.resultData = null;
                broadcastRecord.resultExtras = null;
                break;
            case 3:
                BroadcastQueueModernImpl broadcastQueueModernImpl3 = (BroadcastQueueModernImpl) obj;
                broadcastQueueModernImpl3.getClass();
                broadcastQueueModernImpl3.setDeliveryState(null, null, broadcastRecord, i, broadcastRecord.receivers.get(i), 6, "mBroadcastConsumerDeferApply");
                break;
            default:
                BroadcastQueueModernImpl broadcastQueueModernImpl4 = (BroadcastQueueModernImpl) obj;
                broadcastQueueModernImpl4.getClass();
                broadcastQueueModernImpl4.setDeliveryState(null, null, broadcastRecord, i, broadcastRecord.receivers.get(i), 0, "mBroadcastConsumerDeferClear");
                break;
        }
    }

    @Override // com.android.server.am.BroadcastProcessQueue.BroadcastPredicate
    public boolean test(BroadcastRecord broadcastRecord, int i) {
        return ((String) this.f$0).equals(BroadcastRecord.getReceiverPackageName(broadcastRecord.receivers.get(i)));
    }
}
