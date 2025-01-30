package com.android.systemui.screenshot.sep;

import android.app.IActivityTaskManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.IWindowManager;
import android.window.TaskSnapshot;
import com.android.systemui.screenshot.ImageCaptureImpl;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SemImageCaptureImpl extends ImageCaptureImpl {
    public final String TAG;
    public final IActivityTaskManager atmService;
    public final CoroutineDispatcher bgContext;
    public final Context context;
    public final DevicePolicyManager devicePolicyManager;
    public final boolean useIdentityTransform;
    public final IWindowManager windowManager;

    public SemImageCaptureImpl(Context context, DevicePolicyManager devicePolicyManager, IWindowManager iWindowManager, IActivityTaskManager iActivityTaskManager, CoroutineDispatcher coroutineDispatcher) {
        super(iWindowManager, iActivityTaskManager, coroutineDispatcher);
        this.context = context;
        this.devicePolicyManager = devicePolicyManager;
        this.windowManager = iWindowManager;
        this.atmService = iActivityTaskManager;
        this.bgContext = coroutineDispatcher;
        this.TAG = "Screenshot";
        this.useIdentityTransform = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object semCaptureTask(int i, Continuation continuation) {
        SemImageCaptureImpl$semCaptureTask$1 semImageCaptureImpl$semCaptureTask$1;
        int i2;
        TaskSnapshot taskSnapshot;
        if (continuation instanceof SemImageCaptureImpl$semCaptureTask$1) {
            semImageCaptureImpl$semCaptureTask$1 = (SemImageCaptureImpl$semCaptureTask$1) continuation;
            int i3 = semImageCaptureImpl$semCaptureTask$1.label;
            if ((i3 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                semImageCaptureImpl$semCaptureTask$1.label = i3 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = semImageCaptureImpl$semCaptureTask$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i2 = semImageCaptureImpl$semCaptureTask$1.label;
                if (i2 != 0) {
                    ResultKt.throwOnFailure(obj);
                    SemImageCaptureImpl$semCaptureTask$snapshot$1 semImageCaptureImpl$semCaptureTask$snapshot$1 = new SemImageCaptureImpl$semCaptureTask$snapshot$1(this, i, null);
                    semImageCaptureImpl$semCaptureTask$1.L$0 = this;
                    semImageCaptureImpl$semCaptureTask$1.label = 1;
                    obj = BuildersKt.withContext(this.bgContext, semImageCaptureImpl$semCaptureTask$snapshot$1, semImageCaptureImpl$semCaptureTask$1);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    this = (SemImageCaptureImpl) semImageCaptureImpl$semCaptureTask$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                taskSnapshot = (TaskSnapshot) obj;
                if (taskSnapshot != null) {
                    return new Pair(Boolean.FALSE, null);
                }
                Bitmap wrapHardwareBuffer = Bitmap.wrapHardwareBuffer(taskSnapshot.getHardwareBuffer(), taskSnapshot.getColorSpace());
                boolean containsSecureLayers = taskSnapshot.containsSecureLayers();
                if (containsSecureLayers) {
                    Log.i(this.TAG, "semCaptureTask: snapshot is a secure layer.");
                }
                return new Pair(Boolean.valueOf(containsSecureLayers), wrapHardwareBuffer);
            }
        }
        semImageCaptureImpl$semCaptureTask$1 = new SemImageCaptureImpl$semCaptureTask$1(this, continuation);
        Object obj2 = semImageCaptureImpl$semCaptureTask$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i2 = semImageCaptureImpl$semCaptureTask$1.label;
        if (i2 != 0) {
        }
        taskSnapshot = (TaskSnapshot) obj2;
        if (taskSnapshot != null) {
        }
    }
}
