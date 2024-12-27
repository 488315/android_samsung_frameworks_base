package com.android.systemui.globalactions;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import com.android.systemui.R;

public final class GlobalActionsPopupMenu extends ListPopupWindow {
    public ListAdapter mAdapter;
    public final Context mContext;
    public final int mGlobalActionsSidePadding;
    public final boolean mIsDropDownMode;
    public final int mMaximumWidthThresholdDp;
    public final int mMenuVerticalPadding;
    public AdapterView.OnItemLongClickListener mOnItemLongClickListener;

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
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) (0.9d * d), Integer.MIN_VALUE);
            int i2 = 0;
            for (int i3 = 0; i3 < this.mAdapter.getCount(); i3++) {
                View view = this.mAdapter.getView(i3, null, listView);
                view.measure(makeMeasureSpec, 0);
                i2 = Math.max(view.getMeasuredWidth(), i2);
            }
            if (f < this.mMaximumWidthThresholdDp) {
                i2 = Math.max(i2, (int) (d * 0.5d));
            }
            int i4 = this.mMenuVerticalPadding;
            listView.setPadding(0, i4, 0, i4);
            setWidth(i2);
            if (getAnchorView().getLayoutDirection() == 0) {
                setHorizontalOffset((getAnchorView().getWidth() - this.mGlobalActionsSidePadding) - i2);
            } else {
                setHorizontalOffset(this.mGlobalActionsSidePadding);
            }
        }
        super.show();
    }
}
