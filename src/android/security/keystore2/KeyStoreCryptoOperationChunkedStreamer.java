package android.security.keystore2;

import android.security.KeyStoreException;
import android.security.KeyStoreOperation;
import android.security.keystore.ArrayUtils;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
class KeyStoreCryptoOperationChunkedStreamer implements KeyStoreCryptoOperationStreamer {
    private static final int DEFAULT_CHUNK_SIZE_MAX = 32768;
    private static final int DEFAULT_CHUNK_SIZE_THRESHOLD = 2048;
    private final byte[] mChunk;
    private int mChunkLength;
    private final int mChunkSizeMax;
    private final int mChunkSizeThreshold;
    private long mConsumedInputSizeBytes;
    private final Stream mKeyStoreStream;
    private long mProducedOutputSizeBytes;

    interface Stream {
        byte[] finish(byte[] bArr, byte[] bArr2) throws KeyStoreException;

        byte[] update(byte[] bArr) throws KeyStoreException;
    }

    KeyStoreCryptoOperationChunkedStreamer(Stream stream) {
        this(stream, 2048, 32768);
    }

    KeyStoreCryptoOperationChunkedStreamer(Stream stream, int i) {
        this(stream, i, 32768);
    }

    KeyStoreCryptoOperationChunkedStreamer(Stream stream, int i, int i2) {
        this.mChunkLength = 0;
        this.mConsumedInputSizeBytes = 0L;
        this.mProducedOutputSizeBytes = 0L;
        this.mKeyStoreStream = stream;
        this.mChunkSizeMax = i2;
        if (i <= 0) {
            this.mChunkSizeThreshold = 1;
        } else if (i > i2) {
            this.mChunkSizeThreshold = i2;
        } else {
            this.mChunkSizeThreshold = i;
        }
        this.mChunk = new byte[i2];
    }

    @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
    public byte[] update(byte[] bArr, int i, int i2) throws KeyStoreException {
        if (i2 == 0 || bArr == null) {
            return EmptyArray.BYTE;
        }
        if (i2 < 0 || i < 0 || i + i2 > bArr.length) {
            throw new KeyStoreException(-1000, "Input offset and length out of bounds of input array");
        }
        byte[] bArrConcat = EmptyArray.BYTE;
        int i3 = this.mChunkLength;
        if (i3 > 0) {
            int iCopy = ArrayUtils.copy(bArr, i, this.mChunk, i3, i2);
            i2 -= iCopy;
            i += iCopy;
            int i4 = this.mChunkLength + iCopy;
            this.mChunkLength = i4;
            if (i4 < this.mChunkSizeMax) {
                return bArrConcat;
            }
            byte[] bArrUpdate = this.mKeyStoreStream.update(this.mChunk);
            if (bArrUpdate != null) {
                bArrConcat = ArrayUtils.concat(bArrConcat, bArrUpdate);
            }
            this.mConsumedInputSizeBytes += iCopy;
            this.mChunkLength = 0;
        }
        while (i2 >= this.mChunkSizeThreshold) {
            int i5 = this.mChunkSizeMax;
            if (i2 < i5) {
                i5 = i2;
            }
            byte[] bArrUpdate2 = this.mKeyStoreStream.update(ArrayUtils.subarray(bArr, i, i5));
            i2 -= i5;
            i += i5;
            this.mConsumedInputSizeBytes += i5;
            if (bArrUpdate2 != null) {
                bArrConcat = ArrayUtils.concat(bArrConcat, bArrUpdate2);
            }
        }
        if (i2 > 0) {
            this.mChunkLength = ArrayUtils.copy(bArr, i, this.mChunk, 0, i2);
            this.mConsumedInputSizeBytes += i2;
        }
        this.mProducedOutputSizeBytes += bArrConcat.length;
        return bArrConcat;
    }

    @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
    public byte[] doFinal(byte[] bArr, int i, int i2, byte[] bArr2) throws KeyStoreException {
        byte[] bArrUpdate = update(bArr, i, i2);
        byte[] bArrFinish = this.mKeyStoreStream.finish(ArrayUtils.subarray(this.mChunk, 0, this.mChunkLength), bArr2);
        if (bArrFinish == null) {
            return bArrUpdate;
        }
        this.mProducedOutputSizeBytes += bArrFinish.length;
        return bArrUpdate != null ? ArrayUtils.concat(bArrUpdate, bArrFinish) : bArrFinish;
    }

    @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
    public long getConsumedInputSizeBytes() {
        return this.mConsumedInputSizeBytes;
    }

    @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
    public long getProducedOutputSizeBytes() {
        return this.mProducedOutputSizeBytes;
    }

    public static class MainDataStream implements Stream {
        private final KeyStoreOperation mOperation;

        MainDataStream(KeyStoreOperation keyStoreOperation) {
            this.mOperation = keyStoreOperation;
        }

        @Override // android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.Stream
        public byte[] update(byte[] bArr) throws KeyStoreException {
            return this.mOperation.update(bArr);
        }

        @Override // android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.Stream
        public byte[] finish(byte[] bArr, byte[] bArr2) throws KeyStoreException {
            return this.mOperation.finish(bArr, bArr2);
        }
    }
}
