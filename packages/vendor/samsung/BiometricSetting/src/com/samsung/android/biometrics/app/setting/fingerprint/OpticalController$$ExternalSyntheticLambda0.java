package com.samsung.android.biometrics.app.setting.fingerprint;

import com.samsung.android.biometrics.app.setting.DisplayStateManager;

import java.util.function.BooleanSupplier;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final /* synthetic */ class OpticalController$$ExternalSyntheticLambda0
        implements BooleanSupplier {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;

    @Override // java.util.function.BooleanSupplier
    public final boolean getAsBoolean() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                OpticalController opticalController = (OpticalController) obj;
                for (int i2 = 0; i2 < opticalController.mMaskClients.size(); i2++) {
                    if (((OpticalController.MaskClient) opticalController.mMaskClients.valueAt(i2))
                            .mIsMaskSA) {
                        return true;
                    }
                }
                return false;
            default:
                return ((DisplayStateManager) obj).isOnState();
        }
    }
}
