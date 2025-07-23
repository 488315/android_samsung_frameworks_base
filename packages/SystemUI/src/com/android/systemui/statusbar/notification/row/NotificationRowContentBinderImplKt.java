package com.android.systemui.statusbar.notification.row;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class NotificationRowContentBinderImplKt {
    public static final void setTooltipTextForOA(View view) {
        View findViewById = view.findViewById(R.id.ongoing_activity_expand_icon_buttons);
        if (findViewById instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) findViewById;
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
