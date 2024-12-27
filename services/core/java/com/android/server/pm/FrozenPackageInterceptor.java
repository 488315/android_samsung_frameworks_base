package com.android.server.pm;

import android.content.Context;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManagerInternal;

import com.android.internal.app.FrozenAppActivity;
import com.android.server.LocalServices;
import com.android.server.wm.ActivityInterceptorCallback;

public final class FrozenPackageInterceptor {
    public final Context mContext;
    public final AnonymousClass1 mActivityInterceptorCallback =
            new ActivityInterceptorCallback() { // from class:
                                                // com.android.server.pm.FrozenPackageInterceptor.1
                @Override // com.android.server.wm.ActivityInterceptorCallback
                public final ActivityInterceptorCallback.ActivityInterceptResult
                        onInterceptActivityLaunch(
                                ActivityInterceptorCallback.ActivityInterceptorInfo
                                        activityInterceptorInfo) {
                    if (activityInterceptorInfo.getActivityInfo() == null) {
                        return null;
                    }
                    String str = activityInterceptorInfo.getActivityInfo().packageName;
                    FrozenPackageInterceptor frozenPackageInterceptor =
                            FrozenPackageInterceptor.this;
                    if (frozenPackageInterceptor.mPMInternal.isPackageFrozen(
                            activityInterceptorInfo.getCallingUid(),
                            activityInterceptorInfo.getUserId(),
                            str)) {
                        for (PackageInstaller.SessionInfo sessionInfo :
                                frozenPackageInterceptor
                                        .mContext
                                        .getPackageManager()
                                        .getPackageInstaller()
                                        .getAllSessions()) {
                            if (str.equals(sessionInfo.getAppPackageName())
                                    && sessionInfo.isCommitted()) {
                                return new ActivityInterceptorCallback.ActivityInterceptResult(
                                        FrozenAppActivity.createIntent(
                                                activityInterceptorInfo.getUserId(), str),
                                        activityInterceptorInfo.getCheckedOptions());
                            }
                        }
                    }
                    return null;
                }
            };
    public final PackageManagerInternal mPMInternal =
            (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.pm.FrozenPackageInterceptor$1] */
    public FrozenPackageInterceptor(Context context) {
        this.mContext = context;
    }
}
