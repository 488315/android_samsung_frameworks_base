package com.samsung.android.widget;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemCursorIndexer extends SemAbstractIndexer {

    @Deprecated
    public static final String EXTRA_INDEX_COUNTS = "indexscroll_index_counts";

    @Deprecated
    public static final String EXTRA_INDEX_TITLES = "indexscroll_index_titles";
    private static final boolean debug = true;
    private final String TAG;
    protected int mColumnIndex;
    protected Cursor mCursor;
    protected int mSavedCursorPos;

    @Deprecated
    public SemCursorIndexer(Cursor cursor, int i, CharSequence charSequence) {
        super(charSequence);
        this.TAG = "SemCursorIndexer";
        this.mCursor = cursor;
        this.mColumnIndex = i;
        Log.d("SemCursorIndexer", "SemCursorIndexer constructor");
        if (i < 0) {
            RuntimeException runtimeException = new RuntimeException("here");
            runtimeException.fillInStackTrace();
            Log.w("SemCursorIndexer", "SemCursorIndexer() called with " + i, runtimeException);
        }
    }

    @Deprecated
    public SemCursorIndexer(Cursor cursor, int i, String[] strArr, int i2) {
        super(strArr, i2);
        this.TAG = "SemCursorIndexer";
        this.mCursor = cursor;
        this.mColumnIndex = i;
        Log.d("SemCursorIndexer", "SemCursorIndexer constructor");
        if (i < 0) {
            RuntimeException runtimeException = new RuntimeException("here");
            runtimeException.fillInStackTrace();
            Log.w("SemCursorIndexer", "SemCursorIndexer() called with " + i, runtimeException);
        }
    }

    public SemCursorIndexer(Cursor cursor, int i, CharSequence charSequence, int i2, int i3) {
        super(charSequence, i2, i3);
        this.TAG = "SemCursorIndexer";
        this.mCursor = cursor;
        this.mColumnIndex = i;
        Log.e("SemCursorIndexer", "SemCursorIndexer constructor, profileCount:" + i2 + ", favoriteCount:" + i3);
        if (i < 0) {
            RuntimeException runtimeException = new RuntimeException("here");
            runtimeException.fillInStackTrace();
            Log.w("SemCursorIndexer", "SemCursorIndexer() called with " + i, runtimeException);
        }
    }

    public SemCursorIndexer(Cursor cursor, int i, String[] strArr, int i2, int i3, int i4) {
        super(strArr, i2, i3, i4);
        this.TAG = "SemCursorIndexer";
        this.mCursor = cursor;
        this.mColumnIndex = i;
        Log.e("SemCursorIndexer", "SemCursorIndexer constructor, profileCount:" + i3 + ", favoriteCount:" + i4);
        if (i < 0) {
            RuntimeException runtimeException = new RuntimeException("here");
            runtimeException.fillInStackTrace();
            Log.w("SemCursorIndexer", "SemCursorIndexer() called with " + i, runtimeException);
        }
    }

    @Override // com.samsung.android.widget.SemAbstractIndexer
    protected boolean isDataToBeIndexedAvailable() {
        return getItemCount() > 0 && !this.mCursor.isClosed();
    }

    @Override // com.samsung.android.widget.SemAbstractIndexer
    protected String getItemAt(int i) {
        if (this.mCursor.isClosed()) {
            Log.d("SemCursorIndexer", "SemCursorIndexer getItemCount : mCursor is closed  ");
            return null;
        }
        if (this.mColumnIndex < 0) {
            Log.d("SemCursorIndexer", "getItemAt() mColumnIndex : " + this.mColumnIndex);
        }
        this.mCursor.moveToPosition(i);
        try {
            return this.mCursor.getString(this.mColumnIndex);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.samsung.android.widget.SemAbstractIndexer
    protected int getItemCount() {
        if (this.mCursor.isClosed()) {
            Log.d("SemCursorIndexer", "SemCursorIndexer getItemCount : mCursor is closed  ");
            return 0;
        }
        return this.mCursor.getCount();
    }

    @Override // com.samsung.android.widget.SemAbstractIndexer
    protected Bundle getBundle() {
        Log.d("SemCursorIndexer", "SemCursorIndexer getBundle : Bundle was used by Indexer");
        return this.mCursor.getExtras();
    }

    @Override // com.samsung.android.widget.SemAbstractIndexer
    protected void onBeginTransaction() {
        this.mSavedCursorPos = this.mCursor.getPosition();
        Log.d("SemCursorIndexer", "SemCursorIndexer.onBeginTransaction() : Current cursor pos to save is :  " + this.mSavedCursorPos);
    }

    @Override // com.samsung.android.widget.SemAbstractIndexer
    protected void onEndTransaction() {
        Log.d("SemCursorIndexer", "SemCursorIndexer.onEndTransaction() : Saved cursor pos to restore  is :  " + this.mSavedCursorPos);
        this.mCursor.moveToPosition(this.mSavedCursorPos);
    }

    @Deprecated
    public void setProfileItemsCount(int i) {
        setProfileItem(i);
    }

    @Deprecated
    public void setFavoriteItemsCount(int i) {
        setFavoriteItem(i);
    }

    @Deprecated
    public void setGroupItemsCount(int i) {
        setGroupItem(i);
    }

    @Deprecated
    public void setMiscItemsCount(int i) {
        setDigitItem(i);
    }
}
