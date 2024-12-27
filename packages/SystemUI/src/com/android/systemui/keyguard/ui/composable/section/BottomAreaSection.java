package com.android.systemui.keyguard.ui.composable.section;

import com.android.systemui.keyguard.ui.viewmodel.KeyguardIndicationAreaViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordancesCombinedViewModel;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.VibratorHelper;

public final class BottomAreaSection {
    public final FalsingManager falsingManager;
    public final KeyguardIndicationAreaViewModel indicationAreaViewModel;
    public final KeyguardIndicationController indicationController;
    public final VibratorHelper vibratorHelper;
    public final KeyguardQuickAffordancesCombinedViewModel viewModel;

    public BottomAreaSection(KeyguardQuickAffordancesCombinedViewModel keyguardQuickAffordancesCombinedViewModel, FalsingManager falsingManager, VibratorHelper vibratorHelper, KeyguardIndicationController keyguardIndicationController, KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel) {
        this.falsingManager = falsingManager;
    }
}
