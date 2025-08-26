package android.widget;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.util.SparseIntArray;
import java.text.Collator;

/* loaded from: classes5.dex */
public class AlphabetIndexer extends DataSetObserver implements SectionIndexer {
    private SparseIntArray mAlphaMap;
    protected CharSequence mAlphabet;
    private String[] mAlphabetArray;
    private int mAlphabetLength;
    private Collator mCollator;
    protected int mColumnIndex;
    protected Cursor mDataCursor;

    public AlphabetIndexer(Cursor cursor, int i, CharSequence charSequence) {
        this.mDataCursor = cursor;
        this.mColumnIndex = i;
        this.mAlphabet = charSequence;
        int length = charSequence.length();
        this.mAlphabetLength = length;
        this.mAlphabetArray = new String[length];
        for (int i2 = 0; i2 < this.mAlphabetLength; i2++) {
            this.mAlphabetArray[i2] = Character.toString(this.mAlphabet.charAt(i2));
        }
        this.mAlphaMap = new SparseIntArray(this.mAlphabetLength);
        if (cursor != null) {
            cursor.registerDataSetObserver(this);
        }
        Collator collator = Collator.getInstance();
        this.mCollator = collator;
        collator.setStrength(0);
    }

    @Override // android.widget.SectionIndexer
    public Object[] getSections() {
        return this.mAlphabetArray;
    }

    public void setCursor(Cursor cursor) {
        Cursor cursor2 = this.mDataCursor;
        if (cursor2 != null) {
            cursor2.unregisterDataSetObserver(this);
        }
        this.mDataCursor = cursor;
        if (cursor != null) {
            cursor.registerDataSetObserver(this);
        }
        this.mAlphaMap.clear();
    }

    protected int compare(String str, String str2) {
        String strSubstring;
        if (str.length() == 0) {
            strSubstring = " ";
        } else {
            strSubstring = str.substring(0, 1);
        }
        return this.mCollator.compare(strSubstring, str2);
    }

    @Override // android.widget.SectionIndexer
    public int getPositionForSection(int i) {
        int i2;
        int i3;
        SparseIntArray sparseIntArray = this.mAlphaMap;
        Cursor cursor = this.mDataCursor;
        int iAbs = 0;
        if (cursor == null || this.mAlphabet == null || i <= 0) {
            return 0;
        }
        int i4 = this.mAlphabetLength;
        if (i >= i4) {
            i = i4 - 1;
        }
        int position = cursor.getPosition();
        int count = cursor.getCount();
        char cCharAt = this.mAlphabet.charAt(i);
        String string = Character.toString(cCharAt);
        int i5 = sparseIntArray.get(cCharAt, Integer.MIN_VALUE);
        if (Integer.MIN_VALUE == i5) {
            i2 = count;
        } else {
            if (i5 >= 0) {
                return i5;
            }
            i2 = -i5;
        }
        if (i > 0 && (i3 = sparseIntArray.get(this.mAlphabet.charAt(i - 1), Integer.MIN_VALUE)) != Integer.MIN_VALUE) {
            iAbs = Math.abs(i3);
        }
        int i6 = (i2 + iAbs) / 2;
        while (i6 < i2) {
            cursor.moveToPosition(i6);
            String string2 = cursor.getString(this.mColumnIndex);
            if (string2 != null) {
                int iCompare = compare(string2, string);
                if (iCompare == 0) {
                    if (iAbs == i6) {
                        break;
                    }
                } else {
                    if (iCompare < 0) {
                        int i7 = i6 + 1;
                        if (i7 >= count) {
                            break;
                        }
                        iAbs = i7;
                    }
                    i6 = (iAbs + i2) / 2;
                }
                i2 = i6;
                i6 = (iAbs + i2) / 2;
            } else {
                if (i6 == 0) {
                    break;
                }
                i6--;
            }
        }
        count = i6;
        sparseIntArray.put(cCharAt, count);
        cursor.moveToPosition(position);
        return count;
    }

    @Override // android.widget.SectionIndexer
    public int getSectionForPosition(int i) {
        int position = this.mDataCursor.getPosition();
        this.mDataCursor.moveToPosition(i);
        String string = this.mDataCursor.getString(this.mColumnIndex);
        this.mDataCursor.moveToPosition(position);
        for (int i2 = 0; i2 < this.mAlphabetLength; i2++) {
            if (compare(string, Character.toString(this.mAlphabet.charAt(i2))) == 0) {
                return i2;
            }
        }
        return 0;
    }

    @Override // android.database.DataSetObserver
    public void onChanged() {
        super.onChanged();
        this.mAlphaMap.clear();
    }

    @Override // android.database.DataSetObserver
    public void onInvalidated() {
        super.onInvalidated();
        this.mAlphaMap.clear();
    }
}
