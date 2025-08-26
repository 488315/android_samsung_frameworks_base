package com.android.systemui.screenshot.sep;

import android.app.IActivityTaskManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.IWindowManager;
import android.window.TaskSnapshot;
import com.android.systemui.screenshot.ImageCaptureImpl;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes2.dex */
public final class SemImageCaptureImpl extends ImageCaptureImpl {
    public final String TAG;
    public final IActivityTaskManager atmService;
    public final CoroutineDispatcher bgContext;
    public final Context context;
    public final DevicePolicyManager devicePolicyManager;
    public final IWindowManager windowManager;

    /* renamed from: com.android.systemui.screenshot.sep.SemImageCaptureImpl$semCaptureTask$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SemImageCaptureImpl.this.semCaptureTask(0, this);
        }
    }

    public SemImageCaptureImpl(Context context, DevicePolicyManager devicePolicyManager, IWindowManager iWindowManager, IActivityTaskManager iActivityTaskManager, CoroutineDispatcher coroutineDispatcher) {
        super(iWindowManager, iActivityTaskManager, coroutineDispatcher);
        this.context = context;
        this.devicePolicyManager = devicePolicyManager;
        this.windowManager = iWindowManager;
        this.atmService = iActivityTaskManager;
        this.bgContext = coroutineDispatcher;
        this.TAG = "Screenshot";
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object semCaptureTask(int i, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i2 = anonymousClass1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i2 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objWithContext = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = anonymousClass1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            SemImageCaptureImpl$semCaptureTask$snapshot$1 semImageCaptureImpl$semCaptureTask$snapshot$1 = new SemImageCaptureImpl$semCaptureTask$snapshot$1(this, i, null);
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 1;
            objWithContext = BuildersKt.withContext(this.bgContext, semImageCaptureImpl$semCaptureTask$snapshot$1, anonymousClass1);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (SemImageCaptureImpl) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objWithContext);
        }
        TaskSnapshot taskSnapshot = (TaskSnapshot) objWithContext;
        if (taskSnapshot == null) {
            return new Pair(Boolean.FALSE, null);
        }
        Bitmap bitmapWrapHardwareBuffer = Bitmap.wrapHardwareBuffer(taskSnapshot.getHardwareBuffer(), taskSnapshot.getColorSpace());
        boolean zContainsSecureLayers = taskSnapshot.containsSecureLayers();
        if (zContainsSecureLayers) {
            Log.i(this.TAG, "semCaptureTask: snapshot is a secure layer.");
        }
        return new Pair(Boolean.valueOf(zContainsSecureLayers), bitmapWrapHardwareBuffer);
    }
}
