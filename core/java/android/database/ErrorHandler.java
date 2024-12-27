package android.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDump;

public abstract class ErrorHandler {
    public static final String TAG = "ErrorHandler";
    public SQLiteDump mDbDump = SQLiteDump.DUMMY_DB_DUMP;

    abstract boolean preHandleError(SQLiteDatabase sQLiteDatabase);

    public void handleError(SQLiteDatabase db, boolean deleteDatabase) {
        try {
            if (!preHandleError(db) || diagnoseError(db) || recoverError(db)) {
                return;
            }
            db.setDatabaseIsCorrupted(true);
            postHandleError(db, deleteDatabase);
        } catch (Exception e) {
            this.mDbDump.logAndDump(TAG, "!@ Exception in error handling", e);
        }
    }

    boolean diagnoseError(SQLiteDatabase db) {
        if (db.diagnoseError()) {
            this.mDbDump.logAndDump(TAG, "!@ Diagnose Succeed.");
            return true;
        }
        return false;
    }

    boolean recoverError(SQLiteDatabase db) {
        if (db.doRecovery()) {
            this.mDbDump.logAndDump(TAG, "!@ Recovery Succeed.");
            return true;
        }
        this.mDbDump.logAndDump(TAG, "!@ Recovery Failed.");
        return false;
    }

    void postHandleError(SQLiteDatabase db, boolean deleteDatabase) {}
}
