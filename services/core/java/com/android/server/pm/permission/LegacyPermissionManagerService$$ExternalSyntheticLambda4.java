package com.android.server.pm.permission;

import android.util.Log;
import com.android.internal.util.FunctionalUtils;
import com.android.server.pm.permission.DefaultPermissionGrantPolicy;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class LegacyPermissionManagerService$$ExternalSyntheticLambda4 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LegacyPermissionManagerService f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ LegacyPermissionManagerService$$ExternalSyntheticLambda4(LegacyPermissionManagerService legacyPermissionManagerService, String str, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = legacyPermissionManagerService;
        this.f$1 = str;
        this.f$2 = i;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                LegacyPermissionManagerService legacyPermissionManagerService = this.f$0;
                String str = this.f$1;
                int i = this.f$2;
                DefaultPermissionGrantPolicy defaultPermissionGrantPolicy = legacyPermissionManagerService.mDefaultPermissionGrantPolicy;
                defaultPermissionGrantPolicy.getClass();
                Log.i("DefaultPermGrantPolicy", "Granting permissions to active LUI app for user:" + i);
                defaultPermissionGrantPolicy.grantPermissionsToSystemPackage(defaultPermissionGrantPolicy.NO_PM_CACHE, str, i, true, DefaultPermissionGrantPolicy.CAMERA_PERMISSIONS, DefaultPermissionGrantPolicy.NOTIFICATION_PERMISSIONS);
                break;
            default:
                LegacyPermissionManagerService legacyPermissionManagerService2 = this.f$0;
                String str2 = this.f$1;
                int i2 = this.f$2;
                DefaultPermissionGrantPolicy defaultPermissionGrantPolicy2 = legacyPermissionManagerService2.mDefaultPermissionGrantPolicy;
                defaultPermissionGrantPolicy2.getClass();
                Log.i("DefaultPermGrantPolicy", "Grant permissions to Carrier Service app " + str2 + " for user:" + i2);
                Set[] setArr = {DefaultPermissionGrantPolicy.NOTIFICATION_PERMISSIONS};
                DefaultPermissionGrantPolicy.AnonymousClass1 anonymousClass1 = defaultPermissionGrantPolicy2.NO_PM_CACHE;
                defaultPermissionGrantPolicy2.grantPermissionsToPackage(anonymousClass1, anonymousClass1.getPackageInfo(str2), i2, false, false, setArr);
                break;
        }
    }
}
