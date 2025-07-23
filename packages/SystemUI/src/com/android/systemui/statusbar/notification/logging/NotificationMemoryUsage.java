package com.android.systemui.statusbar.notification.logging;

import android.app.Notification;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationMemoryUsage {

    /* renamed from: notification, reason: collision with root package name */
    public final Notification f135notification;
    public final String notificationKey;
    public final NotificationObjectUsage objectUsage;
    public final String packageName;
    public final int uid;
    public final List viewUsage;

    public NotificationMemoryUsage(String str, int i, String str2, Notification notification2, NotificationObjectUsage notificationObjectUsage, List<NotificationViewUsage> list) {
        this.packageName = str;
        this.uid = i;
        this.notificationKey = str2;
        this.f135notification = notification2;
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
        return Intrinsics.areEqual(this.packageName, notificationMemoryUsage.packageName) && this.uid == notificationMemoryUsage.uid && Intrinsics.areEqual(this.notificationKey, notificationMemoryUsage.notificationKey) && Intrinsics.areEqual(this.f135notification, notificationMemoryUsage.f135notification) && Intrinsics.areEqual(this.objectUsage, notificationMemoryUsage.objectUsage) && Intrinsics.areEqual(this.viewUsage, notificationMemoryUsage.viewUsage);
    }

    public final int hashCode() {
        return this.viewUsage.hashCode() + ((this.objectUsage.hashCode() + ((this.f135notification.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.uid, this.packageName.hashCode() * 31, 31), 31, this.notificationKey)) * 31)) * 31);
    }

    public final String toString() {
        return "NotificationMemoryUsage(packageName=" + this.packageName + ", uid=" + this.uid + ", notificationKey=" + this.notificationKey + ", notification=" + this.f135notification + ", objectUsage=" + this.objectUsage + ", viewUsage=" + this.viewUsage + ")";
    }
}
