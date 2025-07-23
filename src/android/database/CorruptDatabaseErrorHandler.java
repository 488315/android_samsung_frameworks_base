package android.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDump;
import android.database.sqlite.SQLiteException;
import android.util.Pair;
import java.util.List;

/* loaded from: classes.dex */
public class CorruptDatabaseErrorHandler extends ErrorHandler {
    public CorruptDatabaseErrorHandler() {
    }

    public CorruptDatabaseErrorHandler(SQLiteDump sQLiteDump) {
        this.mDbDump = sQLiteDump;
    }

    @Override // android.database.ErrorHandler
    boolean preHandleError(SQLiteDatabase sQLiteDatabase) {
        if (!sQLiteDatabase.isForcedReadOnlyDatabase()) {
            return true;
        }
        this.mDbDump.addDumpLog(ErrorHandler.TAG, "There was a corruption, but ignoring it because the connection is read-only connection.");
        throw new SQLiteException("There was a corruption, but ignoring it because the connection is read-only connection.");
    }

    @Override // android.database.ErrorHandler
    void postHandleError(SQLiteDatabase sQLiteDatabase, boolean z) {
        if (z) {
            List<Pair<String, String>> attachedDbs = sQLiteDatabase.getAttachedDbs();
            sQLiteDatabase.setCheckpointOnClose(false);
            sQLiteDatabase.close();
            if (attachedDbs != null) {
                for (Pair<String, String> pair : attachedDbs) {
                    this.mDbDump.addDumpLog(ErrorHandler.TAG, "!@ Back up corrupted DB File : " + pair.second);
                    DefaultDatabaseErrorHandler.backupDatabaseFile(pair.second);
                }
                return;
            }
            this.mDbDump.addDumpLog(ErrorHandler.TAG, "!@ Back up corrupted DB File : " + sQLiteDatabase.getPath());
            DefaultDatabaseErrorHandler.backupDatabaseFile(sQLiteDatabase.getPath());
        }
    }
}
