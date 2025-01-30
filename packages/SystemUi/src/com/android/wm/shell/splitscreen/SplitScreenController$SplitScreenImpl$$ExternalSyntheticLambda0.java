package com.android.wm.shell.splitscreen;

import com.android.wm.shell.splitscreen.SplitScreenController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SplitScreenController.SplitScreenImpl f$0;

    public /* synthetic */ SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda0(SplitScreenController.SplitScreenImpl splitScreenImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = splitScreenImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SplitScreenController.this.toggleSplitScreen(1);
                break;
            default:
                SplitScreenController.this.enterSplitScreen();
                break;
        }
    }
}
