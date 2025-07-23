package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import com.android.systemui.keyguard.shared.model.ClockSizeSetting;
import com.android.systemui.keyguard.ui.view.KeyguardRootView;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewClockViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.clocks.ClockPreviewConfig;
import com.android.systemui.shared.clocks.ClockRegistry;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardPreviewClockViewBinder {
    public static final KeyguardPreviewClockViewBinder INSTANCE = new KeyguardPreviewClockViewBinder();
    public static final int lockId = View.generateViewId();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClockSizeSetting.values().length];
            try {
                iArr[ClockSizeSetting.DYNAMIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClockSizeSetting.SMALL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private KeyguardPreviewClockViewBinder() {
    }

    public static final void bind(KeyguardRootView keyguardRootView, KeyguardPreviewClockViewModel keyguardPreviewClockViewModel, ClockRegistry clockRegistry, Function3 function3, ClockPreviewConfig clockPreviewConfig) {
        RepeatWhenAttachedKt.repeatWhenAttached(keyguardRootView, EmptyCoroutineContext.INSTANCE, new KeyguardPreviewClockViewBinder$bind$3(keyguardPreviewClockViewModel, function3, clockPreviewConfig, keyguardRootView, clockRegistry, null));
    }
}
