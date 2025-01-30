package com.android.systemui.dagger;

import android.content.Context;
import android.permission.PermissionManager;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FrameworkServicesModule_ProvidePermissionManagerFactory implements Provider {
    public final Provider contextProvider;

    public FrameworkServicesModule_ProvidePermissionManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static PermissionManager providePermissionManager(Context context) {
        PermissionManager permissionManager = (PermissionManager) context.getSystemService(PermissionManager.class);
        if (permissionManager != null) {
            permissionManager.initializeUsageHelper();
        }
        Preconditions.checkNotNullFromProvides(permissionManager);
        return permissionManager;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providePermissionManager((Context) this.contextProvider.get());
    }
}
