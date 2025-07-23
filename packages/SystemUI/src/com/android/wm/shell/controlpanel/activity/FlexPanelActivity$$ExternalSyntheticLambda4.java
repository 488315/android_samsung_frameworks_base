package com.android.wm.shell.controlpanel.activity;

import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class FlexPanelActivity$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ FlexPanelActivity$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                int i2 = FlexPanelActivity.mEditPanelItemSize;
                ((InputMethodManager) obj).semForceHideSoftInput();
                Log.i("FlexPanelActivity", "Hide the Ime when unfold device and keyboard is open.");
                break;
            default:
                FlexPanelActivity flexPanelActivity = ((FlexPanelActivity.AnonymousClass3) obj).this$0;
                int i3 = FlexPanelActivity.mEditPanelItemSize;
                flexPanelActivity.closeOperation();
                break;
        }
    }
}
