package com.android.systemui.statusbar.notification.row.wrapper;

import android.R;
import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.view.View;
import com.android.internal.widget.ImageFloatingTextView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationBigTextTemplateViewWrapper extends NotificationTemplateViewWrapper {
    public ImageFloatingTextView mBigtext;

    public NotificationBigTextTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        int i = NotificationBundleUi.$r8$clinit;
        StatusBarNotification statusBarNotification = expandableNotificationRow.getEntryLegacy().mSbn;
        this.mBigtext = this.mView.findViewById(R.id.choice);
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
