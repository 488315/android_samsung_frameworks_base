package com.android.systemui.keyguard.ui.binder;

import android.widget.ImageView;
import androidx.compose.ui.graphics.Color;
import com.android.systemui.common.ui.view.TouchHandlingView;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryBackgroundViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.kotlin.DisposableHandles;
import com.google.android.msdl.domain.MSDLPlayer;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class DeviceEntryIconViewBinder {
    static {
        new DeviceEntryIconViewBinder();
    }

    private DeviceEntryIconViewBinder() {
    }

    /* renamed from: bind-7D8XEZs, reason: not valid java name */
    public static final void m2617bind7D8XEZs(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, DeviceEntryIconView deviceEntryIconView, DeviceEntryIconViewModel deviceEntryIconViewModel, DeviceEntryForegroundViewModel deviceEntryForegroundViewModel, DeviceEntryBackgroundViewModel deviceEntryBackgroundViewModel, FalsingManager falsingManager, VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, Color color) {
        DisposableHandles disposableHandles = new DisposableHandles();
        TouchHandlingView touchHandlingView = deviceEntryIconView.touchHandlingView;
        ImageView imageView = deviceEntryIconView.iconView;
        ImageView imageView2 = deviceEntryIconView.bgView;
        touchHandlingView.listener = new DeviceEntryIconViewBinder$bind$1(falsingManager, vibratorHelper, coroutineScope, deviceEntryIconViewModel);
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(deviceEntryIconView, coroutineDispatcher, new DeviceEntryIconViewBinder$bind$2(deviceEntryIconViewModel, imageView2, deviceEntryIconView, null)));
        DeviceEntryIconViewBinder$bind$3 deviceEntryIconViewBinder$bind$3 = new DeviceEntryIconViewBinder$bind$3(deviceEntryIconViewModel, touchHandlingView, deviceEntryIconView, mSDLPlayer, vibratorHelper, coroutineScope, null);
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(deviceEntryIconView, emptyCoroutineContext, deviceEntryIconViewBinder$bind$3));
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(imageView, emptyCoroutineContext, new DeviceEntryIconViewBinder$bind$4(imageView, deviceEntryForegroundViewModel, color, deviceEntryIconView, null)));
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(imageView2, coroutineDispatcher, new DeviceEntryIconViewBinder$bind$5(deviceEntryBackgroundViewModel, imageView2, null)));
    }
}
