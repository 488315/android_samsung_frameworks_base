package com.android.systemui.tracing.nano;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class EdgeBackGestureHandlerProto extends MessageNano {
    public boolean allowGesture;

    public EdgeBackGestureHandlerProto() {
        clear();
    }

    public EdgeBackGestureHandlerProto clear() {
        this.allowGesture = false;
        this.cachedSize = -1;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public int computeSerializedSize() {
        if (this.allowGesture) {
            return 0 + CodedOutputByteBufferNano.computeTagSize(1) + 1;
        }
        return 0;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) {
        boolean z = this.allowGesture;
        if (z) {
            codedOutputByteBufferNano.writeTag(1, 0);
            codedOutputByteBufferNano.writeRawByte(z ? 1 : 0);
        }
    }
}
