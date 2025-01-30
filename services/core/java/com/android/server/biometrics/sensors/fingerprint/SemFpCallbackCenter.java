package com.android.server.biometrics.sensors.fingerprint;

import android.os.Binder;
import android.os.Handler;
import com.android.server.biometrics.sensors.AuthenticationClient;
import com.android.server.biometrics.sensors.BaseClientMonitor;
import com.android.server.biometrics.sensors.ClientMonitorCallback;
import com.android.server.biometrics.sensors.EnrollClient;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class SemFpCallbackCenter implements SemFpHalCallbackEx, ClientMonitorCallback {
    public final Handler mHandler;
    public final ServiceProvider mProvider;
    public final List mChallengeListeners = new ArrayList();
    public final List mEventListeners = new ArrayList();
    public final List mAuthenticationListeners = new ArrayList();
    public final List mEnrollListeners = new ArrayList();
    public final List mResetLockoutListeners = new ArrayList();

    public SemFpCallbackCenter(ServiceProvider serviceProvider, Handler handler) {
        this.mProvider = serviceProvider;
        this.mHandler = handler;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpHalCallbackEx
    public void onSpenEvent(int i, int i2) {
        Iterator it = this.mEventListeners.iterator();
        while (it.hasNext()) {
            ((SemFpEventListener) it.next()).onSpenEvent(i, i2);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpHalCallbackEx
    public void onGestureEvent(int i, int i2) {
        Iterator it = this.mEventListeners.iterator();
        while (it.hasNext()) {
            ((SemFpEventListener) it.next()).onGestureEvent(i, i2);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpHalCallbackEx
    public void onError(int i, int i2, int i3, int i4, int i5) {
        if (i3 == 3) {
            Iterator it = this.mAuthenticationListeners.iterator();
            while (it.hasNext()) {
                ((SemFpAuthenticationListener) it.next()).onAuthenticationError(i, i4, i5);
            }
        } else if (i3 == 2) {
            Iterator it2 = this.mEnrollListeners.iterator();
            while (it2.hasNext()) {
                ((SemFpEnrollmentListener) it2.next()).onEnrollError(i, i4, i5);
            }
        }
        Iterator it3 = this.mEventListeners.iterator();
        while (it3.hasNext()) {
            ((SemFpEventListener) it3.next()).onError(i, i2, i4, i5);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpHalCallbackEx
    public void onAcquire(int i, int i2, int i3, int i4, int i5) {
        if (i3 == 3) {
            Iterator it = this.mAuthenticationListeners.iterator();
            while (it.hasNext()) {
                ((SemFpAuthenticationListener) it.next()).onAuthenticationAcquire(i, i2, i4, i5);
            }
        } else if (i3 == 2) {
            Iterator it2 = this.mEnrollListeners.iterator();
            while (it2.hasNext()) {
                ((SemFpEnrollmentListener) it2.next()).onEnrollAcquire(i, i2, i4, i5);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpHalCallbackEx
    public void onEnrolled(int i, int i2) {
        Iterator it = this.mEnrollListeners.iterator();
        while (it.hasNext()) {
            ((SemFpEnrollmentListener) it.next()).onEnrollFinished(i, i2);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpHalCallbackEx
    public void onAuthenticated(int i, int i2, int i3) {
        Iterator it = this.mAuthenticationListeners.iterator();
        while (it.hasNext()) {
            ((SemFpAuthenticationListener) it.next()).onAuthenticationResult(i, i2, i3);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpHalCallbackEx
    public void onChallengeGenerated(int i, int i2, long j) {
        dispatchChallengeListener(i, i2, j, true);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpHalCallbackEx
    public void onChallengeRevoked(int i, int i2, long j) {
        dispatchChallengeListener(i, i2, j, false);
    }

    public final void dispatchChallengeListener(final int i, final int i2, final long j, boolean z) {
        for (SemFpChallengeListener semFpChallengeListener : this.mChallengeListeners) {
            if (z) {
                semFpChallengeListener.onChallengeGenerated(i, j, new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpCallbackCenter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemFpCallbackCenter.this.lambda$dispatchChallengeListener$0(i, i2, j);
                    }
                });
            } else {
                semFpChallengeListener.onChallengeRevoked(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchChallengeListener$0(int i, int i2, long j) {
        this.mProvider.scheduleRevokeChallenge(i, i2, new Binder(), "EnrollSession", j);
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public void onClientStarted(BaseClientMonitor baseClientMonitor) {
        if (baseClientMonitor instanceof AuthenticationClient) {
            Iterator it = this.mAuthenticationListeners.iterator();
            while (it.hasNext()) {
                ((SemFpAuthenticationListener) it.next()).onAuthenticationStarted(baseClientMonitor.getSensorId(), baseClientMonitor.getTargetUserId());
            }
        } else if (baseClientMonitor instanceof EnrollClient) {
            Iterator it2 = this.mEnrollListeners.iterator();
            while (it2.hasNext()) {
                ((SemFpEnrollmentListener) it2.next()).onEnrollStarted(baseClientMonitor.getSensorId(), baseClientMonitor.getTargetUserId());
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
    public void onClientFinished(BaseClientMonitor baseClientMonitor, boolean z) {
        if (baseClientMonitor instanceof AuthenticationClient) {
            Iterator it = this.mAuthenticationListeners.iterator();
            while (it.hasNext()) {
                ((SemFpAuthenticationListener) it.next()).onAuthenticationFinished(baseClientMonitor.getSensorId(), baseClientMonitor.getTargetUserId());
            }
        } else if (baseClientMonitor instanceof EnrollClient) {
            Iterator it2 = this.mEnrollListeners.iterator();
            while (it2.hasNext()) {
                ((SemFpEnrollmentListener) it2.next()).onEnrollFinished(baseClientMonitor.getSensorId(), baseClientMonitor.getTargetUserId());
            }
        }
    }

    public void onResetLockout(final int i, final int i2) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpCallbackCenter$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                SemFpCallbackCenter.this.lambda$onResetLockout$1(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResetLockout$1(int i, int i2) {
        Iterator it = this.mResetLockoutListeners.iterator();
        while (it.hasNext()) {
            ((SemFpResetLockoutListener) it.next()).onResetLockout(i, i2);
        }
    }

    public void addChallengeListener(final SemFpChallengeListener semFpChallengeListener) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpCallbackCenter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SemFpCallbackCenter.this.lambda$addChallengeListener$2(semFpChallengeListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addChallengeListener$2(SemFpChallengeListener semFpChallengeListener) {
        if (semFpChallengeListener == null || this.mChallengeListeners.contains(semFpChallengeListener)) {
            return;
        }
        this.mChallengeListeners.add(semFpChallengeListener);
    }

    public void addAuthenticationListener(final SemFpAuthenticationListener semFpAuthenticationListener) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpCallbackCenter$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                SemFpCallbackCenter.this.lambda$addAuthenticationListener$4(semFpAuthenticationListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addAuthenticationListener$4(SemFpAuthenticationListener semFpAuthenticationListener) {
        if (semFpAuthenticationListener == null || this.mAuthenticationListeners.contains(semFpAuthenticationListener)) {
            return;
        }
        this.mAuthenticationListeners.add(semFpAuthenticationListener);
    }

    public void addEnrollListener(final SemFpEnrollmentListener semFpEnrollmentListener) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpCallbackCenter$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SemFpCallbackCenter.this.lambda$addEnrollListener$6(semFpEnrollmentListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addEnrollListener$6(SemFpEnrollmentListener semFpEnrollmentListener) {
        if (semFpEnrollmentListener == null || this.mEnrollListeners.contains(semFpEnrollmentListener)) {
            return;
        }
        this.mEnrollListeners.add(semFpEnrollmentListener);
    }

    public void addEventListener(final SemFpEventListener semFpEventListener) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpCallbackCenter$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                SemFpCallbackCenter.this.lambda$addEventListener$8(semFpEventListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addEventListener$8(SemFpEventListener semFpEventListener) {
        if (semFpEventListener == null || this.mEventListeners.contains(semFpEventListener)) {
            return;
        }
        this.mEventListeners.add(semFpEventListener);
    }

    public void removeEventListener(final SemFpEventListener semFpEventListener) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpCallbackCenter$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                SemFpCallbackCenter.this.lambda$removeEventListener$9(semFpEventListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeEventListener$9(SemFpEventListener semFpEventListener) {
        if (semFpEventListener != null) {
            this.mEventListeners.remove(semFpEventListener);
        }
    }

    public void addResetLockoutListener(final SemFpResetLockoutListener semFpResetLockoutListener) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpCallbackCenter$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                SemFpCallbackCenter.this.lambda$addResetLockoutListener$10(semFpResetLockoutListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addResetLockoutListener$10(SemFpResetLockoutListener semFpResetLockoutListener) {
        if (semFpResetLockoutListener == null || this.mResetLockoutListeners.contains(semFpResetLockoutListener)) {
            return;
        }
        this.mResetLockoutListeners.add(semFpResetLockoutListener);
    }
}
