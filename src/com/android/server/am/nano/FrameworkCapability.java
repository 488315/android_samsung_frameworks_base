package com.android.server.am.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: classes6.dex */
public final class FrameworkCapability extends MessageNano {
    private static volatile FrameworkCapability[] _emptyArray;
    public String name;

    public static FrameworkCapability[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new FrameworkCapability[0];
                }
            }
        }
        return _emptyArray;
    }

    public FrameworkCapability() {
        clear();
    }

    public FrameworkCapability clear() {
        this.name = "";
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if (!this.name.equals("")) {
            codedOutputByteBufferNano.writeString(1, this.name);
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int iComputeSerializedSize = super.computeSerializedSize();
        return !this.name.equals("") ? iComputeSerializedSize + CodedOutputByteBufferNano.computeStringSize(1, this.name) : iComputeSerializedSize;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public FrameworkCapability mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int tag = codedInputByteBufferNano.readTag();
            if (tag == 0) {
                break;
            }
            if (tag != 10) {
                if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, tag)) {
                    break;
                }
            } else {
                this.name = codedInputByteBufferNano.readString();
            }
        }
        return this;
    }

    public static FrameworkCapability parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
        return (FrameworkCapability) MessageNano.mergeFrom(new FrameworkCapability(), bArr);
    }

    public static FrameworkCapability parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        return new FrameworkCapability().mergeFrom(codedInputByteBufferNano);
    }
}
