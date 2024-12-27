package com.android.server.enterprise.security;

import android.app.ActivityManager;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PasswordPolicy$$ExternalSyntheticLambda46
        implements FunctionalUtils.ThrowingSupplier {
    public final Object getOrThrow() {
        return Integer.valueOf(ActivityManager.getCurrentUser());
    }
}
