package com.android.server.pm;

import android.app.role.RoleManager;
import android.os.Binder;
import android.os.UserHandle;

import com.android.internal.util.CollectionUtils;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.FgThread;

import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DefaultAppProvider {
    public final Supplier mRoleManagerSupplier;
    public final Supplier mUserManagerInternalSupplier;

    public DefaultAppProvider(
            PackageManagerService$$ExternalSyntheticLambda55
                    packageManagerService$$ExternalSyntheticLambda55,
            PackageManagerService$$ExternalSyntheticLambda42
                    packageManagerService$$ExternalSyntheticLambda42) {
        this.mRoleManagerSupplier = packageManagerService$$ExternalSyntheticLambda55;
        this.mUserManagerInternalSupplier = packageManagerService$$ExternalSyntheticLambda42;
    }

    public final String getDefaultHome(int i) {
        return getRoleHolder(
                ((UserManagerInternal) this.mUserManagerInternalSupplier.get())
                        .getProfileParentId(i),
                "android.app.role.HOME");
    }

    public final String getRoleHolder(int i, String str) {
        RoleManager roleManager = (RoleManager) this.mRoleManagerSupplier.get();
        if (roleManager == null) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return (String)
                    CollectionUtils.firstOrNull(
                            roleManager.getRoleHoldersAsUser(str, UserHandle.of(i)));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setDefaultBrowser(int i, final String str) {
        RoleManager roleManager = (RoleManager) this.mRoleManagerSupplier.get();
        if (roleManager == null) {
            return;
        }
        UserHandle of = UserHandle.of(i);
        Executor executor = FgThread.getExecutor();
        Consumer consumer =
                new Consumer() { // from class:
                                 // com.android.server.pm.DefaultAppProvider$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        String str2 = str;
                        if (((Boolean) obj).booleanValue()) {
                            return;
                        }
                        BootReceiver$$ExternalSyntheticOutline0.m(
                                "Failed to set default browser to ", str2, "PackageManager");
                    }
                };
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (str != null) {
                roleManager.addRoleHolderAsUser(
                        "android.app.role.BROWSER", str, 0, of, executor, consumer);
            } else {
                roleManager.clearRoleHoldersAsUser(
                        "android.app.role.BROWSER", 0, of, executor, consumer);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
