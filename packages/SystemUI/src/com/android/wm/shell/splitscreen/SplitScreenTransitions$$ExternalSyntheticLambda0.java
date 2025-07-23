package com.android.wm.shell.splitscreen;

import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.wmshell.WMShell;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenTransitions$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SplitScreenTransitions f$0;

    public /* synthetic */ SplitScreenTransitions$$ExternalSyntheticLambda0(SplitScreenTransitions splitScreenTransitions, int i) {
        this.$r8$classId = i;
        this.f$0 = splitScreenTransitions;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SplitScreenTransitions splitScreenTransitions = this.f$0;
        switch (i) {
            case 0:
                WMShell wMShell = WMShell.this;
                SysUiState flag = wMShell.mSysUiState.setFlag(4096L, true);
                wMShell.mDisplayTracker.getClass();
                ((SysUiStateImpl) flag).commitUpdate();
                break;
            case 1:
                splitScreenTransitions.onFinish(null);
                break;
            case 2:
                splitScreenTransitions.onFinish(null);
                break;
            default:
                splitScreenTransitions.onFinish(null);
                break;
        }
    }
}
