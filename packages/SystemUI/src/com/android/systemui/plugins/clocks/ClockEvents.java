package com.android.systemui.plugins.clocks;

import android.content.res.Resources;
import java.util.Locale;
import java.util.TimeZone;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
