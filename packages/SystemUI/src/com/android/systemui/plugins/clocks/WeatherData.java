package com.android.systemui.plugins.clocks;

import android.icu.number.NumberFormatter;
import android.icu.number.UnlocalizedNumberFormatter;
import android.icu.util.MeasureUnit;
import android.os.Bundle;
import android.util.Log;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.core.os.BuildCompat;
import com.sec.ims.settings.ImsProfile;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Locale;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class WeatherData {
    public static final int $stable = 0;
    public static final boolean DEBUG = true;
    public static final String DESCRIPTION_KEY = "description";
    private static final String DESCRIPTION_PLACEHODLER = "";
    private static final int INVALID_WEATHER_ICON_STATE = -1;
    public static final String STATE_KEY = "state";
    private static final String TAG = "WeatherData";
    private static final int TEMPERATURE_CELSIUS_PLACEHOLDER = 21;
    private static final int TEMPERATURE_FAHRENHEIT_PLACEHOLDER = 58;
    public static final String TEMPERATURE_KEY = "temperature";
    public static final String USE_CELSIUS_KEY = "use_celsius";
    private final String description;
    private final WeatherStateIcon state;
    private final int temperature;
    private final Function1 touchAction;
    private final boolean useCelsius;
    public static final Companion Companion = new Companion(null);
    private static final WeatherStateIcon WEATHERICON_PLACEHOLDER = WeatherStateIcon.MOSTLY_SUNNY;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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

        public final WeatherData fromBundle(Bundle bundle) {
            return fromBundle$default(this, bundle, null, 2, null);
        }

        public final WeatherData getPlaceholderWeatherData(boolean z) {
            return new WeatherData("", WeatherData.WEATHERICON_PLACEHOLDER, z, z ? 21 : 58, null, 16, null);
        }

        private Companion() {
        }

        public final WeatherData fromBundle(Bundle bundle, Function1 function1) {
            String string = bundle.getString("description");
            WeatherStateIcon weatherStateIconFromInt = WeatherStateIcon.Companion.fromInt(bundle.getInt("state", -1));
            Integer intFromBundle = readIntFromBundle(bundle, WeatherData.TEMPERATURE_KEY);
            if (string == null || weatherStateIconFromInt == null || !bundle.containsKey(WeatherData.USE_CELSIUS_KEY) || intFromBundle == null) {
                Log.w(WeatherData.TAG, "Weather data did not parse from " + bundle);
                return null;
            }
            WeatherData weatherData = new WeatherData(string, weatherStateIconFromInt, bundle.getBoolean(WeatherData.USE_CELSIUS_KEY), intFromBundle.intValue(), function1);
            Log.i(WeatherData.TAG, "Weather data parsed " + weatherData + " from " + bundle);
            return weatherData;
        }

        public final WeatherData getPlaceholderWeatherData() {
            Locale locale = Locale.getDefault(Locale.Category.FORMAT);
            String unicodeLocaleType = locale.getUnicodeLocaleType("mu");
            if (unicodeLocaleType == null) {
                unicodeLocaleType = null;
            }
            if (unicodeLocaleType == null) {
                int i = BuildCompat.$r8$clinit;
                unicodeLocaleType = ((UnlocalizedNumberFormatter) ((UnlocalizedNumberFormatter) NumberFormatter.with().usage("weather")).unit(MeasureUnit.CELSIUS)).locale(locale).format(1L).getOutputUnit().getIdentifier();
                if (unicodeLocaleType.startsWith("fahrenhe")) {
                    unicodeLocaleType = "fahrenhe";
                }
            }
            return getPlaceholderWeatherData(unicodeLocaleType.equals("celsius"));
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

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class WeatherStateIcon {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ WeatherStateIcon[] $VALUES;
        public static final Companion Companion;
        private final String icon;
        private final int id;
        public static final WeatherStateIcon UNKNOWN_ICON = new WeatherStateIcon("UNKNOWN_ICON", 0, 0, "");
        public static final WeatherStateIcon SUNNY = new WeatherStateIcon("SUNNY", 1, 1, "a");
        public static final WeatherStateIcon CLEAR_NIGHT = new WeatherStateIcon("CLEAR_NIGHT", 2, 2, "f");
        public static final WeatherStateIcon MOSTLY_SUNNY = new WeatherStateIcon("MOSTLY_SUNNY", 3, 3, "b");
        public static final WeatherStateIcon MOSTLY_CLEAR_NIGHT = new WeatherStateIcon("MOSTLY_CLEAR_NIGHT", 4, 4, "n");
        public static final WeatherStateIcon PARTLY_CLOUDY = new WeatherStateIcon("PARTLY_CLOUDY", 5, 5, "b");
        public static final WeatherStateIcon PARTLY_CLOUDY_NIGHT = new WeatherStateIcon("PARTLY_CLOUDY_NIGHT", 6, 6, "n");
        public static final WeatherStateIcon MOSTLY_CLOUDY_DAY = new WeatherStateIcon("MOSTLY_CLOUDY_DAY", 7, 7, "e");
        public static final WeatherStateIcon MOSTLY_CLOUDY_NIGHT = new WeatherStateIcon("MOSTLY_CLOUDY_NIGHT", 8, 8, "e");
        public static final WeatherStateIcon CLOUDY = new WeatherStateIcon("CLOUDY", 9, 9, "e");
        public static final WeatherStateIcon HAZE_FOG_DUST_SMOKE = new WeatherStateIcon("HAZE_FOG_DUST_SMOKE", 10, 10, "d");
        public static final WeatherStateIcon DRIZZLE = new WeatherStateIcon("DRIZZLE", 11, 11, "c");
        public static final WeatherStateIcon HEAVY_RAIN = new WeatherStateIcon("HEAVY_RAIN", 12, 12, "c");
        public static final WeatherStateIcon SHOWERS_RAIN = new WeatherStateIcon("SHOWERS_RAIN", 13, 13, "c");
        public static final WeatherStateIcon SCATTERED_SHOWERS_DAY = new WeatherStateIcon("SCATTERED_SHOWERS_DAY", 14, 14, "c");
        public static final WeatherStateIcon SCATTERED_SHOWERS_NIGHT = new WeatherStateIcon("SCATTERED_SHOWERS_NIGHT", 15, 15, "c");
        public static final WeatherStateIcon ISOLATED_SCATTERED_TSTORMS_DAY = new WeatherStateIcon("ISOLATED_SCATTERED_TSTORMS_DAY", 16, 16, "i");
        public static final WeatherStateIcon ISOLATED_SCATTERED_TSTORMS_NIGHT = new WeatherStateIcon("ISOLATED_SCATTERED_TSTORMS_NIGHT", 17, 17, "i");
        public static final WeatherStateIcon STRONG_TSTORMS = new WeatherStateIcon("STRONG_TSTORMS", 18, 18, "i");
        public static final WeatherStateIcon BLIZZARD = new WeatherStateIcon("BLIZZARD", 19, 19, "j");
        public static final WeatherStateIcon BLOWING_SNOW = new WeatherStateIcon("BLOWING_SNOW", 20, 20, "j");
        public static final WeatherStateIcon FLURRIES = new WeatherStateIcon("FLURRIES", 21, 21, "h");
        public static final WeatherStateIcon HEAVY_SNOW = new WeatherStateIcon("HEAVY_SNOW", 22, 22, "j");
        public static final WeatherStateIcon SCATTERED_SNOW_SHOWERS_DAY = new WeatherStateIcon("SCATTERED_SNOW_SHOWERS_DAY", 23, 23, "h");
        public static final WeatherStateIcon SCATTERED_SNOW_SHOWERS_NIGHT = new WeatherStateIcon("SCATTERED_SNOW_SHOWERS_NIGHT", 24, 24, "h");
        public static final WeatherStateIcon SNOW_SHOWERS_SNOW = new WeatherStateIcon("SNOW_SHOWERS_SNOW", 25, 25, "g");
        public static final WeatherStateIcon MIXED_RAIN_HAIL_RAIN_SLEET = new WeatherStateIcon("MIXED_RAIN_HAIL_RAIN_SLEET", 26, 26, "h");
        public static final WeatherStateIcon SLEET_HAIL = new WeatherStateIcon("SLEET_HAIL", 27, 27, "h");
        public static final WeatherStateIcon TORNADO = new WeatherStateIcon("TORNADO", 28, 28, "l");
        public static final WeatherStateIcon TROPICAL_STORM_HURRICANE = new WeatherStateIcon("TROPICAL_STORM_HURRICANE", 29, 29, "m");
        public static final WeatherStateIcon WINDY_BREEZY = new WeatherStateIcon("WINDY_BREEZY", 30, 30, "k");
        public static final WeatherStateIcon WINTRY_MIX_RAIN_SNOW = new WeatherStateIcon("WINTRY_MIX_RAIN_SNOW", 31, 31, "h");

        public final class Companion {
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

            private Companion() {
            }
        }

        private static final /* synthetic */ WeatherStateIcon[] $values() {
            return new WeatherStateIcon[]{UNKNOWN_ICON, SUNNY, CLEAR_NIGHT, MOSTLY_SUNNY, MOSTLY_CLEAR_NIGHT, PARTLY_CLOUDY, PARTLY_CLOUDY_NIGHT, MOSTLY_CLOUDY_DAY, MOSTLY_CLOUDY_NIGHT, CLOUDY, HAZE_FOG_DUST_SMOKE, DRIZZLE, HEAVY_RAIN, SHOWERS_RAIN, SCATTERED_SHOWERS_DAY, SCATTERED_SHOWERS_NIGHT, ISOLATED_SCATTERED_TSTORMS_DAY, ISOLATED_SCATTERED_TSTORMS_NIGHT, STRONG_TSTORMS, BLIZZARD, BLOWING_SNOW, FLURRIES, HEAVY_SNOW, SCATTERED_SNOW_SHOWERS_DAY, SCATTERED_SNOW_SHOWERS_NIGHT, SNOW_SHOWERS_SNOW, MIXED_RAIN_HAIL_RAIN_SLEET, SLEET_HAIL, TORNADO, TROPICAL_STORM_HURRICANE, WINDY_BREEZY, WINTRY_MIX_RAIN_SNOW};
        }

        static {
            WeatherStateIcon[] weatherStateIconArr$values = $values();
            $VALUES = weatherStateIconArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(weatherStateIconArr$values);
            Companion = new Companion(null);
        }

        private WeatherStateIcon(String str, int i, int i2, String str2) {
            this.id = i2;
            this.icon = str2;
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

        public final String getIcon() {
            return this.icon;
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
        if ((i2 & 4) != 0) {
            z = weatherData.useCelsius;
        }
        if ((i2 & 8) != 0) {
            i = weatherData.temperature;
        }
        if ((i2 & 16) != 0) {
            function1 = weatherData.touchAction;
        }
        Function1 function12 = function1;
        boolean z2 = z;
        return weatherData.copy(str, weatherStateIcon, z2, i, function12);
    }

    public static final WeatherData fromBundle(Bundle bundle) {
        return Companion.fromBundle(bundle);
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
        int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.temperature, TransitionData$$ExternalSyntheticOutline0.m((this.state.hashCode() + (this.description.hashCode() * 31)) * 31, 31, this.useCelsius), 31);
        Function1 function1 = this.touchAction;
        return iM + (function1 == null ? 0 : function1.hashCode());
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
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, "°", str);
    }

    public static final WeatherData fromBundle(Bundle bundle, Function1 function1) {
        return Companion.fromBundle(bundle, function1);
    }

    public /* synthetic */ WeatherData(String str, WeatherStateIcon weatherStateIcon, boolean z, int i, Function1 function1, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, weatherStateIcon, z, i, (i2 & 16) != 0 ? null : function1);
    }
}
