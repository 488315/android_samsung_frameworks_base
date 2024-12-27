package com.android.systemui.screenshot.appclips;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.window.ScreenCapture;
import com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda1;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.common.HandlerExecutor;
import java.util.Optional;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class AppClipsScreenshotHelperService extends Service {
    public final Optional mOptionalBubbles;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.screenshot.appclips.AppClipsScreenshotHelperService$1, reason: invalid class name */
    public final class AnonymousClass1 extends IAppClipsScreenshotHelperService.Stub {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService
        public final ScreenshotHardwareBufferInternal takeScreenshot(int i) {
            if (AppClipsScreenshotHelperService.this.mOptionalBubbles.isEmpty()) {
                return null;
            }
            BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) ((Bubbles) AppClipsScreenshotHelperService.this.mOptionalBubbles.get());
            bubblesImpl.getClass();
            ScreenCapture.SynchronousScreenCaptureListener createSyncCaptureListener = ScreenCapture.createSyncCaptureListener();
            ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda1(bubblesImpl, i, createSyncCaptureListener));
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
        return new AnonymousClass1();
    }
}
