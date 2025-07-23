package com.android.wm.shell.pip2.phone;

import com.android.wm.shell.pip2.phone.PipTouchHandler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipTouchHandler$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipTouchHandler$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((PhonePipMenuController) obj).hideMenu();
                break;
            default:
                PipTouchHandler.DefaultPipTouchGesture defaultPipTouchGesture = (PipTouchHandler.DefaultPipTouchGesture) obj;
                if (defaultPipTouchGesture.mShouldHideMenuAfterFling) {
                    PipTouchHandler.this.mMenuController.hideMenu();
                    break;
                }
                break;
        }
    }
}
