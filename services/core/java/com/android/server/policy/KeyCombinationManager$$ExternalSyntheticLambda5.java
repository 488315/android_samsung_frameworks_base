package com.android.server.policy;

import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;

import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.input.InputManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyCombinationManager$$ExternalSyntheticLambda5
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyCombinationManager$$ExternalSyntheticLambda5(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        InputDevice device;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((KeyCombinationManager.TwoKeysCombinationRule) obj).cancel();
                break;
            case 1:
                ((KeyCombinationManager.TwoKeysCombinationRule) obj).execute();
                break;
            default:
                KeyCombinationManager keyCombinationManager = (KeyCombinationManager) obj;
                if (keyCombinationManager.mActiveRules.size() == 0) {
                    boolean z = true;
                    for (int i2 = 0; i2 < keyCombinationManager.mDownKeyEvent.size(); i2++) {
                        KeyEvent keyEvent =
                                (KeyEvent) keyCombinationManager.mDownKeyEvent.valueAt(i2);
                        if (keyEvent != null
                                && (device = keyEvent.getDevice()) != null
                                && !device.isExternal()
                                && !device.isVirtual()) {
                            int keyCode = keyEvent.getKeyCode();
                            InputManagerService.LocalService localService =
                                    keyCombinationManager.mInputManagerInternal;
                            int keyCodeState =
                                    localService != null
                                            ? InputManagerService.this.mNative.getKeyCodeState(
                                                    -1, -256, keyCode)
                                            : -1;
                            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                    keyCode,
                                    keyCodeState,
                                    "mResetKeyDownTimesRunnable keyCode=",
                                    " state=",
                                    "KeyCombinationManager");
                            if (keyCodeState > 0) {
                                z = false;
                            }
                        }
                    }
                    if (z) {
                        Log.d("KeyCombinationManager", "Reset keyDownTimes clear");
                        keyCombinationManager.mDownKeyEvent.clear();
                        break;
                    }
                }
                break;
        }
    }
}
