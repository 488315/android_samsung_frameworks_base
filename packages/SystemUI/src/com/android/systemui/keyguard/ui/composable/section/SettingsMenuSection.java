package com.android.systemui.keyguard.ui.composable.section;

import com.android.systemui.keyguard.ui.viewmodel.KeyguardSettingsMenuViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardTouchHandlingViewModel;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.VibratorHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SettingsMenuSection {
    public final ActivityStarter activityStarter;
    public final KeyguardTouchHandlingViewModel touchHandlingViewModel;
    public final VibratorHelper vibratorHelper;
    public final KeyguardSettingsMenuViewModel viewModel;

    public SettingsMenuSection(KeyguardSettingsMenuViewModel keyguardSettingsMenuViewModel, KeyguardTouchHandlingViewModel keyguardTouchHandlingViewModel, VibratorHelper vibratorHelper, ActivityStarter activityStarter) {
        this.viewModel = keyguardSettingsMenuViewModel;
        this.touchHandlingViewModel = keyguardTouchHandlingViewModel;
        this.vibratorHelper = vibratorHelper;
        this.activityStarter = activityStarter;
    }
}
