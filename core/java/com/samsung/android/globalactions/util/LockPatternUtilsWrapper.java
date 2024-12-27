package com.samsung.android.globalactions.util;

import android.app.ActivityManager;
import android.app.trust.TrustManager;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserManager;
import android.view.WindowManagerGlobal;

import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.LockPatternUtils;

/* loaded from: classes6.dex */
public class LockPatternUtilsWrapper {
    private static final String TAG = "LockPatternUtilsWrapper";
    private final Context mContext;
    private HandlerUtil mHandlerUtil;
    private ILockSettings mILockSettings =
            ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
    private final LockPatternUtils mLockPatternUtils;
    private final LogWrapper mLogWrapper;

    public LockPatternUtilsWrapper(
            Context context, LogWrapper logWrapper, HandlerUtil handlerUtil) {
        this.mContext = context;
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mLogWrapper = logWrapper;
        this.mHandlerUtil = handlerUtil;
    }

    public boolean isFMMLocked() {
        return this.mLockPatternUtils.isFMMLockEnabled(ActivityManager.getCurrentUser());
    }

    public boolean isCarrierLockPlusEnabled() {
        try {
            return this.mILockSettings.getCarrierLock(ActivityManager.getCurrentUser());
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean isRMMLocked() {
        return this.mLockPatternUtils.isRMMLockEnabled(ActivityManager.getCurrentUser());
    }

    public boolean isUserUnLocked() {
        UserManager userManager = (UserManager) this.mContext.getSystemService("user");
        return userManager != null && userManager.isUserUnlocked();
    }

    public boolean isStrongAuthForLockDown() {
        int state = this.mLockPatternUtils.getStrongAuthForUser(ActivityManager.getCurrentUser());
        return state == 0 || state == 4;
    }

    public void lockDown() {
        this.mLogWrapper.i(TAG, "lockDown");
        this.mLockPatternUtils.requireStrongAuth(32, -1);
        try {
            WindowManagerGlobal.getWindowManagerService().lockNow(null);
            new Thread(
                            new Runnable() { // from class:
                                             // com.samsung.android.globalactions.util.LockPatternUtilsWrapper$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    LockPatternUtilsWrapper.this.lambda$lockDown$0();
                                }
                            })
                    .start();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: lockProfiles, reason: merged with bridge method [inline-methods] */
    public void lambda$lockDown$0() {
        UserManager um = (UserManager) this.mContext.getSystemService("user");
        TrustManager tm = (TrustManager) this.mContext.getSystemService(Context.TRUST_SERVICE);
        int currentUserId = ActivityManager.getCurrentUser();
        int[] profileIds = um.getEnabledProfileIds(currentUserId);
        for (int id : profileIds) {
            if (id != currentUserId) {
                tm.setDeviceLockedForUser(id, true);
            }
        }
    }

    public void lockDownDelayed(int delay) {
        this.mHandlerUtil.postDelayed(
                new Runnable() { // from class:
                                 // com.samsung.android.globalactions.util.LockPatternUtilsWrapper$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        LockPatternUtilsWrapper.this.lockDown();
                    }
                },
                delay);
    }
}
