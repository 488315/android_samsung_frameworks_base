package com.google.protobuf.nano;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class MessageNano {
    protected volatile int cachedSize = -1;

    public static final byte[] toByteArray(MessageNano messageNano) {
        int serializedSize = messageNano.getSerializedSize();
        byte[] bArr = new byte[serializedSize];
        toByteArray(messageNano, bArr, 0, serializedSize);
        return bArr;
    }

    public int computeSerializedSize() {
        return 0;
    }

    public int getCachedSize() {
        if (this.cachedSize < 0) {
            getSerializedSize();
        }
        return this.cachedSize;
    }

    public int getSerializedSize() {
        int computeSerializedSize = computeSerializedSize();
        this.cachedSize = computeSerializedSize;
        return computeSerializedSize;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            MessageNanoPrinter.print(null, this, new StringBuffer(), stringBuffer);
            return stringBuffer.toString();
        } catch (IllegalAccessException e) {
            return "Error printing proto: " + e.getMessage();
        } catch (InvocationTargetException e2) {
            return "Error printing proto: " + e2.getMessage();
        }
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public MessageNano m2771clone() {
        return (MessageNano) super.clone();
    }

    public static final void toByteArray(MessageNano messageNano, byte[] bArr, int i, int i2) {
        try {
            CodedOutputByteBufferNano newInstance = CodedOutputByteBufferNano.newInstance(bArr, i, i2);
            messageNano.writeTo(newInstance);
            if (newInstance.buffer.remaining() == 0) {
            } else {
                throw new IllegalStateException("Did not write as much data as expected.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) {
    }
}
