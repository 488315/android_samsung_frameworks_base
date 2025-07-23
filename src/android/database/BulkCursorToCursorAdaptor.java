package android.database;

import android.database.AbstractCursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

/* loaded from: classes.dex */
public final class BulkCursorToCursorAdaptor extends AbstractWindowedCursor {
    private static final String TAG = "BulkCursor";
    private IBulkCursor mBulkCursor;
    private String[] mColumns;
    private int mCount;
    private AbstractCursor.SelfContentObserver mObserverBridge = new AbstractCursor.SelfContentObserver(this);
    private boolean mWantsAllOnMoveCalls;

    public void initialize(BulkCursorDescriptor bulkCursorDescriptor) {
        this.mBulkCursor = bulkCursorDescriptor.cursor;
        this.mColumns = bulkCursorDescriptor.columnNames;
        this.mWantsAllOnMoveCalls = bulkCursorDescriptor.wantsAllOnMoveCalls;
        this.mCount = bulkCursorDescriptor.count;
        if (bulkCursorDescriptor.window != null) {
            setWindow(bulkCursorDescriptor.window);
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x0037 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0038 A[RETURN] */
    @Override // android.database.AbstractCursor, android.database.CrossProcessCursor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onMove(int r3, int r4) {
        /*
            r2 = this;
            r2.throwIfCursorIsClosed()
            r3 = 0
            android.database.CursorWindow r0 = r2.mWindow     // Catch: android.os.RemoteException -> L3a
            if (r0 == 0) goto L2a
            android.database.CursorWindow r0 = r2.mWindow     // Catch: android.os.RemoteException -> L3a
            int r0 = r0.getStartPosition()     // Catch: android.os.RemoteException -> L3a
            if (r4 < r0) goto L2a
            android.database.CursorWindow r0 = r2.mWindow     // Catch: android.os.RemoteException -> L3a
            int r0 = r0.getStartPosition()     // Catch: android.os.RemoteException -> L3a
            android.database.CursorWindow r1 = r2.mWindow     // Catch: android.os.RemoteException -> L3a
            int r1 = r1.getNumRows()     // Catch: android.os.RemoteException -> L3a
            int r0 = r0 + r1
            if (r4 < r0) goto L20
            goto L2a
        L20:
            boolean r0 = r2.mWantsAllOnMoveCalls     // Catch: android.os.RemoteException -> L3a
            if (r0 == 0) goto L33
            android.database.IBulkCursor r0 = r2.mBulkCursor     // Catch: android.os.RemoteException -> L3a
            r0.onMove(r4)     // Catch: android.os.RemoteException -> L3a
            goto L33
        L2a:
            android.database.IBulkCursor r0 = r2.mBulkCursor     // Catch: android.os.RemoteException -> L3a
            android.database.CursorWindow r4 = r0.getWindow(r4)     // Catch: android.os.RemoteException -> L3a
            r2.setWindow(r4)     // Catch: android.os.RemoteException -> L3a
        L33:
            android.database.CursorWindow r2 = r2.mWindow
            if (r2 != 0) goto L38
            return r3
        L38:
            r2 = 1
            return r2
        L3a:
            java.lang.String r2 = "BulkCursor"
            java.lang.String r4 = "Unable to get window because the remote process is dead"
            android.util.Log.e(r2, r4)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.database.BulkCursorToCursorAdaptor.onMove(int, int):boolean");
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void deactivate() {
        super.deactivate();
        IBulkCursor iBulkCursor = this.mBulkCursor;
        if (iBulkCursor != null) {
            try {
                iBulkCursor.deactivate();
            } catch (RemoteException unused) {
                Log.w(TAG, "Remote process exception when deactivating");
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        super.close();
        IBulkCursor iBulkCursor = this.mBulkCursor;
        if (iBulkCursor != null) {
            try {
                iBulkCursor.close();
            } catch (RemoteException unused) {
                Log.w(TAG, "Remote process exception when closing");
            } finally {
                this.mBulkCursor = null;
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public boolean requery() {
        throwIfCursorIsClosed();
        try {
            int requery = this.mBulkCursor.requery(getObserver());
            this.mCount = requery;
            if (requery != -1) {
                this.mPos = -1;
                closeWindow();
                super.requery();
                return true;
            }
            deactivate();
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Unable to requery because the remote process exception " + e.getMessage());
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
    public Bundle respond(Bundle bundle) {
        throwIfCursorIsClosed();
        try {
            return this.mBulkCursor.respond(bundle);
        } catch (RemoteException e) {
            Log.w(TAG, "respond() threw RemoteException, returning an empty bundle.", e);
            return Bundle.EMPTY;
        }
    }
}
