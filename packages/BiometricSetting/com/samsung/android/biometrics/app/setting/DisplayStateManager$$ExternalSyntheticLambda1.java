package com.samsung.android.biometrics.app.setting;

import com.samsung.android.biometrics.app.setting.DisplayStateManager;
import java.util.ArrayList;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class DisplayStateManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayStateManager f$0;
    public final /* synthetic */ DisplayStateManager.LimitDisplayStateCallback f$1;

    public /* synthetic */ DisplayStateManager$$ExternalSyntheticLambda1(DisplayStateManager displayStateManager, DisplayStateManager.LimitDisplayStateCallback limitDisplayStateCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = displayStateManager;
        this.f$1 = limitDisplayStateCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((ArrayList) this.f$0.mLimitDisplayCallbacks).remove(this.f$1);
                break;
            default:
                DisplayStateManager.$r8$lambda$jufplp9SAEsLDjToPqv_ZMdXlgI(this.f$0, this.f$1);
                break;
        }
    }
}
