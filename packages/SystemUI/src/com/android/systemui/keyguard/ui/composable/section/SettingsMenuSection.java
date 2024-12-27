package com.android.systemui.keyguard.ui.composable.section;

import com.android.systemui.keyguard.ui.viewmodel.KeyguardLongPressViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSettingsMenuViewModel;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.VibratorHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SettingsMenuSection {
    public final ActivityStarter activityStarter;
    public final KeyguardLongPressViewModel longPressViewModel;
    public final VibratorHelper vibratorHelper;
    public final KeyguardSettingsMenuViewModel viewModel;

    public SettingsMenuSection(KeyguardSettingsMenuViewModel keyguardSettingsMenuViewModel, KeyguardLongPressViewModel keyguardLongPressViewModel, VibratorHelper vibratorHelper, ActivityStarter activityStarter) {
        this.activityStarter = activityStarter;
    }
}
