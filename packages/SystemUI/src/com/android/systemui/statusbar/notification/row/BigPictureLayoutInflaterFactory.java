package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.widget.BigPictureNotificationImageView;
import com.android.internal.widget.NotificationIconManager;

public final class BigPictureLayoutInflaterFactory implements NotifRemoteViewsFactory {
    @Override // com.android.systemui.statusbar.notification.row.NotifRemoteViewsFactory
    public final View instantiate(ExpandableNotificationRow expandableNotificationRow, int i, String str, Context context, AttributeSet attributeSet) {
        if (i != 2 || !str.equals(BigPictureNotificationImageView.class.getName())) {
            return null;
        }
        BigPictureNotificationImageView bigPictureNotificationImageView = new BigPictureNotificationImageView(context, attributeSet);
        expandableNotificationRow.getClass();
        bigPictureNotificationImageView.setIconManager((NotificationIconManager) null);
        return bigPictureNotificationImageView;
    }
}
