package com.android.systemui.globalactions;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import com.android.systemui.R;

/* loaded from: classes2.dex */
public class GlobalActionsPopupMenu extends ListPopupWindow {
    public ListAdapter mAdapter;
    public final Context mContext;
    public final int mGlobalActionsSidePadding;
    public final boolean mIsDropDownMode;
    public final int mMaximumWidthThresholdDp;
    public final int mMenuVerticalPadding;
    public GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda6 mOnItemLongClickListener;

    public GlobalActionsPopupMenu(Context context, boolean z) {
        super(context);
        this.mMenuVerticalPadding = 0;
        this.mGlobalActionsSidePadding = 0;
        this.mMaximumWidthThresholdDp = 800;
        this.mContext = context;
        Resources resources = context.getResources();
        setBackgroundDrawable(resources.getDrawable(R.drawable.global_actions_popup_bg, context.getTheme()));
        this.mIsDropDownMode = z;
        setInputMethodMode(2);
        setModal(true);
        this.mGlobalActionsSidePadding = resources.getDimensionPixelSize(R.dimen.global_actions_side_margin);
        if (z) {
            return;
        }
        this.mMenuVerticalPadding = resources.getDimensionPixelSize(R.dimen.control_menu_vertical_padding);
    }

    @Override // android.widget.ListPopupWindow
    public final void setAdapter(ListAdapter listAdapter) {
        this.mAdapter = listAdapter;
        super.setAdapter(listAdapter);
    }

    @Override // android.widget.ListPopupWindow
    public final void show() {
        super.show();
        if (this.mOnItemLongClickListener != null) {
            getListView().setOnItemLongClickListener(this.mOnItemLongClickListener);
        }
        ListView listView = getListView();
        Resources resources = this.mContext.getResources();
        setVerticalOffset((-getAnchorView().getHeight()) / 2);
        if (this.mIsDropDownMode) {
            listView.setDividerHeight(resources.getDimensionPixelSize(R.dimen.control_list_divider));
            listView.setDivider(resources.getDrawable(R.drawable.global_actions_list_divider_inset));
        } else {
            if (this.mAdapter == null) {
                return;
            }
            int i = Resources.getSystem().getDisplayMetrics().widthPixels;
            float f = i / Resources.getSystem().getDisplayMetrics().density;
            double d = i;
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) (0.9d * d), Integer.MIN_VALUE);
            int iMax = 0;
            for (int i2 = 0; i2 < this.mAdapter.getCount(); i2++) {
                View view = this.mAdapter.getView(i2, null, listView);
                view.measure(iMakeMeasureSpec, 0);
                iMax = Math.max(view.getMeasuredWidth(), iMax);
            }
            if (f < this.mMaximumWidthThresholdDp) {
                iMax = Math.max(iMax, (int) (d * 0.5d));
            }
            int i3 = this.mMenuVerticalPadding;
            listView.setPadding(0, i3, 0, i3);
            setWidth(iMax);
            if (getAnchorView().getLayoutDirection() == 0) {
                setHorizontalOffset((getAnchorView().getWidth() - this.mGlobalActionsSidePadding) - iMax);
            } else {
                setHorizontalOffset(this.mGlobalActionsSidePadding);
            }
        }
        super.show();
    }
}
