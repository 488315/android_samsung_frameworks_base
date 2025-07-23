package com.android.systemui.plugins.clocks;

import android.util.Log;
import com.android.systemui.plugins.PluginWrapper;
import com.android.systemui.plugins.ProtectedPluginListener;
import java.util.Locale;
import java.util.TimeZone;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ClockEventsProtector implements ClockEvents, PluginWrapper<ClockEvents> {
    private static final String CLASS = "ClockEvents";
    private boolean mHasError = false;
    private ClockEvents mInstance;
    private ProtectedPluginListener mListener;

    private ClockEventsProtector(ClockEvents clockEvents, ProtectedPluginListener protectedPluginListener) {
        this.mInstance = clockEvents;
        this.mListener = protectedPluginListener;
    }

    public static ClockEventsProtector protect(ClockEvents clockEvents, ProtectedPluginListener protectedPluginListener) {
        return clockEvents instanceof ClockEventsProtector ? (ClockEventsProtector) clockEvents : new ClockEventsProtector(clockEvents, protectedPluginListener);
    }

    @Override // com.android.systemui.plugins.clocks.ClockEvents
    public boolean isReactiveTouchInteractionEnabled() {
        if (this.mHasError) {
            return false;
        }
        try {
            return this.mInstance.isReactiveTouchInteractionEnabled();
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: isReactiveTouchInteractionEnabled", e);
            this.mHasError = this.mListener.onFail(CLASS, "isReactiveTouchInteractionEnabled", e);
            return false;
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: isReactiveTouchInteractionEnabled", e2);
            this.mHasError = this.mListener.onFail(CLASS, "isReactiveTouchInteractionEnabled", e2);
            return false;
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockEvents
    public void onAlarmDataChanged(AlarmData alarmData) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.onAlarmDataChanged(alarmData);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: onAlarmDataChanged", e);
            this.mHasError = this.mListener.onFail(CLASS, "onAlarmDataChanged", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: onAlarmDataChanged", e2);
            this.mHasError = this.mListener.onFail(CLASS, "onAlarmDataChanged", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockEvents
    public void onLocaleChanged(Locale locale) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.onLocaleChanged(locale);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: onLocaleChanged", e);
            this.mHasError = this.mListener.onFail(CLASS, "onLocaleChanged", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: onLocaleChanged", e2);
            this.mHasError = this.mListener.onFail(CLASS, "onLocaleChanged", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockEvents
    public void onTimeFormatChanged(boolean z) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.onTimeFormatChanged(z);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: onTimeFormatChanged", e);
            this.mHasError = this.mListener.onFail(CLASS, "onTimeFormatChanged", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: onTimeFormatChanged", e2);
            this.mHasError = this.mListener.onFail(CLASS, "onTimeFormatChanged", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockEvents
    public void onTimeZoneChanged(TimeZone timeZone) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.onTimeZoneChanged(timeZone);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: onTimeZoneChanged", e);
            this.mHasError = this.mListener.onFail(CLASS, "onTimeZoneChanged", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: onTimeZoneChanged", e2);
            this.mHasError = this.mListener.onFail(CLASS, "onTimeZoneChanged", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockEvents
    public void onWeatherDataChanged(WeatherData weatherData) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.onWeatherDataChanged(weatherData);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: onWeatherDataChanged", e);
            this.mHasError = this.mListener.onFail(CLASS, "onWeatherDataChanged", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: onWeatherDataChanged", e2);
            this.mHasError = this.mListener.onFail(CLASS, "onWeatherDataChanged", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockEvents
    public void onZenDataChanged(ZenData zenData) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.onZenDataChanged(zenData);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: onZenDataChanged", e);
            this.mHasError = this.mListener.onFail(CLASS, "onZenDataChanged", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: onZenDataChanged", e2);
            this.mHasError = this.mListener.onFail(CLASS, "onZenDataChanged", e2);
        }
    }

    @Override // com.android.systemui.plugins.clocks.ClockEvents
    public void setReactiveTouchInteractionEnabled(boolean z) {
        if (this.mHasError) {
            return;
        }
        try {
            this.mInstance.setReactiveTouchInteractionEnabled(z);
        } catch (Exception e) {
            Log.wtf(CLASS, "Failed to execute: setReactiveTouchInteractionEnabled", e);
            this.mHasError = this.mListener.onFail(CLASS, "setReactiveTouchInteractionEnabled", e);
        } catch (LinkageError e2) {
            Log.wtf(CLASS, "Failed to execute: setReactiveTouchInteractionEnabled", e2);
            this.mHasError = this.mListener.onFail(CLASS, "setReactiveTouchInteractionEnabled", e2);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.plugins.PluginWrapper
    public ClockEvents getPlugin() {
        return this.mInstance;
    }
}
