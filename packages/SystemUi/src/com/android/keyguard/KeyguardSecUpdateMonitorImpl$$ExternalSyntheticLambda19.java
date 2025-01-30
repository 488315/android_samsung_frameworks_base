package com.android.keyguard;

import android.hardware.biometrics.BiometricSourceType;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda19 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda19(int i, Object obj, boolean z) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl = (KeyguardSecUpdateMonitorImpl) this.f$0;
                ((KeyguardUpdateMonitorCallback) obj).onBiometricAuthenticated(((UserTrackerImpl) keyguardSecUpdateMonitorImpl.mUserTracker).getUserId(), BiometricSourceType.FACE, this.f$1);
                break;
            default:
                ((KeyguardUpdateMonitorCallback) obj).onPackageRemoved((String) this.f$0, this.f$1);
                break;
        }
    }
}
