package com.android.systemui.statusbar.pipeline.mobile.data.model;

import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;

/* loaded from: classes3.dex */
public abstract class SubscriptionModelKt {
    public static final SubscriptionModel DEFAULT_SUBSCRIPTION_MODEL = new SubscriptionModel(Integer.MAX_VALUE, false, false, null, "Dummy Carrier", 0, false, false, 0, false, KnoxEnterpriseLicenseManager.ERROR_LICENSE_QUANTITY_EXHAUSTED_ON_AUTO_RELEASE, null);
}
