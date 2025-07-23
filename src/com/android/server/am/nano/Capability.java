package com.android.server.am.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: classes6.dex */
public final class Capability extends MessageNano {
    private static volatile Capability[] _emptyArray;
    public String name;

    public static Capability[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new Capability[0];
                }
            }
        }
        return _emptyArray;
    }

    public Capability() {
        clear();
    }

    public Capability clear() {
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
        int computeSerializedSize = super.computeSerializedSize();
        return !this.name.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(1, this.name) : computeSerializedSize;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public Capability mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                break;
            }
            if (readTag != 10) {
                if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    break;
                }
            } else {
                this.name = codedInputByteBufferNano.readString();
            }
        }
        return this;
    }

    public static Capability parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
        return (Capability) MessageNano.mergeFrom(new Capability(), bArr);
    }

    public static Capability parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        return new Capability().mergeFrom(codedInputByteBufferNano);
    }
}
