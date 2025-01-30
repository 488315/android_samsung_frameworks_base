package com.android.server.power;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import com.samsung.android.smartface.SmartFaceManager;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class SmartStayController {
    public final Clock mClock;
    public final AtomicBoolean mFaceDetectRequested;
    boolean mFaceDetected;
    public final Injector mInjector;
    public final Object mLock;
    public final Runnable mOnFaceDetected;
    public final SmartFaceCallback mSmartFaceCallback;
    public final SmartFaceManagerWrapper mSmartFaceManagerWrapper;
    public boolean mSmartStayEnabled;
    public Handler mSmartStayHandler;
    public HandlerThread mSmartStayHandlerThread;
    public int mUserActivitySummary;
    public int mWakefulness;

    public interface Clock {
        long uptimeMillis();
    }

    public interface SmartFaceCallback {
        void onResult(long j, long j2, boolean z);
    }

    public SmartStayController(Object obj, Context context, Runnable runnable) {
        this(obj, context, runnable, new Injector());
    }

    public SmartStayController(Object obj, Context context, Runnable runnable, Injector injector) {
        this.mWakefulness = 1;
        this.mSmartFaceCallback = new SmartFaceCallback() { // from class: com.android.server.power.SmartStayController.1
            @Override // com.android.server.power.SmartStayController.SmartFaceCallback
            public void onResult(long j, long j2, boolean z) {
                synchronized (SmartStayController.this.mLock) {
                    SmartStayController smartStayController = SmartStayController.this;
                    if (smartStayController.mWakefulness == 1 && smartStayController.mFaceDetectRequested.get() && (SmartStayController.this.mUserActivitySummary & 1) != 0) {
                        SmartStayController.this.mFaceDetected = z;
                        return;
                    }
                    Slog.m72d("SmartStayController", "handleSmartStay : cancelled");
                }
            }
        };
        this.mLock = obj;
        this.mInjector = injector;
        this.mOnFaceDetected = runnable;
        Clock createClock = injector.createClock();
        this.mClock = createClock;
        this.mSmartFaceManagerWrapper = injector.createSmartFaceManagerWrapper(context, createClock);
        this.mFaceDetectRequested = new AtomicBoolean();
    }

    public void setSmartStayLocked(boolean z, int i) {
        if (z) {
            startSmartStayLocked(i);
        } else {
            stopSmartStayLocked();
        }
    }

    public final void startSmartStayLocked(int i) {
        if (this.mSmartStayEnabled) {
            Slog.m72d("SmartStayController", "SmartStay already started");
            return;
        }
        Slog.m72d("SmartStayController", "SmartStay start!");
        this.mSmartStayEnabled = true;
        HandlerThread createHandlerThread = this.mInjector.createHandlerThread("smart stay");
        this.mSmartStayHandlerThread = createHandlerThread;
        createHandlerThread.start();
        this.mSmartStayHandler = new Handler(this.mSmartStayHandlerThread.getLooper());
        this.mWakefulness = i;
    }

    public final void stopSmartStayLocked() {
        if (this.mSmartStayEnabled) {
            Slog.m72d("SmartStayController", "SmartStay stop");
            this.mSmartStayEnabled = false;
            this.mSmartStayHandler.removeCallbacksAndMessages(null);
            this.mSmartStayHandlerThread.quit();
            this.mSmartStayHandlerThread.interrupt();
            this.mSmartStayHandlerThread = null;
            cancelFaceDetect();
        }
    }

    public void checkFaceDetectLocked() {
        if (this.mFaceDetected) {
            Slog.m72d("SmartStayController", "UserActivityState : poke userActivity (face detected)");
            this.mOnFaceDetected.run();
        }
        if (this.mFaceDetectRequested.get()) {
            cancelFaceDetect();
        }
    }

    public void onUserActivity() {
        cancelFaceDetect();
    }

    public void onWakefulnessChangeStarted(int i) {
        this.mWakefulness = i;
        if (i != 1) {
            cancelFaceDetect();
        }
    }

    public final void cancelFaceDetect() {
        this.mFaceDetectRequested.set(false);
        this.mFaceDetected = false;
    }

    public long updateUserActivity(long j, long j2, int i, int i2) {
        this.mUserActivitySummary = i;
        if (this.mWakefulness == 1 && (i & 1) != 0) {
            long smartStayCheckDuration = j2 - this.mSmartFaceManagerWrapper.getSmartStayCheckDuration();
            if (j < smartStayCheckDuration) {
                return smartStayCheckDuration;
            }
            if (this.mFaceDetectRequested.get() || (i2 & 2) != 0) {
                return j2;
            }
            this.mFaceDetectRequested.set(true);
            this.mSmartStayHandler.postAtTime(new Runnable() { // from class: com.android.server.power.SmartStayController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SmartStayController.this.lambda$updateUserActivity$0();
                }
            }, this.mClock.uptimeMillis());
        }
        return j2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateUserActivity$0() {
        this.mSmartFaceManagerWrapper.handleSmartStay(this.mSmartFaceCallback);
    }

    public void dumpInternal(PrintWriter printWriter) {
        printWriter.println("Smart Stay:");
        printWriter.println("  USE_SMART_STAY: true");
        printWriter.println("  mSmartStayEnabled: " + this.mSmartStayEnabled);
        printWriter.println("  SmartStayDelay: " + this.mSmartFaceManagerWrapper.getSmartStayCheckDuration());
        printWriter.println("  mFaceDetectRequested: " + this.mFaceDetectRequested.get());
        printWriter.println("  PREVENT_BAD_CURRENT_CONSUMPTION: true");
    }

    public class SmartFaceManagerWrapper {
        public final Clock mClock;
        public final SmartFaceManager mSmartFaceManager;

        public long getSmartStayCheckDuration() {
            return 2750L;
        }

        public SmartFaceManagerWrapper(Context context, Clock clock) {
            this.mClock = clock;
            this.mSmartFaceManager = SmartFaceManager.getSmartFaceManager(context);
        }

        public void handleSmartStay(SmartFaceCallback smartFaceCallback) {
            long uptimeMillis = this.mClock.uptimeMillis();
            boolean faceDetect = faceDetect();
            long uptimeMillis2 = this.mClock.uptimeMillis();
            Slog.m72d("SmartStayController", "handleSmartStay : faceDetected : " + faceDetect);
            smartFaceCallback.onResult(uptimeMillis, uptimeMillis2, faceDetect);
        }

        public final boolean faceDetect() {
            return this.mSmartFaceManager.checkForSmartStay();
        }
    }

    public class Injector {
        public SmartFaceManagerWrapper createSmartFaceManagerWrapper(Context context, Clock clock) {
            return new SmartFaceManagerWrapper(context, clock);
        }

        public Clock createClock() {
            return new Clock() { // from class: com.android.server.power.SmartStayController$Injector$$ExternalSyntheticLambda0
                @Override // com.android.server.power.SmartStayController.Clock
                public final long uptimeMillis() {
                    return SystemClock.uptimeMillis();
                }
            };
        }

        public HandlerThread createHandlerThread(String str) {
            return new HandlerThread(str);
        }
    }
}
