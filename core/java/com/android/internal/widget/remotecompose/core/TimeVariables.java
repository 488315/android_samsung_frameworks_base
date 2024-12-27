package com.android.internal.widget.remotecompose.core;

import java.time.LocalDateTime;

/* loaded from: classes5.dex */
public class TimeVariables {
    public void updateTime(RemoteContext context) {
        LocalDateTime dateTime = LocalDateTime.now();
        int month = dateTime.getDayOfMonth();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        int seconds = dateTime.getSecond();
        int currentMinute = (hour * 60) + minute;
        int currentSeconds = (minute * 60) + seconds;
        float sec = currentSeconds + (dateTime.getNano() * 1.0E-9f);
        context.loadFloat(1, sec);
        context.loadFloat(2, currentSeconds);
        context.loadFloat(3, currentMinute);
        context.loadFloat(4, hour);
        context.loadFloat(9, month);
    }
}
