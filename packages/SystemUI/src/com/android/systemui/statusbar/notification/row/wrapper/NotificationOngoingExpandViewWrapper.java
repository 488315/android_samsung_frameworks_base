package com.android.systemui.statusbar.notification.row.wrapper;

import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;

public final class NotificationOngoingExpandViewWrapper extends NotificationOngoingViewWrapper {
    public ImageView mExpandButtonEnd;
    public ImageView mExpandButtonTop;
    public boolean mHasEndExpandButton;

    public NotificationOngoingExpandViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationOngoingViewWrapper
    public final void resolveTemplateViews$1(StatusBarNotification statusBarNotification) {
        super.resolveTemplateViews$1(statusBarNotification);
        this.mLeftIcon = (ImageView) this.mView.findViewWithTag("ongoingExpandPrimaryIcon");
        this.mPrimary = (TextView) this.mView.findViewWithTag("expandPrimary");
        this.mSecondary = (TextView) this.mView.findViewWithTag("expandSecondary");
        this.mTime = (TextView) this.mView.findViewWithTag("expandHeaderTime");
        this.mExpandButtonEnd = (ImageView) this.mView.findViewWithTag("ExpandedExpandButtonEnd");
        this.mExpandButtonTop = (ImageView) this.mView.findViewWithTag("ExpandedExpandButtonTop");
        OngoingActivityData ongoingActivityData = this.mData;
        this.mHasEndExpandButton = ongoingActivityData.mActions != null && ongoingActivityData.mActionType == 0 && this.mContext.getResources().getConfiguration().fontScale < 1.3f;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationOngoingViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void updateExpandability(boolean z, View.OnClickListener onClickListener, boolean z2) {
        super.updateExpandability(z, onClickListener, z2);
        ExpandableNotificationRow expandableNotificationRow = this.mRow;
        if (expandableNotificationRow == null || expandableNotificationRow.mEntry.isPromotedState()) {
            return;
        }
        if (this.mHasEndExpandButton) {
            ImageView imageView = this.mExpandButtonEnd;
            if (imageView != null) {
                imageView.setVisibility(z ? 0 : 8);
                this.mExpandButtonTop.setVisibility(8);
                ImageView imageView2 = this.mExpandButtonEnd;
                if (!z) {
                    onClickListener = null;
                }
                imageView2.setOnClickListener(onClickListener);
                return;
            }
            return;
        }
        ImageView imageView3 = this.mExpandButtonTop;
        if (imageView3 != null) {
            imageView3.setVisibility(z ? 0 : 8);
            this.mExpandButtonEnd.setVisibility(8);
            ImageView imageView4 = this.mExpandButtonTop;
            if (!z) {
                onClickListener = null;
            }
            imageView4.setOnClickListener(onClickListener);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationOngoingViewWrapper
    public final void updateTransformedTypes() {
        super.updateTransformedTypes();
        this.mTransformationHelper.addTransformedView(this.mHasEndExpandButton ? this.mExpandButtonEnd : this.mExpandButtonTop, 6);
    }
}
