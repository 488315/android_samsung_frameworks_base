package android.database;

/* loaded from: classes.dex */
public class CrossProcessCursorWrapper extends CursorWrapper implements CrossProcessCursor {
    public CrossProcessCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    @Override // android.database.CrossProcessCursor
    public void fillWindow(int i, CursorWindow cursorWindow) {
        if (this.mCursor instanceof CrossProcessCursor) {
            ((CrossProcessCursor) this.mCursor).fillWindow(i, cursorWindow);
        } else {
            DatabaseUtils.cursorFillWindow(this.mCursor, i, cursorWindow);
        }
    }

    @Override // android.database.CrossProcessCursor
    public CursorWindow getWindow() {
        if (this.mCursor instanceof CrossProcessCursor) {
            return ((CrossProcessCursor) this.mCursor).getWindow();
        }
        return null;
    }

    @Override // android.database.CrossProcessCursor
    public boolean onMove(int i, int i2) {
        if (this.mCursor instanceof CrossProcessCursor) {
            return ((CrossProcessCursor) this.mCursor).onMove(i, i2);
        }
        return true;
    }
}
