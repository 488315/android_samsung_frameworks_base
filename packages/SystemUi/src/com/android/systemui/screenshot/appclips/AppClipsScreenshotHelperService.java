package com.android.systemui.screenshot.appclips;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.ViewRootImpl;
import android.window.ScreenCapture;
import com.android.p038wm.shell.bubbles.BubbleController;
import com.android.p038wm.shell.bubbles.Bubbles;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class AppClipsScreenshotHelperService extends Service {
    public final Optional mOptionalBubbles;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.screenshot.appclips.AppClipsScreenshotHelperService$1 */
    public final class BinderC23531 extends IAppClipsScreenshotHelperService.Stub {
        public BinderC23531() {
        }

        @Override // com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService
        public final ScreenshotHardwareBufferInternal takeScreenshot(final int i) {
            if (AppClipsScreenshotHelperService.this.mOptionalBubbles.isEmpty()) {
                return null;
            }
            final BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) ((Bubbles) AppClipsScreenshotHelperService.this.mOptionalBubbles.get());
            bubblesImpl.getClass();
            final ScreenCapture.SynchronousScreenCaptureListener createSyncCaptureListener = ScreenCapture.createSyncCaptureListener();
            ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ViewRootImpl viewRootImpl;
                    SurfaceControl surfaceControl;
                    BubbleController.BubblesImpl bubblesImpl2 = BubbleController.BubblesImpl.this;
                    int i2 = i;
                    ScreenCapture.SynchronousScreenCaptureListener synchronousScreenCaptureListener = createSyncCaptureListener;
                    BubbleController bubbleController = BubbleController.this;
                    bubbleController.getClass();
                    try {
                        BubbleStackView bubbleStackView = bubbleController.mStackView;
                        bubbleController.mWmService.captureDisplay(i2, (bubbleStackView == null || (viewRootImpl = bubbleStackView.getViewRootImpl()) == null || (surfaceControl = viewRootImpl.getSurfaceControl()) == null) ? null : new ScreenCapture.CaptureArgs.Builder().setExcludeLayers(new SurfaceControl[]{surfaceControl}).build(), synchronousScreenCaptureListener);
                    } catch (RemoteException unused) {
                        Log.e("Bubbles", "Failed to capture screenshot");
                    }
                }
            });
            ScreenCapture.ScreenshotHardwareBuffer buffer = createSyncCaptureListener.getBuffer();
            if (buffer == null) {
                return null;
            }
            return new ScreenshotHardwareBufferInternal(buffer);
        }
    }

    public AppClipsScreenshotHelperService(Optional<Bubbles> optional) {
        this.mOptionalBubbles = optional;
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new BinderC23531();
    }
}
