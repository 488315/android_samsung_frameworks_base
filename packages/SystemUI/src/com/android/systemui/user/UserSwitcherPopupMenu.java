package com.android.systemui.user;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UserSwitcherPopupMenu extends ListPopupWindow {
    public ListAdapter adapter;
    public final Context context;
    public final Resources res;

    public UserSwitcherPopupMenu(Context context) {
        super(context);
        this.context = context;
        this.res = context.getResources();
        setBackgroundDrawable(null);
        setModal(false);
        setOverlapAnchor(true);
    }

    @Override // android.widget.ListPopupWindow
    public final void setAdapter(ListAdapter listAdapter) {
        super.setAdapter(listAdapter);
        this.adapter = listAdapter;
    }

    @Override // android.widget.ListPopupWindow
    public final void show() {
        super.show();
        ListView listView = getListView();
        if (listView != null) {
            int i = 0;
            listView.setVerticalScrollBarEnabled(false);
            listView.setHorizontalScrollBarEnabled(false);
            ShapeDrawable shapeDrawable = new ShapeDrawable();
            shapeDrawable.setAlpha(0);
            listView.setDivider(shapeDrawable);
            listView.setDividerHeight(this.res.getDimensionPixelSize(R.dimen.bouncer_user_switcher_popup_divider_height));
            final int dimensionPixelSize = this.res.getDimensionPixelSize(R.dimen.bouncer_user_switcher_popup_header_height);
            final Context context = this.context;
            listView.addHeaderView(new View(context) { // from class: com.android.systemui.user.UserSwitcherPopupMenu$createSpacer$1
                @Override // android.view.View
                public final void onMeasure(int i2, int i3) {
                    setMeasuredDimension(1, dimensionPixelSize);
                }

                @Override // android.view.View
                public final void draw(Canvas canvas) {
                }
            }, null, false);
            final Context context2 = this.context;
            listView.addFooterView(new View(context2) { // from class: com.android.systemui.user.UserSwitcherPopupMenu$createSpacer$1
                @Override // android.view.View
                public final void onMeasure(int i2, int i3) {
                    setMeasuredDimension(1, dimensionPixelSize);
                }

                @Override // android.view.View
                public final void draw(Canvas canvas) {
                }
            }, null, false);
            ListAdapter listAdapter = this.adapter;
            if (listAdapter != null) {
                int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) (this.res.getDisplayMetrics().widthPixels * 0.25d), Integer.MIN_VALUE);
                int count = listAdapter.getCount();
                int i2 = 0;
                for (int i3 = 0; i3 < count; i3++) {
                    View view = listAdapter.getView(i3, null, listView);
                    view.measure(makeMeasureSpec, 0);
                    i2 = Math.max(view.getMeasuredWidth(), i2);
                }
                i = i2;
            }
            setWidth(i);
        }
        super.show();
    }
}
