package com.android.server.devicepolicy;

import android.content.pm.UserInfo;

import java.util.function.ToIntFunction;

public final /* synthetic */
class DevicePolicyManagerService$DpmsUpgradeDataProvider$$ExternalSyntheticLambda3
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((UserInfo) obj).id;
    }
}
