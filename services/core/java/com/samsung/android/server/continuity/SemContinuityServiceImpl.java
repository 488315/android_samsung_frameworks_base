package com.samsung.android.server.continuity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.samsung.android.continuity.ISemContinuitySimpleListener;
import com.samsung.android.server.continuity.sem.SemWrapper;

public final class SemContinuityServiceImpl extends AbstractSemContinuityServiceImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 mBrReceiver;
    public UserHandle mCurrentUserHandle;

    public SemContinuityServiceImpl(Context context, McfDeviceSyncManager mcfDeviceSyncManager) {
        super(context, mcfDeviceSyncManager);
        ?? r1 = new BroadcastReceiver() { // from class: com.samsung.android.server.continuity.SemContinuityServiceImpl.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (action != null && "com.samsung.intent.action.EMERGENCY_STATE_CHANGED".equals(action)) {
                    int intExtra = intent.getIntExtra("reason", -1);
                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(intExtra, "EMERGENCY_STATE_CHANGED : ", "[MCF_DS_SYS]_SemContinuityServiceImpl");
                    if (intExtra == 2) {
                        SemContinuityServiceImpl.this.mMcfDsManager.stopUser();
                    } else if (intExtra == 5) {
                        SemContinuityServiceImpl semContinuityServiceImpl = SemContinuityServiceImpl.this;
                        semContinuityServiceImpl.mMcfDsManager.startUser(semContinuityServiceImpl.mCurrentUserHandle);
                    }
                }
            }
        };
        this.mBrReceiver = r1;
        context.semRegisterReceiverAsUser(r1, SemWrapper.SEM_ALL, BatteryService$$ExternalSyntheticOutline0.m("com.samsung.intent.action.EMERGENCY_STATE_CHANGED"), null, null, 2);
    }

    public final void cancelDownload(String str, int i) {
        throw new UnsupportedOperationException();
    }

    public final void clearLocalClip(int i) {
        throw new UnsupportedOperationException();
    }

    public BroadcastReceiver getBroadcastReceiver() {
        return this.mBrReceiver;
    }

    public final void registerContinuityCopyListener(ISemContinuitySimpleListener iSemContinuitySimpleListener, int i) {
        throw new UnsupportedOperationException();
    }

    public final boolean requestDownload(String str, ISemContinuitySimpleListener iSemContinuitySimpleListener, int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.samsung.android.server.continuity.AbstractSemContinuityServiceImpl
    public final void setCurrentUserHandle(UserHandle userHandle) {
        this.mCurrentUserHandle = userHandle;
    }

    public final void setLocalClip(Bundle bundle, int i) {
        throw new UnsupportedOperationException();
    }

    public final void unregisterContinuityCopyListener(int i) {
        throw new UnsupportedOperationException();
    }
}
