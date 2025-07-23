package com.android.internal.location.nano;

import android.hardware.scontext.SContextConstants;
import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: classes5.dex */
public interface GnssLogsProto {

    public static final class GnssLog extends MessageNano {
        private static volatile GnssLog[] _emptyArray;
        public String hardwareRevision;
        public double meanL5TopFourAverageCn0DbHz;
        public int meanPositionAccuracyMeters;
        public int meanTimeToFirstFixSecs;
        public double meanTopFourAverageCn0DbHz;
        public int numL5SvStatusProcessed;
        public int numL5SvStatusUsedInFix;
        public int numL5TopFourAverageCn0Processed;
        public int numLocationReportProcessed;
        public int numPositionAccuracyProcessed;
        public int numSvStatusProcessed;
        public int numSvStatusUsedInFix;
        public int numTimeToFirstFixProcessed;
        public int numTopFourAverageCn0Processed;
        public int percentageLocationFailure;
        public PowerMetrics powerMetrics;
        public double standardDeviationL5TopFourAverageCn0DbHz;
        public int standardDeviationPositionAccuracyMeters;
        public int standardDeviationTimeToFirstFixSecs;
        public double standardDeviationTopFourAverageCn0DbHz;

        public static GnssLog[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new GnssLog[0];
                    }
                }
            }
            return _emptyArray;
        }

        public GnssLog() {
            clear();
        }

        public GnssLog clear() {
            this.numLocationReportProcessed = 0;
            this.percentageLocationFailure = 0;
            this.numTimeToFirstFixProcessed = 0;
            this.meanTimeToFirstFixSecs = 0;
            this.standardDeviationTimeToFirstFixSecs = 0;
            this.numPositionAccuracyProcessed = 0;
            this.meanPositionAccuracyMeters = 0;
            this.standardDeviationPositionAccuracyMeters = 0;
            this.numTopFourAverageCn0Processed = 0;
            this.meanTopFourAverageCn0DbHz = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            this.standardDeviationTopFourAverageCn0DbHz = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            this.powerMetrics = null;
            this.hardwareRevision = "";
            this.numSvStatusProcessed = 0;
            this.numL5SvStatusProcessed = 0;
            this.numSvStatusUsedInFix = 0;
            this.numL5SvStatusUsedInFix = 0;
            this.numL5TopFourAverageCn0Processed = 0;
            this.meanL5TopFourAverageCn0DbHz = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            this.standardDeviationL5TopFourAverageCn0DbHz = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int i = this.numLocationReportProcessed;
            if (i != 0) {
                codedOutputByteBufferNano.writeInt32(1, i);
            }
            int i2 = this.percentageLocationFailure;
            if (i2 != 0) {
                codedOutputByteBufferNano.writeInt32(2, i2);
            }
            int i3 = this.numTimeToFirstFixProcessed;
            if (i3 != 0) {
                codedOutputByteBufferNano.writeInt32(3, i3);
            }
            int i4 = this.meanTimeToFirstFixSecs;
            if (i4 != 0) {
                codedOutputByteBufferNano.writeInt32(4, i4);
            }
            int i5 = this.standardDeviationTimeToFirstFixSecs;
            if (i5 != 0) {
                codedOutputByteBufferNano.writeInt32(5, i5);
            }
            int i6 = this.numPositionAccuracyProcessed;
            if (i6 != 0) {
                codedOutputByteBufferNano.writeInt32(6, i6);
            }
            int i7 = this.meanPositionAccuracyMeters;
            if (i7 != 0) {
                codedOutputByteBufferNano.writeInt32(7, i7);
            }
            int i8 = this.standardDeviationPositionAccuracyMeters;
            if (i8 != 0) {
                codedOutputByteBufferNano.writeInt32(8, i8);
            }
            int i9 = this.numTopFourAverageCn0Processed;
            if (i9 != 0) {
                codedOutputByteBufferNano.writeInt32(9, i9);
            }
            if (Double.doubleToLongBits(this.meanTopFourAverageCn0DbHz) != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
                codedOutputByteBufferNano.writeDouble(10, this.meanTopFourAverageCn0DbHz);
            }
            if (Double.doubleToLongBits(this.standardDeviationTopFourAverageCn0DbHz) != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
                codedOutputByteBufferNano.writeDouble(11, this.standardDeviationTopFourAverageCn0DbHz);
            }
            PowerMetrics powerMetrics = this.powerMetrics;
            if (powerMetrics != null) {
                codedOutputByteBufferNano.writeMessage(12, powerMetrics);
            }
            if (!this.hardwareRevision.equals("")) {
                codedOutputByteBufferNano.writeString(13, this.hardwareRevision);
            }
            int i10 = this.numSvStatusProcessed;
            if (i10 != 0) {
                codedOutputByteBufferNano.writeInt32(14, i10);
            }
            int i11 = this.numL5SvStatusProcessed;
            if (i11 != 0) {
                codedOutputByteBufferNano.writeInt32(15, i11);
            }
            int i12 = this.numSvStatusUsedInFix;
            if (i12 != 0) {
                codedOutputByteBufferNano.writeInt32(16, i12);
            }
            int i13 = this.numL5SvStatusUsedInFix;
            if (i13 != 0) {
                codedOutputByteBufferNano.writeInt32(17, i13);
            }
            int i14 = this.numL5TopFourAverageCn0Processed;
            if (i14 != 0) {
                codedOutputByteBufferNano.writeInt32(18, i14);
            }
            if (Double.doubleToLongBits(this.meanL5TopFourAverageCn0DbHz) != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
                codedOutputByteBufferNano.writeDouble(19, this.meanL5TopFourAverageCn0DbHz);
            }
            if (Double.doubleToLongBits(this.standardDeviationL5TopFourAverageCn0DbHz) != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
                codedOutputByteBufferNano.writeDouble(20, this.standardDeviationL5TopFourAverageCn0DbHz);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            int i = this.numLocationReportProcessed;
            if (i != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, i);
            }
            int i2 = this.percentageLocationFailure;
            if (i2 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i2);
            }
            int i3 = this.numTimeToFirstFixProcessed;
            if (i3 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, i3);
            }
            int i4 = this.meanTimeToFirstFixSecs;
            if (i4 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(4, i4);
            }
            int i5 = this.standardDeviationTimeToFirstFixSecs;
            if (i5 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(5, i5);
            }
            int i6 = this.numPositionAccuracyProcessed;
            if (i6 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(6, i6);
            }
            int i7 = this.meanPositionAccuracyMeters;
            if (i7 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(7, i7);
            }
            int i8 = this.standardDeviationPositionAccuracyMeters;
            if (i8 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(8, i8);
            }
            int i9 = this.numTopFourAverageCn0Processed;
            if (i9 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(9, i9);
            }
            if (Double.doubleToLongBits(this.meanTopFourAverageCn0DbHz) != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeDoubleSize(10, this.meanTopFourAverageCn0DbHz);
            }
            if (Double.doubleToLongBits(this.standardDeviationTopFourAverageCn0DbHz) != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeDoubleSize(11, this.standardDeviationTopFourAverageCn0DbHz);
            }
            PowerMetrics powerMetrics = this.powerMetrics;
            if (powerMetrics != null) {
                computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(12, powerMetrics);
            }
            if (!this.hardwareRevision.equals("")) {
                computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(13, this.hardwareRevision);
            }
            int i10 = this.numSvStatusProcessed;
            if (i10 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(14, i10);
            }
            int i11 = this.numL5SvStatusProcessed;
            if (i11 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(15, i11);
            }
            int i12 = this.numSvStatusUsedInFix;
            if (i12 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(16, i12);
            }
            int i13 = this.numL5SvStatusUsedInFix;
            if (i13 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(17, i13);
            }
            int i14 = this.numL5TopFourAverageCn0Processed;
            if (i14 != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(18, i14);
            }
            if (Double.doubleToLongBits(this.meanL5TopFourAverageCn0DbHz) != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeDoubleSize(19, this.meanL5TopFourAverageCn0DbHz);
            }
            return Double.doubleToLongBits(this.standardDeviationL5TopFourAverageCn0DbHz) != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) ? computeSerializedSize + CodedOutputByteBufferNano.computeDoubleSize(20, this.standardDeviationL5TopFourAverageCn0DbHz) : computeSerializedSize;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public GnssLog mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                switch (readTag) {
                    case 0:
                        break;
                    case 8:
                        this.numLocationReportProcessed = codedInputByteBufferNano.readInt32();
                        break;
                    case 16:
                        this.percentageLocationFailure = codedInputByteBufferNano.readInt32();
                        break;
                    case 24:
                        this.numTimeToFirstFixProcessed = codedInputByteBufferNano.readInt32();
                        break;
                    case 32:
                        this.meanTimeToFirstFixSecs = codedInputByteBufferNano.readInt32();
                        break;
                    case 40:
                        this.standardDeviationTimeToFirstFixSecs = codedInputByteBufferNano.readInt32();
                        break;
                    case 48:
                        this.numPositionAccuracyProcessed = codedInputByteBufferNano.readInt32();
                        break;
                    case 56:
                        this.meanPositionAccuracyMeters = codedInputByteBufferNano.readInt32();
                        break;
                    case 64:
                        this.standardDeviationPositionAccuracyMeters = codedInputByteBufferNano.readInt32();
                        break;
                    case 72:
                        this.numTopFourAverageCn0Processed = codedInputByteBufferNano.readInt32();
                        break;
                    case 81:
                        this.meanTopFourAverageCn0DbHz = codedInputByteBufferNano.readDouble();
                        break;
                    case 89:
                        this.standardDeviationTopFourAverageCn0DbHz = codedInputByteBufferNano.readDouble();
                        break;
                    case 98:
                        if (this.powerMetrics == null) {
                            this.powerMetrics = new PowerMetrics();
                        }
                        codedInputByteBufferNano.readMessage(this.powerMetrics);
                        break;
                    case 106:
                        this.hardwareRevision = codedInputByteBufferNano.readString();
                        break;
                    case 112:
                        this.numSvStatusProcessed = codedInputByteBufferNano.readInt32();
                        break;
                    case 120:
                        this.numL5SvStatusProcessed = codedInputByteBufferNano.readInt32();
                        break;
                    case 128:
                        this.numSvStatusUsedInFix = codedInputByteBufferNano.readInt32();
                        break;
                    case 136:
                        this.numL5SvStatusUsedInFix = codedInputByteBufferNano.readInt32();
                        break;
                    case 144:
                        this.numL5TopFourAverageCn0Processed = codedInputByteBufferNano.readInt32();
                        break;
                    case 153:
                        this.meanL5TopFourAverageCn0DbHz = codedInputByteBufferNano.readDouble();
                        break;
                    case 161:
                        this.standardDeviationL5TopFourAverageCn0DbHz = codedInputByteBufferNano.readDouble();
                        break;
                    default:
                        if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                            break;
                        } else {
                            break;
                        }
                }
            }
            return this;
        }

        public static GnssLog parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (GnssLog) MessageNano.mergeFrom(new GnssLog(), bArr);
        }

        public static GnssLog parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new GnssLog().mergeFrom(codedInputByteBufferNano);
        }
    }

    public static final class PowerMetrics extends MessageNano {
        private static volatile PowerMetrics[] _emptyArray;
        public double energyConsumedMah;
        public long loggingDurationMs;
        public long[] timeInSignalQualityLevelMs;

        public static PowerMetrics[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new PowerMetrics[0];
                    }
                }
            }
            return _emptyArray;
        }

        public PowerMetrics() {
            clear();
        }

        public PowerMetrics clear() {
            this.loggingDurationMs = 0L;
            this.energyConsumedMah = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            this.timeInSignalQualityLevelMs = WireFormatNano.EMPTY_LONG_ARRAY;
            this.cachedSize = -1;
            return this;
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            long j = this.loggingDurationMs;
            if (j != 0) {
                codedOutputByteBufferNano.writeInt64(1, j);
            }
            if (Double.doubleToLongBits(this.energyConsumedMah) != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
                codedOutputByteBufferNano.writeDouble(2, this.energyConsumedMah);
            }
            long[] jArr = this.timeInSignalQualityLevelMs;
            if (jArr != null && jArr.length > 0) {
                int i = 0;
                while (true) {
                    long[] jArr2 = this.timeInSignalQualityLevelMs;
                    if (i >= jArr2.length) {
                        break;
                    }
                    codedOutputByteBufferNano.writeInt64(3, jArr2[i]);
                    i++;
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        protected int computeSerializedSize() {
            int computeSerializedSize = super.computeSerializedSize();
            long j = this.loggingDurationMs;
            if (j != 0) {
                computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(1, j);
            }
            if (Double.doubleToLongBits(this.energyConsumedMah) != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
                computeSerializedSize += CodedOutputByteBufferNano.computeDoubleSize(2, this.energyConsumedMah);
            }
            long[] jArr = this.timeInSignalQualityLevelMs;
            if (jArr == null || jArr.length <= 0) {
                return computeSerializedSize;
            }
            int i = 0;
            int i2 = 0;
            while (true) {
                long[] jArr2 = this.timeInSignalQualityLevelMs;
                if (i < jArr2.length) {
                    i2 += CodedOutputByteBufferNano.computeInt64SizeNoTag(jArr2[i]);
                    i++;
                } else {
                    return computeSerializedSize + i2 + jArr2.length;
                }
            }
        }

        @Override // com.android.framework.protobuf.nano.MessageNano
        public PowerMetrics mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            while (true) {
                int readTag = codedInputByteBufferNano.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == 8) {
                    this.loggingDurationMs = codedInputByteBufferNano.readInt64();
                } else if (readTag == 17) {
                    this.energyConsumedMah = codedInputByteBufferNano.readDouble();
                } else if (readTag == 24) {
                    int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 24);
                    long[] jArr = this.timeInSignalQualityLevelMs;
                    int length = jArr == null ? 0 : jArr.length;
                    int i = repeatedFieldArrayLength + length;
                    long[] jArr2 = new long[i];
                    if (length != 0) {
                        System.arraycopy(jArr, 0, jArr2, 0, length);
                    }
                    while (length < i - 1) {
                        jArr2[length] = codedInputByteBufferNano.readInt64();
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    jArr2[length] = codedInputByteBufferNano.readInt64();
                    this.timeInSignalQualityLevelMs = jArr2;
                } else if (readTag != 26) {
                    if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        break;
                    }
                } else {
                    int pushLimit = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                    int position = codedInputByteBufferNano.getPosition();
                    int i2 = 0;
                    while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                        codedInputByteBufferNano.readInt64();
                        i2++;
                    }
                    codedInputByteBufferNano.rewindToPosition(position);
                    long[] jArr3 = this.timeInSignalQualityLevelMs;
                    int length2 = jArr3 == null ? 0 : jArr3.length;
                    int i3 = i2 + length2;
                    long[] jArr4 = new long[i3];
                    if (length2 != 0) {
                        System.arraycopy(jArr3, 0, jArr4, 0, length2);
                    }
                    while (length2 < i3) {
                        jArr4[length2] = codedInputByteBufferNano.readInt64();
                        length2++;
                    }
                    this.timeInSignalQualityLevelMs = jArr4;
                    codedInputByteBufferNano.popLimit(pushLimit);
                }
            }
            return this;
        }

        public static PowerMetrics parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
            return (PowerMetrics) MessageNano.mergeFrom(new PowerMetrics(), bArr);
        }

        public static PowerMetrics parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new PowerMetrics().mergeFrom(codedInputByteBufferNano);
        }
    }
}
