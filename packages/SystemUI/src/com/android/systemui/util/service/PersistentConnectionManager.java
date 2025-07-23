package com.android.systemui.util.service;

import android.util.Log;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceStateLogger;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.service.ObservableServiceConnection;
import com.android.systemui.util.service.Observer;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PersistentConnectionManager<T> implements Dumpable {
    private static final String TAG = "PersistentConnManager";
    private final int mBaseReconnectDelayMs;
    private final DelayableExecutor mBgExecutor;
    private final ObservableServiceConnection<T> mConnection;
    private final TraceStateLogger mConnectionReasonLogger;
    private Runnable mCurrentReconnectCancelable;
    private final DumpManager mDumpManager;
    private final String mDumpsysName;
    private final int mMaxReconnectAttempts;
    private final int mMinConnectionDuration;
    private final Observer mObserver;
    private final SystemClock mSystemClock;
    private int mReconnectAttempts = 0;
    private final Runnable mConnectRunnable = new Runnable() { // from class: com.android.systemui.util.service.PersistentConnectionManager.1
        @Override // java.lang.Runnable
        public void run() {
            PersistentConnectionManager.this.mConnectionReasonLogger.log("ConnectionReasonRetry");
            PersistentConnectionManager.this.mCurrentReconnectCancelable = null;
            PersistentConnectionManager.this.mConnection.bind();
        }
    };
    private final Observer.Callback mObserverCallback = new Observer.Callback() { // from class: com.android.systemui.util.service.PersistentConnectionManager$$ExternalSyntheticLambda0
        @Override // com.android.systemui.util.service.Observer.Callback
        public final void onSourceChanged() {
            PersistentConnectionManager.this.lambda$new$0();
        }
    };
    private final ObservableServiceConnection.Callback<T> mConnectionCallback = (ObservableServiceConnection.Callback<T>) new ObservableServiceConnection.Callback<Object>() { // from class: com.android.systemui.util.service.PersistentConnectionManager.2
        private long mStartTime = -1;

        @Override // com.android.systemui.util.service.ObservableServiceConnection.Callback
        public void onConnected(ObservableServiceConnection<Object> observableServiceConnection, Object obj) {
            this.mStartTime = PersistentConnectionManager.this.mSystemClock.currentTimeMillis();
        }

        @Override // com.android.systemui.util.service.ObservableServiceConnection.Callback
        public void onDisconnected(ObservableServiceConnection<Object> observableServiceConnection, int i) {
            if (i == 4) {
                return;
            }
            if (this.mStartTime <= 0) {
                Log.e(PersistentConnectionManager.TAG, "onDisconnected called with invalid connection start time: " + this.mStartTime);
                return;
            }
            float currentTimeMillis = PersistentConnectionManager.this.mSystemClock.currentTimeMillis() - this.mStartTime;
            this.mStartTime = -1L;
            if (currentTimeMillis <= PersistentConnectionManager.this.mMinConnectionDuration) {
                PersistentConnectionManager.this.scheduleConnectionAttempt();
                return;
            }
            Log.i(PersistentConnectionManager.TAG, "immediately reconnecting since service was connected for " + currentTimeMillis + "ms which is longer than the min duration of " + PersistentConnectionManager.this.mMinConnectionDuration + "ms");
            PersistentConnectionManager.this.initiateConnectionAttempt("ConnectionReasonMinDurationMet");
        }
    };

    public PersistentConnectionManager(SystemClock systemClock, DelayableExecutor delayableExecutor, DumpManager dumpManager, String str, ObservableServiceConnection<T> observableServiceConnection, int i, int i2, int i3, Observer observer) {
        this.mSystemClock = systemClock;
        this.mBgExecutor = delayableExecutor;
        this.mConnection = observableServiceConnection;
        this.mObserver = observer;
        this.mDumpManager = dumpManager;
        String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("PersistentConnManager#", str);
        this.mDumpsysName = m;
        this.mConnectionReasonLogger = new TraceStateLogger(m);
        this.mMaxReconnectAttempts = i;
        this.mBaseReconnectDelayMs = i2;
        this.mMinConnectionDuration = i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initiateConnectionAttempt(String str) {
        this.mConnectionReasonLogger.log(str);
        this.mReconnectAttempts = 0;
        this.mConnection.bind();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        initiateConnectionAttempt("ConnectionReasonObserver");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleConnectionAttempt() {
        Runnable runnable = this.mCurrentReconnectCancelable;
        if (runnable != null) {
            runnable.run();
            this.mCurrentReconnectCancelable = null;
        }
        int i = this.mReconnectAttempts;
        if (i >= this.mMaxReconnectAttempts) {
            Log.d(TAG, "exceeded max connection attempts.");
            return;
        }
        long scalb = (long) Math.scalb(this.mBaseReconnectDelayMs, i);
        Log.d(TAG, "scheduling connection attempt in " + scalb + "milliseconds");
        this.mCurrentReconnectCancelable = this.mBgExecutor.executeDelayed(this.mConnectRunnable, scalb);
        this.mReconnectAttempts = this.mReconnectAttempts + 1;
    }

    public void addConnectionCallback(ObservableServiceConnection.Callback<T> callback) {
        this.mConnection.addCallback(callback);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        MagnificationImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("mMaxReconnectAttempts: "), this.mMaxReconnectAttempts, printWriter, "mBaseReconnectDelayMs: "), this.mBaseReconnectDelayMs, printWriter, "mMinConnectionDuration: "), this.mMinConnectionDuration, printWriter, "mReconnectAttempts: "), this.mReconnectAttempts, printWriter);
        this.mConnection.dump(printWriter);
    }

    public void removeConnectionCallback(ObservableServiceConnection.Callback<T> callback) {
        this.mConnection.removeCallback(callback);
    }

    public void start() {
        this.mDumpManager.registerCriticalDumpable(this.mDumpsysName, this);
        this.mConnection.addCallback(this.mConnectionCallback);
        this.mObserver.addCallback(this.mObserverCallback);
        initiateConnectionAttempt("ConnectionReasonStart");
    }

    public void stop() {
        this.mConnection.removeCallback(this.mConnectionCallback);
        this.mObserver.removeCallback(this.mObserverCallback);
        this.mConnection.unbind();
        this.mDumpManager.unregisterDumpable(this.mDumpsysName);
    }
}
