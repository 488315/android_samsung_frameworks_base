package android.database;

import java.util.Iterator;

/* loaded from: classes.dex */
public final class CursorJoiner implements Iterator<Result>, Iterable<Result> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int[] mColumnsLeft;
    private int[] mColumnsRight;
    private Result mCompareResult;
    private boolean mCompareResultIsValid;
    private Cursor mCursorLeft;
    private Cursor mCursorRight;
    private String[] mValues;

    public enum Result {
        RIGHT,
        LEFT,
        BOTH
    }

    @Override // java.lang.Iterable
    public Iterator<Result> iterator() {
        return this;
    }

    public CursorJoiner(Cursor cursor, String[] strArr, Cursor cursor2, String[] strArr2) {
        if (strArr.length != strArr2.length) {
            throw new IllegalArgumentException("you must have the same number of columns on the left and right, " + strArr.length + " != " + strArr2.length);
        }
        this.mCursorLeft = cursor;
        this.mCursorRight = cursor2;
        cursor.moveToFirst();
        this.mCursorRight.moveToFirst();
        this.mCompareResultIsValid = false;
        this.mColumnsLeft = buildColumnIndiciesArray(cursor, strArr);
        this.mColumnsRight = buildColumnIndiciesArray(cursor2, strArr2);
        this.mValues = new String[this.mColumnsLeft.length * 2];
    }

    private int[] buildColumnIndiciesArray(Cursor cursor, String[] strArr) {
        int[] iArr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            iArr[i] = cursor.getColumnIndexOrThrow(strArr[i]);
        }
        return iArr;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (!this.mCompareResultIsValid) {
            return (this.mCursorLeft.isAfterLast() && this.mCursorRight.isAfterLast()) ? false : true;
        }
        int ordinal = this.mCompareResult.ordinal();
        if (ordinal == 0) {
            return (this.mCursorLeft.isAfterLast() && this.mCursorRight.isLast()) ? false : true;
        }
        if (ordinal == 1) {
            return (this.mCursorLeft.isLast() && this.mCursorRight.isAfterLast()) ? false : true;
        }
        if (ordinal == 2) {
            return (this.mCursorLeft.isLast() && this.mCursorRight.isLast()) ? false : true;
        }
        throw new IllegalStateException("bad value for mCompareResult, " + this.mCompareResult);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Iterator
    public Result next() {
        if (!hasNext()) {
            throw new IllegalStateException("you must only call next() when hasNext() is true");
        }
        incrementCursors();
        boolean isAfterLast = this.mCursorLeft.isAfterLast();
        boolean isAfterLast2 = this.mCursorRight.isAfterLast();
        if (!isAfterLast && !isAfterLast2) {
            populateValues(this.mValues, this.mCursorLeft, this.mColumnsLeft, 0);
            populateValues(this.mValues, this.mCursorRight, this.mColumnsRight, 1);
            int compareStrings = compareStrings(this.mValues);
            if (compareStrings == -1) {
                this.mCompareResult = Result.LEFT;
            } else if (compareStrings == 0) {
                this.mCompareResult = Result.BOTH;
            } else if (compareStrings == 1) {
                this.mCompareResult = Result.RIGHT;
            }
        } else if (!isAfterLast) {
            this.mCompareResult = Result.LEFT;
        } else {
            this.mCompareResult = Result.RIGHT;
        }
        this.mCompareResultIsValid = true;
        return this.mCompareResult;
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("not implemented");
    }

    private static void populateValues(String[] strArr, Cursor cursor, int[] iArr, int i) {
        for (int i2 = 0; i2 < iArr.length; i2++) {
            strArr[(i2 * 2) + i] = cursor.getString(iArr[i2]);
        }
    }

    private void incrementCursors() {
        if (this.mCompareResultIsValid) {
            int ordinal = this.mCompareResult.ordinal();
            if (ordinal == 0) {
                this.mCursorRight.moveToNext();
            } else if (ordinal == 1) {
                this.mCursorLeft.moveToNext();
            } else if (ordinal == 2) {
                this.mCursorLeft.moveToNext();
                this.mCursorRight.moveToNext();
            }
            this.mCompareResultIsValid = false;
        }
    }

    private static int compareStrings(String... strArr) {
        if (strArr.length % 2 != 0) {
            throw new IllegalArgumentException("you must specify an even number of values");
        }
        for (int i = 0; i < strArr.length; i += 2) {
            String str = strArr[i];
            if (str == null) {
                if (strArr[i + 1] != null) {
                    return -1;
                }
            } else {
                String str2 = strArr[i + 1];
                if (str2 == null) {
                    return 1;
                }
                int compareTo = str.compareTo(str2);
                if (compareTo != 0) {
                    return compareTo < 0 ? -1 : 1;
                }
            }
        }
        return 0;
    }
}
