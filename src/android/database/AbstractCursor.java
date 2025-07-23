package android.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import dalvik.system.CloseGuard;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes.dex */
public abstract class AbstractCursor implements CrossProcessCursor {
    private static final String TAG = "Cursor";
    private final CloseGuard mCloseGuard;

    @Deprecated
    protected boolean mClosed;

    @Deprecated
    protected ContentResolver mContentResolver;
    protected Long mCurrentRowID;
    private Uri mNotifyUri;
    private List<Uri> mNotifyUris;
    protected int mRowIdColumnIndex;
    private ContentObserver mSelfObserver;
    private boolean mSelfObserverRegistered;
    protected HashMap<Long, Map<String, Object>> mUpdatedRows;
    private final Object mSelfObserverLock = new Object();
    private final DataSetObservable mDataSetObservable = new DataSetObservable();
    private final ContentObservable mContentObservable = new ContentObservable();
    private Bundle mExtras = Bundle.EMPTY;

    @Deprecated
    protected int mPos = -1;

    private CloseGuard initCloseGuard$ravenwood() {
        return null;
    }

    @Override // android.database.Cursor
    public abstract String[] getColumnNames();

    @Override // android.database.Cursor
    public abstract int getCount();

    @Override // android.database.Cursor
    public abstract double getDouble(int i);

    @Override // android.database.Cursor
    public abstract float getFloat(int i);

    @Override // android.database.Cursor
    public abstract int getInt(int i);

    @Override // android.database.Cursor
    public abstract long getLong(int i);

    @Override // android.database.Cursor
    public abstract short getShort(int i);

    @Override // android.database.Cursor
    public abstract String getString(int i);

    @Override // android.database.Cursor
    public int getType(int i) {
        return 3;
    }

    @Deprecated
    protected Object getUpdatedField(int i) {
        return null;
    }

    @Override // android.database.Cursor
    public boolean getWantsAllOnMoveCalls() {
        return false;
    }

    @Override // android.database.CrossProcessCursor
    public CursorWindow getWindow() {
        return null;
    }

    @Deprecated
    protected boolean isFieldUpdated(int i) {
        return false;
    }

    @Override // android.database.Cursor
    public abstract boolean isNull(int i);

    @Override // android.database.CrossProcessCursor
    public boolean onMove(int i, int i2) {
        return true;
    }

    @Override // android.database.Cursor
    public byte[] getBlob(int i) {
        throw new UnsupportedOperationException("getBlob is not supported");
    }

    @Override // android.database.Cursor
    public int getColumnCount() {
        return getColumnNames().length;
    }

    @Override // android.database.Cursor
    public void deactivate() {
        onDeactivateOrClose();
    }

    protected void onDeactivateOrClose() {
        ContentObserver contentObserver = this.mSelfObserver;
        if (contentObserver != null) {
            this.mContentResolver.unregisterContentObserver(contentObserver);
            this.mSelfObserverRegistered = false;
        }
        this.mDataSetObservable.notifyInvalidated();
    }

    @Override // android.database.Cursor
    public boolean requery() {
        if (this.mSelfObserver != null && !this.mSelfObserverRegistered) {
            int size = this.mNotifyUris.size();
            for (int i = 0; i < size; i++) {
                this.mContentResolver.registerContentObserver(this.mNotifyUris.get(i), true, this.mSelfObserver);
            }
            this.mSelfObserverRegistered = true;
        }
        this.mDataSetObservable.notifyChanged();
        return true;
    }

    @Override // android.database.Cursor
    public boolean isClosed() {
        return this.mClosed;
    }

