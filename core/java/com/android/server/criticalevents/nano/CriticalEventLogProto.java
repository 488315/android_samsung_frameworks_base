package com.android.server.criticalevents.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;

import java.io.IOException;

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
    public void writeTo(CodedOutputByteBufferNano output) throws IOException {
        if (this.timestampMs != 0) {
            output.writeInt64(1, this.timestampMs);
        }
        if (this.windowMs != 0) {
            output.writeInt32(2, this.windowMs);
        }
        if (this.capacity != 0) {
            output.writeInt32(3, this.capacity);
        }
        if (this.events != null && this.events.length > 0) {
            for (int i = 0; i < this.events.length; i++) {
                CriticalEventProto element = this.events[i];
                if (element != null) {
                    output.writeMessage(4, element);
                }
            }
        }
        super.writeTo(output);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int size = super.computeSerializedSize();
        if (this.timestampMs != 0) {
            size += CodedOutputByteBufferNano.computeInt64Size(1, this.timestampMs);
        }
        if (this.windowMs != 0) {
            size += CodedOutputByteBufferNano.computeInt32Size(2, this.windowMs);
        }
        if (this.capacity != 0) {
            size += CodedOutputByteBufferNano.computeInt32Size(3, this.capacity);
        }
        if (this.events != null && this.events.length > 0) {
            for (int i = 0; i < this.events.length; i++) {
                CriticalEventProto element = this.events[i];
                if (element != null) {
                    size += CodedOutputByteBufferNano.computeMessageSize(4, element);
                }
            }
        }
        return size;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public CriticalEventLogProto mergeFrom(CodedInputByteBufferNano input) throws IOException {
        while (true) {
            int tag = input.readTag();
            switch (tag) {
                case 0:
                    return this;
                case 8:
                    this.timestampMs = input.readInt64();
                    break;
                case 16:
                    this.windowMs = input.readInt32();
                    break;
                case 24:
                    int arrayLength = input.readInt32();
                    this.capacity = arrayLength;
                    break;
                case 34:
                    int arrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(input, 34);
                    int i = this.events == null ? 0 : this.events.length;
                    CriticalEventProto[] newArray = new CriticalEventProto[i + arrayLength2];
                    if (i != 0) {
                        System.arraycopy(this.events, 0, newArray, 0, i);
                    }
                    while (i < newArray.length - 1) {
                        newArray[i] = new CriticalEventProto();
                        input.readMessage(newArray[i]);
                        input.readTag();
                        i++;
                    }
                    newArray[i] = new CriticalEventProto();
                    input.readMessage(newArray[i]);
                    this.events = newArray;
                    break;
                default:
                    if (!WireFormatNano.parseUnknownField(input, tag)) {
                        return this;
                    }
                    break;
            }
        }
    }

    public static CriticalEventLogProto parseFrom(byte[] data)
            throws InvalidProtocolBufferNanoException {
        return (CriticalEventLogProto) MessageNano.mergeFrom(new CriticalEventLogProto(), data);
    }

    public static CriticalEventLogProto parseFrom(CodedInputByteBufferNano input)
            throws IOException {
        return new CriticalEventLogProto().mergeFrom(input);
    }
}
