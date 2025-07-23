package com.android.systemui.statusbar.notification.row;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationCompactHeadsUpEvent implements UiEventLogger.UiEventEnum {
    public static final /* synthetic */ NotificationCompactHeadsUpEvent[] $VALUES;
    public static final NotificationCompactHeadsUpEvent NOTIFICATION_COMPACT_HUN_SHOWN;
    private final int eventId;

    static {
        NotificationCompactHeadsUpEvent notificationCompactHeadsUpEvent = new NotificationCompactHeadsUpEvent("NOTIFICATION_COMPACT_HUN_SHOWN", 0, 1857);
        NOTIFICATION_COMPACT_HUN_SHOWN = notificationCompactHeadsUpEvent;
        NotificationCompactHeadsUpEvent[] notificationCompactHeadsUpEventArr = {notificationCompactHeadsUpEvent};
        $VALUES = notificationCompactHeadsUpEventArr;
        EnumEntriesKt.enumEntries(notificationCompactHeadsUpEventArr);
    }

    private NotificationCompactHeadsUpEvent(String str, int i, int i2) {
        this.eventId = i2;
    }

    public static NotificationCompactHeadsUpEvent valueOf(String str) {
        return (NotificationCompactHeadsUpEvent) Enum.valueOf(NotificationCompactHeadsUpEvent.class, str);
    }

    public static NotificationCompactHeadsUpEvent[] values() {
        return (NotificationCompactHeadsUpEvent[]) $VALUES.clone();
    }

    public final int getId() {
        return this.eventId;
    }
}
