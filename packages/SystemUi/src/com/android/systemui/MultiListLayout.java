package com.android.systemui;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import com.android.systemui.globalactions.C1395x58d1e4ae;
import com.android.systemui.util.leak.RotationUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class MultiListLayout extends LinearLayout {
    public MultiListAdapter mAdapter;
    public int mRotation;
    public C1395x58d1e4ae mRotationListener;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class MultiListAdapter extends BaseAdapter {
        public abstract int countListItems();

        public abstract int countSeparatedItems();

        public abstract boolean shouldBeSeparated(int i);
    }

    public MultiListLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRotation = RotationUtils.getRotation(context);
    }

    public abstract ViewGroup getListView();

    public abstract ViewGroup getSeparatedView();

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int rotation = RotationUtils.getRotation(((LinearLayout) this).mContext);
        if (rotation != this.mRotation) {
            C1395x58d1e4ae c1395x58d1e4ae = this.mRotationListener;
            if (c1395x58d1e4ae != null) {
                c1395x58d1e4ae.onRotate();
            }
            this.mRotation = rotation;
        }
    }

    public void onUpdateList() {
        removeAllItems();
        boolean z = this.mAdapter.countSeparatedItems() > 0;
        ViewGroup separatedView = getSeparatedView();
        if (separatedView != null) {
            separatedView.setVisibility(z ? 0 : 8);
        }
    }

    public void removeAllItems() {
        removeAllListViews();
        ViewGroup separatedView = getSeparatedView();
        if (separatedView != null) {
            separatedView.removeAllViews();
        }
    }

    public void removeAllListViews() {
        ViewGroup listView = getListView();
        if (listView != null) {
            listView.removeAllViews();
        }
    }
}
