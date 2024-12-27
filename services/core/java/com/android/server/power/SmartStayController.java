package com.android.server.power;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.VcnManagementService$$ExternalSyntheticLambda10;

import com.samsung.android.smartface.SmartFaceManager;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SmartStayController {
    public final VcnManagementService$$ExternalSyntheticLambda10 mClock;
    public final AtomicBoolean mFaceDetectRequested;
    boolean mFaceDetected;
    public final Injector mInjector;
    public final Object mLock;
    public final Runnable mOnFaceDetected;
    public final SmartFaceManagerWrapper mSmartFaceManagerWrapper;
    public boolean mSmartStayEnabled;
    public Handler mSmartStayHandler;
    public HandlerThread mSmartStayHandlerThread;
    public int mUserActivitySummary;
    public int mWakefulness = 1;
    public final AnonymousClass1 mSmartFaceCallback = new AnonymousClass1();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.power.SmartStayController$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SmartFaceManagerWrapper {
        public final VcnManagementService$$ExternalSyntheticLambda10 mClock;
        public final SmartFaceManager mSmartFaceManager;

        public SmartFaceManagerWrapper(
                Context context,
                VcnManagementService$$ExternalSyntheticLambda10
                        vcnManagementService$$ExternalSyntheticLambda10) {
            this.mClock = vcnManagementService$$ExternalSyntheticLambda10;
            this.mSmartFaceManager = SmartFaceManager.getSmartFaceManager(context);
        }
    }

    public SmartStayController(Object obj, Context context, Runnable runnable, Injector injector) {
        this.mLock = obj;
        this.mInjector = injector;
        this.mOnFaceDetected = runnable;
        injector.getClass();
        VcnManagementService$$ExternalSyntheticLambda10
                vcnManagementService$$ExternalSyntheticLambda10 =
                        new VcnManagementService$$ExternalSyntheticLambda10();
        this.mClock = vcnManagementService$$ExternalSyntheticLambda10;
        injector.getClass();
        this.mSmartFaceManagerWrapper =
                new SmartFaceManagerWrapper(
                        context, vcnManagementService$$ExternalSyntheticLambda10);
        this.mFaceDetectRequested = new AtomicBoolean();
    }

    public final void dumpInternal(PrintWriter printWriter) {
        printWriter.println("Smart Stay:");
        printWriter.println("  USE_SMART_STAY: true");
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                new StringBuilder("  mSmartStayEnabled: "), this.mSmartStayEnabled, printWriter);
        this.mSmartFaceManagerWrapper.getClass();
        printWriter.println("  SmartStayDelay: 2750");
        printWriter.println("  mFaceDetectRequested: " + this.mFaceDetectRequested.get());
        printWriter.println("  PREVENT_BAD_CURRENT_CONSUMPTION: true");
    }
}
