package com.android.server.am;

import android.util.Pair;

import java.util.concurrent.CountDownLatch;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BroadcastQueueModernImpl$$ExternalSyntheticLambda1
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Pair pair = (Pair) obj;
                if (((BooleanSupplier) pair.first).getAsBoolean()) {
                    ((CountDownLatch) pair.second).countDown();
                    break;
                }
                break;
            case 1:
                BroadcastProcessQueue broadcastProcessQueue = (BroadcastProcessQueue) obj;
                if ((!broadcastProcessQueue.isActive() && broadcastProcessQueue.isEmpty())
                        || broadcastProcessQueue.isDeferredUntilActive()) {
                    break;
                }
                break;
            default:
                BroadcastQueueModernImpl$$ExternalSyntheticLambda1
                        broadcastQueueModernImpl$$ExternalSyntheticLambda1 =
                                BroadcastQueueModernImpl.QUEUE_PREDICATE_ANY;
                break;
        }
        return true;
    }
}
