package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.biometrics.common.OperationContext;
import android.hardware.biometrics.fingerprint.ISession;
import android.hardware.biometrics.fingerprint.ISessionCallback;
import android.hardware.biometrics.fingerprint.PointerContext;
import android.hardware.keymaster.HardwareAuthToken;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseLongArray;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticLambda1;
import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.sensors.SemTestHalHelper;

import com.att.iqi.lib.metrics.mm.MM05;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class SemTpaTestHal extends TestHal implements SemTestHalHelper.Callback {
    public CountDownLatch mActionDelayLatch;
    public long mChallenge;
    public final Context mContext;
    public int mCurrentEnrollmentId;
    public ISessionCallback mISessionCallback;
    ServiceThread mThread;
    public CountDownLatch mTspDelayLatch;
    public SemTestHalHelper mTestHalHelper = new SemTestHalHelper(2, this);
    final Set mEnrolledIds = new HashSet();
    public final SparseLongArray mAuthenticatorID = new SparseLongArray();
    public final SemTestSehFingerprint mSehFingerprint = new SemTestSehFingerprint();

    /* renamed from: com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$1, reason: invalid class name */
    public final class AnonymousClass1 extends ISession.Stub {
        public final /* synthetic */ Handler val$handler;
        public final /* synthetic */ ISessionCallback val$sessionCallback;
        public final /* synthetic */ int val$userId;

        public AnonymousClass1(Handler handler, ISessionCallback iSessionCallback, int i) {
            this.val$handler = handler;
            this.val$sessionCallback = iSessionCallback;
            this.val$userId = i;
        }

        public final ICancellationSignal authenticate(final long j) {
            Slog.w("fingerprint.aidl.SemTpaTestHal", "authenticate, " + j);
            final CancellationSignal cancellationSignal = new CancellationSignal();
            cancellationSignal.setOnCancelListener(
                    new SemTpaTestHal$1$$ExternalSyntheticLambda3(
                            this, this.val$handler, this.val$sessionCallback, 1));
            long j2 = SemTpaTestHal.this.mTestHalHelper.mDelayAuthAction;
            Handler handler = this.val$handler;
            Runnable runnable =
                    new Runnable() { // from class:
                                     // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$1$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            SemTpaTestHal.AnonymousClass1 anonymousClass1 =
                                    SemTpaTestHal.AnonymousClass1.this;
                            long j3 = j;
                            CancellationSignal cancellationSignal2 = cancellationSignal;
                            if (SemTpaTestHal.this.mEnrolledIds.isEmpty()) {
                                SemTpaTestHal.this.deliverErrorEvent(8, 1004);
                                return;
                            }
                            List list = SemTpaTestHal.this.mTestHalHelper.mAuthActionList;
                            StringBuilder m =
                                    BatteryService$$ExternalSyntheticOutline0.m(
                                            "start authenticateTPA: ", j3, ", action size = ");
                            ArrayList arrayList = (ArrayList) list;
                            m.append(arrayList.size());
                            Slog.d("fingerprint.aidl.SemTpaTestHal", m.toString());
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                SemTestHalHelper.Action action =
                                        (SemTestHalHelper.Action) it.next();
                                if (cancellationSignal2.isCanceled()) {
                                    return;
                                }
                                SemTpaTestHal.this.mActionDelayLatch = new CountDownLatch(1);
                                try {
                                    SemTpaTestHal.this.mActionDelayLatch.await(
                                            action.delay, TimeUnit.MILLISECONDS);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                action.run();
                                if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL
                                        && action.callbackType
                                                == SemTestHalHelper.CallbackType.TSP_FOD
                                        && action.value == 2) {
                                    SemTpaTestHal.this.mTspDelayLatch = new CountDownLatch(1);
                                    try {
                                        SemTpaTestHal.this.mTspDelayLatch.await(
                                                10000L, TimeUnit.MICROSECONDS);
                                    } catch (InterruptedException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                            }
                        }
                    };
            if (j2 == 0) {
                j2 = 600;
            }
            handler.postDelayed(runnable, j2);
            SemTpaTestHal.this.getClass();
            return new AnonymousClass2(cancellationSignal);
        }

        public final ICancellationSignal authenticateWithContext(
                long j, OperationContext operationContext) {
            return authenticate(j);
        }

        public final void close() {
            Slog.w("fingerprint.aidl.SemTpaTestHal", "close");
            this.val$sessionCallback.onSessionClosed();
        }

        public final ICancellationSignal detectInteraction() {
            Slog.w("fingerprint.aidl.SemTpaTestHal", "detectInteraction");
            CancellationSignal cancellationSignal = new CancellationSignal();
            final Handler handler = this.val$handler;
            final ISessionCallback iSessionCallback = this.val$sessionCallback;
            cancellationSignal.setOnCancelListener(
                    new CancellationSignal
                            .OnCancelListener() { // from class:
                                                  // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$1$$ExternalSyntheticLambda1
                        @Override // android.os.CancellationSignal.OnCancelListener
                        public final void onCancel() {
                            handler.post(
                                    new SemTpaTestHal$$ExternalSyntheticLambda0(
                                            iSessionCallback, 2));
                        }
                    });
            this.val$handler.postDelayed(
                    new SemTpaTestHal$1$$ExternalSyntheticLambda2(
                            this, this.val$sessionCallback, 0),
                    600L);
            SemTpaTestHal.this.getClass();
            return new AnonymousClass2(cancellationSignal);
        }

        public final ICancellationSignal detectInteractionWithContext(
                OperationContext operationContext) {
            return detectInteraction();
        }

        public final ICancellationSignal enroll(HardwareAuthToken hardwareAuthToken) {
            Slog.w("fingerprint.aidl.SemTpaTestHal", "enroll: " + hardwareAuthToken);
            final CancellationSignal cancellationSignal = new CancellationSignal();
            cancellationSignal.setOnCancelListener(
                    new SemTpaTestHal$1$$ExternalSyntheticLambda3(
                            this, this.val$handler, this.val$sessionCallback, 0));
            Handler handler = this.val$handler;
            final int i = this.val$userId;
            handler.postDelayed(
                    new Runnable() { // from class:
                                     // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$1$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            SemTpaTestHal.AnonymousClass1 anonymousClass1 =
                                    SemTpaTestHal.AnonymousClass1.this;
                            CancellationSignal cancellationSignal2 = cancellationSignal;
                            int i2 = i;
                            anonymousClass1.getClass();
                            Random random = new Random();
                            int nextInt = random.nextInt();
                            while (SemTpaTestHal.this.mEnrolledIds.contains(
                                    Integer.valueOf(nextInt))) {
                                nextInt = random.nextInt();
                            }
                            SemTpaTestHal semTpaTestHal = SemTpaTestHal.this;
                            semTpaTestHal.mCurrentEnrollmentId = nextInt;
                            List list = semTpaTestHal.mTestHalHelper.mEnrollActionList;
                            StringBuilder sb = new StringBuilder("start enrollTPA: ");
                            sb.append(SemTpaTestHal.this.mCurrentEnrollmentId);
                            sb.append(", action size = ");
                            ArrayList arrayList = (ArrayList) list;
                            sb.append(arrayList.size());
                            Slog.d("fingerprint.aidl.SemTpaTestHal", sb.toString());
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                SemTestHalHelper.Action action =
                                        (SemTestHalHelper.Action) it.next();
                                if (cancellationSignal2.isCanceled()) {
                                    return;
                                }
                                SemTpaTestHal.this.mActionDelayLatch = new CountDownLatch(1);
                                try {
                                    SemTpaTestHal.this.mActionDelayLatch.await(
                                            action.delay, TimeUnit.MILLISECONDS);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                action.run();
                                if (action.callbackType
                                                == SemTestHalHelper.CallbackType.ENROLL_RESULT
                                        && action.value == 0) {
                                    SemTpaTestHal semTpaTestHal2 = SemTpaTestHal.this;
                                    semTpaTestHal2.mEnrolledIds.add(
                                            Integer.valueOf(semTpaTestHal2.mCurrentEnrollmentId));
                                    SemTpaTestHal semTpaTestHal3 = SemTpaTestHal.this;
                                    semTpaTestHal3.mCurrentEnrollmentId = 0;
                                    semTpaTestHal3.mAuthenticatorID.put(i2, random.nextLong());
                                }
                            }
                        }
                    },
                    600L);
            SemTpaTestHal.this.getClass();
            return new AnonymousClass2(cancellationSignal);
        }

        public final ICancellationSignal enrollWithContext(
                HardwareAuthToken hardwareAuthToken, OperationContext operationContext) {
            return enroll(hardwareAuthToken);
        }

        public final void enumerateEnrollments() {
            Slog.w(
                    "fingerprint.aidl.SemTpaTestHal",
                    "enumerateEnrollments: " + SemTpaTestHal.this.mEnrolledIds.size());
            this.val$sessionCallback.onEnrollmentsEnumerated(
                    SemTpaTestHal.this.mEnrolledIds.stream()
                            .mapToInt(new AudioService$$ExternalSyntheticLambda1(2))
                            .toArray());
        }

        public final void generateChallenge() {
            Slog.w("fingerprint.aidl.SemTpaTestHal", "generateChallenge");
            SemTpaTestHal.this.mChallenge = new Random().nextLong();
            this.val$handler.post(
                    new SemTpaTestHal$1$$ExternalSyntheticLambda2(
                            this, this.val$sessionCallback, 1));
        }

        public final void getAuthenticatorId() {
            Slog.w("fingerprint.aidl.SemTpaTestHal", "getAuthenticatorId");
            this.val$sessionCallback.onAuthenticatorIdRetrieved(
                    SemTpaTestHal.this.mAuthenticatorID.get(this.val$userId, 0L));
        }

        public final String getInterfaceHash() {
            return "41a730a7a6b5aa9cebebce70ee5b5e509b0af6fb";
        }

        public final int getInterfaceVersion() {
            return 4;
        }

        public final void invalidateAuthenticatorId() {
            Slog.w("fingerprint.aidl.SemTpaTestHal", "invalidateAuthenticatorId");
            SemTpaTestHal.this.mAuthenticatorID.put(this.val$userId, new Random().nextLong());
            this.val$sessionCallback.onAuthenticatorIdInvalidated(
                    SemTpaTestHal.this.mAuthenticatorID.get(this.val$userId, 0L));
        }

        public final void onContextChanged(OperationContext operationContext) {
            Slog.w("fingerprint.aidl.SemTpaTestHal", "onContextChanged");
        }

        public final void onPointerCancelWithContext(PointerContext pointerContext) {
            Slog.w("fingerprint.aidl.SemTpaTestHal", "onPointerCancelWithContext");
        }

        public final void onPointerDown(int i, int i2, int i3, float f, float f2) {
            Slog.w("fingerprint.aidl.SemTpaTestHal", "onPointerDown");
        }

        public final void onPointerDownWithContext(PointerContext pointerContext) {
            onPointerDown(
                    pointerContext.pointerId,
                    (int) pointerContext.x,
                    (int) pointerContext.y,
                    pointerContext.minor,
                    pointerContext.major);
        }

        public final void onPointerUp(int i) {
            Slog.w("fingerprint.aidl.SemTpaTestHal", "onPointerUp");
        }

        public final void onPointerUpWithContext(PointerContext pointerContext) {
            onPointerUp(pointerContext.pointerId);
        }

        public final void onUiReady() {
            Slog.w("fingerprint.aidl.SemTpaTestHal", "onUiReady");
        }

        public final void removeEnrollments(int[] iArr) {
            Slog.w("fingerprint.aidl.SemTpaTestHal", "removeEnrollments");
            for (int i : iArr) {
                SemTpaTestHal.this.mEnrolledIds.remove(Integer.valueOf(i));
            }
            this.val$sessionCallback.onEnrollmentsRemoved(iArr);
        }

        public final void resetLockout(HardwareAuthToken hardwareAuthToken) {
            Slog.w("fingerprint.aidl.SemTpaTestHal", "resetLockout");
            this.val$sessionCallback.onLockoutCleared();
        }

        public final void revokeChallenge(final long j) {
            Slog.w("fingerprint.aidl.SemTpaTestHal", "revokeChallenge: " + j);
            Handler handler = this.val$handler;
            final ISessionCallback iSessionCallback = this.val$sessionCallback;
            handler.post(
                    new Runnable() { // from class:
                                     // com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$1$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            try {
                                iSessionCallback.onChallengeRevoked(j);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }

        public final void setIgnoreDisplayTouches(boolean z) {
            Slog.w("fingerprint.aidl.SemTpaTestHal", "setIgnoreDisplayTouches");
        }
    }

    /* renamed from: com.android.server.biometrics.sensors.fingerprint.aidl.SemTpaTestHal$2, reason: invalid class name */
    public final class AnonymousClass2 extends ICancellationSignal.Stub {
        public final /* synthetic */ CancellationSignal val$cs;

        public AnonymousClass2(CancellationSignal cancellationSignal) {
            this.val$cs = cancellationSignal;
        }

        public final void cancel() {
            this.val$cs.cancel();
        }

        public final String getInterfaceHash() {
            return "8a6cd86630181a4df6f20056259ec200ffe39209";
        }

        public final int getInterfaceVersion() {
            return 4;
        }
    }

    public SemTpaTestHal(Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.aidl.TestHal
    public final ISession createSession(int i, int i2, ISessionCallback iSessionCallback) {
        PendingIntentController$$ExternalSyntheticOutline0.m(
                i, i2, "createSession, sensorId: ", " userId: ", "fingerprint.aidl.SemTpaTestHal");
        if (this.mThread == null) {
            this.mTestHalHelper.initActions();
            SemTestSehFingerprint semTestSehFingerprint = this.mSehFingerprint;
            semTestSehFingerprint.mRequestActionTable.clear();
            semTestSehFingerprint.mRequestActionTable.put(6, 100040);
            ServiceThread serviceThread = this.mThread;
            if (serviceThread != null) {
                serviceThread.quitSafely();
                this.mThread = null;
            }
            ServiceThread serviceThread2 =
                    new ServiceThread(-2, "fingerprint.aidl.SemTpaTestHal", true);
            this.mThread = serviceThread2;
            serviceThread2.start();
        }
        this.mISessionCallback = iSessionCallback;
        return new AnonymousClass1(this.mThread.getThreadHandler(), iSessionCallback, i2);
    }

    @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
    public final void deliverAcquiredEvent(int i, int i2) {
        try {
            ISessionCallback iSessionCallback = this.mISessionCallback;
            byte b = 8;
            if (i == 8) {
                b = 0;
            } else {
                byte b2 = 1;
                if (i != 0) {
                    byte b3 = 2;
                    if (i != 1) {
                        b2 = 3;
                        if (i != 2) {
                            b3 = 4;
                            if (i != 3) {
                                b2 = 5;
                                if (i != 4) {
                                    b3 = 6;
                                    if (i != 5) {
                                        b2 = 7;
                                        if (i != 6) {
                                            if (i != 7) {
                                                b2 = 10;
                                                if (i != 10) {
                                                    if (i == 9) {
                                                        b = MM05.IQ_SIP_CALL_STATE_DISCONNECTING;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    b = b3;
                }
                b = b2;
            }
            iSessionCallback.onAcquired(b, i2);
        } catch (RemoteException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
    public final void deliverAuthenticationResult(int i) {
        if (this.mEnrolledIds.isEmpty()) {
            deliverErrorEvent(8, 1004);
            return;
        }
        try {
            if (i != 0) {
                this.mISessionCallback.onAuthenticationSucceeded(
                        ((Integer) this.mEnrolledIds.iterator().next()).intValue(),
                        HardwareAuthTokenUtils.toHardwareAuthToken(new byte[69]));
            } else {
                this.mISessionCallback.onAuthenticationFailed();
            }
        } catch (RemoteException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
    public final void deliverEnrollResult(int i) {
        try {
            this.mISessionCallback.onEnrollmentProgress(this.mCurrentEnrollmentId, i);
        } catch (RemoteException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
    public final void deliverErrorEvent(int i, int i2) {
        try {
            ISessionCallback iSessionCallback = this.mISessionCallback;
            byte b = 0;
            if (i != 17) {
                byte b2 = 1;
                if (i != 1) {
                    b2 = 2;
                    if (i != 2) {
                        b2 = 3;
                        if (i != 3) {
                            b2 = 4;
                            if (i != 4) {
                                b2 = 5;
                                if (i != 5) {
                                    b2 = 6;
                                    if (i != 6) {
                                        b2 = 8;
                                        if (i == 8) {
                                            b = 7;
                                        } else if (i == 18) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                b = b2;
            }
            iSessionCallback.onError(b, i2);
        } catch (RemoteException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
    public final void deliverTspEvent(int i) {
        Intent intent = new Intent("com.samsung.android.fingerprint.action.FINGER_ON_DISPLAY");
        if (i == 2) {
            intent.putExtra("info", 15);
        } else if (i == 1) {
            intent.putExtra("info", 16);
        }
        try {
            this.mContext.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void injectTestHalHelper(SemTestHalHelper semTestHalHelper) {
        this.mTestHalHelper = semTestHalHelper;
    }
}
