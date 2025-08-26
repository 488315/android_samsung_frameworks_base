package androidx.sqlite.db;

import android.database.Cursor;
import java.io.Closeable;

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
