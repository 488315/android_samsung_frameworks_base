package com.android.server.asks;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class ZipAnalyzerUtil {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EocdRecord {
        public int centralDirOffset;
        public int centralDirSize;
        public short diskNumber;
        public short numEntriesThisDisk;
        public short numEntriesTotal;
        public short startDiskNumber;
    }

    public static int countCentralDirectorySignatures(int i, int i2, String str) {
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
        try {
            randomAccessFile.seek(i);
            byte[] bArr = new byte[i2];
            randomAccessFile.readFully(bArr);
            ByteBuffer order = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
            int i3 = 0;
            while (order.remaining() >= 4) {
                if (order.getInt() == 33639248) {
                    i3++;
                } else {
                    order.position(order.position() - 3);
                }
            }
            randomAccessFile.close();
            return i3;
        } catch (Throwable th) {
            try {
                randomAccessFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static long findEOCDOffset(String str) {
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
        try {
            long length = randomAccessFile.length();
            long min = Math.min(65557L, length);
            long j = length - min;
            randomAccessFile.seek(j);
            int i = (int) min;
            byte[] bArr = new byte[i];
            randomAccessFile.readFully(bArr);
            for (int i2 = i - 22; i2 >= 0; i2--) {
                if (ByteBuffer.wrap(bArr, i2, 4).order(ByteOrder.LITTLE_ENDIAN).getInt()
                        == 101010256) {
                    long j2 = j + i2;
                    randomAccessFile.close();
                    return j2;
                }
            }
            randomAccessFile.close();
            throw new IOException("End of Central Directory Record not found");
        } catch (Throwable th) {
            try {
                randomAccessFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static EocdRecord parseEocdRecord(long j, String str) {
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
        try {
            randomAccessFile.seek(j);
            ByteBuffer order = ByteBuffer.allocate(22).order(ByteOrder.LITTLE_ENDIAN);
            randomAccessFile.getChannel().read(order);
            order.flip();
            if (order.getInt() != 101010256) {
                throw new IOException("Invalid EOCD signature");
            }
            short s = order.getShort();
            short s2 = order.getShort();
            short s3 = order.getShort();
            short s4 = order.getShort();
            int i = order.getInt();
            int i2 = order.getInt();
            order.getShort();
            EocdRecord eocdRecord = new EocdRecord();
            eocdRecord.diskNumber = s;
            eocdRecord.startDiskNumber = s2;
            eocdRecord.numEntriesThisDisk = s3;
            eocdRecord.numEntriesTotal = s4;
            eocdRecord.centralDirSize = i;
            eocdRecord.centralDirOffset = i2;
            randomAccessFile.close();
            return eocdRecord;
        } catch (Throwable th) {
            try {
                randomAccessFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
