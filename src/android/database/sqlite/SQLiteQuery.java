package android.database.sqlite;

import android.database.CursorWindow;
import android.os.CancellationSignal;
import android.util.Log;

/* loaded from: classes.dex */
public final class SQLiteQuery extends SQLiteProgram {
    private static final String TAG = "SQLiteQuery";
    private final CancellationSignal mCancellationSignal;

    SQLiteQuery(SQLiteDatabase sQLiteDatabase, String str, CancellationSignal cancellationSignal) {
        super(sQLiteDatabase, str, null, cancellationSignal);
        this.mCancellationSignal = cancellationSignal;
    }

    int fillWindow(CursorWindow cursorWindow, int i, int i2, boolean z) {
        acquireReference();
        try {
            try {
                cursorWindow.acquireReference();
            } catch (Throwable th) {
                th = th;
            }
            try {
                try {
                    int iExecuteForCursorWindow = getSession().executeForCursorWindow(getSql(), getBindArgs(), cursorWindow, i, i2, z, getConnectionFlags(), this.mCancellationSignal);
                    if (cursorWindow.getFilledRows() + i == cursorWindow.getTotalRows()) {
                        getDatabase().tryWalBackgroundCheckpoint();
                    }
                    cursorWindow.releaseReference();
                    return iExecuteForCursorWindow;
                } catch (SQLiteDatabaseCorruptException e) {
                    e = e;
                    SQLiteDatabaseCorruptException sQLiteDatabaseCorruptException = e;
                    onCorruption(sQLiteDatabaseCorruptException.getCorruptCode());
                    throw sQLiteDatabaseCorruptException;
                } catch (SQLiteException e2) {
                    e = e2;
                    SQLiteException sQLiteException = e;
                    Log.e(TAG, "exception: " + sQLiteException.getMessage() + "; query: " + getSql());
                    throw sQLiteException;
                }
            } catch (SQLiteDatabaseCorruptException e3) {
                e = e3;
            } catch (SQLiteException e4) {
                e = e4;
            } catch (Throwable th2) {
                th = th2;
                Throwable th3 = th;
                cursorWindow.releaseReference();
                throw th3;
            }
        } finally {
            releaseReference();
        }
    }

    public String toString() {
        return "SQLiteQuery: " + getSql();
    }
}
