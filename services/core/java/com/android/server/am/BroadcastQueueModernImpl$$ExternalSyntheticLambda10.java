package com.android.server.am;

import android.content.Intent;

import java.util.function.Predicate;

public final /* synthetic */ class BroadcastQueueModernImpl$$ExternalSyntheticLambda10
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BroadcastQueueModernImpl$$ExternalSyntheticLambda10(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                String str = (String) obj2;
                ProcessRecord processRecord = ((BroadcastProcessQueue) obj).app;
                return str.equals(processRecord == null ? null : processRecord.info.packageName);
            default:
                Intent intent = (Intent) obj2;
                BroadcastProcessQueue broadcastProcessQueue = (BroadcastProcessQueue) obj;
                BroadcastRecord broadcastRecord = broadcastProcessQueue.mActive;
                return ((broadcastRecord == null || !intent.filterEquals(broadcastRecord.intent))
                                && BroadcastProcessQueue.isDispatchedInQueue(
                                        broadcastProcessQueue.mPending, intent)
                                && BroadcastProcessQueue.isDispatchedInQueue(
                                        broadcastProcessQueue.mPendingUrgent, intent)
                                && BroadcastProcessQueue.isDispatchedInQueue(
                                        broadcastProcessQueue.mPendingOffload, intent))
                        || broadcastProcessQueue.isDeferredUntilActive();
        }
    }
}
