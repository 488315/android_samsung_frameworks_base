package com.android.server.pm;

import android.app.AppOpsManager;
import android.content.IntentSender;
import android.os.UserHandle;

public final /* synthetic */ class InstallPackageHelper$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InstallPackageHelper f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ InstallPackageHelper$$ExternalSyntheticLambda1(
            InstallPackageHelper installPackageHelper,
            String str,
            int i,
            IntentSender intentSender) {
        this.$r8$classId = 2;
        this.f$0 = installPackageHelper;
        this.f$2 = str;
        this.f$3 = i;
        this.f$1 = intentSender;
    }

    public /* synthetic */ InstallPackageHelper$$ExternalSyntheticLambda1(
            InstallPackageHelper installPackageHelper, int[] iArr, String str, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = installPackageHelper;
        this.f$1 = iArr;
        this.f$2 = str;
        this.f$3 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                InstallPackageHelper installPackageHelper = this.f$0;
                int[] iArr = (int[]) this.f$1;
                String str = this.f$2;
                int i = this.f$3;
                installPackageHelper.getClass();
                for (int i2 : iArr) {
                    ((AppOpsManager)
                                    installPackageHelper.mPm.mContext.getSystemService(
                                            AppOpsManager.class))
                            .setMode(119, UserHandle.getUid(i2, i), str, 3);
                }
                break;
            case 1:
                InstallPackageHelper installPackageHelper2 = this.f$0;
                int[] iArr2 = (int[]) this.f$1;
                String str2 = this.f$2;
                int i3 = this.f$3;
                installPackageHelper2.getClass();
                for (int i4 : iArr2) {
                    ((AppOpsManager)
                                    installPackageHelper2.mPm.mContext.getSystemService(
                                            AppOpsManager.class))
                            .setMode(119, UserHandle.getUid(i4, i3), str2, 2);
                }
                break;
            default:
                InstallPackageHelper installPackageHelper3 = this.f$0;
                String str3 = this.f$2;
                int i5 = this.f$3;
                IntentSender intentSender = (IntentSender) this.f$1;
                installPackageHelper3.mPm.restorePermissionsAndUpdateRolesForNewUserInstall(
                        i5, str3);
                if (intentSender != null) {
                    InstallPackageHelper.onInstallComplete(
                            1, installPackageHelper3.mContext, intentSender);
                    break;
                }
                break;
        }
    }
}
