package com.android.systemui.statusbar.notification.row.wrapper;

import android.R;
import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.view.NotificationHeaderView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.widget.NotificationExpandButton;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.TransformState;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationOngoingViewWrapper extends NotificationHeaderViewWrapper {
    public View mActionIconButtonContainer;
    public TextView mChronometer;
    public OngoingActivityData mData;
    public ImageView mLeftIcon;
    public TextView mNotificationChildrenCount;
    public TextView mPrimary;
    public TextView mSecondary;
    public ImageView mSecondaryIcon;

    public NotificationOngoingViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final TextView getChildrenCountText() {
        return this.mNotificationChildrenCount;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        super.onContentUpdated(expandableNotificationRow);
        ViewTransformationHelper viewTransformationHelper = this.mTransformationHelper;
        viewTransformationHelper.getClass();
        ArraySet arraySet = new ArraySet(viewTransformationHelper.mTransformedViews.values());
        resolveTemplateViews(expandableNotificationRow.mEntry.mSbn);
        updateTransformedTypes();
        viewTransformationHelper.addRemainingTransformTypes(this.mView);
        ArraySet arraySet2 = new ArraySet(viewTransformationHelper.mTransformedViews.values());
        for (int i = 0; i < arraySet.size(); i++) {
            View view = (View) arraySet.valueAt(i);
            if (!arraySet2.contains(view)) {
                TransformState createFrom = TransformState.createFrom(view, viewTransformationHelper);
                createFrom.setVisible(true, true);
                createFrom.recycle();
            }
        }
    }

    public void resolveTemplateViews(StatusBarNotification statusBarNotification) {
        this.mLeftIcon = (ImageView) this.mView.findViewWithTag("ongoingCollapsedPrimaryIcon");
        this.mPrimary = (TextView) this.mView.findViewWithTag("collapsedPrimary");
        this.mSecondary = (TextView) this.mView.findViewWithTag("collapsedSecondary");
        this.mSecondaryIcon = (ImageView) this.mView.findViewWithTag("collapsedSecondaryIcon");
        this.mActionIconButtonContainer = this.mView.findViewWithTag("iconButton");
        this.mNotificationChildrenCount = (TextView) this.mView.findViewById(R.id.input_minute);
        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
        String key = statusBarNotification.getKey();
        ongoingActivityDataHelper.getClass();
        OngoingActivityData pendingOngoingActivityData = OngoingActivityDataHelper.getPendingOngoingActivityData(key);
        this.mData = pendingOngoingActivityData;
        if (pendingOngoingActivityData == null || pendingOngoingActivityData.mChronometerView == null) {
            return;
        }
        this.mChronometer = (TextView) this.mView.findViewWithTag(pendingOngoingActivityData.mChronometerTag);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void updateExpandability(boolean z, ExpandableNotificationRow.AnonymousClass1 anonymousClass1, boolean z2) {
        NotificationExpandButton notificationExpandButton = this.mExpandButton;
        if (notificationExpandButton != null) {
            notificationExpandButton.setVisibility(z ? 0 : 8);
            this.mExpandButton.setOnClickListener(z ? anonymousClass1 : null);
            if (z2) {
                this.mExpandButton.getParent().requestLayout();
            }
        }
        NotificationHeaderView notificationHeaderView = this.mNotificationHeader;
        if (notificationHeaderView != null) {
            notificationHeaderView.setOnClickListener(z ? anonymousClass1 : null);
        }
        ImageView imageView = this.mLeftIcon;
        if (imageView != null) {
            if (!z) {
                anonymousClass1 = null;
            }
            imageView.setOnClickListener(anonymousClass1);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper
    public final void updateTransformedTypes() {
        super.updateTransformedTypes();
        TextView textView = this.mPrimary;
        ViewTransformationHelper viewTransformationHelper = this.mTransformationHelper;
        if (textView != null) {
            viewTransformationHelper.addTransformedView(textView, 1);
        }
        TextView textView2 = this.mSecondary;
        if (textView2 != null) {
            viewTransformationHelper.addTransformedView(textView2, 2);
        }
        ImageView imageView = this.mSecondaryIcon;
        if (imageView != null) {
            viewTransformationHelper.addTransformedView(imageView);
        }
        ImageView imageView2 = this.mLeftIcon;
        if (imageView2 != null) {
            viewTransformationHelper.addTransformedView(imageView2, 0);
        }
        TextView textView3 = this.mChronometer;
        if (textView3 != null) {
            addTransformedViews(textView3);
        }
        View view = this.mActionIconButtonContainer;
        if (view != null) {
            viewTransformationHelper.addTransformedView(view, 5);
        }
    }
}
