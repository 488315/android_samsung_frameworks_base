package com.android.systemui.statusbar.notification.row.icon;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.service.notification.StatusBarNotification;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.widget.NotificationRowIconView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotifRemoteViewsFactory;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationRowIconViewInflaterFactory implements NotifRemoteViewsFactory {
    public final AppIconProvider appIconProvider;
    public final NotificationIconStyleProvider iconStyleProvider;

    public NotificationRowIconViewInflaterFactory(AppIconProvider appIconProvider, NotificationIconStyleProvider notificationIconStyleProvider) {
        this.appIconProvider = appIconProvider;
        this.iconStyleProvider = notificationIconStyleProvider;
    }

    @Override // com.android.systemui.statusbar.notification.row.NotifRemoteViewsFactory
    public final View instantiate(final ExpandableNotificationRow expandableNotificationRow, int i, String str, final Context context, AttributeSet attributeSet) {
        if (!Intrinsics.areEqual(str, NotificationRowIconView.class.getName())) {
            return null;
        }
        NotificationRowIconView notificationRowIconView = new NotificationRowIconView(context, attributeSet);
        final StatusBarNotification statusBarNotification = expandableNotificationRow.getEntryLegacy().mSbn;
        notificationRowIconView.setIconProvider(statusBarNotification == null ? new NotificationRowIconView.NotificationIconProvider() { // from class: com.android.systemui.statusbar.notification.row.icon.NotificationRowIconViewInflaterFactory$createIconProvider$1
            public final Drawable getAppIcon() {
                return null;
            }

            public final boolean shouldShowAppIcon() {
                return false;
            }
        } : new NotificationRowIconView.NotificationIconProvider() { // from class: com.android.systemui.statusbar.notification.row.icon.NotificationRowIconViewInflaterFactory$createIconProvider$2
            public final Drawable getAppIcon() {
                boolean shouldShowWorkProfileBadge = NotificationRowIconViewInflaterFactory.this.iconStyleProvider.shouldShowWorkProfileBadge(context, statusBarNotification);
                return NotificationRowIconViewInflaterFactory.this.appIconProvider.getOrFetchAppIcon(context, statusBarNotification.getPackageName(), shouldShowWorkProfileBadge);
            }

            public final boolean shouldShowAppIcon() {
                boolean shouldShowAppIcon = NotificationRowIconViewInflaterFactory.this.iconStyleProvider.shouldShowAppIcon(context, statusBarNotification);
                expandableNotificationRow.getClass();
                return shouldShowAppIcon;
            }
        });
        return notificationRowIconView;
    }
}
