package com.android.systemui.util.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class ComponentNameProto extends MessageNano {
    private static volatile ComponentNameProto[] _emptyArray;
    public String className;
    public String packageName;

    public ComponentNameProto() {
        clear();
    }

    public static ComponentNameProto[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                try {
                    if (_emptyArray == null) {
                        _emptyArray = new ComponentNameProto[0];
                    }
                } finally {
                }
            }
        }
        return _emptyArray;
    }

    public static ComponentNameProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
        return (ComponentNameProto) MessageNano.mergeFrom(new ComponentNameProto(), bArr);
    }

    public ComponentNameProto clear() {
        this.packageName = "";
        this.className = "";
        this.cachedSize = -1;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public int computeSerializedSize() {
        int iComputeStringSize = !this.packageName.equals("") ? CodedOutputByteBufferNano.computeStringSize(1, this.packageName) : 0;
        return !this.className.equals("") ? CodedOutputByteBufferNano.computeStringSize(2, this.className) + iComputeStringSize : iComputeStringSize;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if (!this.packageName.equals("")) {
            codedOutputByteBufferNano.writeString(1, this.packageName);
        }
        if (this.className.equals("")) {
            return;
        }
        codedOutputByteBufferNano.writeString(2, this.className);
    }

    public static ComponentNameProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        return new ComponentNameProto().mergeFrom(codedInputByteBufferNano);
    }

    @Override // com.google.protobuf.nano.MessageNano
    public ComponentNameProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int tag = codedInputByteBufferNano.readTag();
            if (tag == 0) {
                break;
            }
            if (tag == 10) {
                this.packageName = codedInputByteBufferNano.readString();
            } else if (tag == 18) {
                this.className = codedInputByteBufferNano.readString();
            } else if (!codedInputByteBufferNano.skipField(tag)) {
                break;
            }
        }
        return this;
    }
}
