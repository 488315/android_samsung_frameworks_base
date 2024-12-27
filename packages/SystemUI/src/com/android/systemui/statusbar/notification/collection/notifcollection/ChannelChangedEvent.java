package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.app.NotificationChannel;
import android.os.UserHandle;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ChannelChangedEvent extends NotifEvent {
    public final NotificationChannel channel;
    public final int modificationType;
    public final String pkgName;
    public final UserHandle user;

    public ChannelChangedEvent(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        super("onNotificationChannelModified", null);
        this.pkgName = str;
        this.user = userHandle;
        this.channel = notificationChannel;
        this.modificationType = i;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent
    public final void dispatchToListener(NotifCollectionListener notifCollectionListener) {
        notifCollectionListener.onNotificationChannelModified(this.pkgName, this.user, this.channel, this.modificationType);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ChannelChangedEvent)) {
            return false;
        }
        ChannelChangedEvent channelChangedEvent = (ChannelChangedEvent) obj;
        return Intrinsics.areEqual(this.pkgName, channelChangedEvent.pkgName) && Intrinsics.areEqual(this.user, channelChangedEvent.user) && Intrinsics.areEqual(this.channel, channelChangedEvent.channel) && this.modificationType == channelChangedEvent.modificationType;
    }

    public final int hashCode() {
        return Integer.hashCode(this.modificationType) + ((this.channel.hashCode() + ((this.user.hashCode() + (this.pkgName.hashCode() * 31)) * 31)) * 31);
    }

    public final String toString() {
        return "ChannelChangedEvent(pkgName=" + this.pkgName + ", user=" + this.user + ", channel=" + this.channel + ", modificationType=" + this.modificationType + ")";
    }
}
