package com.android.wifitrackerlib;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.UserManager;
import android.util.ArraySet;
import com.android.systemui.R;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WifiTrackerInjector {
    public final DevicePolicyManager mDevicePolicyManager;
    public final Set mNoAttributionAnnotationPackages;
    public final UserManager mUserManager;

    public WifiTrackerInjector(Context context) {
        UserManager.isDeviceInDemoMode(context);
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        this.mNoAttributionAnnotationPackages = new ArraySet();
        for (String str : context.getString(R.string.wifitrackerlib_no_attribution_annotation_packages).split(",")) {
            this.mNoAttributionAnnotationPackages.add(str);
        }
    }
}
