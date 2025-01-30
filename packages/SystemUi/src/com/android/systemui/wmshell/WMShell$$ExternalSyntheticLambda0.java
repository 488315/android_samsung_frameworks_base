package com.android.systemui.wmshell;

import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.pip.Pip;
import com.android.p038wm.shell.splitscreen.EnterSplitGestureHandler;
import com.android.p038wm.shell.splitscreen.EnterSplitGestureHandler$$ExternalSyntheticLambda1;
import com.android.systemui.model.SysUiState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
    public final void onSystemUiStateChanged(long j) {
        int i = this.$r8$classId;
        WMShell wMShell = this.f$0;
        Object obj = this.f$1;
        switch (i) {
            case 0:
                Pip pip = (Pip) obj;
                wMShell.getClass();
                pip.onSystemUiStateChanged(j, (8440396 & j) == 0);
                break;
            default:
                EnterSplitGestureHandler enterSplitGestureHandler = (EnterSplitGestureHandler) obj;
                wMShell.getClass();
                boolean z = (8440396 & j) == 0;
                enterSplitGestureHandler.getClass();
                boolean z2 = z && (2 & j) == 0;
                boolean z3 = (j & 16) != 0;
                if (enterSplitGestureHandler.mIsSystemUiStateValid != z2) {
                    enterSplitGestureHandler.mIsSystemUiStateValid = z2;
                    ((HandlerExecutor) enterSplitGestureHandler.mMainExecutor).execute(new EnterSplitGestureHandler$$ExternalSyntheticLambda1(enterSplitGestureHandler, 1));
                }
                if (enterSplitGestureHandler.mIsA11yButtonEnabled != z3) {
                    enterSplitGestureHandler.mIsA11yButtonEnabled = z3;
                    break;
                }
                break;
        }
    }
}
