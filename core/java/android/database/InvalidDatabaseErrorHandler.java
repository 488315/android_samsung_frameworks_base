package android.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDump;

public class InvalidDatabaseErrorHandler extends ErrorHandler {
    public InvalidDatabaseErrorHandler() {}

    public InvalidDatabaseErrorHandler(SQLiteDump dump) {
        this.mDbDump = dump;
    }

    @Override // android.database.ErrorHandler
    boolean preHandleError(SQLiteDatabase db) {
        return true;
    }

    @Override // android.database.ErrorHandler
    boolean diagnoseError(SQLiteDatabase db) {
        return false;
    }

    @Override // android.database.ErrorHandler
    void postHandleError(SQLiteDatabase db, boolean deleteDatabase) {
        if (!deleteDatabase) {
            return;
        }
        this.mDbDump.addDumpLog(ErrorHandler.TAG, "!@ Back up corrupted DB File : " + db.getPath());
        DefaultDatabaseErrorHandler.backupDatabaseFile(db.getPath());
    }
}
