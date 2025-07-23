package com.android.systemui.plugins.clocks;

import java.util.Locale;
import java.util.TimeZone;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface ClockEvents {
    boolean isReactiveTouchInteractionEnabled();

    void onAlarmDataChanged(AlarmData alarmData);

    void onLocaleChanged(Locale locale);

    void onTimeFormatChanged(boolean z);

    void onTimeZoneChanged(TimeZone timeZone);

    void onWeatherDataChanged(WeatherData weatherData);

    void onZenDataChanged(ZenData zenData);

    void setReactiveTouchInteractionEnabled(boolean z);
}
