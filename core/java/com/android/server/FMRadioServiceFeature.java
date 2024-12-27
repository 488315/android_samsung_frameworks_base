package com.android.server;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes5.dex */
public class FMRadioServiceFeature {
    public static final String BANDWIDTHAS_76000_108000 = "76000_108000";
    public static final String BANDWIDTHAS_76000_90000 = "76000_90000";
    public static final String BANDWIDTHAS_87500_108000 = "87500_108000";
    public static final int CHIP_MRVL = 3;
    public static final int CHIP_MTK = 8;
    public static final int CHIP_QCOM = 4;
    public static final int CHIP_QCOM_CHROKEE = 9;
    public static final int CHIP_RICHWAVE = 5;
    public static final int CHIP_RICHWAVE_V2 = 10;
    public static final int CHIP_SILICON = 1;
    public static final int CHIP_SLSI = 7;
    public static final int CHIP_SPRD = 6;
    public static final boolean FEATURE_FMRADIO_SUPPORT_EXTERNAL_RADIO_CHIPSET;
    public static final boolean FEATURE_USE_CHIPSET_VOLUME;
    public static final boolean FEATURE_WAIT_PID_DURING_SCAN = false;
    public static SemCscFeature sCscFeature = SemCscFeature.getInstance();
    public static final String FEATURE_BANDWIDTH = sCscFeature.getString("CscFeature_FMRadio_BandWidthAs");
    public static final int FEATURE_FREQUENCYSPACE = sCscFeature.getInteger("CscFeature_FMRadio_FrequencySpaceAs");
    public static final String MEDIATEK_DESENSE_LIST = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_MEDIATEK_DESENSE_LIST");
    public static final String BLEND_SINR_HI = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_QUALCOMM_BLEND_SINR_HI");
    public static final String BLEND_RMSSI_HI = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_QUALCOMM_BLEND_RMSSI_HI");
    public static final String FEATURE_CONFIG_SOFTMUTE = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_COMMON_SOFTMUTE_TH");
    public static final boolean FEATURE_SUPPORT_SOFTMUTE = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FMRADIO_SUPPORT_SOFTMUTE");
    public static final int CHIP_VENDOR = Integer.parseInt(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FMRADIO_CONFIG_CHIP_VENDOR"));

    static {
        boolean z = true;
        if (CHIP_VENDOR != 1 && CHIP_VENDOR != 5 && CHIP_VENDOR != 10 && CHIP_VENDOR != 7) {
            z = false;
        }
        FEATURE_USE_CHIPSET_VOLUME = z;
        FEATURE_FMRADIO_SUPPORT_EXTERNAL_RADIO_CHIPSET = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FMRADIO_SUPPORT_EXTERNAL_RADIO_CHIPSET");
    }
}
