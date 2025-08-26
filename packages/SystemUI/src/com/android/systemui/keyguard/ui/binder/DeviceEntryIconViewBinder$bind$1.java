package com.android.systemui.keyguard.ui.binder;

import android.util.Log;
import android.view.View;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.VibratorHelper;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
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

    public final void onLongPressDetected(View view, boolean z) {
        if (!z && this.$falsingManager.isFalseLongTap(1)) {
            Log.d("DeviceEntryIconViewBinder", "Long press rejected because it is not a11yAction and it is a falseLongTap");
            return;
        }
        this.$vibratorHelper.getClass();
        view.performHapticFeedback(16);
        CoroutineTracingKt.launchTraced$default(this.$applicationScope, null, null, new DeviceEntryIconViewBinder$bind$1$onLongPressDetected$1(view, this.$viewModel, null), 7);
    }
}
