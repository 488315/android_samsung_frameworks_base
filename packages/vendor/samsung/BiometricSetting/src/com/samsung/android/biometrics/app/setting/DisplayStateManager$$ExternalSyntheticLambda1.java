package com.samsung.android.biometrics.app.setting;

import java.util.ArrayList;

public final /* synthetic */ class DisplayStateManager$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayStateManager f$0;
    public final /* synthetic */ DisplayStateManager.LimitDisplayStateCallback f$1;

    public /* synthetic */ DisplayStateManager$$ExternalSyntheticLambda1(
            DisplayStateManager displayStateManager,
            DisplayStateManager.LimitDisplayStateCallback limitDisplayStateCallback,
            int i) {
        this.$r8$classId = i;
        this.f$0 = displayStateManager;
        this.f$1 = limitDisplayStateCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DisplayStateManager displayStateManager = this.f$0;
                DisplayStateManager.LimitDisplayStateCallback limitDisplayStateCallback = this.f$1;
                if (!((ArrayList) displayStateManager.mLimitDisplayCallbacks)
                        .contains(limitDisplayStateCallback)) {
                    ((ArrayList) displayStateManager.mLimitDisplayCallbacks)
                            .add(limitDisplayStateCallback);
                    break;
                }
                break;
            default:
                DisplayStateManager displayStateManager2 = this.f$0;
                ((ArrayList) displayStateManager2.mLimitDisplayCallbacks).remove(this.f$1);
                break;
        }
    }
}
