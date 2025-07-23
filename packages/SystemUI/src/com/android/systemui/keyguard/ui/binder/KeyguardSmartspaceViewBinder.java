package com.android.systemui.keyguard.ui.binder;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$1;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardSmartspaceViewBinder {
    public static final KeyguardSmartspaceViewBinder INSTANCE = new KeyguardSmartspaceViewBinder();

    private KeyguardSmartspaceViewBinder() {
    }

    public static final RepeatWhenAttachedKt$repeatWhenAttached$1 bind(ConstraintLayout constraintLayout, KeyguardRootViewModel keyguardRootViewModel, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, KeyguardBlueprintInteractor keyguardBlueprintInteractor) {
        KeyguardSmartspaceViewBinder$bind$1 keyguardSmartspaceViewBinder$bind$1 = new KeyguardSmartspaceViewBinder$bind$1(constraintLayout, keyguardSmartspaceViewModel, keyguardClockViewModel, keyguardBlueprintInteractor, keyguardRootViewModel, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        return RepeatWhenAttachedKt.repeatWhenAttached(constraintLayout, EmptyCoroutineContext.INSTANCE, keyguardSmartspaceViewBinder$bind$1);
    }
}
