package com.android.server.pm;

import android.content.Context;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.PackageInstaller;
import com.android.internal.app.FrozenAppActivity;
import com.android.server.LocalServices;
import com.android.server.wm.ActivityInterceptorCallback;
import com.android.server.wm.ActivityTaskManagerInternal;

/* loaded from: classes3.dex */
public final class FrozenPackageInterceptor {
    public final Context mContext;
    public final ActivityInterceptorCallback mActivityInterceptorCallback = new ActivityInterceptorCallback() { // from class: com.android.server.pm.FrozenPackageInterceptor.1
        @Override // com.android.server.wm.ActivityInterceptorCallback
        public ActivityInterceptorCallback.ActivityInterceptResult onInterceptActivityLaunch(ActivityInterceptorCallback.ActivityInterceptorInfo activityInterceptorInfo) {
            if (activityInterceptorInfo.getActivityInfo() == null) {
                return null;
            }
            String str = activityInterceptorInfo.getActivityInfo().packageName;
            if (FrozenPackageInterceptor.this.mPMInternal.isPackageFrozen(str, activityInterceptorInfo.getCallingUid(), activityInterceptorInfo.getUserId()) && FrozenPackageInterceptor.this.isPackageBeingInstalled(str)) {
                return new ActivityInterceptorCallback.ActivityInterceptResult(FrozenAppActivity.createIntent(activityInterceptorInfo.getUserId(), str), activityInterceptorInfo.getCheckedOptions());
            }
            return null;
        }
    };
    public final PackageManagerInternal mPMInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);

    public FrozenPackageInterceptor(Context context) {
        this.mContext = context;
    }

    public void registerListeners() {
        ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).registerActivityStartInterceptor(6, this.mActivityInterceptorCallback);
    }

    public final boolean isPackageBeingInstalled(String str) {
        for (PackageInstaller.SessionInfo sessionInfo : this.mContext.getPackageManager().getPackageInstaller().getAllSessions()) {
            if (str.equals(sessionInfo.getAppPackageName()) && sessionInfo.isCommitted()) {
                return true;
            }
        }
        return false;
    }
}
