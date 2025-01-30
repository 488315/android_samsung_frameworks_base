package com.android.systemui.util;

import android.content.res.Configuration;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ConfigurationState {
    public final EnumMap fieldMap;
    public final List fields;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class ConfigurationField {
        public static final /* synthetic */ ConfigurationField[] $VALUES;
        public static final ConfigurationField ASSET_SEQ;
        public static final ConfigurationField DISPLAY_DEVICE_TYPE;
        public static final ConfigurationField ORIENTATION;
        public static final ConfigurationField SCREEN_HEIGHT_DP;
        public static final ConfigurationField SCREEN_LAYOUT;
        public static final ConfigurationField THEME_SEQ;
        public static final ConfigurationField UI_MODE;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static final class ASSET_SEQ extends ConfigurationField {
            public ASSET_SEQ(String str, int i) {
                super(str, i, null);
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public final boolean needToUpdate(EnumMap enumMap, Configuration configuration) {
                Integer num = (Integer) enumMap.get(this);
                return num == null || num.intValue() != configuration.assetsSeq;
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public final void update(EnumMap enumMap, Configuration configuration) {
                enumMap.put((EnumMap) this, (ASSET_SEQ) Integer.valueOf(configuration.assetsSeq));
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static final class DISPLAY_DEVICE_TYPE extends ConfigurationField {
            public DISPLAY_DEVICE_TYPE(String str, int i) {
                super(str, i, null);
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public final boolean needToUpdate(EnumMap enumMap, Configuration configuration) {
                Integer num = (Integer) enumMap.get(this);
                return num == null || num.intValue() != configuration.semDisplayDeviceType;
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public final void update(EnumMap enumMap, Configuration configuration) {
                enumMap.put((EnumMap) this, (DISPLAY_DEVICE_TYPE) Integer.valueOf(configuration.semDisplayDeviceType));
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static final class LOCALE extends ConfigurationField {
            public LOCALE(String str, int i) {
                super(str, i, null);
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public final boolean needToUpdate(EnumMap enumMap, Configuration configuration) {
                Integer num = (Integer) enumMap.get(this);
                return num == null || num.intValue() != configuration.getLocales().indexOf(configuration.getLocales().get(0));
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public final void update(EnumMap enumMap, Configuration configuration) {
                enumMap.put((EnumMap) this, (LOCALE) Integer.valueOf(configuration.getLocales().indexOf(configuration.getLocales().get(0))));
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static final class ORIENTATION extends ConfigurationField {
            public ORIENTATION(String str, int i) {
                super(str, i, null);
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public final boolean needToUpdate(EnumMap enumMap, Configuration configuration) {
                Integer num = (Integer) enumMap.get(this);
                return num == null || num.intValue() != configuration.orientation;
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public final void update(EnumMap enumMap, Configuration configuration) {
                enumMap.put((EnumMap) this, (ORIENTATION) Integer.valueOf(configuration.orientation));
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static final class SCREEN_HEIGHT_DP extends ConfigurationField {
            public SCREEN_HEIGHT_DP(String str, int i) {
                super(str, i, null);
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public final boolean needToUpdate(EnumMap enumMap, Configuration configuration) {
                Integer num = (Integer) enumMap.get(this);
                return num == null || num.intValue() != configuration.screenHeightDp;
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public final void update(EnumMap enumMap, Configuration configuration) {
                enumMap.put((EnumMap) this, (SCREEN_HEIGHT_DP) Integer.valueOf(configuration.screenHeightDp));
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static final class SCREEN_LAYOUT extends ConfigurationField {
            public SCREEN_LAYOUT(String str, int i) {
                super(str, i, null);
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public final boolean needToUpdate(EnumMap enumMap, Configuration configuration) {
                Integer num = (Integer) enumMap.get(this);
                return num == null || num.intValue() != configuration.screenLayout;
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public final void update(EnumMap enumMap, Configuration configuration) {
                enumMap.put((EnumMap) this, (SCREEN_LAYOUT) Integer.valueOf(configuration.screenLayout));
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static final class THEME_SEQ extends ConfigurationField {
            public THEME_SEQ(String str, int i) {
                super(str, i, null);
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public final boolean needToUpdate(EnumMap enumMap, Configuration configuration) {
                Integer num = (Integer) enumMap.get(this);
                return num == null || num.intValue() != configuration.themeSeq;
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public final void update(EnumMap enumMap, Configuration configuration) {
                enumMap.put((EnumMap) this, (THEME_SEQ) Integer.valueOf(configuration.themeSeq));
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static final class UI_MODE extends ConfigurationField {
            public UI_MODE(String str, int i) {
                super(str, i, null);
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public final boolean needToUpdate(EnumMap enumMap, Configuration configuration) {
                Integer num = (Integer) enumMap.get(this);
                return num == null || num.intValue() != configuration.uiMode;
            }

            @Override // com.android.systemui.util.ConfigurationState.ConfigurationField
            public final void update(EnumMap enumMap, Configuration configuration) {
                enumMap.put((EnumMap) this, (UI_MODE) Integer.valueOf(configuration.uiMode));
            }
        }

        static {
            ASSET_SEQ asset_seq = new ASSET_SEQ("ASSET_SEQ", 0);
            ASSET_SEQ = asset_seq;
            DISPLAY_DEVICE_TYPE display_device_type = new DISPLAY_DEVICE_TYPE("DISPLAY_DEVICE_TYPE", 1);
            DISPLAY_DEVICE_TYPE = display_device_type;
            LOCALE locale = new LOCALE("LOCALE", 2);
            ORIENTATION orientation = new ORIENTATION("ORIENTATION", 3);
            ORIENTATION = orientation;
            SCREEN_HEIGHT_DP screen_height_dp = new SCREEN_HEIGHT_DP("SCREEN_HEIGHT_DP", 4);
            SCREEN_HEIGHT_DP = screen_height_dp;
            SCREEN_LAYOUT screen_layout = new SCREEN_LAYOUT("SCREEN_LAYOUT", 5);
            SCREEN_LAYOUT = screen_layout;
            THEME_SEQ theme_seq = new THEME_SEQ("THEME_SEQ", 6);
            THEME_SEQ = theme_seq;
            UI_MODE ui_mode = new UI_MODE("UI_MODE", 7);
            UI_MODE = ui_mode;
            $VALUES = new ConfigurationField[]{asset_seq, display_device_type, locale, orientation, screen_height_dp, screen_layout, theme_seq, ui_mode};
        }

        public /* synthetic */ ConfigurationField(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, i);
        }

        public static ConfigurationField valueOf(String str) {
            return (ConfigurationField) Enum.valueOf(ConfigurationField.class, str);
        }

        public static ConfigurationField[] values() {
            return (ConfigurationField[]) $VALUES.clone();
        }

        public abstract boolean needToUpdate(EnumMap enumMap, Configuration configuration);

        public abstract void update(EnumMap enumMap, Configuration configuration);

        private ConfigurationField(String str, int i) {
        }
    }

    static {
        new Companion(null);
    }

    public ConfigurationState(List<? extends ConfigurationField> list) {
        this.fields = list;
        ConfigurationField[] values = ConfigurationField.values();
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(values.length);
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity < 16 ? 16 : mapCapacity);
        for (ConfigurationField configurationField : values) {
            linkedHashMap.put(configurationField, -100);
        }
        this.fieldMap = new EnumMap(linkedHashMap);
    }

    public final int getValue(ConfigurationField configurationField) {
        Object obj;
        Integer num;
        Iterator it = this.fields.iterator();
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
        if (configurationField2 == null || (num = (Integer) this.fieldMap.get(configurationField2)) == null) {
            num = -100;
        }
        return num.intValue();
    }

    public final boolean needToUpdate(Configuration configuration) {
        if (configuration == null) {
            return false;
        }
        List list = this.fields;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (((ConfigurationField) it.next()).needToUpdate(this.fieldMap, configuration)) {
                return true;
            }
        }
        return false;
    }

    public final void update(Configuration configuration) {
        Iterator it = this.fields.iterator();
        while (it.hasNext()) {
            ((ConfigurationField) it.next()).update(this.fieldMap, configuration);
        }
    }
}
