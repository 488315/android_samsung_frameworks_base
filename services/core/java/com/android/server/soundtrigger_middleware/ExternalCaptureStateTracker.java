package com.android.server.soundtrigger_middleware;

import android.util.Slog;
import com.android.server.soundtrigger_middleware.ICaptureStateNotifier;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
class ExternalCaptureStateTracker implements ICaptureStateNotifier {
    public static final String TAG = "CaptureStateTracker";
    public final List mListeners = new LinkedList();
    public boolean mCaptureActive = true;
    public final Semaphore mNeedToConnect = new Semaphore(1);

    public ExternalCaptureStateTracker() {
        new Thread(new Runnable() { // from class: com.android.server.soundtrigger_middleware.ExternalCaptureStateTracker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ExternalCaptureStateTracker.this.run();
            }
        }).start();
    }

    private native void connect();

    public final void binderDied() {
        Slog.w(TAG, "Audio policy service died");
        this.mNeedToConnect.release();
    }

    public final boolean registerListener(ICaptureStateNotifier.Listener listener) {
        boolean z;
        synchronized (this.mListeners) {
            this.mListeners.add(listener);
            z = this.mCaptureActive;
        }
        return z;
    }

    public final void run() {
        while (true) {
            this.mNeedToConnect.acquireUninterruptibly();
            connect();
        }
    }

    public final void setCaptureState(boolean z) {
        try {
            synchronized (this.mListeners) {
                try {
                    this.mCaptureActive = z;
                    Iterator it = this.mListeners.iterator();
                    while (it.hasNext()) {
                        SoundTriggerHalConcurrentCaptureHandler soundTriggerHalConcurrentCaptureHandler = (SoundTriggerHalConcurrentCaptureHandler) ((ICaptureStateNotifier.Listener) it.next());
                        synchronized (soundTriggerHalConcurrentCaptureHandler.mStartStopLock) {
                            if (z) {
                                soundTriggerHalConcurrentCaptureHandler.abortAllActiveModels();
                            } else {
                                SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda0 soundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda0 = soundTriggerHalConcurrentCaptureHandler.mGlobalCallback;
                                if (soundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda0 != null) {
                                    soundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda0.onResourcesAvailable();
                                }
                            }
                            soundTriggerHalConcurrentCaptureHandler.mCaptureState = z;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                } finally {
                }
            }
        } catch (Exception e) {
            Slog.e(TAG, "Exception caught while setting capture state", e);
        }
    }

    @Override // com.android.server.soundtrigger_middleware.ICaptureStateNotifier
    public final void unregisterListener(ICaptureStateNotifier.Listener listener) {
        synchronized (this.mListeners) {
            this.mListeners.remove(listener);
        }
    }
}
