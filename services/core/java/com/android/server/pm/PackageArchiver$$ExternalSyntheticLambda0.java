package com.android.server.pm;

import android.content.Intent;
import android.content.pm.LauncherApps;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.UserHandle;
import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PackageArchiver$$ExternalSyntheticLambda0 implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PackageArchiver f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ PackageArchiver$$ExternalSyntheticLambda0(PackageArchiver packageArchiver, int i, String str) {
        this.$r8$classId = 0;
        this.f$0 = packageArchiver;
        this.f$1 = i;
        this.f$2 = str;
    }

    public /* synthetic */ PackageArchiver$$ExternalSyntheticLambda0(PackageArchiver packageArchiver, Object obj, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = packageArchiver;
        this.f$2 = obj;
        this.f$1 = i;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(this.f$0.getAppOpsManager().checkOpNoThrow(97, this.f$1, (String) this.f$2) == 1);
            case 1:
                PackageArchiver packageArchiver = this.f$0;
                String str = (String) this.f$2;
                int i = this.f$1;
                if (packageArchiver.mLauncherApps == null) {
                    packageArchiver.mLauncherApps = (LauncherApps) packageArchiver.mContext.getSystemService(LauncherApps.class);
                }
                return packageArchiver.mLauncherApps.getActivityList(str, new UserHandle(i));
            default:
                PackageArchiver packageArchiver2 = this.f$0;
                Intent intent = (Intent) this.f$2;
                int i2 = this.f$1;
                PackageManagerService packageManagerService = packageArchiver2.mPm;
                return new ParceledListSlice(packageManagerService.mResolveIntentHelper.queryIntentReceiversInternal(packageManagerService.snapshotComputer(), intent, null, 0L, i2, Binder.getCallingUid(), -1, false));
        }
    }
}
