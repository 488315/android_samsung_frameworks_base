package com.android.systemui.globalactions;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.helper.widget.Flow;
import com.android.systemui.HardwareBgDrawable;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class GlobalActionsLayoutLite extends GlobalActionsLayout {
    public static final /* synthetic */ int $r8$clinit = 0;

    public GlobalActionsLayoutLite(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOnClickListener(new GlobalActionsLayoutLite$$ExternalSyntheticLambda0());
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout
    public final void addToListView(View view, boolean z) {
        super.addToListView(view, z);
        ((Flow) findViewById(R.id.list_flow)).addView(view);
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

    @Override // com.android.systemui.globalactions.GlobalActionsLayout, com.android.systemui.MultiListLayout
    public final void onUpdateList() {
        super.onUpdateList();
        int integer = getResources().getInteger(R.integer.power_menu_lite_max_columns);
        if (getListView().getChildCount() - 1 == integer + 1 && integer > 2) {
            integer--;
        }
        Flow flow = (Flow) findViewById(R.id.list_flow);
        flow.mFlow.mMaxElementsWrap = integer;
        flow.requestLayout();
    }

    @Override // com.android.systemui.MultiListLayout
    public final void removeAllListViews() {
        View findViewById = findViewById(R.id.list_flow);
        super.removeAllListViews();
        super.addToListView(findViewById, false);
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout
    public boolean shouldReverseListItems() {
        return false;
    }
}
