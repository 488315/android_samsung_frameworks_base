package com.android.systemui.screenshot.sep.widget;

import android.os.RemoteException;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ScreenshotController$handleScreenshot$2$1$1;
import com.android.systemui.screenshot.sep.widget.SemScreenshotLayout;
import kotlin.Unit;

/* loaded from: classes2.dex */
public final /* synthetic */ class SemScreenshotLayout$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SemScreenshotLayout$1$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() throws RemoteException {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ScreenshotController$handleScreenshot$2$1$1 screenshotController$handleScreenshot$2$1$1 = ((SemScreenshotLayout.AnonymousClass1) obj).this$0.mCallback;
                screenshotController$handleScreenshot$2$1$1.this$0.messageContainerController.onScreenshotTaken(screenshotController$handleScreenshot$2$1$1.$screenshot);
                Object obj2 = ScreenshotController.shutterEffectLock;
                ScreenshotController screenshotController = screenshotController$handleScreenshot$2$1$1.this$0;
                synchronized (obj2) {
                    ScreenshotController.isAnimationRunning = false;
                    ScreenshotController.access$detachSemScreenshotLayoutToWindow(screenshotController);
                    Unit unit = Unit.INSTANCE;
                }
                return;
            default:
                ScreenshotController screenshotController2 = ((SemScreenshotLayout.AnonymousClass3) obj).this$0.mCallback.this$0;
                if (screenshotController2.isScreenshotSaveTaskCompleted) {
                    screenshotController2.finishDismiss$1();
                    return;
                }
                return;
        }
    }
}
