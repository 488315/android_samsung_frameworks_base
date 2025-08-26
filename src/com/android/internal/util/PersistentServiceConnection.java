package com.android.internal.util;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import com.android.internal.util.ObservableServiceConnection;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public class PersistentServiceConnection<T> extends ObservableServiceConnection<T> {
    private final int mBaseReconnectDelayMs;
    private Object mCancelToken;
    private final Runnable mConnectRunnable;
    private final ObservableServiceConnection.Callback<T> mConnectionCallback;
    private final Handler mHandler;
    private final Injector mInjector;
    private final Object mLock;
    private final int mMaxReconnectAttempts;
    private final int mMinConnectionDurationMs;
    private int mReconnectAttempts;

    public PersistentServiceConnection(Context context, Executor executor, Handler handler, ObservableServiceConnection.ServiceTransformer<T> serviceTransformer, Intent intent, int i, int i2, int i3, int i4) {
        this(context, executor, handler, serviceTransformer, intent, i, i2, i3, i4, new Injector());
    }

    public PersistentServiceConnection(Context context, Executor executor, Handler handler, ObservableServiceConnection.ServiceTransformer<T> serviceTransformer, Intent intent, int i, int i2, int i3, int i4, Injector injector) {
        super(context, executor, serviceTransformer, intent, i);
        this.mConnectionCallback = new ObservableServiceConnection.Callback<T>() { // from class: com.android.internal.util.PersistentServiceConnection.1
            private long mConnectedTime;

            @Override // com.android.internal.util.ObservableServiceConnection.Callback
            public void onConnected(ObservableServiceConnection<T> observableServiceConnection, T t) {
                this.mConnectedTime = PersistentServiceConnection.this.mInjector.uptimeMillis();
            }

            @Override // com.android.internal.util.ObservableServiceConnection.Callback
            public void onDisconnected(ObservableServiceConnection<T> observableServiceConnection, int i5) {
                if (i5 == 4) {
                    return;
                }
                synchronized (PersistentServiceConnection.this.mLock) {
                    if (PersistentServiceConnection.this.mInjector.uptimeMillis() - this.mConnectedTime > PersistentServiceConnection.this.mMinConnectionDurationMs) {
                        PersistentServiceConnection.this.mReconnectAttempts = 0;
                        PersistentServiceConnection.this.bindInternalLocked();
                    } else {
                        PersistentServiceConnection.this.scheduleConnectionAttemptLocked();
                    }
                }
            }
        };
        this.mLock = new Object();
        this.mConnectRunnable = new Runnable() { // from class: com.android.internal.util.PersistentServiceConnection.2
            @Override // java.lang.Runnable
            public void run() {
                synchronized (PersistentServiceConnection.this.mLock) {
                    PersistentServiceConnection.this.mCancelToken = null;
                    PersistentServiceConnection.this.bindInternalLocked();
                }
            }
        };
        this.mHandler = handler;
        this.mMinConnectionDurationMs = i2;
        this.mMaxReconnectAttempts = i3;
        this.mBaseReconnectDelayMs = i4;
        this.mInjector = injector;
    }

    @Override // com.android.internal.util.ObservableServiceConnection
    public boolean bind() {
        boolean zBindInternalLocked;
        synchronized (this.mLock) {
            addCallback(this.mConnectionCallback);
            this.mReconnectAttempts = 0;
            zBindInternalLocked = bindInternalLocked();
        }
        return zBindInternalLocked;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean bindInternalLocked() {
        return super.bind();
    }

    @Override // com.android.internal.util.ObservableServiceConnection
    public void unbind() {
        synchronized (this.mLock) {
            removeCallback(this.mConnectionCallback);
            cancelPendingConnectionAttemptLocked();
            super.unbind();
        }
    }

    private void cancelPendingConnectionAttemptLocked() {
        Object obj = this.mCancelToken;
        if (obj != null) {
            this.mHandler.removeCallbacksAndMessages(obj);
            this.mCancelToken = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleConnectionAttemptLocked() {
        cancelPendingConnectionAttemptLocked();
        int i = this.mReconnectAttempts;
        if (i >= this.mMaxReconnectAttempts) {
            return;
        }
        long jScalb = (long) Math.scalb(this.mBaseReconnectDelayMs, i);
        Object obj = new Object();
        this.mCancelToken = obj;
        this.mHandler.postDelayed(this.mConnectRunnable, obj, jScalb);
        this.mReconnectAttempts++;
    }

    public static class Injector {
        public long uptimeMillis() {
            return SystemClock.uptimeMillis();
        }
    }
}
