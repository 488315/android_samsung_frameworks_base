package com.android.wm.shell.nano;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Transition extends MessageNano {
    private static volatile Transition[] _emptyArray;
    public long abortTimeNs;
    public long dispatchTimeNs;
    public int handler;

    /* renamed from: id */
    public int f455id;
    public long mergeRequestTimeNs;
    public long mergeTimeNs;
    public int mergedInto;

    public Transition() {
        clear();
    }

    public static Transition[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new Transition[0];
                }
            }
        }
        return _emptyArray;
    }

    public Transition clear() {
        this.f455id = 0;
        this.dispatchTimeNs = 0L;
        this.handler = 0;
        this.mergeTimeNs = 0L;
        this.mergeRequestTimeNs = 0L;
        this.mergedInto = 0;
        this.abortTimeNs = 0L;
        this.cachedSize = -1;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public int computeSerializedSize() {
        int computeInt32Size = CodedOutputByteBufferNano.computeInt32Size(1, this.f455id) + 0;
        long j = this.dispatchTimeNs;
        if (j != 0) {
            computeInt32Size += CodedOutputByteBufferNano.computeInt64Size(2, j);
        }
        int i = this.handler;
        if (i != 0) {
            computeInt32Size += CodedOutputByteBufferNano.computeInt32Size(3, i);
        }
        long j2 = this.mergeTimeNs;
        if (j2 != 0) {
            computeInt32Size += CodedOutputByteBufferNano.computeInt64Size(4, j2);
        }
        long j3 = this.mergeRequestTimeNs;
        if (j3 != 0) {
            computeInt32Size += CodedOutputByteBufferNano.computeInt64Size(5, j3);
        }
        int i2 = this.mergedInto;
        if (i2 != 0) {
            computeInt32Size += CodedOutputByteBufferNano.computeInt32Size(6, i2);
        }
        long j4 = this.abortTimeNs;
        return j4 != 0 ? computeInt32Size + CodedOutputByteBufferNano.computeInt64Size(7, j4) : computeInt32Size;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) {
        codedOutputByteBufferNano.writeInt32(1, this.f455id);
        long j = this.dispatchTimeNs;
        if (j != 0) {
            codedOutputByteBufferNano.writeTag(2, 0);
            while ((j & (-128)) != 0) {
                codedOutputByteBufferNano.writeRawByte((((int) j) & 127) | 128);
                j >>>= 7;
            }
            codedOutputByteBufferNano.writeRawByte((int) j);
        }
        int i = this.handler;
        if (i != 0) {
            codedOutputByteBufferNano.writeInt32(3, i);
        }
        long j2 = this.mergeTimeNs;
        if (j2 != 0) {
            codedOutputByteBufferNano.writeTag(4, 0);
            while ((j2 & (-128)) != 0) {
                codedOutputByteBufferNano.writeRawByte((((int) j2) & 127) | 128);
                j2 >>>= 7;
            }
            codedOutputByteBufferNano.writeRawByte((int) j2);
        }
        long j3 = this.mergeRequestTimeNs;
        if (j3 != 0) {
            codedOutputByteBufferNano.writeTag(5, 0);
            while ((j3 & (-128)) != 0) {
                codedOutputByteBufferNano.writeRawByte((((int) j3) & 127) | 128);
                j3 >>>= 7;
            }
            codedOutputByteBufferNano.writeRawByte((int) j3);
        }
        int i2 = this.mergedInto;
        if (i2 != 0) {
            codedOutputByteBufferNano.writeInt32(6, i2);
        }
        long j4 = this.abortTimeNs;
        if (j4 != 0) {
            codedOutputByteBufferNano.writeTag(7, 0);
            while ((j4 & (-128)) != 0) {
                codedOutputByteBufferNano.writeRawByte((((int) j4) & 127) | 128);
                j4 >>>= 7;
            }
            codedOutputByteBufferNano.writeRawByte((int) j4);
        }
    }
}
