package com.android.internal.os;

import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.EventLog;
import android.util.SparseIntArray;
import com.android.internal.os.BinderCallsStats;
import com.android.internal.os.BinderInternal;
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
    static final BinderProxyCountEventListenerDelegate sBinderProxyCountEventListenerDelegate = new BinderProxyCountEventListenerDelegate();

    public interface BinderProxyCountEventListener {
        void onLimitReached(int i);

        default void onWarningThresholdReached(int i) {
        }
    }

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
        GcWatcher() {
        }

        protected void finalize() throws Throwable {
            BinderInternal.handleGc();
            BinderInternal.sLastGcTime = SystemClock.uptimeMillis();
            synchronized (BinderInternal.sGcWatchers) {
                BinderInternal.sTmpWatchers = (Runnable[]) BinderInternal.sGcWatchers.toArray(BinderInternal.sTmpWatchers);
            }
            for (int i = 0; i < BinderInternal.sTmpWatchers.length; i++) {
                if (BinderInternal.sTmpWatchers[i] != null) {
                    BinderInternal.sTmpWatchers[i].run();
                }
            }
            BinderInternal.sGcWatcher = new WeakReference<>(new GcWatcher());
        }
    }

    public static void addGcWatcher(Runnable runnable) {
        synchronized (sGcWatchers) {
            sGcWatchers.add(runnable);
        }
    }

    public static long getLastGcTime() {
        return sLastGcTime;
    }

    public static void forceGc(String str) {
        EventLog.writeEvent(2741, str);
        VMRuntime.getRuntime().requestConcurrentGC();
    }

    static void forceBinderGc() {
        forceGc("Binder");
    }

    public static void binderProxyLimitCallbackFromNative(int i) {
        sBinderProxyCountEventListenerDelegate.notifyLimitReached(i);
    }

    public static void binderProxyWarningCallbackFromNative(int i) {
        sBinderProxyCountEventListenerDelegate.notifyWarningReached(i);
    }

    public static void setBinderProxyCountCallback(BinderProxyCountEventListener binderProxyCountEventListener, Handler handler) {
        Preconditions.checkNotNull(handler, "Must provide NonNull Handler to setBinderProxyCountCallback when setting BinderProxyCountEventListener");
        sBinderProxyCountEventListenerDelegate.setListener(binderProxyCountEventListener, handler);
    }

    public static void clearBinderProxyCountCallback() {
        sBinderProxyCountEventListenerDelegate.setListener(null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class BinderProxyCountEventListenerDelegate {
        private BinderProxyCountEventListener mBinderProxyCountEventListener;
        private Handler mHandler;

        private BinderProxyCountEventListenerDelegate() {
        }

        void setListener(BinderProxyCountEventListener binderProxyCountEventListener, Handler handler) {
            synchronized (this) {
                this.mBinderProxyCountEventListener = binderProxyCountEventListener;
                this.mHandler = handler;
            }
        }

        void notifyLimitReached(final int i) {
            synchronized (this) {
                if (this.mBinderProxyCountEventListener != null) {
                    this.mHandler.post(new Runnable() { // from class: com.android.internal.os.BinderInternal$BinderProxyCountEventListenerDelegate$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            BinderInternal.BinderProxyCountEventListenerDelegate.this.lambda$notifyLimitReached$0(i);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyLimitReached$0(int i) {
            this.mBinderProxyCountEventListener.onLimitReached(i);
        }

        void notifyWarningReached(final int i) {
            synchronized (this) {
                if (this.mBinderProxyCountEventListener != null) {
                    this.mHandler.post(new Runnable() { // from class: com.android.internal.os.BinderInternal$BinderProxyCountEventListenerDelegate$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            BinderInternal.BinderProxyCountEventListenerDelegate.this.lambda$notifyWarningReached$1(i);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyWarningReached$1(int i) {
            this.mBinderProxyCountEventListener.onWarningThresholdReached(i);
        }
    }
}
