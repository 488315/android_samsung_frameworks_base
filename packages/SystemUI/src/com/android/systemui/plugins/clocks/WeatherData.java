package com.android.systemui.plugins.clocks;

import android.os.Bundle;
import android.util.Log;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import com.sec.ims.settings.ImsProfile;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
    private final Function1 touchAction;
    private final boolean useCelsius;

    public final class Companion {
        private Companion() {
        }

        public static /* synthetic */ WeatherData fromBundle$default(Companion companion, Bundle bundle, Function1 function1, int i, Object obj) {
            if ((i & 2) != 0) {
                function1 = null;
            }
            return companion.fromBundle(bundle, function1);
        }

        private final Integer readIntFromBundle(Bundle bundle, String str) {
            try {
                String string = bundle.getString(str);
                if (string != null) {
                    return Integer.valueOf(Integer.parseInt(string));
                }
                return null;
            } catch (Exception unused) {
                return null;
            }
        }

        public final WeatherData fromBundle(Bundle bundle, Function1 function1) {
            String string = bundle.getString("description");
            WeatherStateIcon fromInt = WeatherStateIcon.Companion.fromInt(bundle.getInt("state", -1));
            Integer readIntFromBundle = readIntFromBundle(bundle, WeatherData.TEMPERATURE_KEY);
            if (string == null || fromInt == null || !bundle.containsKey(WeatherData.USE_CELSIUS_KEY) || readIntFromBundle == null) {
                Log.w(WeatherData.TAG, "Weather data did not parse from " + bundle);
                return null;
            }
            WeatherData weatherData = new WeatherData(string, fromInt, bundle.getBoolean(WeatherData.USE_CELSIUS_KEY), readIntFromBundle.intValue(), function1);
            Log.i(WeatherData.TAG, "Weather data parsed " + weatherData + " from " + bundle);
            return weatherData;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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

    public final class WeatherStateIcon {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ WeatherStateIcon[] $VALUES;
        public static final Companion Companion;
        private final int id;
        public static final WeatherStateIcon UNKNOWN_ICON = new WeatherStateIcon("UNKNOWN_ICON", 0, 0);
        public static final WeatherStateIcon SUNNY = new WeatherStateIcon("SUNNY", 1, 1);
        public static final WeatherStateIcon CLEAR_NIGHT = new WeatherStateIcon("CLEAR_NIGHT", 2, 2);
        public static final WeatherStateIcon MOSTLY_SUNNY = new WeatherStateIcon("MOSTLY_SUNNY", 3, 3);
        public static final WeatherStateIcon MOSTLY_CLEAR_NIGHT = new WeatherStateIcon("MOSTLY_CLEAR_NIGHT", 4, 4);
        public static final WeatherStateIcon PARTLY_CLOUDY = new WeatherStateIcon("PARTLY_CLOUDY", 5, 5);
        public static final WeatherStateIcon PARTLY_CLOUDY_NIGHT = new WeatherStateIcon("PARTLY_CLOUDY_NIGHT", 6, 6);
        public static final WeatherStateIcon MOSTLY_CLOUDY_DAY = new WeatherStateIcon("MOSTLY_CLOUDY_DAY", 7, 7);
        public static final WeatherStateIcon MOSTLY_CLOUDY_NIGHT = new WeatherStateIcon("MOSTLY_CLOUDY_NIGHT", 8, 8);
        public static final WeatherStateIcon CLOUDY = new WeatherStateIcon("CLOUDY", 9, 9);
        public static final WeatherStateIcon HAZE_FOG_DUST_SMOKE = new WeatherStateIcon("HAZE_FOG_DUST_SMOKE", 10, 10);
        public static final WeatherStateIcon DRIZZLE = new WeatherStateIcon("DRIZZLE", 11, 11);
        public static final WeatherStateIcon HEAVY_RAIN = new WeatherStateIcon("HEAVY_RAIN", 12, 12);
        public static final WeatherStateIcon SHOWERS_RAIN = new WeatherStateIcon("SHOWERS_RAIN", 13, 13);
        public static final WeatherStateIcon SCATTERED_SHOWERS_DAY = new WeatherStateIcon("SCATTERED_SHOWERS_DAY", 14, 14);
        public static final WeatherStateIcon SCATTERED_SHOWERS_NIGHT = new WeatherStateIcon("SCATTERED_SHOWERS_NIGHT", 15, 15);
        public static final WeatherStateIcon ISOLATED_SCATTERED_TSTORMS_DAY = new WeatherStateIcon("ISOLATED_SCATTERED_TSTORMS_DAY", 16, 16);
        public static final WeatherStateIcon ISOLATED_SCATTERED_TSTORMS_NIGHT = new WeatherStateIcon("ISOLATED_SCATTERED_TSTORMS_NIGHT", 17, 17);
        public static final WeatherStateIcon STRONG_TSTORMS = new WeatherStateIcon("STRONG_TSTORMS", 18, 18);
        public static final WeatherStateIcon BLIZZARD = new WeatherStateIcon("BLIZZARD", 19, 19);
        public static final WeatherStateIcon BLOWING_SNOW = new WeatherStateIcon("BLOWING_SNOW", 20, 20);
        public static final WeatherStateIcon FLURRIES = new WeatherStateIcon("FLURRIES", 21, 21);
        public static final WeatherStateIcon HEAVY_SNOW = new WeatherStateIcon("HEAVY_SNOW", 22, 22);
        public static final WeatherStateIcon SCATTERED_SNOW_SHOWERS_DAY = new WeatherStateIcon("SCATTERED_SNOW_SHOWERS_DAY", 23, 23);
        public static final WeatherStateIcon SCATTERED_SNOW_SHOWERS_NIGHT = new WeatherStateIcon("SCATTERED_SNOW_SHOWERS_NIGHT", 24, 24);
        public static final WeatherStateIcon SNOW_SHOWERS_SNOW = new WeatherStateIcon("SNOW_SHOWERS_SNOW", 25, 25);
        public static final WeatherStateIcon MIXED_RAIN_HAIL_RAIN_SLEET = new WeatherStateIcon("MIXED_RAIN_HAIL_RAIN_SLEET", 26, 26);
        public static final WeatherStateIcon SLEET_HAIL = new WeatherStateIcon("SLEET_HAIL", 27, 27);
        public static final WeatherStateIcon TORNADO = new WeatherStateIcon("TORNADO", 28, 28);
        public static final WeatherStateIcon TROPICAL_STORM_HURRICANE = new WeatherStateIcon("TROPICAL_STORM_HURRICANE", 29, 29);
        public static final WeatherStateIcon WINDY_BREEZY = new WeatherStateIcon("WINDY_BREEZY", 30, 30);
        public static final WeatherStateIcon WINTRY_MIX_RAIN_SNOW = new WeatherStateIcon("WINTRY_MIX_RAIN_SNOW", 31, 31);

        public final class Companion {
            private Companion() {
            }

            public final WeatherStateIcon fromInt(int i) {
                for (WeatherStateIcon weatherStateIcon : WeatherStateIcon.values()) {
                    if (weatherStateIcon.getId() == i) {
                        return weatherStateIcon;
                    }
                }
                return null;
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        private static final /* synthetic */ WeatherStateIcon[] $values() {
            return new WeatherStateIcon[]{UNKNOWN_ICON, SUNNY, CLEAR_NIGHT, MOSTLY_SUNNY, MOSTLY_CLEAR_NIGHT, PARTLY_CLOUDY, PARTLY_CLOUDY_NIGHT, MOSTLY_CLOUDY_DAY, MOSTLY_CLOUDY_NIGHT, CLOUDY, HAZE_FOG_DUST_SMOKE, DRIZZLE, HEAVY_RAIN, SHOWERS_RAIN, SCATTERED_SHOWERS_DAY, SCATTERED_SHOWERS_NIGHT, ISOLATED_SCATTERED_TSTORMS_DAY, ISOLATED_SCATTERED_TSTORMS_NIGHT, STRONG_TSTORMS, BLIZZARD, BLOWING_SNOW, FLURRIES, HEAVY_SNOW, SCATTERED_SNOW_SHOWERS_DAY, SCATTERED_SNOW_SHOWERS_NIGHT, SNOW_SHOWERS_SNOW, MIXED_RAIN_HAIL_RAIN_SLEET, SLEET_HAIL, TORNADO, TROPICAL_STORM_HURRICANE, WINDY_BREEZY, WINTRY_MIX_RAIN_SNOW};
        }

        static {
            WeatherStateIcon[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
            Companion = new Companion(null);
        }

        private WeatherStateIcon(String str, int i, int i2) {
            this.id = i2;
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static WeatherStateIcon valueOf(String str) {
            return (WeatherStateIcon) Enum.valueOf(WeatherStateIcon.class, str);
        }

        public static WeatherStateIcon[] values() {
            return (WeatherStateIcon[]) $VALUES.clone();
        }

        public final int getId() {
            return this.id;
        }
    }

    public WeatherData(String str, WeatherStateIcon weatherStateIcon, boolean z, int i, Function1 function1) {
        this.description = str;
        this.state = weatherStateIcon;
        this.useCelsius = z;
        this.temperature = i;
        this.touchAction = function1;
    }

    public static /* synthetic */ WeatherData copy$default(WeatherData weatherData, String str, WeatherStateIcon weatherStateIcon, boolean z, int i, Function1 function1, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = weatherData.description;
        }
        if ((i2 & 2) != 0) {
            weatherStateIcon = weatherData.state;
        }
        WeatherStateIcon weatherStateIcon2 = weatherStateIcon;
        if ((i2 & 4) != 0) {
            z = weatherData.useCelsius;
        }
        boolean z2 = z;
        if ((i2 & 8) != 0) {
            i = weatherData.temperature;
        }
        int i3 = i;
        if ((i2 & 16) != 0) {
            function1 = weatherData.touchAction;
        }
        return weatherData.copy(str, weatherStateIcon2, z2, i3, function1);
    }

    public final String component1() {
        return this.description;
    }

    public final WeatherStateIcon component2() {
        return this.state;
    }

    public final boolean component3() {
        return this.useCelsius;
    }

    public final int component4() {
        return this.temperature;
    }

    public final Function1 component5() {
        return this.touchAction;
    }

    public final WeatherData copy(String str, WeatherStateIcon weatherStateIcon, boolean z, int i, Function1 function1) {
        return new WeatherData(str, weatherStateIcon, z, i, function1);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WeatherData)) {
            return false;
        }
        WeatherData weatherData = (WeatherData) obj;
        return Intrinsics.areEqual(this.description, weatherData.description) && this.state == weatherData.state && this.useCelsius == weatherData.useCelsius && this.temperature == weatherData.temperature && Intrinsics.areEqual(this.touchAction, weatherData.touchAction);
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

    public final Function1 getTouchAction() {
        return this.touchAction;
    }

    public final boolean getUseCelsius() {
        return this.useCelsius;
    }

    public int hashCode() {
        int m = KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.temperature, TransitionData$$ExternalSyntheticOutline0.m((this.state.hashCode() + (this.description.hashCode() * 31)) * 31, 31, this.useCelsius), 31);
        Function1 function1 = this.touchAction;
        return m + (function1 == null ? 0 : function1.hashCode());
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
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, "°", str);
    }

    public /* synthetic */ WeatherData(String str, WeatherStateIcon weatherStateIcon, boolean z, int i, Function1 function1, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, weatherStateIcon, z, i, (i2 & 16) != 0 ? null : function1);
    }
}
