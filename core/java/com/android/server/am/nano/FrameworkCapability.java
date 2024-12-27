package com.android.server.am.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: classes5.dex */
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
    public FrameworkCapability mergeFrom(CodedInputByteBufferNano input) throws IOException {
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

    public static FrameworkCapability parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
        return (FrameworkCapability) MessageNano.mergeFrom(new FrameworkCapability(), data);
    }

    public static FrameworkCapability parseFrom(CodedInputByteBufferNano input) throws IOException {
        return new FrameworkCapability().mergeFrom(input);
    }
}
