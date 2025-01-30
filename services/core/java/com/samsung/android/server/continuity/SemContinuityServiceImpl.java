package com.samsung.android.server.continuity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import com.samsung.android.continuity.ISemContinuitySimpleListener;
import com.samsung.android.mcfds.lib.DeviceSyncManager;

/* loaded from: classes2.dex */
public class SemContinuityServiceImpl extends AbstractSemContinuityServiceImpl {
    public final BroadcastReceiver mBrReceiver;
    public UserHandle mCurrentUserHandle;

    public SemContinuityServiceImpl(Context context, McfDeviceSyncManager mcfDeviceSyncManager) {
        super(context, mcfDeviceSyncManager);
        this.mBrReceiver = new BroadcastReceiver() { // from class: com.samsung.android.server.continuity.SemContinuityServiceImpl.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (action != null && "com.samsung.intent.action.EMERGENCY_STATE_CHANGED".equals(action)) {
                    int intExtra = intent.getIntExtra("reason", -1);
                    Log.d("[MCF_DS_SYS]_SemContinuityServiceImpl", "EMERGENCY_STATE_CHANGED : " + intExtra);
                    if (intExtra == 2) {
                        SemContinuityServiceImpl.this.mMcfDsManager.stopUser();
                    } else if (intExtra == 5) {
                        SemContinuityServiceImpl semContinuityServiceImpl = SemContinuityServiceImpl.this;
                        semContinuityServiceImpl.mMcfDsManager.startUser(semContinuityServiceImpl.mCurrentUserHandle);
                    }
                }
            }
        };
        registerReceiver(context);
    }

    public static SemContinuityServiceImpl createInstance(Context context) {
        return new SemContinuityServiceImpl(context, new McfDeviceSyncManager(new PreconditionObserver(context), new DeviceSyncManager(context)));
    }

    @Override // com.samsung.android.server.continuity.AbstractSemContinuityServiceImpl
    public void setCurrentUserHandle(UserHandle userHandle) {
        this.mCurrentUserHandle = userHandle;
    }

    public void onUserUnlocking(UserHandle userHandle) {
        int semGetIdentifier = userHandle.semGetIdentifier();
        boolean isManagedProfile = this.mUserManager.isManagedProfile();
        Log.i("[MCF_DS_SYS]_SemContinuityServiceImpl", "onUserUnlocking - " + semGetIdentifier + ", " + isManagedProfile);
        if (semGetIdentifier != ActivityManager.semGetCurrentUser() || isManagedProfile) {
            return;
        }
        this.mCurrentUserId = semGetIdentifier;
        this.mCurrentUserHandle = userHandle;
        this.mMcfDsManager.startUser(userHandle);
    }

    public final void registerReceiver(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
        context.semRegisterReceiverAsUser(this.mBrReceiver, UserHandle.SEM_ALL, intentFilter, null, null);
    }

    public void setLocalClip(Bundle bundle, int i) {
        throw new UnsupportedOperationException();
    }

    public void clearLocalClip(int i) {
        throw new UnsupportedOperationException();
    }

    public void registerContinuityCopyListener(ISemContinuitySimpleListener iSemContinuitySimpleListener, int i) {
        throw new UnsupportedOperationException();
    }

    public void unregisterContinuityCopyListener(int i) {
        throw new UnsupportedOperationException();
    }

    public boolean requestDownload(String str, ISemContinuitySimpleListener iSemContinuitySimpleListener, int i) {
        throw new UnsupportedOperationException();
    }

    public void cancelDownload(String str, int i) {
        throw new UnsupportedOperationException();
    }

    public BroadcastReceiver getBroadcastReceiver() {
        return this.mBrReceiver;
    }
}
