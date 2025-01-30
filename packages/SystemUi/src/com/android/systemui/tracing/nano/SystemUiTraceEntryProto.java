package com.android.systemui.tracing.nano;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SystemUiTraceEntryProto extends MessageNano {
    private static volatile SystemUiTraceEntryProto[] _emptyArray;
    public long elapsedRealtimeNanos;
    public SystemUiTraceProto systemUi;

    public SystemUiTraceEntryProto() {
        clear();
    }

    public static SystemUiTraceEntryProto[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new SystemUiTraceEntryProto[0];
                }
            }
        }
        return _emptyArray;
    }

    public SystemUiTraceEntryProto clear() {
        this.elapsedRealtimeNanos = 0L;
        this.systemUi = null;
        this.cachedSize = -1;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public int computeSerializedSize() {
        int computeTagSize = this.elapsedRealtimeNanos != 0 ? 0 + CodedOutputByteBufferNano.computeTagSize(1) + 8 : 0;
        SystemUiTraceProto systemUiTraceProto = this.systemUi;
        return systemUiTraceProto != null ? computeTagSize + CodedOutputByteBufferNano.computeMessageSize(3, systemUiTraceProto) : computeTagSize;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) {
        long j = this.elapsedRealtimeNanos;
        if (j != 0) {
            codedOutputByteBufferNano.writeFixed64(1, j);
        }
        SystemUiTraceProto systemUiTraceProto = this.systemUi;
        if (systemUiTraceProto != null) {
            codedOutputByteBufferNano.writeMessage(3, systemUiTraceProto);
        }
    }
}
