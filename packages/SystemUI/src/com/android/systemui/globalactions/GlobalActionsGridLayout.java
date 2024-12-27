package com.android.systemui.globalactions;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class GlobalActionsGridLayout extends GlobalActionsLayout {
    public GlobalActionsGridLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout
    public final void addToListView(View view, boolean z) {
        ListGridLayout listGridLayout = (ListGridLayout) super.getListView();
        if (listGridLayout != null) {
            ViewGroup parentView = listGridLayout.getParentView(listGridLayout.mCurrentCount, listGridLayout.mReverseSublists, listGridLayout.mSwapRowsAndColumns);
            if (listGridLayout.mReverseItems) {
                parentView.addView(view, 0);
            } else {
                parentView.addView(view);
            }
            parentView.setVisibility(0);
            listGridLayout.mCurrentCount++;
        }
    }

    public float getAnimationDistance() {
        return (((ListGridLayout) super.getListView()).getRowCount() * getContext().getResources().getDimension(R.dimen.global_actions_grid_item_height)) / 2.0f;
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout, com.android.systemui.MultiListLayout
    public final ViewGroup getListView() {
        return (ListGridLayout) super.getListView();
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
        ListGridLayout listGridLayout = (ListGridLayout) super.getListView();
        if (separatedView != null) {
            separatedView.removeAllViews();
        }
        if (listGridLayout != null) {
            for (int i = 0; i < listGridLayout.getChildCount(); i++) {
                ViewGroup sublist = listGridLayout.getSublist(i);
                if (sublist != null) {
                    sublist.removeAllViews();
                    sublist.setVisibility(8);
                }
            }
            listGridLayout.mCurrentCount = 0;
        }
    }

    @Override // com.android.systemui.MultiListLayout
    public final void removeAllListViews() {
        ListGridLayout listGridLayout = (ListGridLayout) super.getListView();
        if (listGridLayout != null) {
            for (int i = 0; i < listGridLayout.getChildCount(); i++) {
                ViewGroup sublist = listGridLayout.getSublist(i);
                if (sublist != null) {
                    sublist.removeAllViews();
                    sublist.setVisibility(8);
                }
            }
            listGridLayout.mCurrentCount = 0;
        }
    }

    public void setupListView() {
        ListGridLayout listGridLayout = (ListGridLayout) super.getListView();
        listGridLayout.mExpectedCount = this.mAdapter.countListItems();
        listGridLayout.mReverseSublists = shouldReverseSublists();
        listGridLayout.mReverseItems = shouldReverseListItems();
        listGridLayout.mSwapRowsAndColumns = shouldSwapRowsAndColumns();
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
}
