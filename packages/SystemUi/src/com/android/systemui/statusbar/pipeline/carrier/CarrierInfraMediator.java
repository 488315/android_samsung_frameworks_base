package com.android.systemui.statusbar.pipeline.carrier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface CarrierInfraMediator {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Conditions {
        SUPPORT_TSS20,
        NO_SERVICE_WHEN_NO_SIM,
        SIGNAL_BAR_WHEN_EMERGENCY,
        CHANGE_SIGNAL_ONE_LEVEL_PER_SEC,
        ZERO_SIGNAL_LEVEL_ON_VOWIFI,
        /* JADX INFO: Fake field, exist only in values array */
        SIGNAL_BAR_ONLY_WITH_VOICE_REG,
        USE_VOICE_NO_SERVICE_ICON,
        IS_VOICE_CAPABLE,
        /* JADX INFO: Fake field, exist only in values array */
        EMPTY_PLMN_WHEN_NO_SIM,
        /* JADX INFO: Fake field, exist only in values array */
        SUPPORT_DUAL_IMS,
        MULTI_LINE_CARRIER_LABEL,
        DISPLAY_CBCH50,
        IS_CLARO_PLMN,
        CARRIER_LOGO_ON_HOME_SCREEN,
        USE_LTE_CA_ICON,
        USE_5G_ONE_SHAPED_ICON,
        USE_LTE_INSTEAD_OF_4G,
        USE_4G_PLUS_INSTEAD_OF_4G,
        USE_4_HALF_G_INSTEAD_OF_4G_PLUS,
        USE_HSPA_DATA_ICON,
        IS_CSC_MATCHED_SIM,
        USE_DISABLED_DATA_ICON,
        IS_CHINA_DISABLED_ICON,
        IS_HKTW_DISABLED_ICON,
        IS_LATIN_DISABLED_ICON,
        /* JADX INFO: Fake field, exist only in values array */
        SUB_SCREEN_STATUS_ICONS,
        USE_5G_ENLARGED_ICON,
        SUPPORT_ROAMING_ICON,
        GSM_ROAMING_ICON_ONLY,
        CDMA_ROAMING_ICON_ONLY,
        NO_ROAMING_ICON_AT_GSM,
        /* JADX INFO: Fake field, exist only in values array */
        SUB_SCREEN_STATUS_ICONS,
        USE_FEMTOCELL_ICON,
        USE_WIFI_CALLING_ICON,
        IS_CHINA,
        IS_USA,
        IS_USA_OPEN,
        IS_USA_TMOBILE,
        IS_USA_TMOBILE_FAMILY,
        IS_USA_VZW,
        IS_USA_SPRINT,
        IS_KT,
        IS_LGT,
        /* JADX INFO: Fake field, exist only in values array */
        SUB_SCREEN_STATUS_ICONS,
        IS_LATIN_AMX_FAMILY,
        IS_LATIN_DOR,
        SHOW_TWO_PHONE_MODE_ICON,
        SUB_SCREEN_SIGNAL,
        /* JADX INFO: Fake field, exist only in values array */
        SUB_SCREEN_STATUS_ICONS,
        SUB_SCREEN_MODE_ICON,
        SUPPORT_CARRIER_ENABLED_SATELLITE
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Values {
        ICON_BRANDING,
        ICON_BRANDING_FROM_CSC_FEATURE,
        ICON_BRANDING_FROM_CARRIER_FEATURE,
        OVERRIDE_ICON_BRANDING,
        MAX_SIGNAL_LEVEL,
        EXTRA_SYSTEM_ICON_LIST,
        /* JADX INFO: Fake field, exist only in values array */
        IMS_ICON
    }

    Object get(Values values, int i, Object... objArr);

    boolean isEnabled(Conditions conditions, int i, Object... objArr);

    void set(Values values, Object... objArr);
}
