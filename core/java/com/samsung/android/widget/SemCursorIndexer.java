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
    public SemCursorIndexer(Cursor cursor, int sortedColumnIndex, CharSequence indexCharacters) {
        super(indexCharacters);
        this.TAG = "SemCursorIndexer";
        this.mCursor = cursor;
        this.mColumnIndex = sortedColumnIndex;
        Log.d("SemCursorIndexer", "SemCursorIndexer constructor");
        if (sortedColumnIndex < 0) {
            RuntimeException e = new RuntimeException("here");
            e.fillInStackTrace();
            Log.w("SemCursorIndexer", "SemCursorIndexer() called with " + sortedColumnIndex, e);
        }
    }

    @Deprecated
    public SemCursorIndexer(Cursor cursor, int sortedColumnIndex, String[] indexCharacters, int aLangIndex) {
        super(indexCharacters, aLangIndex);
        this.TAG = "SemCursorIndexer";
        this.mCursor = cursor;
        this.mColumnIndex = sortedColumnIndex;
        Log.d("SemCursorIndexer", "SemCursorIndexer constructor");
        if (sortedColumnIndex < 0) {
            RuntimeException e = new RuntimeException("here");
            e.fillInStackTrace();
            Log.w("SemCursorIndexer", "SemCursorIndexer() called with " + sortedColumnIndex, e);
        }
    }

    public SemCursorIndexer(Cursor cursor, int sortedColumnIndex, CharSequence indexCharacters, int profileCount, int favoriteCount) {
        super(indexCharacters, profileCount, favoriteCount);
        this.TAG = "SemCursorIndexer";
        this.mCursor = cursor;
        this.mColumnIndex = sortedColumnIndex;
        Log.e("SemCursorIndexer", "SemCursorIndexer constructor, profileCount:" + profileCount + ", favoriteCount:" + favoriteCount);
        if (sortedColumnIndex < 0) {
            RuntimeException e = new RuntimeException("here");
            e.fillInStackTrace();
            Log.w("SemCursorIndexer", "SemCursorIndexer() called with " + sortedColumnIndex, e);
        }
    }

    public SemCursorIndexer(Cursor cursor, int sortedColumnIndex, String[] indexCharacters, int aLangIndex, int profileCount, int favoriteCount) {
        super(indexCharacters, aLangIndex, profileCount, favoriteCount);
        this.TAG = "SemCursorIndexer";
        this.mCursor = cursor;
        this.mColumnIndex = sortedColumnIndex;
        Log.e("SemCursorIndexer", "SemCursorIndexer constructor, profileCount:" + profileCount + ", favoriteCount:" + favoriteCount);
        if (sortedColumnIndex < 0) {
            RuntimeException e = new RuntimeException("here");
            e.fillInStackTrace();
            Log.w("SemCursorIndexer", "SemCursorIndexer() called with " + sortedColumnIndex, e);
        }
    }

    @Override // com.samsung.android.widget.SemAbstractIndexer
    protected boolean isDataToBeIndexedAvailable() {
        return getItemCount() > 0 && !this.mCursor.isClosed();
    }

    @Override // com.samsung.android.widget.SemAbstractIndexer
    protected String getItemAt(int pos) {
        if (this.mCursor.isClosed()) {
            Log.d("SemCursorIndexer", "SemCursorIndexer getItemCount : mCursor is closed  ");
            return null;
        }
        if (this.mColumnIndex < 0) {
            Log.d("SemCursorIndexer", "getItemAt() mColumnIndex : " + this.mColumnIndex);
        }
        this.mCursor.moveToPosition(pos);
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
    public void setProfileItemsCount(int count) {
        setProfileItem(count);
    }

    @Deprecated
    public void setFavoriteItemsCount(int count) {
        setFavoriteItem(count);
    }

    @Deprecated
    public void setGroupItemsCount(int count) {
        setGroupItem(count);
    }

    @Deprecated
    public void setMiscItemsCount(int count) {
        setDigitItem(count);
    }
}
