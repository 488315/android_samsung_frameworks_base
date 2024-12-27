package com.samsung.android.biometrics.app.setting;

import android.util.Log;
import android.view.Display;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final /* synthetic */ class DisplayStateManager$1$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayStateManager.AnonymousClass1 f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ DisplayStateManager$1$$ExternalSyntheticLambda0(
            DisplayStateManager.AnonymousClass1 anonymousClass1, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = anonymousClass1;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DisplayStateManager.AnonymousClass1 anonymousClass1 = this.f$0;
                int i = this.f$1;
                if (anonymousClass1.this$0.mCurrentStateLogical != i) {
                    if (Utils.DEBUG) {
                        Log.d(
                                "BSS_DisplayStateManager",
                                "SysUiDisplayStateCallback#onFinish: "
                                        + DisplayStateManager.stateToString(i));
                    }
                    DisplayStateManager displayStateManager = anonymousClass1.this$0;
                    displayStateManager.mCurrentStateLogical = i;
                    if (displayStateManager.mCurrentDisplayState != 2 || !Display.isDozeState(i)) {
                        DisplayStateManager displayStateManager2 = anonymousClass1.this$0;
                        if (displayStateManager2.mDisplayStateFromKeyguard != 2 || i != 1) {
                            displayStateManager2.handleDisplayStateChanged(i);
                            break;
                        }
                    } else {
                        Log.i(
                                "BSS_DisplayStateManager",
                                "ignore, Switched directly from ON to DOZE");
                        break;
                    }
                }
                break;
            default:
                DisplayStateManager.AnonymousClass1 anonymousClass12 = this.f$0;
                int i2 = this.f$1;
                if (anonymousClass12.this$0.mCurrentStateLogical != i2) {
                    if (Utils.DEBUG) {
                        Log.d(
                                "BSS_DisplayStateManager",
                                "SysUiDisplayStateCallback#onStart: "
                                        + DisplayStateManager.stateToString(i2));
                    }
                    DisplayStateManager displayStateManager3 = anonymousClass12.this$0;
                    displayStateManager3.mCurrentStateLogical = i2 + 1000;
                    if (i2 == 2 && !displayStateManager3.isOnState()) {
                        anonymousClass12.this$0.handleDisplayStateChanged(1002);
                        break;
                    } else if (i2 == 1 && anonymousClass12.this$0.isOnState()) {
                        anonymousClass12.this$0.handleDisplayStateChanged(1001);
                        break;
                    }
                }
                break;
        }
    }
}
