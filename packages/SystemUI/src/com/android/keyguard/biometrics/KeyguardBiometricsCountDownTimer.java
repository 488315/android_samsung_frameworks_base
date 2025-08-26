package com.android.keyguard.biometrics;

import android.content.Context;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.widget.SystemUITextView;

/* loaded from: classes.dex */
public class KeyguardBiometricsCountDownTimer extends CountDownTimer {
    public SystemUITextView mBiometricMessageArea;
    public final int mBiometricType;
    public final Context mContext;
    public final int mFailedAttempts;
    public boolean mIsTalkbackUpdated;

    public KeyguardBiometricsCountDownTimer(Context context, long j, long j2, SystemUITextView systemUITextView) {
        super(j, j2);
        this.mContext = context;
        this.mBiometricMessageArea = systemUITextView;
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
        int failedBiometricUnlockAttempts = keyguardUpdateMonitor.getFailedBiometricUnlockAttempts();
        this.mFailedAttempts = failedBiometricUnlockAttempts;
        int biometricType = keyguardUpdateMonitor.getBiometricType();
        this.mBiometricType = biometricType;
        StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("KeyguardBiometricsCountDownTimer( millisInFuture = ", j, " , countDownInterval = ");
        sbM.append(j2);
        sbM.append(" , mFailedAttempts = ");
        sbM.append(failedBiometricUnlockAttempts);
        sbM.append(" , mBiometricType = ");
        sbM.append(biometricType);
        sbM.append(" )");
        Log.d("KeyguardBiometricsCountDownTimer", sbM.toString());
    }

    @Override // android.os.CountDownTimer
    public final void onFinish() {
        Log.d("KeyguardBiometricsCountDownTimer", "onFinish()");
        SystemUITextView systemUITextView = this.mBiometricMessageArea;
        if (systemUITextView != null) {
            systemUITextView.setText("");
            this.mBiometricMessageArea.setVisibility(8);
        }
        this.mIsTalkbackUpdated = false;
        ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_FACE_LOCKOUT_RESET);
    }

    @Override // android.os.CountDownTimer
    public final void onTick(long j) throws Resources.NotFoundException {
        String quantityString;
        int iRound = (int) Math.round(j / 1000.0d);
        int i = this.mBiometricType;
        if (i == 1) {
            Resources resources = this.mContext.getResources();
            int i2 = this.mFailedAttempts;
            quantityString = resources.getQuantityString(R.plurals.kg_too_many_failed_attempts_by_fingerprint, i2, Integer.valueOf(i2));
        } else if (i != 256) {
            Resources resources2 = this.mContext.getResources();
            int i3 = this.mFailedAttempts;
            quantityString = resources2.getQuantityString(R.plurals.kg_too_many_failed_attempts_by_biometrics, i3, Integer.valueOf(i3));
        } else {
            Resources resources3 = this.mContext.getResources();
            int i4 = this.mFailedAttempts;
            quantityString = resources3.getQuantityString(R.plurals.kg_too_many_failed_attempts_by_face, i4, Integer.valueOf(i4));
        }
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(quantityString, " ");
        sbM.append(this.mContext.getResources().getQuantityString(R.plurals.kg_too_many_failed_countdown, iRound, Integer.valueOf(iRound)));
        String string = sbM.toString();
        SystemUITextView systemUITextView = this.mBiometricMessageArea;
        if (systemUITextView == null) {
            Log.d("KeyguardBiometricsCountDownTimer", "onTick ( mBiometricMessageArea is null )");
            return;
        }
        systemUITextView.setText(string);
        if (this.mIsTalkbackUpdated) {
            return;
        }
        this.mBiometricMessageArea.announceForAccessibility(string);
        this.mIsTalkbackUpdated = true;
    }

    public final void stop() {
        Log.d("KeyguardBiometricsCountDownTimer", "stop()");
        SystemUITextView systemUITextView = this.mBiometricMessageArea;
        if (systemUITextView != null) {
            systemUITextView.setText("");
            this.mBiometricMessageArea.setVisibility(8);
            this.mBiometricMessageArea = null;
        }
        this.mIsTalkbackUpdated = false;
        cancel();
    }
}
