package com.android.systemui.keyguard;

import android.media.SoundPool;
import com.android.internal.widget.LockPatternUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class SafeUIKeyguardViewMediator$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SafeUIKeyguardViewMediator f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ SafeUIKeyguardViewMediator$$ExternalSyntheticLambda6(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = safeUIKeyguardViewMediator;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = this.f$0;
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
            default:
                SafeUIKeyguardViewMediator safeUIKeyguardViewMediator2 = this.f$0;
                int i2 = this.f$1;
                LockPatternUtils lockPatternUtils = safeUIKeyguardViewMediator2.mLockPatternUtils;
                if (lockPatternUtils.isSecure(i2)) {
                    lockPatternUtils.getDevicePolicyManager().reportKeyguardDismissed(i2);
                    return;
                }
                return;
        }
    }
}
