package com.android.server.devicepolicy;

import android.app.admin.IDevicePolicyManager;
import android.content.Context;
import android.os.ServiceManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SecurityPolicyHelper {
    public final Context mContext;
    public IDevicePolicyManager mDPM;

    public SecurityPolicyHelper(Context context) {
        this.mContext = context;
        if (this.mDPM == null) {
            this.mDPM = IDevicePolicyManager.Stub.asInterface(ServiceManager.getService("device_policy"));
        }
        this.mDPM = this.mDPM;
    }
}
