package com.android.systemui.statusbar.phone;

import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;

/* loaded from: classes3.dex */
public final /* synthetic */ class SystemUIDialog$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SystemUIDialog f$0;

    public /* synthetic */ SystemUIDialog$$ExternalSyntheticLambda0(SystemUIDialog systemUIDialog, int i) {
        this.$r8$classId = i;
        this.f$0 = systemUIDialog;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SystemUIDialog systemUIDialog = this.f$0;
        switch (i) {
            case 0:
                int i2 = SystemUIDialog.$r8$clinit;
                systemUIDialog.updateWindowSize();
                break;
            case 1:
                SysUiState flag = systemUIDialog.mSysUiState.setFlag(32768L, true);
                systemUIDialog.mContext.getDisplayId();
                ((SysUiStateImpl) flag).commitUpdate();
                break;
            default:
                SysUiState flag2 = systemUIDialog.mSysUiState.setFlag(32768L, false);
                systemUIDialog.mContext.getDisplayId();
                ((SysUiStateImpl) flag2).commitUpdate();
                break;
        }
    }
}
