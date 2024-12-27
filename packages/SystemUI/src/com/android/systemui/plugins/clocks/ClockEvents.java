package com.android.systemui.plugins.clocks;

import android.content.res.Resources;
import java.util.Locale;
import java.util.TimeZone;

public interface ClockEvents {
    boolean isReactiveTouchInteractionEnabled();

    void onAlarmDataChanged(AlarmData alarmData);

    void onColorPaletteChanged(Resources resources);

    void onLocaleChanged(Locale locale);

    void onSeedColorChanged(Integer num);

    void onTimeFormatChanged(boolean z);

    void onTimeZoneChanged(TimeZone timeZone);

    void onWeatherDataChanged(WeatherData weatherData);

    void onZenDataChanged(ZenData zenData);

    void setReactiveTouchInteractionEnabled(boolean z);
}
