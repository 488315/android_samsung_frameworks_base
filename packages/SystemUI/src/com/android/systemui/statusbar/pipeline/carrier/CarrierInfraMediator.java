package com.android.systemui.statusbar.pipeline.carrier;

import kotlin.enums.EnumEntriesKt;

public interface CarrierInfraMediator {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class Conditions {
        public static final /* synthetic */ Conditions[] $VALUES;
        public static final Conditions CARRIER_LOGO_ON_HOME_SCREEN;
        public static final Conditions CDMA_ROAMING_ICON_ONLY;
        public static final Conditions CHANGE_SIGNAL_ONE_LEVEL_PER_SEC;
        public static final Conditions DISPLAY_CBCH50;
        public static final Conditions GSM_ROAMING_ICON_ONLY;
        public static final Conditions IS_CHINA;
        public static final Conditions IS_CHINA_DEVICE;
        public static final Conditions IS_CHINA_DISABLED_ICON;
        public static final Conditions IS_CLARO_PLMN;
        public static final Conditions IS_HKTW_DISABLED_ICON;
        public static final Conditions IS_KT;
        public static final Conditions IS_LATIN_AMX_FAMILY;
        public static final Conditions IS_LATIN_DISABLED_ICON;
        public static final Conditions IS_LATIN_DOR;
        public static final Conditions IS_LGT;
        public static final Conditions IS_USA;
        public static final Conditions IS_USA_ATT_DEVICE;
        public static final Conditions IS_USA_OPEN;
        public static final Conditions IS_USA_SPRINT;
        public static final Conditions IS_USA_TMOBILE;
        public static final Conditions IS_USA_TMOBILE_FAMILY;
        public static final Conditions IS_USA_TMO_DEVICE;
        public static final Conditions IS_USA_VZW;
        public static final Conditions IS_VOICE_CAPABLE;
        public static final Conditions MULTI_LINE_CARRIER_LABEL;
        public static final Conditions NO_ROAMING_ICON_AT_GSM;
        public static final Conditions NO_SERVICE_WHEN_NO_SIM;
        public static final Conditions SHOW_TWO_PHONE_MODE_ICON;
        public static final Conditions SIGNAL_BAR_WHEN_EMERGENCY;
        public static final Conditions SUB_SCREEN_MODE_ICON;
        public static final Conditions SUB_SCREEN_SIGNAL;
        public static final Conditions SUPPORT_CARRIER_ENABLED_SATELLITE;
        public static final Conditions SUPPORT_ROAMING_ICON;
        public static final Conditions SUPPORT_TSS20;
        public static final Conditions USE_4G_PLUS_INSTEAD_OF_4G;
        public static final Conditions USE_4_HALF_G_INSTEAD_OF_4G_PLUS;
        public static final Conditions USE_5G_ENLARGED_ICON;
        public static final Conditions USE_5G_ONE_SHAPED_ICON;
        public static final Conditions USE_DISABLED_DATA_ICON;
        public static final Conditions USE_HSPA_DATA_ICON;
        public static final Conditions USE_LTE_CA_ICON;
        public static final Conditions USE_LTE_INSTEAD_OF_4G;
        public static final Conditions USE_VOICE_NO_SERVICE_ICON;
        public static final Conditions USE_WIFI_CALLING_ICON;
        public static final Conditions ZERO_SIGNAL_LEVEL_ON_VOWIFI;

