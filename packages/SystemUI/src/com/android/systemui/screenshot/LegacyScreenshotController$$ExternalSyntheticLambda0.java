package com.android.systemui.screenshot;

import android.animation.Animator;
import android.content.Context;
import android.view.View;
import android.widget.Toast;
import com.android.systemui.R;
import com.android.systemui.screenshot.ScreenshotShelfViewProxy;
import com.android.systemui.screenshot.ui.ScreenshotAnimationController;
import com.android.systemui.screenshot.ui.viewmodel.ScreenshotViewModel;
import java.util.Iterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class LegacyScreenshotController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ LegacyScreenshotController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                LegacyScreenshotController legacyScreenshotController = (LegacyScreenshotController) obj;
                legacyScreenshotController.getClass();
                legacyScreenshotController.mViewProxy.requestDismissal(ScreenshotEvent.SCREENSHOT_INTERACTION_TIMEOUT, null);
                break;
            case 1:
                Toast.makeText((Context) ((LegacyScreenshotController) obj).mContext, R.string.screenshot_saved_title, 0).show();
                break;
            case 2:
                ((LegacyScreenshotController) obj).removeWindow();
                break;
            default:
                ScreenshotShelfViewProxy screenshotShelfViewProxy = (ScreenshotShelfViewProxy) obj;
                ScreenshotViewModel screenshotViewModel = screenshotShelfViewProxy.viewModel;
                screenshotViewModel._scrollableRect.setValue(null);
                screenshotViewModel._scrollingScrim.setValue(null);
                ScreenshotAnimationController screenshotAnimationController = screenshotShelfViewProxy.animationController;
                Animator animator = screenshotAnimationController.animator;
                if (animator != null) {
                    animator.cancel();
                }
                Iterator it = screenshotAnimationController.fadeUI.iterator();
                while (it.hasNext()) {
                    ((View) it.next()).setAlpha(1.0f);
                }
                screenshotAnimationController.screenshotPreview.setAlpha(1.0f);
                ScreenshotShelfViewProxy.ScreenshotViewCallback screenshotViewCallback = screenshotShelfViewProxy.callbacks;
                if (screenshotViewCallback != null) {
                    screenshotViewCallback.onUserInteraction();
                    break;
                }
                break;
        }
    }
}
