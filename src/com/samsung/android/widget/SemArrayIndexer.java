package com.samsung.android.widget;

import android.os.Bundle;
import java.util.List;

/* loaded from: classes6.dex */
public class SemArrayIndexer extends SemAbstractIndexer {
    private static final String TAG = "SemArrayIndexer";
    private static final boolean debug = false;
    protected List<String> mData;

    @Override // com.samsung.android.widget.SemAbstractIndexer
    protected Bundle getBundle() {
        return null;
    }

    @Deprecated
    public SemArrayIndexer(List<String> list, CharSequence charSequence) {
        super(charSequence);
        this.mData = list;
    }

    @Override // com.samsung.android.widget.SemAbstractIndexer
    protected int getItemCount() {
        return this.mData.size();
    }

    @Override // com.samsung.android.widget.SemAbstractIndexer
    protected String getItemAt(int i) {
        return this.mData.get(i);
    }

    @Override // com.samsung.android.widget.SemAbstractIndexer
    protected boolean isDataToBeIndexedAvailable() {
        return getItemCount() > 0;
    }
}
