package com.android.internal.location.altitude.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes5.dex */
public final class S2TileProto extends MessageNano {
    private static volatile S2TileProto[] _emptyArray;
    public byte[] byteBuffer;
    public byte[] byteJpeg;
    public byte[] bytePng;
    public String tileKey;

    public static S2TileProto[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new S2TileProto[0];
                }
            }
        }
        return _emptyArray;
    }

    public S2TileProto() {
        clear();
    }

    public S2TileProto clear() {
        this.tileKey = "";
        this.byteBuffer = WireFormatNano.EMPTY_BYTES;
        this.byteJpeg = WireFormatNano.EMPTY_BYTES;
        this.bytePng = WireFormatNano.EMPTY_BYTES;
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if (!this.tileKey.equals("")) {
            codedOutputByteBufferNano.writeString(1, this.tileKey);
        }
        if (!Arrays.equals(this.byteBuffer, WireFormatNano.EMPTY_BYTES)) {
            codedOutputByteBufferNano.writeBytes(2, this.byteBuffer);
        }
        if (!Arrays.equals(this.byteJpeg, WireFormatNano.EMPTY_BYTES)) {
            codedOutputByteBufferNano.writeBytes(3, this.byteJpeg);
        }
        if (!Arrays.equals(this.bytePng, WireFormatNano.EMPTY_BYTES)) {
            codedOutputByteBufferNano.writeBytes(4, this.bytePng);
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int iComputeSerializedSize = super.computeSerializedSize();
        if (!this.tileKey.equals("")) {
            iComputeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.tileKey);
        }
        if (!Arrays.equals(this.byteBuffer, WireFormatNano.EMPTY_BYTES)) {
            iComputeSerializedSize += CodedOutputByteBufferNano.computeBytesSize(2, this.byteBuffer);
        }
        if (!Arrays.equals(this.byteJpeg, WireFormatNano.EMPTY_BYTES)) {
            iComputeSerializedSize += CodedOutputByteBufferNano.computeBytesSize(3, this.byteJpeg);
        }
        return !Arrays.equals(this.bytePng, WireFormatNano.EMPTY_BYTES) ? iComputeSerializedSize + CodedOutputByteBufferNano.computeBytesSize(4, this.bytePng) : iComputeSerializedSize;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public S2TileProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int tag = codedInputByteBufferNano.readTag();
            if (tag == 0) {
                break;
            }
            if (tag == 10) {
                this.tileKey = codedInputByteBufferNano.readString();
            } else if (tag == 18) {
                this.byteBuffer = codedInputByteBufferNano.readBytes();
            } else if (tag == 26) {
                this.byteJpeg = codedInputByteBufferNano.readBytes();
            } else if (tag != 34) {
                if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, tag)) {
                    break;
                }
            } else {
                this.bytePng = codedInputByteBufferNano.readBytes();
            }
        }
        return this;
    }

    public static S2TileProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
        return (S2TileProto) MessageNano.mergeFrom(new S2TileProto(), bArr);
    }

    public static S2TileProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        return new S2TileProto().mergeFrom(codedInputByteBufferNano);
    }
}
