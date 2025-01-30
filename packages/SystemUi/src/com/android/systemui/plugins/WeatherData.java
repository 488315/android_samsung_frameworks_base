package com.android.systemui.plugins;

import android.os.Bundle;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.sec.ims.settings.ImsProfile;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WeatherData {
    public static final Companion Companion = new Companion(null);
    public static final boolean DEBUG = true;
    public static final String DESCRIPTION_KEY = "description";
    private static final int INVALID_WEATHER_ICON_STATE = -1;
    public static final String STATE_KEY = "state";
    private static final String TAG = "WeatherData";
    public static final String TEMPERATURE_KEY = "temperature";
    public static final String USE_CELSIUS_KEY = "use_celsius";
    private final String description;
    private final WeatherStateIcon state;
    private final int temperature;
    private final boolean useCelsius;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum WeatherStateIcon {
        UNKNOWN_ICON(0),
        SUNNY(1),
        CLEAR_NIGHT(2),
        MOSTLY_SUNNY(3),
        MOSTLY_CLEAR_NIGHT(4),
        PARTLY_CLOUDY(5),
        PARTLY_CLOUDY_NIGHT(6),
        MOSTLY_CLOUDY_DAY(7),
        MOSTLY_CLOUDY_NIGHT(8),
        CLOUDY(9),
        HAZE_FOG_DUST_SMOKE(10),
        DRIZZLE(11),
        HEAVY_RAIN(12),
        SHOWERS_RAIN(13),
        SCATTERED_SHOWERS_DAY(14),
        SCATTERED_SHOWERS_NIGHT(15),
        ISOLATED_SCATTERED_TSTORMS_DAY(16),
        ISOLATED_SCATTERED_TSTORMS_NIGHT(17),
        STRONG_TSTORMS(18),
        BLIZZARD(19),
        BLOWING_SNOW(20),
        FLURRIES(21),
        HEAVY_SNOW(22),
        SCATTERED_SNOW_SHOWERS_DAY(23),
        SCATTERED_SNOW_SHOWERS_NIGHT(24),
        SNOW_SHOWERS_SNOW(25),
        MIXED_RAIN_HAIL_RAIN_SLEET(26),
        SLEET_HAIL(27),
        TORNADO(28),
        TROPICAL_STORM_HURRICANE(29),
        WINDY_BREEZY(30),
        WINTRY_MIX_RAIN_SNOW(31);

        public static final Companion Companion = new Companion(null);

        /* renamed from: id */
        private final int f329id;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public final WeatherStateIcon fromInt(int i) {
                for (WeatherStateIcon weatherStateIcon : WeatherStateIcon.values()) {
                    if (weatherStateIcon.getId() == i) {
                        return weatherStateIcon;
                    }
                }
                return null;
            }
        }

        WeatherStateIcon(int i) {
            this.f329id = i;
        }

        public final int getId() {
            return this.f329id;
        }
    }

    public WeatherData(String str, WeatherStateIcon weatherStateIcon, boolean z, int i) {
        this.description = str;
        this.state = weatherStateIcon;
        this.useCelsius = z;
        this.temperature = i;
    }

    public final String getDescription() {
        return this.description;
    }

    public final WeatherStateIcon getState() {
        return this.state;
    }

    public final int getTemperature() {
        return this.temperature;
    }

    public final boolean getUseCelsius() {
        return this.useCelsius;
    }

    public String toString() {
        String str = this.useCelsius ? ImsProfile.TIMER_NAME_C : ImsProfile.TIMER_NAME_F;
        WeatherStateIcon weatherStateIcon = this.state;
        String str2 = this.description;
        int i = this.temperature;
        StringBuilder sb = new StringBuilder();
        sb.append(weatherStateIcon);
        sb.append(" (\"");
        sb.append(str2);
        sb.append("\") ");
        sb.append(i);
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, "°", str);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final Integer readIntFromBundle(Bundle bundle, String str) {
            try {
                return Integer.valueOf(Integer.parseInt(bundle.getString(str)));
            } catch (Exception unused) {
                return null;
            }
        }

        public final WeatherData fromBundle(Bundle bundle) {
            String string = bundle.getString("description");
            WeatherStateIcon fromInt = WeatherStateIcon.Companion.fromInt(bundle.getInt("state", -1));
            Integer readIntFromBundle = readIntFromBundle(bundle, WeatherData.TEMPERATURE_KEY);
            if (string == null || fromInt == null || !bundle.containsKey(WeatherData.USE_CELSIUS_KEY) || readIntFromBundle == null) {
                Log.w(WeatherData.TAG, "Weather data did not parse from " + bundle);
                return null;
            }
            WeatherData weatherData = new WeatherData(string, fromInt, bundle.getBoolean(WeatherData.USE_CELSIUS_KEY), readIntFromBundle.intValue());
            Log.i(WeatherData.TAG, "Weather data parsed " + weatherData + " from " + bundle);
            return weatherData;
        }

        public static /* synthetic */ void getDESCRIPTION_KEY$annotations() {
        }

        public static /* synthetic */ void getSTATE_KEY$annotations() {
        }

        public static /* synthetic */ void getTEMPERATURE_KEY$annotations() {
        }

        public static /* synthetic */ void getUSE_CELSIUS_KEY$annotations() {
        }
    }
}
