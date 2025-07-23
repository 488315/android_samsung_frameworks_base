package com.android.systemui.wmshell;

import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.wmshell.BubblesManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubblesManager$5$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ BubblesManager.AnonymousClass5 f$0;
    public final /* synthetic */ SysUiState f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ BubblesManager$5$$ExternalSyntheticLambda3(BubblesManager.AnonymousClass5 anonymousClass5, SysUiState sysUiState, boolean z) {
        this.f$0 = anonymousClass5;
        this.f$1 = sysUiState;
        this.f$2 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        BubblesManager.AnonymousClass5 anonymousClass5 = this.f$0;
        SysUiState sysUiState = this.f$1;
        boolean z = this.f$2;
        anonymousClass5.getClass();
        SysUiState flag = sysUiState.setFlag(16384L, z);
        BubblesManager bubblesManager = BubblesManager.this;
        bubblesManager.mContext.getDisplayId();
        ((SysUiStateImpl) flag).commitUpdate();
        if (z) {
            return;
        }
        SysUiState flag2 = sysUiState.setFlag(8388608L, false);
        bubblesManager.mContext.getDisplayId();
        ((SysUiStateImpl) flag2).commitUpdate();
    }
}
