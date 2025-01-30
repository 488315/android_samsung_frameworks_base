package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubscreenNotificationDetail extends SubscreenNotificationTemplate {
    public NotificationEntry mEntry;

    public SubscreenNotificationDetail(Context context) {
        super(context);
        new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationDetail.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscreenNotificationDetail.this.getClass();
            }
        };
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenNotificationTemplate
    public final void makeView(NotificationEntry notificationEntry, boolean z) {
        FrameLayout frameLayout = this.mLayout;
        if (frameLayout != null) {
            this.mEntry = notificationEntry;
            this.mDeviceModel.makePopupDetailView(this.mContext, notificationEntry, z, frameLayout);
            if (z || this.mDeviceModel.useTopPresentation()) {
                this.mLayout.setOnTouchListener(this.mOnTouchListener);
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.SubscreenNotificationTemplate
    public final void performClick() {
        this.mDeviceModel.detailClicked(this.mEntry);
    }
}
