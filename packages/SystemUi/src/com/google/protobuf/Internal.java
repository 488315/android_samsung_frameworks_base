package com.google.protobuf;

import com.google.protobuf.CodedInputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Internal {
    public static final byte[] EMPTY_BYTE_ARRAY;
    public static final Charset UTF_8;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface EnumLite {
        int getNumber();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface EnumLiteMap {
        EnumLite findValueByNumber(int i);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface EnumVerifier {
        boolean isInRange(int i);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ProtobufList extends List, RandomAccess {
        ProtobufList mutableCopyWithCapacity(int i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static {
        Charset.forName("US-ASCII");
        UTF_8 = Charset.forName("UTF-8");
        Charset.forName("ISO-8859-1");
        byte[] bArr = new byte[0];
        EMPTY_BYTE_ARRAY = bArr;
        ByteBuffer.wrap(bArr);
        try {
            new CodedInputStream.ArrayDecoder(bArr, 0, 0 == true ? 1 : 0, 0 == true ? 1 : 0).pushLimit(0);
        } catch (InvalidProtocolBufferException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Internal() {
    }

    public static int hashLong(long j) {
        return (int) (j ^ (j >>> 32));
    }
}
