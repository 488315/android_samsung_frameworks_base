package com.samsung.android.widget;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import java.text.Collator;
import java.util.HashMap;

/* loaded from: classes6.dex */
public abstract class SemAbstractIndexer extends DataSetObserver {
    private static final char DIGIT_CHAR = '#';
    public static final char FAVORITE_CHAR = 9733;
    private static final String GROUP_CHAR = "👥︎";
    private static final char GROUP_CHECKER = 55357;
    protected static final String INDEXSCROLL_INDEX_COUNTS = "indexscroll_index_counts";
    protected static final String INDEXSCROLL_INDEX_TITLES = "indexscroll_index_titles";
    private static final char SYMBOL_BASE_CHAR = '!';
    private static final char SYMBOL_CHAR = '&';
    private static final boolean debug = false;
    protected SparseIntArray mAlphaMap;
    protected CharSequence mAlphabet;
    protected String[] mAlphabetArray;
    protected int mAlphabetLength;
    private Bundle mBundle;
    private int[] mCachingValue;
    protected Collator mCollator;
    private int mFavoriteItemCount;
    protected String[] mLangAlphabetArray;
    private int mProfileItemCount;
    private boolean mUseFavoriteIndex;
    private final String TAG = "SemAbstractIndexer";
    private int mGroupItemCount = 0;
    private int mDigitItemCount = 0;
    private boolean mUseGroupIndex = false;
    private boolean mUseDigitIndex = false;
    private final DataSetObservable mDataSetObservable = new DataSetObservable();
    private boolean mRegisteredDataSetObservable = false;
    private HashMap<Integer, Integer> mLangIndexMap = new HashMap<>();
    private int mCurrentLang = -1;

    protected abstract Bundle getBundle();

    protected abstract String getItemAt(int i);

    protected abstract int getItemCount();

    protected abstract boolean isDataToBeIndexedAvailable();

    protected void onBeginTransaction() {
    }

    protected void onEndTransaction() {
    }

    public SemAbstractIndexer(CharSequence charSequence) {
        this.mProfileItemCount = 0;
        this.mFavoriteItemCount = 0;
        this.mUseFavoriteIndex = false;
        this.mUseFavoriteIndex = false;
        this.mProfileItemCount = 0;
        this.mFavoriteItemCount = 0;
        initIndexer(charSequence);
    }

    public SemAbstractIndexer(String[] strArr, int i) {
        this.mProfileItemCount = 0;
        this.mFavoriteItemCount = 0;
        this.mUseFavoriteIndex = false;
        this.mUseFavoriteIndex = false;
        this.mProfileItemCount = 0;
        this.mFavoriteItemCount = 0;
        this.mLangAlphabetArray = strArr;
        setMultiLangIndexer(i);
    }

    public SemAbstractIndexer(CharSequence charSequence, int i, int i2) {
        this.mProfileItemCount = 0;
        this.mFavoriteItemCount = 0;
        this.mUseFavoriteIndex = false;
        this.mUseFavoriteIndex = true;
        this.mProfileItemCount = i;
        this.mFavoriteItemCount = i2;
        initIndexer(charSequence);
    }

    public SemAbstractIndexer(String[] strArr, int i, int i2, int i3) {
        this.mProfileItemCount = 0;
        this.mFavoriteItemCount = 0;
        this.mUseFavoriteIndex = false;
        this.mUseFavoriteIndex = true;
        this.mProfileItemCount = i2;
        this.mFavoriteItemCount = i3;
        this.mLangAlphabetArray = strArr;
        setMultiLangIndexer(i);
    }

    public String[] getLangAlphabetArray() {
        return this.mLangAlphabetArray;
    }

    public int getCachingValue(int i) {
        if (i < 0 || i >= this.mAlphabetLength) {
            return -1;
        }
        return this.mCachingValue[i];
    }

    public void setMultiLangIndexer(int i) {
        StringBuilder sb;
        this.mCurrentLang = i;
        if (this.mUseFavoriteIndex) {
            if (this.mUseGroupIndex) {
                sb = new StringBuilder(String.valueOf(FAVORITE_CHAR) + GROUP_CHECKER);
                sb.append(SYMBOL_CHAR);
            } else {
                sb = new StringBuilder(String.valueOf(FAVORITE_CHAR) + SYMBOL_CHAR);
            }
        } else {
            sb = new StringBuilder(String.valueOf(SYMBOL_CHAR));
        }
        int i2 = 0;
        while (i2 < this.mLangAlphabetArray.length) {
            for (int i3 = 0; i3 < this.mLangAlphabetArray[i2].length(); i3++) {
                this.mLangIndexMap.put(Integer.valueOf(sb.length()), Integer.valueOf(i2));
                sb.append(this.mLangAlphabetArray[i2].charAt(i3));
            }
            i2++;
        }
        if (this.mUseDigitIndex) {
            this.mLangIndexMap.put(Integer.valueOf(sb.length()), Integer.valueOf(i2 - 1));
            sb.append(DIGIT_CHAR);
        }
        initIndexer(sb.toString());
    }

