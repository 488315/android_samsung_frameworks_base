package com.android.systemui.statusbar.pipeline.carrier;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CommonUtil {
    public final String countryISO = SemCscFeature.getInstance().getString("CountryISO", "");
    public final String overriddenIconBranding = "";
    public final String salesCode;
    public final SystemPropertiesWrapper systemPropertiesWrapper;

    public CommonUtil(SystemPropertiesWrapper systemPropertiesWrapper) {
        this.systemPropertiesWrapper = systemPropertiesWrapper;
        this.salesCode = systemPropertiesWrapper.salesCode;
    }

    public final String getIconBranding(int i) {
        if (supportTSS20() || Intrinsics.areEqual(this.countryISO, "US")) {
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
