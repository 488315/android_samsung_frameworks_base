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

    /* JADX WARN: Removed duplicated region for block: B:77:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int getPositionForString(java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 254
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.widget.SemAbstractIndexer.getPositionForString(java.lang.String):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0080 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006a A[EDGE_INSN: B:22:0x006a->B:16:0x006a BREAK  A[LOOP:1: B:9:0x0057->B:13:0x0067], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void getBundleInfo() {
        /*
            r9 = this;
            android.os.Bundle r0 = r9.mBundle
            java.lang.String r1 = "indexscroll_index_titles"
            java.lang.String[] r0 = r0.getStringArray(r1)
            android.os.Bundle r1 = r9.mBundle
            java.lang.String r2 = "indexscroll_index_counts"
            int[] r1 = r1.getIntArray(r2)
            int r2 = r9.mProfileItemCount
            r3 = 0
            r4 = r3
            r5 = r4
        L15:
            int r6 = r9.mAlphabetLength
            if (r4 >= r6) goto L83
            java.lang.CharSequence r6 = r9.mAlphabet
            char r6 = r6.charAt(r4)
            int[] r7 = r9.mCachingValue
            r7[r4] = r2
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "Get index info from bundle ("
            r7.<init>(r8)
            r7.append(r4)
            java.lang.String r8 = ") : "
            r7.append(r8)
            r7.append(r6)
            java.lang.String r8 = " = "
            r7.append(r8)
            r7.append(r2)
            java.lang.String r7 = r7.toString()
            java.lang.String r8 = "SemAbstractIndexer"
            android.util.Log.d(r8, r7)
            r7 = 9733(0x2605, float:1.3639E-41)
            if (r6 != r7) goto L4e
            int r7 = r9.mFavoriteItemCount
        L4c:
            int r2 = r2 + r7
            goto L56
        L4e:
            r7 = 55357(0xd83d, float:7.7572E-41)
            if (r6 != r7) goto L56
            int r7 = r9.mGroupItemCount
            goto L4c
        L56:
            r7 = r5
        L57:
            int r8 = r0.length
            if (r7 >= r8) goto L6a
            r8 = r0[r7]
            char r8 = r8.charAt(r3)
            if (r6 != r8) goto L67
            r5 = r1[r7]
            int r2 = r2 + r5
            r5 = r7
            goto L6a
        L67:
            int r7 = r7 + 1
            goto L57
        L6a:
            java.lang.String r7 = "#"
            char r7 = r7.charAt(r3)
            if (r6 != r7) goto L80
            int[] r6 = r9.mCachingValue
            int r7 = r9.getItemCount()
            int r8 = r9.mProfileItemCount
            int r7 = r7 + r8
            int r8 = r9.mDigitItemCount
            int r7 = r7 - r8
            r6[r4] = r7
        L80:
            int r4 = r4 + 1
            goto L15
        L83:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.widget.SemAbstractIndexer.getBundleInfo():void");
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