    public void setProfileItem(int i) {
        if (i >= 0) {
            this.mProfileItemCount = i;
        }
    }

    public void setFavoriteItem(int i) {
        if (i >= 0) {
            this.mFavoriteItemCount = i;
            this.mUseFavoriteIndex = true;
            setMultiLangIndexer(this.mCurrentLang);
        }
    }

    public void setGroupItem(int i) {
        if (i >= 0) {
            this.mGroupItemCount = i;
            this.mUseGroupIndex = true;
            setMultiLangIndexer(this.mCurrentLang);
        }
    }

    public void setDigitItem(int i) {
        if (i >= 0) {
            this.mDigitItemCount = i;
            this.mUseDigitIndex = true;
            setMultiLangIndexer(this.mCurrentLang);
        }
    }

    public boolean isUseDigitIndex() {
        return this.mUseDigitIndex;
    }

    public int getCurrentLang() {
        return this.mCurrentLang;
    }

    public int getLangbyIndex(int i) {
        if (i < 0 || this.mLangIndexMap == null) {
            return -1;
        }
        Integer num = new Integer(i);
        if (this.mLangIndexMap.containsKey(num)) {
            return this.mLangIndexMap.get(num).intValue();
        }
        return -1;
    }

    private void initIndexer(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() == 0) {
            throw new IllegalArgumentException("Invalid indexString :" + ((Object) charSequence));
        }
        this.mAlphabet = charSequence;
        int length = charSequence.length();
        this.mAlphabetLength = length;
        this.mCachingValue = new int[length];
        this.mAlphabetArray = new String[length];
        for (int i = 0; i < this.mAlphabetLength; i++) {
            if (this.mUseGroupIndex && this.mAlphabet.charAt(i) == 55357) {
                this.mAlphabetArray[i] = GROUP_CHAR;
            } else {
                this.mAlphabetArray[i] = Character.toString(this.mAlphabet.charAt(i));
            }
        }
        this.mAlphaMap = new SparseIntArray(this.mAlphabetLength);
        Collator collator = Collator.getInstance();
        this.mCollator = collator;
        collator.setStrength(0);
    }

    String[] getAlphabetArray() {
        return this.mAlphabetArray;
    }

    protected int compare(String str, String str2) {
        return this.mCollator.compare(str, str2);
    }

    public void cacheIndexInfo() {
        if (!isDataToBeIndexedAvailable() || getItemCount() == 0) {
            return;
        }
        Bundle bundle = getBundle();
        this.mBundle = bundle;
        if (bundle != null && bundle.containsKey("indexscroll_index_titles") && this.mBundle.containsKey("indexscroll_index_counts")) {
            getBundleInfo();
            return;
        }
        onBeginTransaction();
        for (int i = 0; i < this.mAlphabetLength; i++) {
            this.mCachingValue[i] = getPositionForString("" + this.mAlphabet.charAt(i));
        }
        onEndTransaction();
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0078  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int getPositionForString(String str) {
        int iAbs;
        int iAbs2;
        int i;
        int i2;
        SparseIntArray sparseIntArray = this.mAlphaMap;
        int itemCount = getItemCount();
        if (itemCount == 0 || this.mAlphabet == null) {
            return 0;
        }
        if (str != null && str.length() != 0) {
            char cCharAt = str.charAt(0);
            int i3 = sparseIntArray.get(cCharAt, Integer.MIN_VALUE);
            if (Integer.MIN_VALUE != i3) {
                iAbs2 = Math.abs(i3);
                iAbs = itemCount;
            } else {
                int iIndexOf = this.mAlphabet.toString().indexOf(cCharAt);
                if (iIndexOf > 0) {
                    int i4 = iIndexOf - 1;
                    int iAbs3 = (cCharAt <= this.mAlphabet.charAt(i4) || (i2 = sparseIntArray.get(this.mAlphabet.charAt(i4), Integer.MIN_VALUE)) == Integer.MIN_VALUE) ? 0 : Math.abs(i2);
                    if (iIndexOf < this.mAlphabet.length() - 1) {
                        int i5 = iIndexOf + 1;
                        iAbs = (cCharAt >= this.mAlphabet.charAt(i5) || (i = sparseIntArray.get(this.mAlphabet.charAt(i5), Integer.MIN_VALUE)) == Integer.MIN_VALUE) ? itemCount : Math.abs(i);
                        iAbs2 = iAbs3;
                    }
                }
            }
            char cCharAt2 = str.charAt(0);
            String str2 = cCharAt2 == '&' ? "!" : str;
            if (cCharAt2 == 9733) {
                int i6 = this.mProfileItemCount;
                if (iAbs2 < i6) {
                    iAbs2 = i6;
                }
            } else if (cCharAt2 == 55357) {
                int i7 = this.mProfileItemCount;
                int i8 = this.mFavoriteItemCount;
                if (iAbs2 < i7 + i8) {
                    iAbs2 = i7 + i8;
                }
            } else {
                int i9 = this.mProfileItemCount;
                int i10 = this.mFavoriteItemCount;
                int i11 = this.mGroupItemCount;
                if (iAbs2 < i9 + i10 + i11) {
                    iAbs2 = i9 + i10 + i11;
                }
            }
            int i12 = iAbs - this.mDigitItemCount;
            if (cCharAt2 == '#') {
                iAbs2 = i12;
            }
            int i13 = (i12 + iAbs2) / 2;
            while (i13 >= iAbs2 && i13 < i12) {
                String itemAt = getItemAt(i13);
                if (itemAt != null && !itemAt.equals("")) {
                    int iCompare = compare(itemAt, str2);
                    if (cCharAt2 == 9733 || cCharAt2 == '&' || cCharAt2 == '#') {
                        iCompare = 1;
                    }
                    if (iCompare == 0) {
                        if (iAbs2 == i13) {
                            break;
                        }
                    } else {
                        if (iCompare < 0) {
                            int i14 = i13 + 1;
                            if (i14 >= itemCount) {
                                break;
                            }
                            iAbs2 = i14;
                        }
                        i13 = (iAbs2 + i12) / 2;
                    }
                    i12 = i13;
                    i13 = (iAbs2 + i12) / 2;
                } else {
                    if (i13 <= iAbs2) {
                        break;
                    }
                    i13--;
                }
            }
            itemCount = i13;
            if (str.length() == 1) {
                sparseIntArray.put(cCharAt, itemCount);
            }
        }
        return itemCount;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0080 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006a A[EDGE_INSN: B:28:0x006a->B:19:0x006a BREAK  A[LOOP:1: B:13:0x0057->B:18:0x0067], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void getBundleInfo() {
        int i;
        int i2;
        String[] stringArray = this.mBundle.getStringArray("indexscroll_index_titles");
        int[] intArray = this.mBundle.getIntArray("indexscroll_index_counts");
        int i3 = this.mProfileItemCount;
        int i4 = 0;
        for (int i5 = 0; i5 < this.mAlphabetLength; i5++) {
            char cCharAt = this.mAlphabet.charAt(i5);
            this.mCachingValue[i5] = i3;
            Log.d("SemAbstractIndexer", "Get index info from bundle (" + i5 + ") : " + cCharAt + " = " + i3);
            if (cCharAt == 9733) {
                i = this.mFavoriteItemCount;
            } else {
                if (cCharAt == 55357) {
                    i = this.mGroupItemCount;
                }
                i2 = i4;
                while (true) {
                    if (i2 >= stringArray.length) {
                        break;
                    }
                    if (cCharAt == stringArray[i2].charAt(0)) {
                        i3 += intArray[i2];
                        i4 = i2;
                        break;
                    }
                    i2++;
                }
                if (cCharAt != "#".charAt(0)) {
                    this.mCachingValue[i5] = (getItemCount() + this.mProfileItemCount) - this.mDigitItemCount;
                }
            }
            i3 += i;
            i2 = i4;
            while (true) {
                if (i2 >= stringArray.length) {
                }
                i2++;
            }
            if (cCharAt != "#".charAt(0)) {
            }
        }
    }

    @Override // android.database.DataSetObserver
    public void onChanged() {
        super.onChanged();
        this.mAlphaMap.clear();
        this.mDataSetObservable.notifyChanged();
    }

    @Override // android.database.DataSetObserver
    public void onInvalidated() {
        super.onInvalidated();
        this.mAlphaMap.clear();
        this.mDataSetObservable.notifyInvalidated();
    }

    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        if (!this.mRegisteredDataSetObservable) {
            this.mDataSetObservable.registerObserver(dataSetObserver);
            this.mRegisteredDataSetObservable = true;
        } else {
            Log.e("SemAbstractIndexer", "Observer " + dataSetObserver + " is already registered.");
        }
    }

    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        if (this.mRegisteredDataSetObservable) {
            this.mDataSetObservable.unregisterObserver(dataSetObserver);
            this.mRegisteredDataSetObservable = false;
        } else {
            Log.e("SemAbstractIndexer", "Observer " + dataSetObserver + " was not registered.");
        }
    }
}
