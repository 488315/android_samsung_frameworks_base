package com.android.systemui.tracing.nano;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SystemUiTraceProto extends MessageNano {
    public EdgeBackGestureHandlerProto edgeBackGestureHandler;

    public SystemUiTraceProto() {
        clear();
    }

    public SystemUiTraceProto clear() {
        this.edgeBackGestureHandler = null;
        this.cachedSize = -1;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public int computeSerializedSize() {
        EdgeBackGestureHandlerProto edgeBackGestureHandlerProto = this.edgeBackGestureHandler;
        if (edgeBackGestureHandlerProto != null) {
            return 0 + CodedOutputByteBufferNano.computeMessageSize(1, edgeBackGestureHandlerProto);
        }
        return 0;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) {
        EdgeBackGestureHandlerProto edgeBackGestureHandlerProto = this.edgeBackGestureHandler;
        if (edgeBackGestureHandlerProto != null) {
            codedOutputByteBufferNano.writeMessage(1, edgeBackGestureHandlerProto);
        }
    }
}
