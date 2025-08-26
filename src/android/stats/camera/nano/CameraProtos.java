package android.stats.camera.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: classes3.dex */
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
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int i = this.width;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(1, i);
            }
            int i2 = this.height;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(2, i2);
            }
            int i3 = this.format;
            if (i3 != 0) {
                codedOutputByteBufferNano.writeInt32(3, i3);
            }
            int i4 = this.dataSpace;
            if (i4 != 0) {
                codedOutputByteBufferNano.writeInt32(4, i4);
            }
            long j = this.usage;
            if (j != 0) {
                codedOutputByteBufferNano.writeInt64(5, j);
            }
            long j2 = this.requestCount;
            if (j2 != 0) {
                codedOutputByteBufferNano.writeInt64(6, j2);
            }
            long j3 = this.errorCount;
            if (j3 != 0) {
                codedOutputByteBufferNano.writeInt64(7, j3);
            }
            int i5 = this.firstCaptureLatencyMillis;
            if (i5 != 0) {
                codedOutputByteBufferNano.writeInt32(8, i5);
            }
            int i6 = this.maxHalBuffers;
            if (i6 != 0) {
                codedOutputByteBufferNano.writeInt32(9, i6);
            }
            int i7 = this.maxAppBuffers;
            if (i7 != 0) {
                codedOutputByteBufferNano.writeInt32(10, i7);
            }
            int i8 = this.histogramType;
            if (i8 != 0) {
                codedOutputByteBufferNano.writeInt32(11, i8);
            }
            float[] fArr = this.histogramBins;
            int i9 = 0;
            if (fArr != null && fArr.length > 0) {
                int i10 = 0;
                while (true) {
                    float[] fArr2 = this.histogramBins;
                    if (i10 >= fArr2.length) {
                        break;
                    }
                    codedOutputByteBufferNano.writeFloat(12, fArr2[i10]);
                    i10++;
                }
            }
            long[] jArr = this.histogramCounts;
            if (jArr != null && jArr.length > 0) {
                while (true) {
                    long[] jArr2 = this.histogramCounts;
                    if (i9 >= jArr2.length) {
                        break;
                    }
                    codedOutputByteBufferNano.writeInt64(13, jArr2[i9]);
                    i9++;
                }
            }
            long j4 = this.dynamicRangeProfile;
            if (j4 != 0) {
                codedOutputByteBufferNano.writeInt64(14, j4);
            }
            long j5 = this.streamUseCase;
            if (j5 != 0) {
                codedOutputByteBufferNano.writeInt64(15, j5);
            }
            int i11 = this.colorSpace;
            if (i11 != 0) {
                codedOutputByteBufferNano.writeInt32(16, i11);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            long[] jArr;
            int iComputeSerializedSize = super.computeSerializedSize();
            int i = this.width;
            if (i != 0) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, i);
            }
            int i2 = this.height;
            if (i2 != 0) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i2);
            }
            int i3 = this.format;
            if (i3 != 0) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, i3);
            }
            int i4 = this.dataSpace;
            if (i4 != 0) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(4, i4);
            }
            long j = this.usage;
            if (j != 0) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(5, j);
            }
            long j2 = this.requestCount;
            if (j2 != 0) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(6, j2);
            }
            long j3 = this.errorCount;
            if (j3 != 0) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(7, j3);
            }
            int i5 = this.firstCaptureLatencyMillis;
            if (i5 != 0) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(8, i5);
            }
            int i6 = this.maxHalBuffers;
            if (i6 != 0) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(9, i6);
            }
            int i7 = this.maxAppBuffers;
            if (i7 != 0) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(10, i7);
            }
            int i8 = this.histogramType;
            if (i8 != 0) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(11, i8);
            }
            float[] fArr = this.histogramBins;
            if (fArr != null && fArr.length > 0) {
                iComputeSerializedSize = iComputeSerializedSize + (fArr.length * 4) + fArr.length;
            }
            long[] jArr2 = this.histogramCounts;
            if (jArr2 != null && jArr2.length > 0) {
                int i9 = 0;
                int iComputeInt64SizeNoTag = 0;
                while (true) {
                    jArr = this.histogramCounts;
                    if (i9 >= jArr.length) {
                        break;
                    }
                    iComputeInt64SizeNoTag += CodedOutputByteBufferNano.computeInt64SizeNoTag(jArr[i9]);
                    i9++;
                }
                iComputeSerializedSize = iComputeSerializedSize + iComputeInt64SizeNoTag + jArr.length;
            }
            long j4 = this.dynamicRangeProfile;
            if (j4 != 0) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(14, j4);
            }
            long j5 = this.streamUseCase;
            if (j5 != 0) {
                iComputeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(15, j5);
            }
            int i10 = this.colorSpace;
            return i10 != 0 ? iComputeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(16, i10) : iComputeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public CameraStreamProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int tag = codedInputByteBufferNano.readTag();
                switch (tag) {
                    case 0:
                        break;
                    case 8:
                        this.width = codedInputByteBufferNano.readInt32();
                        break;
                    case 16:
                        this.height = codedInputByteBufferNano.readInt32();
                        break;
                    case 24:
                        this.format = codedInputByteBufferNano.readInt32();
                        break;
                    case 32:
                        this.dataSpace = codedInputByteBufferNano.readInt32();
                        break;
                    case 40:
                        this.usage = codedInputByteBufferNano.readInt64();
                        break;
                    case 48:
                        this.requestCount = codedInputByteBufferNano.readInt64();
                        break;
                    case 56:
                        this.errorCount = codedInputByteBufferNano.readInt64();
                        break;
                    case 64:
                        this.firstCaptureLatencyMillis = codedInputByteBufferNano.readInt32();
                        break;
                    case 72:
                        this.maxHalBuffers = codedInputByteBufferNano.readInt32();
                        break;
                    case 80:
                        this.maxAppBuffers = codedInputByteBufferNano.readInt32();
                        break;
                    case 88:
                        int int32 = codedInputByteBufferNano.readInt32();
                        if (int32 != 0 && int32 != 1) {
                            break;
                        } else {
                            this.histogramType = int32;
                            break;
                        }
                        break;
                    case 98:
                        int rawVarint32 = codedInputByteBufferNano.readRawVarint32();
                        int iPushLimit = codedInputByteBufferNano.pushLimit(rawVarint32);
                        int i = rawVarint32 / 4;
                        float[] fArr = this.histogramBins;
                        int length = fArr == null ? 0 : fArr.length;
                        int i2 = i + length;
                        float[] fArr2 = new float[i2];
                        if (length != 0) {
                            System.arraycopy(fArr, 0, fArr2, 0, length);
                        }
                        while (length < i2) {
                            fArr2[length] = codedInputByteBufferNano.readFloat();
                            length++;
                        }
                        this.histogramBins = fArr2;
                        codedInputByteBufferNano.popLimit(iPushLimit);
                        break;
                    case 101:
                        int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 101);
                        float[] fArr3 = this.histogramBins;
                        int length2 = fArr3 == null ? 0 : fArr3.length;
                        int i3 = repeatedFieldArrayLength + length2;
                        float[] fArr4 = new float[i3];
                        if (length2 != 0) {
                            System.arraycopy(fArr3, 0, fArr4, 0, length2);
                        }
                        while (length2 < i3 - 1) {
                            fArr4[length2] = codedInputByteBufferNano.readFloat();
                            codedInputByteBufferNano.readTag();
                            length2++;
                        }
                        fArr4[length2] = codedInputByteBufferNano.readFloat();
                        this.histogramBins = fArr4;
                        break;
                    case 104:
                        int repeatedFieldArrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 104);
                        long[] jArr = this.histogramCounts;
                        int length3 = jArr == null ? 0 : jArr.length;
                        int i4 = repeatedFieldArrayLength2 + length3;
                        long[] jArr2 = new long[i4];
                        if (length3 != 0) {
                            System.arraycopy(jArr, 0, jArr2, 0, length3);
                        }
                        while (length3 < i4 - 1) {
                            jArr2[length3] = codedInputByteBufferNano.readInt64();
                            codedInputByteBufferNano.readTag();
                            length3++;
                        }
                        jArr2[length3] = codedInputByteBufferNano.readInt64();
                        this.histogramCounts = jArr2;
                        break;
                    case 106:
                        int iPushLimit2 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int position = codedInputByteBufferNano.getPosition();
                        int i5 = 0;
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt64();
                            i5++;
                        }
                        codedInputByteBufferNano.rewindToPosition(position);
                        long[] jArr3 = this.histogramCounts;
                        int length4 = jArr3 == null ? 0 : jArr3.length;
                        int i6 = i5 + length4;
                        long[] jArr4 = new long[i6];
                        if (length4 != 0) {
                            System.arraycopy(jArr3, 0, jArr4, 0, length4);
                        }
                        while (length4 < i6) {
                            jArr4[length4] = codedInputByteBufferNano.readInt64();
                            length4++;
                        }
                        this.histogramCounts = jArr4;
                        codedInputByteBufferNano.popLimit(iPushLimit2);
                        break;
                    case 112:
                        this.dynamicRangeProfile = codedInputByteBufferNano.readInt64();
                        break;
                    case 120:
                        this.streamUseCase = codedInputByteBufferNano.readInt64();
                        break;
                    case 128:
                        this.colorSpace = codedInputByteBufferNano.readInt32();
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, tag)) {
                            break;
                        } else {
                            break;
                        }
                }
            }
            return this;
        }

        public static CameraStreamProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (CameraStreamProto) MessageNano.mergeFrom(new CameraStreamProto(), bArr);
        }

        public static CameraStreamProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new CameraStreamProto().mergeFrom(codedInputByteBufferNano);
        }
    }
}
