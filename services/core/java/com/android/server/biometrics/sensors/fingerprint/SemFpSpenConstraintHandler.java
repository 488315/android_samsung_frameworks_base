package com.android.server.biometrics.sensors.fingerprint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Handler;
import android.util.Pair;
import android.util.Slog;

import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider;

import com.samsung.android.content.smartclip.SpenGestureManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemFpSpenConstraintHandler
        implements SemFpEventListener, SemFpEnrollmentListener, SemFpAuthenticationListener {
    static final String ACTION_FOD_UPDATE =
            "com.samsung.android.fingerprint.action.FINGER_ON_DISPLAY";
    static final int EXTRA_FOD_UPDATE_TSP_BLOCK = 226;
    static final int EXTRA_FOD_UPDATE_TSP_UNBLOCK = 225;
    static final String KEY_FOD_EXTRA_INFO = "info";
    public final Context mContext;
    public final Handler mH;
    public boolean mIsTspBlocked;
    public final Pair mProvider;
    public final SemFpSpenConstraintHandler$$ExternalSyntheticLambda0
            mRunnableHandleTspBlockAction =
                    new Runnable() { // from class:
                                     // com.android.server.biometrics.sensors.fingerprint.SemFpSpenConstraintHandler$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SemFpSpenConstraintHandler semFpSpenConstraintHandler =
                                    SemFpSpenConstraintHandler.this;
                            semFpSpenConstraintHandler.getClass();
                            if (Utils.DEBUG) {
                                HeimdAllFsService$$ExternalSyntheticOutline0.m(
                                        "SemFpSpenConstraintHandler",
                                        new StringBuilder("handleTspBlockAction: "),
                                        semFpSpenConstraintHandler.mIsTspBlocked);
                            }
                            semFpSpenConstraintHandler.notifyTspBlockStatusToHal(
                                    ((Integer) semFpSpenConstraintHandler.mProvider.first)
                                            .intValue());
                            ((FingerprintProvider)
                                            ((ServiceProvider)
                                                    semFpSpenConstraintHandler.mProvider.second))
                                    .semNotifyTspBlockStateToClient(
                                            semFpSpenConstraintHandler.mIsTspBlocked);
                        }
                    };
    public SpenGestureManager mSpenGestureManager;
    BroadcastReceiver mTspBrReceiver;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.biometrics.sensors.fingerprint.SemFpSpenConstraintHandler$$ExternalSyntheticLambda0] */
    public SemFpSpenConstraintHandler(Context context, Pair pair, Handler handler) {
        this.mContext = context;
        this.mProvider = pair;
        this.mH = handler;
    }

    public void handleTspBlockAction(int i) {
        Handler handler = this.mH;
        SemFpSpenConstraintHandler$$ExternalSyntheticLambda0
                semFpSpenConstraintHandler$$ExternalSyntheticLambda0 =
                        this.mRunnableHandleTspBlockAction;
        handler.removeCallbacks(semFpSpenConstraintHandler$$ExternalSyntheticLambda0);
        handler.postDelayed(semFpSpenConstraintHandler$$ExternalSyntheticLambda0, i);
    }

    public final void notifyTspBlockStatusToHal(int i) {
        ((FingerprintProvider) ((ServiceProvider) this.mProvider.second))
                .semRequest(i, 35, this.mIsTspBlocked ? 1 : 0, null, null);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener
    public final void onAuthenticationAcquire(int i, int i2, int i3) {
        if (i2 == 6 && i3 == 10001 && this.mIsTspBlocked) {
            handleTspBlockAction(0);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener
    public final void onAuthenticationStarted(int i, int i2) {
        this.mH.postDelayed(
                new SemFpSpenConstraintHandler$$ExternalSyntheticLambda1(this, i, 1), 10L);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpEnrollmentListener
    public final void onEnrollAcquire(int i, int i2) {
        if (i == 6 && i2 == 10001 && this.mIsTspBlocked) {
            handleTspBlockAction(0);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpEnrollmentListener
    public final void onEnrollStarted(int i, int i2) {
        this.mH.postDelayed(
                new SemFpSpenConstraintHandler$$ExternalSyntheticLambda1(this, i, 0), 10L);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpEventListener
    public final void onSpenEvent(int i) {
        if (this.mSpenGestureManager == null) {
            this.mSpenGestureManager =
                    (SpenGestureManager) this.mContext.getSystemService("spengestureservice");
        }
        SpenGestureManager spenGestureManager = this.mSpenGestureManager;
        if (spenGestureManager == null) {
            return;
        }
        try {
            spenGestureManager.notifyBleSpenChargeLockState(i == 30002);
        } catch (RuntimeException e) {
            Slog.w("SemFpSpenConstraintHandler", "onSpenEvent: " + e.getMessage());
        }
    }

    public void setTspBlockState(boolean z) {
        this.mIsTspBlocked = z;
    }
}
