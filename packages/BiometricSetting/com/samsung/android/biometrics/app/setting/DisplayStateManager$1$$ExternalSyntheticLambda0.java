package com.samsung.android.biometrics.app.setting;

import android.util.Log;
import android.view.Display;
import com.samsung.android.biometrics.app.setting.DisplayStateManager;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class DisplayStateManager$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayStateManager.C02161 f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ DisplayStateManager$1$$ExternalSyntheticLambda0(DisplayStateManager.C02161 c02161, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = c02161;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        int i2;
        int i3;
        int i4;
        switch (this.$r8$classId) {
            case 0:
                DisplayStateManager.C02161 c02161 = this.f$0;
                int i5 = this.f$1;
                i2 = DisplayStateManager.this.mCurrentStateLogical;
                if (i2 != i5) {
                    if (Utils.DEBUG) {
                        Log.d("BSS_DisplayStateManager", "SysUiDisplayStateCallback#onFinish: " + DisplayStateManager.stateToString(i5));
                    }
                    DisplayStateManager.this.mCurrentStateLogical = i5;
                    i3 = DisplayStateManager.this.mCurrentDisplayState;
                    if (i3 != 2 || !Display.isDozeState(i5)) {
                        i4 = DisplayStateManager.this.mDisplayStateFromKeyguard;
                        if (i4 != 2 || i5 != 1) {
                            DisplayStateManager.this.handleDisplayStateChanged(i5);
                            break;
                        }
                    } else {
                        Log.i("BSS_DisplayStateManager", "ignore, Switched directly from ON to DOZE");
                        break;
                    }
                }
                break;
            default:
                DisplayStateManager.C02161 c021612 = this.f$0;
                int i6 = this.f$1;
                i = DisplayStateManager.this.mCurrentStateLogical;
                if (i != i6) {
                    if (Utils.DEBUG) {
                        Log.d("BSS_DisplayStateManager", "SysUiDisplayStateCallback#onStart: " + DisplayStateManager.stateToString(i6));
                    }
                    DisplayStateManager.this.mCurrentStateLogical = i6 + 1000;
                    if (i6 == 2 && !DisplayStateManager.this.isOnState()) {
                        DisplayStateManager.this.handleDisplayStateChanged(1002);
                        break;
                    } else if (i6 == 1 && DisplayStateManager.this.isOnState()) {
                        DisplayStateManager.this.handleDisplayStateChanged(1001);
                        break;
                    }
                }
                break;
        }
    }
}
