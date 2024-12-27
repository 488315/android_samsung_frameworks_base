package com.android.server.power;

import android.os.VibrationEffect;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.policy.PhoneWindowManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class Notifier$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Notifier f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Notifier.Interactivity f$2;

    public /* synthetic */ Notifier$$ExternalSyntheticLambda3(
            Notifier notifier, int i, Notifier.Interactivity interactivity, int i2) {
        this.$r8$classId = i2;
        this.f$0 = notifier;
        this.f$1 = i;
        this.f$2 = interactivity;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                Notifier notifier = this.f$0;
                int i = this.f$1;
                Notifier.Interactivity interactivity = this.f$2;
                VibrationEffect vibrationEffect = Notifier.CHARGING_VIBRATION_EFFECT;
                notifier.getClass();
                ((PhoneWindowManager) notifier.mPolicy)
                        .finishedWakingUp(i, interactivity.changeOnReason);
                break;
            case 1:
                Notifier notifier2 = this.f$0;
                int i2 = this.f$1;
                Notifier.Interactivity interactivity2 = this.f$2;
                VibrationEffect vibrationEffect2 = Notifier.CHARGING_VIBRATION_EFFECT;
                notifier2.getClass();
                ((PhoneWindowManager) notifier2.mPolicy)
                        .finishedGoingToSleep(i2, interactivity2.changeOffReason);
                break;
            case 2:
                Notifier notifier3 = this.f$0;
                int i3 = this.f$1;
                Notifier.Interactivity interactivity3 = this.f$2;
                VibrationEffect vibrationEffect3 = Notifier.CHARGING_VIBRATION_EFFECT;
                notifier3.getClass();
                ((PhoneWindowManager) notifier3.mPolicy)
                        .startedWakingUp(i3, interactivity3.changeOnReason);
                StringBuilder sb = new StringBuilder("handleEarlyInteractiveChange: groupId=");
                sb.append(i3);
                sb.append(" reason=");
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        sb, interactivity3.changeOnReason, "PowerManagerNotifier");
                break;
            default:
                Notifier notifier4 = this.f$0;
                int i4 = this.f$1;
                Notifier.Interactivity interactivity4 = this.f$2;
                VibrationEffect vibrationEffect4 = Notifier.CHARGING_VIBRATION_EFFECT;
                notifier4.getClass();
                ((PhoneWindowManager) notifier4.mPolicy)
                        .startedGoingToSleep(i4, interactivity4.changeOffReason);
                break;
        }
    }
}
