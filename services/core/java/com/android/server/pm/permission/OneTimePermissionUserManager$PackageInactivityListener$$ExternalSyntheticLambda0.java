package com.android.server.pm.permission;

import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;

public final /* synthetic */
class OneTimePermissionUserManager$PackageInactivityListener$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OneTimePermissionUserManager.PackageInactivityListener f$0;

    public /* synthetic */
    OneTimePermissionUserManager$PackageInactivityListener$$ExternalSyntheticLambda0(
            OneTimePermissionUserManager.PackageInactivityListener packageInactivityListener,
            int i) {
        this.$r8$classId = i;
        this.f$0 = packageInactivityListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        OneTimePermissionUserManager.PackageInactivityListener packageInactivityListener = this.f$0;
        switch (i) {
            case 0:
                StringBuilder sb = new StringBuilder("One time session expired for ");
                sb.append(packageInactivityListener.mPackageName);
                sb.append(" (");
                sb.append(packageInactivityListener.mUid);
                sb.append("). deviceID ");
                UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                        sb, packageInactivityListener.mDeviceId, "OneTimePermissionUserManager");
                packageInactivityListener.this$0.mPermissionControllerManager
                        .notifyOneTimePermissionSessionTimeout(
                                packageInactivityListener.mPackageName,
                                packageInactivityListener.mDeviceId);
                return;
            default:
                synchronized (packageInactivityListener.mInnerLock) {
                    try {
                        int uidProcessState =
                                packageInactivityListener.this$0.mActivityManagerInternal
                                        .getUidProcessState(packageInactivityListener.mUid);
                        int i2 = uidProcessState == 20 ? 0 : uidProcessState > 4 ? 1 : 2;
                        if (i2 == 0) {
                            packageInactivityListener.onPackageInactiveLocked();
                            return;
                        } else {
                            packageInactivityListener.updateUidState(i2);
                            return;
                        }
                    } finally {
                    }
                }
        }
    }
}
