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
            int readRawVarint = readRawVarint(inputStream);
            if (readRawVarint <= 0) {
                return;
            }
            long parseVarint = parseVarint(this.mVarIntBuffer, readRawVarint);
            int i = (int) (parseVarint >>> 3);
            int i2 = (int) (parseVarint & 7);
            if (i == 0) {
                return;
            }
            if (this.mFieldPredicate.test(Integer.valueOf(i))) {
                outputStream.write(this.mVarIntBuffer, 0, readRawVarint);
                copyFieldData(inputStream, outputStream, i2);
            } else {
                skipFieldData(inputStream, i2);
            }
        }
    }

    private int readRawVarint(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        if (read < 0) {
            return 0;
        }
        this.mVarIntBuffer[0] = (byte) read;
        int i = 1;
        while ((read & 128) != 0) {
            read = inputStream.read();
            if (read < 0) {
                throw new IOException("Malformed varint: reached EOF mid-varint");
            }
            if (i >= 10) {
                throw new IOException("Malformed varint: too many bytes (max 10)");
            }
            this.mVarIntBuffer[i] = (byte) read;
            i++;
        }
        return i;
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
        int read;
        do {
            read = inputStream.read();
            if (read < 0) {
                throw new IOException("EOF while copying varint");
            }
            outputStream.write(read);
        } while ((read & 128) != 0);
    }

    private void copyFixed(InputStream inputStream, OutputStream outputStream, int i) throws IOException {
        int i2 = i;
        while (i2 > 0) {
            int read = inputStream.read(this.mBuffer, 0, Math.min(i2, this.mBuffer.length));
            if (read < 0) {
                throw new IOException("EOF while copying fixed" + (i * 8) + " field");
            }
            outputStream.write(this.mBuffer, 0, read);
            i2 -= read;
        }
    }

    private void copyLengthDelimited(InputStream inputStream, OutputStream outputStream) throws IOException {
        int readRawVarint = readRawVarint(inputStream);
        if (readRawVarint <= 0) {
            throw new IOException("EOF reading length for length-delimited field");
        }
        outputStream.write(this.mVarIntBuffer, 0, readRawVarint);
        long parseVarint = parseVarint(this.mVarIntBuffer, readRawVarint);
        if (parseVarint < 0 || parseVarint > 2147483647L) {
            throw new IOException("Invalid length for length-delimited field: " + parseVarint);
        }
        copyFixed(inputStream, outputStream, (int) parseVarint);
    }

    private static void skipVarint(InputStream inputStream) throws IOException {
        int i = 0;
        do {
            int read = inputStream.read();
            if (read < 0) {
                throw new IOException("EOF while skipping varint");
            }
            if ((read & 128) == 0) {
                return;
            } else {
                i++;
            }
        } while (i <= 10);
        throw new IOException("Malformed varint: exceeds maximum length of 10 bytes");
    }

    private void skipBytes(InputStream inputStream, long j) throws IOException {
        long skip = inputStream.skip(j);
        if (skip >= j) {
            return;
        }
        while (true) {
            j -= skip;
            if (j <= 0) {
                return;
            }
            int read = inputStream.read(this.mBuffer, 0, (int) Math.min(j, this.mBuffer.length));
            if (j < 0) {
                throw new IOException("EOF while skipping bytes");
            }
            skip = read;
        }
    }

    private void skipLengthDelimited(InputStream inputStream) throws IOException {
        int readRawVarint = readRawVarint(inputStream);
        if (readRawVarint <= 0) {
            throw new IOException("EOF reading length for length-delimited field");
        }
        long parseVarint = parseVarint(this.mVarIntBuffer, readRawVarint);
        if (parseVarint < 0 || parseVarint > 2147483647L) {
            throw new IOException("Invalid length to skip: " + parseVarint);
        }
        skipBytes(inputStream, parseVarint);
    }
}
