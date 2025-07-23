package com.android.framework.protobuf.nano;

import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes5.dex */
final class UnknownFieldData {
    final byte[] bytes;
    final int tag;

    UnknownFieldData(int i, byte[] bArr) {
        this.tag = i;
        this.bytes = bArr;
    }

    int computeSerializedSize() {
        return CodedOutputByteBufferNano.computeRawVarint32Size(this.tag) + this.bytes.length;
    }

    void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        codedOutputByteBufferNano.writeRawVarint32(this.tag);
        codedOutputByteBufferNano.writeRawBytes(this.bytes);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UnknownFieldData)) {
            return false;
        }
        UnknownFieldData unknownFieldData = (UnknownFieldData) obj;
        return this.tag == unknownFieldData.tag && Arrays.equals(this.bytes, unknownFieldData.bytes);
    }

    public int hashCode() {
        return ((527 + this.tag) * 31) + Arrays.hashCode(this.bytes);
    }
}
