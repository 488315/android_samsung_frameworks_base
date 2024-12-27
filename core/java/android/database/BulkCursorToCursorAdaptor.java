package android.database;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

/* loaded from: classes.dex */
public final class BulkCursorToCursorAdaptor extends AbstractWindowedCursor {
    private static final String TAG = "BulkCursor";
    private IBulkCursor mBulkCursor;
    private String[] mColumns;
    private int mCount;
    private AbstractCursor.SelfContentObserver mObserverBridge =
            new AbstractCursor.SelfContentObserver(this);
    private boolean mWantsAllOnMoveCalls;

    public void initialize(BulkCursorDescriptor d) {
        this.mBulkCursor = d.cursor;
        this.mColumns = d.columnNames;
        this.mWantsAllOnMoveCalls = d.wantsAllOnMoveCalls;
        this.mCount = d.count;
        if (d.window != null) {
            setWindow(d.window);
        }
    }

    public IContentObserver getObserver() {
        return this.mObserverBridge.getContentObserver();
    }

    private void throwIfCursorIsClosed() {
        if (this.mBulkCursor == null) {
            throw new StaleDataException("Attempted to access a cursor after it has been closed.");
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getCount() {
        throwIfCursorIsClosed();
        return this.mCount;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0038 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0039 A[RETURN] */
    @Override // android.database.AbstractCursor, android.database.CrossProcessCursor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onMove(int r5, int r6) {
        /*
            r4 = this;
            r4.throwIfCursorIsClosed()
            r0 = 0
            android.database.CursorWindow r1 = r4.mWindow     // Catch: android.os.RemoteException -> L3b
            if (r1 == 0) goto L2a
            android.database.CursorWindow r1 = r4.mWindow     // Catch: android.os.RemoteException -> L3b
            int r1 = r1.getStartPosition()     // Catch: android.os.RemoteException -> L3b
            if (r6 < r1) goto L2a
            android.database.CursorWindow r1 = r4.mWindow     // Catch: android.os.RemoteException -> L3b
            int r1 = r1.getStartPosition()     // Catch: android.os.RemoteException -> L3b
            android.database.CursorWindow r2 = r4.mWindow     // Catch: android.os.RemoteException -> L3b
            int r2 = r2.getNumRows()     // Catch: android.os.RemoteException -> L3b
            int r1 = r1 + r2
            if (r6 < r1) goto L20
            goto L2a
        L20:
            boolean r1 = r4.mWantsAllOnMoveCalls     // Catch: android.os.RemoteException -> L3b
            if (r1 == 0) goto L33
            android.database.IBulkCursor r1 = r4.mBulkCursor     // Catch: android.os.RemoteException -> L3b
            r1.onMove(r6)     // Catch: android.os.RemoteException -> L3b
            goto L33
        L2a:
            android.database.IBulkCursor r1 = r4.mBulkCursor     // Catch: android.os.RemoteException -> L3b
            android.database.CursorWindow r1 = r1.getWindow(r6)     // Catch: android.os.RemoteException -> L3b
            r4.setWindow(r1)     // Catch: android.os.RemoteException -> L3b
        L33:
            android.database.CursorWindow r1 = r4.mWindow
            if (r1 != 0) goto L39
            return r0
        L39:
            r0 = 1
            return r0
        L3b:
            r1 = move-exception
            java.lang.String r2 = "BulkCursor"
            java.lang.String r3 = "Unable to get window because the remote process is dead"
            android.util.Log.e(r2, r3)
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.database.BulkCursorToCursorAdaptor.onMove(int,"
                        + " int):boolean");
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void deactivate() {
        super.deactivate();
        if (this.mBulkCursor != null) {
            try {
                this.mBulkCursor.deactivate();
            } catch (RemoteException e) {
                Log.w(TAG, "Remote process exception when deactivating");
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor, java.io.Closeable,
    // java.lang.AutoCloseable
    public void close() {
        super.close();
        if (this.mBulkCursor != null) {
            try {
                try {
                    this.mBulkCursor.close();
                } catch (RemoteException e) {
                    Log.w(TAG, "Remote process exception when closing");
                }
            } finally {
                this.mBulkCursor = null;
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public boolean requery() {
        throwIfCursorIsClosed();
        try {
            this.mCount = this.mBulkCursor.requery(getObserver());
            if (this.mCount != -1) {
                this.mPos = -1;
                closeWindow();
                super.requery();
                return true;
            }
            deactivate();
            return false;
        } catch (Exception ex) {
            Log.e(TAG, "Unable to requery because the remote process exception " + ex.getMessage());
            deactivate();
            return false;
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public String[] getColumnNames() {
        throwIfCursorIsClosed();
        return this.mColumns;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public Bundle getExtras() {
        throwIfCursorIsClosed();
        try {
            return this.mBulkCursor.getExtras();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public Bundle respond(Bundle extras) {
        throwIfCursorIsClosed();
        try {
            return this.mBulkCursor.respond(extras);
        } catch (RemoteException e) {
            Log.w(TAG, "respond() threw RemoteException, returning an empty bundle.", e);
            return Bundle.EMPTY;
        }
    }
}
