package android.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseConfiguration;
import android.database.sqlite.SQLiteDump;
import android.util.Log;

/* loaded from: classes.dex */
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

    public DefaultDatabaseErrorHandler(SQLiteDump sQLiteDump) {
        this.mDeleteDatabaseIfCorrupted = true;
        SQLiteDump sQLiteDump2 = SQLiteDump.DUMMY_DB_DUMP;
        this.mDbDump = sQLiteDump;
    }

    public static void backupDatabaseFile(String str) {
        Log.e(TAG, "!@ Back up corrupted DB File : " + str);
        SQLiteDatabase.deleteDatabaseFile(str + CORRUPT_SUFFIX);
        SQLiteDatabase.renameDatabaseFile(str, str + CORRUPT_SUFFIX);
    }

    public void setDeleteDatabaseIfCorrupted(boolean z) {
        this.mDeleteDatabaseIfCorrupted = z;
    }

    @Override // android.database.DatabaseErrorHandler
    public void onCorruption(SQLiteDatabase sQLiteDatabase) {
        this.mDbDump.logAndDump(TAG, "Corruption reported by sqlite on database: " + sQLiteDatabase.getPath());
        SQLiteDatabase.wipeDetected(sQLiteDatabase.getPath(), "corruption");
        SQLiteDump sQLiteDump = this.mDbDump;
        StringBuilder sb = new StringBuilder("DB wipe detected: package= reason=corruption file=");
        sb.append(sQLiteDatabase.getPath());
        sb.append(" ");
        sb.append(SQLiteDatabase.getFileTimestamps(sQLiteDatabase.getPath()));
        sb.append(" checkfile ");
        sb.append(SQLiteDatabase.getFileTimestamps(sQLiteDatabase.getPath() + "-wipecheck"));
        sQLiteDump.addDumpLog(TAG, sb.toString(), Log.getStackTraceString(new Throwable("STACKTRACE")));
        ErrorHandler errorHandler = getErrorHandler(sQLiteDatabase);
        if (!this.mDeleteDatabaseIfCorrupted) {
            this.mDbDump.logAndDump(TAG, "This application uses own corruption handler.");
        }
        errorHandler.handleError(sQLiteDatabase, this.mDeleteDatabaseIfCorrupted);
    }

    private ErrorHandler getErrorHandler(SQLiteDatabase sQLiteDatabase) {
        String path = sQLiteDatabase.getPath();
        if (path.equalsIgnoreCase(SQLiteDatabaseConfiguration.MEMORY_DB_PATH) || path.trim().length() == 0) {
            return new DummyDatabaseErrorHandler(this.mDbDump);
        }
        if (!sQLiteDatabase.isOpen()) {
            return new InvalidDatabaseErrorHandler(this.mDbDump);
        }
        return new CorruptDatabaseErrorHandler(this.mDbDump);
    }
}
