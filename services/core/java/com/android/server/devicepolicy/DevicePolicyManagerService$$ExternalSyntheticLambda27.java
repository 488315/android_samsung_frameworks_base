package com.android.server.devicepolicy;

import android.app.Notification;
import android.content.Intent;
import android.os.RemoteException;
import android.security.IKeyChainService;
import android.security.KeyChain;
import android.util.IndentingPrintWriter;

import com.android.server.utils.Slogf;

public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda27
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DevicePolicyManagerService f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda27(
            DevicePolicyManagerService devicePolicyManagerService, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mContext.startActivity((Intent) this.f$1);
                return;
            case 1:
                this.f$0.mInjector.getNotificationManager().notify(59, (Notification) this.f$1);
                return;
            case 2:
                DevicePolicyManagerService devicePolicyManagerService = this.f$0;
                String str = (String) this.f$1;
                devicePolicyManagerService.getClass();
                try {
                    KeyChain.KeyChainConnection bind =
                            KeyChain.bind(devicePolicyManagerService.mInjector.mContext);
                    try {
                        IKeyChainService service = bind.getService();
                        if (service.hasCredentialManagementApp()
                                && str.equals(service.getCredentialManagementAppPackageName())) {
                            service.removeCredentialManagementApp();
                        }
                        bind.close();
                        return;
                    } catch (Throwable th) {
                        if (bind != null) {
                            try {
                                bind.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (RemoteException
                        | AssertionError
                        | IllegalStateException
                        | InterruptedException e) {
                    Slogf.e(
                            "DevicePolicyManager",
                            "Unable to remove the credential management app",
                            e);
                    return;
                }
            default:
                DevicePolicyManagerService devicePolicyManagerService2 = this.f$0;
                IndentingPrintWriter indentingPrintWriter = (IndentingPrintWriter) this.f$1;
                if (devicePolicyManagerService2.mNetworkLoggingNotificationUserId != -10000) {
                    indentingPrintWriter.println(
                            "mNetworkLoggingNotificationUserId:  "
                                    + devicePolicyManagerService2
                                            .mNetworkLoggingNotificationUserId);
                    return;
                }
                return;
        }
    }
}
