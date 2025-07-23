package com.android.service.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: classes6.dex */
public final class StringListParamProto extends MessageNano {
    private static volatile StringListParamProto[] _emptyArray;
    public String[] element;

    public static StringListParamProto[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new StringListParamProto[0];
                }
            }
        }
        return _emptyArray;
    }

    public StringListParamProto() {
        clear();
    }

    public StringListParamProto clear() {
        this.element = WireFormatNano.EMPTY_STRING_ARRAY;
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        String[] strArr = this.element;
        if (strArr != null && strArr.length > 0) {
            int i = 0;
            while (true) {
                String[] strArr2 = this.element;
                if (i >= strArr2.length) {
                    break;
                }
                String str = strArr2[i];
                if (str != null) {
                    codedOutputByteBufferNano.writeString(1, str);
                }
                i++;
            }
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        String[] strArr = this.element;
        if (strArr == null || strArr.length <= 0) {
            return computeSerializedSize;
        }
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            String[] strArr2 = this.element;
            if (i >= strArr2.length) {
                return computeSerializedSize + i2 + i3;
            }
            String str = strArr2[i];
            if (str != null) {
                i3++;
                i2 += CodedOutputByteBufferNano.computeStringSizeNoTag(str);
            }
            i++;
        }
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public StringListParamProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
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
                int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                String[] strArr = this.element;
                int length = strArr == null ? 0 : strArr.length;
                int i = repeatedFieldArrayLength + length;
                String[] strArr2 = new String[i];
                if (length != 0) {
                    System.arraycopy(strArr, 0, strArr2, 0, length);
                }
                while (length < i - 1) {
                    strArr2[length] = codedInputByteBufferNano.readString();
                    codedInputByteBufferNano.readTag();
                    length++;
                }
                strArr2[length] = codedInputByteBufferNano.readString();
                this.element = strArr2;
            }
        }
        return this;
    }

    public static StringListParamProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
        return (StringListParamProto) MessageNano.mergeFrom(new StringListParamProto(), bArr);
    }

    public static StringListParamProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        return new StringListParamProto().mergeFrom(codedInputByteBufferNano);
    }
}
