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
    public void writeTo(CodedOutputByteBufferNano output) throws IOException {
        if (this.mapS2Level != 0) {
            output.writeInt32(1, this.mapS2Level);
        }
        if (this.cacheTileS2Level != 0) {
            output.writeInt32(2, this.cacheTileS2Level);
        }
        if (this.diskTileS2Level != 0) {
            output.writeInt32(3, this.diskTileS2Level);
        }
        if (Double.doubleToLongBits(this.modelAMeters)
                != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
            output.writeDouble(4, this.modelAMeters);
        }
        if (Double.doubleToLongBits(this.modelBMeters)
                != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
            output.writeDouble(5, this.modelBMeters);
        }
        if (Double.doubleToLongBits(this.modelRmseMeters)
                != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
            output.writeDouble(6, this.modelRmseMeters);
        }
        super.writeTo(output);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int size = super.computeSerializedSize();
        if (this.mapS2Level != 0) {
            size += CodedOutputByteBufferNano.computeInt32Size(1, this.mapS2Level);
        }
        if (this.cacheTileS2Level != 0) {
            size += CodedOutputByteBufferNano.computeInt32Size(2, this.cacheTileS2Level);
        }
        if (this.diskTileS2Level != 0) {
            size += CodedOutputByteBufferNano.computeInt32Size(3, this.diskTileS2Level);
        }
        if (Double.doubleToLongBits(this.modelAMeters)
                != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
            size += CodedOutputByteBufferNano.computeDoubleSize(4, this.modelAMeters);
        }
        if (Double.doubleToLongBits(this.modelBMeters)
                != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
            size += CodedOutputByteBufferNano.computeDoubleSize(5, this.modelBMeters);
        }
        if (Double.doubleToLongBits(this.modelRmseMeters)
                != Double.doubleToLongBits(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
            return size + CodedOutputByteBufferNano.computeDoubleSize(6, this.modelRmseMeters);
        }
        return size;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public MapParamsProto mergeFrom(CodedInputByteBufferNano input) throws IOException {
        while (true) {
            int tag = input.readTag();
            switch (tag) {
                case 0:
                    return this;
                case 8:
                    this.mapS2Level = input.readInt32();
                    break;
                case 16:
                    this.cacheTileS2Level = input.readInt32();
                    break;
                case 24:
                    this.diskTileS2Level = input.readInt32();
                    break;
                case 33:
                    this.modelAMeters = input.readDouble();
                    break;
                case 41:
                    this.modelBMeters = input.readDouble();
                    break;
                case 49:
                    this.modelRmseMeters = input.readDouble();
                    break;
                default:
                    if (!WireFormatNano.parseUnknownField(input, tag)) {
                        return this;
                    }
                    break;
            }
        }
    }

    public static MapParamsProto parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
        return (MapParamsProto) MessageNano.mergeFrom(new MapParamsProto(), data);
    }

    public static MapParamsProto parseFrom(CodedInputByteBufferNano input) throws IOException {
        return new MapParamsProto().mergeFrom(input);
    }
}
