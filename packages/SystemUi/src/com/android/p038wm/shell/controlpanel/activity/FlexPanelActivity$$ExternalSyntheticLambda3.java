package com.android.p038wm.shell.controlpanel.activity;

import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import com.android.p038wm.shell.controlpanel.activity.FlexPanelActivity;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class FlexPanelActivity$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ FlexPanelActivity$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                InputMethodManager inputMethodManager = (InputMethodManager) this.f$0;
                int i = FlexPanelActivity.mEditPanelItemSize;
                inputMethodManager.semForceHideSoftInput();
                Log.i("FlexPanelActivity", "Hide the Ime when unfold device and keyboard is open.");
                break;
            default:
                FlexPanelActivity flexPanelActivity = ((FlexPanelActivity.C39273) this.f$0).this$0;
                int i2 = FlexPanelActivity.mEditPanelItemSize;
                flexPanelActivity.closeOperation();
                break;
        }
    }
}
