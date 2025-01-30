package com.android.server.biometrics.sensors.fingerprint.hidl;

import android.content.Context;
import android.content.Intent;
import android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import com.android.server.ServiceThread;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.SemTestHalHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import vendor.samsung.hardware.biometrics.fingerprint.V3_0.ISehBiometricsFingerprint;

/* loaded from: classes.dex */
public class SehTestHal extends ISehBiometricsFingerprint.Stub {
    public final SparseLongArray mAuthenticatorID;
    public IBiometricsFingerprintClientCallback mCallback;
    public final Context mContext;
    public CountDownLatch mCountDownLatch;
    public int mCurrentEnrollmentId;
    public int mCurrentUserID;
    public final long mDeviceId;
    public final Set mEnrollmentIds;

    /* renamed from: mH */
    public Handler f1664mH;
    public final boolean mIsTpaMode;
    public final Random mRandom;
    public final SparseIntArray mRequestActionTable;
    public long mSessionId;
    public final TestHal mTestHal;
    public SemTestHalHelper mTestHalHelper;
    public ServiceThread mThread;
    public final AtomicBoolean mIsCancellation = new AtomicBoolean(false);
    public final ArrayList mSehRequestOutput = new ArrayList();

    public SehTestHal(Context context, int i, boolean z) {
        this.mContext = context;
        this.mIsTpaMode = z;
        this.mTestHal = new TestHal(context, i);
        Random random = new Random();
        this.mRandom = random;
        this.mAuthenticatorID = new SparseLongArray();
        this.mDeviceId = random.nextLong();
        this.mRequestActionTable = new SparseIntArray();
        this.mEnrollmentIds = new HashSet();
        if (z) {
            this.mTestHalHelper = new SemTestHalHelper(2, new SemTestHalHelper.Callback() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.SehTestHal.1
                @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
                public void deliverAcquiredEvent(int i2, int i3) {
                    SehTestHal.this.deliverAcquiredEvent(i2, i3);
                }

                @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
                public void deliverErrorEvent(int i2, int i3) {
                    SehTestHal.this.deliverErrorEvent(i2, i3);
                }

                @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
                public void deliverEnrollResult(int i2) {
                    SehTestHal.this.deliverEnrollResult(i2);
                }

                @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
                public void deliverAuthenticationResult(int i2) {
                    SehTestHal.this.deliverAuthenticationResult(i2);
                }

                @Override // com.android.server.biometrics.sensors.SemTestHalHelper.Callback
                public void deliverTspEvent(int i2) {
                    SehTestHal.this.deliverTspEvent(i2);
                }
            });
            ServiceThread serviceThread = new ServiceThread("fingerprint.hidl.SehTestHal", -2, true);
            this.mThread = serviceThread;
            serviceThread.start();
            Handler threadHandler = this.mThread.getThreadHandler();
            this.f1664mH = threadHandler;
            threadHandler.post(new SehTestHal$$ExternalSyntheticLambda1(this));
        } else {
            initRequestTable();
        }
        if (Utils.DEBUG) {
            Slog.d("fingerprint.hidl.SehTestHal", "SehTestHal: constructed, " + i + ", " + z);
        }
    }

    public void destroy() {
        ServiceThread serviceThread = this.mThread;
        if (serviceThread != null) {
            serviceThread.quitSafely();
        }
        this.mThread = null;
    }

    public void deliverAcquiredEvent(int i, int i2) {
        if (this.mIsTpaMode) {
            try {
                this.mCallback.onAcquired(this.mDeviceId, i, i2);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void deliverErrorEvent(int i, int i2) {
        if (this.mIsTpaMode) {
            try {
                this.mCallback.onError(this.mDeviceId, i, i2);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void deliverEnrollResult(int i) {
        if (this.mIsTpaMode) {
            try {
                this.mCallback.onEnrollResult(this.mDeviceId, this.mCurrentEnrollmentId, this.mCurrentUserID, i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deliverAuthenticationResult(int i) {
        if (this.mIsTpaMode) {
            try {
                if (this.mEnrollmentIds.isEmpty()) {
                    deliverErrorEvent(8, 1004);
                    return;
                }
                if (i != 0) {
                    i = ((Integer) this.mEnrollmentIds.iterator().next()).intValue();
                }
                this.mCallback.onAuthenticated(this.mDeviceId, i, this.mCurrentUserID, new ArrayList(Collections.nCopies(69, (byte) 0)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deliverTspEvent(int i) {
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

    @Override // vendor.samsung.hardware.biometrics.fingerprint.V3_0.ISehBiometricsFingerprint
    public void sehRequest(int i, int i2, ArrayList arrayList, ISehBiometricsFingerprint.sehRequestCallback sehrequestcallback) {
        CountDownLatch countDownLatch;
        Slog.w("fingerprint.hidl.SehTestHal", "sehRequest: " + i + ", " + i2);
        sehrequestcallback.onValues(this.mRequestActionTable.get(i, 0), this.mSehRequestOutput);
        this.mSehRequestOutput.clear();
        if (i == 22 && i2 == 2 && (countDownLatch = this.mCountDownLatch) != null) {
            countDownLatch.countDown();
        }
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public long setNotify(IBiometricsFingerprintClientCallback iBiometricsFingerprintClientCallback) {
        this.mCallback = iBiometricsFingerprintClientCallback;
        return this.mTestHal.setNotify(iBiometricsFingerprintClientCallback);
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public long preEnroll() {
        if (this.mIsTpaMode) {
            long nextLong = this.mRandom.nextLong();
            this.mSessionId = nextLong;
            return nextLong;
        }
        return this.mTestHal.preEnroll();
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public int enroll(byte[] bArr, int i, int i2) {
        if (this.mIsTpaMode) {
            return enrollTPA(bArr, i, i2);
        }
        return this.mTestHal.enroll(bArr, i, i2);
    }

    public final int enrollTPA(byte[] bArr, int i, int i2) {
        Slog.d("fingerprint.hidl.SehTestHal", "enrollTPA: " + i + ", " + this.mCurrentUserID);
        if (this.mCurrentUserID != i) {
            return -1;
        }
        this.f1664mH.postDelayed(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.SehTestHal$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SehTestHal.this.lambda$enrollTPA$0();
            }
        }, 600L);
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enrollTPA$0() {
        int nextInt = this.mRandom.nextInt();
        while (this.mEnrollmentIds.contains(Integer.valueOf(nextInt))) {
            nextInt = this.mRandom.nextInt();
        }
        this.mCurrentEnrollmentId = nextInt;
        List<SemTestHalHelper.Action> enrollActionList = this.mTestHalHelper.getEnrollActionList();
        Slog.d("fingerprint.hidl.SehTestHal", "start enrollTPA: " + this.mCurrentEnrollmentId + ", action size = " + enrollActionList.size());
        for (SemTestHalHelper.Action action : enrollActionList) {
            if (this.mIsCancellation.get()) {
                return;
            }
            try {
                Thread.sleep(action.getDelay());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            action.run();
            if (action.isFinishEnroll()) {
                this.mEnrollmentIds.add(Integer.valueOf(this.mCurrentEnrollmentId));
                this.mCurrentEnrollmentId = 0;
                this.mAuthenticatorID.put(this.mCurrentUserID, this.mRandom.nextLong());
            }
        }
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public int postEnroll() {
        if (this.mIsTpaMode) {
            this.mSessionId = 0L;
            return 0;
        }
        return this.mTestHal.postEnroll();
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public long getAuthenticatorId() {
        if (this.mIsTpaMode) {
            if (this.mEnrollmentIds.isEmpty()) {
                return 0L;
            }
            return this.mAuthenticatorID.get(this.mCurrentUserID);
        }
        return this.mTestHal.getAuthenticatorId();
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public int cancel() {
        if (this.mIsTpaMode) {
            this.mIsCancellation.set(true);
            this.f1664mH.post(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.SehTestHal$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    SehTestHal.this.lambda$cancel$1();
                }
            });
            return 0;
        }
        return this.mTestHal.cancel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancel$1() {
        try {
            this.mTestHal.cancel();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.mIsCancellation.set(false);
        this.mCurrentEnrollmentId = 0;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public int enumerate() {
        return this.mTestHal.enumerate();
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public int remove(int i, int i2) {
        return this.mTestHal.remove(i, i2);
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public int setActiveGroup(int i, String str) {
        this.mCurrentUserID = i;
        return this.mTestHal.setActiveGroup(i, str);
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public int authenticate(long j, int i) {
        if (this.mIsTpaMode) {
            return authenticateTPA(j, i);
        }
        return this.mTestHal.authenticate(j, i);
    }

    public final int authenticateTPA(long j, final int i) {
        Slog.d("fingerprint.hidl.SehTestHal", "authenticateTPA: " + i + ", " + this.mCurrentUserID);
        if (this.mCurrentUserID != i) {
            return -1;
        }
        long delayAuthAction = this.mTestHalHelper.getDelayAuthAction();
        Handler handler = this.f1664mH;
        Runnable runnable = new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.SehTestHal$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SehTestHal.this.lambda$authenticateTPA$2(i);
            }
        };
        if (delayAuthAction == 0) {
            delayAuthAction = 600;
        }
        handler.postDelayed(runnable, delayAuthAction);
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$authenticateTPA$2(int i) {
        if (this.mEnrollmentIds.isEmpty()) {
            deliverErrorEvent(8, 1004);
            return;
        }
        List<SemTestHalHelper.Action> authActionList = this.mTestHalHelper.getAuthActionList();
        Slog.d("fingerprint.hidl.SehTestHal", "start authenticateTPA: " + i + ", action size = " + authActionList.size());
        for (SemTestHalHelper.Action action : authActionList) {
            if (this.mIsCancellation.get()) {
                return;
            }
            try {
                Thread.sleep(action.getDelay());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            action.run();
            if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL && action.getCallbackType() == SemTestHalHelper.CallbackType.TSP_FOD && action.getValue() == 2) {
                CountDownLatch countDownLatch = new CountDownLatch(1);
                this.mCountDownLatch = countDownLatch;
                try {
                    countDownLatch.await(10000L, TimeUnit.MICROSECONDS);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public void postUpdateAction() {
        this.f1664mH.post(new SehTestHal$$ExternalSyntheticLambda1(this));
    }

    public final void initActions() {
        initRequestTable();
        SemTestHalHelper semTestHalHelper = this.mTestHalHelper;
        if (semTestHalHelper != null) {
            semTestHalHelper.initActions();
        }
    }

    public final void initRequestTable() {
        this.mRequestActionTable.clear();
        this.mRequestActionTable.put(6, 100040);
    }
}
