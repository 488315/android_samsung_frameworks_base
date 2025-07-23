package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.widget.BigPictureNotificationImageView;
import com.android.internal.widget.NotificationIconManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BigPictureLayoutInflaterFactory implements NotifRemoteViewsFactory {
    @Override // com.android.systemui.statusbar.notification.row.NotifRemoteViewsFactory
    public final View instantiate(ExpandableNotificationRow expandableNotificationRow, int i, String str, Context context, AttributeSet attributeSet) {
        if (i != 2 || !Intrinsics.areEqual(str, BigPictureNotificationImageView.class.getName())) {
            return null;
        }
        BigPictureNotificationImageView bigPictureNotificationImageView = new BigPictureNotificationImageView(context, attributeSet);
        expandableNotificationRow.getClass();
        bigPictureNotificationImageView.setIconManager((NotificationIconManager) null);
        return bigPictureNotificationImageView;
    }
}
