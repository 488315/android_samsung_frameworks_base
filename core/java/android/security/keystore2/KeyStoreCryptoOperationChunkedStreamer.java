package android.security.keystore2;

import android.security.KeyStoreException;
import android.security.KeyStoreOperation;
import android.security.keystore.ArrayUtils;

import libcore.util.EmptyArray;

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

    KeyStoreCryptoOperationChunkedStreamer(Stream operation) {
        this(operation, 2048, 32768);
    }

    KeyStoreCryptoOperationChunkedStreamer(Stream operation, int chunkSizeThreshold) {
        this(operation, chunkSizeThreshold, 32768);
    }

    KeyStoreCryptoOperationChunkedStreamer(
            Stream operation, int chunkSizeThreshold, int chunkSizeMax) {
        this.mChunkLength = 0;
        this.mChunkLength = 0;
        this.mConsumedInputSizeBytes = 0L;
        this.mProducedOutputSizeBytes = 0L;
        this.mKeyStoreStream = operation;
        this.mChunkSizeMax = chunkSizeMax;
        if (chunkSizeThreshold <= 0) {
            this.mChunkSizeThreshold = 1;
        } else if (chunkSizeThreshold > chunkSizeMax) {
            this.mChunkSizeThreshold = chunkSizeMax;
        } else {
            this.mChunkSizeThreshold = chunkSizeThreshold;
        }
        this.mChunk = new byte[this.mChunkSizeMax];
    }

    @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
    public byte[] update(byte[] input, int inputOffset, int inputLength) throws KeyStoreException {
        if (inputLength == 0 || input == null) {
            return EmptyArray.BYTE;
        }
        if (inputLength < 0 || inputOffset < 0 || inputOffset + inputLength > input.length) {
            throw new KeyStoreException(
                    -1000, "Input offset and length out of bounds of input array");
        }
        byte[] output = EmptyArray.BYTE;
        if (this.mChunkLength > 0) {
            int inputConsumed =
                    ArrayUtils.copy(
                            input, inputOffset, this.mChunk, this.mChunkLength, inputLength);
            inputLength -= inputConsumed;
            inputOffset += inputConsumed;
            this.mChunkLength += inputConsumed;
            if (this.mChunkLength < this.mChunkSizeMax) {
                return output;
            }
            byte[] o = this.mKeyStoreStream.update(this.mChunk);
            if (o != null) {
                output = ArrayUtils.concat(output, o);
            }
            this.mConsumedInputSizeBytes += inputConsumed;
            this.mChunkLength = 0;
        }
        while (inputLength >= this.mChunkSizeThreshold) {
            int nextChunkSize = inputLength < this.mChunkSizeMax ? inputLength : this.mChunkSizeMax;
            byte[] o2 =
                    this.mKeyStoreStream.update(
                            ArrayUtils.subarray(input, inputOffset, nextChunkSize));
            inputLength -= nextChunkSize;
            inputOffset += nextChunkSize;
            this.mConsumedInputSizeBytes += nextChunkSize;
            if (o2 != null) {
                output = ArrayUtils.concat(output, o2);
            }
        }
        if (inputLength > 0) {
            this.mChunkLength = ArrayUtils.copy(input, inputOffset, this.mChunk, 0, inputLength);
            this.mConsumedInputSizeBytes += inputLength;
        }
        this.mProducedOutputSizeBytes += output.length;
        return output;
    }

    @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
    public byte[] doFinal(byte[] input, int inputOffset, int inputLength, byte[] signature)
            throws KeyStoreException {
        byte[] output = update(input, inputOffset, inputLength);
        byte[] finalChunk = ArrayUtils.subarray(this.mChunk, 0, this.mChunkLength);
        byte[] o = this.mKeyStoreStream.finish(finalChunk, signature);
        if (o != null) {
            this.mProducedOutputSizeBytes += o.length;
            if (output != null) {
                return ArrayUtils.concat(output, o);
            }
            return o;
        }
        return output;
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

        MainDataStream(KeyStoreOperation operation) {
            this.mOperation = operation;
        }

        @Override // android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.Stream
        public byte[] update(byte[] input) throws KeyStoreException {
            return this.mOperation.update(input);
        }

        @Override // android.security.keystore2.KeyStoreCryptoOperationChunkedStreamer.Stream
        public byte[] finish(byte[] input, byte[] signature) throws KeyStoreException {
            return this.mOperation.finish(input, signature);
        }
    }
}
