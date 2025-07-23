package com.google.protobuf;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Internal {
    public static final byte[] EMPTY_BYTE_ARRAY;
    public static final Charset UTF_8;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface EnumLite {
        int getNumber();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface EnumLiteMap {
        EnumLite findValueByNumber(int i);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface EnumVerifier {
        boolean isInRange(int i);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface IntList extends ProtobufList {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ProtobufList extends List, RandomAccess {
        ProtobufList mutableCopyWithCapacity(int i);
    }

    static {
        Charset.forName("US-ASCII");
        UTF_8 = Charset.forName("UTF-8");
        Charset.forName("ISO-8859-1");
        byte[] bArr = new byte[0];
        EMPTY_BYTE_ARRAY = bArr;
        ByteBuffer.wrap(bArr);
        CodedInputStream.newInstance(bArr, 0, 0, false);
    }

    private Internal() {
    }

    public static void checkNotNull(Object obj, String str) {
        if (obj == null) {
            throw new NullPointerException(str);
        }
    }

    public static int hashLong(long j) {
        return (int) (j ^ (j >>> 32));
    }
}
