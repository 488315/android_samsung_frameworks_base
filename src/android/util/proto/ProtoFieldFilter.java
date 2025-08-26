package android.util.proto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public class ProtoFieldFilter {
    private static final int BUFFER_SIZE_BYTES = 4096;
    private final byte[] mBuffer;
    private final Predicate<Integer> mFieldPredicate;
    private final byte[] mVarIntBuffer;

    public ProtoFieldFilter(Predicate<Integer> predicate, int i) {
        this.mVarIntBuffer = new byte[10];
        this.mFieldPredicate = predicate;
        this.mBuffer = new byte[i];
    }

    public ProtoFieldFilter(Predicate<Integer> predicate) {
        this(predicate, 4096);
    }

    public void filter(InputStream inputStream, OutputStream outputStream) throws IOException {
        while (true) {
            int rawVarint = readRawVarint(inputStream);
            if (rawVarint <= 0) {
                return;
            }
            long varint = parseVarint(this.mVarIntBuffer, rawVarint);
            int i = (int) (varint >>> 3);
            int i2 = (int) (varint & 7);
            if (i == 0) {
                return;
            }
            if (this.mFieldPredicate.test(Integer.valueOf(i))) {
                outputStream.write(this.mVarIntBuffer, 0, rawVarint);
                copyFieldData(inputStream, outputStream, i2);
            } else {
                skipFieldData(inputStream, i2);
            }
        }
    }

    private int readRawVarint(InputStream inputStream) throws IOException {
        int i = inputStream.read();
        if (i < 0) {
            return 0;
        }
        this.mVarIntBuffer[0] = (byte) i;
        int i2 = 1;
        while ((i & 128) != 0) {
            i = inputStream.read();
            if (i < 0) {
                throw new IOException("Malformed varint: reached EOF mid-varint");
            }
            if (i2 >= 10) {
                throw new IOException("Malformed varint: too many bytes (max 10)");
            }
            this.mVarIntBuffer[i2] = (byte) i;
            i2++;
        }
        return i2;
    }

    private static long parseVarint(byte[] bArr, int i) throws IOException {
        long j = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            j |= (bArr[i3] & Byte.MAX_VALUE) << i2;
            i2 += 7;
            if (i2 > 63) {
                throw new IOException("Malformed varint: exceeds 64 bits");
            }
        }
        return j;
    }

    private void copyFieldData(InputStream inputStream, OutputStream outputStream, int i) throws IOException {
        if (i == 0) {
            copyVarint(inputStream, outputStream);
            return;
        }
        if (i == 1) {
            copyFixed(inputStream, outputStream, 8);
            return;
        }
        if (i == 2) {
            copyLengthDelimited(inputStream, outputStream);
        } else if (i == 5) {
            copyFixed(inputStream, outputStream, 4);
        } else {
            throw new IOException("Unknown or unsupported wire type: " + i);
        }
    }

    private void skipFieldData(InputStream inputStream, int i) throws IOException {
        if (i == 0) {
            skipVarint(inputStream);
            return;
        }
        if (i == 1) {
            skipBytes(inputStream, 8L);
            return;
        }
        if (i == 2) {
            skipLengthDelimited(inputStream);
        } else if (i == 5) {
            skipBytes(inputStream, 4L);
        } else {
            throw new IOException("Unknown or unsupported wire type: " + i);
        }
    }

    private static void copyVarint(InputStream inputStream, OutputStream outputStream) throws IOException {
        int i;
        do {
            i = inputStream.read();
            if (i < 0) {
                throw new IOException("EOF while copying varint");
            }
            outputStream.write(i);
        } while ((i & 128) != 0);
    }

    private void copyFixed(InputStream inputStream, OutputStream outputStream, int i) throws IOException {
        int i2 = i;
        while (i2 > 0) {
            int i3 = inputStream.read(this.mBuffer, 0, Math.min(i2, this.mBuffer.length));
            if (i3 < 0) {
                throw new IOException("EOF while copying fixed" + (i * 8) + " field");
            }
            outputStream.write(this.mBuffer, 0, i3);
            i2 -= i3;
        }
    }

    private void copyLengthDelimited(InputStream inputStream, OutputStream outputStream) throws IOException {
        int rawVarint = readRawVarint(inputStream);
        if (rawVarint <= 0) {
            throw new IOException("EOF reading length for length-delimited field");
        }
        outputStream.write(this.mVarIntBuffer, 0, rawVarint);
        long varint = parseVarint(this.mVarIntBuffer, rawVarint);
        if (varint < 0 || varint > 2147483647L) {
            throw new IOException("Invalid length for length-delimited field: " + varint);
        }
        copyFixed(inputStream, outputStream, (int) varint);
    }

    private static void skipVarint(InputStream inputStream) throws IOException {
        int i = 0;
        do {
            int i2 = inputStream.read();
            if (i2 < 0) {
                throw new IOException("EOF while skipping varint");
            }
            if ((i2 & 128) == 0) {
                return;
            } else {
                i++;
            }
        } while (i <= 10);
        throw new IOException("Malformed varint: exceeds maximum length of 10 bytes");
    }

    private void skipBytes(InputStream inputStream, long j) throws IOException {
        long jSkip = inputStream.skip(j);
        if (jSkip >= j) {
            return;
        }
        while (true) {
            j -= jSkip;
            if (j <= 0) {
                return;
            }
            int i = inputStream.read(this.mBuffer, 0, (int) Math.min(j, this.mBuffer.length));
            if (j < 0) {
                throw new IOException("EOF while skipping bytes");
            }
            jSkip = i;
        }
    }

    private void skipLengthDelimited(InputStream inputStream) throws IOException {
        int rawVarint = readRawVarint(inputStream);
        if (rawVarint <= 0) {
            throw new IOException("EOF reading length for length-delimited field");
        }
        long varint = parseVarint(this.mVarIntBuffer, rawVarint);
        if (varint < 0 || varint > 2147483647L) {
            throw new IOException("Invalid length to skip: " + varint);
        }
        skipBytes(inputStream, varint);
    }
}
