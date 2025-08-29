package com.google.protobuf;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/* loaded from: classes4.dex */
public abstract class AbstractMessageLite implements MessageLite {
    protected int memoizedHashCode = 0;

    public abstract class Builder implements MessageLiteOrBuilder, Cloneable {
        @Override // 
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public abstract GeneratedMessageLite.Builder mo3286clone();
    }

    public static void addAll(Iterable iterable, List list) {
        Charset charset = Internal.UTF_8;
        iterable.getClass();
        if (iterable instanceof LazyStringList) {
            List underlyingElements = ((LazyStringList) iterable).getUnderlyingElements();
            LazyStringList lazyStringList = (LazyStringList) list;
            int size = list.size();
            for (Object obj : underlyingElements) {
                if (obj == null) {
                    String str = "Element at index " + (lazyStringList.size() - size) + " is null.";
                    for (int size2 = lazyStringList.size() - 1; size2 >= size; size2--) {
                        lazyStringList.remove(size2);
                    }
                    throw new NullPointerException(str);
                }
                if (obj instanceof ByteString) {
                    lazyStringList.add((ByteString) obj);
                } else {
                    lazyStringList.add((LazyStringList) obj);
                }
            }
            return;
        }
        if (iterable instanceof PrimitiveNonBoxingCollection) {
            list.addAll((Collection) iterable);
            return;
        }
        if ((list instanceof ArrayList) && (iterable instanceof Collection)) {
            ((ArrayList) list).ensureCapacity(((Collection) iterable).size() + list.size());
        }
        int size3 = list.size();
        for (Object obj2 : iterable) {
            if (obj2 == null) {
                String str2 = "Element at index " + (list.size() - size3) + " is null.";
                for (int size4 = list.size() - 1; size4 >= size3; size4--) {
                    list.remove(size4);
                }
                throw new NullPointerException(str2);
            }
            list.add(obj2);
        }
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

    public final String getSerializingExceptionMessage(String str) {
        return "Serializing " + getClass().getName() + " to a " + str + " threw an IOException (should never happen).";
    }

    public void setMemoizedSerializedSize(int i) {
        throw new UnsupportedOperationException();
    }

    public final byte[] toByteArray() {
        try {
            int serializedSize = ((GeneratedMessageLite) this).getSerializedSize(null);
            byte[] bArr = new byte[serializedSize];
            Logger logger = CodedOutputStream.logger;
            CodedOutputStream.ArrayEncoder arrayEncoder = new CodedOutputStream.ArrayEncoder(bArr, 0, serializedSize);
            ((GeneratedMessageLite) this).writeTo(arrayEncoder);
            if (arrayEncoder.spaceLeft() == 0) {
                return bArr;
            }
            throw new IllegalStateException("Did not write as much data as expected.");
        } catch (IOException e) {
            throw new RuntimeException(getSerializingExceptionMessage("byte array"), e);
        }
    }

    public final void writeTo(OutputStream outputStream) throws IOException {
        GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) this;
        int serializedSize = generatedMessageLite.getSerializedSize(null);
        Logger logger = CodedOutputStream.logger;
        if (serializedSize > 4096) {
            serializedSize = 4096;
        }
        CodedOutputStream.OutputStreamEncoder outputStreamEncoder = new CodedOutputStream.OutputStreamEncoder(outputStream, serializedSize);
        generatedMessageLite.writeTo(outputStreamEncoder);
        if (outputStreamEncoder.position > 0) {
            outputStreamEncoder.doFlush();
        }
    }
}
