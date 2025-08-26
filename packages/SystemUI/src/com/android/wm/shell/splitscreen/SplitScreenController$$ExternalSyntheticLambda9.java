package com.android.wm.shell.splitscreen;

import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenController$$ExternalSyntheticLambda9 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ SplitScreenController$$ExternalSyntheticLambda9(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = 0;
        int i2 = this.$r8$classId;
        int i3 = this.f$0;
        switch (i2) {
            case 0:
                ((SplitScreenController.SplitTwoFingerGestureStarter) obj).startSplitByTwoTouchSwipeIfPossible(i3, "ISplitScreen");
                break;
            case 1:
                int i4 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                ((SplitScreenController) obj).exitSplitScreen(i3, 0);
                break;
            default:
                int i5 = SplitScreenController.ISplitScreenImpl.$r8$clinit;
                ((SplitScreenController) obj).mGestureStarter.ifPresent(new SplitScreenController$$ExternalSyntheticLambda9(i3, i));
                break;
        }
    }
}
