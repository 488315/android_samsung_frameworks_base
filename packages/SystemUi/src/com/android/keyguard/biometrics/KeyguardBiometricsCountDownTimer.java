package com.android.keyguard.biometrics;

import android.content.Context;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.FaceAuthUiEvent;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.widget.SystemUITextView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardBiometricsCountDownTimer extends CountDownTimer {
    public SystemUITextView mBiometricMessageArea;
    public final int mBiometricType;
    public final Context mContext;
    public final int mFailedAttempts;
    public boolean mIsTalkbackUpdated;

    public KeyguardBiometricsCountDownTimer(Context context, long j, long j2, SystemUITextView systemUITextView) {
        super(j, j2);
        this.mContext = context;
        this.mBiometricMessageArea = systemUITextView;
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
        int failedBiometricUnlockAttempts = keyguardUpdateMonitor.getFailedBiometricUnlockAttempts(KeyguardUpdateMonitor.getCurrentUser());
        this.mFailedAttempts = failedBiometricUnlockAttempts;
        int biometricType = keyguardUpdateMonitor.getBiometricType(KeyguardUpdateMonitor.getCurrentUser());
        this.mBiometricType = biometricType;
        StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("KeyguardBiometricsCountDownTimer( millisInFuture = ", j, " , countDownInterval = ");
        m17m.append(j2);
        m17m.append(" , mFailedAttempts = ");
        m17m.append(failedBiometricUnlockAttempts);
        m17m.append(" , mBiometricType = ");
        m17m.append(biometricType);
        m17m.append(" )");
        Log.d("KeyguardBiometricsCountDownTimer", m17m.toString());
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
        ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_FACE_LOCKOUT_RESET);
    }

    @Override // android.os.CountDownTimer
    public final void onTick(long j) {
        String quantityString;
        int round = (int) Math.round(j / 1000.0d);
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
        StringBuilder m2m = AbstractC0000x2c234b15.m2m(quantityString, " ");
        m2m.append(this.mContext.getResources().getQuantityString(R.plurals.kg_too_many_failed_countdown, round, Integer.valueOf(round)));
        String sb = m2m.toString();
        SystemUITextView systemUITextView = this.mBiometricMessageArea;
        if (systemUITextView == null) {
            Log.d("KeyguardBiometricsCountDownTimer", "onTick ( mBiometricMessageArea is null )");
            return;
        }
        systemUITextView.setText(sb);
        if (this.mIsTalkbackUpdated) {
            return;
        }
        this.mBiometricMessageArea.announceForAccessibility(sb);
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
