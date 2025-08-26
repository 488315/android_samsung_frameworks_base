package com.samsung.sesl.compose.phone.ui.text;

import androidx.compose.ui.text.font.FontWeight;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.sec.ims.volte2.data.VolteConstants;

/* loaded from: classes4.dex */
public final class SeslFontWeight {
    public static final FontWeight Bold;
    public static final SeslFontWeight INSTANCE = new SeslFontWeight();
    public static final FontWeight SemiBold;

    static {
        new FontWeight(300);
        new FontWeight(400);
        SemiBold = new FontWeight(VolteConstants.ErrorCode.BUSY_EVERYWHERE);
        Bold = new FontWeight(KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED);
    }

    private SeslFontWeight() {
    }
}
