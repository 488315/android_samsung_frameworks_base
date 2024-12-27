package com.android.systemui.screenshot;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.screenshot.ImageExporter;
import com.android.systemui.screenshot.ScreenshotNotificationsController;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class HeadlessScreenshotHandler implements ScreenshotHandler {
    public final ImageCapture imageCapture;
    public final ImageExporter imageExporter;
    public final Executor mainExecutor;
    public final ScreenshotNotificationsController.Factory notificationsControllerFactory;
    public final UiEventLogger uiEventLogger;
    public final UserManager userManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public HeadlessScreenshotHandler(ImageExporter imageExporter, Executor executor, ImageCapture imageCapture, UserManager userManager, UiEventLogger uiEventLogger, ScreenshotNotificationsController.Factory factory) {
        this.imageExporter = imageExporter;
        this.mainExecutor = executor;
        this.imageCapture = imageCapture;
        this.userManager = userManager;
        this.uiEventLogger = uiEventLogger;
        this.notificationsControllerFactory = factory;
    }

    public static final void access$logScreenshotResultStatus(HeadlessScreenshotHandler headlessScreenshotHandler, Uri uri, ScreenshotData screenshotData) {
        if (uri == null) {
            headlessScreenshotHandler.uiEventLogger.log(ScreenshotEvent.SCREENSHOT_NOT_SAVED, 0, screenshotData.getPackageNameString());
            headlessScreenshotHandler.notificationsControllerFactory.create(screenshotData.displayId).notifyScreenshotError(R.string.screenshot_failed_to_save_text);
            return;
        }
        headlessScreenshotHandler.uiEventLogger.log(ScreenshotEvent.SCREENSHOT_SAVED, 0, screenshotData.getPackageNameString());
        UserManager userManager = headlessScreenshotHandler.userManager;
        UserHandle userHandle = screenshotData.userHandle;
        if (userHandle == null) {
            userHandle = Process.myUserHandle();
        }
        if (userManager.isManagedProfile(userHandle.getIdentifier())) {
            headlessScreenshotHandler.uiEventLogger.log(ScreenshotEvent.SCREENSHOT_SAVED_TO_WORK_PROFILE, 0, screenshotData.getPackageNameString());
        }
    }

    @Override // com.android.systemui.screenshot.ScreenshotHandler
    public final void handleScreenshot(final ScreenshotData screenshotData, final Consumer consumer, final TakeScreenshotService.RequestCallback requestCallback) {
        int i = screenshotData.type;
        int i2 = screenshotData.displayId;
        if (i == 1) {
            screenshotData.bitmap = ((ImageCaptureImpl) this.imageCapture).captureDisplay(i2, null);
        }
        if (screenshotData.bitmap == null) {
            Log.e("HeadlessScreenshotHandler", "handleScreenshot: Screenshot bitmap was null");
            this.notificationsControllerFactory.create(i2).notifyScreenshotError(R.string.screenshot_failed_to_capture_text);
            ((TakeScreenshotService.RequestCallbackImpl) requestCallback).reportError();
            return;
        }
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        UUID randomUUID = UUID.randomUUID();
        Bitmap bitmap = screenshotData.bitmap;
        UserHandle userHandle = screenshotData.userHandle;
        if (userHandle == null) {
            userHandle = Process.myUserHandle();
        }
        final CallbackToFutureAdapter.SafeFuture export = this.imageExporter.export(newSingleThreadExecutor, randomUUID, bitmap, userHandle, screenshotData.displayId);
        export.delegate.addListener(new Runnable() { // from class: com.android.systemui.screenshot.HeadlessScreenshotHandler$handleScreenshot$1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    ImageExporter.Result result = (ImageExporter.Result) ListenableFuture.this.get();
                    Log.d("HeadlessScreenshotHandler", "Saved screenshot: " + result);
                    HeadlessScreenshotHandler.access$logScreenshotResultStatus(this, result.uri, screenshotData);
                    consumer.accept(result.uri);
                    Messenger messenger = ((TakeScreenshotService.RequestCallbackImpl) requestCallback).mReplyTo;
                    boolean z = TakeScreenshotService.sConfigured;
                    try {
                        messenger.send(Message.obtain((Handler) null, 2));
                    } catch (RemoteException e) {
                        Log.d("Screenshot", "ignored remote exception", e);
                    }
                } catch (Exception e2) {
                    Log.d("HeadlessScreenshotHandler", "Failed to store screenshot", e2);
                    consumer.accept(null);
                    ((TakeScreenshotService.RequestCallbackImpl) requestCallback).reportError();
                }
            }
        }, this.mainExecutor);
    }
}
