package com.android.systemui.globalactions.util;

import android.content.Context;
import com.samsung.android.knox.SemEnterpriseDeviceManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SemEnterpriseDeviceManagerWrapper {
    public final SemEnterpriseDeviceManager mSemEnterpriseDeviceManager;

    public SemEnterpriseDeviceManagerWrapper(Context context) {
        this.mSemEnterpriseDeviceManager = SemEnterpriseDeviceManager.getInstance(context);
    }
}
