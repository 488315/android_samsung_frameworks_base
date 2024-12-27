package com.android.systemui.screenshot;

import android.app.admin.DevicePolicyManager;
import com.android.systemui.screenshot.sep.SemImageCaptureImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0139 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00c2 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
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
