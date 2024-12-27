package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.widget.BigPictureNotificationImageView;
import com.android.internal.widget.NotificationIconManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
