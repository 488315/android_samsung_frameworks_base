package com.android.systemui.screenshot;

import android.app.admin.DevicePolicyManager;
import android.graphics.Bitmap;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.screenshot.ScreenshotPolicy;
import com.android.systemui.screenshot.sep.SemImageCaptureImpl;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RequestProcessor {
    public final String TAG = "Screenshot";
    public final DevicePolicyManager devicePolicyManager;
    public final CoroutineScope mainScope;
    public final ScreenshotPolicy policy;
    public final SemImageCaptureImpl semCapture;

    public RequestProcessor(DevicePolicyManager devicePolicyManager, SemImageCaptureImpl semImageCaptureImpl, ImageCapture imageCapture, ScreenshotPolicy screenshotPolicy, FeatureFlags featureFlags, CoroutineScope coroutineScope) {
        this.devicePolicyManager = devicePolicyManager;
        this.semCapture = semImageCaptureImpl;
        this.policy = screenshotPolicy;
        this.mainScope = coroutineScope;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x012b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b2 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object process(ScreenshotData screenshotData, Continuation continuation) {
        RequestProcessor$process$2 requestProcessor$process$2;
        CoroutineSingletons coroutineSingletons;
        int i;
        Object isManagedProfile$suspendImpl;
        RequestProcessor requestProcessor;
        ScreenshotPolicy.DisplayContentInfo displayContentInfo;
        Bitmap bitmap;
        if (continuation instanceof RequestProcessor$process$2) {
            requestProcessor$process$2 = (RequestProcessor$process$2) continuation;
            int i2 = requestProcessor$process$2.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                requestProcessor$process$2.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = requestProcessor$process$2.result;
                coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = requestProcessor$process$2.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    if (screenshotData.type != 3) {
                        int i3 = screenshotData.displayId;
                        requestProcessor$process$2.L$0 = this;
                        requestProcessor$process$2.L$1 = screenshotData;
                        requestProcessor$process$2.label = 1;
                        ScreenshotPolicyImpl screenshotPolicyImpl = (ScreenshotPolicyImpl) this.policy;
                        screenshotPolicyImpl.getClass();
                        obj = ScreenshotPolicyImpl.findPrimaryContent$suspendImpl(screenshotPolicyImpl, i3, requestProcessor$process$2);
                        if (obj == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    }
                    return screenshotData;
                }
                if (i != 1) {
                    if (i != 2) {
                        if (i != 3) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        displayContentInfo = (ScreenshotPolicy.DisplayContentInfo) requestProcessor$process$2.L$1;
                        screenshotData = (ScreenshotData) requestProcessor$process$2.L$0;
                        ResultKt.throwOnFailure(obj);
                        Pair pair = (Pair) obj;
                        bitmap = (Bitmap) pair.getSecond();
                        if (bitmap != null) {
                            return screenshotData;
                        }
                        screenshotData.secureLayer = ((Boolean) pair.getFirst()).booleanValue();
                        screenshotData.type = 3;
                        screenshotData.bitmap = bitmap;
                        screenshotData.screenBounds = displayContentInfo.bounds;
                        return screenshotData;
                    }
                    displayContentInfo = (ScreenshotPolicy.DisplayContentInfo) requestProcessor$process$2.L$2;
                    screenshotData = (ScreenshotData) requestProcessor$process$2.L$1;
                    requestProcessor = (RequestProcessor) requestProcessor$process$2.L$0;
                    ResultKt.throwOnFailure(obj);
                    if (((Boolean) obj).booleanValue()) {
                        if (new MultiWindowManager().isFlexPanelRunning()) {
                            Log.i(requestProcessor.TAG, "process: skipped for flex panel mode.");
                            return screenshotData;
                        }
                        if (SemPersonaManager.isSecureFolderId(displayContentInfo.user.getIdentifier())) {
                            Log.i(requestProcessor.TAG, "process: skipped for secure folder.");
                            return screenshotData;
                        }
                        boolean screenCaptureDisabled = requestProcessor.devicePolicyManager.getScreenCaptureDisabled(null, displayContentInfo.user.getIdentifier());
                        String str = requestProcessor.TAG;
                        if (screenCaptureDisabled) {
                            Log.i(str, "process: skipped by dpm policy.");
                            screenshotData.type = 3;
                            screenshotData.disableCapture = true;
                            return screenshotData;
                        }
                        int i4 = displayContentInfo.taskId;
                        if (i4 == -1) {
                            Log.i(str, "process: invalid task id");
                            return screenshotData;
                        }
                        requestProcessor$process$2.L$0 = screenshotData;
                        requestProcessor$process$2.L$1 = displayContentInfo;
                        requestProcessor$process$2.L$2 = null;
                        requestProcessor$process$2.label = 3;
                        obj = requestProcessor.semCapture.semCaptureTask(i4, requestProcessor$process$2);
                        if (obj == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        Pair pair2 = (Pair) obj;
                        bitmap = (Bitmap) pair2.getSecond();
                        if (bitmap != null) {
                        }
                    }
                    return screenshotData;
                }
                screenshotData = (ScreenshotData) requestProcessor$process$2.L$1;
                this = (RequestProcessor) requestProcessor$process$2.L$0;
                ResultKt.throwOnFailure(obj);
                ScreenshotPolicy.DisplayContentInfo displayContentInfo2 = (ScreenshotPolicy.DisplayContentInfo) obj;
                Log.d(this.TAG, "findPrimaryContent: " + displayContentInfo2);
                screenshotData.taskId = displayContentInfo2.taskId;
                screenshotData.topComponent = displayContentInfo2.component;
                UserHandle userHandle = displayContentInfo2.user;
                screenshotData.userHandle = userHandle;
                int identifier = userHandle.getIdentifier();
                requestProcessor$process$2.L$0 = this;
                requestProcessor$process$2.L$1 = screenshotData;
                requestProcessor$process$2.L$2 = displayContentInfo2;
                requestProcessor$process$2.label = 2;
                ScreenshotPolicyImpl screenshotPolicyImpl2 = (ScreenshotPolicyImpl) this.policy;
                screenshotPolicyImpl2.getClass();
                isManagedProfile$suspendImpl = ScreenshotPolicyImpl.isManagedProfile$suspendImpl(screenshotPolicyImpl2, identifier, requestProcessor$process$2);
                if (isManagedProfile$suspendImpl != coroutineSingletons) {
                    return coroutineSingletons;
                }
                requestProcessor = this;
                displayContentInfo = displayContentInfo2;
                obj = isManagedProfile$suspendImpl;
                if (((Boolean) obj).booleanValue()) {
                }
                return screenshotData;
            }
        }
        requestProcessor$process$2 = new RequestProcessor$process$2(this, continuation);
        Object obj2 = requestProcessor$process$2.result;
        coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = requestProcessor$process$2.label;
        if (i != 0) {
        }
        ScreenshotPolicy.DisplayContentInfo displayContentInfo22 = (ScreenshotPolicy.DisplayContentInfo) obj2;
        Log.d(this.TAG, "findPrimaryContent: " + displayContentInfo22);
        screenshotData.taskId = displayContentInfo22.taskId;
        screenshotData.topComponent = displayContentInfo22.component;
        UserHandle userHandle2 = displayContentInfo22.user;
        screenshotData.userHandle = userHandle2;
        int identifier2 = userHandle2.getIdentifier();
        requestProcessor$process$2.L$0 = this;
        requestProcessor$process$2.L$1 = screenshotData;
        requestProcessor$process$2.L$2 = displayContentInfo22;
        requestProcessor$process$2.label = 2;
        ScreenshotPolicyImpl screenshotPolicyImpl22 = (ScreenshotPolicyImpl) this.policy;
        screenshotPolicyImpl22.getClass();
        isManagedProfile$suspendImpl = ScreenshotPolicyImpl.isManagedProfile$suspendImpl(screenshotPolicyImpl22, identifier2, requestProcessor$process$2);
        if (isManagedProfile$suspendImpl != coroutineSingletons) {
        }
    }
}
