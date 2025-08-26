package com.android.server.ondeviceintelligence.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

/* loaded from: classes6.dex */
public final class InferenceInfo extends MessageNano {
    private static volatile InferenceInfo[] _emptyArray;
    public long endTimeMs;
    public long startTimeMs;
    public long suspendedTimeMs;
    public int uid;

    public static InferenceInfo[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new InferenceInfo[0];
                }
            }
        }
        return _emptyArray;
    }

    public InferenceInfo() {
        clear();
    }

    public InferenceInfo clear() {
        this.uid = 0;
        this.startTimeMs = 0L;
        this.endTimeMs = 0L;
        this.suspendedTimeMs = 0L;
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        int i = this.uid;
        if (i != 0) {
            codedOutputByteBufferNano.writeInt32(1, i);
        }
        long j = this.startTimeMs;
        if (j != 0) {
            codedOutputByteBufferNano.writeInt64(2, j);
        }
        long j2 = this.endTimeMs;
        if (j2 != 0) {
            codedOutputByteBufferNano.writeInt64(3, j2);
        }
        long j3 = this.suspendedTimeMs;
        if (j3 != 0) {
            codedOutputByteBufferNano.writeInt64(4, j3);
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int iComputeSerializedSize = super.computeSerializedSize();
        int i = this.uid;
        if (i != 0) {
            iComputeSerializedSize += CodedOutputByteBufferNano.computeInt32Size(1, i);
        }
        long j = this.startTimeMs;
        if (j != 0) {
            iComputeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(2, j);
        }
        long j2 = this.endTimeMs;
        if (j2 != 0) {
            iComputeSerializedSize += CodedOutputByteBufferNano.computeInt64Size(3, j2);
        }
        long j3 = this.suspendedTimeMs;
        return j3 != 0 ? iComputeSerializedSize + CodedOutputByteBufferNano.computeInt64Size(4, j3) : iComputeSerializedSize;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public InferenceInfo mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int tag = codedInputByteBufferNano.readTag();
            if (tag == 0) {
                break;
            }
            if (tag == 8) {
                this.uid = codedInputByteBufferNano.readInt32();
            } else if (tag == 16) {
                this.startTimeMs = codedInputByteBufferNano.readInt64();
            } else if (tag == 24) {
                this.endTimeMs = codedInputByteBufferNano.readInt64();
            } else if (tag != 32) {
                if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, tag)) {
                    break;
                }
            } else {
                this.suspendedTimeMs = codedInputByteBufferNano.readInt64();
            }
        }
        return this;
    }

    public static InferenceInfo parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
        return (InferenceInfo) MessageNano.mergeFrom(new InferenceInfo(), bArr);
    }

    public static InferenceInfo parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        return new InferenceInfo().mergeFrom(codedInputByteBufferNano);
    }
}
