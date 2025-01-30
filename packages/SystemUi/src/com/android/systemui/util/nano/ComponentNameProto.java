package com.android.systemui.util.nano;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ComponentNameProto extends MessageNano {
    public String className;
    public String packageName;

    public ComponentNameProto() {
        clear();
    }

    public ComponentNameProto clear() {
        this.packageName = "";
        this.className = "";
        this.cachedSize = -1;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public int computeSerializedSize() {
        int computeStringSize = this.packageName.equals("") ? 0 : 0 + CodedOutputByteBufferNano.computeStringSize(1, this.packageName);
        return !this.className.equals("") ? computeStringSize + CodedOutputByteBufferNano.computeStringSize(2, this.className) : computeStringSize;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) {
        if (!this.packageName.equals("")) {
            codedOutputByteBufferNano.writeString(1, this.packageName);
        }
        if (this.className.equals("")) {
            return;
        }
        codedOutputByteBufferNano.writeString(2, this.className);
    }
}
