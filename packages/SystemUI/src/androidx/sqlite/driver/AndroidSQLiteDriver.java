package androidx.sqlite.driver;

import android.database.sqlite.SQLiteDatabase;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteDriver;

/* loaded from: classes.dex */
public final class AndroidSQLiteDriver implements SQLiteDriver {
    @Override // androidx.sqlite.SQLiteDriver
    public final SQLiteConnection open(String str) {
        SQLiteDatabase sQLiteDatabaseOpenOrCreateDatabase = SQLiteDatabase.openOrCreateDatabase(str, (SQLiteDatabase.CursorFactory) null);
        sQLiteDatabaseOpenOrCreateDatabase.getClass();
        return new AndroidSQLiteConnection(sQLiteDatabaseOpenOrCreateDatabase);
    }
}
