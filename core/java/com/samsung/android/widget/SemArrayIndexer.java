package com.samsung.android.widget;

import android.os.Bundle;

import java.util.List;

public class SemArrayIndexer extends SemAbstractIndexer {
    private static final String TAG = "SemArrayIndexer";
    private static final boolean debug = false;
    protected List<String> mData;

    @Deprecated
    public SemArrayIndexer(List<String> listData, CharSequence indexCharacters) {
        super(indexCharacters);
        this.mData = listData;
    }

    @Override // com.samsung.android.widget.SemAbstractIndexer
    protected int getItemCount() {
        return this.mData.size();
    }

    @Override // com.samsung.android.widget.SemAbstractIndexer
    protected String getItemAt(int pos) {
        return this.mData.get(pos);
    }

    @Override // com.samsung.android.widget.SemAbstractIndexer
    protected Bundle getBundle() {
        return null;
    }

    @Override // com.samsung.android.widget.SemAbstractIndexer
    protected boolean isDataToBeIndexedAvailable() {
        return getItemCount() > 0;
    }
}
