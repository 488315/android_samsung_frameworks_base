package com.android.server.devicepolicy;

import android.content.pm.UserInfo;

import java.util.function.Function;

public final /* synthetic */ class PolicyEnforcerCallbacks$$ExternalSyntheticLambda11
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Integer.valueOf(((UserInfo) obj).id);
    }
}
