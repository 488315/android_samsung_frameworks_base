package com.android.internal.util;

import com.android.internal.midi.MidiConstants;
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
        int i3 = this.mStream.read(bArr, i, i2);
        if (i3 != -1) {
            this.mTail += i3;
        }
        return i3;
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
        int iNextTokenIndex = nextTokenIndex();
        if (iNextTokenIndex == -1) {
            throw new ProtocolException("Missing required string");
        }
        return parseAndConsumeString(iNextTokenIndex);
    }

    public long nextLong() throws IOException {
        return nextLong(false);
    }

    public long nextLong(boolean z) throws IOException {
        int iNextTokenIndex = nextTokenIndex();
        if (iNextTokenIndex == -1) {
            throw new ProtocolException("Missing required long");
        }
        return parseAndConsumeLong(iNextTokenIndex, z);
    }

    public long nextOptionalLong(long j) throws IOException {
        int iNextTokenIndex = nextTokenIndex();
        return iNextTokenIndex == -1 ? j : parseAndConsumeLong(iNextTokenIndex, false);
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
    */
    private long parseAndConsumeLong(int i, boolean z) throws IOException {
        int i2 = this.mBuffer[0] == 45 ? 1 : 0;
        long j = 0;
        int i3 = i2;
        while (i3 < i) {
            int i4 = this.mBuffer[i3] + MidiConstants.STATUS_CHANNEL_PRESSURE;
            if (i4 < 0 || i4 > 9) {
                if (!z) {
                    throw invalidLong(i);
                }
                consumeBuf(i + 1);
                return i2 == 0 ? j : -j;
            }
            long j2 = (10 * j) - i4;
            if (j2 > j) {
                throw invalidLong(i);
            }
            i3++;
            j = j2;
        }
        consumeBuf(i + 1);
        if (i2 == 0) {
        }
    }

    private NumberFormatException invalidLong(int i) {
        return new NumberFormatException("invalid long: ".concat(new String(this.mBuffer, 0, i, StandardCharsets.US_ASCII)));
    }

    public int nextInt() throws IOException {
        long jNextLong = nextLong();
        if (jNextLong > 2147483647L || jNextLong < -2147483648L) {
            throw new NumberFormatException("parsed value larger than integer");
        }
        return (int) jNextLong;
    }

    public void nextIgnored() throws IOException {
        int iNextTokenIndex = nextTokenIndex();
        if (iNextTokenIndex == -1) {
            throw new ProtocolException("Missing required token");
        }
        consumeBuf(iNextTokenIndex + 1);
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
