package com.android.systemui.screenshot.policy;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.os.Process;
import android.os.UserHandle;
import com.android.systemui.screenshot.ImageCapture;
import com.android.systemui.screenshot.ScreenshotRequestProcessor;
import com.android.systemui.screenshot.data.repository.DisplayContentRepository;
import com.android.systemui.screenshot.sep.SemImageCaptureImpl;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PolicyRequestProcessor implements ScreenshotRequestProcessor {
    public final CoroutineDispatcher background;
    public final ImageCapture capture;
    public final ComponentName defaultComponent;
    public final UserHandle defaultOwner;
    public final DisplayContentRepository displayTasks;
    public final List policies;
    public final SemImageCaptureImpl semImageCapture;

    public PolicyRequestProcessor(CoroutineDispatcher coroutineDispatcher, ImageCapture imageCapture, DisplayContentRepository displayContentRepository, List<? extends CapturePolicy> list, ScreenshotPolicy screenshotPolicy, UserHandle userHandle, ComponentName componentName, SemImageCaptureImpl semImageCaptureImpl, DevicePolicyManager devicePolicyManager) {
        this.background = coroutineDispatcher;
        this.capture = imageCapture;
        this.displayTasks = displayContentRepository;
        this.policies = list;
        this.defaultOwner = userHandle;
        this.defaultComponent = componentName;
        this.semImageCapture = semImageCaptureImpl;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0067, code lost:
    
        if (r11 == r0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0083, code lost:
    
        if (r11 == r0) goto L28;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object modify(com.android.systemui.screenshot.ScreenshotData r9, com.android.systemui.screenshot.policy.LegacyCaptureParameters r10, kotlin.coroutines.jvm.internal.ContinuationImpl r11) {
        /*
            r8 = this;
            boolean r0 = r11 instanceof com.android.systemui.screenshot.policy.PolicyRequestProcessor$modify$2
            if (r0 == 0) goto L14
            r0 = r11
            com.android.systemui.screenshot.policy.PolicyRequestProcessor$modify$2 r0 = (com.android.systemui.screenshot.policy.PolicyRequestProcessor$modify$2) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.label = r1
        L12:
            r7 = r0
            goto L1a
        L14:
            com.android.systemui.screenshot.policy.PolicyRequestProcessor$modify$2 r0 = new com.android.systemui.screenshot.policy.PolicyRequestProcessor$modify$2
            r0.<init>(r8, r11)
            goto L12
        L1a:
            java.lang.Object r11 = r7.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L38
            if (r1 == r3) goto L34
            if (r1 != r2) goto L2c
            kotlin.ResultKt.throwOnFailure(r11)
            goto L86
        L2c:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L34:
            kotlin.ResultKt.throwOnFailure(r11)
            goto L6a
        L38:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r1 = "[modify] CaptureParameters = "
            r11.<init>(r1)
            r11.append(r10)
            java.lang.String r11 = r11.toString()
            java.lang.String r1 = "PolicyRequestProcessor"
            android.util.Log.d(r1, r11)
            com.android.systemui.screenshot.policy.CaptureType r11 = r10.type
            boolean r1 = r11 instanceof com.android.systemui.screenshot.policy.CaptureType.IsolatedTask
            if (r1 == 0) goto L6d
            r1 = r3
            android.content.ComponentName r3 = r10.component
            android.os.UserHandle r4 = r10.owner
            com.android.systemui.screenshot.policy.CaptureType$IsolatedTask r11 = (com.android.systemui.screenshot.policy.CaptureType.IsolatedTask) r11
            int r5 = r11.taskId
            android.graphics.Rect r6 = r11.taskBounds
            r7.label = r1
            r1 = r8
            r2 = r9
            java.lang.Object r11 = r1.replaceWithTaskSnapshot(r2, r3, r4, r5, r6, r7)
            if (r11 != r0) goto L6a
            goto L85
        L6a:
            com.android.systemui.screenshot.ScreenshotData r11 = (com.android.systemui.screenshot.ScreenshotData) r11
            return r11
        L6d:
            r1 = r8
            r8 = r2
            r2 = r9
            boolean r9 = r11 instanceof com.android.systemui.screenshot.policy.CaptureType.FullScreen
            if (r9 == 0) goto L89
            android.content.ComponentName r3 = r10.component
            android.os.UserHandle r4 = r10.owner
            com.android.systemui.screenshot.policy.CaptureType$FullScreen r11 = (com.android.systemui.screenshot.policy.CaptureType.FullScreen) r11
            int r5 = r11.displayId
            r7.label = r8
            r6 = 0
            java.lang.Object r11 = r1.replaceWithScreenshot(r2, r3, r4, r5, r6, r7)
            if (r11 != r0) goto L86
        L85:
            return r0
        L86:
            com.android.systemui.screenshot.ScreenshotData r11 = (com.android.systemui.screenshot.ScreenshotData) r11
            return r11
        L89:
            kotlin.NoWhenBranchMatchedException r8 = new kotlin.NoWhenBranchMatchedException
            r8.<init>()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.policy.PolicyRequestProcessor.modify(com.android.systemui.screenshot.ScreenshotData, com.android.systemui.screenshot.policy.LegacyCaptureParameters, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:87:0x0095, code lost:
    
        if (r2 == r4) goto L78;
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002d  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:33:0x00da -> B:20:0x00db). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object process(com.android.systemui.screenshot.ScreenshotData r21, kotlin.coroutines.jvm.internal.ContinuationImpl r22) {
        /*
            Method dump skipped, instructions count: 405
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.policy.PolicyRequestProcessor.process(com.android.systemui.screenshot.ScreenshotData, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object replaceWithScreenshot(com.android.systemui.screenshot.ScreenshotData r15, android.content.ComponentName r16, android.os.UserHandle r17, int r18, java.lang.Integer r19, kotlin.coroutines.jvm.internal.ContinuationImpl r20) {
        /*
            r14 = this;
            r0 = r16
            r1 = r17
            r2 = r20
            boolean r3 = r2 instanceof com.android.systemui.screenshot.policy.PolicyRequestProcessor$replaceWithScreenshot$1
            if (r3 == 0) goto L19
            r3 = r2
            com.android.systemui.screenshot.policy.PolicyRequestProcessor$replaceWithScreenshot$1 r3 = (com.android.systemui.screenshot.policy.PolicyRequestProcessor$replaceWithScreenshot$1) r3
            int r4 = r3.label
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            r6 = r4 & r5
            if (r6 == 0) goto L19
            int r4 = r4 - r5
            r3.label = r4
            goto L1e
        L19:
            com.android.systemui.screenshot.policy.PolicyRequestProcessor$replaceWithScreenshot$1 r3 = new com.android.systemui.screenshot.policy.PolicyRequestProcessor$replaceWithScreenshot$1
            r3.<init>(r14, r2)
        L1e:
            java.lang.Object r2 = r3.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r4 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r5 = r3.label
            r6 = 1
            if (r5 == 0) goto L48
            if (r5 != r6) goto L40
            java.lang.Object r14 = r3.L$3
            java.lang.Integer r14 = (java.lang.Integer) r14
            java.lang.Object r0 = r3.L$2
            android.os.UserHandle r0 = (android.os.UserHandle) r0
            java.lang.Object r1 = r3.L$1
            android.content.ComponentName r1 = (android.content.ComponentName) r1
            java.lang.Object r3 = r3.L$0
            com.android.systemui.screenshot.ScreenshotData r3 = (com.android.systemui.screenshot.ScreenshotData) r3
            kotlin.ResultKt.throwOnFailure(r2)
            r7 = r0
            r8 = r1
            r5 = r3
            goto L88
        L40:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r14.<init>(r0)
            throw r14
        L48:
            kotlin.ResultKt.throwOnFailure(r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r5 = "Capturing screenshot: "
            r2.<init>(r5)
            r2.append(r0)
            java.lang.String r5 = " / "
            r2.append(r5)
            r2.append(r1)
            java.lang.String r2 = r2.toString()
            java.lang.String r5 = "PolicyRequestProcessor"
            android.util.Log.i(r5, r2)
            r3.L$0 = r15
            r3.L$1 = r0
            r3.L$2 = r1
            r5 = r19
            r3.L$3 = r5
            r3.label = r6
            com.android.systemui.screenshot.policy.PolicyRequestProcessor$captureDisplay$2 r6 = new com.android.systemui.screenshot.policy.PolicyRequestProcessor$captureDisplay$2
            r7 = 0
            r8 = r18
            r6.<init>(r14, r8, r7)
            kotlinx.coroutines.CoroutineDispatcher r14 = r14.background
            java.lang.Object r14 = kotlinx.coroutines.BuildersKt.withContext(r14, r6, r3)
            if (r14 != r4) goto L83
            return r4
        L83:
            r2 = r14
            r8 = r0
            r7 = r1
            r14 = r5
            r5 = r15
        L88:
            r11 = r2
            android.graphics.Bitmap r11 = (android.graphics.Bitmap) r11
            if (r11 == 0) goto Laf
            int r6 = r5.type
            android.graphics.Rect r10 = new android.graphics.Rect
            int r0 = r11.getWidth()
            int r1 = r11.getHeight()
            r2 = 0
            r10.<init>(r2, r2, r0, r1)
            if (r14 == 0) goto La5
            int r14 = r14.intValue()
        La3:
            r9 = r14
            goto La7
        La5:
            r14 = -1
            goto La3
        La7:
            r13 = 1858(0x742, float:2.604E-42)
            r12 = 0
            com.android.systemui.screenshot.ScreenshotData r14 = com.android.systemui.screenshot.ScreenshotData.copy$default(r5, r6, r7, r8, r9, r10, r11, r12, r13)
            return r14
        Laf:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r0 = "Failed to capture screenshot"
            r14.<init>(r0)
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.policy.PolicyRequestProcessor.replaceWithScreenshot(com.android.systemui.screenshot.ScreenshotData, android.content.ComponentName, android.os.UserHandle, int, java.lang.Integer, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object replaceWithTaskSnapshot(com.android.systemui.screenshot.ScreenshotData r17, android.content.ComponentName r18, android.os.UserHandle r19, int r20, android.graphics.Rect r21, kotlin.coroutines.jvm.internal.ContinuationImpl r22) {
        /*
            r16 = this;
            r0 = r16
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r22
            boolean r5 = r4 instanceof com.android.systemui.screenshot.policy.PolicyRequestProcessor$replaceWithTaskSnapshot$1
            if (r5 == 0) goto L1d
            r5 = r4
            com.android.systemui.screenshot.policy.PolicyRequestProcessor$replaceWithTaskSnapshot$1 r5 = (com.android.systemui.screenshot.policy.PolicyRequestProcessor$replaceWithTaskSnapshot$1) r5
            int r6 = r5.label
            r7 = -2147483648(0xffffffff80000000, float:-0.0)
            r8 = r6 & r7
            if (r8 == 0) goto L1d
            int r6 = r6 - r7
            r5.label = r6
            goto L22
        L1d:
            com.android.systemui.screenshot.policy.PolicyRequestProcessor$replaceWithTaskSnapshot$1 r5 = new com.android.systemui.screenshot.policy.PolicyRequestProcessor$replaceWithTaskSnapshot$1
            r5.<init>(r0, r4)
        L22:
            java.lang.Object r4 = r5.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r6 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r7 = r5.label
            r8 = 1
            if (r7 == 0) goto L50
            if (r7 != r8) goto L48
            int r0 = r5.I$0
            java.lang.Object r1 = r5.L$3
            android.graphics.Rect r1 = (android.graphics.Rect) r1
            java.lang.Object r2 = r5.L$2
            android.os.UserHandle r2 = (android.os.UserHandle) r2
            java.lang.Object r3 = r5.L$1
            android.content.ComponentName r3 = (android.content.ComponentName) r3
            java.lang.Object r5 = r5.L$0
            com.android.systemui.screenshot.ScreenshotData r5 = (com.android.systemui.screenshot.ScreenshotData) r5
            kotlin.ResultKt.throwOnFailure(r4)
            r11 = r0
            r12 = r1
            r10 = r3
            r7 = r5
        L46:
            r9 = r2
            goto L8d
        L48:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L50:
            kotlin.ResultKt.throwOnFailure(r4)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r7 = "Capturing task snapshot: "
            r4.<init>(r7)
            r4.append(r1)
            java.lang.String r7 = " / "
            r4.append(r7)
            r4.append(r2)
            java.lang.String r4 = r4.toString()
            java.lang.String r7 = "PolicyRequestProcessor"
            android.util.Log.i(r7, r4)
            r4 = r17
            r5.L$0 = r4
            r5.L$1 = r1
            r5.L$2 = r2
            r7 = r21
            r5.L$3 = r7
            r5.I$0 = r3
            r5.label = r8
            com.android.systemui.screenshot.sep.SemImageCaptureImpl r0 = r0.semImageCapture
            java.lang.Object r0 = r0.semCaptureTask(r3, r5)
            if (r0 != r6) goto L87
            return r6
        L87:
            r10 = r1
            r11 = r3
            r12 = r7
            r7 = r4
            r4 = r0
            goto L46
        L8d:
            kotlin.Pair r4 = (kotlin.Pair) r4
            java.lang.Object r0 = r4.getSecond()
            r13 = r0
            android.graphics.Bitmap r13 = (android.graphics.Bitmap) r13
            if (r13 == 0) goto Laa
            java.lang.Object r0 = r4.getFirst()
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r14 = r0.booleanValue()
            r15 = 834(0x342, float:1.169E-42)
            r8 = 3
            com.android.systemui.screenshot.ScreenshotData r0 = com.android.systemui.screenshot.ScreenshotData.copy$default(r7, r8, r9, r10, r11, r12, r13, r14, r15)
            return r0
        Laa:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Failed to capture screenshot"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.policy.PolicyRequestProcessor.replaceWithTaskSnapshot(com.android.systemui.screenshot.ScreenshotData, android.content.ComponentName, android.os.UserHandle, int, android.graphics.Rect, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public /* synthetic */ PolicyRequestProcessor(CoroutineDispatcher coroutineDispatcher, ImageCapture imageCapture, DisplayContentRepository displayContentRepository, List list, ScreenshotPolicy screenshotPolicy, UserHandle userHandle, ComponentName componentName, SemImageCaptureImpl semImageCaptureImpl, DevicePolicyManager devicePolicyManager, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(coroutineDispatcher, imageCapture, displayContentRepository, list, screenshotPolicy, (i & 32) != 0 ? Process.myUserHandle() : userHandle, componentName, semImageCaptureImpl, devicePolicyManager);
    }
}
