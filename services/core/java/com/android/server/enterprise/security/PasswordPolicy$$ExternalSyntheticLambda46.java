package com.android.server.enterprise.security;

import android.app.ActivityManager;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class PasswordPolicy$$ExternalSyntheticLambda46
        implements FunctionalUtils.ThrowingSupplier {
    public final Object getOrThrow() {
        return Integer.valueOf(ActivityManager.getCurrentUser());
    }
}
