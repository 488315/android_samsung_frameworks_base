package com.android.internal.util;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.nio.charset.StandardCharsets;

/* loaded from: classes4.dex */
public class ProcFileReader implements Closeable {
    private final byte[] mBuffer;
    private boolean mLineFinished;
    private final InputStream mStream;
    private int mTail;

    public ProcFileReader(InputStream inputStream) throws IOException {
        this(inputStream, 4096);
    }

    public ProcFileReader(InputStream inputStream, int i) throws IOException {
        this.mStream = inputStream;
        this.mBuffer = new byte[i];
        if (inputStream.markSupported()) {
            inputStream.mark(0);
        }
        fillBuf();
    }

    private int fillBuf() throws IOException {
        byte[] bArr = this.mBuffer;
        int length = bArr.length;
        int i = this.mTail;
        int i2 = length - i;
        if (i2 == 0) {
            throw new IOException("attempting to fill already-full buffer");
        }
        int read = this.mStream.read(bArr, i, i2);
        if (read != -1) {
            this.mTail += read;
        }
        return read;
    }

    private void consumeBuf(int i) throws IOException {
        int i2;
        while (true) {
            i2 = this.mTail;
            if (i >= i2 || this.mBuffer[i] != 32) {
                break;
            } else {
                i++;
            }
        }
        byte[] bArr = this.mBuffer;
        System.arraycopy(bArr, i, bArr, 0, i2 - i);
        int i3 = this.mTail - i;
        this.mTail = i3;
        if (i3 == 0) {
            fillBuf();
            if (this.mTail <= 0 || this.mBuffer[0] != 32) {
                return;
            }
            consumeBuf(0);
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
                if (b == 32) {
                    return i;
                }
                i++;
            } else if (fillBuf() <= 0) {
                throw new ProtocolException("End of stream while looking for token boundary");
            }
        }
    }

    public boolean hasMoreData() {
        return this.mTail > 0;
    }

    public void finishLine() throws IOException {
        int i = 0;
        if (this.mLineFinished) {
            this.mLineFinished = false;
            return;
        }
        while (true) {
            if (i < this.mTail) {
                if (this.mBuffer[i] == 10) {
                    consumeBuf(i + 1);
                    return;
                }
                i++;
            } else if (fillBuf() <= 0) {
                throw new ProtocolException("End of stream while looking for line boundary");
            }
        }
    }

    public String nextString() throws IOException {
        int nextTokenIndex = nextTokenIndex();
        if (nextTokenIndex == -1) {
            throw new ProtocolException("Missing required string");
        }
        return parseAndConsumeString(nextTokenIndex);
    }

    public long nextLong() throws IOException {
        return nextLong(false);
    }

    public long nextLong(boolean z) throws IOException {
        int nextTokenIndex = nextTokenIndex();
        if (nextTokenIndex == -1) {
            throw new ProtocolException("Missing required long");
        }
        return parseAndConsumeLong(nextTokenIndex, z);
    }

    public long nextOptionalLong(long j) throws IOException {
        int nextTokenIndex = nextTokenIndex();
        return nextTokenIndex == -1 ? j : parseAndConsumeLong(nextTokenIndex, false);
    }

    private String parseAndConsumeString(int i) throws IOException {
        String str = new String(this.mBuffer, 0, i, StandardCharsets.US_ASCII);
        consumeBuf(i + 1);
        return str;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x003d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x003e  */
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
            if (r0 != r2) goto Lb
            r1 = r3
        Lb:
            r4 = 0
            r0 = r1
        Le:
            if (r0 >= r11) goto L37
            byte[] r2 = r10.mBuffer
            r2 = r2[r0]
            int r2 = r2 + (-48)
            if (r2 < 0) goto L2f
            r6 = 9
            if (r2 <= r6) goto L1d
            goto L2f
        L1d:
            r6 = 10
            long r6 = r6 * r4
            long r8 = (long) r2
            long r6 = r6 - r8
            int r2 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r2 > 0) goto L2a
            int r0 = r0 + 1
            r4 = r6
            goto Le
        L2a:
            java.lang.NumberFormatException r10 = r10.invalidLong(r11)
            throw r10
        L2f:
            if (r12 == 0) goto L32
            goto L37
        L32:
            java.lang.NumberFormatException r10 = r10.invalidLong(r11)
            throw r10
        L37:
            int r11 = r11 + r3
            r10.consumeBuf(r11)
            if (r1 == 0) goto L3e
            return r4
        L3e:
            long r10 = -r4
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.util.ProcFileReader.parseAndConsumeLong(int, boolean):long");
    }

    private NumberFormatException invalidLong(int i) {
        return new NumberFormatException("invalid long: ".concat(new String(this.mBuffer, 0, i, StandardCharsets.US_ASCII)));
    }

    public int nextInt() throws IOException {
        long nextLong = nextLong();
        if (nextLong > 2147483647L || nextLong < -2147483648L) {
            throw new NumberFormatException("parsed value larger than integer");
        }
        return (int) nextLong;
    }

    public void nextIgnored() throws IOException {
        int nextTokenIndex = nextTokenIndex();
        if (nextTokenIndex == -1) {
            throw new ProtocolException("Missing required token");
        }
        consumeBuf(nextTokenIndex + 1);
    }

    public void rewind() throws IOException {
        InputStream inputStream = this.mStream;
        if (inputStream instanceof FileInputStream) {
            ((FileInputStream) inputStream).getChannel().position(0L);
        } else if (inputStream.markSupported()) {
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
