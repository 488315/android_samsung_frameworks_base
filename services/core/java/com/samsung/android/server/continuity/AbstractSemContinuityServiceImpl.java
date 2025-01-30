package com.samsung.android.server.continuity;

import android.content.Context;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.samsung.android.continuity.ISemContinuityManager;
import com.samsung.android.server.continuity.common.ExecutorUtil;

/* loaded from: classes2.dex */
public abstract class AbstractSemContinuityServiceImpl extends ISemContinuityManager.Stub {
    public int mCurrentUserId = -10000;
    public final McfDeviceSyncManager mMcfDsManager;
    public final UserManager mUserManager;

    public abstract void setCurrentUserHandle(UserHandle userHandle);

    public AbstractSemContinuityServiceImpl(Context context, McfDeviceSyncManager mcfDeviceSyncManager) {
        ExecutorUtil.start();
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mMcfDsManager = mcfDeviceSyncManager;
    }

    public void onUserSwitching(final UserHandle userHandle) {
        ExecutorUtil.executeOnMain(new Runnable() { // from class: com.samsung.android.server.continuity.AbstractSemContinuityServiceImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AbstractSemContinuityServiceImpl.this.lambda$onUserSwitching$0(userHandle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUserSwitching$0(UserHandle userHandle) {
        if (this.mCurrentUserId != -10000) {
            this.mMcfDsManager.stopUser();
        }
        this.mCurrentUserId = userHandle.semGetIdentifier();
        setCurrentUserHandle(userHandle);
        this.mMcfDsManager.startUser(userHandle);
    }

    public int getNearbyDeviceCount(int i, int i2) {
        if (this.mMcfDsManager.getCurrentUserId() != i2) {
            Log.w("[MCF_DS_SYS]_SemContinuityServiceImpl", "getNearbyDeviceCount - invalid user " + i2);
            return 0;
        }
        return this.mMcfDsManager.getNearbyDeviceCount(i);
    }
}
