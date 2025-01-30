package com.android.systemui.keyguard;

import android.media.SoundPool;
import android.os.SystemClock;
import android.os.Trace;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.KeyguardViewMediator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardViewMediator f$0;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda2(KeyguardViewMediator keyguardViewMediator, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardViewMediator;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mTrustManager.reportKeyguardShowingChanged();
                break;
            case 1:
                KeyguardViewMediator keyguardViewMediator = this.f$0;
                keyguardViewMediator.getClass();
                Log.m140e("KeyguardViewMediator", "mHideAnimationFinishedRunnable#run");
                keyguardViewMediator.mHideAnimationRunning = false;
                keyguardViewMediator.tryKeyguardDone();
                break;
            case 2:
                KeyguardViewMediator keyguardViewMediator2 = this.f$0;
                keyguardViewMediator2.setPendingLock(keyguardViewMediator2.doKeyguardLocked(null, true));
                break;
            case 3:
                this.f$0.setPendingLock(true);
                break;
            case 4:
                this.f$0.startKeyguardExitAnimation(0L, 0L);
                break;
            case 5:
                this.f$0.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                break;
            case 6:
                KeyguardViewMediator keyguardViewMediator3 = this.f$0;
                keyguardViewMediator3.getClass();
                keyguardViewMediator3.handleStartKeyguardExitAnimation(SystemClock.uptimeMillis() + keyguardViewMediator3.mHideAnimation.getStartOffset(), keyguardViewMediator3.mHideAnimation.getDuration(), null, null, null, null);
                break;
            case 7:
                this.f$0.tryKeyguardDone();
                break;
            case 8:
                this.f$0.setPendingLock(false);
                break;
            case 9:
                this.f$0.resetStateLocked(true);
                break;
            case 10:
                KeyguardViewMediator keyguardViewMediator4 = this.f$0;
                keyguardViewMediator4.getClass();
                Trace.beginSection("KeyguardViewMediator#hideLocked");
                Log.m138d("KeyguardViewMediator", "hideLocked");
                KeyguardViewMediator.HandlerC147812 handlerC147812 = keyguardViewMediator4.mHandler;
                handlerC147812.sendMessage(handlerC147812.obtainMessage(2));
                Trace.endSection();
                break;
            case 11:
                this.f$0.mDelayedShowingSequence++;
                break;
            case 12:
                KeyguardViewMediator keyguardViewMediator5 = this.f$0;
                keyguardViewMediator5.getClass();
                long lockTimeout = keyguardViewMediator5.getLockTimeout(KeyguardUpdateMonitor.getCurrentUser());
                if (lockTimeout != 0) {
                    keyguardViewMediator5.doKeyguardLaterLocked(lockTimeout);
                    break;
                } else {
                    keyguardViewMediator5.doKeyguardLocked(null, false);
                    break;
                }
            case 13:
                this.f$0.mPendingReset = false;
                break;
            case 14:
                this.f$0.handleHide();
                break;
            case 15:
                this.f$0.adjustStatusBarLocked(false, false);
                break;
            default:
                KeyguardViewMediator keyguardViewMediator6 = this.f$0;
                SoundPool soundPool = keyguardViewMediator6.mLockSounds;
                int i = keyguardViewMediator6.mLockSoundId;
                int i2 = keyguardViewMediator6.mUnlockSoundId;
                int i3 = keyguardViewMediator6.mTrustedSoundId;
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = keyguardViewMediator6.mHelper;
                keyguardViewMediatorHelperImpl.getClass();
                int load = soundPool.load("/system/media/audio/ui/Unlock_VA_Mode.ogg", 1);
                keyguardViewMediatorHelperImpl.lockStaySoundId = load;
                if (load == 0) {
                    Log.m142w("KeyguardViewMediator", "failed to load lock stay sound from /system/media/audio/ui/Unlock_VA_Mode.ogg");
                }
                if (LsRune.KEYGUARD_LOCK_SITUATION_VOLUME) {
                    soundPool.semSetSituationType(i, "stv_lock_screen");
                    soundPool.semSetSituationType(i2, "stv_unlock_screen");
                    soundPool.semSetSituationType(i3, "stv_unlock_screen");
                    soundPool.semSetSituationType(keyguardViewMediatorHelperImpl.lockStaySoundId, "stv_unlock_screen");
                }
                keyguardViewMediatorHelperImpl.lockSounds = soundPool;
                keyguardViewMediatorHelperImpl.unlockSoundId = i2;
                break;
        }
    }
}
