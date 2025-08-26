package com.android.systemui.statusbar.notification.row.wrapper;

import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;

/* loaded from: classes3.dex */
public class NotificationOngoingExpandViewWrapper extends NotificationOngoingViewWrapper {
    public NotificationOngoingExpandViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationOngoingViewWrapper
    public final void resolveTemplateViews(StatusBarNotification statusBarNotification) {
        super.resolveTemplateViews(statusBarNotification);
        this.mLeftIcon = (ImageView) this.mView.findViewWithTag("ongoingExpandPrimaryIcon");
        this.mPrimary = (TextView) this.mView.findViewWithTag("expandPrimary");
        this.mSecondary = (TextView) this.mView.findViewWithTag("expandSecondary");
        this.mSecondaryIcon = (ImageView) this.mView.findViewWithTag("expandSecondaryIcon");
        OngoingActivityData ongoingActivityData = this.mData;
        if (ongoingActivityData == null || ongoingActivityData.mDescription.isEmpty()) {
            return;
        }
        this.mSecondary = (TextView) this.mView.findViewWithTag("description");
    }
}
