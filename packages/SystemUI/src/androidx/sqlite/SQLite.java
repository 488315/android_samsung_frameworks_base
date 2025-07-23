package androidx.sqlite;

import android.database.SQLException;
import kotlin.jdk7.AutoCloseableKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SQLite {
    public static final void execSQL(SQLiteConnection sQLiteConnection, String str) {
        SQLiteStatement prepare = sQLiteConnection.prepare(str);
        try {
            prepare.step();
            AutoCloseableKt.closeFinally(prepare, null);
        } finally {
        }
    }

    public static final void throwSQLiteException(int i, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("Error code: " + i);
        if (str != null) {
            sb.append(", message: ".concat(str));
        }
        throw new SQLException(sb.toString());
    }
}
