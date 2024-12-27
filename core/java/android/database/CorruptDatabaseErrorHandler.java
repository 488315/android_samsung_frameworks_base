package android.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDump;
import android.database.sqlite.SQLiteException;
import android.util.Pair;

import java.util.List;

/* loaded from: classes.dex */
public class CorruptDatabaseErrorHandler extends ErrorHandler {
    public CorruptDatabaseErrorHandler() {}

    public CorruptDatabaseErrorHandler(SQLiteDump dump) {
        this.mDbDump = dump;
    }

    @Override // android.database.ErrorHandler
    boolean preHandleError(SQLiteDatabase db) {
        if (db.isForcedReadOnlyDatabase()) {
            this.mDbDump.addDumpLog(
                    ErrorHandler.TAG,
                    "There was a corruption, but ignoring it because the connection is read-only"
                            + " connection.");
            throw new SQLiteException(
                    "There was a corruption, but ignoring it because the connection is read-only"
                            + " connection.");
        }
        return true;
    }

    @Override // android.database.ErrorHandler
    void postHandleError(SQLiteDatabase db, boolean deleteDatabase) {
        if (!deleteDatabase) {
            return;
        }
        List<Pair<String, String>> attachedDbs = db.getAttachedDbs();
        db.setCheckpointOnClose(false);
        db.close();
        if (attachedDbs == null) {
            this.mDbDump.addDumpLog(
                    ErrorHandler.TAG, "!@ Back up corrupted DB File : " + db.getPath());
            DefaultDatabaseErrorHandler.backupDatabaseFile(db.getPath());
            return;
        }
        for (Pair<String, String> p : attachedDbs) {
            this.mDbDump.addDumpLog(ErrorHandler.TAG, "!@ Back up corrupted DB File : " + p.second);
            DefaultDatabaseErrorHandler.backupDatabaseFile(p.second);
        }
    }
}
