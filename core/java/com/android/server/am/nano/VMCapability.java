package com.android.server.am.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;

import java.io.IOException;

public final class VMCapability extends MessageNano {
    private static volatile VMCapability[] _emptyArray;
    public String name;

    public static VMCapability[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new VMCapability[0];
                }
            }
        }
        return _emptyArray;
    }

    public VMCapability() {
        clear();
    }

    public VMCapability clear() {
        this.name = "";
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano output) throws IOException {
        if (!this.name.equals("")) {
            output.writeString(1, this.name);
        }
        super.writeTo(output);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int size = super.computeSerializedSize();
        if (!this.name.equals("")) {
            return size + CodedOutputByteBufferNano.computeStringSize(1, this.name);
        }
        return size;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public VMCapability mergeFrom(CodedInputByteBufferNano input) throws IOException {
        while (true) {
            int tag = input.readTag();
            switch (tag) {
                case 0:
                    return this;
                case 10:
                    this.name = input.readString();
                    break;
                default:
                    if (!WireFormatNano.parseUnknownField(input, tag)) {
                        return this;
                    }
                    break;
            }
        }
    }

    public static VMCapability parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
        return (VMCapability) MessageNano.mergeFrom(new VMCapability(), data);
    }

    public static VMCapability parseFrom(CodedInputByteBufferNano input) throws IOException {
        return new VMCapability().mergeFrom(input);
    }
}
