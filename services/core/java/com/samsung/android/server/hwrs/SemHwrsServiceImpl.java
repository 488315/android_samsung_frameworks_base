package com.samsung.android.server.hwrs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;

public final class SemHwrsServiceImpl extends AbstractSemHwrsServiceImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 mBrReceiver;
    public UserHandle mCurrentUserHandle;

    public SemHwrsServiceImpl(Context context, PreconditionObserver preconditionObserver) {
        super(context, preconditionObserver);
        ?? r1 = new BroadcastReceiver() { // from class: com.samsung.android.server.hwrs.SemHwrsServiceImpl.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (action != null && "com.samsung.intent.action.EMERGENCY_STATE_CHANGED".equals(action)) {
                    int intExtra = intent.getIntExtra("reason", -1);
                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(intExtra, "EMERGENCY_STATE_CHANGED : ", "[HWRS_SYS]SemHwrsService");
                    if (intExtra == 2) {
                        SemHwrsServiceImpl.this.mPrecondManager.stopUser();
                    } else if (intExtra == 5) {
                        SemHwrsServiceImpl semHwrsServiceImpl = SemHwrsServiceImpl.this;
                        semHwrsServiceImpl.mPrecondManager.startUser(semHwrsServiceImpl.mCurrentUserHandle);
                    }
                }
            }
        };
        this.mBrReceiver = r1;
        context.semRegisterReceiverAsUser(r1, UserHandle.SEM_ALL, BatteryService$$ExternalSyntheticOutline0.m("com.samsung.intent.action.EMERGENCY_STATE_CHANGED"), null, null);
    }

    public BroadcastReceiver getBroadcastReceiver() {
        return this.mBrReceiver;
    }

    @Override // com.samsung.android.server.hwrs.AbstractSemHwrsServiceImpl
    public final void setCurrentUserHandle(UserHandle userHandle) {
        this.mCurrentUserHandle = userHandle;
    }
}
