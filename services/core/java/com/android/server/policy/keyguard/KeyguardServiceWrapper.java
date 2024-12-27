package com.android.server.policy.keyguard;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IKeyguardDrawnCallback;
import com.android.internal.policy.IKeyguardExitCallback;
import com.android.internal.policy.IKeyguardService;
import com.android.internal.policy.IKeyguardStateCallback;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class KeyguardServiceWrapper implements IKeyguardService {
    public KeyguardStateMonitor mKeyguardStateMonitor;
    public IKeyguardService mService;

    public final void addStateMonitorCallback(IKeyguardStateCallback iKeyguardStateCallback) {
        try {
            this.mService.addStateMonitorCallback(iKeyguardStateCallback);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final IBinder asBinder() {
        return this.mService.asBinder();
    }

    public final void dismiss(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
        try {
            this.mService.dismiss(iKeyguardDismissCallback, charSequence);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void dismissKeyguardToLaunch(Intent intent) {
        try {
            this.mService.dismissKeyguardToLaunch(intent);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void doKeyguardTimeout(Bundle bundle) {
        KeyguardStateMonitor keyguardStateMonitor = this.mKeyguardStateMonitor;
        int i = keyguardStateMonitor.mCurrentUserId;
        if (keyguardStateMonitor.mLockPatternUtils.isSecure(i) || keyguardStateMonitor.mSimSecure) {
            this.mKeyguardStateMonitor.onShowingStateChanged(true, i);
        }
        try {
            this.mService.doKeyguardTimeout(bundle);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void onBootCompleted() {
        try {
            this.mService.onBootCompleted();
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void onDreamingStarted() {
        try {
            this.mService.onDreamingStarted();
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void onDreamingStopped() {
        try {
            this.mService.onDreamingStopped();
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void onFinishedBootAnim() {
        try {
            this.mService.onFinishedBootAnim();
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void onFinishedGoingToSleep(int i, boolean z) {
        try {
            this.mService.onFinishedGoingToSleep(i, z);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void onFinishedWakingUp() {
        try {
            this.mService.onFinishedWakingUp();
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void onScreenTurnedOff() {
        try {
            this.mService.onScreenTurnedOff();
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void onScreenTurnedOn() {
        try {
            this.mService.onScreenTurnedOn();
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void onScreenTurningOff() {
        try {
            this.mService.onScreenTurningOff();
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void onScreenTurningOn(IKeyguardDrawnCallback iKeyguardDrawnCallback) {
        try {
            this.mService.onScreenTurningOn(iKeyguardDrawnCallback);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void onShortPowerPressedGoHome() {
        try {
            this.mService.onShortPowerPressedGoHome();
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void onStartedGoingToSleep(int i) {
        try {
            this.mService.onStartedGoingToSleep(i);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void onStartedWakingUp(int i, boolean z) {
        try {
            this.mService.onStartedWakingUp(i, z);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void onSystemKeyPressed(int i) {
        try {
            this.mService.onSystemKeyPressed(i);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void onSystemReady() {
        try {
            this.mService.onSystemReady();
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void setCoverOccluded(boolean z) {
        try {
            this.mService.setCoverOccluded(z);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void setCurrentUser(int i) {
        KeyguardStateMonitor keyguardStateMonitor = this.mKeyguardStateMonitor;
        synchronized (keyguardStateMonitor) {
            keyguardStateMonitor.mCurrentUserId = i;
        }
        try {
            this.mService.setCurrentUser(i);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void setDexOccluded(boolean z) {
        try {
            this.mService.setDexOccluded(z);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void setKeyguardEnabled(boolean z) {
        try {
            this.mService.setKeyguardEnabled(z);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void setOccluded(boolean z, boolean z2) {
        try {
            this.mService.setOccluded(z, z2);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent intent) {
        try {
            this.mService.setPendingIntentAfterUnlock(pendingIntent, intent);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void setSwitchingUser(boolean z) {
        try {
            this.mService.setSwitchingUser(z);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void showDismissibleKeyguard() {
        try {
            this.mService.showDismissibleKeyguard();
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void startFingerprintAuthentication() {
        try {
            this.mService.startFingerprintAuthentication();
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void startKeyguardExitAnimation(long j, long j2) {
        try {
            this.mService.startKeyguardExitAnimation(j, j2);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void startedEarlyWakingUp(int i) {
        try {
            this.mService.startedEarlyWakingUp(i);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }

    public final void verifyUnlock(IKeyguardExitCallback iKeyguardExitCallback) {
        try {
            this.mService.verifyUnlock(iKeyguardExitCallback);
        } catch (RemoteException e) {
            Slog.w("KeyguardServiceWrapper", "Remote Exception", e);
        }
    }
}
