package android.database;

import android.util.SparseArray;
import java.util.Map;

/* loaded from: classes.dex */
public class RedactingCursor extends CrossProcessCursorWrapper {
    private final SparseArray<Object> mRedactions;

    @Override // android.database.CrossProcessCursorWrapper, android.database.CrossProcessCursor
    public CursorWindow getWindow() {
        return null;
    }

    private RedactingCursor(Cursor cursor, SparseArray<Object> sparseArray) {
        super(cursor);
        this.mRedactions = sparseArray;
    }

    public static Cursor create(Cursor cursor, Map<String, Object> map) {
        SparseArray sparseArray = new SparseArray();
        String[] columnNames = cursor.getColumnNames();
        for (int i = 0; i < columnNames.length; i++) {
            if (map.containsKey(columnNames[i])) {
                sparseArray.put(i, map.get(columnNames[i]));
            }
        }
        return sparseArray.size() == 0 ? cursor : new RedactingCursor(cursor, sparseArray);
    }

    @Override // android.database.CrossProcessCursorWrapper, android.database.CrossProcessCursor
    public void fillWindow(int i, CursorWindow cursorWindow) {
        DatabaseUtils.cursorFillWindow(this, i, cursorWindow);
    }

    @Override // android.database.CursorWrapper
    public Cursor getWrappedCursor() {
        throw new UnsupportedOperationException("Returning underlying cursor risks leaking redacted data");
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public double getDouble(int i) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            return ((Double) this.mRedactions.valueAt(indexOfKey)).doubleValue();
        }
        return super.getDouble(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public float getFloat(int i) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            return ((Float) this.mRedactions.valueAt(indexOfKey)).floatValue();
        }
        return super.getFloat(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getInt(int i) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            return ((Integer) this.mRedactions.valueAt(indexOfKey)).intValue();
        }
        return super.getInt(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public long getLong(int i) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            return ((Long) this.mRedactions.valueAt(indexOfKey)).longValue();
        }
        return super.getLong(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public short getShort(int i) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            return ((Short) this.mRedactions.valueAt(indexOfKey)).shortValue();
        }
        return super.getShort(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public String getString(int i) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            return (String) this.mRedactions.valueAt(indexOfKey);
        }
        return super.getString(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            charArrayBuffer.data = ((String) this.mRedactions.valueAt(indexOfKey)).toCharArray();
            charArrayBuffer.sizeCopied = charArrayBuffer.data.length;
        } else {
            super.copyStringToBuffer(i, charArrayBuffer);
        }
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public byte[] getBlob(int i) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            return (byte[]) this.mRedactions.valueAt(indexOfKey);
        }
        return super.getBlob(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getType(int i) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            return DatabaseUtils.getTypeOfObject(this.mRedactions.valueAt(indexOfKey));
        }
        return super.getType(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public boolean isNull(int i) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            return this.mRedactions.valueAt(indexOfKey) == null;
        }
        return super.isNull(i);
    }
}