        static {
            Conditions conditions = new Conditions("SUPPORT_TSS20", 0);
            SUPPORT_TSS20 = conditions;
            Conditions conditions2 = new Conditions("NO_SERVICE_WHEN_NO_SIM", 1);
            NO_SERVICE_WHEN_NO_SIM = conditions2;
            Conditions conditions3 = new Conditions("SIGNAL_BAR_WHEN_EMERGENCY", 2);
            SIGNAL_BAR_WHEN_EMERGENCY = conditions3;
            Conditions conditions4 = new Conditions("CHANGE_SIGNAL_ONE_LEVEL_PER_SEC", 3);
            CHANGE_SIGNAL_ONE_LEVEL_PER_SEC = conditions4;
            Conditions conditions5 = new Conditions("ZERO_SIGNAL_LEVEL_ON_VOWIFI", 4);
            ZERO_SIGNAL_LEVEL_ON_VOWIFI = conditions5;
            Conditions conditions6 = new Conditions("USE_VOICE_NO_SERVICE_ICON", 5);
            USE_VOICE_NO_SERVICE_ICON = conditions6;
            Conditions conditions7 = new Conditions("IS_VOICE_CAPABLE", 6);
            IS_VOICE_CAPABLE = conditions7;
            Conditions conditions8 = new Conditions("MULTI_LINE_CARRIER_LABEL", 7);
            MULTI_LINE_CARRIER_LABEL = conditions8;
            Conditions conditions9 = new Conditions("DISPLAY_CBCH50", 8);
            DISPLAY_CBCH50 = conditions9;
            Conditions conditions10 = new Conditions("IS_CLARO_PLMN", 9);
            IS_CLARO_PLMN = conditions10;
            Conditions conditions11 = new Conditions("CARRIER_LOGO_ON_HOME_SCREEN", 10);
            CARRIER_LOGO_ON_HOME_SCREEN = conditions11;
            Conditions conditions12 = new Conditions("USE_LTE_CA_ICON", 11);
            USE_LTE_CA_ICON = conditions12;
            Conditions conditions13 = new Conditions("USE_5G_ONE_SHAPED_ICON", 12);
            USE_5G_ONE_SHAPED_ICON = conditions13;
            Conditions conditions14 = new Conditions("USE_LTE_INSTEAD_OF_4G", 13);
            USE_LTE_INSTEAD_OF_4G = conditions14;
            Conditions conditions15 = new Conditions("USE_4G_PLUS_INSTEAD_OF_4G", 14);
            USE_4G_PLUS_INSTEAD_OF_4G = conditions15;
            Conditions conditions16 = new Conditions("USE_4_HALF_G_INSTEAD_OF_4G_PLUS", 15);
            USE_4_HALF_G_INSTEAD_OF_4G_PLUS = conditions16;
            Conditions conditions17 = new Conditions("USE_HSPA_DATA_ICON", 16);
            USE_HSPA_DATA_ICON = conditions17;
            Conditions conditions18 = new Conditions("USE_DISABLED_DATA_ICON", 17);
            USE_DISABLED_DATA_ICON = conditions18;
            Conditions conditions19 = new Conditions("IS_CHINA_DISABLED_ICON", 18);
            IS_CHINA_DISABLED_ICON = conditions19;
            Conditions conditions20 = new Conditions("IS_HKTW_DISABLED_ICON", 19);
            IS_HKTW_DISABLED_ICON = conditions20;
            Conditions conditions21 = new Conditions("IS_LATIN_DISABLED_ICON", 20);
            IS_LATIN_DISABLED_ICON = conditions21;
            Conditions conditions22 = new Conditions("USE_5G_ENLARGED_ICON", 21);
            USE_5G_ENLARGED_ICON = conditions22;
            Conditions conditions23 = new Conditions("SUPPORT_ROAMING_ICON", 22);
            SUPPORT_ROAMING_ICON = conditions23;
            Conditions conditions24 = new Conditions("GSM_ROAMING_ICON_ONLY", 23);
            GSM_ROAMING_ICON_ONLY = conditions24;
            Conditions conditions25 = new Conditions("CDMA_ROAMING_ICON_ONLY", 24);
            CDMA_ROAMING_ICON_ONLY = conditions25;
            Conditions conditions26 = new Conditions("NO_ROAMING_ICON_AT_GSM", 25);
            NO_ROAMING_ICON_AT_GSM = conditions26;
            Conditions conditions27 = new Conditions("USE_WIFI_CALLING_ICON", 26);
            USE_WIFI_CALLING_ICON = conditions27;
            Conditions conditions28 = new Conditions("IS_CHINA", 27);
            IS_CHINA = conditions28;
            Conditions conditions29 = new Conditions("IS_CHINA_DEVICE", 28);
            IS_CHINA_DEVICE = conditions29;
            Conditions conditions30 = new Conditions("IS_USA", 29);
            IS_USA = conditions30;
            Conditions conditions31 = new Conditions("IS_USA_OPEN", 30);
            IS_USA_OPEN = conditions31;
            Conditions conditions32 = new Conditions("IS_USA_TMOBILE", 31);
            IS_USA_TMOBILE = conditions32;
            Conditions conditions33 = new Conditions("IS_USA_TMOBILE_FAMILY", 32);
            IS_USA_TMOBILE_FAMILY = conditions33;
            Conditions conditions34 = new Conditions("IS_USA_TMO_DEVICE", 33);
            IS_USA_TMO_DEVICE = conditions34;
            Conditions conditions35 = new Conditions("IS_USA_ATT_DEVICE", 34);
            IS_USA_ATT_DEVICE = conditions35;
            Conditions conditions36 = new Conditions("IS_USA_VZW", 35);
            IS_USA_VZW = conditions36;
            Conditions conditions37 = new Conditions("IS_USA_SPRINT", 36);
            IS_USA_SPRINT = conditions37;
            Conditions conditions38 = new Conditions("IS_KT", 37);
            IS_KT = conditions38;
            Conditions conditions39 = new Conditions("IS_LGT", 38);
            IS_LGT = conditions39;
            Conditions conditions40 = new Conditions("IS_LATIN_AMX_FAMILY", 39);
            IS_LATIN_AMX_FAMILY = conditions40;
            Conditions conditions41 = new Conditions("IS_LATIN_DOR", 40);
            IS_LATIN_DOR = conditions41;
            Conditions conditions42 = new Conditions("SHOW_TWO_PHONE_MODE_ICON", 41);
            SHOW_TWO_PHONE_MODE_ICON = conditions42;
            Conditions conditions43 = new Conditions("SUB_SCREEN_SIGNAL", 42);
            SUB_SCREEN_SIGNAL = conditions43;
            Conditions conditions44 = new Conditions("SUB_SCREEN_STATUS_ICONS", 43);
            Conditions conditions45 = new Conditions("SUB_SCREEN_MODE_ICON", 44);
            SUB_SCREEN_MODE_ICON = conditions45;
            Conditions conditions46 = new Conditions("SUPPORT_CARRIER_ENABLED_SATELLITE", 45);
            SUPPORT_CARRIER_ENABLED_SATELLITE = conditions46;
            Conditions[] conditionsArr = {conditions, conditions2, conditions3, conditions4, conditions5, conditions6, conditions7, conditions8, conditions9, conditions10, conditions11, conditions12, conditions13, conditions14, conditions15, conditions16, conditions17, conditions18, conditions19, conditions20, conditions21, conditions22, conditions23, conditions24, conditions25, conditions26, conditions27, conditions28, conditions29, conditions30, conditions31, conditions32, conditions33, conditions34, conditions35, conditions36, conditions37, conditions38, conditions39, conditions40, conditions41, conditions42, conditions43, conditions44, conditions45, conditions46};
            $VALUES = conditionsArr;
            EnumEntriesKt.enumEntries(conditionsArr);
        }

