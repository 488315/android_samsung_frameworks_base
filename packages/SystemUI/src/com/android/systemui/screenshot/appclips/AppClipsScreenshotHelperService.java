package com.android.systemui.screenshot.appclips;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.window.ScreenCapture;
import com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda0;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.Optional;

/* loaded from: classes2.dex */
public class AppClipsScreenshotHelperService extends Service {
    public final Optional mOptionalBubbles;

    /* renamed from: com.android.systemui.screenshot.appclips.AppClipsScreenshotHelperService$1, reason: invalid class name */
    public class AnonymousClass1 extends IAppClipsScreenshotHelperService.Stub {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.screenshot.appclips.IAppClipsScreenshotHelperService
        public final ScreenshotHardwareBufferInternal takeScreenshot(int i) {
            if (AppClipsScreenshotHelperService.this.mOptionalBubbles.isEmpty()) {
                return null;
            }
            BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) ((Bubbles) AppClipsScreenshotHelperService.this.mOptionalBubbles.get());
            bubblesImpl.getClass();
            ScreenCapture.SynchronousScreenCaptureListener synchronousScreenCaptureListenerCreateSyncCaptureListener = ScreenCapture.createSyncCaptureListener();
            BubbleController.this.mMainExecutor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda0(bubblesImpl, i, synchronousScreenCaptureListenerCreateSyncCaptureListener));
            ScreenCapture.ScreenshotHardwareBuffer buffer = synchronousScreenCaptureListenerCreateSyncCaptureListener.getBuffer();
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
