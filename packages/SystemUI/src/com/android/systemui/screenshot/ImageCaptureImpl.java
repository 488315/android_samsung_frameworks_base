package com.android.systemui.screenshot;

import android.app.IActivityTaskManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.IWindowManager;
import android.window.ScreenCapture;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class ImageCaptureImpl implements ImageCapture {
    public final IActivityTaskManager atmService;
    public final CoroutineDispatcher bgContext;
    public final IWindowManager windowManager;

    public ImageCaptureImpl(IWindowManager iWindowManager, IActivityTaskManager iActivityTaskManager, CoroutineDispatcher coroutineDispatcher) {
        this.windowManager = iWindowManager;
        this.atmService = iActivityTaskManager;
        this.bgContext = coroutineDispatcher;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0047 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object captureTask$suspendImpl(com.android.systemui.screenshot.ImageCaptureImpl r5, int r6, kotlin.coroutines.Continuation r7) {
        /*
            boolean r0 = r7 instanceof com.android.systemui.screenshot.ImageCaptureImpl$captureTask$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.screenshot.ImageCaptureImpl$captureTask$1 r0 = (com.android.systemui.screenshot.ImageCaptureImpl$captureTask$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.screenshot.ImageCaptureImpl$captureTask$1 r0 = new com.android.systemui.screenshot.ImageCaptureImpl$captureTask$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L30
            if (r2 != r4) goto L28
            kotlin.ResultKt.throwOnFailure(r7)
            goto L43
        L28:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L30:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.CoroutineDispatcher r7 = r5.bgContext
            com.android.systemui.screenshot.ImageCaptureImpl$captureTask$snapshot$1 r2 = new com.android.systemui.screenshot.ImageCaptureImpl$captureTask$snapshot$1
            r2.<init>(r5, r6, r3)
            r0.label = r4
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r2, r0)
            if (r7 != r1) goto L43
            return r1
        L43:
            android.window.TaskSnapshot r7 = (android.window.TaskSnapshot) r7
            if (r7 != 0) goto L48
            return r3
        L48:
            android.hardware.HardwareBuffer r5 = r7.getHardwareBuffer()
            android.graphics.ColorSpace r6 = r7.getColorSpace()
            android.graphics.Bitmap r5 = android.graphics.Bitmap.wrapHardwareBuffer(r5, r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.ImageCaptureImpl.captureTask$suspendImpl(com.android.systemui.screenshot.ImageCaptureImpl, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Bitmap captureDisplay(int i, Rect rect) {
        ScreenCapture.CaptureArgs build = new ScreenCapture.CaptureArgs.Builder().setSourceCrop(rect).build();
        ScreenCapture.SynchronousScreenCaptureListener createSyncCaptureListener = ScreenCapture.createSyncCaptureListener();
        this.windowManager.captureDisplay(i, build, createSyncCaptureListener);
        ScreenCapture.ScreenshotHardwareBuffer buffer = createSyncCaptureListener.getBuffer();
        if (buffer != null) {
            return buffer.asBitmap();
        }
        return null;
    }
}
