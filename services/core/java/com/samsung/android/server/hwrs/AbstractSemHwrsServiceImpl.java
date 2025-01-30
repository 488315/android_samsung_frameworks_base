package com.samsung.android.server.hwrs;

import android.content.Context;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.samsung.android.hwrs.ISemHwrsManager;
import com.samsung.android.server.hwrs.common.HwrsUtils;

/* loaded from: classes2.dex */
public abstract class AbstractSemHwrsServiceImpl extends ISemHwrsManager.Stub {
    public int mCurrentUserId = -10000;
    public final PreconditionObserver mPrecondManager;
    public final UserManager mUserManager;

    public abstract void setCurrentUserHandle(UserHandle userHandle);

    public AbstractSemHwrsServiceImpl(Context context, PreconditionObserver preconditionObserver) {
        Log.d("[HWRS_SYS]SemHwrsService", "AbstractSemHwrsServiceImpl entered");
        HwrsUtils.start();
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mPrecondManager = preconditionObserver;
    }

    public void onUserSwitching(final UserHandle userHandle) {
        HwrsUtils.executeOnMain(new Runnable() { // from class: com.samsung.android.server.hwrs.AbstractSemHwrsServiceImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AbstractSemHwrsServiceImpl.this.lambda$onUserSwitching$0(userHandle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUserSwitching$0(UserHandle userHandle) {
        if (this.mCurrentUserId != -10000) {
            Log.d("[HWRS_SYS]SemHwrsService", "stopUser entered");
            this.mPrecondManager.stopUser();
        }
        this.mCurrentUserId = userHandle.semGetIdentifier();
        setCurrentUserHandle(userHandle);
        this.mPrecondManager.startUser(userHandle);
        Log.d("[HWRS_SYS]SemHwrsService", "startUser entered");
    }

    public boolean isCameraShareEnable() {
        Log.d("[HWRS_SYS]SemHwrsService", "test call");
        return this.mPrecondManager.isHWRSEnable();
    }
}
