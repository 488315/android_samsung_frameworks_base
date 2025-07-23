package com.android.systemui.keyguard.ui.composable.section;

import com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DefaultClockSection {
    public final AodBurnInViewModel aodBurnInViewModel;
    public final KeyguardClockViewModel viewModel;

    public DefaultClockSection(KeyguardClockViewModel keyguardClockViewModel, AodBurnInViewModel aodBurnInViewModel) {
        this.viewModel = keyguardClockViewModel;
        this.aodBurnInViewModel = aodBurnInViewModel;
    }
}
