package com.android.systemui.wmshell;

import android.view.KeyEvent;
import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class WMShell$12$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WMShell$12$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                WMShell wMShell = WMShell.this;
                SysUiState flag = wMShell.mSysUiState.setFlag(65536L, false);
                wMShell.mDisplayTracker.getClass();
                ((SysUiStateImpl) flag).commitUpdate();
                break;
            case 1:
                WMShell wMShell2 = WMShell.this;
                SysUiState flag2 = wMShell2.mSysUiState.setFlag(65536L, true);
                wMShell2.mDisplayTracker.getClass();
                ((SysUiStateImpl) flag2).commitUpdate();
                break;
            case 2:
                WMShell wMShell3 = WMShell.this;
                SysUiState flag3 = wMShell3.mSysUiState.setFlag(65536L, true);
                wMShell3.mDisplayTracker.getClass();
                ((SysUiStateImpl) flag3).commitUpdate();
                break;
            default:
                WMShell.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                break;
        }
    }
}
