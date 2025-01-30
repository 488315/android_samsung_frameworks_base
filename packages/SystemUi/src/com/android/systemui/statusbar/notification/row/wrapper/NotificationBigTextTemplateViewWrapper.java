package com.android.systemui.statusbar.notification.row.wrapper;

import android.R;
import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.view.View;
import com.android.internal.widget.ImageFloatingTextView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationBigTextTemplateViewWrapper extends NotificationTemplateViewWrapper {
    public ImageFloatingTextView mBigtext;

    public NotificationBigTextTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        StatusBarNotification statusBarNotification = expandableNotificationRow.mEntry.mSbn;
        this.mBigtext = this.mView.findViewById(R.id.bubble_button);
        super.onContentUpdated(expandableNotificationRow);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper
    public final void updateTransformedTypes() {
        super.updateTransformedTypes();
        View view = this.mBigtext;
        if (view != null) {
            this.mTransformationHelper.addTransformedView(view, 2);
        }
    }
}
