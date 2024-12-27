package android.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDump;

public class DummyDatabaseErrorHandler extends ErrorHandler {
    public DummyDatabaseErrorHandler() {}

    public DummyDatabaseErrorHandler(SQLiteDump dump) {
        this.mDbDump = dump;
    }

    @Override // android.database.ErrorHandler
    boolean preHandleError(SQLiteDatabase db) {
        return false;
    }
}
