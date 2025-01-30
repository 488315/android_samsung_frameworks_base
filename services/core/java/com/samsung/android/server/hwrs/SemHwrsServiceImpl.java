package com.samsung.android.server.hwrs;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.util.Log;

/* loaded from: classes2.dex */
public class SemHwrsServiceImpl extends AbstractSemHwrsServiceImpl {
    public final BroadcastReceiver mBrReceiver;
    public UserHandle mCurrentUserHandle;

    @Override // com.samsung.android.server.hwrs.AbstractSemHwrsServiceImpl
    public /* bridge */ /* synthetic */ boolean isCameraShareEnable() {
        return super.isCameraShareEnable();
    }

    @Override // com.samsung.android.server.hwrs.AbstractSemHwrsServiceImpl
    public /* bridge */ /* synthetic */ void onUserSwitching(UserHandle userHandle) {
        super.onUserSwitching(userHandle);
    }

    public SemHwrsServiceImpl(Context context, PreconditionObserver preconditionObserver) {
        super(context, preconditionObserver);
        this.mBrReceiver = new BroadcastReceiver() { // from class: com.samsung.android.server.hwrs.SemHwrsServiceImpl.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (action != null && "com.samsung.intent.action.EMERGENCY_STATE_CHANGED".equals(action)) {
                    int intExtra = intent.getIntExtra("reason", -1);
                    Log.d("[HWRS_SYS]SemHwrsService", "EMERGENCY_STATE_CHANGED : " + intExtra);
                    if (intExtra == 2) {
                        SemHwrsServiceImpl.this.mPrecondManager.stopUser();
                    } else if (intExtra == 5) {
                        SemHwrsServiceImpl semHwrsServiceImpl = SemHwrsServiceImpl.this;
                        semHwrsServiceImpl.mPrecondManager.startUser(semHwrsServiceImpl.mCurrentUserHandle);
                    }
                }
            }
        };
        registerReceiver(context);
    }

    public static SemHwrsServiceImpl createInstance(Context context) {
        Log.d("[HWRS_SYS]SemHwrsService", "createInstance entered");
        return new SemHwrsServiceImpl(context, new PreconditionObserver(context));
    }

    @Override // com.samsung.android.server.hwrs.AbstractSemHwrsServiceImpl
    public void setCurrentUserHandle(UserHandle userHandle) {
        this.mCurrentUserHandle = userHandle;
    }

    public void onUserUnlocking(UserHandle userHandle) {
        int semGetIdentifier = userHandle.semGetIdentifier();
        boolean isManagedProfile = this.mUserManager.isManagedProfile();
        Log.i("[HWRS_SYS]SemHwrsService", "onUserUnlocking - " + semGetIdentifier + ", " + isManagedProfile);
        if (semGetIdentifier != ActivityManager.semGetCurrentUser() || isManagedProfile) {
            return;
        }
        this.mCurrentUserId = semGetIdentifier;
        this.mCurrentUserHandle = userHandle;
        this.mPrecondManager.startUser(userHandle);
    }

    public final void registerReceiver(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
        context.semRegisterReceiverAsUser(this.mBrReceiver, UserHandle.SEM_ALL, intentFilter, null, null);
    }

    public BroadcastReceiver getBroadcastReceiver() {
        return this.mBrReceiver;
    }
}
