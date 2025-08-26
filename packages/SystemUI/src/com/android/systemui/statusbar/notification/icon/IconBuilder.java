package com.android.systemui.statusbar.notification.icon;

import android.content.Context;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* loaded from: classes3.dex */
public final class IconBuilder {
    public final Context context;

    public IconBuilder(Context context) {
        this.context = context;
    }

    public static StatusBarIconView createIconView$default(IconBuilder iconBuilder, NotificationEntry notificationEntry) {
        Context context = iconBuilder.context;
        iconBuilder.getClass();
        return new StatusBarIconView(context, AbstractResolvableFuture$$ExternalSyntheticOutline0.m(notificationEntry.mSbn.getPackageName(), "/0x", Integer.toHexString(notificationEntry.mSbn.getId())), notificationEntry.mSbn);
    }
}
