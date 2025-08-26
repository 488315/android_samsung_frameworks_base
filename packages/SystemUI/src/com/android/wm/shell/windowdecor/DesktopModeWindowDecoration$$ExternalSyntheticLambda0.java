package com.android.wm.shell.windowdecor;

import android.util.Log;
import android.view.inputmethod.InputMethodManager;

/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopModeWindowDecoration$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DesktopModeWindowDecoration$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((DesktopModeWindowDecoration) obj).closeMaximizeMenu();
                break;
            case 1:
                ((DesktopModeWindowDecoration) obj).closeHandleMenu();
                break;
            default:
                ((InputMethodManager) obj).semForceHideSoftInput();
                Log.i("DesktopModeWindowDecoration", "Hide the Ime to use the multi-window handler.");
                break;
        }
    }
}
