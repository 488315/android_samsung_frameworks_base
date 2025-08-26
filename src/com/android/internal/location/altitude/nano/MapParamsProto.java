package com.android.internal.location.altitude.nano;

import android.hardware.scontext.SContextConstants;
import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: classes5.dex */
public final class MapParamsProto extends MessageNano {
    private static volatile MapParamsProto[] _emptyArray;
    public int cacheTileS2Level;
    public int diskTileS2Level;
    public int mapS2Level;
    public double modelAMeters;
    public double modelBMeters;
    public double modelRmseMeters;

    public static MapParamsProto[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new MapParamsProto[0];
                }
            }
        }
        return _emptyArray;
    }

    public MapParamsProto() {
        clear();
    }

    public MapParamsProto clear() {
        this.mapS2Level = 0;
        this.cacheTileS2Level = 0;
        this.diskTileS2Level = 0;
        this.modelAMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.modelBMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.modelRmseMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        int i = this.mapS2Level;
        if (i != 0) {
            codedOutputByteBufferNano.writeInt32(1, i);
        }
        int i2 = this.cacheTileS2Level;
        if (i2 != 0) {
            codedOutputByteBufferNano.writeInt32(2, i2);
        }
        int i3 = this.diskTileS2Level;
        if (i3 != 0) {
            codedOutputByteBufferNano.writeInt32(3, i3);
        }
        if (Double.doubleToLongBits(this.modelAMeters) != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
            codedOutputByteBufferNano.writeDouble(4, this.modelAMeters);
        }
        if (Double.doubleToLongBits(this.modelBMeters) != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
            codedOutputByteBufferNano.writeDouble(5, this.modelBMeters);
        }
        if (Double.doubleToLongBits(this.modelRmseMeters) != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
            codedOutputByteBufferNano.writeDouble(6, this.modelRmseMeters);
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int iComputeSerializedSize = super.computeSerializedSize();
        int i = this.mapS2Level;
        if (i != 0) {
            iComputeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, i);
        }
        int i2 = this.cacheTileS2Level;
        if (i2 != 0) {
            iComputeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i2);
        }
        int i3 = this.diskTileS2Level;
        if (i3 != 0) {
            iComputeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, i3);
        }
        if (Double.doubleToLongBits(this.modelAMeters) != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
            iComputeSerializedSize += CodedOutputByteBufferNano.computeDoubleSize(4, this.modelAMeters);
        }
        if (Double.doubleToLongBits(this.modelBMeters) != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
            iComputeSerializedSize += CodedOutputByteBufferNano.computeDoubleSize(5, this.modelBMeters);
        }
        return Double.doubleToLongBits(this.modelRmseMeters) != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) ? iComputeSerializedSize + CodedOutputByteBufferNano.computeDoubleSize(6, this.modelRmseMeters) : iComputeSerializedSize;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public MapParamsProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int tag = codedInputByteBufferNano.readTag();
            if (tag == 0) {
                break;
            }
            if (tag == 8) {
                this.mapS2Level = codedInputByteBufferNano.readInt32();
            } else if (tag == 16) {
                this.cacheTileS2Level = codedInputByteBufferNano.readInt32();
            } else if (tag == 24) {
                this.diskTileS2Level = codedInputByteBufferNano.readInt32();
            } else if (tag == 33) {
                this.modelAMeters = codedInputByteBufferNano.readDouble();
            } else if (tag == 41) {
                this.modelBMeters = codedInputByteBufferNano.readDouble();
            } else if (tag != 49) {
                if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, tag)) {
                    break;
                }
            } else {
                this.modelRmseMeters = codedInputByteBufferNano.readDouble();
            }
        }
        return this;
    }

    public static MapParamsProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
        return (MapParamsProto) MessageNano.mergeFrom(new MapParamsProto(), bArr);
    }

    public static MapParamsProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        return new MapParamsProto().mergeFrom(codedInputByteBufferNano);
    }
}
