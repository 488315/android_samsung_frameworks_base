package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;

/* loaded from: classes3.dex */
public class PromotedPermissionGutsContent extends LinearLayout implements NotificationGuts.GutsContent, View.OnClickListener {
    public NotificationGutsManager.AnonymousClass1 mDemoteAction;
    public NotificationGuts mGutsContainer;
    public StatusBarNotification mSbn;
    public TextView mUndoButton;

    public PromotedPermissionGutsContent(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        new MetricsLogger();
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final int getActualHeight() {
        return getHeight();
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean handleCloseControls(boolean z, boolean z2) {
        if (!z) {
            return false;
        }
        this.mDemoteAction.onClick(this);
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean isLeavebehind() {
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean needsFalsingProtection() {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        dispatchConfigurationChanged(getResources().getConfiguration());
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        NotificationGuts notificationGuts = this.mGutsContainer;
        if (notificationGuts != null) {
            notificationGuts.resetFalsingCheck();
        }
        if (view.getId() == R.id.undo) {
            this.mGutsContainer.closeControls(view, false);
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        TextView textView = (TextView) findViewById(R.id.undo);
        this.mUndoButton = textView;
        textView.setOnClickListener(this);
        this.mUndoButton.setContentDescription(getContext().getString(R.string.snooze_undo_content_description));
    }

    public final boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        if (i != R.id.action_snooze_undo) {
            return false;
        }
        this.mGutsContainer.closeControls(this.mUndoButton, false);
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final void setGutsParent(NotificationGuts notificationGuts) {
        this.mGutsContainer = notificationGuts;
    }

    public final void setStatusBarNotification(StatusBarNotification statusBarNotification) {
        this.mSbn = statusBarNotification;
        ((TextView) findViewById(R.id.demote_explain)).setText(((LinearLayout) this).mContext.getResources().getString(R.string.demote_explain_text, this.mSbn.getPackageName()));
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean shouldBeSavedOnClose() {
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final boolean willBeRemoved() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotificationGuts.GutsContent
    public final View getContentView() {
        return this;
    }
}
