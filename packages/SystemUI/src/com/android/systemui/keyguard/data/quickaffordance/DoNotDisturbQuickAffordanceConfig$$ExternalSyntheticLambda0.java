package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.modes.shared.ModesUi;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class DoNotDisturbQuickAffordanceConfig$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        int i = DoNotDisturbQuickAffordanceConfig.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i2 = ModesUi.$r8$clinit;
        throw new IllegalStateException("New code path not supported when android.app.modes_ui is disabled.");
    }
}
