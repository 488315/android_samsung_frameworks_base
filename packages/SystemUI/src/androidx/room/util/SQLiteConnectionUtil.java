package androidx.room.util;

import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import kotlin.jdk7.AutoCloseableKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SQLiteConnectionUtil {
    public static final long getLastInsertedRowId(SQLiteConnection sQLiteConnection) {
        if (getTotalChangedRows(sQLiteConnection) == 0) {
            return -1L;
        }
        SQLiteStatement prepare = sQLiteConnection.prepare("SELECT last_insert_rowid()");
        try {
            prepare.step();
            long j = prepare.getLong(0);
            AutoCloseableKt.closeFinally(prepare, null);
            return j;
        } finally {
        }
    }

    public static final int getTotalChangedRows(SQLiteConnection sQLiteConnection) {
        SQLiteStatement prepare = sQLiteConnection.prepare("SELECT changes()");
        try {
            prepare.step();
            int i = (int) prepare.getLong(0);
            AutoCloseableKt.closeFinally(prepare, null);
            return i;
        } finally {
        }
    }
}
