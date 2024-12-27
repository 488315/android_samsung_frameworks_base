package com.android.server.location.contexthub;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AuthStateDenialTimer {
    public static final long TIMEOUT_MS = TimeUnit.SECONDS.toMillis(60);
    public boolean mCancelled = false;
    public final ContextHubClientBroker mClient;
    public final CountDownHandler mHandler;
    public final long mNanoAppId;
    public long mStopTimeInFuture;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CountDownHandler extends Handler {
        public CountDownHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            AuthStateDenialTimer authStateDenialTimer;
            synchronized (AuthStateDenialTimer.this) {
                try {
                    AuthStateDenialTimer authStateDenialTimer2 = AuthStateDenialTimer.this;
                    if (authStateDenialTimer2.mCancelled) {
                        return;
                    }
                    long elapsedRealtime =
                            authStateDenialTimer2.mStopTimeInFuture - SystemClock.elapsedRealtime();
                    if (elapsedRealtime <= 0) {
                        AuthStateDenialTimer authStateDenialTimer3 = AuthStateDenialTimer.this;
                        ContextHubClientBroker contextHubClientBroker =
                                authStateDenialTimer3.mClient;
                        long j = authStateDenialTimer3.mNanoAppId;
                        synchronized (contextHubClientBroker.mMessageChannelNanoappIdMap) {
                            authStateDenialTimer =
                                    (AuthStateDenialTimer)
                                            ((ConcurrentHashMap)
                                                            contextHubClientBroker
                                                                    .mNappToAuthTimerMap)
                                                    .remove(Long.valueOf(j));
                        }
                        if (authStateDenialTimer != null) {
                            contextHubClientBroker.updateNanoAppAuthState(
                                    j, Collections.emptyList(), true, false);
                        }
                    } else {
                        sendMessageDelayed(obtainMessage(1), elapsedRealtime);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public AuthStateDenialTimer(
            ContextHubClientBroker contextHubClientBroker, long j, Looper looper) {
        this.mClient = contextHubClientBroker;
        this.mNanoAppId = j;
        this.mHandler = new CountDownHandler(looper);
    }
}
