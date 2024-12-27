package com.android.systemui.keyguard.ui.binder;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.coroutines.EmptyCoroutineContext;

public final class KeyguardBlueprintViewBinder {
    public static final KeyguardBlueprintViewBinder INSTANCE = new KeyguardBlueprintViewBinder();

    private KeyguardBlueprintViewBinder() {
    }

    public static final void bind(KeyguardBlueprintViewModel keyguardBlueprintViewModel, ConstraintLayout constraintLayout, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel) {
        RepeatWhenAttachedKt.repeatWhenAttached(constraintLayout, EmptyCoroutineContext.INSTANCE, new KeyguardBlueprintViewBinder$bind$1(keyguardBlueprintViewModel, keyguardClockViewModel, keyguardSmartspaceViewModel, constraintLayout, null));
    }
}
