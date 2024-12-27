package com.android.systemui.globalactions;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.systemui.HardwareBgDrawable;
import com.android.systemui.R;

public class GlobalActionsFlatLayout extends GlobalActionsLayout {
    public GlobalActionsFlatLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout
    public final void addToListView(View view, boolean z) {
        super.addToListView(view, z);
        View findViewById = findViewById(R.id.global_actions_overflow_button);
        if (findViewById != null) {
            getListView().removeView(findViewById);
            super.addToListView(findViewById, z);
        }
    }

    public float getAnimationDistance() {
        return getGridItemSize() / 2.0f;
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout
    public final HardwareBgDrawable getBackgroundDrawable(int i) {
        return null;
    }

    public float getGridItemSize() {
        return getContext().getResources().getDimension(R.dimen.global_actions_grid_item_height);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        ViewGroup listView = getListView();
        boolean z2 = false;
        for (int i5 = 0; i5 < listView.getChildCount(); i5++) {
            View childAt = listView.getChildAt(i5);
            if (childAt instanceof GlobalActionsItem) {
                z2 = z2 || ((GlobalActionsItem) childAt).isTruncated();
            }
        }
        if (z2) {
            for (int i6 = 0; i6 < listView.getChildCount(); i6++) {
                View childAt2 = listView.getChildAt(i6);
                if (childAt2 instanceof GlobalActionsItem) {
                    TextView textView = (TextView) ((GlobalActionsItem) childAt2).findViewById(android.R.id.message);
                    textView.setSingleLine(true);
                    textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                }
            }
        }
    }

    @Override // com.android.systemui.MultiListLayout
    public final void removeAllListViews() {
        View findViewById = findViewById(R.id.global_actions_overflow_button);
        super.removeAllListViews();
        if (findViewById != null) {
            super.addToListView(findViewById, false);
        }
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout
    public boolean shouldReverseListItems() {
        int currentRotation = getCurrentRotation();
        if (currentRotation == 0) {
            return false;
        }
        return getCurrentLayoutDirection() == 1 ? currentRotation == 1 : currentRotation == 3;
    }
}
