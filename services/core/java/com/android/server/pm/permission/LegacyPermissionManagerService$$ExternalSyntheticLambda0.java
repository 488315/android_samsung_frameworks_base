package com.android.server.pm.permission;

import android.content.pm.PackageInfo;
import android.util.Log;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class LegacyPermissionManagerService$$ExternalSyntheticLambda0
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LegacyPermissionManagerService f$0;
    public final /* synthetic */ String[] f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ LegacyPermissionManagerService$$ExternalSyntheticLambda0(
            LegacyPermissionManagerService legacyPermissionManagerService,
            String[] strArr,
            int i,
            int i2) {
        this.$r8$classId = i2;
        this.f$0 = legacyPermissionManagerService;
        this.f$1 = strArr;
        this.f$2 = i;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                LegacyPermissionManagerService legacyPermissionManagerService = this.f$0;
                String[] strArr = this.f$1;
                int i = this.f$2;
                DefaultPermissionGrantPolicy defaultPermissionGrantPolicy =
                        legacyPermissionManagerService.mDefaultPermissionGrantPolicy;
                defaultPermissionGrantPolicy.getClass();
                Log.i(
                        "DefaultPermGrantPolicy",
                        "Granting permissions to enabled ImsServices for user:" + i);
                if (strArr != null) {
                    for (String str : strArr) {
                        defaultPermissionGrantPolicy.grantPermissionsToSystemPackage(
                                defaultPermissionGrantPolicy.NO_PM_CACHE,
                                str,
                                i,
                                false,
                                DefaultPermissionGrantPolicy.PHONE_PERMISSIONS,
                                DefaultPermissionGrantPolicy.MICROPHONE_PERMISSIONS,
                                DefaultPermissionGrantPolicy.ALWAYS_LOCATION_PERMISSIONS,
                                DefaultPermissionGrantPolicy.CAMERA_PERMISSIONS,
                                DefaultPermissionGrantPolicy.CONTACTS_PERMISSIONS);
                    }
                    break;
                }
                break;
            case 1:
                LegacyPermissionManagerService legacyPermissionManagerService2 = this.f$0;
                String[] strArr2 = this.f$1;
                int i2 = this.f$2;
                DefaultPermissionGrantPolicy defaultPermissionGrantPolicy2 =
                        legacyPermissionManagerService2.mDefaultPermissionGrantPolicy;
                defaultPermissionGrantPolicy2.getClass();
                Log.i(
                        "DefaultPermGrantPolicy",
                        "Granting permissions to enabled carrier apps for user:" + i2);
                if (strArr2 != null) {
                    for (String str2 : strArr2) {
                        defaultPermissionGrantPolicy2.grantPermissionsToSystemPackage(
                                defaultPermissionGrantPolicy2.NO_PM_CACHE,
                                str2,
                                i2,
                                false,
                                DefaultPermissionGrantPolicy.PHONE_PERMISSIONS,
                                DefaultPermissionGrantPolicy.ALWAYS_LOCATION_PERMISSIONS,
                                DefaultPermissionGrantPolicy.SMS_PERMISSIONS);
                    }
                    break;
                }
                break;
            case 2:
                LegacyPermissionManagerService legacyPermissionManagerService3 = this.f$0;
                String[] strArr3 = this.f$1;
                int i3 = this.f$2;
                DefaultPermissionGrantPolicy defaultPermissionGrantPolicy3 =
                        legacyPermissionManagerService3.mDefaultPermissionGrantPolicy;
                defaultPermissionGrantPolicy3.getClass();
                Log.i("DefaultPermGrantPolicy", "Revoke permissions from LUI apps for user:" + i3);
                if (strArr3 != null) {
                    for (String str3 : strArr3) {
                        DefaultPermissionGrantPolicy.AnonymousClass1 anonymousClass1 =
                                defaultPermissionGrantPolicy3.NO_PM_CACHE;
                        PackageInfo systemPackageInfo = anonymousClass1.getSystemPackageInfo(str3);
                        if (anonymousClass1.isSystemPackage(systemPackageInfo)
                                && DefaultPermissionGrantPolicy
                                        .doesPackageSupportRuntimePermissions(systemPackageInfo)) {
                            DefaultPermissionGrantPolicy.revokeRuntimePermissions(
                                    anonymousClass1,
                                    str3,
                                    DefaultPermissionGrantPolicy.CAMERA_PERMISSIONS,
                                    true,
                                    i3);
                        }
                    }
                    break;
                }
                break;
            case 3:
                LegacyPermissionManagerService legacyPermissionManagerService4 = this.f$0;
                String[] strArr4 = this.f$1;
                int i4 = this.f$2;
                DefaultPermissionGrantPolicy defaultPermissionGrantPolicy4 =
                        legacyPermissionManagerService4.mDefaultPermissionGrantPolicy;
                defaultPermissionGrantPolicy4.getClass();
                Log.i(
                        "DefaultPermGrantPolicy",
                        "Granting permissions to enabled data services for user:" + i4);
                if (strArr4 != null) {
                    for (String str4 : strArr4) {
                        defaultPermissionGrantPolicy4.grantPermissionsToSystemPackage(
                                defaultPermissionGrantPolicy4.NO_PM_CACHE,
                                str4,
                                i4,
                                true,
                                DefaultPermissionGrantPolicy.PHONE_PERMISSIONS,
                                DefaultPermissionGrantPolicy.ALWAYS_LOCATION_PERMISSIONS);
                    }
                    break;
                }
                break;
            default:
                LegacyPermissionManagerService legacyPermissionManagerService5 = this.f$0;
                String[] strArr5 = this.f$1;
                int i5 = this.f$2;
                DefaultPermissionGrantPolicy defaultPermissionGrantPolicy5 =
                        legacyPermissionManagerService5.mDefaultPermissionGrantPolicy;
                defaultPermissionGrantPolicy5.getClass();
                Log.i(
                        "DefaultPermGrantPolicy",
                        "Revoking permissions from disabled data services for user:" + i5);
                if (strArr5 != null) {
                    for (String str5 : strArr5) {
                        DefaultPermissionGrantPolicy.AnonymousClass1 anonymousClass12 =
                                defaultPermissionGrantPolicy5.NO_PM_CACHE;
                        PackageInfo systemPackageInfo2 =
                                anonymousClass12.getSystemPackageInfo(str5);
                        if (anonymousClass12.isSystemPackage(systemPackageInfo2)
                                && DefaultPermissionGrantPolicy
                                        .doesPackageSupportRuntimePermissions(systemPackageInfo2)) {
                            DefaultPermissionGrantPolicy.revokeRuntimePermissions(
                                    anonymousClass12,
                                    str5,
                                    DefaultPermissionGrantPolicy.PHONE_PERMISSIONS,
                                    true,
                                    i5);
                            DefaultPermissionGrantPolicy.revokeRuntimePermissions(
                                    anonymousClass12,
                                    str5,
                                    DefaultPermissionGrantPolicy.ALWAYS_LOCATION_PERMISSIONS,
                                    true,
                                    i5);
                        }
                    }
                    break;
                }
                break;
        }
    }
}
