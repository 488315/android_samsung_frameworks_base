package com.android.systemui.globalactions;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class GlobalActionsGridLayout extends GlobalActionsLayout {
    public GlobalActionsGridLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout
    public final void addToListView(View view, boolean z) {
        ListGridLayout listView = getListView();
        if (listView != null) {
            ViewGroup parentView = listView.getParentView(listView.mCurrentCount, listView.mReverseSublists, listView.mSwapRowsAndColumns);
            if (listView.mReverseItems) {
                parentView.addView(view, 0);
            } else {
                parentView.addView(view);
            }
            parentView.setVisibility(0);
            listView.mCurrentCount++;
        }
    }

    public float getAnimationDistance() {
        return (getListView().getRowCount() * getContext().getResources().getDimension(R.dimen.global_actions_grid_item_height)) / 2.0f;
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout, com.android.systemui.MultiListLayout
    public final void onUpdateList() {
        setupListView();
        super.onUpdateList();
        updateSeparatedItemSize();
    }

    @Override // com.android.systemui.MultiListLayout
    public final void removeAllItems() {
        ViewGroup separatedView = getSeparatedView();
        ListGridLayout listView = getListView();
        if (separatedView != null) {
            separatedView.removeAllViews();
        }
        if (listView != null) {
            for (int i = 0; i < listView.getChildCount(); i++) {
                ViewGroup sublist = listView.getSublist(i);
                if (sublist != null) {
                    sublist.removeAllViews();
                    sublist.setVisibility(8);
                }
            }
            listView.mCurrentCount = 0;
        }
    }

    @Override // com.android.systemui.MultiListLayout
    public final void removeAllListViews() {
        ListGridLayout listView = getListView();
        if (listView != null) {
            for (int i = 0; i < listView.getChildCount(); i++) {
                ViewGroup sublist = listView.getSublist(i);
                if (sublist != null) {
                    sublist.removeAllViews();
                    sublist.setVisibility(8);
                }
            }
            listView.mCurrentCount = 0;
        }
    }

    public void setupListView() {
        ListGridLayout listView = getListView();
        listView.mExpectedCount = this.mAdapter.countListItems();
        listView.mReverseSublists = shouldReverseSublists();
        listView.mReverseItems = shouldReverseListItems();
        listView.mSwapRowsAndColumns = shouldSwapRowsAndColumns();
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout
    public final boolean shouldReverseListItems() {
        int currentRotation = getCurrentRotation();
        boolean z = currentRotation == 0 || currentRotation == 3;
        return getCurrentLayoutDirection() == 1 ? !z : z;
    }

    public boolean shouldReverseSublists() {
        return getCurrentRotation() == 3;
    }

    public boolean shouldSwapRowsAndColumns() {
        return getCurrentRotation() != 0;
    }

    public void updateSeparatedItemSize() {
        ViewGroup separatedView = getSeparatedView();
        if (separatedView.getChildCount() == 0) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = separatedView.getChildAt(0).getLayoutParams();
        if (separatedView.getChildCount() == 1) {
            layoutParams.width = -1;
            layoutParams.height = -1;
        } else {
            layoutParams.width = -2;
            layoutParams.height = -2;
        }
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout, com.android.systemui.MultiListLayout
    public final ListGridLayout getListView() {
        return (ListGridLayout) super.getListView();
    }
}
