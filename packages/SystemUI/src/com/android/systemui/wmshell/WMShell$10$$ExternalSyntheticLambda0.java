package com.android.systemui.wmshell;

import android.view.KeyEvent;
import com.android.systemui.model.SysUiState;
import com.android.systemui.wmshell.WMShell;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class WMShell$10$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WMShell$10$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                WMShell wMShell = ((WMShell.AnonymousClass10) obj).this$0;
                SysUiState sysUiState = wMShell.mSysUiState;
                sysUiState.setFlag(65536L, true);
                wMShell.mDisplayTracker.getClass();
                sysUiState.commitUpdate(0);
                break;
            case 1:
                WMShell wMShell2 = ((WMShell.AnonymousClass10) obj).this$0;
                SysUiState sysUiState2 = wMShell2.mSysUiState;
                sysUiState2.setFlag(65536L, false);
                wMShell2.mDisplayTracker.getClass();
                sysUiState2.commitUpdate(0);
                break;
            case 2:
                WMShell wMShell3 = ((WMShell.AnonymousClass10) obj).this$0;
                SysUiState sysUiState3 = wMShell3.mSysUiState;
                sysUiState3.setFlag(65536L, true);
                wMShell3.mDisplayTracker.getClass();
                sysUiState3.commitUpdate(0);
                break;
            default:
                ((WMShell.AnonymousClass11) obj).this$0.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                break;
        }
    }
}
