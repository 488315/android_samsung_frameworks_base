package com.android.systemui.plugins.clocks;

import java.util.Locale;
import java.util.TimeZone;

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
