package com.android.server.utils.quota;

import android.util.ArrayMap;
import android.util.LongArrayQueue;
import android.util.proto.ProtoOutputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class CountQuotaTracker$$ExternalSyntheticLambda5
        implements UptcMap.UptcDataConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CountQuotaTracker f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ CountQuotaTracker$$ExternalSyntheticLambda5(
            CountQuotaTracker countQuotaTracker, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = countQuotaTracker;
        this.f$1 = obj;
    }

    @Override // com.android.server.utils.quota.UptcMap.UptcDataConsumer
    public final void accept(int i, Object obj, String str, String str2) {
        switch (this.$r8$classId) {
            case 0:
                ProtoOutputStream protoOutputStream = (ProtoOutputStream) this.f$1;
                CountQuotaTracker.ExecutionStats executionStats =
                        (CountQuotaTracker.ExecutionStats) obj;
                CountQuotaTracker countQuotaTracker = this.f$0;
                boolean booleanValue =
                        ((Boolean) countQuotaTracker.mFreeQuota.getOrDefault(i, str, Boolean.FALSE))
                                .booleanValue();
                long start = protoOutputStream.start(2246267895811L);
                if ((((str.hashCode() * 31) + (i * 31)) + str2) != null) {
                    str2.getClass();
                }
                long start2 = protoOutputStream.start(1146756268033L);
                protoOutputStream.write(1120986464257L, i);
                protoOutputStream.write(1138166333442L, str);
                protoOutputStream.write(1138166333443L, str2);
                protoOutputStream.end(start2);
                protoOutputStream.write(1133871366146L, booleanValue);
                LongArrayQueue longArrayQueue =
                        (LongArrayQueue) countQuotaTracker.mEventTimes.get(i, str, str2);
                if (longArrayQueue != null) {
                    for (int size = longArrayQueue.size() - 1; size >= 0; size--) {
                        long start3 = protoOutputStream.start(2246267895811L);
                        protoOutputStream.write(1112396529665L, longArrayQueue.get(size));
                        protoOutputStream.end(start3);
                    }
                }
                long start4 = protoOutputStream.start(2246267895812L);
                protoOutputStream.write(1112396529665L, executionStats.expirationTimeElapsed);
                protoOutputStream.write(1112396529666L, executionStats.windowSizeMs);
                protoOutputStream.write(1120986464259L, executionStats.countLimit);
                protoOutputStream.write(1120986464260L, executionStats.countInWindow);
                protoOutputStream.write(1112396529669L, executionStats.inQuotaTimeElapsed);
                protoOutputStream.end(start4);
                protoOutputStream.end(start);
                break;
            default:
                CountQuotaTracker countQuotaTracker2 = this.f$0;
                countQuotaTracker2.getClass();
                UptcMap uptcMap = (UptcMap) this.f$1;
                ArrayMap arrayMap = (ArrayMap) uptcMap.mData.get(i, str);
                if (arrayMap == null || !arrayMap.containsKey(str2)) {
                    countQuotaTracker2.maybeUpdateStatusForUptcLocked(i, str, str2);
                    Boolean bool = Boolean.TRUE;
                    ArrayMap arrayMap2 = (ArrayMap) uptcMap.mData.get(i, str);
                    if (arrayMap2 == null) {
                        arrayMap2 = new ArrayMap();
                        uptcMap.mData.add(i, str, arrayMap2);
                    }
                    arrayMap2.put(str2, bool);
                    break;
                }
                break;
        }
    }
}
