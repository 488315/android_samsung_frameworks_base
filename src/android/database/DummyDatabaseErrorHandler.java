package android.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDump;

/* loaded from: classes.dex */
public class DummyDatabaseErrorHandler extends ErrorHandler {
    @Override // android.database.ErrorHandler
    boolean preHandleError(SQLiteDatabase sQLiteDatabase) {
        return false;
    }

    public DummyDatabaseErrorHandler() {
    }

    public DummyDatabaseErrorHandler(SQLiteDump sQLiteDump) {
        this.mDbDump = sQLiteDump;
    }
}
