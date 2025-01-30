package com.android.server.biometrics.sensors.fingerprint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.samsung.android.content.smartclip.SpenGestureManager;

/* loaded from: classes.dex */
public class SemFpSpenConstraintHandler implements SemFpEventListener, SemFpEnrollmentListener, SemFpAuthenticationListener {
    static final String ACTION_FOD_UPDATE = "com.samsung.android.fingerprint.action.FINGER_ON_DISPLAY";
    static final int EXTRA_FOD_UPDATE_TSP_BLOCK = 226;
    static final int EXTRA_FOD_UPDATE_TSP_UNBLOCK = 225;
    static final String KEY_FOD_EXTRA_INFO = "info";
    public final Context mContext;
    public boolean mIsTspBlocked;
    public final Pair mProvider;
    public SpenGestureManager mSpenGestureManager;
    BroadcastReceiver mTspBrReceiver;

    /* renamed from: mH */
    public final Handler f1659mH = SemFpMainThread.get().getHandler();
    public final Runnable mRunnableHandleTspBlockAction = new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpSpenConstraintHandler$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            SemFpSpenConstraintHandler.this.lambda$new$0();
        }
    };

    public SemFpSpenConstraintHandler(Context context, Pair pair) {
        this.mContext = context;
        this.mProvider = pair;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        if (Utils.DEBUG) {
            Slog.i("SemFpSpenConstraintHandler", "handleTspBlockAction: " + this.mIsTspBlocked);
        }
        lambda$onEnrollStarted$1(((Integer) this.mProvider.first).intValue());
        ((ServiceProvider) this.mProvider.second).semNotifyTspBlockStateToClient(this.mIsTspBlocked);
    }

    public void start() {
        ((ServiceProvider) this.mProvider.second).semAddEventListener(this);
        ((ServiceProvider) this.mProvider.second).semAddAuthenticationListener(this);
        ((ServiceProvider) this.mProvider.second).semAddEnrollmentListener(this);
        if (SemBiometricFeature.FP_FEATURE_TSP_BLOCK) {
            startObserveTspBlockEvent();
        }
    }

    public final void startObserveTspBlockEvent() {
        if (this.mTspBrReceiver != null) {
            return;
        }
        this.mTspBrReceiver = new BroadcastReceiver() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpSpenConstraintHandler.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (SemFpSpenConstraintHandler.ACTION_FOD_UPDATE.equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra(SemFpSpenConstraintHandler.KEY_FOD_EXTRA_INFO, -1);
                    if (intExtra == SemFpSpenConstraintHandler.EXTRA_FOD_UPDATE_TSP_BLOCK && !SemFpSpenConstraintHandler.this.mIsTspBlocked) {
                        SemFpSpenConstraintHandler.this.mIsTspBlocked = true;
                        SemFpSpenConstraintHandler.this.handleTspBlockAction(FrameworkStatsLog.f446x58c6c32);
                    } else if (intExtra == SemFpSpenConstraintHandler.EXTRA_FOD_UPDATE_TSP_UNBLOCK && SemFpSpenConstraintHandler.this.mIsTspBlocked) {
                        SemFpSpenConstraintHandler.this.mIsTspBlocked = false;
                        SemFpSpenConstraintHandler.this.handleTspBlockAction(FrameworkStatsLog.f446x58c6c32);
                    }
                }
            }
        };
        Utils.registerBroadcastAsUser(this.mContext, this.mTspBrReceiver, new IntentFilter(ACTION_FOD_UPDATE), UserHandle.ALL, this.f1659mH);
    }

    public void handleTspBlockAction(int i) {
        this.f1659mH.removeCallbacks(this.mRunnableHandleTspBlockAction);
        this.f1659mH.postDelayed(this.mRunnableHandleTspBlockAction, i);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpEventListener
    public void onSpenEvent(int i, int i2) {
        if (this.mSpenGestureManager == null) {
            this.mSpenGestureManager = (SpenGestureManager) this.mContext.getSystemService("spengestureservice");
        }
        SpenGestureManager spenGestureManager = this.mSpenGestureManager;
        if (spenGestureManager == null) {
            return;
        }
        try {
            spenGestureManager.notifyBleSpenChargeLockState(i2 == 30002);
        } catch (RuntimeException e) {
            Slog.w("SemFpSpenConstraintHandler", "onSpenEvent: " + e.getMessage());
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener
    public void onAuthenticationAcquire(int i, int i2, int i3, int i4) {
        if (i3 == 6 && i4 == 10001 && this.mIsTspBlocked) {
            handleTspBlockAction(0);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpEnrollmentListener
    public void onEnrollAcquire(int i, int i2, int i3, int i4) {
        if (i3 == 6 && i4 == 10001 && this.mIsTspBlocked) {
            handleTspBlockAction(0);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpEnrollmentListener
    public void onEnrollStarted(final int i, int i2) {
        this.f1659mH.postDelayed(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpSpenConstraintHandler$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SemFpSpenConstraintHandler.this.lambda$onEnrollStarted$1(i);
            }
        }, 10L);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.SemFpAuthenticationListener
    public void onAuthenticationStarted(final int i, int i2) {
        this.f1659mH.postDelayed(new Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.SemFpSpenConstraintHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SemFpSpenConstraintHandler.this.lambda$onAuthenticationStarted$2(i);
            }
        }, 10L);
    }

    /* renamed from: notifyTspBlockStatusToHal, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final void lambda$onEnrollStarted$1(int i) {
        ((ServiceProvider) this.mProvider.second).semRequest(i, 35, this.mIsTspBlocked ? 1 : 0, null, null);
    }

    public void setTspBlockState(boolean z) {
        this.mIsTspBlocked = z;
    }
}
