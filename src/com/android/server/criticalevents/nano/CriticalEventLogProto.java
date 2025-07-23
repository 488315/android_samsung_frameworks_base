package com.android.server.criticalevents.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: classes6.dex */
public final class CriticalEventLogProto extends MessageNano {
    private static volatile CriticalEventLogProto[] _emptyArray;
    public int capacity;
    public CriticalEventProto[] events;
    public long timestampMs;
    public int windowMs;

    public static CriticalEventLogProto[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new CriticalEventLogProto[0];
                }
            }
        }
        return _emptyArray;
    }

    public CriticalEventLogProto() {
        clear();
    }

    public CriticalEventLogProto clear() {
        this.timestampMs = 0L;
        this.windowMs = 0;
        this.capacity = 0;
        this.events = CriticalEventProto.emptyArray();
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        long j = this.timestampMs;
        if (j != 0) {
            codedOutputByteBufferNano.writeInt64(1, j);
        }
        int i = this.windowMs;
        if (i != 0) {
            codedOutputByteBufferNano.writeInt32(2, i);
        }
        int i2 = this.capacity;
        if (i2 != 0) {
            codedOutputByteBufferNano.writeInt32(3, i2);
        }
        CriticalEventProto[] criticalEventProtoArr = this.events;
        if (criticalEventProtoArr != null && criticalEventProtoArr.length > 0) {
            int i3 = 0;
            while (true) {
                CriticalEventProto[] criticalEventProtoArr2 = this.events;
                if (i3 >= criticalEventProtoArr2.length) {
                    break;
                }
                CriticalEventProto criticalEventProto = criticalEventProtoArr2[i3];
                if (criticalEventProto != null) {
                    codedOutputByteBufferNano.writeMessage(4, criticalEventProto);
                }
                i3++;
            }
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        long j = this.timestampMs;
        if (j != 0) {
            computeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(1, j);
        }
        int i = this.windowMs;
        if (i != 0) {
            computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(2, i);
        }
        int i2 = this.capacity;
        if (i2 != 0) {
            computeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(3, i2);
        }
        CriticalEventProto[] criticalEventProtoArr = this.events;
        if (criticalEventProtoArr != null && criticalEventProtoArr.length > 0) {
            int i3 = 0;
            while (true) {
                CriticalEventProto[] criticalEventProtoArr2 = this.events;
                if (i3 >= criticalEventProtoArr2.length) {
                    break;
                }
                CriticalEventProto criticalEventProto = criticalEventProtoArr2[i3];
                if (criticalEventProto != null) {
                    computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(4, criticalEventProto);
                }
                i3++;
            }
        }
        return computeSerializedSize;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public CriticalEventLogProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                break;
            }
            if (readTag == 8) {
                this.timestampMs = codedInputByteBufferNano.readInt64();
            } else if (readTag == 16) {
                this.windowMs = codedInputByteBufferNano.readInt32();
            } else if (readTag == 24) {
                this.capacity = codedInputByteBufferNano.readInt32();
            } else if (readTag != 34) {
                if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                    break;
                }
            } else {
                int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
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

    public static CriticalEventLogProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
        return (CriticalEventLogProto) MessageNano.mergeFrom(new CriticalEventLogProto(), bArr);
    }

    public static CriticalEventLogProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        return new CriticalEventLogProto().mergeFrom(codedInputByteBufferNano);
    }
}
