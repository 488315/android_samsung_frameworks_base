package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.ByteString;
import androidx.datastore.preferences.protobuf.CodedOutputStream;
import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class AbstractMessageLite implements MessageLite {
    protected int memoizedHashCode = 0;

    public abstract class Builder implements MessageLiteOrBuilder, Cloneable {
        @Override // 
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public abstract GeneratedMessageLite.Builder mo898clone();
    }

    public int getMemoizedSerializedSize() {
        throw new UnsupportedOperationException();
    }

    public int getSerializedSize(Schema schema) {
        int memoizedSerializedSize = getMemoizedSerializedSize();
        if (memoizedSerializedSize != -1) {
            return memoizedSerializedSize;
        }
        int serializedSize = schema.getSerializedSize(this);
        setMemoizedSerializedSize(serializedSize);
        return serializedSize;
    }

    public void setMemoizedSerializedSize(int i) {
        throw new UnsupportedOperationException();
    }

    public final ByteString toByteString() {
        try {
            int serializedSize = ((GeneratedMessageLite) this).getSerializedSize(null);
            ByteString byteString = ByteString.EMPTY;
            ByteString.CodedBuilder codedBuilder = new ByteString.CodedBuilder(serializedSize, null);
            CodedOutputStream.ArrayEncoder arrayEncoder = codedBuilder.output;
            ((GeneratedMessageLite) this).writeTo(arrayEncoder);
            if (arrayEncoder.limit - arrayEncoder.position == 0) {
                return new ByteString.LiteralByteString(codedBuilder.buffer);
            }
            throw new IllegalStateException("Did not write as much data as expected.");
        } catch (IOException e) {
            throw new RuntimeException("Serializing " + getClass().getName() + " to a ByteString threw an IOException (should never happen).", e);
        }
    }
}
