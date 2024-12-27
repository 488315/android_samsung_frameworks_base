package com.android.systemui.wmshell;

import com.android.systemui.model.SysUiState;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.pip.phone.PipController;
import com.android.wm.shell.splitscreen.EnterSplitGestureHandler;
import com.android.wm.shell.splitscreen.EnterSplitGestureHandler$$ExternalSyntheticLambda1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class WMShell$$ExternalSyntheticLambda0 implements SysUiState.SysUiStateCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WMShell f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ WMShell$$ExternalSyntheticLambda0(WMShell wMShell, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = wMShell;
        this.f$1 = obj;
    }

    @Override // com.android.systemui.model.SysUiState.SysUiStateCallback
    public final void onSystemUiStateChanged(final long j) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.getClass();
                final boolean z = (8440396 & j) == 0;
                final PipController.PipImpl pipImpl = (PipController.PipImpl) ((Pip) this.f$1);
                ((HandlerExecutor) PipController.this.mMainExecutor).execute(new Runnable(z, j) { // from class: com.android.wm.shell.pip.phone.PipController$PipImpl$$ExternalSyntheticLambda0
                    public final /* synthetic */ boolean f$1;

                    @Override // java.lang.Runnable
                    public final void run() {
                        PipController.this.mTouchHandler.mPipResizeGestureHandler.mIsSysUiStateValid = this.f$1;
                    }
                });
                break;
            default:
                this.f$0.getClass();
                boolean z2 = (8440396 & j) == 0;
                EnterSplitGestureHandler enterSplitGestureHandler = (EnterSplitGestureHandler) this.f$1;
                enterSplitGestureHandler.getClass();
                boolean z3 = z2 && (2 & j) == 0;
                boolean z4 = (j & 16) != 0;
                if (enterSplitGestureHandler.mIsSystemUiStateValid != z3) {
                    enterSplitGestureHandler.mIsSystemUiStateValid = z3;
                    ((HandlerExecutor) enterSplitGestureHandler.mMainExecutor).execute(new EnterSplitGestureHandler$$ExternalSyntheticLambda1(enterSplitGestureHandler, 1));
                }
                if (enterSplitGestureHandler.mIsA11yButtonEnabled != z4) {
                    enterSplitGestureHandler.mIsA11yButtonEnabled = z4;
                    break;
                }
                break;
        }
    }
}
