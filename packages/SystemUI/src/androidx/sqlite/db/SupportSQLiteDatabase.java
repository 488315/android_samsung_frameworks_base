package androidx.sqlite.db;

import android.database.Cursor;
import java.io.Closeable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface SupportSQLiteDatabase extends Closeable {
    void beginTransaction();

    void beginTransactionNonExclusive();

    default void beginTransactionReadOnly() {
        beginTransaction();
    }

    SupportSQLiteStatement compileStatement(String str);

    void endTransaction();

    void execSQL(String str);

    boolean inTransaction();

    boolean isOpen();

    Cursor query();

    Cursor query(SupportSQLiteQuery supportSQLiteQuery);

    void setTransactionSuccessful();
}
