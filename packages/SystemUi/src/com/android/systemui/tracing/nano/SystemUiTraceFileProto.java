package com.android.systemui.tracing.nano;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SystemUiTraceFileProto extends MessageNano {
    public SystemUiTraceEntryProto[] entry;
    public long magicNumber;

    public SystemUiTraceFileProto() {
        clear();
    }

    public SystemUiTraceFileProto clear() {
        this.magicNumber = 0L;
        this.entry = SystemUiTraceEntryProto.emptyArray();
        this.cachedSize = -1;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public int computeSerializedSize() {
        int i = 0;
        int computeTagSize = this.magicNumber != 0 ? CodedOutputByteBufferNano.computeTagSize(1) + 8 + 0 : 0;
        SystemUiTraceEntryProto[] systemUiTraceEntryProtoArr = this.entry;
        if (systemUiTraceEntryProtoArr != null && systemUiTraceEntryProtoArr.length > 0) {
            while (true) {
                SystemUiTraceEntryProto[] systemUiTraceEntryProtoArr2 = this.entry;
                if (i >= systemUiTraceEntryProtoArr2.length) {
                    break;
                }
                SystemUiTraceEntryProto systemUiTraceEntryProto = systemUiTraceEntryProtoArr2[i];
                if (systemUiTraceEntryProto != null) {
                    computeTagSize += CodedOutputByteBufferNano.computeMessageSize(2, systemUiTraceEntryProto);
                }
                i++;
            }
        }
        return computeTagSize;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) {
        long j = this.magicNumber;
        if (j != 0) {
            codedOutputByteBufferNano.writeFixed64(1, j);
        }
        SystemUiTraceEntryProto[] systemUiTraceEntryProtoArr = this.entry;
        if (systemUiTraceEntryProtoArr == null || systemUiTraceEntryProtoArr.length <= 0) {
            return;
        }
        int i = 0;
        while (true) {
            SystemUiTraceEntryProto[] systemUiTraceEntryProtoArr2 = this.entry;
            if (i >= systemUiTraceEntryProtoArr2.length) {
                return;
            }
            SystemUiTraceEntryProto systemUiTraceEntryProto = systemUiTraceEntryProtoArr2[i];
            if (systemUiTraceEntryProto != null) {
                codedOutputByteBufferNano.writeMessage(2, systemUiTraceEntryProto);
            }
            i++;
        }
    }
}
