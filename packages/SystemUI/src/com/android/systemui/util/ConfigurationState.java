package com.android.systemui.util;

import android.content.res.Configuration;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.util.ConfigurationState;
import com.samsung.android.knox.accounts.HostAuth;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class ConfigurationState {
    public static final int DEFAULT_VALUE = -100;
    private final EnumMap<ConfigurationField, Integer> fieldMap;
    private final List<ConfigurationField> fields;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public abstract class ConfigurationField {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ ConfigurationField[] $VALUES;
        public static final ConfigurationField ASSET_SEQ = new ASSET_SEQ("ASSET_SEQ", 0);
        public static final ConfigurationField DISPLAY_DEVICE_TYPE = new DISPLAY_DEVICE_TYPE("DISPLAY_DEVICE_TYPE", 1);
        public static final ConfigurationField LOCALE = new LOCALE("LOCALE", 2);
        public static final ConfigurationField ORIENTATION = new ORIENTATION("ORIENTATION", 3);
        public static final ConfigurationField SCREEN_HEIGHT_DP = new SCREEN_HEIGHT_DP("SCREEN_HEIGHT_DP", 4);
        public static final ConfigurationField SCREEN_LAYOUT = new SCREEN_LAYOUT("SCREEN_LAYOUT", 5);
        public static final ConfigurationField THEME_SEQ = new THEME_SEQ("THEME_SEQ", 6);
        public static final ConfigurationField UI_MODE = new UI_MODE("UI_MODE", 7);
        public static final ConfigurationField DENSITY_DPI = new DENSITY_DPI("DENSITY_DPI", 8);

        final class ASSET_SEQ extends ConfigurationField {
            public ASSET_SEQ(String str, int i) {
                super(str, i, null);
            }

            private final String convertToString(int i) {
                return i == -100 ? "def" : String.valueOf(i);
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public boolean needToUpdate(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                Integer num = enumMap.get(this);
                return num == null || num.intValue() != configuration.assetsSeq;
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public String toCompareString(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                Integer num = enumMap.get(this);
                return getSummaryString("assetSeq", num != null ? convertToString(num.intValue()) : null, convertToString(configuration.assetsSeq));
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public void update(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                enumMap.put((EnumMap<ConfigurationField, Integer>) this, (ASSET_SEQ) Integer.valueOf(configuration.assetsSeq));
            }
        }

        final class DENSITY_DPI extends ConfigurationField {
            public DENSITY_DPI(String str, int i) {
                super(str, i, null);
            }

            private final String convertToString(int i) {
                if (i == -100) {
                    return "def";
                }
                if (i == 0) {
                    return "undef";
                }
                return i + "dpi";
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public boolean needToUpdate(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                Integer num = enumMap.get(this);
                return num == null || num.intValue() != configuration.densityDpi;
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public String toCompareString(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                Integer num = enumMap.get(this);
                return getSummaryString("densityDpi", num != null ? convertToString(num.intValue()) : null, convertToString(configuration.densityDpi));
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public void update(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                enumMap.put((EnumMap<ConfigurationField, Integer>) this, (DENSITY_DPI) Integer.valueOf(configuration.densityDpi));
            }
        }

        final class DISPLAY_DEVICE_TYPE extends ConfigurationField {
            public DISPLAY_DEVICE_TYPE(String str, int i) {
                super(str, i, null);
            }

            private final String convertToString(int i) {
                return i != -100 ? i != -1 ? i != 0 ? i != 4 ? i != 5 ? String.valueOf(i) : "sub" : "dual" : "main" : "undef" : "def";
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public boolean needToUpdate(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                Integer num = enumMap.get(this);
                return num == null || num.intValue() != configuration.semDisplayDeviceType;
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public String toCompareString(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                Integer num = enumMap.get(this);
                return getSummaryString("displayDeviceType", num != null ? convertToString(num.intValue()) : null, convertToString(configuration.semDisplayDeviceType));
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public void update(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                enumMap.put((EnumMap<ConfigurationField, Integer>) this, (DISPLAY_DEVICE_TYPE) Integer.valueOf(configuration.semDisplayDeviceType));
            }
        }

        final class LOCALE extends ConfigurationField {
            public LOCALE(String str, int i) {
                super(str, i, null);
            }

            private final String convertToString(int i) {
                return i == -100 ? "def" : String.valueOf(i);
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public boolean needToUpdate(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                Integer num = enumMap.get(this);
                return num == null || num.intValue() != configuration.getLocales().indexOf(configuration.getLocales().get(0));
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public String toCompareString(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                Integer num = enumMap.get(this);
                return getSummaryString("localeIndex", num != null ? convertToString(num.intValue()) : null, convertToString(configuration.getLocales().indexOf(configuration.getLocales().get(0))));
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public void update(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                enumMap.put((EnumMap<ConfigurationField, Integer>) this, (LOCALE) Integer.valueOf(configuration.getLocales().indexOf(configuration.getLocales().get(0))));
            }
        }

        final class ORIENTATION extends ConfigurationField {
            public ORIENTATION(String str, int i) {
                super(str, i, null);
            }

            private final String convertToString(int i) {
                return i != -100 ? i != 0 ? i != 1 ? i != 2 ? String.valueOf(i) : "land" : HostAuth.PORT : "undef" : "def";
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public boolean needToUpdate(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                Integer num = enumMap.get(this);
                return num == null || num.intValue() != configuration.orientation;
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public String toCompareString(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                Integer num = enumMap.get(this);
                return getSummaryString("orientation", num != null ? convertToString(num.intValue()) : null, convertToString(configuration.orientation));
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public void update(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                enumMap.put((EnumMap<ConfigurationField, Integer>) this, (ORIENTATION) Integer.valueOf(configuration.orientation));
            }
        }

        final class SCREEN_HEIGHT_DP extends ConfigurationField {
            public SCREEN_HEIGHT_DP(String str, int i) {
                super(str, i, null);
            }

            private final String convertToString(int i) {
                if (i == -100) {
                    return "def";
                }
                if (i == 0) {
                    return "undef";
                }
                return i + "dp";
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public boolean needToUpdate(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                Integer num = enumMap.get(this);
                return num == null || num.intValue() != configuration.screenHeightDp;
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public String toCompareString(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                Integer num = enumMap.get(this);
                return getSummaryString("screenHeightDp", num != null ? convertToString(num.intValue()) : null, convertToString(configuration.screenHeightDp));
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public void update(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                enumMap.put((EnumMap<ConfigurationField, Integer>) this, (SCREEN_HEIGHT_DP) Integer.valueOf(configuration.screenHeightDp));
            }
        }

        final class SCREEN_LAYOUT extends ConfigurationField {
            public SCREEN_LAYOUT(String str, int i) {
                super(str, i, null);
            }

            private final String convertToString(int i) {
                if (i == -100) {
                    return "def";
                }
                int i2 = i & 15;
                return i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? String.valueOf(i2) : "xlrg" : "lrg" : "nrml" : "smll" : "undef";
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public boolean needToUpdate(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                Integer num = enumMap.get(this);
                return num == null || num.intValue() != configuration.screenLayout;
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public String toCompareString(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                Integer num = enumMap.get(this);
                return getSummaryString("screenLayout", num != null ? convertToString(num.intValue()) : null, convertToString(configuration.screenLayout));
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public void update(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                enumMap.put((EnumMap<ConfigurationField, Integer>) this, (SCREEN_LAYOUT) Integer.valueOf(configuration.screenLayout));
            }
        }

        final class THEME_SEQ extends ConfigurationField {
            public THEME_SEQ(String str, int i) {
                super(str, i, null);
            }

            private final String convertToString(int i) {
                return i == -100 ? "def" : String.valueOf(i);
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public boolean needToUpdate(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                Integer num = enumMap.get(this);
                return num == null || num.intValue() != configuration.themeSeq;
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public String toCompareString(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                Integer num = enumMap.get(this);
                return getSummaryString("themeSeq", num != null ? convertToString(num.intValue()) : null, convertToString(configuration.themeSeq));
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public void update(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                enumMap.put((EnumMap<ConfigurationField, Integer>) this, (THEME_SEQ) Integer.valueOf(configuration.themeSeq));
            }
        }

        final class UI_MODE extends ConfigurationField {
            public UI_MODE(String str, int i) {
                super(str, i, null);
            }

            private final String convertToString(int i) {
                String str;
                if (i == -100) {
                    return "def";
                }
                int i2 = i & 15;
                switch (i2) {
                    case 0:
                        str = "undef";
                        break;
                    case 1:
                        str = SystemUIAnalytics.QPNE_VID_NORMAL;
                        break;
                    case 2:
                        str = "desk";
                        break;
                    case 3:
                        str = "car";
                        break;
                    case 4:
                        str = "television";
                        break;
                    case 5:
                        str = "appliance";
                        break;
                    case 6:
                        str = "watch";
                        break;
                    case 7:
                        str = "vrheadset";
                        break;
                    default:
                        str = String.valueOf(i2);
                        break;
                }
                int i3 = i & 48;
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "/", i3 != 0 ? i3 != 16 ? i3 != 32 ? String.valueOf(i3) : "night" : "nightNo" : "undef");
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public boolean needToUpdate(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                Integer num = enumMap.get(this);
                return num == null || num.intValue() != configuration.uiMode;
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public String toCompareString(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                Integer num = enumMap.get(this);
                return getSummaryString("uiMode", num != null ? convertToString(num.intValue()) : null, convertToString(configuration.uiMode));
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public void update(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration) {
                enumMap.put((EnumMap<ConfigurationField, Integer>) this, (UI_MODE) Integer.valueOf(configuration.uiMode));
            }
        }

        private static final /* synthetic */ ConfigurationField[] $values() {
            return new ConfigurationField[]{ASSET_SEQ, DISPLAY_DEVICE_TYPE, LOCALE, ORIENTATION, SCREEN_HEIGHT_DP, SCREEN_LAYOUT, THEME_SEQ, UI_MODE, DENSITY_DPI};
        }

        static {
            ConfigurationField[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }

        public /* synthetic */ ConfigurationField(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, i);
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static ConfigurationField valueOf(String str) {
            return (ConfigurationField) Enum.valueOf(ConfigurationField.class, str);
        }

        public static ConfigurationField[] values() {
            return (ConfigurationField[]) $VALUES.clone();
        }

        public final String getSummaryString(String str, String str2, String str3) {
            return str + " = " + str2 + " > " + str3;
        }

        public abstract boolean needToUpdate(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration);

        public abstract String toCompareString(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration);

        public abstract void update(EnumMap<ConfigurationField, Integer> enumMap, Configuration configuration);

        private ConfigurationField(String str, int i) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ConfigurationState(List<? extends ConfigurationField> list) {
        this.fields = list;
        EnumEntries entries = ConfigurationField.getEntries();
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(entries, 10));
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity < 16 ? 16 : mapCapacity);
        for (Object obj : entries) {
            linkedHashMap.put(obj, -100);
        }
        this.fieldMap = new EnumMap<>(linkedHashMap);
    }

    public final int getValue(ConfigurationField configurationField) {
        Object obj;
        Integer num;
        Iterator<T> it = this.fields.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (((ConfigurationField) obj) == configurationField) {
                break;
            }
        }
        ConfigurationField configurationField2 = (ConfigurationField) obj;
        if (configurationField2 == null || (num = this.fieldMap.get(configurationField2)) == null) {
            num = -100;
        }
        return num.intValue();
    }

    public final boolean needToUpdate(Configuration configuration) {
        if (configuration == null) {
            return false;
        }
        List<ConfigurationField> list = this.fields;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (((ConfigurationField) it.next()).needToUpdate(this.fieldMap, configuration)) {
                return true;
            }
        }
        return false;
    }

    public final String toCompareString(final Configuration configuration) {
        return configuration == null ? "" : CollectionsKt___CollectionsKt.joinToString$default(this.fields, ", ", null, null, new Function1() { // from class: com.android.systemui.util.ConfigurationState$toCompareString$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(ConfigurationState.ConfigurationField configurationField) {
                EnumMap<ConfigurationState.ConfigurationField, Integer> enumMap;
                enumMap = ConfigurationState.this.fieldMap;
                return configurationField.toCompareString(enumMap, configuration);
            }
        }, 30);
    }

    public final void update(Configuration configuration) {
        Iterator<T> it = this.fields.iterator();
        while (it.hasNext()) {
            ((ConfigurationField) it.next()).update(this.fieldMap, configuration);
        }
    }
}
