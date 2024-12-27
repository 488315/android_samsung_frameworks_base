package com.android.systemui.screenshot;

import android.app.admin.DevicePolicyManager;
import com.android.systemui.screenshot.sep.SemImageCaptureImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class RequestProcessor implements ScreenshotRequestProcessor {
    public final ImageCapture capture;
    public final DevicePolicyManager devicePolicyManager;
    public final ScreenshotPolicy policy;
    public final SemImageCaptureImpl semCapture;

    public RequestProcessor(ImageCapture imageCapture, ScreenshotPolicy screenshotPolicy, DevicePolicyManager devicePolicyManager, SemImageCaptureImpl semImageCaptureImpl) {
        this.capture = imageCapture;
        this.policy = screenshotPolicy;
        this.devicePolicyManager = devicePolicyManager;
        this.semCapture = semImageCaptureImpl;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object process(com.android.systemui.screenshot.ScreenshotData r11, kotlin.coroutines.Continuation r12) {
        /*
            Method dump skipped, instructions count: 381
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.RequestProcessor.process(com.android.systemui.screenshot.ScreenshotData, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public /* synthetic */ RequestProcessor(ImageCapture imageCapture, ScreenshotPolicy screenshotPolicy, DevicePolicyManager devicePolicyManager, SemImageCaptureImpl semImageCaptureImpl, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(imageCapture, screenshotPolicy, (i & 4) != 0 ? null : devicePolicyManager, (i & 8) != 0 ? null : semImageCaptureImpl);
    }
}
