package com.android.internal.app.procstats;

import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoUtils;
import com.android.internal.app.procstats.SparseMappingTable;

/* loaded from: classes5.dex */
public class PssTable extends SparseMappingTable.Table {
    public PssTable(SparseMappingTable sparseMappingTable) {
        super(sparseMappingTable);
    }

    public void mergeStats(PssTable pssTable) {
        int keyCount = pssTable.getKeyCount();
        for (int i = 0; i < keyCount; i++) {
            int keyAt = pssTable.getKeyAt(i);
            int orAddKey = getOrAddKey(SparseMappingTable.getIdFromKey(keyAt), 10);
            mergeStats(getArrayForKey(orAddKey), SparseMappingTable.getIndexFromKey(orAddKey), pssTable.getArrayForKey(keyAt), SparseMappingTable.getIndexFromKey(keyAt));
        }
    }

    public void mergeStats(int i, int i2, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9) {
        int orAddKey = getOrAddKey((byte) i, 10);
        mergeStats(getArrayForKey(orAddKey), SparseMappingTable.getIndexFromKey(orAddKey), i2, j, j2, j3, j4, j5, j6, j7, j8, j9);
    }

    public static void mergeStats(long[] jArr, int i, long[] jArr2, int i2) {
        mergeStats(jArr, i, (int) jArr2[i2], jArr2[i2 + 1], jArr2[i2 + 2], jArr2[i2 + 3], jArr2[i2 + 4], jArr2[i2 + 5], jArr2[i2 + 6], jArr2[i2 + 7], jArr2[i2 + 8], jArr2[i2 + 9]);
    }

    public static void mergeStats(long[] jArr, int i, int i2, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9) {
        long j10 = jArr[i];
        if (j10 == 0) {
            jArr[i] = i2;
            jArr[i + 1] = j;
            jArr[i + 2] = j2;
            jArr[i + 3] = j3;
            jArr[i + 4] = j4;
            jArr[i + 5] = j5;
            jArr[i + 6] = j6;
            jArr[i + 7] = j7;
            jArr[i + 8] = j8;
            jArr[i + 9] = j9;
            return;
        }
        long j11 = i2 + j10;
        jArr[i] = j11;
        int i3 = i + 1;
        if (jArr[i3] > j) {
            jArr[i3] = j;
        }
        double d = j10;
        double d2 = i2;
        double d3 = j11;
        jArr[i + 2] = (long) (((jArr[r11] * d) + (j2 * d2)) / d3);
        int i4 = i + 3;
        if (jArr[i4] < j3) {
            jArr[i4] = j3;
        }
        int i5 = i + 4;
        if (jArr[i5] > j4) {
            jArr[i5] = j4;
        }
        jArr[i + 5] = (long) (((jArr[r2] * d) + (j5 * d2)) / d3);
        int i6 = i + 6;
        if (jArr[i6] < j6) {
            jArr[i6] = j6;
        }
        long j12 = jArr[i + 7];
        jArr[i + 8] = (long) (((jArr[r2] * d) + (j8 * d2)) / d3);
        int i7 = i + 9;
        if (jArr[i7] < j9) {
            jArr[i7] = j9;
        }
    }

    public void writeStatsToProtoForKey(ProtoOutputStream protoOutputStream, int i) {
        writeStatsToProto(protoOutputStream, getArrayForKey(i), SparseMappingTable.getIndexFromKey(i));
    }

    public static void writeStatsToProto(ProtoOutputStream protoOutputStream, long[] jArr, int i) {
        protoOutputStream.write(1120986464261L, jArr[i]);
        ProtoUtils.toAggStatsProto(protoOutputStream, 1146756268038L, jArr[i + 1], jArr[i + 2], jArr[i + 3]);
        ProtoUtils.toAggStatsProto(protoOutputStream, 1146756268039L, jArr[i + 4], jArr[i + 5], jArr[i + 6]);
        ProtoUtils.toAggStatsProto(protoOutputStream, 1146756268040L, jArr[i + 7], jArr[i + 8], jArr[i + 9]);
    }

    long[] getRssMeanAndMax(int i) {
        long[] arrayForKey = getArrayForKey(i);
        int indexFromKey = SparseMappingTable.getIndexFromKey(i);
        return new long[]{arrayForKey[indexFromKey + 8], arrayForKey[indexFromKey + 9]};
    }
}
