package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.VibratorHelper;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

public final class DeviceEntryIconViewBinder$bind$1 {
    public final /* synthetic */ CoroutineScope $applicationScope;
    public final /* synthetic */ FalsingManager $falsingManager;
    public final /* synthetic */ VibratorHelper $vibratorHelper;
    public final /* synthetic */ DeviceEntryIconViewModel $viewModel;

    public DeviceEntryIconViewBinder$bind$1(FalsingManager falsingManager, VibratorHelper vibratorHelper, CoroutineScope coroutineScope, DeviceEntryIconViewModel deviceEntryIconViewModel) {
        this.$falsingManager = falsingManager;
        this.$vibratorHelper = vibratorHelper;
        this.$applicationScope = coroutineScope;
        this.$viewModel = deviceEntryIconViewModel;
    }

    public final void onLongPressDetected(View view) {
        if (this.$falsingManager.isFalseLongTap(1)) {
            return;
        }
        this.$vibratorHelper.getClass();
        view.performHapticFeedback(16);
        BuildersKt.launch$default(this.$applicationScope, null, null, new DeviceEntryIconViewBinder$bind$1$onLongPressDetected$1(this.$viewModel, null), 3);
    }
}
