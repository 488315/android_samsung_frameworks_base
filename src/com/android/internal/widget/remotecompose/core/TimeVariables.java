package com.android.internal.widget.remotecompose.core;

import java.time.LocalDateTime;
import java.time.ZoneId;

/* loaded from: classes6.dex */
public class TimeVariables {
    /* JADX WARN: Type inference failed for: r9v1, types: [java.time.ZonedDateTime] */
    public void updateTime(RemoteContext remoteContext, ZoneId zoneId, LocalDateTime localDateTime) {
        int value = localDateTime.getMonth().getValue();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        int i = (hour * 60) + minute;
        float second = (minute * 60) + localDateTime.getSecond();
        int value2 = localDateTime.getDayOfWeek().getValue();
        long epochSecond = localDateTime.toEpochSecond(localDateTime.atZone(zoneId).toOffsetDateTime().getOffset());
        remoteContext.loadFloat(10, r9.getTotalSeconds());
        remoteContext.loadFloat(1, (localDateTime.getNano() * 1.0E-9f) + second);
        remoteContext.loadInteger(32, (int) epochSecond);
        remoteContext.loadFloat(2, second);
        remoteContext.loadFloat(3, i);
        remoteContext.loadFloat(4, hour);
        float f = value;
        remoteContext.loadFloat(9, f);
        remoteContext.loadFloat(12, f);
        remoteContext.loadFloat(11, value2);
        remoteContext.loadFloat(28, CoreDocument.getDocumentApiLevel() + 0.0f);
    }

    public void updateTime(RemoteContext remoteContext) {
        ZoneId systemDefault = ZoneId.systemDefault();
        updateTime(remoteContext, systemDefault, LocalDateTime.now(systemDefault));
    }
}
