package com.android.server.am.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: classes6.dex */
public final class VMInfo extends MessageNano {
    private static volatile VMInfo[] _emptyArray;
    public String name;
    public String version;

    public static VMInfo[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new VMInfo[0];
                }
            }
        }
        return _emptyArray;
    }

    public VMInfo() {
        clear();
    }

    public VMInfo clear() {
        this.name = "";
        this.version = "";
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if (!this.name.equals("")) {
            codedOutputByteBufferNano.writeString(1, this.name);
        }
        if (!this.version.equals("")) {
            codedOutputByteBufferNano.writeString(2, this.version);
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        if (!this.name.equals("")) {
            computeSerializedSize += CodedOutputByteBufferNano.computeStringSize(1, this.name);
        }
        return !this.version.equals("") ? computeSerializedSize + CodedOutputByteBufferNano.computeStringSize(2, this.version) : computeSerializedSize;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public VMInfo mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                break;
            }
            if (readTag == 10) {
                this.name = codedInputByteBufferNano.readString();
            } else if (readTag != 18) {
                if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    break;
                }
            } else {
                this.version = codedInputByteBufferNano.readString();
            }
        }
        return this;
    }

    public static VMInfo parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
        return (VMInfo) MessageNano.mergeFrom(new VMInfo(), bArr);
    }

    public static VMInfo parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        return new VMInfo().mergeFrom(codedInputByteBufferNano);
    }
}
