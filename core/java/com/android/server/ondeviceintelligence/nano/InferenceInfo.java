package com.android.server.ondeviceintelligence.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;

import java.io.IOException;

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
    public void writeTo(CodedOutputByteBufferNano output) throws IOException {
        if (this.uid != 0) {
            output.writeInt32(1, this.uid);
        }
        if (this.startTimeMs != 0) {
            output.writeInt64(2, this.startTimeMs);
        }
        if (this.endTimeMs != 0) {
            output.writeInt64(3, this.endTimeMs);
        }
        if (this.suspendedTimeMs != 0) {
            output.writeInt64(4, this.suspendedTimeMs);
        }
        super.writeTo(output);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int size = super.computeSerializedSize();
        if (this.uid != 0) {
            size += CodedOutputByteBufferNano.computeInt32Size(1, this.uid);
        }
        if (this.startTimeMs != 0) {
            size += CodedOutputByteBufferNano.computeInt64Size(2, this.startTimeMs);
        }
        if (this.endTimeMs != 0) {
            size += CodedOutputByteBufferNano.computeInt64Size(3, this.endTimeMs);
        }
        if (this.suspendedTimeMs != 0) {
            return size + CodedOutputByteBufferNano.computeInt64Size(4, this.suspendedTimeMs);
        }
        return size;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public InferenceInfo mergeFrom(CodedInputByteBufferNano input) throws IOException {
        while (true) {
            int tag = input.readTag();
            switch (tag) {
                case 0:
                    return this;
                case 8:
                    this.uid = input.readInt32();
                    break;
                case 16:
                    this.startTimeMs = input.readInt64();
                    break;
                case 24:
                    this.endTimeMs = input.readInt64();
                    break;
                case 32:
                    this.suspendedTimeMs = input.readInt64();
                    break;
                default:
                    if (!WireFormatNano.parseUnknownField(input, tag)) {
                        return this;
                    }
                    break;
            }
        }
    }

    public static InferenceInfo parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
        return (InferenceInfo) MessageNano.mergeFrom(new InferenceInfo(), data);
    }

    public static InferenceInfo parseFrom(CodedInputByteBufferNano input) throws IOException {
        return new InferenceInfo().mergeFrom(input);
    }
}
