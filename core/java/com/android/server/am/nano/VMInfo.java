package com.android.server.am.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: classes5.dex */
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
    public void writeTo(CodedOutputByteBufferNano output) throws IOException {
        if (!this.name.equals("")) {
            output.writeString(1, this.name);
        }
        if (!this.version.equals("")) {
            output.writeString(2, this.version);
        }
        super.writeTo(output);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int size = super.computeSerializedSize();
        if (!this.name.equals("")) {
            size += CodedOutputByteBufferNano.computeStringSize(1, this.name);
        }
        if (!this.version.equals("")) {
            return size + CodedOutputByteBufferNano.computeStringSize(2, this.version);
        }
        return size;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public VMInfo mergeFrom(CodedInputByteBufferNano input) throws IOException {
        while (true) {
            int tag = input.readTag();
            switch (tag) {
                case 0:
                    return this;
                case 10:
                    this.name = input.readString();
                    break;
                case 18:
                    this.version = input.readString();
                    break;
                default:
                    if (!WireFormatNano.parseUnknownField(input, tag)) {
                        return this;
                    }
                    break;
            }
        }
    }

    public static VMInfo parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
        return (VMInfo) MessageNano.mergeFrom(new VMInfo(), data);
    }

    public static VMInfo parseFrom(CodedInputByteBufferNano input) throws IOException {
        return new VMInfo().mergeFrom(input);
    }
}
