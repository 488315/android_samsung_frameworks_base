package com.android.internal.database;

import android.database.AbstractCursor;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.util.Log;
import java.lang.reflect.Array;

/* loaded from: classes5.dex */
public class SortCursor extends AbstractCursor {
    private static final String TAG = "SortCursor";
    private int[][] mCurRowNumCache;
    private Cursor mCursor;
    private Cursor[] mCursors;
    private int[] mSortColumns;
    private final int ROWCACHESIZE = 64;
    private int[] mRowNumCache = new int[64];
    private int[] mCursorCache = new int[64];
    private int mLastCacheHit = -1;
    private DataSetObserver mObserver = new DataSetObserver() { // from class: com.android.internal.database.SortCursor.1
        @Override // android.database.DataSetObserver
        public void onChanged() {
            SortCursor.this.mPos = -1;
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            SortCursor.this.mPos = -1;
        }
    };

    public SortCursor(Cursor[] cursorArr, String str) {
        this.mCursors = cursorArr;
        int length = cursorArr.length;
        this.mSortColumns = new int[length];
        for (int i = 0; i < length; i++) {
            Cursor cursor = this.mCursors[i];
            if (cursor != null) {
                cursor.registerDataSetObserver(this.mObserver);
                this.mCursors[i].moveToFirst();
                this.mSortColumns[i] = this.mCursors[i].getColumnIndexOrThrow(str);
            }
        }
        this.mCursor = null;
        String str2 = "";
        for (int i2 = 0; i2 < length; i2++) {
            Cursor cursor2 = this.mCursors[i2];
            if (cursor2 != null && !cursor2.isAfterLast()) {
                String string = this.mCursors[i2].getString(this.mSortColumns[i2]);
                if (this.mCursor == null || (string != null && string.compareToIgnoreCase(str2) < 0)) {
                    this.mCursor = this.mCursors[i2];
                    str2 = string;
                }
            }
        }
        for (int length2 = this.mRowNumCache.length - 1; length2 >= 0; length2--) {
            this.mRowNumCache[length2] = -2;
        }
        this.mCurRowNumCache = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 64, length);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getCount() {
        int length = this.mCursors.length;
        int count = 0;
        for (int i = 0; i < length; i++) {
            Cursor cursor = this.mCursors[i];
            if (cursor != null) {
                count += cursor.getCount();
            }
        }
        return count;
    }

    @Override // android.database.AbstractCursor, android.database.CrossProcessCursor
    public boolean onMove(int i, int i2) {
        if (i == i2) {
            return true;
        }
        int i3 = i2 % 64;
        if (this.mRowNumCache[i3] == i2) {
            int i4 = this.mCursorCache[i3];
            Cursor cursor = this.mCursors[i4];
            this.mCursor = cursor;
            if (cursor == null) {
                Log.w(TAG, "onMove: cache results in a null cursor.");
                return false;
            }
            cursor.moveToPosition(this.mCurRowNumCache[i3][i4]);
            this.mLastCacheHit = i3;
            return true;
        }
        this.mCursor = null;
        int length = this.mCursors.length;
        if (this.mLastCacheHit >= 0) {
            for (int i5 = 0; i5 < length; i5++) {
                Cursor cursor2 = this.mCursors[i5];
                if (cursor2 != null) {
                    cursor2.moveToPosition(this.mCurRowNumCache[this.mLastCacheHit][i5]);
                }
            }
        }
        if (i2 < i || i == -1) {
            for (int i6 = 0; i6 < length; i6++) {
                Cursor cursor3 = this.mCursors[i6];
                if (cursor3 != null) {
                    cursor3.moveToFirst();
                }
            }
            i = 0;
        }
        if (i < 0) {
            i = 0;
        }
        int i7 = -1;
        while (i <= i2) {
            String str = "";
            i7 = -1;
            for (int i8 = 0; i8 < length; i8++) {
                Cursor cursor4 = this.mCursors[i8];
                if (cursor4 != null && !cursor4.isAfterLast()) {
                    String string = this.mCursors[i8].getString(this.mSortColumns[i8]);
                    if (i7 < 0 || (string != null && string.compareToIgnoreCase(str) < 0)) {
                        i7 = i8;
                        str = string;
                    }
                }
            }
            if (i == i2) {
                break;
            }
            Cursor cursor5 = this.mCursors[i7];
            if (cursor5 != null) {
                cursor5.moveToNext();
            }
            i++;
        }
        this.mCursor = this.mCursors[i7];
        this.mRowNumCache[i3] = i2;
        this.mCursorCache[i3] = i7;
        for (int i9 = 0; i9 < length; i9++) {
            Cursor cursor6 = this.mCursors[i9];
            if (cursor6 != null) {
                this.mCurRowNumCache[i3][i9] = cursor6.getPosition();
            }
        }
        this.mLastCacheHit = -1;
        return true;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public String getString(int i) {
        return this.mCursor.getString(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public short getShort(int i) {
        return this.mCursor.getShort(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getInt(int i) {
        return this.mCursor.getInt(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public long getLong(int i) {
        return this.mCursor.getLong(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public float getFloat(int i) {
        return this.mCursor.getFloat(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public double getDouble(int i) {
        return this.mCursor.getDouble(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getType(int i) {
        return this.mCursor.getType(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public boolean isNull(int i) {
        return this.mCursor.isNull(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public byte[] getBlob(int i) {
        return this.mCursor.getBlob(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public String[] getColumnNames() {
        Cursor cursor = this.mCursor;
        if (cursor != null) {
            return cursor.getColumnNames();
        }
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            Cursor cursor2 = this.mCursors[i];
            if (cursor2 != null) {
                return cursor2.getColumnNames();
            }
        }
        throw new IllegalStateException("No cursor that can return names");
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void deactivate() {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            Cursor cursor = this.mCursors[i];
            if (cursor != null) {
                cursor.deactivate();
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            Cursor cursor = this.mCursors[i];
            if (cursor != null) {
                cursor.close();
                this.mCursors[i] = null;
            }
        }
        super.close();
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            Cursor cursor = this.mCursors[i];
            if (cursor != null) {
                cursor.registerDataSetObserver(dataSetObserver);
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            Cursor cursor = this.mCursors[i];
            if (cursor != null) {
                cursor.unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public boolean requery() {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            Cursor cursor = this.mCursors[i];
            if (cursor != null && !cursor.requery()) {
                return false;
            }
        }
        return true;
    }
}
