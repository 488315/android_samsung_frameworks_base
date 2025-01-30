package com.android.server.asks;

import android.util.Slog;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public abstract class ZipAnalyzerUtil {
  public static int[] analyzeZipFile(String str) {
    int[] iArr = {0, 0};
    File file = new File(str);
    if (!file.exists() || !file.isFile()) {
      Slog.i("PackageInformation_ZIP", "The file " + str + " does not exist.");
      return null;
    }
    try {
      EocdRecord parseEocdRecord = parseEocdRecord(str, findEOCDOffset(str));
      if (parseEocdRecord.diskNumber > 0 || parseEocdRecord.startDiskNumber > 0) {
        Slog.i("PackageInformation_ZIP", "ZIP Number of this Disk Tampering");
        iArr[0] = 1;
      }
      int countCentralDirectorySignatures =
          countCentralDirectorySignatures(
              str, parseEocdRecord.centralDirOffset, parseEocdRecord.centralDirSize);
      if (parseEocdRecord.numEntriesThisDisk != countCentralDirectorySignatures
          || parseEocdRecord.numEntriesTotal != countCentralDirectorySignatures) {
        Slog.i("PackageInformation_ZIP", "Number of Entries this DiskTampering");
        iArr[1] = 1;
      }
    } catch (IOException e) {
      Slog.e("PackageInformation_ZIP", e.getMessage());
    } catch (IllegalStateException e2) {
      Slog.e("PackageInformation_ZIP", e2.getMessage());
    }
    return iArr;
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
        if (ByteBuffer.wrap(bArr, i2, 4).order(ByteOrder.LITTLE_ENDIAN).getInt() == 101010256) {
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

  public static EocdRecord parseEocdRecord(String str, long j) {
    RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
    try {
      randomAccessFile.seek(j);
      ByteBuffer order = ByteBuffer.allocate(22).order(ByteOrder.LITTLE_ENDIAN);
      randomAccessFile.getChannel().read(order);
      order.flip();
      if (order.getInt() != 101010256) {
        throw new IOException("Invalid EOCD signature");
      }
      EocdRecord eocdRecord =
          new EocdRecord(
              order.getShort(),
              order.getShort(),
              order.getShort(),
              order.getShort(),
              order.getInt(),
              order.getInt(),
              order.getShort());
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

  public static int countCentralDirectorySignatures(String str, int i, int i2) {
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

  public class EocdRecord {
    public int centralDirOffset;
    public int centralDirSize;
    public short commentLen;
    public short diskNumber;
    public short numEntriesThisDisk;
    public short numEntriesTotal;
    public short startDiskNumber;

    public EocdRecord(short s, short s2, short s3, short s4, int i, int i2, short s5) {
      this.diskNumber = s;
      this.startDiskNumber = s2;
      this.numEntriesThisDisk = s3;
      this.numEntriesTotal = s4;
      this.centralDirSize = i;
      this.centralDirOffset = i2;
      this.commentLen = s5;
    }
  }
}
