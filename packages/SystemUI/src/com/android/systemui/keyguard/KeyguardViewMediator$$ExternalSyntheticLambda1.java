package com.android.systemui.keyguard;

import android.media.SoundPool;
import android.os.Trace;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.KeyguardViewMediator;

public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardViewMediator f$0;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda1(KeyguardViewMediator keyguardViewMediator, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardViewMediator;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        KeyguardViewMediator keyguardViewMediator = this.f$0;
        switch (i) {
            case 0:
                keyguardViewMediator.getClass();
                android.util.Log.e("KeyguardViewMediator", "mHideAnimationFinishedRunnable#run");
                keyguardViewMediator.mHideAnimationRunning = false;
                keyguardViewMediator.tryKeyguardDone$1();
                break;
            case 1:
                keyguardViewMediator.mPM.userActivity(keyguardViewMediator.mSystemClock.uptimeMillis(), false);
                break;
            case 2:
                keyguardViewMediator.getClass();
                Trace.beginSection("KeyguardViewMediator#hideLocked");
                android.util.Log.d("KeyguardViewMediator", "hideLocked");
                KeyguardViewMediator.AnonymousClass13 anonymousClass13 = keyguardViewMediator.mHandler;
                anonymousClass13.sendMessage(anonymousClass13.obtainMessage(2));
                Trace.endSection();
                break;
            case 3:
                keyguardViewMediator.adjustStatusBarLocked$1(false, false);
                break;
            case 4:
                keyguardViewMediator.handleHide$1();
                break;
            case 5:
                long lockTimeout$1 = keyguardViewMediator.getLockTimeout$1(keyguardViewMediator.mSelectedUserInteractor.getSelectedUserId());
                if (lockTimeout$1 != 0) {
                    keyguardViewMediator.doKeyguardLaterLocked$1(lockTimeout$1);
                    break;
                } else {
                    keyguardViewMediator.doKeyguardLocked(null, false);
                    break;
                }
            case 6:
                keyguardViewMediator.tryKeyguardDone$1();
                break;
            case 7:
                keyguardViewMediator.setPendingLock(keyguardViewMediator.doKeyguardLocked(null, true));
                break;
            default:
                SoundPool soundPool = keyguardViewMediator.mLockSounds;
                int i2 = keyguardViewMediator.mLockSoundId;
                int i3 = keyguardViewMediator.mUnlockSoundId;
                int i4 = keyguardViewMediator.mTrustedSoundId;
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = keyguardViewMediator.mHelper;
                keyguardViewMediatorHelperImpl.getClass();
                int load = soundPool.load("/system/media/audio/ui/Unlock_VA_Mode.ogg", 1);
                keyguardViewMediatorHelperImpl.lockStaySoundId = load;
                if (load == 0) {
                    Log.w("KeyguardViewMediator", "failed to load lock stay sound from /system/media/audio/ui/Unlock_VA_Mode.ogg");
                }
                if (LsRune.KEYGUARD_LOCK_SITUATION_VOLUME) {
                    soundPool.semSetSituationType(i2, "stv_lock_screen");
                    soundPool.semSetSituationType(i3, "stv_unlock_screen");
                    soundPool.semSetSituationType(i4, "stv_unlock_screen");
                    soundPool.semSetSituationType(keyguardViewMediatorHelperImpl.lockStaySoundId, "stv_unlock_screen");
                }
                keyguardViewMediatorHelperImpl.lockSounds = soundPool;
                keyguardViewMediatorHelperImpl.unlockSoundId = i3;
                break;
        }
    }
}
