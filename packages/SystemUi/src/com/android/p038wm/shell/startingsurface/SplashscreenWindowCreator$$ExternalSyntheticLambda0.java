package com.android.p038wm.shell.startingsurface;

import android.view.Choreographer;
import com.android.p038wm.shell.startingsurface.SplashscreenWindowCreator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SplashscreenWindowCreator$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SplashscreenWindowCreator$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SplashscreenWindowCreator splashscreenWindowCreator = (SplashscreenWindowCreator) this.f$0;
                splashscreenWindowCreator.getClass();
                splashscreenWindowCreator.mChoreographer = Choreographer.getInstance();
                break;
            default:
                SplashscreenWindowCreator.SplashWindowRecord splashWindowRecord = (SplashscreenWindowCreator.SplashWindowRecord) this.f$0;
                splashWindowRecord.this$0.removeWindowInner(splashWindowRecord.mRootView, true, false);
                break;
        }
    }
}
