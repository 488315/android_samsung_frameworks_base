package com.android.systemui.keyguard.ui.composable.section;

import com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;

/* loaded from: classes2.dex */
public final class DefaultClockSection {
    public final AodBurnInViewModel aodBurnInViewModel;
    public final KeyguardClockViewModel viewModel;

    public DefaultClockSection(KeyguardClockViewModel keyguardClockViewModel, AodBurnInViewModel aodBurnInViewModel) {
        this.viewModel = keyguardClockViewModel;
        this.aodBurnInViewModel = aodBurnInViewModel;
    }
}