        private Conditions(String str, int i) {
        }

        public static Conditions valueOf(String str) {
            return (Conditions) Enum.valueOf(Conditions.class, str);
        }

        public static Conditions[] values() {
            return (Conditions[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class Values {
        public static final /* synthetic */ Values[] $VALUES;
        public static final Values EXTRA_SYSTEM_ICON_LIST;
        public static final Values ICON_BRANDING;
        public static final Values ICON_BRANDING_FROM_CARRIER_FEATURE;
        public static final Values ICON_BRANDING_FROM_CSC_FEATURE;
        public static final Values MAX_SIGNAL_LEVEL;
        public static final Values OVERRIDE_ICON_BRANDING;

        static {
            Values values = new Values("ICON_BRANDING", 0);
            ICON_BRANDING = values;
            Values values2 = new Values("ICON_BRANDING_FROM_CSC_FEATURE", 1);
            ICON_BRANDING_FROM_CSC_FEATURE = values2;
            Values values3 = new Values("ICON_BRANDING_FROM_CARRIER_FEATURE", 2);
            ICON_BRANDING_FROM_CARRIER_FEATURE = values3;
            Values values4 = new Values("OVERRIDE_ICON_BRANDING", 3);
            OVERRIDE_ICON_BRANDING = values4;
            Values values5 = new Values("MAX_SIGNAL_LEVEL", 4);
            MAX_SIGNAL_LEVEL = values5;
            Values values6 = new Values("EXTRA_SYSTEM_ICON_LIST", 5);
            EXTRA_SYSTEM_ICON_LIST = values6;
            Values[] valuesArr = {values, values2, values3, values4, values5, values6};
            $VALUES = valuesArr;
            EnumEntriesKt.enumEntries(valuesArr);
        }

        private Values(String str, int i) {
        }

        public static Values valueOf(String str) {
            return (Values) Enum.valueOf(Values.class, str);
        }

        public static Values[] values() {
            return (Values[]) $VALUES.clone();
        }
    }

    Object get(Values values, int i, Object... objArr);

    boolean isEnabled(Conditions conditions, int i, Object... objArr);
}
