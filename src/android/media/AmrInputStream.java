package android.media;

import android.media.MediaCodec;
import android.util.Log;
import android.view.Surface;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
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
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString("mime", "audio/3gpp");
        mediaFormat.setInteger(MediaFormat.KEY_SAMPLE_RATE, 8000);
        mediaFormat.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1);
        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 12200);
        String strFindEncoderForFormat = new MediaCodecList(0).findEncoderForFormat(mediaFormat);
        if (strFindEncoderForFormat != null) {
            try {
                MediaCodec mediaCodecCreateByCodecName = MediaCodec.createByCodecName(strFindEncoderForFormat);
                this.mCodec = mediaCodecCreateByCodecName;
                mediaCodecCreateByCodecName.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 1);
                this.mCodec.start();
            } catch (IOException unused) {
                MediaCodec mediaCodec = this.mCodec;
                if (mediaCodec != null) {
                    mediaCodec.release();
                }
                this.mCodec = null;
            }
        }
        this.mInfo = new MediaCodec.BufferInfo();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.mOneByte, 0, 1) == 1) {
            return this.mOneByte[0] & 255;
        }
        return -1;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws MediaCodec.CryptoException, IOException {
        int iDequeueInputBuffer;
        if (this.mCodec == null) {
            throw new IllegalStateException("not open");
        }
        if (this.mBufOut >= this.mBufIn && !this.mSawOutputEOS) {
            this.mBufOut = 0;
            this.mBufIn = 0;
            while (!this.mSawInputEOS && (iDequeueInputBuffer = this.mCodec.dequeueInputBuffer(0L)) >= 0) {
                int i3 = 0;
                while (true) {
                    if (i3 >= 320) {
                        break;
                    }
                    int i4 = this.mInputStream.read(this.mBuf, i3, 320 - i3);
                    if (i4 == -1) {
                        this.mSawInputEOS = true;
                        break;
                    }
                    i3 += i4;
                }
                this.mCodec.getInputBuffer(iDequeueInputBuffer).put(this.mBuf, 0, i3);
                this.mCodec.queueInputBuffer(iDequeueInputBuffer, 0, i3, 0L, this.mSawInputEOS ? 4 : 0);
            }
            int iDequeueOutputBuffer = this.mCodec.dequeueOutputBuffer(this.mInfo, 0L);
            if (iDequeueOutputBuffer >= 0) {
                this.mBufIn = this.mInfo.size;
                this.mCodec.getOutputBuffer(iDequeueOutputBuffer).get(this.mBuf, 0, this.mBufIn);
                this.mCodec.releaseOutputBuffer(iDequeueOutputBuffer, false);
                if ((this.mInfo.flags & 4) != 0) {
                    this.mSawOutputEOS = true;
                }
            }
        }
        int i5 = this.mBufOut;
        int i6 = this.mBufIn;
        if (i5 >= i6) {
            return (this.mSawInputEOS && this.mSawOutputEOS) ? -1 : 0;
        }
        int i7 = i2 > i6 - i5 ? i6 - i5 : i2;
        System.arraycopy(this.mBuf, i5, bArr, i, i7);
        this.mBufOut += i7;
        return i7;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        try {
            InputStream inputStream = this.mInputStream;
            if (inputStream != null) {
                inputStream.close();
            }
            this.mInputStream = null;
            try {
                MediaCodec mediaCodec = this.mCodec;
                if (mediaCodec != null) {
                    mediaCodec.release();
                }
            } finally {
            }
        } catch (Throwable th) {
            this.mInputStream = null;
            try {
                MediaCodec mediaCodec2 = this.mCodec;
                if (mediaCodec2 != null) {
                    mediaCodec2.release();
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
