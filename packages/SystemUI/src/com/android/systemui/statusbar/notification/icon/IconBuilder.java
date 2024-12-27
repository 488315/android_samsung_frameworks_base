package com.android.systemui.statusbar.notification.icon;

import android.content.Context;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class IconBuilder {
    public final Context context;

    public IconBuilder(Context context) {
        this.context = context;
    }

    public final StatusBarIconView createIconView(NotificationEntry notificationEntry) {
        return new StatusBarIconView(this.context, AbstractResolvableFuture$$ExternalSyntheticOutline0.m(notificationEntry.mSbn.getPackageName(), "/0x", Integer.toHexString(notificationEntry.mSbn.getId())), notificationEntry.mSbn);
    }
}
