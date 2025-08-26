package com.android.systemui.wmshell;

import com.android.systemui.model.SysUiState;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.splitscreen.EnterSplitGestureHandler;
import com.android.wm.shell.splitscreen.EnterSplitGestureHandler$$ExternalSyntheticLambda1;

/* loaded from: classes3.dex */
public final /* synthetic */ class WMShell$$ExternalSyntheticLambda1 implements SysUiState.SysUiStateCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WMShell f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ WMShell$$ExternalSyntheticLambda1(WMShell wMShell, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = wMShell;
        this.f$1 = obj;
    }

    @Override // com.android.systemui.model.SysUiState.SysUiStateCallback
    public final void onSystemUiStateChanged(int i, long j) {
        Object obj = this.f$1;
        switch (this.$r8$classId) {
            case 0:
                int i2 = WMShell.$r8$clinit;
                ((Pip) obj).onSystemUiStateChanged(j, (8440396 & j) == 0);
                break;
            default:
                int i3 = WMShell.$r8$clinit;
                this.f$0.getClass();
                boolean z = (8440396 & j) == 0;
                EnterSplitGestureHandler enterSplitGestureHandler = (EnterSplitGestureHandler) obj;
                enterSplitGestureHandler.getClass();
                boolean z2 = z && (2 & j) == 0;
                boolean z3 = (j & 16) != 0;
                if (enterSplitGestureHandler.mIsSystemUiStateValid != z2) {
                    enterSplitGestureHandler.mIsSystemUiStateValid = z2;
                    enterSplitGestureHandler.mMainExecutor.execute(new EnterSplitGestureHandler$$ExternalSyntheticLambda1(enterSplitGestureHandler, 1));
                }
                if (enterSplitGestureHandler.mIsA11yButtonEnabled != z3) {
                    enterSplitGestureHandler.mIsA11yButtonEnabled = z3;
                    break;
                }
                break;
        }
    }
}
