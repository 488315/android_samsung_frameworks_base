package com.android.systemui.statusbar.notification.row;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;

/* loaded from: classes3.dex */
public abstract class NotificationRowContentBinderImplKt {
    public static final void setTooltipTextForOA(View view) {
        View viewFindViewById = view.findViewById(R.id.ongoing_activity_expand_icon_buttons);
        if (viewFindViewById instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) viewFindViewById;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt != null && childAt.getContentDescription().length() != 0) {
                    childAt.setTooltipText(childAt.getContentDescription());
                }
            }
        }
    }
}
