package com.android.wm.shell.splitscreen;

import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda9 implements Runnable {
    public final /* synthetic */ SplitScreenController.SplitScreenImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda9(SplitScreenController.SplitScreenImpl splitScreenImpl, int i, String str) {
        this.f$0 = splitScreenImpl;
        this.f$1 = i;
        this.f$2 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SplitScreenController.SplitScreenImpl splitScreenImpl = this.f$0;
        final int i = this.f$1;
        final String str = this.f$2;
        SplitScreenController.this.mGestureStarter.ifPresent(new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SplitScreenController.SplitTwoFingerGestureStarter) obj).startSplitByTwoTouchSwipeIfPossible(i, str);
            }
        });
    }
}
