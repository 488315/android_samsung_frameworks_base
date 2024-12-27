package com.android.systemui.keyguard;

import android.media.SoundPool;
import android.os.RemoteException;
import com.android.systemui.keyguard.SafeUIKeyguardViewMediator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class SafeUIKeyguardViewMediator$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ SafeUIKeyguardViewMediator$$ExternalSyntheticLambda8(Object obj, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = (SafeUIKeyguardViewMediator) this.f$0;
                int i = this.f$1;
                if (safeUIKeyguardViewMediator.mAudioManager.isStreamMute(safeUIKeyguardViewMediator.mUiSoundsStreamType)) {
                    return;
                }
                SoundPool soundPool = safeUIKeyguardViewMediator.mLockSounds;
                float f = safeUIKeyguardViewMediator.mLockSoundVolume;
                int play = soundPool.play(i, f, f, 1, 0, 1.0f);
                synchronized (safeUIKeyguardViewMediator) {
                    safeUIKeyguardViewMediator.mLockSoundStreamId = play;
                }
                return;
            case 1:
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator2 = (SafeUIKeyguardViewMediator) this.f$0;
                int i2 = this.f$1;
                if (safeUIKeyguardViewMediator2.mLockPatternUtils.isSecure(i2)) {
                    safeUIKeyguardViewMediator2.mLockPatternUtils.getDevicePolicyManager().reportKeyguardDismissed(i2);
                    return;
                }
                return;
            default:
                SafeUIKeyguardViewMediator.AnonymousClass13 anonymousClass13 = (SafeUIKeyguardViewMediator.AnonymousClass13) this.f$0;
                int i3 = this.f$1;
                anonymousClass13.getClass();
                try {
                    anonymousClass13.this$0.mActivityTaskManagerService.keyguardGoingAway(i3);
                    return;
                } catch (RemoteException e) {
                    android.util.Log.e("SafeUIKeyguardViewMediator", "Error while calling WindowManager", e);
                    return;
                }
        }
    }
}
