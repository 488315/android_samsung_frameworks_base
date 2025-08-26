package com.android.systemui.keyguard;

import android.content.Intent;
import android.os.RemoteException;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;

/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardViewMediator f$0;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda0(KeyguardViewMediator keyguardViewMediator, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardViewMediator;
    }

    @Override // java.lang.Runnable
    public final void run() throws RemoteException {
        int i = this.$r8$classId;
        KeyguardViewMediator keyguardViewMediator = this.f$0;
        switch (i) {
            case 0:
                Intent intent = KeyguardViewMediator.USER_PRESENT_INTENT;
                keyguardViewMediator.dismiss(null, null);
                break;
            case 1:
                Intent intent2 = KeyguardViewMediator.USER_PRESENT_INTENT;
                keyguardViewMediator.getClass();
                android.util.Log.d("KeyguardViewMediator", "mHideAnimationFinishedRunnable#run");
                keyguardViewMediator.mHideAnimationRunning = false;
                keyguardViewMediator.tryKeyguardDone$1();
                break;
            case 2:
                keyguardViewMediator.mPM.userActivity(keyguardViewMediator.mSystemClock.uptimeMillis(), false);
                break;
            case 3:
                Intent intent3 = KeyguardViewMediator.USER_PRESENT_INTENT;
                RecyclerView$$ExternalSyntheticOutline0.m(keyguardViewMediator.mGoingAwayRequestedForUserId, "KeyguardViewMediator", new StringBuilder("keyguardGoingAway requested for userId: "));
                try {
                    if (!keyguardViewMediator.mHelper.keyguardGoingAway(6)) {
                        throw new RemoteException();
                    }
                    break;
                } catch (RemoteException e) {
                    keyguardViewMediator.mSurfaceBehindRemoteAnimationRequested = false;
                    android.util.Log.e("KeyguardViewMediator", "Failed to report keyguardGoingAway", e);
                    return;
                }
            default:
                Intent intent4 = KeyguardViewMediator.USER_PRESENT_INTENT;
                keyguardViewMediator.setPendingLock(keyguardViewMediator.doKeyguardLocked(null, true));
                break;
        }
    }
}
