package androidx.sqlite.db;

import java.io.Closeable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface SupportSQLiteProgram extends Closeable {
    void bindBlob(int i, byte[] bArr);

    void bindDouble(double d, int i);

    void bindLong(int i, long j);

    void bindNull(int i);

    void bindString(int i, String str);
}
