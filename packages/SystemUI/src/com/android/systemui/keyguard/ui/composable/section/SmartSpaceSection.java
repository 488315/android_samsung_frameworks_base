package com.android.systemui.keyguard.ui.composable.section;

import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SmartSpaceSection {
    public final AodBurnInViewModel aodBurnInViewModel;
    public final KeyguardSmartspaceViewModel keyguardSmartspaceViewModel;
    public final KeyguardUnlockAnimationController keyguardUnlockAnimationController;
    public final LockscreenSmartspaceController lockscreenSmartspaceController;

    public SmartSpaceSection(LockscreenSmartspaceController lockscreenSmartspaceController, KeyguardUnlockAnimationController keyguardUnlockAnimationController, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, AodBurnInViewModel aodBurnInViewModel) {
        this.lockscreenSmartspaceController = lockscreenSmartspaceController;
        this.keyguardUnlockAnimationController = keyguardUnlockAnimationController;
        this.keyguardSmartspaceViewModel = keyguardSmartspaceViewModel;
        this.aodBurnInViewModel = aodBurnInViewModel;
    }
}
