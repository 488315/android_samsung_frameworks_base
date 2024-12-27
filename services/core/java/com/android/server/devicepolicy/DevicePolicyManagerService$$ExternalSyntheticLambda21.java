package com.android.server.devicepolicy;

import android.content.pm.ApplicationInfo;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.ConnectivitySettingsManager;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.os.UserHandle;

import com.android.internal.telephony.SmsApplication;
import com.android.internal.util.FunctionalUtils;
import com.android.server.utils.Slogf;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda21
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda21(
            int i, DevicePolicyManagerService devicePolicyManagerService, String str) {
        this.$r8$classId = 3;
        this.f$0 = devicePolicyManagerService;
        this.f$2 = i;
        this.f$1 = str;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda21(
            DevicePolicyManagerService devicePolicyManagerService, String str, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = str;
        this.f$2 = i;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                String str = this.f$1;
                int i = this.f$2;
                devicePolicyManagerService.getClass();
                try {
                    if (devicePolicyManagerService.mIPackageManager.isPackageAvailable(str, i)) {
                        return;
                    }
                    devicePolicyManagerService.mIPackageManager.installExistingPackageAsUser(
                            str, i, 4194304, 1, (List) null);
                    return;
                } catch (RemoteException e) {
                    Slogf.wtf(
                            "DevicePolicyManager",
                            e,
                            "Failed to install admin package %s for user %d",
                            str,
                            Integer.valueOf(i));
                    return;
                }
            case 1:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                String str2 = this.f$1;
                int i2 = this.f$2;
                devicePolicyManagerService2.getClass();
                try {
                    ApplicationInfo applicationInfo =
                            devicePolicyManagerService2.mIPackageManager.getApplicationInfo(
                                    str2, 0L, i2);
                    if (applicationInfo == null) {
                        Slogf.w("DevicePolicyManager", "Non-existent VPN package: " + str2);
                    } else {
                        devicePolicyManagerService2
                                .mInjector
                                .getAppOpsManager()
                                .setMode(47, applicationInfo.uid, str2, 3);
                    }
                    return;
                } catch (RemoteException e2) {
                    Slogf.e("DevicePolicyManager", "Can't talk to package managed", e2);
                    return;
                }
            case 2:
                this.f$0.enforcePackageIsSystemPackage(this.f$2, this.f$1);
                return;
            case 3:
                DevicePolicyManagerService devicePolicyManagerService3 = this.f$0;
                int i3 = this.f$2;
                String str3 = this.f$1;
                ConnectivitySettingsManager.setPrivateDnsMode(
                        devicePolicyManagerService3.mContext, i3);
                ConnectivitySettingsManager.setPrivateDnsHostname(
                        devicePolicyManagerService3.mContext, str3);
                return;
            case 4:
                DevicePolicyManagerService devicePolicyManagerService4 = this.f$0;
                final String str4 = this.f$1;
                int i4 = this.f$2;
                devicePolicyManagerService4.getClass();
                final CompletableFuture completableFuture = new CompletableFuture();
                devicePolicyManagerService4.mRoleManager.addRoleHolderAsUser(
                        "android.app.role.DIALER",
                        str4,
                        0,
                        UserHandle.of(i4),
                        AsyncTask.THREAD_POOL_EXECUTOR,
                        new Consumer() { // from class:
                            // com.android.server.devicepolicy.DevicePolicyManagerService$$ExternalSyntheticLambda220
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                CompletableFuture completableFuture2 = completableFuture;
                                String str5 = str4;
                                if (((Boolean) obj).booleanValue()) {
                                    completableFuture2.complete(null);
                                } else {
                                    completableFuture2.completeExceptionally(
                                            new IllegalArgumentException(
                                                    ConnectivityModuleConnector$$ExternalSyntheticOutline0
                                                            .m$1(
                                                                    str5,
                                                                    " cannot be set as the"
                                                                        + " dialer")));
                                }
                            }
                        });
                try {
                    completableFuture.get(20L, TimeUnit.SECONDS);
                    return;
                } catch (ExecutionException e3) {
                    Throwable cause = e3.getCause();
                    if (!(cause instanceof IllegalArgumentException)) {
                        throw new IllegalStateException(cause);
                    }
                    throw ((IllegalArgumentException) cause);
                } catch (TimeoutException e4) {
                    throw new IllegalArgumentException(
                            "Timeout when setting the app as the dialer", e4);
                }
            case 5:
                this.f$0.enforcePackageIsSystemPackage(this.f$2, this.f$1);
                return;
            case 6:
                this.f$0.enforcePackageIsSystemPackage(this.f$2, this.f$1);
                return;
            default:
                SmsApplication.setDefaultApplicationAsUser(this.f$1, this.f$0.mContext, this.f$2);
                return;
        }
    }
}
