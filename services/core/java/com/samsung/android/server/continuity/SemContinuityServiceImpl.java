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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SemContinuityServiceImpl extends AbstractSemContinuityServiceImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 mBrReceiver;
    public UserHandle mCurrentUserHandle;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.content.BroadcastReceiver, com.samsung.android.server.continuity.SemContinuityServiceImpl$1] */
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
