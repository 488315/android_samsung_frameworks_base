package com.android.p038wm.shell.splitscreen;

import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import com.android.p038wm.shell.splitscreen.StageCoordinator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda6(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((InputMethodManager) this.f$0).semForceHideSoftInput();
                Log.i("StageCoordinator", "Hide the Ime to release adjustedForIme.");
                break;
            case 1:
                ((StageCoordinator.C41173) this.f$0).this$0.exitSplitScreen(null, 0);
                break;
            default:
                ((StageCoordinator.StageListenerImpl) this.f$0).onNoLongerSupportMultiWindow();
                break;
        }
    }
}
