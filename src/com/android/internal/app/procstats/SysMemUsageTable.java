package com.android.internal.app.procstats;

import android.util.DebugUtils;
import com.android.internal.app.procstats.SparseMappingTable;
import java.io.PrintWriter;

/* loaded from: classes5.dex */
public class SysMemUsageTable extends SparseMappingTable.Table {
    public SysMemUsageTable(SparseMappingTable sparseMappingTable) {
        super(sparseMappingTable);
    }

    public void mergeStats(SysMemUsageTable sysMemUsageTable) {
        int keyCount = sysMemUsageTable.getKeyCount();
        for (int i = 0; i < keyCount; i++) {
            int keyAt = sysMemUsageTable.getKeyAt(i);
            mergeStats(SparseMappingTable.getIdFromKey(keyAt), sysMemUsageTable.getArrayForKey(keyAt), SparseMappingTable.getIndexFromKey(keyAt));
        }
    }

    public void mergeStats(int i, long[] jArr, int i2) {
        int orAddKey = getOrAddKey((byte) i, 16);
        mergeSysMemUsage(getArrayForKey(orAddKey), SparseMappingTable.getIndexFromKey(orAddKey), jArr, i2);
    }

    public long[] getTotalMemUsage() {
        long[] jArr = new long[16];
        int keyCount = getKeyCount();
        for (int i = 0; i < keyCount; i++) {
            int keyAt = getKeyAt(i);
            mergeSysMemUsage(jArr, 0, getArrayForKey(keyAt), SparseMappingTable.getIndexFromKey(keyAt));
        }
        return jArr;
    }

    public static void mergeSysMemUsage(long[] jArr, int i, long[] jArr2, int i2) {
        long j = jArr[i];
        long j2 = jArr2[i2];
        int i3 = 1;
        if (j == 0) {
            jArr[i] = j2;
            while (i3 < 16) {
                jArr[i + i3] = jArr2[i2 + i3];
                i3++;
            }
            return;
        }
        if (j2 > 0) {
            long j3 = j + j2;
            jArr[i] = j3;
            for (int i4 = 16; i3 < i4; i4 = 16) {
                int i5 = i + i3;
                long j4 = jArr[i5];
                int i6 = i2 + i3;
                long j5 = jArr2[i6];
                if (j4 > j5) {
                    jArr[i5] = j5;
                }
                int i7 = i3;
                jArr[i5 + 1] = (long) (((jArr[r9] * j) + (jArr2[i6 + 1] * j2)) / j3);
                int i8 = i5 + 2;
                long j6 = jArr[i8];
                long j7 = jArr2[i6 + 2];
                if (j6 < j7) {
                    jArr[i8] = j7;
                }
                i3 = i7 + 3;
            }
        }
    }

    public void dump(PrintWriter printWriter, String str, int[] iArr, int[] iArr2) {
        int i;
        int i2;
        PrintWriter printWriter2 = printWriter;
        int i3 = 0;
        int i4 = -1;
        while (i3 < iArr.length) {
            int i5 = 0;
            int i6 = -1;
            while (i5 < iArr2.length) {
                int i7 = iArr[i3];
                int i8 = iArr2[i5];
                int i9 = (i7 + i8) * 16;
                long valueForId = getValueForId((byte) i9, 0);
                if (valueForId > 0) {
                    printWriter.print(str);
                    if (iArr.length > 1) {
                        DumpUtils.printScreenLabel(printWriter2, i4 != i7 ? i7 : -1);
                        i = i7;
                    } else {
                        i = i4;
                    }
                    if (iArr2.length > 1) {
                        DumpUtils.printMemLabel(printWriter2, i6 != i8 ? i8 : -1, (char) 0);
                        i2 = i8;
                    } else {
                        i2 = i6;
                    }
                    printWriter2.print(": ");
                    printWriter2.print(valueForId);
                    printWriter2.println(" samples:");
                    dumpCategory(printWriter2, str, "  Cached", i9, 1);
                    dumpCategory(printWriter, str, "  Free", i9, 4);
                    dumpCategory(printWriter, str, "  ZRam", i9, 7);
                    dumpCategory(printWriter, str, "  Kernel", i9, 10);
                    dumpCategory(printWriter, str, "  Native", i9, 13);
                    i6 = i2;
                    i4 = i;
                }
                i5++;
                printWriter2 = printWriter;
            }
            i3++;
            printWriter2 = printWriter;
        }
    }

    private void dumpCategory(PrintWriter printWriter, String str, String str2, int i, int i2) {
        printWriter.print(str);
        printWriter.print(str2);
        printWriter.print(": ");
        byte b = (byte) i;
        DebugUtils.printSizeValue(printWriter, getValueForId(b, i2) * 1024);
        printWriter.print(" min, ");
        DebugUtils.printSizeValue(printWriter, getValueForId(b, i2 + 1) * 1024);
        printWriter.print(" avg, ");
        DebugUtils.printSizeValue(printWriter, getValueForId(b, i2 + 2) * 1024);
        printWriter.println(" max");
    }
}
