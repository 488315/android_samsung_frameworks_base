package com.android.systemui;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda0;
import com.android.systemui.util.leak.RotationUtils;

public abstract class MultiListLayout extends LinearLayout {
    public MultiListAdapter mAdapter;
    public int mRotation;
    public GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda0 mRotationListener;

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
            GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda0 globalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda0 = this.mRotationListener;
            if (globalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda0 != null) {
                globalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda0.onRotate();
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
