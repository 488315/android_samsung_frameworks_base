package com.android.keyguard;

import com.samsung.android.bio.face.SemBioFaceManager;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda24 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda24(boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        boolean z = this.f$0;
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) obj;
        switch (i) {
            case 0:
                SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardUpdateMonitorCallback.onFaceUnlockOptionChanged(z);
                break;
            case 1:
                SemBioFaceManager semBioFaceManager2 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardUpdateMonitorCallback.onKeyguardVisibilityChanged(z);
                break;
            case 2:
                SemBioFaceManager semBioFaceManager3 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardUpdateMonitorCallback.onFaceWidgetFullscreenModeChanged(z);
                break;
            case 3:
                SemBioFaceManager semBioFaceManager4 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardUpdateMonitorCallback.onTableModeChanged(z);
                break;
            case 4:
                SemBioFaceManager semBioFaceManager5 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardUpdateMonitorCallback.onPrimaryBouncerVisibilityChanged(z);
                break;
            default:
                SemBioFaceManager semBioFaceManager6 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardUpdateMonitorCallback.onUSBRestrictionChanged(z);
                break;
        }
    }
}
