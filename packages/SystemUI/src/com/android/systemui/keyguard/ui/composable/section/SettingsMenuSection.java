package com.android.systemui.keyguard.ui.composable.section;

import com.android.systemui.keyguard.ui.viewmodel.KeyguardLongPressViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSettingsMenuViewModel;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.VibratorHelper;

public final class SettingsMenuSection {
    public final ActivityStarter activityStarter;
    public final KeyguardLongPressViewModel longPressViewModel;
    public final VibratorHelper vibratorHelper;
    public final KeyguardSettingsMenuViewModel viewModel;

    public SettingsMenuSection(KeyguardSettingsMenuViewModel keyguardSettingsMenuViewModel, KeyguardLongPressViewModel keyguardLongPressViewModel, VibratorHelper vibratorHelper, ActivityStarter activityStarter) {
        this.activityStarter = activityStarter;
    }
}
