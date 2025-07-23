package com.android.systemui.dextouchpad.manager.notification;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
