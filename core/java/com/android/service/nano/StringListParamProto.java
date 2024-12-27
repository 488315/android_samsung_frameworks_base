package com.android.service.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: classes5.dex */
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
    public void writeTo(CodedOutputByteBufferNano output) throws IOException {
        if (this.element != null && this.element.length > 0) {
            for (int i = 0; i < this.element.length; i++) {
                String element = this.element[i];
                if (element != null) {
                    output.writeString(1, element);
                }
            }
        }
        super.writeTo(output);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int size = super.computeSerializedSize();
        if (this.element != null && this.element.length > 0) {
            int dataCount = 0;
            int dataSize = 0;
            for (int i = 0; i < this.element.length; i++) {
                String element = this.element[i];
                if (element != null) {
                    dataCount++;
                    dataSize += CodedOutputByteBufferNano.computeStringSizeNoTag(element);
                }
            }
            return size + dataSize + (dataCount * 1);
        }
        return size;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public StringListParamProto mergeFrom(CodedInputByteBufferNano input) throws IOException {
        while (true) {
            int tag = input.readTag();
            switch (tag) {
                case 0:
                    return this;
                case 10:
                    int arrayLength = WireFormatNano.getRepeatedFieldArrayLength(input, 10);
                    int i = this.element == null ? 0 : this.element.length;
                    String[] newArray = new String[i + arrayLength];
                    if (i != 0) {
                        System.arraycopy(this.element, 0, newArray, 0, i);
                    }
                    while (i < newArray.length - 1) {
                        newArray[i] = input.readString();
                        input.readTag();
                        i++;
                    }
                    newArray[i] = input.readString();
                    this.element = newArray;
                    break;
                default:
                    if (!WireFormatNano.parseUnknownField(input, tag)) {
                        return this;
                    }
                    break;
            }
        }
    }

    public static StringListParamProto parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
        return (StringListParamProto) MessageNano.mergeFrom(new StringListParamProto(), data);
    }

    public static StringListParamProto parseFrom(CodedInputByteBufferNano input) throws IOException {
        return new StringListParamProto().mergeFrom(input);
    }
}
