package com.android.p038wm.shell.nano;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HandlerMapping extends MessageNano {
    private static volatile HandlerMapping[] _emptyArray;

    /* renamed from: id */
    public int f454id;
    public String name;

    public HandlerMapping() {
        clear();
    }

    public static HandlerMapping[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new HandlerMapping[0];
                }
            }
        }
        return _emptyArray;
    }

    public HandlerMapping clear() {
        this.f454id = 0;
        this.name = "";
        this.cachedSize = -1;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public int computeSerializedSize() {
        return CodedOutputByteBufferNano.computeStringSize(2, this.name) + CodedOutputByteBufferNano.computeInt32Size(1, this.f454id) + 0;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) {
        codedOutputByteBufferNano.writeInt32(1, this.f454id);
        codedOutputByteBufferNano.writeString(2, this.name);
    }
}
