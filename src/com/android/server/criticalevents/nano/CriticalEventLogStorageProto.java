package com.android.server.criticalevents.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: classes6.dex */
public final class CriticalEventLogStorageProto extends MessageNano {
    private static volatile CriticalEventLogStorageProto[] _emptyArray;
    public CriticalEventProto[] events;

    public static CriticalEventLogStorageProto[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new CriticalEventLogStorageProto[0];
                }
            }
        }
        return _emptyArray;
    }

    public CriticalEventLogStorageProto() {
        clear();
    }

    public CriticalEventLogStorageProto clear() {
        this.events = CriticalEventProto.emptyArray();
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        CriticalEventProto[] criticalEventProtoArr = this.events;
        if (criticalEventProtoArr != null && criticalEventProtoArr.length > 0) {
            int i = 0;
            while (true) {
                CriticalEventProto[] criticalEventProtoArr2 = this.events;
                if (i >= criticalEventProtoArr2.length) {
                    break;
                }
                CriticalEventProto criticalEventProto = criticalEventProtoArr2[i];
                if (criticalEventProto != null) {
                    codedOutputByteBufferNano.writeMessage(1, criticalEventProto);
                }
                i++;
            }
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        CriticalEventProto[] criticalEventProtoArr = this.events;
        if (criticalEventProtoArr != null && criticalEventProtoArr.length > 0) {
            int i = 0;
            while (true) {
                CriticalEventProto[] criticalEventProtoArr2 = this.events;
                if (i >= criticalEventProtoArr2.length) {
                    break;
                }
                CriticalEventProto criticalEventProto = criticalEventProtoArr2[i];
                if (criticalEventProto != null) {
                    computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, criticalEventProto);
                }
                i++;
            }
        }
        return computeSerializedSize;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public CriticalEventLogStorageProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
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
                CriticalEventProto[] criticalEventProtoArr = this.events;
                int length = criticalEventProtoArr == null ? 0 : criticalEventProtoArr.length;
                int i = repeatedFieldArrayLength + length;
                CriticalEventProto[] criticalEventProtoArr2 = new CriticalEventProto[i];
                if (length != 0) {
                    System.arraycopy(criticalEventProtoArr, 0, criticalEventProtoArr2, 0, length);
                }
                while (length < i - 1) {
                    CriticalEventProto criticalEventProto = new CriticalEventProto();
                    criticalEventProtoArr2[length] = criticalEventProto;
                    codedInputByteBufferNano.readMessage(criticalEventProto);
                    codedInputByteBufferNano.readTag();
                    length++;
                }
                CriticalEventProto criticalEventProto2 = new CriticalEventProto();
                criticalEventProtoArr2[length] = criticalEventProto2;
                codedInputByteBufferNano.readMessage(criticalEventProto2);
                this.events = criticalEventProtoArr2;
            }
        }
        return this;
    }

    public static CriticalEventLogStorageProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
        return (CriticalEventLogStorageProto) MessageNano.mergeFrom(new CriticalEventLogStorageProto(), bArr);
    }

    public static CriticalEventLogStorageProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        return new CriticalEventLogStorageProto().mergeFrom(codedInputByteBufferNano);
    }
}
