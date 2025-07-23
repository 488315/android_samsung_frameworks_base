package android.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDump;

/* loaded from: classes.dex */
public class InvalidDatabaseErrorHandler extends ErrorHandler {
    @Override // android.database.ErrorHandler
    boolean diagnoseError(SQLiteDatabase sQLiteDatabase) {
        return false;
    }

    @Override // android.database.ErrorHandler
    boolean preHandleError(SQLiteDatabase sQLiteDatabase) {
        return true;
    }

    public InvalidDatabaseErrorHandler() {
    }

    public InvalidDatabaseErrorHandler(SQLiteDump sQLiteDump) {
        this.mDbDump = sQLiteDump;
    }

    @Override // android.database.ErrorHandler
    void postHandleError(SQLiteDatabase sQLiteDatabase, boolean z) {
        if (z) {
            this.mDbDump.addDumpLog(ErrorHandler.TAG, "!@ Back up corrupted DB File : " + sQLiteDatabase.getPath());
            DefaultDatabaseErrorHandler.backupDatabaseFile(sQLiteDatabase.getPath());
        }
    }
}
