package android.stats.camera.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;

import java.io.IOException;

public interface CameraProtos {

    public static final class CameraStreamProto extends MessageNano {
        public static final int CAPTURE_LATENCY = 1;
        public static final int UNKNOWN = 0;
        private static volatile CameraStreamProto[] _emptyArray;
        public int colorSpace;
        public int dataSpace;
        public long dynamicRangeProfile;
        public long errorCount;
        public int firstCaptureLatencyMillis;
        public int format;
        public int height;
        public float[] histogramBins;
        public long[] histogramCounts;
        public int histogramType;
        public int maxAppBuffers;
        public int maxHalBuffers;
        public long requestCount;
        public long streamUseCase;
        public long usage;
        public int width;

        public static CameraStreamProto[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new CameraStreamProto[0];
                    }
                }
            }
            return _emptyArray;
        }

        public CameraStreamProto() {
            clear();
        }

        public CameraStreamProto clear() {
            this.width = 0;
            this.height = 0;
            this.format = 0;
            this.dataSpace = 0;
            this.usage = 0L;
            this.requestCount = 0L;
            this.errorCount = 0L;
            this.firstCaptureLatencyMillis = 0;
            this.maxHalBuffers = 0;
            this.maxAppBuffers = 0;
            this.histogramType = 0;
            this.histogramBins = WireFormatNano.EMPTY_FLOAT_ARRAY;
            this.histogramCounts = WireFormatNano.EMPTY_LONG_ARRAY;
            this.dynamicRangeProfile = 0L;
            this.streamUseCase = 0L;
            this.colorSpace = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (this.width != 0) {
                output.writeInt32(1, this.width);
            }
            if (this.height != 0) {
                output.writeInt32(2, this.height);
            }
            if (this.format != 0) {
                output.writeInt32(3, this.format);
            }
            if (this.dataSpace != 0) {
                output.writeInt32(4, this.dataSpace);
            }
            if (this.usage != 0) {
                output.writeInt64(5, this.usage);
            }
            if (this.requestCount != 0) {
                output.writeInt64(6, this.requestCount);
            }
            if (this.errorCount != 0) {
                output.writeInt64(7, this.errorCount);
            }
            if (this.firstCaptureLatencyMillis != 0) {
                output.writeInt32(8, this.firstCaptureLatencyMillis);
            }
            if (this.maxHalBuffers != 0) {
                output.writeInt32(9, this.maxHalBuffers);
            }
            if (this.maxAppBuffers != 0) {
                output.writeInt32(10, this.maxAppBuffers);
            }
            if (this.histogramType != 0) {
                output.writeInt32(11, this.histogramType);
            }
            if (this.histogramBins != null && this.histogramBins.length > 0) {
                for (int i = 0; i < this.histogramBins.length; i++) {
                    output.writeFloat(12, this.histogramBins[i]);
                }
            }
            if (this.histogramCounts != null && this.histogramCounts.length > 0) {
                for (int i2 = 0; i2 < this.histogramCounts.length; i2++) {
                    output.writeInt64(13, this.histogramCounts[i2]);
                }
            }
            if (this.dynamicRangeProfile != 0) {
                output.writeInt64(14, this.dynamicRangeProfile);
            }
            if (this.streamUseCase != 0) {
                output.writeInt64(15, this.streamUseCase);
            }
            if (this.colorSpace != 0) {
                output.writeInt32(16, this.colorSpace);
            }
            super.writeTo(output);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (this.width != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(1, this.width);
            }
            if (this.height != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(2, this.height);
            }
            if (this.format != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(3, this.format);
            }
            if (this.dataSpace != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(4, this.dataSpace);
            }
            if (this.usage != 0) {
                size += CodedOutputByteBufferNano.computeInt64Size(5, this.usage);
            }
            if (this.requestCount != 0) {
                size += CodedOutputByteBufferNano.computeInt64Size(6, this.requestCount);
            }
            if (this.errorCount != 0) {
                size += CodedOutputByteBufferNano.computeInt64Size(7, this.errorCount);
            }
            if (this.firstCaptureLatencyMillis != 0) {
                size +=
                        CodedOutputByteBufferNano.computeInt32Size(
                                8, this.firstCaptureLatencyMillis);
            }
            if (this.maxHalBuffers != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(9, this.maxHalBuffers);
            }
            if (this.maxAppBuffers != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(10, this.maxAppBuffers);
            }
            if (this.histogramType != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(11, this.histogramType);
            }
            if (this.histogramBins != null && this.histogramBins.length > 0) {
                int dataSize = this.histogramBins.length * 4;
                size = size + dataSize + (this.histogramBins.length * 1);
            }
            if (this.histogramCounts != null && this.histogramCounts.length > 0) {
                int dataSize2 = 0;
                for (int i = 0; i < this.histogramCounts.length; i++) {
                    long element = this.histogramCounts[i];
                    dataSize2 += CodedOutputByteBufferNano.computeInt64SizeNoTag(element);
                }
                size = size + dataSize2 + (this.histogramCounts.length * 1);
            }
            if (this.dynamicRangeProfile != 0) {
                size += CodedOutputByteBufferNano.computeInt64Size(14, this.dynamicRangeProfile);
            }
            if (this.streamUseCase != 0) {
                size += CodedOutputByteBufferNano.computeInt64Size(15, this.streamUseCase);
            }
            if (this.colorSpace != 0) {
                return size + CodedOutputByteBufferNano.computeInt32Size(16, this.colorSpace);
            }
            return size;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public CameraStreamProto mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 8:
                        this.width = input.readInt32();
                        break;
                    case 16:
                        this.height = input.readInt32();
                        break;
                    case 24:
                        this.format = input.readInt32();
                        break;
                    case 32:
                        this.dataSpace = input.readInt32();
                        break;
                    case 40:
                        this.usage = input.readInt64();
                        break;
                    case 48:
                        this.requestCount = input.readInt64();
                        break;
                    case 56:
                        this.errorCount = input.readInt64();
                        break;
                    case 64:
                        this.firstCaptureLatencyMillis = input.readInt32();
                        break;
                    case 72:
                        this.maxHalBuffers = input.readInt32();
                        break;
                    case 80:
                        this.maxAppBuffers = input.readInt32();
                        break;
                    case 88:
                        int value = input.readInt32();
                        switch (value) {
                            case 0:
                            case 1:
                                this.histogramType = value;
                                break;
                        }
                    case 98:
                        int length = input.readRawVarint32();
                        int limit = input.pushLimit(length);
                        int arrayLength = length / 4;
                        int i = this.histogramBins == null ? 0 : this.histogramBins.length;
                        float[] newArray = new float[i + arrayLength];
                        if (i != 0) {
                            System.arraycopy(this.histogramBins, 0, newArray, 0, i);
                        }
                        while (i < newArray.length) {
                            newArray[i] = input.readFloat();
                            i++;
                        }
                        this.histogramBins = newArray;
                        input.popLimit(limit);
                        break;
                    case 101:
                        int arrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(input, 101);
                        int i2 = this.histogramBins == null ? 0 : this.histogramBins.length;
                        float[] newArray2 = new float[i2 + arrayLength2];
                        if (i2 != 0) {
                            System.arraycopy(this.histogramBins, 0, newArray2, 0, i2);
                        }
                        while (i2 < newArray2.length - 1) {
                            newArray2[i2] = input.readFloat();
                            input.readTag();
                            i2++;
                        }
                        newArray2[i2] = input.readFloat();
                        this.histogramBins = newArray2;
                        break;
                    case 104:
                        int arrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(input, 104);
                        int i3 = this.histogramCounts == null ? 0 : this.histogramCounts.length;
                        long[] newArray3 = new long[i3 + arrayLength3];
                        if (i3 != 0) {
                            System.arraycopy(this.histogramCounts, 0, newArray3, 0, i3);
                        }
                        while (i3 < newArray3.length - 1) {
                            newArray3[i3] = input.readInt64();
                            input.readTag();
                            i3++;
                        }
                        newArray3[i3] = input.readInt64();
                        this.histogramCounts = newArray3;
                        break;
                    case 106:
                        int limit2 = input.pushLimit(input.readRawVarint32());
                        int arrayLength4 = 0;
                        int startPos = input.getPosition();
                        while (input.getBytesUntilLimit() > 0) {
                            input.readInt64();
                            arrayLength4++;
                        }
                        input.rewindToPosition(startPos);
                        int i4 = this.histogramCounts == null ? 0 : this.histogramCounts.length;
                        long[] newArray4 = new long[i4 + arrayLength4];
                        if (i4 != 0) {
                            System.arraycopy(this.histogramCounts, 0, newArray4, 0, i4);
                        }
                        while (i4 < newArray4.length) {
                            newArray4[i4] = input.readInt64();
                            i4++;
                        }
                        this.histogramCounts = newArray4;
                        input.popLimit(limit2);
                        break;
                    case 112:
                        this.dynamicRangeProfile = input.readInt64();
                        break;
                    case 120:
                        this.streamUseCase = input.readInt64();
                        break;
                    case 128:
                        this.colorSpace = input.readInt32();
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(input, tag)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public static CameraStreamProto parseFrom(byte[] data)
                throws InvalidProtocolBufferNanoException {
            return (CameraStreamProto) MessageNano.mergeFrom(new CameraStreamProto(), data);
        }

        public static CameraStreamProto parseFrom(CodedInputByteBufferNano input)
                throws IOException {
            return new CameraStreamProto().mergeFrom(input);
        }
    }
}
