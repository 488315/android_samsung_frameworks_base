package com.android.server.vibrator;

import android.util.IndentingPrintWriter;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;

import java.util.ArrayDeque;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class GroupedAggregatedLogRecords {
    public final int mAggregationTimeLimitMs;
    public final SparseArray mGroupedRecords = new SparseArray();
    public final int mSizeLimit;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AggregatedLogRecord {
        public int mCount = 1;
        public final SingleLogRecord mFirst;
        public SingleLogRecord mLatest;

        public AggregatedLogRecord(SingleLogRecord singleLogRecord) {
            this.mFirst = singleLogRecord;
            this.mLatest = singleLogRecord;
        }

        public final synchronized void dump(IndentingPrintWriter indentingPrintWriter) {
            try {
                this.mFirst.dump(indentingPrintWriter);
                int i = this.mCount;
                if (i == 1) {
                    return;
                }
                if (i > 2) {
                    indentingPrintWriter.println(
                            "-> Skipping " + (this.mCount - 2) + " aggregated entries, latest:");
                }
                this.mLatest.dump(indentingPrintWriter);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface SingleLogRecord {
        void dump(IndentingPrintWriter indentingPrintWriter);

        void dump(ProtoOutputStream protoOutputStream, long j);

        long getCreateUptimeMs();

        int getGroupKey();

        boolean mayAggregate(SingleLogRecord singleLogRecord);
    }

    public GroupedAggregatedLogRecords(int i, int i2) {
        this.mSizeLimit = i;
        this.mAggregationTimeLimitMs = i2;
    }

    public final synchronized AggregatedLogRecord add(SingleLogRecord singleLogRecord) {
        boolean z;
        try {
            int groupKey = singleLogRecord.getGroupKey();
            if (!this.mGroupedRecords.contains(groupKey)) {
                this.mGroupedRecords.put(groupKey, new ArrayDeque(this.mSizeLimit));
            }
            ArrayDeque arrayDeque = (ArrayDeque) this.mGroupedRecords.get(groupKey);
            if (this.mAggregationTimeLimitMs > 0 && !arrayDeque.isEmpty()) {
                AggregatedLogRecord aggregatedLogRecord =
                        (AggregatedLogRecord) arrayDeque.getLast();
                long j = this.mAggregationTimeLimitMs;
                synchronized (aggregatedLogRecord) {
                    z =
                            aggregatedLogRecord.mLatest.mayAggregate(singleLogRecord)
                                    && Math.abs(
                                                    aggregatedLogRecord.mLatest.getCreateUptimeMs()
                                                            - singleLogRecord.getCreateUptimeMs())
                                            < j;
                }
                if (z) {
                    synchronized (aggregatedLogRecord) {
                        aggregatedLogRecord.mLatest = singleLogRecord;
                        aggregatedLogRecord.mCount++;
                    }
                    return null;
                }
            }
            AggregatedLogRecord aggregatedLogRecord2 =
                    arrayDeque.size() >= this.mSizeLimit
                            ? (AggregatedLogRecord) arrayDeque.removeFirst()
                            : null;
            arrayDeque.addLast(new AggregatedLogRecord(singleLogRecord));
            return aggregatedLogRecord2;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void dump(IndentingPrintWriter indentingPrintWriter) {
        for (int i = 0; i < this.mGroupedRecords.size(); i++) {
            try {
                dumpGroupHeader(indentingPrintWriter, this.mGroupedRecords.keyAt(i));
                indentingPrintWriter.increaseIndent();
                Iterator it = ((ArrayDeque) this.mGroupedRecords.valueAt(i)).iterator();
                while (it.hasNext()) {
                    ((AggregatedLogRecord) it.next()).dump(indentingPrintWriter);
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final synchronized void dump(ProtoOutputStream protoOutputStream) {
        for (int i = 0; i < this.mGroupedRecords.size(); i++) {
            long findGroupKeyProtoFieldId = findGroupKeyProtoFieldId(this.mGroupedRecords.keyAt(i));
            Iterator it = ((ArrayDeque) this.mGroupedRecords.valueAt(i)).iterator();
            while (it.hasNext()) {
                AggregatedLogRecord aggregatedLogRecord = (AggregatedLogRecord) it.next();
                synchronized (aggregatedLogRecord) {
                    aggregatedLogRecord.mFirst.dump(protoOutputStream, findGroupKeyProtoFieldId);
                    if (aggregatedLogRecord.mCount > 1) {
                        aggregatedLogRecord.mLatest.dump(
                                protoOutputStream, findGroupKeyProtoFieldId);
                    }
                }
            }
        }
    }

    public abstract void dumpGroupHeader(IndentingPrintWriter indentingPrintWriter, int i);

    public abstract long findGroupKeyProtoFieldId(int i);
}
