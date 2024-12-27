package com.android.internal.os;

import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.EventLog;
import android.util.SparseIntArray;

import com.android.internal.util.Preconditions;

import dalvik.system.VMRuntime;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: classes5.dex */
public class BinderInternal {
    private static final String TAG = "BinderInternal";
    static long sLastGcTime;
    static WeakReference<GcWatcher> sGcWatcher = new WeakReference<>(new GcWatcher());
    static ArrayList<Runnable> sGcWatchers = new ArrayList<>();
    static Runnable[] sTmpWatchers = new Runnable[1];
    static final BinderProxyCountEventListenerDelegate sBinderProxyCountEventListenerDelegate =
            new BinderProxyCountEventListenerDelegate();

    public static class CallSession {
        public Class<? extends Binder> binderClass;
        long cpuTimeStarted;
        boolean exceptionThrown;
        public boolean recordedCall;
        long timeStarted;
        public int transactionCode;
    }

    public interface CallStatsObserver {
        void noteBinderThreadNativeIds(int[] iArr);

        void noteCallStats(int i, long j, Collection<BinderCallsStats.CallStat> collection);
    }

    public interface Observer {
        void callEnded(CallSession callSession, int i, int i2, int i3);

        CallSession callStarted(Binder binder, int i, int i2);

        void callThrewException(CallSession callSession, Exception exc);
    }

    @FunctionalInterface
    public interface WorkSourceProvider {
        int resolveWorkSourceUid(int i);
    }

    public static final native void disableBackgroundScheduling(boolean z);

    public static final native IBinder getContextObject();

    static final native void handleGc();

    public static final native void joinThreadPool();

    public static final native int nGetBinderProxyCount(int i);

    public static final native SparseIntArray nGetBinderProxyPerUidCounts();

    public static final native void nSetBinderProxyCountEnabled(boolean z);

    public static final native void nSetBinderProxyCountWatermarks(int i, int i2, int i3);

    public static final native void setMaxThreads(int i);

    static final class GcWatcher {
        GcWatcher() {}

        protected void finalize() throws Throwable {
            BinderInternal.handleGc();
            BinderInternal.sLastGcTime = SystemClock.uptimeMillis();
            synchronized (BinderInternal.sGcWatchers) {
                BinderInternal.sTmpWatchers =
                        (Runnable[])
                                BinderInternal.sGcWatchers.toArray(BinderInternal.sTmpWatchers);
            }
            for (int i = 0; i < BinderInternal.sTmpWatchers.length; i++) {
                if (BinderInternal.sTmpWatchers[i] != null) {
                    BinderInternal.sTmpWatchers[i].run();
                }
            }
            BinderInternal.sGcWatcher = new WeakReference<>(new GcWatcher());
        }
    }

    public static void addGcWatcher(Runnable watcher) {
        synchronized (sGcWatchers) {
            sGcWatchers.add(watcher);
        }
    }

    public static long getLastGcTime() {
        return sLastGcTime;
    }

    public static void forceGc(String reason) {
        EventLog.writeEvent(2741, reason);
        VMRuntime.getRuntime().requestConcurrentGC();
    }

    static void forceBinderGc() {
        forceGc("Binder");
    }

    public interface BinderProxyCountEventListener {
        void onLimitReached(int i);

        default void onWarningThresholdReached(int uid) {}
    }

    public static void binderProxyLimitCallbackFromNative(int uid) {
        sBinderProxyCountEventListenerDelegate.notifyLimitReached(uid);
    }

    public static void binderProxyWarningCallbackFromNative(int uid) {
        sBinderProxyCountEventListenerDelegate.notifyWarningReached(uid);
    }

    public static void setBinderProxyCountCallback(
            BinderProxyCountEventListener listener, Handler handler) {
        Preconditions.checkNotNull(
                handler,
                "Must provide NonNull Handler to setBinderProxyCountCallback when setting"
                    + " BinderProxyCountEventListener");
        sBinderProxyCountEventListenerDelegate.setListener(listener, handler);
    }

    public static void clearBinderProxyCountCallback() {
        sBinderProxyCountEventListenerDelegate.setListener(null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class BinderProxyCountEventListenerDelegate {
        private BinderProxyCountEventListener mBinderProxyCountEventListener;
        private Handler mHandler;

        private BinderProxyCountEventListenerDelegate() {}

        void setListener(BinderProxyCountEventListener listener, Handler handler) {
            synchronized (this) {
                this.mBinderProxyCountEventListener = listener;
                this.mHandler = handler;
            }
        }

        void notifyLimitReached(final int uid) {
            synchronized (this) {
                if (this.mBinderProxyCountEventListener != null) {
                    this.mHandler.post(
                            new Runnable() { // from class:
                                             // com.android.internal.os.BinderInternal$BinderProxyCountEventListenerDelegate$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BinderInternal.BinderProxyCountEventListenerDelegate.this
                                            .lambda$notifyLimitReached$0(uid);
                                }
                            });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyLimitReached$0(int uid) {
            this.mBinderProxyCountEventListener.onLimitReached(uid);
        }

        void notifyWarningReached(final int uid) {
            synchronized (this) {
                if (this.mBinderProxyCountEventListener != null) {
                    this.mHandler.post(
                            new Runnable() { // from class:
                                             // com.android.internal.os.BinderInternal$BinderProxyCountEventListenerDelegate$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BinderInternal.BinderProxyCountEventListenerDelegate.this
                                            .lambda$notifyWarningReached$1(uid);
                                }
                            });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyWarningReached$1(int uid) {
            this.mBinderProxyCountEventListener.onWarningThresholdReached(uid);
        }
    }
}
