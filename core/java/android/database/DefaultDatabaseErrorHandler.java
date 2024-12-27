package android.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseConfiguration;
import android.database.sqlite.SQLiteDump;
import android.util.Log;

public final class DefaultDatabaseErrorHandler implements DatabaseErrorHandler {
    private static final String CORRUPT_SUFFIX = ".corrupt";
    private static final String[] DATABASE_SUFFIX = {"", "-journal", "-shm", "-wal", "-se"};
    private static final String TAG = "DefaultDatabaseErrorHandler";
    private SQLiteDump mDbDump;
    private boolean mDeleteDatabaseIfCorrupted;

    public DefaultDatabaseErrorHandler() {
        this.mDeleteDatabaseIfCorrupted = true;
        this.mDbDump = SQLiteDump.DUMMY_DB_DUMP;
    }

    public DefaultDatabaseErrorHandler(SQLiteDump dump) {
        this.mDeleteDatabaseIfCorrupted = true;
        this.mDbDump = SQLiteDump.DUMMY_DB_DUMP;
        this.mDbDump = dump;
    }

    public static void backupDatabaseFile(String path) {
        Log.e(TAG, "!@ Back up corrupted DB File : " + path);
        SQLiteDatabase.deleteDatabaseFile(path + CORRUPT_SUFFIX);
        SQLiteDatabase.renameDatabaseFile(path, path + CORRUPT_SUFFIX);
    }

    public void setDeleteDatabaseIfCorrupted(boolean deleteDatabaseIfCorrupted) {
        this.mDeleteDatabaseIfCorrupted = deleteDatabaseIfCorrupted;
    }

    @Override // android.database.DatabaseErrorHandler
    public void onCorruption(SQLiteDatabase dbObj) {
        this.mDbDump.logAndDump(
                TAG, "Corruption reported by sqlite on database: " + dbObj.getPath());
        SQLiteDatabase.wipeDetected(dbObj.getPath(), "corruption");
        this.mDbDump.addDumpLog(
                TAG,
                "DB wipe detected: package= reason=corruption file="
                        + dbObj.getPath()
                        + " "
                        + SQLiteDatabase.getFileTimestamps(dbObj.getPath())
                        + " checkfile "
                        + SQLiteDatabase.getFileTimestamps(dbObj.getPath() + "-wipecheck"),
                Log.getStackTraceString(new Throwable("STACKTRACE")));
        ErrorHandler errorHandler = getErrorHandler(dbObj);
        if (!this.mDeleteDatabaseIfCorrupted) {
            this.mDbDump.logAndDump(TAG, "This application uses own corruption handler.");
        }
        errorHandler.handleError(dbObj, this.mDeleteDatabaseIfCorrupted);
    }

    private ErrorHandler getErrorHandler(SQLiteDatabase dbObj) {
        String path = dbObj.getPath();
        if (path.equalsIgnoreCase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH)
                || path.trim().length() == 0) {
            return new DummyDatabaseErrorHandler(this.mDbDump);
        }
        if (!dbObj.isOpen()) {
            return new InvalidDatabaseErrorHandler(this.mDbDump);
        }
        return new CorruptDatabaseErrorHandler(this.mDbDump);
    }
}
