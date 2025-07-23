package android.util.apk;

import android.util.Pair;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes4.dex */
abstract class ZipUtils {
    private static final int UINT16_MAX_VALUE = 65535;
    private static final int ZIP64_EOCD_LOCATOR_SIG_REVERSE_BYTE_ORDER = 1347094023;
    private static final int ZIP64_EOCD_LOCATOR_SIZE = 20;
    private static final int ZIP_EOCD_CENTRAL_DIR_OFFSET_FIELD_OFFSET = 16;
    private static final int ZIP_EOCD_CENTRAL_DIR_SIZE_FIELD_OFFSET = 12;
    private static final int ZIP_EOCD_COMMENT_LENGTH_FIELD_OFFSET = 20;
    private static final int ZIP_EOCD_REC_MIN_SIZE = 22;
    private static final int ZIP_EOCD_REC_SIG = 101010256;

    private ZipUtils() {
    }

    static Pair<ByteBuffer, Long> findZipEndOfCentralDirectoryRecord(RandomAccessFile randomAccessFile) throws IOException {
        if (randomAccessFile.getChannel().size() < 22) {
            return null;
        }
        Pair<ByteBuffer, Long> findZipEndOfCentralDirectoryRecord = findZipEndOfCentralDirectoryRecord(randomAccessFile, 0);
        return findZipEndOfCentralDirectoryRecord != null ? findZipEndOfCentralDirectoryRecord : findZipEndOfCentralDirectoryRecord(randomAccessFile, 65535);
    }

    private static Pair<ByteBuffer, Long> findZipEndOfCentralDirectoryRecord(RandomAccessFile randomAccessFile, int i) throws IOException {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException("maxCommentSize: " + i);
        }
        long size = randomAccessFile.getChannel().size();
        if (size < 22) {
            return null;
        }
        ByteBuffer allocate = ByteBuffer.allocate(((int) Math.min(i, size - 22)) + 22);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        long capacity = size - allocate.capacity();
        randomAccessFile.seek(capacity);
        randomAccessFile.readFully(allocate.array(), allocate.arrayOffset(), allocate.capacity());
        int findZipEndOfCentralDirectoryRecord = findZipEndOfCentralDirectoryRecord(allocate);
        if (findZipEndOfCentralDirectoryRecord == -1) {
            return null;
        }
        allocate.position(findZipEndOfCentralDirectoryRecord);
        ByteBuffer slice = allocate.slice();
        slice.order(ByteOrder.LITTLE_ENDIAN);
        return Pair.create(slice, Long.valueOf(capacity + findZipEndOfCentralDirectoryRecord));
    }

    private static int findZipEndOfCentralDirectoryRecord(ByteBuffer byteBuffer) {
        assertByteOrderLittleEndian(byteBuffer);
        int capacity = byteBuffer.capacity();
        if (capacity < 22) {
            return -1;
        }
        int i = capacity - 22;
        int min = Math.min(i, 65535);
        for (int i2 = 0; i2 <= min; i2++) {
            int i3 = i - i2;
            if (byteBuffer.getInt(i3) == ZIP_EOCD_REC_SIG && getUnsignedInt16(byteBuffer, i3 + 20) == i2) {
                return i3;
            }
        }
        return -1;
    }

    public static final boolean isZip64EndOfCentralDirectoryLocatorPresent(RandomAccessFile randomAccessFile, long j) throws IOException {
        long j2 = j - 20;
        if (j2 < 0) {
            return false;
        }
        randomAccessFile.seek(j2);
        return randomAccessFile.readInt() == ZIP64_EOCD_LOCATOR_SIG_REVERSE_BYTE_ORDER;
    }

    public static long getZipEocdCentralDirectoryOffset(ByteBuffer byteBuffer) {
        assertByteOrderLittleEndian(byteBuffer);
        return getUnsignedInt32(byteBuffer, byteBuffer.position() + 16);
    }

    public static void setZipEocdCentralDirectoryOffset(ByteBuffer byteBuffer, long j) {
        assertByteOrderLittleEndian(byteBuffer);
        setUnsignedInt32(byteBuffer, byteBuffer.position() + 16, j);
    }

    public static long getZipEocdCentralDirectorySizeBytes(ByteBuffer byteBuffer) {
        assertByteOrderLittleEndian(byteBuffer);
        return getUnsignedInt32(byteBuffer, byteBuffer.position() + 12);
    }

    private static void assertByteOrderLittleEndian(ByteBuffer byteBuffer) {
        if (byteBuffer.order() != ByteOrder.LITTLE_ENDIAN) {
            throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
        }
    }

    private static int getUnsignedInt16(ByteBuffer byteBuffer, int i) {
        return byteBuffer.getShort(i) & 65535;
    }

    private static long getUnsignedInt32(ByteBuffer byteBuffer, int i) {
        return byteBuffer.getInt(i) & 4294967295L;
    }

    private static void setUnsignedInt32(ByteBuffer byteBuffer, int i, long j) {
        if (j < 0 || j > 4294967295L) {
            throw new IllegalArgumentException("uint32 value of out range: " + j);
        }
        byteBuffer.putInt(byteBuffer.position() + i, (int) j);
    }
}
