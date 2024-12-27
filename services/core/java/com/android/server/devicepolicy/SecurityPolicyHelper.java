package com.android.server.devicepolicy;

import android.app.admin.IDevicePolicyManager;
import android.content.Context;
import android.os.ServiceManager;

public final class SecurityPolicyHelper {
    public final Context mContext;
    public IDevicePolicyManager mDPM;

    public SecurityPolicyHelper(Context context) {
        this.mContext = context;
        if (this.mDPM == null) {
            this.mDPM =
                    IDevicePolicyManager.Stub.asInterface(
                            ServiceManager.getService("device_policy"));
        }
        this.mDPM = this.mDPM;
    }
}
