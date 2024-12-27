package com.android.internal.util;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.nio.charset.StandardCharsets;

/* loaded from: classes5.dex */
public class ProcFileReader implements Closeable {
    private final byte[] mBuffer;
    private boolean mLineFinished;
    private final InputStream mStream;
    private int mTail;

    public ProcFileReader(InputStream stream) throws IOException {
        this(stream, 4096);
    }

    public ProcFileReader(InputStream stream, int bufferSize) throws IOException {
        this.mStream = stream;
        this.mBuffer = new byte[bufferSize];
        if (stream.markSupported()) {
            this.mStream.mark(0);
        }
        fillBuf();
    }

    private int fillBuf() throws IOException {
        int length = this.mBuffer.length - this.mTail;
        if (length == 0) {
            throw new IOException("attempting to fill already-full buffer");
        }
        int read = this.mStream.read(this.mBuffer, this.mTail, length);
        if (read != -1) {
            this.mTail += read;
        }
        return read;
    }

    private void consumeBuf(int count) throws IOException {
        while (count < this.mTail && this.mBuffer[count] == 32) {
            count++;
        }
        System.arraycopy(this.mBuffer, count, this.mBuffer, 0, this.mTail - count);
        this.mTail -= count;
        if (this.mTail == 0) {
            fillBuf();
            if (this.mTail > 0 && this.mBuffer[0] == 32) {
                consumeBuf(0);
            }
        }
    }

    private int nextTokenIndex() throws IOException {
        if (this.mLineFinished) {
            return -1;
        }
        int i = 0;
        while (true) {
            if (i < this.mTail) {
                byte b = this.mBuffer[i];
                if (b == 10) {
                    this.mLineFinished = true;
                    return i;
                }
                if (b != 32) {
                    i++;
                } else {
                    return i;
                }
            } else if (fillBuf() <= 0) {
                throw new ProtocolException("End of stream while looking for token boundary");
            }
        }
    }

    public boolean hasMoreData() {
        return this.mTail > 0;
    }

    public void finishLine() throws IOException {
        if (this.mLineFinished) {
            this.mLineFinished = false;
            return;
        }
        int i = 0;
        while (true) {
            if (i < this.mTail) {
                if (this.mBuffer[i] != 10) {
                    i++;
                } else {
                    consumeBuf(i + 1);
                    return;
                }
            } else if (fillBuf() <= 0) {
                throw new ProtocolException("End of stream while looking for line boundary");
            }
        }
    }

    public String nextString() throws IOException {
        int tokenIndex = nextTokenIndex();
        if (tokenIndex == -1) {
            throw new ProtocolException("Missing required string");
        }
        return parseAndConsumeString(tokenIndex);
    }

    public long nextLong() throws IOException {
        return nextLong(false);
    }

    public long nextLong(boolean stopAtInvalid) throws IOException {
        int tokenIndex = nextTokenIndex();
        if (tokenIndex == -1) {
            throw new ProtocolException("Missing required long");
        }
        return parseAndConsumeLong(tokenIndex, stopAtInvalid);
    }

    public long nextOptionalLong(long def) throws IOException {
        int tokenIndex = nextTokenIndex();
        if (tokenIndex == -1) {
            return def;
        }
        return parseAndConsumeLong(tokenIndex, false);
    }

    private String parseAndConsumeString(int tokenIndex) throws IOException {
        String s = new String(this.mBuffer, 0, tokenIndex, StandardCharsets.US_ASCII);
        consumeBuf(tokenIndex + 1);
        return s;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private long parseAndConsumeLong(int r11, boolean r12) throws java.io.IOException {
        /*
            r10 = this;
            byte[] r0 = r10.mBuffer
            r1 = 0
            r0 = r0[r1]
            r2 = 45
            r3 = 1
            if (r0 != r2) goto Lc
            r0 = r3
            goto Ld
        Lc:
            r0 = r1
        Ld:
            r4 = 0
            if (r0 == 0) goto L12
            r1 = r3
        L12:
            if (r1 >= r11) goto L3b
            byte[] r2 = r10.mBuffer
            r2 = r2[r1]
            int r2 = r2 + (-48)
            if (r2 < 0) goto L33
            r3 = 9
            if (r2 <= r3) goto L21
            goto L33
        L21:
            r6 = 10
            long r6 = r6 * r4
            long r8 = (long) r2
            long r6 = r6 - r8
            int r3 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r3 > 0) goto L2e
            r4 = r6
            int r1 = r1 + 1
            goto L12
        L2e:
            java.lang.NumberFormatException r3 = r10.invalidLong(r11)
            throw r3
        L33:
            if (r12 == 0) goto L36
            goto L3b
        L36:
            java.lang.NumberFormatException r3 = r10.invalidLong(r11)
            throw r3
        L3b:
            int r1 = r11 + 1
            r10.consumeBuf(r1)
            if (r0 == 0) goto L44
            r1 = r4
            goto L45
        L44:
            long r1 = -r4
        L45:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.util.ProcFileReader.parseAndConsumeLong(int,"
                    + " boolean):long");
    }

    private NumberFormatException invalidLong(int tokenIndex) {
        return new NumberFormatException(
                "invalid long: "
                        + new String(this.mBuffer, 0, tokenIndex, StandardCharsets.US_ASCII));
    }

    public int nextInt() throws IOException {
        long value = nextLong();
        if (value > 2147483647L || value < -2147483648L) {
            throw new NumberFormatException("parsed value larger than integer");
        }
        return (int) value;
    }

    public void nextIgnored() throws IOException {
        int tokenIndex = nextTokenIndex();
        if (tokenIndex == -1) {
            throw new ProtocolException("Missing required token");
        }
        consumeBuf(tokenIndex + 1);
    }

    public void rewind() throws IOException {
        if (this.mStream instanceof FileInputStream) {
            ((FileInputStream) this.mStream).getChannel().position(0L);
        } else if (this.mStream.markSupported()) {
            this.mStream.reset();
        } else {
            throw new IOException("The InputStream is NOT markable");
        }
        this.mTail = 0;
        this.mLineFinished = false;
        fillBuf();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.mStream.close();
    }
}
