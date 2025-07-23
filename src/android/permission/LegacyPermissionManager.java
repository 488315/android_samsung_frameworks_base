package android.permission;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.permission.ILegacyPermissionManager;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final class LegacyPermissionManager {
    private final ILegacyPermissionManager mLegacyPermissionManager;

    public LegacyPermissionManager() throws ServiceManager.ServiceNotFoundException {
        this(ILegacyPermissionManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.LEGACY_PERMISSION_SERVICE)));
    }

    public LegacyPermissionManager(ILegacyPermissionManager iLegacyPermissionManager) {
        this.mLegacyPermissionManager = iLegacyPermissionManager;
    }

    public int checkDeviceIdentifierAccess(String str, String str2, String str3, int i, int i2) {
        try {
            return this.mLegacyPermissionManager.checkDeviceIdentifierAccess(str, str2, str3, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int checkPhoneNumberAccess(String str, String str2, String str3, int i, int i2) {
        try {
            return this.mLegacyPermissionManager.checkPhoneNumberAccess(str, str2, str3, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void grantDefaultPermissionsToLuiApp(String str, UserHandle userHandle, Executor executor, final Consumer<Boolean> consumer) {
        try {
            this.mLegacyPermissionManager.grantDefaultPermissionsToActiveLuiApp(str, userHandle.getIdentifier());
            executor.execute(new Runnable() { // from class: android.permission.LegacyPermissionManager$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(true);
                }
            });
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void revokeDefaultPermissionsFromLuiApps(String[] strArr, UserHandle userHandle, Executor executor, final Consumer<Boolean> consumer) {
        try {
            this.mLegacyPermissionManager.revokeDefaultPermissionsFromLuiApps(strArr, userHandle.getIdentifier());
            executor.execute(new Runnable() { // from class: android.permission.LegacyPermissionManager$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(true);
                }
            });
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void grantDefaultPermissionsToEnabledImsServices(String[] strArr, UserHandle userHandle, Executor executor, final Consumer<Boolean> consumer) {
        try {
            this.mLegacyPermissionManager.grantDefaultPermissionsToEnabledImsServices(strArr, userHandle.getIdentifier());
            executor.execute(new Runnable() { // from class: android.permission.LegacyPermissionManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(true);
                }
            });
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void grantDefaultPermissionsToEnabledTelephonyDataServices(String[] strArr, UserHandle userHandle, Executor executor, final Consumer<Boolean> consumer) {
        try {
            this.mLegacyPermissionManager.grantDefaultPermissionsToEnabledTelephonyDataServices(strArr, userHandle.getIdentifier());
            executor.execute(new Runnable() { // from class: android.permission.LegacyPermissionManager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(true);
                }
            });
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void revokeDefaultPermissionsFromDisabledTelephonyDataServices(String[] strArr, UserHandle userHandle, Executor executor, final Consumer<Boolean> consumer) {
        try {
            this.mLegacyPermissionManager.revokeDefaultPermissionsFromDisabledTelephonyDataServices(strArr, userHandle.getIdentifier());
            executor.execute(new Runnable() { // from class: android.permission.LegacyPermissionManager$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(true);
                }
            });
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void grantDefaultPermissionsToEnabledCarrierApps(String[] strArr, UserHandle userHandle, Executor executor, final Consumer<Boolean> consumer) {
        try {
            this.mLegacyPermissionManager.grantDefaultPermissionsToEnabledCarrierApps(strArr, userHandle.getIdentifier());
            executor.execute(new Runnable() { // from class: android.permission.LegacyPermissionManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(true);
                }
            });
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void grantDefaultPermissionsToCarrierServiceApp(String str, int i) {
        try {
            this.mLegacyPermissionManager.grantDefaultPermissionsToCarrierServiceApp(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
