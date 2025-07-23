package com.samsung.sesl.compose.phone.ui.text;

import androidx.compose.ui.text.font.FontWeight;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
