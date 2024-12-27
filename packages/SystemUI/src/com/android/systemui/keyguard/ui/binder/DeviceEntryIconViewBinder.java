package com.android.systemui.keyguard.ui.binder;

import android.widget.ImageView;
import androidx.compose.ui.graphics.Color;
import com.android.systemui.Flags;
import com.android.systemui.common.ui.view.LongPressHandlingView;
import com.android.systemui.deviceentry.shared.DeviceEntryUdfpsRefactor;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryBackgroundViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.VibratorHelper;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.CoroutineScope;

public final class DeviceEntryIconViewBinder {
    static {
        new DeviceEntryIconViewBinder();
    }

    private DeviceEntryIconViewBinder() {
    }

    /* renamed from: bind-9Oi015Q, reason: not valid java name */
    public static final void m1965bind9Oi015Q(CoroutineScope coroutineScope, DeviceEntryIconView deviceEntryIconView, DeviceEntryIconViewModel deviceEntryIconViewModel, DeviceEntryForegroundViewModel deviceEntryForegroundViewModel, DeviceEntryBackgroundViewModel deviceEntryBackgroundViewModel, FalsingManager falsingManager, VibratorHelper vibratorHelper, Color color) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
        Flags.deviceEntryUdfpsRefactor();
        LongPressHandlingView longPressHandlingView = deviceEntryIconView.longPressHandlingView;
        ImageView imageView = deviceEntryIconView.iconView;
        ImageView imageView2 = deviceEntryIconView.bgView;
        longPressHandlingView.listener = new DeviceEntryIconViewBinder$bind$1(falsingManager, vibratorHelper, coroutineScope, deviceEntryIconViewModel);
        RepeatWhenAttachedKt.repeatWhenAttached(deviceEntryIconView, EmptyCoroutineContext.INSTANCE, new DeviceEntryIconViewBinder$bind$2(deviceEntryIconViewModel, longPressHandlingView, deviceEntryIconView, vibratorHelper, coroutineScope, imageView2, null));
        RepeatWhenAttachedKt.repeatWhenAttached(imageView, EmptyCoroutineContext.INSTANCE, new DeviceEntryIconViewBinder$bind$3(imageView, deviceEntryForegroundViewModel, deviceEntryIconView, color, null));
        RepeatWhenAttachedKt.repeatWhenAttached(imageView2, EmptyCoroutineContext.INSTANCE, new DeviceEntryIconViewBinder$bind$4(deviceEntryBackgroundViewModel, imageView2, null));
    }
}
