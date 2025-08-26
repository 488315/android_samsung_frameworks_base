package com.android.systemui.dextouchpad.manager.notification;

/* loaded from: classes2.dex */
public enum NotificationType {
    TOUCHPAD(200, NotificationId.TOUCHPAD.id, "group_touchpad"),
    SPEN(201, NotificationId.SPEN.id, "group_touchpad");

    public final String group;
    public final int id;
    public final int type;

    NotificationType(int i, int i2, String str) {
        this.type = i;
        this.id = i2;
        this.group = str;
    }
}
