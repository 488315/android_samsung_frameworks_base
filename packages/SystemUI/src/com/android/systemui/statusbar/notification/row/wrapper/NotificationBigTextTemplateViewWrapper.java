package com.android.systemui.statusbar.notification.row.wrapper;

import android.R;
import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.view.View;
import com.android.internal.widget.ImageFloatingTextView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationBigTextTemplateViewWrapper extends NotificationTemplateViewWrapper {
    public ImageFloatingTextView mBigtext;

    public NotificationBigTextTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final int isEllipsis() {
        if (this.mBigtext.getLayout() != null) {
            return this.mBigtext.getLayout().getEllipsisCount(this.mBigtext.getLayout().getLineCount() - 1) > 0 ? 1 : 2;
        }
        return 0;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        StatusBarNotification statusBarNotification = expandableNotificationRow.mEntry.mSbn;
        this.mBigtext = this.mView.findViewById(R.id.button5);
        super.onContentUpdated(expandableNotificationRow);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void updateSummarize(ExpandableNotificationRow expandableNotificationRow) {
        StatusBarNotification statusBarNotification = expandableNotificationRow.mEntry.mSbn;
        this.mBigtext = this.mView.findViewById(R.id.button5);
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
