package com.android.systemui.statusbar.notification.logging;

import android.app.Notification;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationMemoryUsage {

    /* renamed from: notification, reason: collision with root package name */
    public final Notification f842notification;
    public final String notificationKey;
    public final NotificationObjectUsage objectUsage;
    public final String packageName;
    public final int uid;
    public final List viewUsage;

    public NotificationMemoryUsage(String str, int i, String str2, Notification notification2, NotificationObjectUsage notificationObjectUsage, List<NotificationViewUsage> list) {
        this.packageName = str;
        this.uid = i;
        this.notificationKey = str2;
        this.f842notification = notification2;
        this.objectUsage = notificationObjectUsage;
        this.viewUsage = list;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotificationMemoryUsage)) {
            return false;
        }
        NotificationMemoryUsage notificationMemoryUsage = (NotificationMemoryUsage) obj;
        return Intrinsics.areEqual(this.packageName, notificationMemoryUsage.packageName) && this.uid == notificationMemoryUsage.uid && Intrinsics.areEqual(this.notificationKey, notificationMemoryUsage.notificationKey) && Intrinsics.areEqual(this.f842notification, notificationMemoryUsage.f842notification) && Intrinsics.areEqual(this.objectUsage, notificationMemoryUsage.objectUsage) && Intrinsics.areEqual(this.viewUsage, notificationMemoryUsage.viewUsage);
    }

    public final int hashCode() {
        return this.viewUsage.hashCode() + ((this.objectUsage.hashCode() + ((this.f842notification.hashCode() + AppInfo$$ExternalSyntheticOutline0.m41m(this.notificationKey, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.uid, this.packageName.hashCode() * 31, 31), 31)) * 31)) * 31);
    }

    public final String toString() {
        return "NotificationMemoryUsage(packageName=" + this.packageName + ", uid=" + this.uid + ", notificationKey=" + this.notificationKey + ", notification=" + this.f842notification + ", objectUsage=" + this.objectUsage + ", viewUsage=" + this.viewUsage + ")";
    }
}
