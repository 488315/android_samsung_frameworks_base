package com.android.systemui.screenshot.sep;

import android.app.IActivityTaskManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.view.IWindowManager;
import com.android.systemui.screenshot.ImageCaptureImpl;
import kotlinx.coroutines.CoroutineDispatcher;

public final class SemImageCaptureImpl extends ImageCaptureImpl {
    public final String TAG;
    public final IActivityTaskManager atmService;
    public final CoroutineDispatcher bgContext;
    public final Context context;
    public final DevicePolicyManager devicePolicyManager;
    public final IWindowManager windowManager;

    public SemImageCaptureImpl(Context context, DevicePolicyManager devicePolicyManager, IWindowManager iWindowManager, IActivityTaskManager iActivityTaskManager, CoroutineDispatcher coroutineDispatcher) {
        super(iWindowManager, iActivityTaskManager, coroutineDispatcher);
        this.context = context;
        this.devicePolicyManager = devicePolicyManager;
        this.windowManager = iWindowManager;
        this.atmService = iActivityTaskManager;
        this.bgContext = coroutineDispatcher;
        this.TAG = "Screenshot";
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object semCaptureTask(int r6, kotlin.coroutines.Continuation r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.screenshot.sep.SemImageCaptureImpl$semCaptureTask$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.screenshot.sep.SemImageCaptureImpl$semCaptureTask$1 r0 = (com.android.systemui.screenshot.sep.SemImageCaptureImpl$semCaptureTask$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.screenshot.sep.SemImageCaptureImpl$semCaptureTask$1 r0 = new com.android.systemui.screenshot.sep.SemImageCaptureImpl$semCaptureTask$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L34
            if (r2 != r4) goto L2c
            java.lang.Object r5 = r0.L$0
            com.android.systemui.screenshot.sep.SemImageCaptureImpl r5 = (com.android.systemui.screenshot.sep.SemImageCaptureImpl) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L49
        L2c:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L34:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.screenshot.sep.SemImageCaptureImpl$semCaptureTask$snapshot$1 r7 = new com.android.systemui.screenshot.sep.SemImageCaptureImpl$semCaptureTask$snapshot$1
            r7.<init>(r5, r6, r3)
            r0.L$0 = r5
            r0.label = r4
            kotlinx.coroutines.CoroutineDispatcher r6 = r5.bgContext
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r6, r7, r0)
            if (r7 != r1) goto L49
            return r1
        L49:
            android.window.TaskSnapshot r7 = (android.window.TaskSnapshot) r7
            if (r7 != 0) goto L55
            kotlin.Pair r5 = new kotlin.Pair
            java.lang.Boolean r6 = java.lang.Boolean.FALSE
            r5.<init>(r6, r3)
            return r5
        L55:
            android.hardware.HardwareBuffer r6 = r7.getHardwareBuffer()
            android.graphics.ColorSpace r0 = r7.getColorSpace()
            android.graphics.Bitmap r6 = android.graphics.Bitmap.wrapHardwareBuffer(r6, r0)
            boolean r7 = r7.containsSecureLayers()
            if (r7 == 0) goto L6f
            java.lang.String r5 = r5.TAG
            java.lang.String r0 = "semCaptureTask: snapshot is a secure layer."
            android.util.Log.i(r5, r0)
        L6f:
            kotlin.Pair r5 = new kotlin.Pair
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)
            r5.<init>(r7, r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.sep.SemImageCaptureImpl.semCaptureTask(int, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
