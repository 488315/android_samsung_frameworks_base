package com.android.systemui.statusbar.pipeline.carrier;

import android.os.Build;
import android.os.SystemProperties;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import java.util.List;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CommonUtil {
    public final String VALUE_SUB_DISPLAY_POLICY;
    public final String countryISO = SemCscFeature.getInstance().getString("CountryISO", "");
    public final List extraSystemIcons = StringsKt__StringsKt.split$default(SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigDefIndicatorAdditionalSystemIcon", ""), new String[]{","}, 0, 6);
    public String overriddenIconBranding = "";
    public final String salesCode;
    public final SystemPropertiesWrapper systemPropertiesWrapper;

    public CommonUtil(SystemPropertiesWrapper systemPropertiesWrapper) {
        this.systemPropertiesWrapper = systemPropertiesWrapper;
        this.salesCode = systemPropertiesWrapper.salesCode;
        this.VALUE_SUB_DISPLAY_POLICY = ("user".equals(Build.TYPE) || (SystemProperties.getInt("persist.debug.subdisplay_test_mode", 0) & 1) == 0) ? SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY") : "";
    }

    public final String getIconBranding(int i) {
        if (supportTSS20()) {
            return SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigOpBrandingForIndicatorIcon", "", false);
        }
        String str = this.overriddenIconBranding;
        return str.length() == 0 ? SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigOpBrandingForIndicatorIcon", "") : str;
    }

    public final boolean supportTSS20() {
        SystemPropertiesWrapper systemPropertiesWrapper = this.systemPropertiesWrapper;
        return systemPropertiesWrapper.singleSKU && systemPropertiesWrapper.unified;
    }
}
