package android.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDump;

/* loaded from: classes.dex */
public abstract class ErrorHandler {
    public static final String TAG = "ErrorHandler";
    public SQLiteDump mDbDump = SQLiteDump.DUMMY_DB_DUMP;

    void postHandleError(SQLiteDatabase sQLiteDatabase, boolean z) {
    }

    abstract boolean preHandleError(SQLiteDatabase sQLiteDatabase);

    public void handleError(SQLiteDatabase sQLiteDatabase, boolean z) {
        try {
            if (preHandleError(sQLiteDatabase) && !diagnoseError(sQLiteDatabase) && !recoverError(sQLiteDatabase)) {
                sQLiteDatabase.setDatabaseIsCorrupted(true);
                postHandleError(sQLiteDatabase, z);
            }
        } catch (Exception e) {
            this.mDbDump.logAndDump(TAG, "!@ Exception in error handling", e);
        }
    }

    boolean diagnoseError(SQLiteDatabase sQLiteDatabase) {
        if (!sQLiteDatabase.diagnoseError()) {
            return false;
        }
        this.mDbDump.logAndDump(TAG, "!@ Diagnose Succeed.");
        return true;
    }

    boolean recoverError(SQLiteDatabase sQLiteDatabase) {
        if (sQLiteDatabase.doRecovery()) {
            this.mDbDump.logAndDump(TAG, "!@ Recovery Succeed.");
            return true;
        }
        this.mDbDump.logAndDump(TAG, "!@ Recovery Failed.");
        return false;
    }
}
