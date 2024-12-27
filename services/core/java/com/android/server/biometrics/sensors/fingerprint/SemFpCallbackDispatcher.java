package com.android.server.biometrics.sensors.fingerprint;

import android.os.Binder;
import android.os.Handler;
import android.util.Pair;

import com.android.server.biometrics.sensors.AuthenticationClient;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.EnrollClient;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider$$ExternalSyntheticLambda10;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SemFpCallbackDispatcher implements ClientMonitorCallback {
    public final Handler mHandler;
    public final ServiceProvider mProvider;
    public final List mChallengeListeners = new ArrayList();
    public final List mEventListeners = new ArrayList();
    public final List mAuthenticationListeners = new ArrayList();
    public final List mEnrollListeners = new ArrayList();
    public final List mCaptureEventListeners = new ArrayList();
    public final List mResetLockoutListeners = new ArrayList();

    public SemFpCallbackDispatcher(ServiceProvider serviceProvider, Handler handler) {
        this.mProvider = serviceProvider;
        this.mHandler = handler;
    }

    public final void dispatchChallengeListener(
            final int i, final int i2, boolean z, final long j) {
        Iterator it = ((ArrayList) this.mChallengeListeners).iterator();
        while (it.hasNext()) {
            SemFpEnrollSessionMonitor semFpEnrollSessionMonitor =
                    (SemFpEnrollSessionMonitor) it.next();
            if (z) {
                Runnable runnable =
                        new Runnable() { // from class:
                                         // com.android.server.biometrics.sensors.fingerprint.SemFpCallbackDispatcher$$ExternalSyntheticLambda6
                            @Override // java.lang.Runnable
                            public final void run() {
                                SemFpCallbackDispatcher semFpCallbackDispatcher =
                                        SemFpCallbackDispatcher.this;
                                int i3 = i;
                                int i4 = i2;
                                long j2 = j;
                                semFpCallbackDispatcher.getClass();
                                Binder binder = new Binder();
                                FingerprintProvider fingerprintProvider =
                                        (FingerprintProvider) semFpCallbackDispatcher.mProvider;
                                fingerprintProvider.mHandler.post(
                                        new FingerprintProvider$$ExternalSyntheticLambda10(
                                                fingerprintProvider,
                                                i3,
                                                binder,
                                                i4,
                                                "EnrollSession",
                                                j2));
                            }
                        };
                synchronized (semFpEnrollSessionMonitor.mEnrollSessions) {
                    try {
                        semFpEnrollSessionMonitor.mEnrollSessions.put(
                                i, new Pair(runnable, Long.valueOf(j)));
                        if (semFpEnrollSessionMonitor.mEnrollSessions.size() == 1) {
                            semFpEnrollSessionMonitor.mDisplayStateMonitor.registerStateCallback(
                                    semFpEnrollSessionMonitor);
                        }
                    } finally {
                    }
                }
            } else {
                synchronized (semFpEnrollSessionMonitor.mEnrollSessions) {
                    try {
                        semFpEnrollSessionMonitor.mEnrollSessions.remove(i);
                        if (semFpEnrollSessionMonitor.mEnrollSessions.size() == 0) {
                            ((CopyOnWriteArrayList)
                                            semFpEnrollSessionMonitor
                                                    .mDisplayStateMonitor
                                                    .mDisplayStateCallbacks)
                                    .remove(semFpEnrollSessionMonitor);
                        }
                    } finally {
                    }
                }
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public final void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
        if (baseClientMonitor instanceof AuthenticationClient) {
            Iterator it = ((ArrayList) this.mAuthenticationListeners).iterator();
            while (it.hasNext()) {
                ((SemFpAuthenticationListener) it.next())
                        .onAuthenticationFinished(
                                baseClientMonitor.mSensorId, baseClientMonitor.mTargetUserId);
            }
        } else if (baseClientMonitor instanceof EnrollClient) {
            Iterator it2 = ((ArrayList) this.mEnrollListeners).iterator();
            while (it2.hasNext()) {
                ((SemFpEnrollmentListener) it2.next())
                        .onEnrollFinished(
                                baseClientMonitor.mSensorId, baseClientMonitor.mTargetUserId);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public final void onClientStarted(BaseClientMonitor baseClientMonitor) {
        if (baseClientMonitor instanceof AuthenticationClient) {
            Iterator it = ((ArrayList) this.mAuthenticationListeners).iterator();
            while (it.hasNext()) {
                ((SemFpAuthenticationListener) it.next())
                        .onAuthenticationStarted(
                                baseClientMonitor.mSensorId, baseClientMonitor.mTargetUserId);
            }
        } else if (baseClientMonitor instanceof EnrollClient) {
            Iterator it2 = ((ArrayList) this.mEnrollListeners).iterator();
            while (it2.hasNext()) {
                ((SemFpEnrollmentListener) it2.next())
                        .onEnrollStarted(
                                baseClientMonitor.mSensorId, baseClientMonitor.mTargetUserId);
            }
        }
    }
}
