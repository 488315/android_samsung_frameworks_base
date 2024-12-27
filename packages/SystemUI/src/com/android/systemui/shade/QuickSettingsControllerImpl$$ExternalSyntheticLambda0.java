package com.android.systemui.shade;

import java.util.function.BooleanSupplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class QuickSettingsControllerImpl$$ExternalSyntheticLambda0 implements BooleanSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QuickSettingsControllerImpl f$0;

    public /* synthetic */ QuickSettingsControllerImpl$$ExternalSyntheticLambda0(QuickSettingsControllerImpl quickSettingsControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = quickSettingsControllerImpl;
    }

    @Override // java.util.function.BooleanSupplier
    public final boolean getAsBoolean() {
        int i = this.$r8$classId;
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
        switch (i) {
            case 0:
                return quickSettingsControllerImpl.mAnimatorExpand;
            case 1:
                return quickSettingsControllerImpl.mStackScrollerOverscrolling;
            case 2:
                return quickSettingsControllerImpl.mEnableClipping;
            case 3:
                return quickSettingsControllerImpl.mShadeExpandedFraction <= 0.0f;
            default:
                return quickSettingsControllerImpl.getExpanded$1();
        }
    }
}
