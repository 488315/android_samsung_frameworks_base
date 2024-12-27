package android.internal.framework.protobuf.nano;

import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes2.dex */
final class UnknownFieldData {
    final byte[] bytes;
    final int tag;

    UnknownFieldData(int tag, byte[] bytes) {
        this.tag = tag;
        this.bytes = bytes;
    }

    int computeSerializedSize() {
        int size = 0 + CodedOutputByteBufferNano.computeRawVarint32Size(this.tag);
        return size + this.bytes.length;
    }

    void writeTo(CodedOutputByteBufferNano output) throws IOException {
        output.writeRawVarint32(this.tag);
        output.writeRawBytes(this.bytes);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UnknownFieldData)) {
            return false;
        }
        UnknownFieldData other = (UnknownFieldData) o;
        return this.tag == other.tag && Arrays.equals(this.bytes, other.bytes);
    }

    public int hashCode() {
        int result = (17 * 31) + this.tag;
        return (result * 31) + Arrays.hashCode(this.bytes);
    }
}