    @Override // android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.mClosed = true;
        this.mContentObservable.unregisterAll();
        onDeactivateOrClose();
        CloseGuard closeGuard = this.mCloseGuard;
        if (closeGuard != null) {
            closeGuard.close();
        }
    }

    @Override // android.database.Cursor
    public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {
        String string = getString(i);
        if (string != null) {
            char[] cArr = charArrayBuffer.data;
            if (cArr == null || cArr.length < string.length()) {
                charArrayBuffer.data = string.toCharArray();
            } else {
                string.getChars(0, string.length(), cArr, 0);
            }
            charArrayBuffer.sizeCopied = string.length();
            return;
        }
        charArrayBuffer.sizeCopied = 0;
    }

    public AbstractCursor() {
        CloseGuard initCloseGuard = initCloseGuard();
        this.mCloseGuard = initCloseGuard;
        if (initCloseGuard != null) {
            initCloseGuard.open("AbstractCursor.close");
        }
    }

    private CloseGuard initCloseGuard() {
        return CloseGuard.get();
    }

    @Override // android.database.Cursor
    public final int getPosition() {
        return this.mPos;
    }

    @Override // android.database.Cursor
    public final boolean moveToPosition(int i) {
        int count = getCount();
        if (i >= count) {
            this.mPos = count;
            return false;
        }
        if (i < 0) {
            this.mPos = -1;
            return false;
        }
        int i2 = this.mPos;
        if (i == i2) {
            return true;
        }
        boolean onMove = onMove(i2, i);
        if (!onMove) {
            this.mPos = -1;
            return onMove;
        }
        this.mPos = i;
        return onMove;
    }

    @Override // android.database.CrossProcessCursor
    public void fillWindow(int i, CursorWindow cursorWindow) {
        DatabaseUtils.cursorFillWindow(this, i, cursorWindow);
    }

    @Override // android.database.Cursor
    public final boolean move(int i) {
        return moveToPosition(this.mPos + i);
    }

    @Override // android.database.Cursor
    public final boolean moveToFirst() {
        return moveToPosition(0);
    }

    @Override // android.database.Cursor
    public final boolean moveToLast() {
        return moveToPosition(getCount() - 1);
    }

    @Override // android.database.Cursor
    public final boolean moveToNext() {
        return moveToPosition(this.mPos + 1);
    }

    @Override // android.database.Cursor
    public final boolean moveToPrevious() {
        return moveToPosition(this.mPos - 1);
    }

    @Override // android.database.Cursor
    public final boolean isFirst() {
        return this.mPos == 0 && getCount() != 0;
    }

    @Override // android.database.Cursor
    public final boolean isLast() {
        int count = getCount();
        return this.mPos == count + (-1) && count != 0;
    }

    @Override // android.database.Cursor
    public final boolean isBeforeFirst() {
        return getCount() == 0 || this.mPos == -1;
    }

    @Override // android.database.Cursor
    public final boolean isAfterLast() {
        return getCount() == 0 || this.mPos == getCount();
    }

    @Override // android.database.Cursor
    public int getColumnIndex(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf != -1) {
            Log.e(TAG, "requesting column name with table name -- " + str, new Exception());
            str = str.substring(lastIndexOf + 1);
        }
        String[] columnNames = getColumnNames();
        int length = columnNames.length;
        for (int i = 0; i < length; i++) {
            if (columnNames[i].equalsIgnoreCase(str)) {
                return i;
            }
        }
        return -1;
    }

    @Override // android.database.Cursor
    public int getColumnIndexOrThrow(String str) {
        String str2;
        int columnIndex = getColumnIndex(str);
        if (columnIndex >= 0) {
            return columnIndex;
        }
        try {
            str2 = Arrays.toString(getColumnNames());
        } catch (Exception e) {
            Log.d(TAG, "Cannot collect column names for debug purposes", e);
            str2 = "";
        }
        throw new IllegalArgumentException("column '" + str + "' does not exist. Available columns: " + str2);
    }

    @Override // android.database.Cursor
    public String getColumnName(int i) {
        return getColumnNames()[i];
    }

    @Override // android.database.Cursor
    public void registerContentObserver(ContentObserver contentObserver) {
        this.mContentObservable.registerObserver(contentObserver);
    }

    @Override // android.database.Cursor
    public void unregisterContentObserver(ContentObserver contentObserver) {
        if (this.mClosed) {
            return;
        }
        this.mContentObservable.unregisterObserver(contentObserver);
    }

    @Override // android.database.Cursor
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.mDataSetObservable.registerObserver(dataSetObserver);
    }

    @Override // android.database.Cursor
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this.mDataSetObservable.unregisterObserver(dataSetObserver);
    }

    protected void onChange(boolean z) {
        synchronized (this.mSelfObserverLock) {
            this.mContentObservable.dispatchChange(z, null);
            List<Uri> list = this.mNotifyUris;
            if (list != null && z) {
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    this.mContentResolver.notifyChange(this.mNotifyUris.get(i), this.mSelfObserver);
                }
            }
        }
    }

    @Override // android.database.Cursor
    public void setNotificationUri(ContentResolver contentResolver, Uri uri) {
        setNotificationUris(contentResolver, Arrays.asList(uri));
    }

    @Override // android.database.Cursor
    public void setNotificationUris(ContentResolver contentResolver, List<Uri> list) {
        Objects.requireNonNull(contentResolver);
        Objects.requireNonNull(list);
        setNotificationUris(contentResolver, list, contentResolver.getUserId(), true);
    }

    public void setNotificationUris(ContentResolver contentResolver, List<Uri> list, int i, boolean z) {
        synchronized (this.mSelfObserverLock) {
            this.mNotifyUris = list;
            this.mNotifyUri = list.get(0);
            this.mContentResolver = contentResolver;
            ContentObserver contentObserver = this.mSelfObserver;
            if (contentObserver != null) {
                contentResolver.unregisterContentObserver(contentObserver);
                this.mSelfObserverRegistered = false;
            }
            if (z) {
                this.mSelfObserver = new SelfContentObserver(this);
                int size = this.mNotifyUris.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mContentResolver.registerContentObserver(this.mNotifyUris.get(i2), true, this.mSelfObserver, i);
                }
                this.mSelfObserverRegistered = true;
            }
        }
    }

    @Override // android.database.Cursor
    public Uri getNotificationUri() {
        Uri uri;
        synchronized (this.mSelfObserverLock) {
            uri = this.mNotifyUri;
        }
        return uri;
    }

    @Override // android.database.Cursor
    public List<Uri> getNotificationUris() {
        List<Uri> list;
        synchronized (this.mSelfObserverLock) {
            list = this.mNotifyUris;
        }
        return list;
    }

    @Override // android.database.Cursor
    public void setExtras(Bundle bundle) {
        if (bundle == null) {
            bundle = Bundle.EMPTY;
        }
        this.mExtras = bundle;
    }

    @Override // android.database.Cursor
    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.database.Cursor
    public Bundle respond(Bundle bundle) {
        return Bundle.EMPTY;
    }

    protected void checkPosition() {
        if (-1 == this.mPos || getCount() == this.mPos) {
            throw new CursorIndexOutOfBoundsException(this.mPos, getCount());
        }
    }

    protected void finalize() {
        ContentObserver contentObserver = this.mSelfObserver;
        if (contentObserver != null && this.mSelfObserverRegistered) {
            this.mContentResolver.unregisterContentObserver(contentObserver);
        }
        try {
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            if (this.mClosed) {
                return;
            }
            close();
        } catch (Exception unused) {
        }
    }

    protected static class SelfContentObserver extends ContentObserver {
        WeakReference<AbstractCursor> mCursor;

        @Override // android.database.ContentObserver
        public boolean deliverSelfNotifications() {
            return false;
        }

        public SelfContentObserver(AbstractCursor abstractCursor) {
            super(null);
            this.mCursor = new WeakReference<>(abstractCursor);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            AbstractCursor abstractCursor = this.mCursor.get();
            if (abstractCursor != null) {
                abstractCursor.onChange(false);
            }
        }
    }
}
