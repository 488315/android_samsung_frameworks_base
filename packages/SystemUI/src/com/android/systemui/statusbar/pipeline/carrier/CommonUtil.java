package com.android.systemui.statusbar.pipeline.carrier;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
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
        if (!supportTSS20()) {
            String str = this.countryISO;
            if (!Intrinsics.areEqual(str, "US")) {
                Intrinsics.areEqual(str, "CA");
            }
        }
        return SemCarrierFeature.getInstance().getString(i, "CarrierFeature_SystemUI_ConfigOpBrandingForIndicatorIcon", "", false);
    }

    public final boolean supportTSS20() {
        SystemPropertiesWrapper systemPropertiesWrapper = this.systemPropertiesWrapper;
        return systemPropertiesWrapper.singleSKU && systemPropertiesWrapper.unified;
    }
}
