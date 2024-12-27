package com.android.server.enterprise.security;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.content.ComponentName;
import android.os.Binder;
import android.os.UserManager;

import com.android.internal.util.FunctionalUtils;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.util.List;

public final /* synthetic */ class PasswordPolicy$$ExternalSyntheticLambda14
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PasswordPolicy f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ PasswordPolicy$$ExternalSyntheticLambda14(
            PasswordPolicy passwordPolicy, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = passwordPolicy;
        this.f$1 = i;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                PasswordPolicy passwordPolicy = this.f$0;
                int i = this.f$1;
                IActivityManager iActivityManager = ActivityManagerNative.getDefault();
                List<ActivityManager.RecentTaskInfo> list =
                        iActivityManager.getRecentTasks(12, 0, i).getList();
                if (!list.isEmpty()) {
                    for (ActivityManager.RecentTaskInfo recentTaskInfo : list) {
                        ComponentName component = recentTaskInfo.baseIntent.getComponent();
                        if (component != null) {
                            String packageName = component.getPackageName();
                            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                                    "packageName ", packageName, "PasswordPolicy");
                            if (packageName != null
                                    && packageName.equals(
                                            KnoxCustomManagerService.SETTING_PKG_NAME)) {
                                iActivityManager.forceStopPackage(
                                        KnoxCustomManagerService.SETTING_PKG_NAME, i);
                                iActivityManager.removeTask(recentTaskInfo.persistentId);
                            }
                        }
                    }
                    break;
                }
                break;
            default:
                PasswordPolicy passwordPolicy2 = this.f$0;
                int i2 = this.f$1;
                UserManager userManager =
                        (UserManager) passwordPolicy2.mContext.getSystemService("user");
                int[] iArr = PasswordPolicy.BIOMETRIC_AUTHENTICATION_TYPES;
                for (int i3 = 0; i3 < 2; i3++) {
                    int i4 = iArr[i3];
                    if (!passwordPolicy2.isBiometricAuthenticationEnabledAsUser(i2, i4)) {
                        int identifier =
                                userManager.getProfileParent(i2).getUserHandle().getIdentifier();
                        PasswordPolicy.Injector injector = passwordPolicy2.mInjector;
                        PasswordPolicy$$ExternalSyntheticLambda4
                                passwordPolicy$$ExternalSyntheticLambda4 =
                                        new PasswordPolicy$$ExternalSyntheticLambda4(
                                                passwordPolicy2, i4, identifier, 1);
                        injector.getClass();
                        Binder.withCleanCallingIdentity(passwordPolicy$$ExternalSyntheticLambda4);
                    }
                }
                break;
        }
    }
}
