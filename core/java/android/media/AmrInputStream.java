package android.media;

import android.util.Log;
import android.view.Surface;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class AmrInputStream extends InputStream {
    private static final int SAMPLES_PER_FRAME = 160;
    private static final String TAG = "AmrInputStream";
    MediaCodec mCodec;
    MediaCodec.BufferInfo mInfo;
    private InputStream mInputStream;
    boolean mSawInputEOS;
    boolean mSawOutputEOS;
    private final byte[] mBuf = new byte[320];
    private int mBufIn = 0;
    private int mBufOut = 0;
    private byte[] mOneByte = new byte[1];

    public AmrInputStream(InputStream inputStream) {
        Log.w(TAG, "@@@@ AmrInputStream is not a public API @@@@");
        this.mInputStream = inputStream;
        MediaFormat format = new MediaFormat();
        format.setString("mime", "audio/3gpp");
        format.setInteger(MediaFormat.KEY_SAMPLE_RATE, 8000);
        format.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1);
        format.setInteger(MediaFormat.KEY_BIT_RATE, 12200);
        MediaCodecList mcl = new MediaCodecList(0);
        String name = mcl.findEncoderForFormat(format);
        if (name != null) {
            try {
                this.mCodec = MediaCodec.createByCodecName(name);
                this.mCodec.configure(format, (Surface) null, (MediaCrypto) null, 1);
                this.mCodec.start();
            } catch (IOException e) {
                if (this.mCodec != null) {
                    this.mCodec.release();
                }
                this.mCodec = null;
            }
        }
        this.mInfo = new MediaCodec.BufferInfo();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int rtn = read(this.mOneByte, 0, 1);
        if (rtn == 1) {
            return this.mOneByte[0] & 255;
        }
        return -1;
    }

    @Override // java.io.InputStream
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] b, int offset, int length) throws IOException {
        int length2;
        int index;
        if (this.mCodec == null) {
            throw new IllegalStateException("not open");
        }
        if (this.mBufOut >= this.mBufIn && !this.mSawOutputEOS) {
            this.mBufOut = 0;
            this.mBufIn = 0;
            while (!this.mSawInputEOS && (index = this.mCodec.dequeueInputBuffer(0L)) >= 0) {
                int numRead = 0;
                while (true) {
                    if (numRead >= 320) {
                        break;
                    }
                    int n = this.mInputStream.read(this.mBuf, numRead, 320 - numRead);
                    if (n == -1) {
                        this.mSawInputEOS = true;
                        break;
                    }
                    numRead += n;
                }
                ByteBuffer buf = this.mCodec.getInputBuffer(index);
                buf.put(this.mBuf, 0, numRead);
                this.mCodec.queueInputBuffer(index, 0, numRead, 0L, this.mSawInputEOS ? 4 : 0);
            }
            int index2 = this.mCodec.dequeueOutputBuffer(this.mInfo, 0L);
            if (index2 >= 0) {
                this.mBufIn = this.mInfo.size;
                ByteBuffer out = this.mCodec.getOutputBuffer(index2);
                out.get(this.mBuf, 0, this.mBufIn);
                this.mCodec.releaseOutputBuffer(index2, false);
                if ((4 & this.mInfo.flags) != 0) {
                    this.mSawOutputEOS = true;
                }
            }
        }
        if (this.mBufOut >= this.mBufIn) {
            return (this.mSawInputEOS && this.mSawOutputEOS) ? -1 : 0;
        }
        if (length > this.mBufIn - this.mBufOut) {
            length2 = this.mBufIn - this.mBufOut;
        } else {
            length2 = length;
        }
        System.arraycopy(this.mBuf, this.mBufOut, b, offset, length2);
        this.mBufOut += length2;
        return length2;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            if (this.mInputStream != null) {
                this.mInputStream.close();
            }
            this.mInputStream = null;
            try {
                if (this.mCodec != null) {
                    this.mCodec.release();
                }
            } finally {
            }
        } catch (Throwable th) {
            this.mInputStream = null;
            try {
                if (this.mCodec != null) {
                    this.mCodec.release();
                }
                throw th;
            } finally {
            }
        }
    }

    protected void finalize() throws Throwable {
        if (this.mCodec != null) {
            Log.w(TAG, "AmrInputStream wasn't closed");
            this.mCodec.release();
        }
    }
}
